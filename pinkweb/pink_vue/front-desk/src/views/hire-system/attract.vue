<template>
  <div class="attract-body">
    <!-- 页面标题 -->
    <page-title>
      招揽 Attract
    </page-title>
    <div class="main-body">
      <div class="info-table">
        <!-- 申请者信息表格 -->
        <el-table ref="multipleTable" :data="table_data" tooltip-effect="dark" style="width: 100%" highlight-current-row
          :default-sort="{ prop: 'date', order: 'descending' }">
          <!-- 选择框 -->
          <el-table-column type="selection" width="60" />
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
          <!-- 岗位代号 -->
          <el-table-column prop="p_id" label="岗位代号" width="130" sortable />
          <!-- 申请岗位 -->
          <el-table-column prop="recruitment.r_name" label="申请岗位" width="130" sortable />
          <!-- 操作按钮：详情和删除 -->
          <el-table-column fixed="right" label="操作" width="130">
            <template slot-scope="scope">
              <!-- 查看详情按钮 -->
              <el-button @click="detailClick(scope.row)" type="text" size="small">详情</el-button>
              <!-- 删除按钮，带确认弹窗 -->
              <!-- <el-popconfirm title="确定接受？" @confirm="acceptClick(scope.row)"> -->
              <el-button @click="acceptClick(scope.row)" slot="reference" type="text" size="small">接受</el-button>
              <!-- </el-popconfirm> -->
              <el-popconfirm title="确定删除？" @confirm="deleteClick(scope.row)">
                <el-button slot="reference" type="text" size="small">删除</el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="table-foot">
        <!-- 分页组件 -->
        <el-pagination background @size-change="findSizeChange" @current-change="findPage" :current-page.sync="pageNow"
          :page-size="size" layout="total, prev, pager, next" :total="total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import PageTitle from "../../components/hire-system/title/PageTitle";
/**
 * "attract" 组件定义
 * 该组件用于展示和管理招聘吸引信息
 */
export default {
  name: "attract",
  components: { PageTitle },
  data() {
    return {
      // 表格数据数组
      table_data: [],
      // 每页显示的条数
      size: 8,
      //总条数
      total: 0,
      // 当前页码
      pageNow: 1,
      // 申请时间
      apply_time: "",
      // ehr系统ID
      ehr_id: ''
    }
  },
  created() {
    const _this = this
    // 在组件创建时获取表格数据
    // 需要从后端获取数据，这里使用axios进行HTTP请求
    this.$http.get('http://localhost:8085/hire/hire_attract/' + localStorage.getItem("r_id"), {
      params: {
        pageNow: 1,
        pageSize: 8
      }
    })
      .then(function (response) {
        for (var len = 0; len <= 7; len++) {
          if (len >= response.data.list.length) {
            break;
          }
          // 将获取到的数据格式化后存入table_data
          _this.table_data.push(response.data.list[len])
          _this.table_data[len].created_time = _this.table_data[len].created_time.substring(0, 10)
          console.log(_this.table_data[len].created_time)
        }
        // 设置总条数
        _this.total = response.data.total
      })
      .catch(function (error) {
        console.log(error)
      });

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
          that.table_data = []
          console.log(response.data.list.length)
          for (var len = (that.pageNow - 1) * that.size; len <= that.pageNow * that.size - 1; len++) {
            if (len >= response.data.list.length) {
              break;
            }
            // 将获取到的数据格式化后存入table_data
            that.table_data[len] = response.data.list[len];
            console.log(response.data.list[len])
            console.log(that.table_data[len])
            that.table_data[len].created_time = that.table_data[len].created_time.substring(0, 10)
            console.log(that.table_data[len].created_time)
          }
          // 设置总条数
          that.total = response.data.total

        })
        .catch(function (error) {
          console.log(error);
        });
    },
    // 点击详情按钮时的处理函数
    detailClick(index) {
      // 跳转到详情页面
      this.$router.push({
        path: '/hire/attract_detail',
        query: {
          id: index.apply_id,
        }
      })
    },
    // 点击删除按钮时的处理函数
    deleteClick(index) {
      console.log(index);
      var _this = this
      this.$http.get('http://localhost:8085/hire/hire_attract/delete/', {
        params: {
          apply_id: index.apply_id,
        }
      })
        .then(function (response) {
          for (var len = 0; len < response.data.length; len++) {
            // 将删除后的数据更新到table_data
            _this.table_data.push(response.data[len])
          }
        })
        .catch(function (error) {
          console.log(error);
        })
      // 刷新页面
      location.reload();
    },
    async acceptClick(index) {
      const c_id = index.apply_id
      console.log(c_id);
      const sendMessage = "您已成功被录用"
      await this.$http.post('http://localhost:8085/message/sendHr', {
        data: {
          u_id: c_id.toString(),                    // 接收方用户id
          p_id: localStorage.getItem('r_id'),  // 发送方(HR)id
          message: sendMessage               // 消息内容
        }
      });

      this.deleteClick(index);
    },
  }
}
</script>

<style scoped>
@import "../../assets/css/hire-system/table-common.css";
</style>
