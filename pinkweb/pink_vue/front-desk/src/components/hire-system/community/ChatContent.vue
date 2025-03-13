<template>
  <!-- 主聊天界面组件，仅在当前 box.index 等于 show 时显示 -->
  <div class="chat-main" v-if="this.box.index === this.show">
    <!-- 右侧头部区域，显示当前聊天对象的名称和索引 -->
    <div class="right-header">{{ box.name }}</div>
    <!-- 右侧主体区域，包含聊天内容和输入框 -->
    <div class="right-body">
      <el-row>
        <!-- 左侧聊天内容区域，占据18列宽度 -->
        <el-col :span="18" class="left-body">
          <div class="content-body">
            <!-- 遍历排序后的聊天列表，渲染每个聊天项 -->
            <chat-item v-for="(community, index) in this.sortList" :key="index" :community="community"
              :index="index"></chat-item>
          </div>
          <!-- 输入框区域，用于发送消息 -->
          <div class="input-body">
            <el-form ref="form" :model="communication" label-width="80px">
              <div class="enter-text">
                <!-- 多行文本输入框，绑定 communication.text -->
                <el-input :rows="3" type="textarea" v-model="communication.text"></el-input>
              </div>
              <!-- 发送按钮，点击后触发 onSubmit 方法 -->
              <el-button @click="onSubmit" size="mini" type="warning" class="communicate-sub">发送</el-button>
            </el-form>
          </div>
        </el-col>
        <!-- 右侧辅助信息区域，占据6列宽度 -->
        <el-col :span="6" class="right-chat-body">
          <!-- 提示用户使用APP聊天更方便 -->
          <div class="rc-title_1">使用APP聊天更方便</div>
          <!-- 显示二维码图片，引导用户扫描 -->
          <img src="../../../assets/img/QR/code.jpg" alt="qr-code" class="qr-code">
          <!-- 提示用户扫描二维码使用微信小程序 -->
          <div class="rc-title_2">扫描使用微信小程序</div>
          <!-- 提示用户了解更多 -->
          <div class="rc-title_2">了解更多</div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import ChatItem from "./ChatItem.vue";
export default {
  name: "ChatContent",
  components: { ChatItem },
  props: {
    box: Object,
    index: Number,
    show: Number
  },
  data() {
    return {
      communication: {
        text: '',
      },
      sortList: []
    }
  },
  watch: {
    show(newVal) {
      console.log("show 变化为：" + newVal);
    }
  },
  created() {

    console.log("ChatContent" + this.show);

    // this.getNew(this.box.communities)
  },
  methods: {
    formatCurrentTime() {
      const now = new Date();
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, '0'); // 月份从0开始，需加1
      const day = String(now.getDate()).padStart(2, '0');
      const hours = String(now.getHours()).padStart(2, '0');
      const minutes = String(now.getMinutes()).padStart(2, '0');
      const seconds = String(now.getSeconds()).padStart(2, '0');
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },
    async onSubmit() {
      // 如果消息为空，不发送
      if (!this.communication.text.trim()) {
        return;
      }

      try {
        // 发送消息到后端
        const response = await this.$http.post('http://localhost:8085/message/sendHr', {
          data: {
            u_id: this.box.id,                     // 接收方用户id
            p_id: localStorage.getItem('r_id'),    // 发送方(HR)id
            message: this.communication.text        // 消息内容
          }
        });

        if (response.data === "Message received successfully") {
          // 发送成功后，在本地添加新消息
          const newMessage = {
            m_id: Date.now().toString(),
            m_flag: 1,                    // HR发送的消息
            m_s_id: localStorage.getItem('r_id'),
            m_r_id: this.box.id,
            m_time: this.formatCurrentTime(),
            m_content: this.communication.text
          };

          // 添加到聊天记录中
          this.box.communities.push(newMessage);

          // 重新排序消息
          this.getNew(this.box.communities);
          

          // 清空输入框
          this.communication.text = '';
        }
      } catch (error) {
        console.log("发送消息失败：", error);
        this.$message.error('发送消息失败，请重试');
      }
    },
    // 重新按时间对聊天内容排序
    getNew(communities) {
      let sortTimeList = [];
      let sortList = [];
      for (let i = 0; i < communities.length; i++) {
        sortTimeList.push(Date.parse(communities[i].m_time))
      }
      sortTimeList.sort()
      // sortTimeList.sort((a, b) => b - a);
      for (let j = 0; j < sortTimeList.length; j++) {
        for (let i = 0; i < communities.length; i++) {
          if (Date.parse(communities[i].m_time) === sortTimeList[j]) {
            sortList.push(communities[i])
          }
        }
      }
      this.sortList = sortList
    }
  },
  mounted() {
    this.getNew(this.box.communities)
  }
}
</script>

<style scoped>
@import "../../../assets/css/hire-system/components/chatcontent.css";
</style>
