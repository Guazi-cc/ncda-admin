<template>
  <el-dialog
    @open="sticDialogOpen"
    title="数据统计"
    width="60%"
    :visible.sync="sticDialogVisible"
    :before-close="closeSticDialog"
  >
    <div class="stic-box">
      <el-tabs v-model="sticActiveName" @tab-click="handleClick">
        <el-tab-pane label="柱状图" name="first">
          <div style="padding: 10px;">
            <el-row class="margin-b-20">
              <el-col :span="7">
                <span class="search-label">横坐标：</span>
                <el-radio-group v-model="sticSearchForm.xdataType" size="mini">
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
                <BarChart
                  ref="barChart"
                  :sticSearchForm="sticSearchForm"
                  class="bar-chart"
                ></BarChart>
                <!-- <div id="bar" class="bar-chart"></div> -->
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
</template>

<script>
import BarChart from "../chart/BarChart";

export default {
  props: ["sticDialogVisible", "moneyStateOptions"],
  components: {
    BarChart
  },
  data() {
    return {
      sticSearchForm: {
        monthStart: "",
        monthEnd: "",
        xdataType: "0", // 横坐标，0是时间，1是类型
        moneyState: "0" // 0是支出，1是收入
      },
      sticActiveName: "first"
    };
  },
  methods: {
    sticDialogOpen() {
      this.$nextTick(() => {
        this.$refs.barChart.drawBar();
        // this.drawBar();
      });
      this.sticActiveName = "first";
    },
    handleClick({ paneName }) {
      if (paneName === "first") {
        this.$nextTick(() => {
          this.$refs.barChart.drawBar();
        });
      } else if (paneName === "second") {
        this.$nextTick(() => {
          this.drawRadarChart();
        });
      } else if (paneName === "third") {
      } else {
      }
    },
    drawRadarChart() {},
    barChartSearchClick() {
      this.$refs.barChart.drawBar();
    },
    closeSticDialog() {
      this.$emit("closeSticDialog");
    }
  },
  computed: {
    chrtRadar() {
      return this.$echarts.init(this.$util.getDom("radarChart"));
    }
  }
};
</script>

<style lang="less" scoped>
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
</style>
