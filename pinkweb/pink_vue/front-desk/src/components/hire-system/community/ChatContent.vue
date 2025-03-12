<template>
  <div class="chat-main" v-if="this.box.index === this.show">
    <div class="right-header">{{box.name}}</div>
    <div class="right-body">
      <el-row>
        <el-col :span="18" class="left-body">
          <div class="content-body">
            <chat-item v-for="(community, index) in this.sortList" :key="index" :community="community" :index="index"></chat-item>
          </div>
          <div class="input-body">
            <el-form ref="form" :model="communication" label-width="80px">
              <div class="enter-text">
                <el-input :rows="3" type="textarea" v-model="communication.text"></el-input>
              </div>
              <el-button @click="onSubmit" size="mini" type="warning" class="communicate-sub">发送</el-button>
            </el-form>
          </div>
        </el-col>
        <el-col :span="6" class="right-chat-body">
          <div class="rc-title_1">使用APP聊天更方便</div>
          <img src="../../../assets/img/QR/code.jpg" alt="qr-code" class="qr-code">
          <div class="rc-title_2">扫描使用微信小程序</div>
          <div class="rc-title_2">了解更多</div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  import ChatItem from "./ChatItem";
  export default {
    name: "ChatContent",
    components: {ChatItem},
    props: {
      box: Object,
      index: Number,
      show: Number
    },
    data() {
      return{
        communication: {
          text: '',
        },
        sortList: []
      }
    },
    methods: {
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
              m_time: new Date().toISOString().slice(0, 19).replace('T', ' '),
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
        sortTimeList.sort();
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
