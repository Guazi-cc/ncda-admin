<template>
  <div>
    <div class="search-box">
      <el-row type="flex">
        <el-col :xs="8" :sm="4" :md="5" :lg="4" :xl="1">
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
          <el-button type="primary" size="mini" @click="statistic"
            >统计分析</el-button
          >
          <el-button type="primary" size="mini" @click="typeManagement"
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

    <!-- 统计  -->
    <el-dialog
      @open="sticDialogOpen"
      title="数据统计"
      width="60%"
      :visible.sync="sticDialogVisible"
    >
      <div class="stic-box">
        <el-tabs v-model="sticActiveName" @tab-click="handleClick">
          <el-tab-pane label="柱状图" name="first">
            <div style="padding: 10px;">
              <el-row class="margin-b-20">
                <el-col :span="7">
                  <span class="search-label">横坐标：</span>
                  <el-radio-group
                    v-model="sticSearchForm.xdataType"
                    size="mini"
                  >
                    <el-radio label="0" border>时间</el-radio>
                    <el-radio label="1" border>类型</el-radio>
                  </el-radio-group>
                </el-col>
                <el-col :span="10">
                  <span class="search-label">选择时间段：</span>
                  <el-date-picker
                    v-model="sticSearchForm.monthStart"
                    type="month"
                    placeholder="选择月"
                    size="mini"
                    value-format="yyyy-MM-dd"
                    format="yyyy-MM"
                    style="width: 110px !important;"
                    clearable
                  >
                  </el-date-picker>
                  -
                  <el-date-picker
                    v-model="sticSearchForm.monthEnd"
                    type="month"
                    placeholder="选择月"
                    size="mini"
                    value-format="yyyy-MM-dd"
                    format="yyyy-MM"
                    style="width: 110px !important;"
                    clearable
                  >
                  </el-date-picker>
                </el-col>
                <el-col :span="6">
                  <span class="search-label">出/入：</span>
                  <el-select
                    v-model="sticSearchForm.moneyState"
                    placeholder="请选择"
                    size="mini"
                    style="width: 120px !important;"
                  >
                    <el-option
                      v-for="item in moneyStateOptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="1">
                  <el-button
                    icon="el-icon-search"
                    circle
                    size="mini"
                    @click="barChartSearchClick"
                  ></el-button>
                </el-col>
              </el-row>
              <el-row class="margin-b-20">
                <el-col :span="24">
                  <div id="bar" class="bar-chart"></div>
                </el-col>
              </el-row>
            </div>
          </el-tab-pane>
          <el-tab-pane label="雷达图" name="second">
            <div id="radarChart" class="bar-chart"></div>
          </el-tab-pane>
          <el-tab-pane label="giao" name="fourth">雷达图</el-tab-pane>
        </el-tabs>
      </div>
    </el-dialog>

    <!-- 高级设置 -->
    <el-dialog
      title="高级设置"
      :visible.sync="advancedSettingShow"
      width="550px"
      @open="advancedSettingOpen"
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
          <el-button @click="advancedSettingShow = false" class="pull-right"
            >取消</el-button
          >
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 分类管理 -->
    <el-dialog title="分类管理" :visible.sync="typeDialogVisible" width="450px">
      <div style="height: 400px">
        <div class="margin-b-10">
          <el-button
            icon="el-icon-plus"
            title="新增"
            size="mini"
            @click="openAddTypeDialog('新增')"
          ></el-button>
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
  </div>
</template>

<script>
// import { CodeDiff } from "v-code-diff";
import Util from "@/assets/js/util";
import CalendarHeatmap from "@/components/page/accountBill/chart/CalendarHeatmap";
import EditDialog from "@/components/page/accountBill/dialog/EditDialog";
import UploadDialog from "@/components/page/accountBill/dialog/UploadDialog";
import PreviewDialog from "@/components/page/accountBill/dialog/PreviewDialog";
import CompareDialog from "@/components/page/accountBill/dialog/CompareDialog";

export default {
  name: "Table",
  components: {
    // CodeDiff,
    CalendarHeatmap,
    EditDialog,
    UploadDialog,
    PreviewDialog,
    CompareDialog
  },
  data() {
    return {
      screenHeight: 0, // 屏幕高度
      loading: false,
      tableData: [], // 表格数据
      typeManageTableData: [],
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
      typeManageRules: {
        typeOneName: [
          { required: true, message: "类型名不能为空", trigger: "blur, change" }
        ]
      },
      advancedSettingRules: {},
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
      typeManageForm: {
        typeOneName: "",
        typeKeyword: ""
      },
      sticSearchForm: {
        monthStart: "",
        monthEnd: "",
        xdataType: "0", // 横坐标，0是时间，1是类型
        moneyState: "0" // 0是支出，1是收入
      },
      isShowEditDialog: false, // 编辑弹窗
      uploadDialogVisible: false, // 上传弹窗
      previewDialogVisible: false, // 预览弹窗
      compareDialogVisible: false, // 对比弹窗
      sticDialogVisible: false, // 统计弹窗
      typeDialogVisible: false, // 类型弹窗
      typeInnerVisible: false,
      advancedSettingShow: false, // 高级设置弹窗
      sticActiveName: "first", // 统计图表弹窗的tab
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
      defaultProps: {
        children: "twoTypeList",
        label: "label"
      },
      currentYear: new Date().getFullYear(),
      heatmapMax: 200,
      hiddenNum: 1,
      innerDialogTitle: ""
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
    reloadTimeLine() {
      this.$refs["uploadDialog"].getCurrentFileUploadTimeLine();
    },
    openCompare(data) {
      this.compareForm.oldStr = data.oldContent;
      this.compareForm.newStr = data.newContent;
      this.compareForm.newData = data.newData;
      this.compareDialogVisible = true;
    },
    closeCompareDialog() {
      this.compareDialogVisible = false;
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
    statistic() {
      this.sticDialogVisible = true;
      this.sticActiveName = "first";
    },
    typeManagement() {
      this.typeDialogVisible = true;
    },
    handleNodeClick(data) {
      console.log(data);
    },
    sticDialogOpen() {
      this.$nextTick(() => {
        this.drawBar();
      });
    },
    loadChart() {
      this.$nextTick(() => {
        this.$refs.calendarHeatmap.drawCalendarHeatmap();
      });
    },
    handleClick({ paneName }) {
      if (paneName === "first") {
        this.$nextTick(() => {
          this.drawBar();
        });
      } else if (paneName === "second") {
        this.$nextTick(() => {
          this.drawRadarChart();
        });
      } else if (paneName === "third") {
      } else {
      }
    },
    drawBar() {
      this.$axios
        .post("/api/acbi/selectBarChartData", {
          startTime: this.startTimeFormatter(this.sticSearchForm.monthStart),
          endTime: this.EndTimeFormatter(this.sticSearchForm.monthEnd),
          moneyState: this.sticSearchForm.moneyState,
          xdataType: this.sticSearchForm.xdataType
        })
        .then(res => {
          const { data } = res;
          let xData;
          if (this.sticSearchForm.xdataType === "0") {
            xData = data.data.map(({ date }) => date.substring(0, 7));
          } else {
            xData = data.data.map(({ typeOneName }) => typeOneName);
          }
          const yData = data.data.map(({ money }) => money);
          const title = "数据の统计";
          this.chartBar.setOption({
            title: Object.assign({}, Util.defaultEchartsOpt.title, {
              text: title
            }),
            tooltip: {
              trigger: "item",
              formatter: "{a} <br/>{b} : {c}"
            },
            xAxis: {
              type: "category",
              data: xData,
              axisLine: {
                lineStyle: {
                  color: "#999"
                }
              },
              axisLabel: {
                margin: 10,
                textStyle: {
                  fontSize: 12
                }
              }
            },
            yAxis: {
              type: "value",
              splitLine: {
                lineStyle: {
                  color: ["#D4DFF5"]
                }
              },
              axisTick: {
                show: false
              },
              axisLine: {
                lineStyle: {
                  color: "#999"
                }
              },
              axisLabel: {
                margin: 10,
                textStyle: {
                  fontSize: 12
                }
              }
            },
            series: [
              {
                name: title,
                data: yData,
                type: "bar",
                symbol: "triangle",
                symbolSize: 20,
                lineStyle: {
                  normal: {
                    color: "green",
                    width: 4,
                    type: "dashed"
                  }
                },
                barWidth: 25,
                itemStyle: {
                  normal: {
                    // barBorderRadius: 30,
                    // color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    //   {
                    //     offset: 0,
                    //     color: "#00feff"
                    //   },
                    //   {
                    //     offset: 0.5,
                    //     color: "#027eff"
                    //   },
                    //   {
                    //     offset: 1,
                    //     color: "#0286ff"
                    //   }
                    // ])
                  }
                }
              }
            ]
          });
        });
    },
    drawRadarChart() {},
    openAdvancedSetting() {
      this.advancedSettingShow = true;
    },
    advancedSettingOpen() {
      this.getAdvancedSetting();
    },
    // 加载图数据和表数据
    loadData() {
      this.loadChart();
      this.getTableData();
    },
    saveAdvancedSetting() {
      this.$axios
        .post("/api/acbi/saveAdvancedSetting", this.advancedSettingForm)
        .then(res => {
          const { data } = res;
          if (data.success) {
            this.$message.success("设置成功");
            this.advancedSettingShow = false;
            this.loadData();
          } else {
            this.$message.warning("设置失败");
          }
        })
        .catch(err => {
          console.log(err);
          this.$message.error("高级设置保存失败");
        });
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
    barChartSearchClick() {
      this.drawBar();
    },
    startTimeFormatter(param) {
      if (this.$util.isEmpty(param)) {
        let date = new Date();
        date.setFullYear(new Date().getFullYear());
        date.setMonth(0);
        date.setDate(1);
        // 今年1月1日
        return new Date(date).Format("yyyy-MM-dd");
      }
      return param;
    },
    EndTimeFormatter(param) {
      if (param == null || param == "") {
        return new Date().Format("yyyy-MM-dd");
      }
      let monthLast = this.$util.monthLastDate(new Date(param)); // 当月最后一天
      return this.$util.dateToString(new Date(monthLast), "yyyy-MM-dd");
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
                this.getOneType();
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
    openAddTypeDialog() {
      this.innerDialogTitle = "新增分类";
      this.typeInnerVisible = true;
    },
    openEditTypeDialog(row) {
      this.innerDialogTitle = "编辑分类";
      this.$nextTick(() => {
        // 回显数据延迟赋值，为了让表单先清空
        this.typeManageForm = JSON.parse(JSON.stringify(row));
      });

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
            this.getOneType();
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
            this.getOneType();
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
    }
  },
  computed: {
    tableHeight() {
      return this.screenHeight - 220 + "px";
    },
    rightChartHeigh() {
      return this.screenHeight - 180 + "px";
    },
    chartBar() {
      return this.$echarts.init(Util.getDom("bar"));
    },
    chrtRadar() {
      return this.$echarts.init(Util.getDom("radarChart"));
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

.my-msg {
  z-index: 9999 !important;
}
.compare-box {
  position: relative;
  height: 450px;
  overflow: auto;
}
.compare-title {
  display: inline-block;
  width: 50%;
  font-size: 14px;
  color: #606266;
}
.stic-box {
  position: relative;
  height: 450px;
  overflow: auto;
}
.type-box {
  position: relative;
  height: 500px;
  overflow: auto;
}
.chart {
  width: 100%;
  height: 300px;
  .border-radius(8px);
  background-color: #fff;
  box-shadow: 0 0 5px transparent;
  &:hover {
    box-shadow: 0 0 5px @mainColor;
  }
}
.bar-chart {
  margin: 0 auto;
  width: 95%;
  height: 300px;
  .border-radius(8px);
  background-color: @boxBgColor;
  box-shadow: 0 0 5px transparent;
  &:hover {
    box-shadow: 0 0 5px @mainColor;
  }
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
</style>
