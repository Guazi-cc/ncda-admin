<template>
  <!-- 上传成功预览Dialog  -->
  <el-dialog title="账单预览" width="35%" :visible.sync="previewDialogVisible">
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
</template>

<script>
import acBiUtil from "@/assets/js/acBiUtil";

export default {
  props: ["previewDialogVisible", "typeOptions", "previewTableData"],
  data() {
    return {};
  },
  methods: {
    savePreviewData() {
      /* 根据关键词给数据分类 */
      const data = acBiUtil.analysisType(
        this.previewTableData,
        this.typeOptions
      );
      this.$axios
        .post("/api/acbi/saveUploadData", data)
        .then(res => {
          const { data } = res;
          if (data.success) {
            this.$message({
              message: "数据保存成功！",
              type: "success",
              customClass: "my-msg"
            });
            this.$emit("closePreviewDialog");
            this.$emit("loadData");
            this.$emit("loadTimeLine");
          } else {
            if (
              this.$util.isEmpty(data.data.oldContent) &&
              this.$util.isEmpty(data.data.newContent)
            ) {
              this.$message({
                message: `数据保存失败，${data.message}`,
                type: "error",
                customClass: "my-msg"
              });
              return;
            }
            this.$message({
              message: `数据保存失败，${data.message}`,
              type: "warning",
              customClass: "my-msg"
            });
            this.$emit("closePreviewDialog");
            // 打开数据比较弹窗
            this.$emit("openCompare", data.data);
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
    }
  }
};
</script>

<style></style>
