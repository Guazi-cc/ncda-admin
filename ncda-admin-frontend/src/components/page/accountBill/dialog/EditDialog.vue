<template>
  <!--编辑-弹出层-->
  <el-dialog
    title="编辑"
    :visible.sync="isShowEditDialog"
    width="430px"
		:before-close="closeEditDialog"
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
        <el-select
          v-model="formFileds.type"
          placeholder="请选择"
          style="width: 100% !important;"
        >
          <el-option
            v-for="item in typeOptions"
            :key="item.typeId"
            :label="item.typeOneName"
            :value="item.typeId"
          >
          </el-option>
        </el-select>
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
        <el-button @click="closeEditDialog" class="pull-right">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
export default {
  props: {
    isShowEditDialog: Boolean,
    formFileds: Object,
    typeOptions: Array
  },
  data() {
    return {
      // 编辑弹窗校验
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
      visible: this.isShowEditDialog
    };
  },
  mounted() {},
  methods: {
    dialogClose() {
      // 清空编辑表单
      this.$refs.editForm.resetFields();
    },
    handleEdit() {
      this.$refs.editForm.validate(isValid => {
        if (!isValid) return;
        console.log(this.formFileds);
        this.$axios
          .post("/api/acbi/updateAcBiData", this.formFileds)
          .then(res => {
            const { data } = res;
            if (data.success) {
              this.$message.success("数据更新成功");
              this.$emit("getTableData");
              // this.getTableData();
              // this.isShowEditDialog = false;
              this.$emit("closeEditDialog");
            } else {
              this.$message.warning("数据更新失败");
            }
          })
          .catch(err => {
            console.log(err);
            this.$message.error("数据更新失败，发生了一些错误哈哈");
          });
      });
    },
    closeEditDialog() {
      this.$emit("closeEditDialog");
    }
  }
};
</script>

<style></style>
