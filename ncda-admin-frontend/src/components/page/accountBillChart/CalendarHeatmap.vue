<template>
  <!-- 日历热力图 -->
  <div id="calendarHeatmap" class="chart"></div>
</template>

<script>
export default {
  props: ["searchForm", "advanceSetting", "currentYear"],
  data() {
    return {};
  },
  methods: {
    drawCalendarHeatmap() {
      this.$axios
        .post("/api/acbi/selectCalendarHeatmapChartData", {
          itemName: this.searchForm.itemName,
          year: this.searchForm.yearMonth.substring(0, 4),
          type: this.searchForm.type,
          moneyState: this.$util.isEmpty(this.searchForm.moneyState)
            ? 0
            : this.searchForm.moneyState,
          moneyMax: this.searchForm.moneyMax,
          moneyMin: this.advanceSetting.moneyMin,
          filterKeyword: this.advanceSetting.filterKeyword
        })
        .then(res => {
          const { data } = res;
          const cdata = data.data.map(({ date, money }) => [date, money]);
          let _this = this;
          this.chartCalendarHeatmap.on("click", params => {
            _this.searchForm.date = params.data[0];
            _this.$emit("getTableData");
          });
          this.chartCalendarHeatmap.setOption({
            // title: {
            //   top: 5,
            //   left: "center",
            //   text: "aaaaaaa"
            // },
            tooltip: {
              formatter: "{c}"
            },
            visualMap: {
              min: 0,
              max: this.advanceSetting.heatmapMax,
              // type: "piecewise",
              calculable: true,
              orient: "horizontal",
              left: "center",
              top: "bottom",
              itemWidth: 10,
              // splitNumber: 8,
              inRange: {
                color: [
                  "#ffecb3",
                  "#ffe082",
                  "#ffd54f",
                  "#ffca28",
                  "#ffc107",
                  "#ffb300",
                  "#ffa000",
                  "#ff8f00",
                  "#ff6f00"
                ]
              }
            },
            calendar: {
              top: 60,
              left: "center",
              right: 5,
              bottom: 40,
              cellSize: ["auto", 5],
              range: this.currentYear,
              // itemStyle: {
              //   borderWidth: 0.5
              // },
              yearLabel: { show: true },
              orient: "vertical"
            },
            series: {
              type: "heatmap",
              coordinateSystem: "calendar",
              data: cdata
            }
          });
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  computed: {
    chartCalendarHeatmap() {
      return this.$echarts.init(this.$util.getDom("calendarHeatmap"));
    }
  }
};
</script>

<style></style>
