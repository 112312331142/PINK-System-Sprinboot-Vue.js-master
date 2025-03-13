<template>
  <footer class="footer">
    <el-col :span="8">
      <div class="footer-content footer-left">
        <div class="copyright footer-title">Copyright © 2021 Pink</div>
      </div>
    </el-col>
    <el-col :span="8">
      <div class="footer-content footer-left">
        <div class="bold footer-title">Contract</div>
        <div class="middle">Huazhong University of Science and Technology</div>
        <div class="middle">M202076625@hust.edu.cn</div>
      </div>
    </el-col>
    <el-col :span="8">
      <div class="footer-content">
        <div class="footer-icons">
          <el-button type="primary" icon="el-icon-phone" circle></el-button>
          <el-button type="success" icon="el-icon-service" circle></el-button>
          <el-button type="info" icon="el-icon-message" circle @click="MailVisible = true"></el-button>
          <el-button type="danger" icon="el-icon-chat-dot-round" @click="AIChatVisible = true">
            AI智能聊天</el-button>
        </div>
      </div>
      <!-- mail发送邮件 -->
      <el-dialog :visible.sync="MailVisible" title="发送邮件给我们">
        <el-form ref="form" :model="emailForm" label-width="80px">
          <el-form-item label="收件人">
            <el-input v-model="emailForm.to" placeholder="请输入收件人邮箱"></el-input>
          </el-form-item>
          <el-form-item label="主题">
            <el-input v-model="emailForm.subject" placeholder="请输入邮件主题"></el-input>
          </el-form-item>
          <el-form-item label="内容">
            <el-input :rows="5" type="textarea" v-model="emailForm.text" placeholder="请输入邮件内容"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="sendEmail">发送</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
      <!-- AI智能聊天 -->
      <el-dialog :visible.sync="AIChatVisible" title="AI智能聊天">
        <el-row>
          <el-col :span="16">
            <div class="left-content" height="400px">
              <el-row>
                <el-col :span="24">
                  <ul class="dialogue-ul">
                    <communicate-li v-for="chat in chats" :key="chat.index" :chat="chat"></communicate-li>
                  </ul>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <div class="enter-box">
                    <el-form ref="form" :model="communication" label-width="80px">
                      <div class="enter-text">
                        <el-input :rows="3" type="textarea" v-model="communication.text"></el-input>
                      </div>
                      <el-button @click="onSubmit" size="mini" type="warning" class="communicate-sub">发送</el-button>
                    </el-form>
                  </div>
                </el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>
      </el-dialog>
    </el-col>
  </footer>
</template>


<script>
import CommunicateLi from "../../../components/apply-system/communicate/CommunicateLi.vue";

export default {
  name: "Footer",
  components: {
    CommunicateLi
  },
  data() {
    return {
      AIChatVisible: false,
      MailVisible: false,
      chats: [
        {
          index: 1,
          type: 'AI回复',
          content: '您好，我是AI机器人，请问有什么问题吗？'
        },
        // {
        //   index: 2,
        //   type: '已接受',
        //   content: '不可能，你太菜了'
        // }
      ],
      communication: {
        text: ''
      },
      emailForm: {
        to: '',
        subject: '',
        text: ''
      }
    }
  },
  methods: {
    onSubmit() {
      if (this.communication.text.trim() === '') {
        this.$message({
          showClose: true,
          message: '内容不能为空',
          type: 'warning'
        });
        return;
      }

      // 创建新的消息对象
      const newChat = {
        index: this.chats.length + 1,
        type: '已发送',
        content: this.communication.text
      };

      // 将新消息添加到 chats 数组
      this.chats.push(newChat);

      // 清空输入框
      this.communication.text = '';

      // 发送消息到后端并处理流式响应
      this.AIChat(newChat);
    },
    async AIChat(chat) {
      // 获取user用户的id

      try {
        const response = await this.$http.post('http://localhost:8085/chat/stream', {
          id: 1,
          message: chat.content
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
          index: this.chats.length + 1,
          type: 'AI回复', // 修改为 "已接受" 表示这是 AI 的回复
          content: aiResponse // 将提取的内容赋值给 content
        };

        // 将新消息添加到 chats 数组
        this.chats.push(newChat);
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
    async sendEmail() {

      try {
        // 调用后端发邮件接口
        const response = await this.$http.post('http://localhost:8085/sendEmail', {
          to: this.emailForm.to, // 收件人邮箱
          subject: this.emailForm.subject, // 邮件主题
          text: this.emailForm.text // 邮件内容
        });

        // 检查响应状态
        if (response.status === 200 && response.data.success) {
          this.$message({
            showClose: true,
            message: '邮件发送成功',
            type: 'success'
          });
        } else {
          this.$message({
            showClose: true,
            message: '邮件发送失败，请稍后重试。',
            type: 'error'
          });
          // alert('邮件发送失败，请稍后重试。');
        }
      } catch (error) {
        console.error('邮件发送失败:', error);
        // alert('邮件发送失败，请检查网络或联系管理员。');
        this.$message({
          showClose: true,
          message: '邮件发送失败，请检查网络或联系管理员。',
          type: 'error'
        });
      }
      this.MailVisible = false;
      // 清空emailForm
      this.emailForm = {
        to: this.emailForm.to,
        subject: '',
        text: ''
      };
    }

  }
}
</script>

<style scoped>
@import "../../../assets/css/apply-system/components/footer.css";
@import "../../../assets/css/apply-system/position-detail.css";
</style>
