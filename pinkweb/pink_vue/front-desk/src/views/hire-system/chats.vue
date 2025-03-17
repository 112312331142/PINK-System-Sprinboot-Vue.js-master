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
              <span id="new_btn" class="new-btn" @mouseout="mouseOut" @mouseover="mouseOver"
                @click="userListVisible = true">
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
          <chat-content v-for="(box, index) in chat_boxes" :key="index" :box="box" :index="index" :show="connect_index"
            ref="chatContent"></chat-content>
        </div>
      </el-col>
    </el-row>
    <!-- 用户列表弹窗 -->
    <!--     <el-dialog title="用户列表" :visible.sync="userListVisible" width="70%" :data="userList">
      <el-table :data="paginatedUserList" style="width: 100%" border>
        <el-table-column prop="id" label="用户ID" width="120"></el-table-column>
        <el-table-column prop="avatar" label="头像">
          <template slot-scope="scope">
            <img :src="scope.row.avatar" alt="" class="avatar-img" width="40px" />
          </template>
</el-table-column>
<el-table-column prop="name" label="用户名" width="180"></el-table-column>
<el-table-column prop="email" label="邮箱"></el-table-column>
<el-table-column prop="phone" label="电话"></el-table-column>
<el-table-column label="操作" width="120">
  <template slot-scope="scope">
            <el-button type="text" @click="selectUser(scope.row)">选择</el-button>
          </template>
</el-table-column>
</el-table>

<el-pagination class="pagination" layout="prev, pager, next" :total="userList.length" :page-size="pageSize"
  :current-page="pageNow" @current-change="handlePageChange">
</el-pagination>
</el-dialog> -->
    <el-dialog title="用户列表" :visible.sync="userListVisible" width="70%">
      <el-table :data="userList" style="width: 100%" border>
        <!-- 申请者ID -->
        <el-table-column prop="apply_id" label="ID" width="100" />
        <!-- 申请者姓名 -->
        <el-table-column prop="candidate.user.name" label="姓名" width="120" sortable />
        <!-- 申请时间 -->
        <el-table-column prop="created_time" label="申请时间" width="150" sortable />
        <!-- 毕业院校 -->
        <el-table-column prop="candidate.highSchool" label="毕业院校" width="160" sortable />
        <!-- 学历 -->
        <el-table-column prop="candidate.highDegree" label="学历" width="120" sortable />
        <!-- 申请岗位 -->
        <el-table-column prop="recruitment.r_name" label="申请岗位" width="130" sortable />
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <el-button type="text" @click="selectUser(scope.row)">选择</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页组件 -->
      <el-pagination class="pagination" background @size-change="findSizeChange" @current-change="findPage"
        :current-page.sync="pageNow" :page-size="size" layout="total, prev, pager, next" :total="total">
      </el-pagination>
    </el-dialog>
  </div>
</template>

<script>
// 导入PageTitle模块
import PageTitle from "../../components/hire-system/title/PageTitle";
// 导入ChatBox模块
import ChatBox from "../../components/hire-system/community/ChatBox.vue";
// 导入ChatContent模块
import ChatContent from "../../components/hire-system/community/ChatContent.vue";
export default {
  name: "chats",
  components: { ChatContent, ChatBox, PageTitle },
  /**
 * data函数用于定义组件的响应式数据对象。
 * 返回一个包含以下属性的对象：
 * 
 * @property {string} search - 用于存储搜索框的输入内容，初始值为空字符串。
 * @property {string} show_chat - 控制聊天界面的显示状态，初始值为'1'。
 * @property {number} connect_index - 表示连接索引或某种状态的数值，初始值为3。
 * 
 * @returns {Object} 包含组件所需的所有响应式数据属性。
 */
  data() {
    return {
      search: '',
      show_chat: '1',
      connect_index: 0,
      userListVisible: false,
      chat_boxes: [
      ],
      userList: [
      ],
      pageNow: 0, // 当前页码
      pageSize: 5,    // 每页显示条数
      size: 5,
      total: 0,
    }
  },
  computed: {
    // // 计算当前页的用户列表
    // paginatedUserList() {
    //   const start = (this.pageNow - 1) * this.pageSize;
    //   const end = start + this.pageSize;
    //   return this.userList.slice(start, end);
    // }
  },
  created() {
    this.fetchUserList()
    // console.log("r_id=" + localStorage.getItem('r_id'));
    // console.log("c_id=" + localStorage.getItem('c_id'));
    this.fetchMessages()
  },
  methods: {
    // 当每页条数改变时的处理函数
    findSizeChange(size) {
      console.log("当每页条数改变时" + size);
      this.size = size;
      this.findAll()
    },
    // 当页码改变时的处理函数
    findPage() {
      console.log(this.pageNow)
      this.findAll(this.pageNow, this.size)
    },
    // 获取数据的函数
    findAll(page, size) {
      var that = this
      this.$http.get('http://localhost:8085/hire/hire_attract/' + localStorage.getItem("r_id"), {
        params: {
          pageNow: this.pageNow,
          pageSize: this.size
        }
      })
        .then(function (response) {
          that.userList = []
          console.log(response.data.list.length)
          for (var len = (that.pageNow - 1) * that.size; len < that.pageNow * that.size; len++) {
            console.log("len=" + len);
            console.log("ccc=" + that.pageNow * that.size);
            if (len >= response.data.list.length) {
              break;
            }
            // 将获取到的数据格式化后存入table_data
            that.userList[len] = response.data.list[len];
            that.userList[len].created_time = that.userList[len].created_time.substring(0, 10)
            console.log(that.userList[len].created_time)
          }
          // 设置总条数
          that.total = response.data.total

        })
        .catch(function (error) {
          console.log(error);
        });
    },
    fetchUserList() {
      this.userList = []
      console.log("创建组件");
      const _this = this
      this.$http.get('http://localhost:8085/hire/hire_attract/' + localStorage.getItem("r_id"), {
        params: {
          pageNow: 1,
          pageSize: 5
        }
      })
        .then(function (response) {
          for (var len = 0; len <= 4; len++) {
            if (len >= response.data.list.length) {
              break;
            }
            // 将获取到的数据格式化后存入table_data
            _this.userList.push(response.data.list[len])
            console.log("--" + response.data.list[len]);
            _this.userList[len].created_time = _this.userList[len].created_time.substring(0, 10)
            console.log(_this.userList[len].created_time)
          }
          // 设置总条数
          _this.total = response.data.total
        })
        .catch(function (error) {
          console.log(error)
        });

    },
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
            console.log("chats:name=" + userName + " " + message.m_content);

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
            m_s_id: message.c_id.toString(),
            m_r_id: message.r_id.toString(),
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
      console.log("子组件传来的值：" + this.connect_index);

    },
    // 选择用户
    selectUser(user) {
      console.log("选择了用户：", user);
      this.userListVisible = false; // 关闭弹窗
      // 可以在这里处理选中用户的逻辑，比如添加到聊天框
      const message = "我们可以聊天了，" + user.candidate.user.name
      this.sendMessage(message, user.apply_id.toString())
      this.fetchMessages()

    },
    async sendMessage(message, c_id) {
      try {
        const response = await this.$http.post('http://localhost:8085/message/sendHr', {
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
              m_time: this.formatCurrentTime(),
              m_content: message
            };
            targetGroup.communities.push(newMessage);

            // 通知子组件更新排序
            // this.$refs.chatContent.getNew(targetGroup.communities);
            // this.$refs.chatContent.scrollToBottom();
            this.fetchMessages();
            // 刷新页面
            this.$router.go(0);

          }
        }
      } catch (error) {
        console.log("发送消息失败：", error);
      }
    }
  },
  watch: {
    connect_index(newVal) {
      console.log("connect_index 变化为：" + newVal);
    }
  }
}
</script>

<style scoped>
@import "../../assets/css/hire-system/chats.css";
</style>
