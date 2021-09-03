<template>
  <!-- 分类管理 -->
  <el-dialog
    title="分类管理"
    :visible.sync="typeDialogVisible"
    width="450px"
    :before-close="closeDialog"
  >
    <div style="height: 400px">
      <div class="margin-b-10">
        <el-button
          icon="el-icon-plus"
          title="新增"
          size="mini"
          @click="openAddTypeDialog('新增')"
        ></el-button>
        <el-popover
          placement="left"
          width="400"
          trigger="click"
          class="pull-right"
        >
          <h4>分类管理：</h4>
          <div class="t-i-1">①提供分类的新增、修改、删除</div>
          <div class="t-i-1">
            ②可为分类添加多个关键词，词与词之间使用空格分隔
          </div>
          <div class="t-i-1">
            ③添加关键词后所上传的数据会根据关键词自动分类
          </div>
          <div class="t-i-1">
            <strong>注：</strong>
            只对添加关键词后所上传的数据有效，对之前已存在的数据无效
          </div>
          <el-button type="text" size="mini" slot="reference">说明书</el-button>
        </el-popover>
      </div>

      <el-table
        ref="tableRef"
        :data="typeOptions"
        style="width: 99%"
        border
        stripe
        highlight-current-row
        height="360px"
        size="mini"
      >
        <el-table-column type="index" label="序号" width="50">
        </el-table-column>
        <el-table-column property="typeOneName" label="名称" width="70">
        </el-table-column>
        <el-table-column
          property="typeKeyword"
          label="关键词"
          show-overflow-tooltip
          width="145"
        >
        </el-table-column>
        <el-table-column label="操作" width="140" align="center">
          <template slot-scope="scope">
            <el-button
              circle
              icon="el-icon-edit"
              type="primary"
              title="编辑"
              size="small"
              @click="openEditTypeDialog(scope.row)"
            ></el-button>
            <el-button
              circle
              icon="el-icon-delete"
              type="danger"
              title="删除"
              size="small"
              @click="typeDelete(scope.row)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog
        width="30%"
        :title="innerDialogTitle"
        :visible.sync="typeInnerVisible"
        append-to-body
        @close="typeInnerDialogClose"
      >
        <el-form
          ref="typeManageForm"
          :model="typeManageForm"
          label-width="70px"
          :rules="typeManageRules"
        >
          <el-form-item label="分类名" prop="typeOneName">
            <el-input
              placeholder="请输入分类名"
              v-model="typeManageForm.typeOneName"
            ></el-input>
          </el-form-item>
          <el-form-item label="关键词" prop="typeKeyword">
            <el-input
              type="textarea"
              :rows="2"
              placeholder="请输入本类关键词，词与词之间用空格分隔"
              v-model="typeManageForm.typeKeyword"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="typeInnerVisible = false"
            >取 消</el-button
          >
          <el-button type="primary" size="mini" @click="saveOrUpdateType"
            >确 定</el-button
          >
        </div>
      </el-dialog>
    </div>
  </el-dialog>
</template>

<script>
export default {
  props: ["typeDialogVisible", "typeOptions"],
  data() {
    return {
      innerDialogTitle: "",
      typeManageForm: {
        typeOneName: "",
        typeKeyword: ""
      },
      typeInnerVisible: false,
      typeManageRules: {
        typeOneName: [
          { required: true, message: "类型名不能为空", trigger: "blur, change" }
        ]
      }
    };
  },
  methods: {
    openEditTypeDialog(row) {
      this.innerDialogTitle = "编辑分类";
      this.$nextTick(() => {
        // 回显数据延迟赋值，为了让表单先清空
        this.typeManageForm = JSON.parse(JSON.stringify(row));
      });
      this.typeInnerVisible = true;
    },
    openAddTypeDialog() {
      this.innerDialogTitle = "新增分类";
      this.typeInnerVisible = true;
    },
    saveOrUpdateType() {
      this.$refs.typeManageForm.validate(isValid => {
        if (!isValid) return;
        if (this.innerDialogTitle.includes("新增")) {
          this.saveType();
        } else {
          this.updateType();
        }
      });
    },
    saveType() {
      this.$axios
        .post("/api/acbi/saveType", this.typeManageForm)
        .then(res => {
          const { data } = res;
          if (data.success) {
            this.$message.success("分类保存成功");
            this.$emit("getOneType");
            // this.getOneType();
          } else {
            this.$message.warning("分类保存失败");
          }
          this.typeInnerVisible = false;
        })
        .catch(err => {
          console.log(err);
          this.$message.error("分类保存失败，发生了一些错误哈哈");
          this.typeInnerVisible = false;
        });
    },
    updateType() {
      this.$axios
        .post("/api/acbi/updateType", this.typeManageForm)
        .then(res => {
          const { data } = res;
          if (data.success) {
            this.$message.success("分类更新成功");
            this.$emit("getOneType");
            // this.getOneType();
          } else {
            this.$message.warning("分类更新失败");
          }
          this.typeInnerVisible = false;
        })
        .catch(err => {
          console.log(err);
          this.$message.error("分类更新失败，发生了一些错误哈哈");
        });
    },
    typeInnerDialogClose() {
      this.$refs["typeManageForm"].resetFields();
    },
    typeDelete(row) {
      this.$confirm("确认删除本条分类", "提示", {
        comfirmButtonText: "确定",
        cancelButtonText: "取消"
      })
        .then(() => {
          this.$axios
            .get("/api/acbi/deleteType", {
              params: {
                typeId: row.typeId
              }
            })
            .then(({ data }) => {
              if (data.success) {
                this.$message.success("删除成功！");
                // this.getOneType();
                this.$emit("getOneType");
              } else {
                this.$message.warning("删除失败");
              }
            })
            .catch(err => {
              console.log(err);
              thie.$message.error("删除失败!!发生错误");
            });
        })
        .catch(() => {});
    },
    closeDialog() {
      this.$emit("closeTypeManageDialog")
    }
  }
};
</script>

<style></style>
