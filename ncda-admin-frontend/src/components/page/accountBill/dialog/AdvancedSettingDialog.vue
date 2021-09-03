<template>
  <!-- 高级设置 -->
  <el-dialog
    title="高级设置"
    :visible.sync="advancedSettingShow"
    width="550px"
		:before-close="closeDialog"
    @open="getAdvancedSetting"
  >
    <el-form
      ref="advancedSettingForm"
      :model="advancedSettingForm"
      label-width="100px"
      :rules="advancedSettingRules"
    >
      <el-form-item label="热力图最大值" prop="heatmapMax">
        <el-input-number
          v-model="advancedSettingForm.heatmapMax"
          controls-position="right"
          :min="10"
        ></el-input-number>
      </el-form-item>
      <el-form-item label="金额最大值" prop="moneyMax">
        <el-input-number
          v-model="advancedSettingForm.moneyMax"
          controls-position="right"
          :precision="2"
          :step="0.1"
          :min="0.0"
        ></el-input-number>
      </el-form-item>
      <el-form-item label="金额最小值" prop="moneyMin">
        <el-input-number
          v-model="advancedSettingForm.moneyMin"
          controls-position="right"
          :precision="2"
          :step="0.1"
          :min="0.0"
        ></el-input-number>
      </el-form-item>
      <el-form-item label="过滤关键词" prop="filterKeyword">
        <el-input
          type="textarea"
          :rows="2"
          placeholder="请输入内容，关键词之间用空格分隔"
          v-model="advancedSettingForm.filterKeyword"
        >
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          @click="saveAdvancedSetting"
          class="pull-right margin-l-25"
          >确定</el-button
        >
        <el-button @click="closeDialog" class="pull-right">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
export default {
  props: ["advancedSettingShow", "advancedSettingForm"],
  data() {
    return {
      advancedSettingRules: {}
    };
  },
  methods: {
    getAdvancedSetting() {
      this.$emit("getAdvancedSetting");
    },
    saveAdvancedSetting() {
      this.$axios
        .post("/api/acbi/saveAdvancedSetting", this.advancedSettingForm)
        .then(res => {
          const { data } = res;
          if (data.success) {
            this.$message.success("设置成功");
            this.closeDialog();
            this.$emit("loadData");
          } else {
            this.$message.warning("设置失败");
          }
        })
        .catch(err => {
          console.log(err);
          this.$message.error("高级设置保存失败");
        });
    },
    closeDialog() {
      this.$emit("closeAdvancedSettingDialog");
    }
  }
};
</script>

<style></style>
