<template>
  <!-- 比较Dialog  -->
  <el-dialog title="数据比较" width="60%" :visible.sync="compareDialogVisible">
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
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button size="mini" @click="compareDataNoSave">取 消</el-button>
      <el-button type="primary" size="mini" @click="compareDataSave"
        >保 存</el-button
      >
    </span>
  </el-dialog>
</template>

<script>
import { CodeDiff } from "v-code-diff";

export default {
	props: ["compareDialogVisible", "compareForm"],
	components: {
		CodeDiff
	},
	methods: {
    compareDataNoSave() {
      this.$confirm("将保留原始数据并删除新数据，你真的要这样做吗？", "提示", {
        comfirmButtonText: "确定",
        cancelButtonText: "取消"
      })
        .then(() => {
					this.$emit("closeCompareDialog")
        })
        .catch(() => {
          this.$message("达咩~");
        });
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
                this.$message({
                  message: "数据更新成功",
                  type: "success",
                  customClass: "my-msg"
                });
								this.$emit("closeCompareDialog")
								this.$emit('loadData')
								this.$emit("reloadTimeLine")
              } else {
                this.$message({
                  message: "数据更新失败",
                  type: "warning",
                  customClass: "my-msg"
                });
              }
            })
            .catch(err => {
              console.log(err);
              this.$message({
                message: "数据更新失败",
                type: "error",
                customClass: "my-msg"
              });
            });
        })
        .catch(() => {});
    },
	}
};
</script>

<style></style>
