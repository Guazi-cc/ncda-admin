<template>
  <div>
    <div class="search-box">
      <el-row>
        <el-col :span="1"><span class="search-label">月份：</span></el-col>
        <el-col :span="4">
          <el-date-picker
            v-model="searchForm.month"
            type="month"
            placeholder="选择月"
            size="mini"
            value-format="yyyy-MM-dd"
            format="yyyy年MM月"
            style="width: 160px !important;"
          >
          </el-date-picker
        ></el-col>
        <el-col :span="1"><span class="search-label">类型：</span></el-col>
        <el-col :span="4">
          <el-select
            v-model="searchForm.type"
            placeholder="请选择"
            size="mini"
            style="width: 160px !important;"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option> </el-select
        ></el-col>
        <el-col :span="1">
          <el-button type="primary" size="mini" @click="searchClick"
            >搜索</el-button
          ></el-col
        >
        <el-col :span="1" :offset="10">
          <el-button @click="openUploadDialog" type="primary" size="mini"
            >上传Bill</el-button
          ></el-col
        >
      </el-row>
    </div>
    <!--表格内容-->
    <el-table
      ref="list"
      v-loading="loading"
      :data="tableData"
      style="width: 100%"
      border
      stripe
      highlight-current-row
      :height="tableHeight"
      :default-sort="{ prop: 'date', order: 'descending' }"
      @row-click="handleRowClick"
      @select-all="handleCheckedAllAndCheckedNone"
      @select="handleCheckedAllAndCheckedNone"
    >
      <el-table-column type="selection" width="45" align="center">
      </el-table-column>
      <!-- <el-table-column type="index" label="序号" width="50"> </el-table-column> -->
      <el-table-column property="date" label="日期" width="180" sortable>
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 5px">{{ scope.row.date }}</span>
        </template>
      </el-table-column>
      <el-table-column property="itemName" label="名称" width="180">
      </el-table-column>
      <el-table-column property="money" label="金额">
        <template slot-scope="scope">
          <span v-if="scope.row.moneyState == 1" class="money-state-in"
            >+{{ scope.row.money }}</span
          >
          <span v-else class="money-state-out">-{{ scope.row.money }}</span>
        </template>
      </el-table-column>
      <el-table-column property="type" label="类型"> </el-table-column>
      <el-table-column property="comment" label="备注"> </el-table-column>
      <el-table-column label="操作" width="130" align="center">
        <template slot-scope="scope">
          <el-button
            circle
            icon="el-icon-edit-outline"
            type="primary"
            title="编辑"
            size="small"
            @click="rowEdit(scope.$index, scope.row)"
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <el-pagination
      :page-sizes="[31, 62, 93]"
      :total="pagination.total"
      :current-page.sync="pagination.currentPage"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
      layout="total, sizes, prev, pager, next, jumper"
    >
    </el-pagination>
    <!--编辑-弹出层-->
    <el-dialog
      title="编辑"
      :visible.sync="isShowEditDialog"
      width="430px"
      @close="dialogClose"
    >
      <el-form
        ref="editForm"
        :model="formFileds"
        label-width="55px"
        :rules="rules"
      >
        <el-form-item label="日期">
          <el-date-picker
            v-model="formFileds.date"
            disabled
            value-format="yyyy-MM-dd"
            :editable="false"
            :clearable="false"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="名称" prop="itemName">
          <el-input v-model="formFileds.itemName" disabled></el-input>
        </el-form-item>
        <el-form-item label="金额" prop="money">
          <el-input v-model="formFileds.money" disabled></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="money">
          <el-radio-group v-model="formFileds.moneyState" disabled>
            <el-radio-button :label="0">支出</el-radio-button>
            <el-radio-button :label="1">收入</el-radio-button>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="类型" prop="type">
          <el-input v-model="formFileds.type"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="comment">
          <el-input v-model="formFileds.comment"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="handleEdit()"
            class="pull-right margin-l-25"
            >确定</el-button
          >
          <el-button @click="isShowEditDialog = false" class="pull-right"
            >取消</el-button
          >
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 上传Dialog -->
    <el-dialog
      title="账单上传"
      :visible.sync="uploadDialogVisible"
      width="35%"
      :before-close="uploadDialogClosd"
      @open="uploadDialogOpen"
    >
      <div style="height: 400px">
        <el-tabs v-model="activeName" @tab-click="tabHandleClick">
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
        </el-tabs>
        <span slot="footer" class="dialog-footer">
          <el-button
            type="primary"
            @click="submitUpload"
            size="mini"
            class="pull-right margin-l-10"
            >确 定</el-button
          >
          <el-button
            @click="uploadDialogVisible = false"
            size="mini"
            class="pull-right"
            >取 消</el-button
          >
        </span>
      </div>
    </el-dialog>

    <!-- 上传成功预览Dialog  -->
    <el-dialog
      title="账单预览"
      width="35%"
      :visible.sync="previewDialogVisible"
    >
      <div style="height: 500px;">
        <el-table height="450px" :data="previewTableData" size="mini">
          <el-table-column property="date" label="日期" width="180" sortable>
          </el-table-column>
          <el-table-column property="itemName" label="名称" width="180">
          </el-table-column>
          <el-table-column property="money" label="金额">
            <template slot-scope="scope">
              <span v-if="scope.row.moneyState == 1" class="money-state-in"
                >+{{ scope.row.money }}</span
              >
              <span v-else class="money-state-out">-{{ scope.row.money }}</span>
            </template>
          </el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="savePreviewData" size="mini"
            >保 存</el-button
          >
        </span>
      </div>
    </el-dialog>

    <!-- 比较Dialog  -->
    <el-dialog
      title="数据比较"
      width="60%"
      :visible.sync="compareDialogVisible"
    >
      <div class="compare-title">旧数据：</div>
      <div class="compare-title">新数据：</div>
      <div class="compare-box">
        <!-- https://github.com/Shimada666/v-code-diff -->
        <code-diff
          :old-string="compareForm.oldStr"
          :new-string="compareForm.newStr"
          :isShowNoChange="true"
          :context="100"
          diffStyle="word"
          output-format="side-by-side"
        />
        <span slot="footer" class="dialog-footer btn-bottom">
          <el-button size="mini" @click="compareDataNoSave">取 消</el-button>
          <el-button type="primary" size="mini" @click="compareDataSave"
            >保 存</el-button
          >
        </span>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { CodeDiff } from "v-code-diff";
export default {
  name: "Table",
  components: {
    CodeDiff
  },
  data() {
    return {
      screenHeight: 0, // 屏幕高度
      loading: false,
      formFileds: {
        id: null,
        date: "",
        itemName: "",
        money: 0,
        moneyState: "",
        comment: "",
        type: null
      },
      rules: {
        date: [
          { required: true, message: "日期不能为空", trigger: "blur, change" }
        ],
        itemName: [
          { required: true, message: "名称不能为空", trigger: "blur, change" }
        ],
        money: [
          { required: true, message: "金额不能为空", trigger: "blur, change" }
        ]
      },
      tableData: [],
      previewTableData: [],
      isShowEditDialog: false,
      options: [
        {
          value: "选项1",
          label: "黄金糕"
        },
        {
          value: "选项2",
          label: "双皮奶"
        },
        {
          value: "选项3",
          label: "蚵仔煎"
        },
        {
          value: "选项4",
          label: "龙须面"
        },
        {
          value: "选项5",
          label: "北京烤鸭"
        }
      ],
      pagination: {
        pageSize: 31,
        currentPage: 1,
        total: 0
      },
      searchForm: {
        month: "",
        type: ""
      },
      uploadDialogVisible: false,
      previewDialogVisible: false,
      compareDialogVisible: false,
      activeName: "first",
      uploadForm: {
        accBillText: "👉这里是可以输入内容的✨",
        fileList: []
      },
      compareForm: {
        oldStr: "",
        newStr: "",
        newData: null
      }
    };
  },
  mounted() {
    this.getScreenHeight();
    this.getTableData();
  },
  methods: {
    getTableData() {
      this.loading = true;
      this.$axios
        .post("/api/acbi/getAccountBill", {
          date: this.searchForm.month,
          pageSize: this.pagination.pageSize,
          currentPage: this.pagination.currentPage
        })
        .then(res => {
          const { data } = res;
          this.tableData = data.data.list;
          this.pagination.total = data.total;
          this.loading = false;
        })
        .catch(err => {
          console.log(err);
          this.loading = false;
        });
    },
    handleRowClick(row, event, column) {
      // 仅选中当前行
      this.setCurRowChecked(row);
    },
    handleCheckedAllAndCheckedNone(selection) {
      // 当前选中仅一行时操作-（当前表格行高亮）
      selection.length !== 1 && this.$refs.list.setCurrentRow();
    },
    dialogClose() {
      // 清空编辑表单
      this.$refs.editForm.resetFields();
    },
    rowEdit(index, row) {
      this.setCurRowChecked(row);

      // 给编辑弹出层赋值
      // ***这里需要注意的是：因为加了排序 所以tableData的顺序和实际显示的行顺序不一样
      for (const key in this.formFileds) {
        this.formFileds[key] = row[key];
      }
      this.isShowEditDialog = true;
    },
    handleEdit() {
      this.$refs.editForm.validate(isValid => {
        if (!isValid) return;
        console.log(this.formFileds)
      });
    },
    setCurRowChecked(row) {
      this.$refs.list.clearSelection();
      this.$refs.list.toggleRowSelection(row);
    },
    getScreenHeight() {
      this.screenHeight = document.documentElement.clientHeight;
      window.onresize = () => {
        return (() => {
          this.screenHeight = document.documentElement.clientHeight;
        })();
      };
    },
    openUploadDialog() {
      this.uploadDialogVisible = true;
    },
    uploadDialogClosd() {
      this.uploadDialogVisible = false;
    },
    tabHandleClick() {},
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
    searchClick() {
      this.getTableData();
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
            this.uploadDialogVisible = false;
            this.previewTableData = data.data;
            this.previewDialogVisible = true;
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
            this.uploadDialogVisible = false;
            this.previewTableData = data.data;
            this.previewDialogVisible = true;
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
    savePreviewData() {
      this.$axios
        .post("/api/acbi/saveUploadData", this.previewTableData)
        .then(res => {
          const { data } = res;
          if (data.success) {
            this.previewDialogVisible = false;
            this.getTableData();
            this.$message({
              message: "数据保存成功！",
              type: "success",
              customClass: "my-msg"
            });
          } else {
            if (
              data.data.oldContent !== null &&
              data.data.oldContent !== "" &&
              data.data.newContent !== null &&
              data.data.newContent !== ""
            ) {
              this.$message({
                message: `数据保存失败，${data.message}`,
                type: "warning",
                customClass: "my-msg"
              });
              this.previewDialogVisible = false;
              this.compareForm.oldStr = data.data.oldContent;
              this.compareForm.newStr = data.data.newContent;
              this.compareForm.newData = data.data.newData;
              this.compareDialogVisible = true;
            } else {
              this.$message({
                message: `数据保存失败，${data.message}`,
                type: "error",
                customClass: "my-msg"
              });
            }
          }
        })
        .catch(err => {
          console.log(err);
          this.$message({
            message: "数据保存失败，程序出现了异常",
            type: "error",
            customClass: "my-msg"
          });
        });
    },
    compareDataNoSave() {
      this.$confirm("将保留原始数据并删除新数据，你真的要这样做吗？", "提示", {
        comfirmButtonText: "确定",
        cancelButtonText: "取消"
      })
        .then(() => {
          this.compareDialogVisible = false;
        })
        .catch(() => {});
    },
    compareDataSave() {
      this.$confirm("新数据将覆盖原始数据", "提示", {
        comfirmButtonText: "确定",
        cancelButtonText: "取消"
      })
        .then(() => {
          this.$axios
            .post("/api/acbi/saveNewData", this.compareForm.newData)
            .then(res => {
              const { data } = res;
              if (data.success) {
                this.compareDialogVisible = false;
                this.$message({
                  message: "数据更新成功",
                  type: "success",
                  customClass: "my-msg"
                });
                this.getTableData();
              }
            })
            .catch(err => {
              console.log(err);
            });
        })
        .catch(() => {});
    },
    handleCurrentChange(val) {
      debugger;
      this.pagination.currentPage = val;
      this.getTableData();
    },
    handleSizeChange(val) {
      debugger;
      this.pagination.pageSize = val;
      this.getTableData();
    }
  },
  computed: {
    tableHeight() {
      return this.screenHeight - 200 + "px";
    }
  }
};
</script>

<style scoped lang="less">
.el-form {
  padding: 0 10px;
}
.el-date-editor {
  width: 100% !important;
}
.search-box {
  margin-bottom: 10px;
}
.upload-box {
  height: 308px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #fff;
}
.search-label {
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
  font-size: 14px;
  color: #606266;
}
.money-state-out {
  font-size: 12px;
  color: #67c23a;
}
.money-state-in {
  font-size: 12px;
  color: #f56c6c;
}
.my-msg {
  z-index: 9999 !important;
}
.compare-box {
  position: relative;
  height: 500px;
  overflow: auto;
}
.compare-title {
  display: inline-block;
  width: 50%;
  font-size: 14px;
  color: #606266;
}
.btn-bottom {
  position: absolute;
  bottom: 2%;
  right: 2%;
}
</style>
