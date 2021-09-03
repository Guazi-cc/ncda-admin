<template>
  <!-- 上传Dialog -->
  <el-dialog
    title="账单上传"
    :visible.sync="uploadDialogVisible"
    width="35%"
    :before-close="closeUploadDialog"
    @open="uploadDialogOpen"
  >
    <div style="height: 400px">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="文本输入" name="first">
          <div class="upload-box">
            <el-input
              type="textarea"
              :autosize="{ minRows: 14, maxRows: 14 }"
              v-model="uploadForm.accBillText"
              @focus="accBillTextSelect"
              class="resizeNone"
            ></el-input>
          </div>
        </el-tab-pane>
        <el-tab-pane label="文件上传" name="second">
          <div class="upload-box">
            <el-upload
              class="upload-demo"
              drag
              ref="upload"
              :limit="1"
              :auto-upload="false"
              accept=".txt"
              action="/api/acbi/fileUpload"
              :file-list="uploadForm.fileList"
              :on-remove="handleUploadRemove"
              :on-change="handleUploadChange"
              :on-exceed="handleUploadExceed"
              :before-remove="beforeRemove"
            >
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">
                将文件拖到此处，或<em>点击上传</em>
              </div>
              <div class="el-upload__tip" slot="tip">
                只能上传txt文件，且不超过500kb
              </div>
            </el-upload>
          </div>
        </el-tab-pane>
        <el-tab-pane label="上传记录" name="third">
          <div class="upload-box" style="display: block; overflow-y:auto">
            <el-collapse
              v-model="uploadRecordActive"
              accordion
              style="width: 95%; margin:0 auto;"
            >
              <el-collapse-item
                v-for="(item, index) in currentFileUploadTimeLine"
                :key="index"
                :name="index"
              >
                <template slot="title">
                  <i class="header-icon el-icon-document"></i>
                  账单时间：{{ item.date | dateFormat | retainYearAndMonth }}
                  &nbsp;
                  <i class="header-icon el-icon-date"></i>
                  上传日期：{{ item.uploadTime | dateFormat }}
                </template>
                <template>
                  <div style="margin: 0 5px;">
                    <span>上传记录：</span>
                    <TimeLine
                      ref="uploadRecordTL"
                      :date="item.date"
                      style="padding: 5px;"
                      @loadTimeLine="getCurrentFileUploadTimeLine"
                      @loadData="loadData"
                    ></TimeLine>
                  </div>
                </template>
              </el-collapse-item>
            </el-collapse>
          </div>
        </el-tab-pane>
      </el-tabs>
      <span
        slot="footer"
        class="dialog-footer"
        v-if="activeName === 'second' || activeName === 'first'"
      >
        <el-popover placement="left" width="400" trigger="click">
          <h4>填写说明：</h4>
          <div class="t-i-1">本系统操作逻辑仅符合个人习惯，</div>
          <div class="t-i-1">
            若想使用账本功能，请按正确的格式填写或上传文件。
          </div>
          <div class="t-i-1">①第一行为标题，切记一定要包含年份</div>
          <div class="t-i-1">
            ②正文部分,开头第一项是日期(格式: 月.日) 后面为账单具体内容，每一项用
            <strong>/</strong>
            分隔。账单项目默认为支出，若为收入则数字前应有<strong>+</strong>
          </div>
          <div class="t-i-1">③每日账单之间用换行作为分隔</div>
          <div class="t-i-1">
            ④建议每次上传一个月的数据，若同一个月份上传多次，则只保留最后一次的上传
          </div>
          <h4>填写示例：</h4>
          <el-input
            type="textarea"
            :rows="5"
            v-model="example"
            class="resizeNone"
            readonly
          ></el-input>
          <el-button type="text" size="mini" slot="reference">说明书</el-button>
        </el-popover>
        <el-button
          type="primary"
          @click="submitUpload"
          size="mini"
          class="pull-right margin-l-10"
          >确 定</el-button
        >
        <el-button @click="closeUploadDialog" size="mini" class="pull-right"
          >取 消</el-button
        >
      </span>
    </div>
  </el-dialog>
</template>

<script>
import TimeLine from "@/components/page/accountBill/dialog/UploadRecordTimeLine";

export default {
  components: {
    TimeLine
  },
  props: { uploadDialogVisible: Boolean },
  data() {
    return {
      activeName: "first", // 上传弹窗的 tab
      uploadRecordActive: "",
      uploadForm: {
        accBillText: "👉这里是可以输入内容的✨",
        fileList: []
      },
      currentFileUploadTimeLine: [],
      example:
        "2021.5\n5.1/消费丙15/消费乙88/收入甲+40\n5.2/工资+1888\n5.3/捐款500/晚饭1.5\n5.4/捡钱+0.1"
    };
  },
  mounted() {},
  methods: {
    getCurrentFileUploadTimeLine() {
      this.$axios
        .get("/api/acbi/getCurrentFileUploadTimeLine")
        .then(({ data }) => {
          if (data.success) {
            this.currentFileUploadTimeLine = data.data;
          } else {
            this.$message.warning("获取当前上传文件时间线失败");
          }
        })
        .catch(err => {
          console.log(err);
          this.$message.error("获取当前上传文件时间线失败");
        });
    },
    handleClick() {
      if (this.activeName === "third") {
        this.getCurrentFileUploadTimeLine();
      }
    },
    closeUploadDialog() {
      this.$emit("closeUploadDialog");
    },
    accBillTextSelect() {
      if (this.uploadForm.accBillText.substring(0, 1) === "\ud83d") {
        this.uploadForm.accBillText = "";
      }
    },
    uploadDialogOpen() {
      // 弹窗内容初始化
      this.uploadForm.accBillText = "👉这里是可以输入内容的✨";
      this.uploadForm.fileList = [];
      this.activeName = "first";
      this.uploadRecordActive = "";
    },
    /***************文件上传相关方法*******************/
    handleUploadChange(file, fileList) {
      // 文件状态改变时的钩子
      this.uploadForm.fileList = fileList;
    },
    handleUploadRemove(file, fileList) {
      // 文件列表移除文件时的钩子
      this.uploadForm.fileList = fileList;
    },
    handleUploadExceed(files, fileList) {
      // 文件超出个数限制时的钩子
      this.$message.warning(`只允许选择一个文件`);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    /************************************************/
    clickTest() {
      this.$message("test");
    },
    submitUpload() {
      if (this.activeName === "first") {
        if (
          this.uploadForm.accBillText.trim() === "" ||
          this.uploadForm.accBillText === "👉这里是可以输入内容的✨"
        ) {
          this.$message.warning("请填写内容！");
          return;
        }
        this.textUpload();
      } else {
        // this.$refs.upload.submit();    // 不用他原生的的上传方法
        if (this.uploadForm.fileList.length === 0) {
          this.$message.warning("没文件你上传个J8，往里整文件啊！");
          return;
        }
        const fileSize = this.uploadForm.fileList[0].size / 1024 / 1024;
        if (fileSize < 5) {
          const formData = new FormData();
          formData.append("file", this.uploadForm.fileList[0].raw);
          this.fileUpload(formData);
        } else {
          this.$message.warning("文件大小限制为5M，你的很大，我忍不了");
        }
      }
    },
    fileUpload(formData) {
      this.$axios
        .post("api/acbi/fileUpload", formData)
        .then(res => {
          const { data } = res;
          if (data.success) {
            this.handle(data.data);
            this.$message({
              message: "文件解析成功",
              type: "success",
              customClass: "my-msg"
            });
          } else {
            this.$message({
              message: `文本解析失败，${data.message}`,
              type: "error",
              customClass: "my-msg"
            });
          }
        })
        .catch(err => {
          console.log(err);
          this.$message({
            message: "发生了一些错误！！！",
            type: "error",
            customClass: "my-msg"
          });
        });
    },
    textUpload() {
      this.$axios
        .post("/api/acbi/textUpload", {
          text: this.uploadForm.accBillText
        })
        .then(res => {
          const { data } = res;
          if (data.success) {
            this.handle(data.data);
            this.$message({
              message: "文本解析成功",
              type: "success",
              customClass: "my-msg"
            });
          } else {
            this.$message({
              message: "文本解析失败，" + data.message,
              type: "error",
              customClass: "my-msg"
            });
          }
        })
        .catch(err => {
          console.log(err);
          this.$message({
            message: "发生了一些错误！！！",
            type: "error",
            customClass: "my-msg"
          });
        });
    },
    handle(data) {
      this.closeUploadDialog();
      this.$emit("setPreviewTableData", data);
      this.$emit("openPreviewDialog");
    },
    loadData() {
      this.$emit("loadData");
    }
  },
  filters: {
    dateFormat(dateStr) {
      return dateStr.replace(/-/g, ".");
    },
    retainYearAndMonth(dateStr) {
      return dateStr.substring(0, 7);
    }
  }
};
</script>

<style scoped>
.upload-box {
  height: 308px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #fff;
}
.t-i-1 {
  text-indent: 1em;
}
</style>
<style lang="less">
.resizeNone {
  .el-textarea__inner {
    //el_input中的隐藏属性
    resize: none; //主要是这个样式
  }
}
</style>
