<template>
  <div class="home">
    <div class="stat">
      <!--用户卡片-->
      <div class="stat-user">
        <div class="stat-user__title">
          后台管理系统模板
        </div>
        <div class="stat-user__detail">
          <p>欢迎您，{{ username }}</p>
          <p>当前时间：{{ nowTime }}</p>
        </div>
        <img class="stat-user__portrait" src="portrait.jpg" alt="" />
      </div>
      <!--系统统计数据概览-->
      <div class="stat-info">
        <el-row :gutter="20" v-for="(info, index) in stat" :key="index">
          <el-col :span="8" v-for="(item, index) in info" :key="index">
            <div class="stat-info__item">
              <div
                class="stat-info__icon"
                :style="{ 'background-color': item.bgColor }"
              >
                <i :class="item.icon"></i>
              </div>
              <div class="stat-info__detail">
                <p class="stat-info__total">{{ item.total }}</p>
                <p class="stat-info__title">{{ item.title }}</p>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <el-row :gutter="20" class="margin-t-20 list">
      <!--待办事项-->
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span style="cursor:pointer" @click="openTodoList"
              ><i class="el-icon-tickets margin-r-5"></i>待办事项</span
            >
            <i class="el-icon-plus" @click="addNewTodoItem" title="新增"></i>
          </div>
          <p v-for="(item, index) in todoList.slice(0, 10)" :key="index">
            <el-checkbox
              v-model="item.status"
              @change="changeSatus(item)"
            ></el-checkbox
            >&nbsp;&nbsp;
            <span :class="{ active: item.status }">{{ item.content }}</span>
          </p>
        </el-card>
      </el-col>
      <!--最新消息-->
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span><i class="el-icon-news margin-r-5"></i>最新消息</span>
          </div>
          <p v-for="(item, i) in latestNewList" :key="i">
            <span class="latest-new-list__time"
              ><i class="el-icon-time margin-r-5"></i>{{ item.time }}：</span
            >
            <span>{{ item.title }}</span>
          </p>
          <span>more</span>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog title="待办事项" :visible.sync="todoListDialog" width="770px">
      <el-tabs v-model="activeName">
        <el-tab-pane label="待办事项" name="first">
          <div class="todolist-box">
            <el-table
              :data="todoList.filter(e => !e.status)"
              style="width: 100%"
            >
              <el-table-column type="index" width="50">
                <template slot-scope="scope">
                  <el-checkbox
                    v-model="scope.row.status"
                    @change="changeSatus(scope.row)"
                  ></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column
                prop="createTime"
                label="创建时间"
                width="180"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column
                prop="content"
                label="内容"
                width="420"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column label="操作" width="80">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="danger"
                    icon="el-icon-delete"
                    circle
                    @click="removeTodoList(scope.row)"
                  ></el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
        <el-tab-pane label="已完成" name="second">
          <div class="todolist-box">
            <el-table
              :data="todoList.filter(e => e.status)"
              style="width: 100%"
            >
              <el-table-column type="index" width="50">
                <template slot-scope="scope">
                  <el-checkbox
                    v-model="scope.row.status"
                    @change="changeSatus(scope.row)"
                  ></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column
                prop="completeTime"
                label="完成时间"
                width="180"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column
                prop="content"
                label="内容"
                width="420"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column label="操作" width="80">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="danger"
                    icon="el-icon-delete"
                    circle
                    @click="removeTodoList(scope.row)"
                  ></el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
        <el-tab-pane label="回收站" name="thrid"
          ><div class="todolist-box">
            <el-table :data="delTodoList" style="width: 100%">
              <el-table-column type="index" width="50">
                <template slot-scope="scope">
                  <el-checkbox
                    v-model="scope.row.status"
                    disabled
                  ></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column
                prop="createTime"
                label="创建时间"
                width="180"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column
                prop="content"
                label="内容"
                width="110"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column
                prop="completeTime"
                label="完成时间"
                width="180"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column label="操作" width="180">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="success"
                    plain
                    @click="recoverTodoList(scope.row)"
                    >还原</el-button
                  >
                  <el-button
                    size="mini"
                    type="danger"
                    plain
                    @click="deleteTodoList(scope.row)"
                    >彻底删除</el-button
                  >
                </template>
              </el-table-column>
            </el-table>
          </div></el-tab-pane
        >
      </el-tabs>
    </el-dialog>
  </div>
</template>

<script>
import Util from "@/assets/js/util";
import * as sysTool from "@/assets/js/systemTool";

const latestNewList = [];
for (let i = 0; i < 10; i++) {
  latestNewList.push({
    time: new Date(new Date().getTime() + i * 24 * 3600 * 1000).Format(
      "yyyy-MM-dd"
    ),
    title: "今日的最新新闻来咯~~~"
  });
}
export default {
  name: "Home",
  data() {
    return {
      stat: [
        [
          {
            icon: "el-icon-service",
            title: "公司总员工数",
            total: 198397,
            bgColor: "#ebcc6f"
          },
          {
            icon: "el-icon-location-outline",
            title: "客户分布区域",
            total: 19,
            bgColor: "#3acaa9"
          },
          {
            icon: "el-icon-star-off",
            title: "收货好评",
            total: 190857,
            bgColor: "#67c4ed"
          }
        ],
        [
          {
            icon: "el-icon-edit-outline",
            title: "历史订单数",
            total: 9397,
            bgColor: "#af84cb"
          },
          {
            icon: "el-icon-share",
            title: "产品总转发数量",
            total: 9097,
            bgColor: "#67c4ed"
          },
          {
            icon: "el-icon-goods",
            title: "产品总数",
            total: 397,
            bgColor: "#ebcc6f"
          }
        ]
      ],
      username: localStorage.getItem("username"),
      nowTime: new Date().Format("yyyy-MM-dd hh:mm:ss"),
      todoList: [],
      delTodoList: [],
      latestNewList,
      visitorInfo: {
        ip: "",
        area: "",
        brower: "",
        os: ""
      },
      todoListDialog: false,
      activeName: "first"
    };
  },
  methods: {
    setNowTime() {
      setInterval(() => {
        this.nowTime = new Date().Format("yyyy-MM-dd hh:mm:ss");
      }, 1000);
    },
    getTodoList() {
      this.$axios
        .get("/api/home/getTodoList")
        .then(({ data }) => {
          if (data.success) {
            this.todoList = data.data.filter(e => !e.delState);
            this.delTodoList = data.data.filter(e => e.delState);
          } else {
            this.$message.warning("获取待办事项列表失败！");
          }
        })
        .catch(err => {
          console.log(err);
          this.$message.error("获取待办事项失败！");
        });
    },
    addNewTodoItem() {
      this.$prompt("请输入待办事项主题", "新增待办事项", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      })
        .then(({ value }) => {
          this.doAddTodoList(value);
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消新增待办事项"
          });
        });
    },
    doAddTodoList(value) {
      this.$axios
        .post("/api/home/addTodoList", {
          content: value
        })
        .then(({ data }) => {
          if (data.success) {
            this.$message.success("新增待办事项成功！");
            this.getTodoList();
          } else {
            this.$message.warning("新增待办事项失败！");
          }
        })
        .catch(err => {
          console.log(err);
          this.$message.error("新增待办事项失败！");
        });
    },
    removeTodoList(row) {
      this.$confirm("是否删除本条记录？", "提示", {
        comfirmButtonText: "确定",
        cancelButtonText: "取消"
      })
        .then(() => {
          this.$axios
            .post("/api/home/removeTodoList", row)
            .then(({ data }) => {
              if (data.success) {
                this.$message.success("移除成功");
                this.getTodoList();
              } else {
                this.$message.warning("移除失败");
              }
            })
            .catch(err => {
              console.log(err);
              this.$message.error("移除失败");
            });
        })
        .catch(() => {});
    },
    recoverTodoList(row) {
      this.$axios
        .post("/api/home/recoverTodoList", row)
        .then(({ data }) => {
          if (data.success) {
            this.$message.success("还原成功");
            this.getTodoList();
          } else {
            this.$message.warning("还原失败");
          }
        })
        .catch(err => {
          console.log(err);
          this.$message.error("还原失败");
        });
    },
    deleteTodoList(row) {
      this.$confirm("是否彻底删除本条记录？", "提示", {
        comfirmButtonText: "确定",
        cancelButtonText: "取消"
      })
        .then(() => {
          this.$axios
            .post("/api/home/deleteTodoList", row)
            .then(({ data }) => {
              if (data.success) {
                this.$message.success("删除成功");
                this.getTodoList();
              } else {
                this.$message.warning("删除失败");
              }
            })
            .catch(err => {
              console.log(err);
              this.$message.error("删除失败");
            });
        })
        .catch(() => {});
    },
    changeSatus(data) {
      this.$axios
        .post("/api/home/updateTodoList", data)
        .then(({ data }) => {
          if (data.success) {
            this.getTodoList();
          } else {
            this.$message.warning("修改失败");
          }
        })
        .catch(err => {
          console.log(err);
          this.$message.error("修改失败");
        });
    },
    openTodoList() {
      this.todoListDialog = true;
    },
    getVisitorInfo() {
      this.visitorInfo.ip = sessionStorage.getItem("ip");
      this.visitorInfo.area = sessionStorage.getItem("area");
      this.visitorInfo.brower = sysTool.GetCurrentBrowser();
      this.visitorInfo.os = sysTool.GetOs();
      console.log(
        `ip: ${this.visitorInfo.ip}，地区：${this.visitorInfo.area}，
        浏览器：${this.visitorInfo.brower}，操作系统：${this.visitorInfo.os}`
      );
    }
  },
  mounted() {
    this.setNowTime();
    this.getVisitorInfo();
    this.getTodoList();
  }
};
</script>

<style scoped lang="less">
.home {
  height: calc(~"100% - 40px");
}
.stat {
  display: flex;
  height: 230px;
}
.stat-user {
  position: relative;
  width: 300px;
  background-color: @boxBgColor;
  box-shadow: 2px 2px 5px #ccc;
}
.stat-user__title {
  height: 100px;
  background-color: @mainColor;
  color: white;
  font-size: 18px;
  font-weight: bold;
  text-align: center;
  line-height: 70px;
}
.stat-user__detail {
  padding-top: 50px;
  color: #666;
  font-size: 13px;
  text-align: center;
}
.stat-user__portrait {
  position: absolute;
  top: 50%;
  left: 50%;
  .circle(80px);
  border: 3px solid white;
  box-shadow: 0 0 5px #ccc;
  margin-top: -55px;
  margin-left: -40px;
}
.stat-info {
  flex: 1;
  margin-left: 20px;
}
.el-row + .el-row {
  margin-top: 10px;
}
.stat-info__item {
  display: flex;
  height: 110px;
  box-shadow: 2px 2px 5px #ccc;
  background-color: @boxBgColor;
}
.stat-info__icon {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 80px;
  color: white;
  [class*="el-icon"] {
    font-size: 50px;
  }
}
.stat-info__detail {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.stat-info__total {
  color: #0085d0;
  font-size: 27px;
  font-weight: bold;
}
.stat-info__title {
  color: #666;
  font-size: 12px;
}
.list {
  display: flex;
  height: calc(~"100% - 250px");
  .el-col {
    height: 100%;
  }
}
.el-card {
  height: 100%;
  background-color: @boxBgColor;
  .el-icon-plus {
    float: right;
    color: @dangerColor;
    font-weight: bold;
    cursor: pointer;
  }
}
.el-card__header span {
  color: @subColor;
}
.el-card__body {
  p {
    border-bottom: 1px solid #e5e5e5;
    color: #555;
    font-size: 15px;
    line-height: 30px;
  }
  .active {
    color: #666;
    text-decoration: line-through;
  }
}
.latest-new-list__time {
  color: #666;
  font-size: 14px;
}
.todolist-box {
  height: 450px;
  overflow-y: auto;
}
</style>
<style lang="less">
.home .el-card__body {
  padding-top: 0px;
}
</style>
