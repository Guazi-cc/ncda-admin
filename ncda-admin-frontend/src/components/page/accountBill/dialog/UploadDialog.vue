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
      <el-tabs v-model="activeName">
        <el-tab-pane label="文本输入" name="first">
          <div class="upload-box">
            <el-input
              type="textarea"
              :autosize="{ minRows: 14, maxRows: 14 }"
              v-model="uploadForm.accBillText"
              @focus="accBillTextSelect"
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
              <el-collapse-item name="1">
                <template slot="title">
                  <i class="header-icon el-icon-document"></i
                  >&nbsp;&nbsp;账单时间：2021.8&nbsp;&nbsp;&nbsp;&nbsp;
                  <i class="header-icon el-icon-date"></i
                  >&nbsp;&nbsp;上传日期：2021.8.31 15:00&nbsp;&nbsp;&nbsp;&nbsp;
                  <el-button type="text" @click.stop="clickTest"
                    >查看</el-button
                  >
                </template>
                <template>
                  <div style="margin: 0px 5px 5px 5px;">
                    历史上传记录：
                    <TimeLine style="padding: 5px;"></TimeLine>
                  </div>
                </template>
              </el-collapse-item>
              <el-collapse-item title="反馈 Feedback" name="2">
                <div>
                  控制反馈：通过界面样式和交互动效让用户可以清晰的感知自己的操作；
                </div>
                <div>
                  页面反馈：操作后，通过页面元素的变化清晰地展现当前状态。
                </div>
              </el-collapse-item>
              <el-collapse-item title="效率 Efficiency" name="3">
                <div>简化流程：设计简洁直观的操作流程；</div>
                <div>
                  清晰明确：语言表达清晰且表意明确，让用户快速理解进而作出决策；
                </div>
                <div>
                  帮助用户识别：界面简单直白，让用户快速识别而非回忆，减少用户记忆负担。
                </div>
              </el-collapse-item>
              <el-collapse-item title="可控 Controllability" name="4">
                <div>
                  用户决策：根据场景可给予用户操作建议或安全提示，但不能代替用户进行决策；
                </div>
                <div>
                  结果可控：用户可以自由的进行操作，包括撤销、回退和终止当前操作等。
                </div>
              </el-collapse-item>
            </el-collapse>
          </div>
        </el-tab-pane>
        <el-tab-pane label="test" name="ff">
          <div class="upload-box">
            <el-timeline>
              <el-timeline-item
                v-for="(activity, index) in activities"
                :key="index"
                :icon="activity.icon"
                :type="activity.type"
                :color="activity.color"
                :size="activity.size"
                :timestamp="activity.timestamp"
              >
                {{ activity.content }}
              </el-timeline-item>
            </el-timeline>
          </div>
        </el-tab-pane>
      </el-tabs>
      <span
        slot="footer"
        class="dialog-footer"
        v-if="activeName === 'second' || activeName === 'first'"
      >
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
      uploadForm: {
        accBillText: "👉这里是可以输入内容的✨",
        fileList: []
      }
    };
  },
  mounted() {},
  methods: {
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
</style>
