<template>
  <el-timeline>
    <el-timeline-item
      v-for="(activity, index) in activities"
      :key="index"
      :icon="activity.icon"
      :color="activity.color"
      :type="activity.type"
      :timestamp="activity.uploadTime"
      placement="top"
    >
      <el-card shadow="hover">
        <span>{{ activity.fileContent | contentFormat }}</span>
        <el-popover placement="right" width="300" trigger="hover">
          <el-input
            type="textarea"
            :rows="10"
            v-model="activity.fileContent"
            class="resizeNone"
            readonly
          >
          </el-input>
          <el-button type="text" slot="reference"
            ><i class="el-icon-more"></i
          ></el-button>
        </el-popover>
        <el-button
          type="text"
          style="float: right;"
          @click="deleteData(activity, index)"
          ><i class="el-icon-delete"></i
        ></el-button>
      </el-card>
    </el-timeline-item>
  </el-timeline>
</template>

<script>
export default {
  props: ["date"],
  data() {
    return {
      activities: [],
      visible: false
    };
  },
  mounted() {
    this.getHistoryFileUploadTimeLine();
  },
  methods: {
    getHistoryFileUploadTimeLine() {
      this.$axios
        .post("/api/acbi/getHistoryFileUploadTimeLine", {
          date: this.date
        })
        .then(({ data }) => {
          this.activities = data.data;
          for (let item of this.activities) {
            item.color = "#0bbd87";
            item.icon = "el-icon-more";
            if (item.delState == 0) {
              item.type = "primary";
              item.color = "#FF8C00";
              item.icon = "el-icon-check";
            }
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    deleteData(activity, index) {
      let msg = "";
      if (index == 0) {
        msg =
          "本条主数据为主要数据，删除后将同时删除历史关联数据以及表中当前年月的数据！！";
      } else {
        msg = `确认删除你在${activity.uploadTime}上传的这条历史数据吗？`;
      }
      this.$confirm(msg, "提示", {
        comfirmButtonText: "确定",
        cancelButtonText: "取消"
      })
        .then(() => {
          if (index == 0) {
            this.deletePrimaryData(activity);
          } else {
            this.deleteHistoryData(activity);
          }
        })
        .catch(() => {
          this.$message("罢了 罢了");
        });
    },
    deletePrimaryData(activity) {
      this.$axios
        .post("/api/acbi/deletePrimaryData", activity)
        .then(({ data }) => {
          if (data.success) {
            this.$message.success("主要数据及其关联数据删除成功");
          } else {
            this.$message.warning("主要数据及其关联数据删除失败");
          }
          this.$emit("loadTimeLine");
          this.$emit("getTableData");
        })
        .catch(err => {
          console.log(err);
          this.$message.error("删除失败!!发生错误");
        });
    },
    deleteHistoryData(activity) {
      this.$axios
        .get("/api/acbi/deleteHistoryData", {
          params: {
            id: activity.id
          }
        })
        .then(({ data }) => {
          if (data.success) {
            this.$message.success("历史数据删除成功！");
          } else {
            this.$message.warning("历史数据删除失败！");
          }
          this.getHistoryFileUploadTimeLine();
        })
        .catch(err => {
          console.log(err);
          this.$message.error("历史数据删除失败！还发生了些发生错误");
        });
    }
  },
  filters: {
    contentFormat(val) {
      return val.substring(0, 30);
    }
  }
};
</script>

<style scoped>
@import "../../../../assets/css/mycss/timeline-item.css";
@import "../../../../assets/css/mycss/timeline.css";
</style>