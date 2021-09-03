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
</template>

<script>
export default {
  props: ["sticDialogVisible", "moneyStateOptions"],
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
        this.drawBar();
      });
      this.sticActiveName = "first";
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
            title: Object.assign({}, this.$util.defaultEchartsOpt.title, {
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
    barChartSearchClick() {
      this.drawBar();
    },
    closeSticDialog() {
      this.$emit("closeSticDialog")
    }
  },
  computed: {
    chartBar() {
      return this.$echarts.init(this.$util.getDom("bar"));
    },
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
