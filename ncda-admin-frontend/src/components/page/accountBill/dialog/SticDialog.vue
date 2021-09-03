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
          <div class="padding-10 char-box">
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
                  class="chart"
                  style="height: 300px"
                ></BarChart>
              </el-col>
            </el-row>
          </div>
        </el-tab-pane>
        <el-tab-pane label="雷达图" name="second">
          <div class="padding-10 char-box">
            <el-row>
              <el-col :span="24">11</el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <div id="pieChart" class="chart" style="height: 300px"></div>
              </el-col>
              <el-col :span="12">
                <div id="radar" class="chart" style="height: 300px"></div>
              </el-col>
            </el-row>
          </div>
        </el-tab-pane>
        <el-tab-pane label="wordCloud" name="third">
          <div class="padding-10 char-box">
            <el-row>
              <el-col :span="24">
                <WordCloud
                  ref="wordChart"
                  class="chart"
                  style="height: 380px; padding: 5px"
                ></WordCloud>
              </el-col>
            </el-row>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </el-dialog>
</template>

<script>
import BarChart from "../chart/BarChart";
import WordCloud from "../chart/WordCloud";

export default {
  props: ["sticDialogVisible", "moneyStateOptions"],
  components: {
    BarChart,
    WordCloud
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
          this.drawPieChart();
          this.drawRadar();
        });
      } else if (paneName === "third") {
        this.$nextTick(() => {
          this.$refs.wordChart.drawWordCloud();
        });
      }
    },
    drawPieChart() {
      this.pieChart.setOption({
        title: {
          text: "某站点用户访问来源",
          subtext: "纯属虚构",
          left: "center"
        },
        tooltip: {
          trigger: "item"
        },
        legend: {
          orient: "vertical",
          left: "left"
        },
        series: [
          {
            name: "访问来源",
            type: "pie",
            radius: "50%",
            roseType: 'radius',
            data: [
              { value: 1048, name: "搜索引擎" },
              { value: 735, name: "直接访问" },
              { value: 580, name: "邮件营销" },
              { value: 484, name: "联盟广告" },
              { value: 300, name: "视频广告" }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)"
              }
            }
          }
        ]
      });
    },
    drawRadar() {
      this.$axios.get("/chart-simple/radar").then(res => {
        const title = "部门预算支出比";
        this.chartRadar.setOption({
          title: Object.assign({}, this.$util.defaultEchartsOpt.title, {
            text: title
          }),
          tooltip: {},
          radar: {
            name: {
              textStyle: {
                color: "#999"
              }
            },
            center: ["50%", "58%"],
            radius: "57%",
            indicator: [
              { name: "销售", max: 16000 },
              { name: "管理", max: 16000 },
              { name: "运营", max: 16000 },
              { name: "客服", max: 16000 },
              { name: "研发", max: 16000 },
              { name: "市场", max: 16000 }
            ]
          },
          series: [
            {
              name: "预算 vs 开销（Budget vs spending）",
              type: "radar",
              symbol: "circle",
              symbolSize: 1,
              data: [
                {
                  value: res.data.budget,
                  name: "预算分配（Allocated Budget）",
                  areaStyle: {
                    normal: {
                      color: "rgba(154, 116, 179, 0.7)"
                    }
                  },
                  itemStyle: {
                    color: "rgba(154, 116, 179, 1)",
                    borderColor: "rgba(154, 116, 179, 0.3)",
                    borderWidth: 10
                  },
                  lineStyle: {
                    normal: {
                      color: "rgba(154, 116, 179, 1)",
                      width: 2
                    }
                  }
                },
                {
                  value: res.data.spending,
                  name: "实际开销（Actual Spending）",
                  areaStyle: {
                    normal: {
                      color: "rgba(126, 237, 238, 0.7)"
                    }
                  },
                  itemStyle: {
                    color: "rgba(126, 237, 238, 1)",
                    borderColor: "rgba(126, 237, 238, 0.3)",
                    borderWidth: 10
                  },
                  lineStyle: {
                    normal: {
                      color: "rgba(126, 237, 238, 1)",
                      width: 2
                    }
                  }
                }
              ]
            }
          ]
        });
      });
      return this;
    },
    barChartSearchClick() {
      this.$refs.barChart.drawBar();
    },
    closeSticDialog() {
      this.$emit("closeSticDialog");
    }
  },
  computed: {
    pieChart() {
      return this.$echarts.init(this.$util.getDom("pieChart"));
    },
    chartRadar() {
      return this.$echarts.init(this.$util.getDom("radar"));
    }
  }
};
</script>

<style lang="less" scoped>
.padding-10 {
  padding: 10px;
}
.char-box {
  height: 400px;
  .border-radius(8px);
}
.chart {
  margin: 0 auto;
  width: 95%;
  .border-radius(8px);
  background-color: @boxBgColor;
  box-shadow: 0 0 5px transparent;
  &:hover {
    box-shadow: 0 0 5px @mainColor;
  }
}
</style>
