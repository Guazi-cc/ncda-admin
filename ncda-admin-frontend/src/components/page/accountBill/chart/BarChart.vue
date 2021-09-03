<template>
  <div id="bar" class="bar-chart"></div>
</template>

<script>
export default {
  props: ["sticSearchForm"],
  methods: {
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
    }
  },
  computed: {
    chartBar() {
      return this.$echarts.init(this.$util.getDom("bar"));
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
