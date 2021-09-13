<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="16">
        <div id="kline">
          <el-card shadow="never">
            <!-- :klineParams="klineParams" :klineData="klineData" 绑定下面data数据 
        用于自定制数据传输到vue-kline, ref="callMethods"绑定一个DOM事件 用于调用接口  --->
            <Vue-kline
              :klineParams="klineParams"
              :klineData="klineData"
              ref="callMethods"
            ></Vue-kline>
          </el-card>
        </div>
      </el-col>
      <el-col :span="8">
        <el-card shadow="never">
          <div class="market-obx">
            <div class="title">
              <h4 style="display: inline">{{ traNm }}</h4>
              <h3 style="display: inline">{{ price }}</h3>
              <span>+10%</span>
            </div>
            <el-row>
              <el-col :span="12">
                <el-card shadow="never" style="height: 200px">
                  <span style="font-size: 12px;">102.5</span>
                  <div style="display: inline; width: 20px;">
                    <el-progress
                      :width="50"
                      :text-inside="true"
                      :stroke-width="15"
                      :percentage="50"
                      status="exception"
                      :format="progressFormat"
                    ></el-progress>
                  </div>
                </el-card>
              </el-col>
              <el-col :span="12">
                <el-card shadow="never" style="height: 200px"> </el-card>
              </el-col>
            </el-row>
          </div>
        </el-card>
        <el-card shadow="never">
          <div class="transaction-box">
            <el-row :gutter="20">
              <el-col :span="12"
                ><el-button @click="maichu" style="width: 100%" type="danger"
                  >卖出</el-button
                ></el-col
              >
              <el-col :span="12"
                ><el-button style="width: 100%" type="success"
                  >买入</el-button
                ></el-col
              >
            </el-row>
            <el-row class="margin-t-5">
              <el-col :span="24">
                <el-radio-group
                  v-model="type"
                  size="mini"
                  class="input-with-select"
                >
                  <el-radio-button label="1">限价委托</el-radio-button>
                  <el-radio-button label="2">市价委托</el-radio-button>
                  <el-radio-button label="3">止盈止损</el-radio-button>
                  <el-radio-button label="4">计划委托</el-radio-button>
                </el-radio-group>
              </el-col>
            </el-row>
            <el-row class="margin-t-5">
              <el-col :span="24">
                <el-input
                  placeholder="金额"
                  v-model="input1"
                  @input="inputChange"
                >
                  <el-select
                    v-model="select"
                    slot="prepend"
                    placeholder="请选择"
                    style="whdth: 50%;"
                  >
                    <el-option label="金额" value="1"></el-option>
                    <el-option label="数量" value="2"></el-option>
                  </el-select>
                  <template slot="append">{{
                    select === "1" ? "USDT" : "CDC"
                  }}</template>
                </el-input>

                <el-slider
                  v-model="num"
                  :format-tooltip="formatTooltip"
                  @input="numChange"
                ></el-slider>
                <span style="font-size: 12px;">可用</span>
                <span style="font-size: 12px; float: right;"
                  >{{ total }}USDT</span
                >
              </el-col>
            </el-row>
            <el-card shadow="never">
              <el-tabs v-model="activeName" tab-position="right">
                <el-tab-pane label="限价委托" name="first">
                  <div style="display: inline-block">
                    <el-progress
                      type="circle"
                      :percentage="28"
                      color="#f56c6c"
                    ></el-progress>
                  </div>
                  <span style="font-size: 12px; color: #f56c6c;"
                    >限价卖出 CDC/USDT</span
                  >
                </el-tab-pane>
                <el-tab-pane label="市价委托" name="second"
                  >市价委托</el-tab-pane
                >
                <el-tab-pane label="止盈止损" name="third"
                  >止盈止损</el-tab-pane
                >
              </el-tabs>
            </el-card>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import VueKline from "vue-kline"; //当前页引入vue-kline
import myData from "@/assets/data";
import "@/assets/css/border.less";

let klineWidth;
let nnn = 685;
export default {
  components: {
    VueKline
  },
  data() {
    return {
      klineParams: {
        width: 750, // k线窗口宽
        height: 600, // k线窗口高
        theme: "light", // 主题颜色
        language: "zh-cn", //语言
        ranges: ["1w", "1d", "1h", "30m", "15m", "5m", "1m", "line"], // 聚合选项
        symbol: "CDC", // 交易代号
        symbolName: "CDC/USDT", // 交易名称
        intervalTime: 3000, // k线更新周期 毫秒
        depthWidth: 50 // 深度图宽度
      },
      klineData: {},
      traNm: "CDC/USDT",
      price: 400,
      type: 1,
      num: 0,
      select: "1",
      input1: 0,
      total: 6565464564,
      activeName: "first"
    };
  },
  mounted() {
    let klineDiv = document.getElementById("kline");
    let klineWhth = klineDiv.offsetWidth - 100;
    klineWidth = Number(klineWhth);
    console.log(klineDiv);
    debugger;
    setTimeout(() => {
      this.requestData(); // 进入页面时执行 requestData()
    }, 500);
  },
  beforeMount() {},
  created() {},
  methods: {
    maichu() {
      this.$message.success("卖个屁");
    },
    formatTooltip(val) {
      return val + "%";
    },
    progressFormat(val) {
      return "185.12";
    },
    requestData() {
      //方法名任意取
      // this.$axios
      //   .request({
      //     url: "xxxxx", //请求地址
      //     method: "POST"
      //   })
      //   .then(ret => {
      //     this.klineData = ret; // 把返回数据赋值到上面的 klineData,
      //   });
      console.log(myData);
      setTimeout(() => {
        this.klineData = myData;
      }, 1);
    },
    numChange(val) {
      this.input1 = this.total * (val / 100);
    },
    inputChange(val) {
      let percentage = val / this.total;
      this.num = Math.round(percentage * 100);
    }
  },
  computed: {}
};
</script>

<style lang="less" scoped>
.market-obx {
  height: 215px;
}
.transaction-box {
  height: 350px;
}
.title {
  width: 60%;
  margin: 0 auto;
  // background-color: rgb(223, 203, 203);
}
.left-box {
  width: 100%;
  height: 100%;
  background-color: rgb(196, 226, 209);
}
</style>
<style></style>
