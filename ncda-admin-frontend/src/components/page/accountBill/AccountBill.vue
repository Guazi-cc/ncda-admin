<template>
  <div>
    <div class="search-box">
      <el-row type="flex">
        <el-col :xs="8" :sm="4" :md="5" :lg="4" :xl="4">
          <span class="search-label">名称：</span>
          <el-input
            v-model="searchForm.itemName"
            placeholder="请输入内容"
            style="width: 70% !important;"
            size="mini"
            clearable
            @keyup.enter.native="searchClick"
          ></el-input>
        </el-col>
        <el-col :span="4">
          <span class="search-label">月份：</span>
          <el-date-picker
            v-model="searchForm.yearMonth"
            type="month"
            placeholder="选择月"
            size="mini"
            value-format="yyyy-MM-dd"
            format="yyyy年MM月"
            style="width: 70% !important;"
            clearable
          >
          </el-date-picker
        ></el-col>
        <el-col :span="4">
          <span class="search-label">类型：</span>
          <el-select
            v-model="searchForm.type"
            placeholder="请选择"
            size="mini"
            style="width: 70% !important;"
            clearable
          >
            <el-option
              v-for="item in typeOptions"
              :key="item.typeId"
              :label="item.typeOneName"
              :value="item.typeId"
            >
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="4">
          <span class="search-label">出/入：</span>
          <el-select
            v-model="searchForm.moneyState"
            placeholder="请选择"
            size="mini"
            style="width: 70% !important;"
            clearable
          >
            <el-option
              v-for="item in moneyStateOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option> </el-select
        ></el-col>
        <el-col :span="8">
          <el-button type="primary" size="mini" @click="searchClick"
            >搜索</el-button
          >
          <el-button type="primary" size="mini" @click="openSticDialog"
            >统计分析</el-button
          >
          <el-button type="primary" size="mini" @click="openTypeManageDialog"
            >分类管理</el-button
          >
          <el-button @click="openUploadDialog" type="primary" size="mini"
            >上传</el-button
          >
          <el-button @click="exportData" type="primary" size="mini"
            >导出</el-button
          >
        </el-col>
      </el-row>
    </div>
    <el-row :gutter="10">
      <el-col :span="20">
        <!--表格内容-->
        <el-table
          ref="tableRef"
          v-loading="loading"
          :data="tableData"
          style="width: 120%"
          border
          stripe
          highlight-current-row
          :height="tableHeight"
          :default-sort="{ prop: 'date', order: 'descending' }"
          @row-click="handleRowClick"
          show-summary
          :summary-method="getSummaries"
        >
          <!-- <el-table-column type="selection" width="45" align="center"></el-table-column> -->
          <!-- <el-table-column type="index" label="序号" width="50"> </el-table-column> -->
          <el-table-column property="date" label="日期" width="180" sortable>
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 5px">{{ scope.row.date }}</span>
            </template>
          </el-table-column>
          <el-table-column
            property="itemName"
            label="名称"
            width="180"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column property="money" label="金额" sortable>
            <template slot-scope="scope">
              <span v-if="scope.row.moneyState == 1" class="money-state-in"
                >+{{ scope.row.money }}</span
              >
              <span v-else class="money-state-out">-{{ scope.row.money }}</span>
            </template>
          </el-table-column>
          <el-table-column property="typeOneName" label="类型">
          </el-table-column>
          <el-table-column
            property="comment"
            label="备注"
            show-overflow-tooltip
          >
          </el-table-column>
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
        <el-row class="margin-t-5">
          <el-col :span="1">
            <el-button
              icon="el-icon-bell"
              circle
              size="mini"
              @click="openAdvancedSetting"
              v-if="advancedShow"
            ></el-button>
          </el-col>
          <el-col :span="1">
            <div class="hidden-div" @click="hiddenClick"></div>
          </el-col>
          <el-col :span="22">
            <!--分页-->
            <el-pagination
              :page-sizes="[100, 200, 300, 500]"
              :total="pagination.total"
              :current-page.sync="pagination.currentPage"
              @current-change="handleCurrentChange"
              @size-change="handleSizeChange"
              layout="total, sizes, prev, pager, next, jumper"
            >
            </el-pagination>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="4">
        <CalendarHeatmap
          ref="calendarHeatmap"
          @getTableData="getTableData"
          :searchForm="searchForm"
          :currentYear="currentYear"
          :style="{ height: rightChartHeigh }"
          :heatmapMax="advancedSettingForm.heatmapMax"
        ></CalendarHeatmap>
      </el-col>
    </el-row>

    <!--编辑-弹出层-->
    <EditDialog
      ref="eidtDialog"
      :isShowEditDialog="isShowEditDialog"
      :formFileds="formFileds"
      :typeOptions="typeOptions"
      @closeEditDialog="closeEditDialog"
      @getTableData="getTableData"
    ></EditDialog>

    <!-- 上传Dialog -->
    <UploadDialog
      ref="uploadDialog"
      :uploadDialogVisible="uploadDialogVisible"
      @closeUploadDialog="closeUploadDialog"
      @setPreviewTableData="setPreviewTableData"
      @openPreviewDialog="openPreviewDialog"
      @loadData="loadData"
    ></UploadDialog>

    <!-- 上传成功预览Dialog  -->
    <PreviewDialog
      :previewDialogVisible="previewDialogVisible"
      :typeOptions="typeOptions"
      :previewTableData="previewTableData"
      @closePreviewDialog="closePreviewDialog"
      @loadData="loadData"
      @openCompare="openCompare"
      @loadTimeLine="reloadTimeLine"
    ></PreviewDialog>

    <!-- 比较Dialog -->
    <CompareDialog
      :compareDialogVisible="compareDialogVisible"
      :compareForm="compareForm"
      @closeCompareDialog="closeCompareDialog"
      @loadData="loadData"
      @reloadTimeLine="reloadTimeLine"
    ></CompareDialog>

    <!-- 统计 -->
    <SticDialog
      :sticDialogVisible="sticDialogVisible"
      :moneyStateOptions="moneyStateOptions"
      @closeSticDialog="closeSticDialog"
    ></SticDialog>

    <!-- 高级设置 -->
    <AdvancedSettingDialog
      :advancedSettingShow="advancedSettingShow"
      :advancedSettingForm="advancedSettingForm"
      @closeAdvancedSettingDialog="closeAdvancedSettingDialog"
      @getAdvancedSetting="getAdvancedSetting"
      @loadData="loadData"
    ></AdvancedSettingDialog>

    <!-- 分类管理 -->
    <TypeManageDialog
      :typeDialogVisible="typeDialogVisible"
      :typeOptions="typeOptions"
      @closeTypeManageDialog="closeTypeManageDialog"
      @getOneType="getOneType"
    ></TypeManageDialog>
  </div>
</template>

<script>
import CalendarHeatmap from "./chart/CalendarHeatmap";
import EditDialog from "./dialog/EditDialog";
import UploadDialog from "./dialog/UploadDialog";
import PreviewDialog from "./dialog/PreviewDialog";
import CompareDialog from "./dialog/CompareDialog";
import SticDialog from "./dialog/SticDialog";
import AdvancedSettingDialog from "./dialog/AdvancedSettingDialog";
import TypeManageDialog from "./dialog/TypeManageDialog";

export default {
  name: "Table",
  components: {
    CalendarHeatmap,
    EditDialog,
    UploadDialog,
    PreviewDialog,
    CompareDialog,
    SticDialog,
    AdvancedSettingDialog,
    TypeManageDialog
  },
  data() {
    return {
      screenHeight: 0, // 屏幕高度
      loading: false,
      tableData: [], // 表格数据
      previewTableData: [], // 预览表格数据
      typeOptions: [],
      // 编辑弹窗表单
      formFileds: {
        id: null,
        date: "",
        itemName: "",
        money: 0,
        moneyState: "",
        comment: null,
        type: null
      },
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
      moneyStateOptions: [
        {
          value: "1",
          label: "收入"
        },
        {
          value: "0",
          label: "支出"
        }
      ],
      pagination: {
        pageSize: 100,
        currentPage: 1,
        total: 0
      },
      searchForm: {
        yearMonth: "", // 年月
        type: "", // 类型
        moneyState: "", // 收入支出状态
        date: "" // 年月日，点击热力图按年月日搜索中会用到
      },
      isShowEditDialog: false, // 编辑弹窗
      uploadDialogVisible: false, // 上传弹窗
      previewDialogVisible: false, // 预览弹窗
      compareDialogVisible: false, // 对比弹窗
      sticDialogVisible: false, // 统计弹窗
      typeDialogVisible: false, // 类型弹窗
      advancedSettingShow: false, // 高级设置弹窗
      compareForm: {
        oldStr: "",
        newStr: "",
        newData: null
      },
      advancedSettingForm: {
        heatmapMax: 0,
        moneyMax: 0,
        moneyMin: 0,
        filterKeyword: ""
      },
      currentYear: new Date().getFullYear(),
      hiddenNum: 1
    };
  },
  mounted() {
    this.getScreenHeight();
    this.getOneType();
    this.getAdvancedSetting().then(() => {
      this.loadData();
    });
  },
  methods: {
    // 加载图数据和表数据
    loadData() {
      this.loadChart();
      this.getTableData();
    },
    getTableData() {
      this.loading = true;
      this.$axios
        .post("/api/acbi/getAccountBill", {
          itemName: this.searchForm.itemName,
          date: this.searchForm.date,
          yearMonth: this.searchForm.yearMonth,
          pageSize: this.pagination.pageSize,
          currentPage: this.pagination.currentPage,
          type: this.searchForm.type,
          moneyState: this.searchForm.moneyState
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
          this.$message.error("数据加载失败！");
        });
    },
    getOneType() {
      this.$axios
        .get("/api/acbi/getOneType")
        .then(res => {
          const { data } = res;
          if (data.success) {
            this.typeOptions = data.data;
          } else {
            this.$message.warning("分类加载失败");
          }
        })
        .catch(err => {
          console.log(err);
          this.$message.error("分类加载失败");
        });
    },
    loadChart() {
      this.$nextTick(() => {
        this.$refs.calendarHeatmap.drawCalendarHeatmap();
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
    rowEdit(index, row) {
      this.setCurRowChecked(row);
      // 给编辑弹出层赋值
      // ***这里需要注意的是：因为加了排序 所以tableData的顺序和实际显示的行顺序不一样
      for (const key in this.formFileds) {
        this.formFileds[key] = row[key];
      }
      this.isShowEditDialog = true;
    },
    closeEditDialog() {
      this.isShowEditDialog = false;
    },
    setCurRowChecked(row) {
      this.$refs.tableRef.clearSelection();
      this.$refs.tableRef.toggleRowSelection(row);
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
    closeUploadDialog() {
      this.uploadDialogVisible = false;
    },
    searchClick() {
      this.searchForm.date = ""; // 搜索条件中没有date 项，给空值
      this.loadData();
      if (
        this.searchForm.yearMonth != null &&
        this.searchForm.yearMonth != ""
      ) {
        this.currentYear = Number(this.searchForm.yearMonth.substring(0, 4)); // 为日历热力图的当前年份赋值
      }
    },
    setPreviewTableData(data) {
      this.previewTableData = data;
    },
    openPreviewDialog() {
      this.previewDialogVisible = true;
    },
    closePreviewDialog() {
      this.previewDialogVisible = false;
    },
    closeCompareDialog() {
      this.compareDialogVisible = false;
    },
    openSticDialog() {
      this.sticDialogVisible = true;
    },
    closeSticDialog() {
      this.sticDialogVisible = false;
    },
    openAdvancedSetting() {
      this.advancedSettingShow = true;
    },
    closeAdvancedSettingDialog() {
      this.advancedSettingShow = false;
    },
    openTypeManageDialog() {
      this.typeDialogVisible = true;
    },
    closeTypeManageDialog() {
      this.typeDialogVisible = false;
    },
    reloadTimeLine() {
      this.$refs["uploadDialog"].getCurrentFileUploadTimeLine();
    },
    openCompare(data) {
      this.compareForm.oldStr = data.oldContent;
      this.compareForm.newStr = data.newContent;
      this.compareForm.newData = data.newData;
      this.compareDialogVisible = true;
    },
    handleCurrentChange(val) {
      this.pagination.currentPage = val;
      this.getTableData();
    },
    handleSizeChange(val) {
      this.pagination.pageSize = val;
      this.getTableData();
    },
    getSummaries(param) {
      const { columns, data } = param;
      let sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          const title = this.searchForm.moneyState === "0" ? "支出" : "收入";
          sums[index] = title + "合计";
          return;
        }
        const values = data
          .filter(item => item.moneyState == this.searchForm.moneyState)
          .map(item => Number(item[column.property]));
        // const values = data.map(item => Number(item[column.property]));
        if (!values.every(value => isNaN(value))) {
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr);
            if (!isNaN(value)) {
              return prev + curr;
            } else {
              return prev;
            }
          }, 0);
          sums[index] += " 元";
        } else {
          sums[index] = "N/A";
        }
      });
      sums[3] = "N/A";
      sums[4] = "N/A";
      return sums;
    },
    async getAdvancedSetting() {
      this.$axios
        .get("/api/acbi/getAdvancedSetting")
        .then(res => {
          const { data } = res;
          if (data.success) {
            this.advancedSettingForm = data.data;
          } else {
            this.$message.warning("高级设置获取失败");
          }
        })
        .catch(err => {
          console.log(err);
          this.$message.error("高级设置获取失败");
        });
    },
    hiddenClick() {
      this.hiddenNum += 1;
      // this.$message.success(this.hiddenNum)
    },
    exportData() {
      this.$confirm("将当前页面数据导出为Excel？", "提示", {
        comfirmButtonText: "确定",
        cancelButtonText: "取消"
      })
        .then(() => {
          require.ensure([], () => {
            const {
              export_json_to_excel
            } = require("@/assets/js/Export2Excel");
            const exportData = JSON.parse(JSON.stringify(this.tableData));
            let num = 0;
            for (let item of exportData) {
              item.num = ++num;
              item.moneyState = item.moneyState == 0 ? "支出" : "收入";
            }
            const tHeader = ["日期", "名称", "出/入", "金额", "类型", "备注"];
            const filterVal = [
              "date",
              "itemName",
              "moneyState",
              "money",
              "typeOneName",
              "comment"
            ];
            const formatData = this.formatJson(filterVal, exportData);
            export_json_to_excel(tHeader, formatData, "我的账本");
          });
        })
        .catch(() => {
          this.$message("不需要就拉倒");
        });
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => v[j]));
    }
  },
  computed: {
    tableHeight() {
      return this.screenHeight - 220 + "px";
    },
    rightChartHeigh() {
      return this.screenHeight - 180 + "px";
    },
    advancedShow() {
      return this.hiddenNum % 14 === 0;
    }
  },
  updated() {
    this.$nextTick(() => {
      this.$refs["tableRef"].doLayout();
    });
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
.search-label {
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
  font-size: 14px;
  color: #606266;
}
.hidden-div {
  width: 30px;
  height: 30px;
  &:hover {
    box-shadow: 0 0 2px rgb(145, 138, 138);
  }
}
</style>
<style>
.main {
  padding-bottom: 0px !important;
}
.money-state-out {
  font-size: 14px;
  color: #67c23a;
}
.money-state-in {
  font-size: 14px;
  color: #f56c6c;
}
.my-msg {
  z-index: 9999 !important;
}
.t-i-1 {
  text-indent: 1em;
}
</style>
