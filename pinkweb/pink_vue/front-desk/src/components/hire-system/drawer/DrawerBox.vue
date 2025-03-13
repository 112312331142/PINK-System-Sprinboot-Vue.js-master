<template>
  <div>
    <!-- 触发抽屉的按钮 -->
    <el-button @click="drawer = true" type="primary" icon="el-icon-s-operation" circle class="drawer-btn">
    </el-button>

    <!-- 抽屉组件 -->
    <el-drawer :visible.sync="drawer" :with-header="false" size="40%">
      <div class="ai-chat-container">
        <!-- 消息显示区域 -->
        <div class="chat-messages">
          <div v-for="(message, index) in messages" :key="index" :class="['message', message.role]">
            <span>{{ message.content }}</span>
          </div>
        </div>

        <!-- 输入框和发送按钮 -->
        <div class="chat-input">
          <el-input v-model="inputMessage" placeholder="请输入您的问题..." @keyup.enter="sendMessage" class="input-area">
          </el-input>
          <el-button @click="sendMessage" type="primary" class="send-btn">
            发送
          </el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
export default {
  name: "DrawerBox",
  data() {
    return {
      drawer: false, // 控制抽屉的显示状态
      inputMessage: "", // 用户输入的消息
      messages: [
        {
          role: 'ai',
          content: '您好，我是AI机器人，请问有什么问题吗？'
        },
      ], // 存储聊天记录
    };
  },
  methods: {
    /**
     * 发送消息并模拟 AI 回复
     */
    async sendMessage() {
      if (!this.inputMessage.trim()) {
        this.$message.warning("请输入有效内容！");
        return;
      }

      // 添加用户消息到聊天记录
      this.messages.push({ role: "user", content: this.inputMessage });

      // 清空输入框
      const userMessage = this.inputMessage;
      this.inputMessage = "";

      // 模拟 AI 回复
      await this.simulateAIResponse(userMessage);
    },

    /**
     * 模拟 AI 回复（可以替换为实际的 API 调用）
     * @param {string} userMessage - 用户发送的消息
     */
    async simulateAIResponse(chat) {
      /*  模拟延迟
       await new Promise((resolve) => setTimeout(resolve, 1000));
 
       // 简单的 AI 回复逻辑
       let aiResponse = "抱歉，我无法理解您的问题。";
       if (userMessage.includes("你好")) {
         aiResponse = "您好！有什么可以帮您的吗？";
       } else if (userMessage.includes("天气")) {
         aiResponse = "今天的天气很晴朗！";
       } else if (userMessage.includes("谢谢")) {
         aiResponse = "不客气！随时为您服务。";
       }
 
       添加 AI 消息到聊天记录
       this.messages.push({ role: "ai", content: aiResponse });
  */
      // ------------------
      try {
        const response = await this.$http.post('http://localhost:8085/chat/stream', {
          id: 1,
          message: chat
        });

        // 提取返回数据中的有用信息
        const rawData = response.data; // 响应的原始数据
        const messages = rawData.split('\n') // 按行分割数据
          .filter(line => line.startsWith('data:')) // 过滤出以 "data:" 开头的行
          .map(line => line.replace('data:', '').trim()) // 去掉 "data:" 并去除多余空格
          .filter(line => line !== '[DONE]'); // 去掉结束标志 [DONE]

        // 将所有消息拼接成一个完整的字符串
        const aiResponse = messages.join('');

        // 创建新的消息对象
        const newChat = {
          role: 'ai',
          content: aiResponse // 将提取的内容赋值给 content
        };

        // 将新消息添加到 chats 数组
        this.messages.push(newChat);
      } catch (error) {
        console.error('AIChat 请求失败:', error);
        this.$message({
          showClose: true,
          message: '与服务器通信失败，请稍后重试',
          type: 'warning'
        });
        // alert('与服务器通信失败，请稍后重试');
      }
    },
  },
};
</script>

<style scoped>
@import "../../../assets/css/hire-system/components/drawerbox.css";
</style>