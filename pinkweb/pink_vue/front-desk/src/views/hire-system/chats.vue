<template>
  <div class="release-body">
    <page-title>
      讯息 Chats
    </page-title>
    <el-row class="main-body">
      <el-col :span="6" class="left-content">
        <div class="search-col">
          <el-row>
            <el-col :span="20">
              <el-input placeholder="搜索" v-model="search" class="search-input">
                <i slot="prefix" class="el-input__icon el-icon-search"></i>
              </el-input>
            </el-col>
            <el-col :span="4" class="new-col">
              <span id="new_btn" class="new-btn" @mouseout="mouseOut" @mouseover="mouseOver">
                <i class="el-icon-plus"></i>
              </span>
            </el-col>
          </el-row>
        </div>
        <div class="target-li">
          <ul>
            <chat-box v-for="(box, index) in chat_boxes" :key="index" :box="box" :index="index"
              @func="getFromChild"></chat-box>
          </ul>
        </div>
      </el-col>
      <el-col :span="18">
        <div class="right-content">
          <chat-content v-for="(box, index) in chat_boxes" :key="index" :box="box" :index="index"
            :show="connect_index" ref="chatContent"></chat-content>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// 导入PageTitle模块
import PageTitle from "../../components/hire-system/title/PageTitle";
// 导入ChatBox模块
import ChatBox from "../../components/hire-system/community/ChatBox";
// 导入ChatContent模块
import ChatContent from "../../components/hire-system/community/ChatContent";
export default {
  name: "chats",
  components: { ChatContent, ChatBox, PageTitle },
  data() {
    return {
      search: '',
      show_chat: '1',
      connect_index: 0,
      chat_boxes: [
        // {
        //   index: 0,
        //   id: '10001',
        //   avatar: require('../../assets/img/avatar/avatar02.png'),
        //   name: '马小超',
        //   // 聊天内容
        //   communities: [
        //     {
        //       // 信息id号
        //       m_id: '01',
        //       // 发送方或接收方判定(0是用户发送过来的，1是hr发送过去的)
        //       m_flag: 1,
        //       // 接收方id
        //       m_s_id: "10001",
        //       // 发送方id
        //       m_r_id: "10001",
        //       // 信息时间
        //       m_time: '2021-07-25 15:17:07',
        //       // 内容
        //       m_content: '山不在高，有仙则名。水不在深，有龙则灵。'
        //     },
        //     {
        //       m_id: '02',
        //       m_flag: 0,
        //       m_s_id: "10001",
        //       m_r_id: "10001",
        //       m_time: '2008-06-25 15:18:08',
        //       m_content: '斯是陋室，惟吾德馨。苔痕上阶绿，草色入帘青。'
        //     },
        //     {
        //       m_id: '03',
        //       m_flag: 0,
        //       m_s_id: "10001",
        //       m_r_id: "10001",
        //       m_time: '2020-08-25 15:18:08',
        //       m_content: '谈笑有鸿儒，往来无白丁。可以调素琴，阅金经。'
        //     },
        //     {
        //       m_id: '04',
        //       m_flag: 1,
        //       m_s_id: "10001",
        //       m_r_id: "10001",
        //       m_time: '2019-08-25 15:18:08',
        //       m_content: '无丝竹之乱耳，无案牍之劳形。南阳诸葛庐，西蜀子云亭。孔子云：何陋之有？'
        //     }
        //   ]
        // },
        // {
        //   index: 1,
        //   id: '10002',
        //   avatar: require('../../assets/img/avatar/avatar01.png'),
        //   name: '余三胖',
        //   // 聊天内容
        //   communities: [
        //     {
        //       m_id: '01',
        //       m_flag: 0,
        //       m_s_id: "10001",
        //       m_r_id: "10001",
        //       m_time: '2021-03-05 15:10:07',
        //       m_content: '寒蝉凄切，对长亭晚，骤雨初歇。'
        //     },
        //     {
        //       m_id: '02',
        //       m_flag: 1,
        //       m_s_id: "10001",
        //       m_r_id: "10001",
        //       m_time: '2021-03-05 15:10:08',
        //       m_content: '都门帐饮无绪，留恋处，兰舟催发。'
        //     },
        //     {
        //       m_id: '03',
        //       m_flag: 0,
        //       m_s_id: "10001",
        //       m_r_id: "10001",
        //       m_time: '2021-03-05 15:10:09',
        //       m_content: '执手相看泪眼，竟无语凝噎。'
        //     },
        //     {
        //       m_id: '04',
        //       m_flag: 1,
        //       m_s_id: "10001",
        //       m_r_id: "10001",
        //       m_time: '2021-03-05 15:10:10',
        //       m_content: '念去去，千里烟波，暮霭沉沉楚天阔。'
        //     },
        //     {
        //       m_id: '05',
        //       m_flag: 0,
        //       m_s_id: "10001",
        //       m_r_id: "10001",
        //       m_time: '2021-03-05 15:11:10',
        //       m_content: '多情自古伤离别，更那堪，冷落清秋节！'
        //     },
        //     {
        //       m_id: '06',
        //       m_flag: 1,
        //       m_s_id: "10001",
        //       m_r_id: "10001",
        //       m_time: '2021-03-05 15:11:11',
        //       m_content: '今宵酒醒何处？杨柳岸，晓风残月。'
        //     },
        //     {
        //       m_id: '07',
        //       m_flag: 0,
        //       m_s_id: "10001",
        //       m_r_id: "10001",
        //       m_time: '2021-03-05 15:13:21',
        //       m_content: '此去经年，应是良辰好景虚设。'
        //     },
        //     {
        //       m_id: '08',
        //       m_flag: 1,
        //       m_s_id: "10001",
        //       m_r_id: "10001",
        //       m_time: '2021-03-05 15:13:22',
        //       m_content: '便纵有千种风情，更与何人说？'
        //     }
        //   ]
        // },
      ]
    }
  },
  created() {
    console.log("创建组件");
    console.log("r_id=" + localStorage.getItem('r_id'));
    console.log("c_id=" + localStorage.getItem('c_id'));
    this.fetchMessages()
  },
  methods: {
    // 获取用户名称的方法
    async getUserName(id) {
      try {
        const response = await this.$http.get('http://localhost:8085/message/getName', {
          params: { id: id }
        });
        return response.data;
      } catch (error) {
        console.log("获取用户名称失败：", error);
        return `用户${id}`; // 获取失败时返回默认名称
      }
    },

    // 修改后的 fetchMessages 方法
    async fetchMessages() {
      const _this = this;
      try {
        const response = await this.$http.get('http://localhost:8085/message/getHr', {
          params: {
            r_id: localStorage.getItem('r_id')
          }
        });

        // 创建一个临时对象来按 c_id 分组消息
        const messageGroups = {};
        
        // 使用 for...of 循环来支持 async/await
        for (const message of response.data) {
          const c_id = message.c_id.toString();
          
          // 如果这个 c_id 的分组还不存在，就创建一个新的
          if (!messageGroups[c_id]) {
            // 获取用户真实姓名
            const userName = await this.getUserName(parseInt(c_id));
            
            messageGroups[c_id] = {
              index: Object.keys(messageGroups).length,
              id: c_id,
              avatar: require('../../assets/img/avatar/avatar01.png'),
              name: userName,
              communities: []
            };
          }


          // 将消息添加到对应的分组中
          messageGroups[c_id].communities.push({
            m_id: message.m_id.toString(),
            m_flag: message.flag,
            m_s_id: message.r_id.toString(),
            m_r_id: message.c_id.toString(),
            m_time: message.m_time,
            m_content: message.m_content
          });
        }
        
        // 将分组后的结果转换为数组并赋值给 chat_boxes
        _this.chat_boxes = Object.values(messageGroups);
        
        console.log("回显信息成功", _this.chat_boxes);
      } catch (error) {
        console.log("回显信息失败：", error);
      }
    },
    mouseOver() {
      const dom = document.getElementById("new_btn");
      dom.style.backgroundColor = "#f3f3f3";
    },
    mouseOut() {
      const dom = document.getElementById("new_btn");
      dom.style.backgroundColor = "#ffffff";
    },
    // 获取子组件传来的值
    getFromChild(index) {
      this.connect_index = index
    },
    async sendMessage(message, c_id) {
      try {
        const response = await this.$http.post('http://localhost:8085/message/send', {
          data: {
            u_id: c_id,                    // 接收方用户id
            p_id: localStorage.getItem('r_id'),  // 发送方(HR)id
            message: message               // 消息内容
          }
        });

        if (response.data === "Message received successfully") {
          // 发送成功后，更新本地消息列表
          const targetGroup = this.chat_boxes.find(box => box.id === c_id);
          if (targetGroup) {
            const newMessage = {
              m_id: Date.now().toString(),
              m_flag: 1,                   // HR发送的消息
              m_s_id: c_id,
              m_r_id: localStorage.getItem('r_id'),
              m_time: new Date().toISOString().slice(0, 19).replace('T', ' '),
              m_content: message
            };
            targetGroup.communities.push(newMessage);
            
            // 通知子组件更新排序
            this.$refs.chatContent.getNew(targetGroup.communities);
          }
        }
      } catch (error) {
        console.log("发送消息失败：", error);
      }
    }
  }
}
</script>

<style scoped>
@import "../../assets/css/hire-system/chats.css";
</style>
