<template>
  <div>
    <el-row>
      <el-col :span="24">
        <!-- :klineParams="klineParams" :klineData="klineData" 绑定下面data数据 
        用于自定制数据传输到vue-kline, ref="callMethods"绑定一个DOM事件 用于调用接口  --->
        <Vue-kline
          :klineParams="klineParams"
          :klineData="klineData"
          ref="callMethods"
        ></Vue-kline>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import VueKline from "vue-kline"; //当前页引入vue-kline
import myData from "@/assets/data";


export default {
  components: {
    VueKline
  },
  data() {
    return {
      klineParams: {
        width: 800, // k线窗口宽
        height: 600, // k线窗口高
        theme: "light", // 主题颜色
        language: "zh-cn", //语言
        ranges: ["1w", "1d", "1h", "30m", "15m", "5m", "1m", "line"], // 聚合选项
        symbol: "BTC", // 交易代号
        symbolName: "BTC/USD", // 交易名称
        intervalTime: 3000, // k线更新周期 毫秒
        depthWidth: 50 // 深度图宽度
      },
      klineData: {}
    };
  },
  mounted() {
    this.requestData(); // 进入页面时执行 requestData()
  },
  methods: {
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
        this.klineData = myData
      }, 800)
    }
  },
  computed: {}
};
</script>

<style lang="less" scoped></style>
