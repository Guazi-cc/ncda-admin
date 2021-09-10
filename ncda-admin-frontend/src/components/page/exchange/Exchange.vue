<template>
  <div>
    <el-row>
      <el-col :span="16">
        <!-- :klineParams="klineParams" :klineData="klineData" 绑定下面data数据 
        用于自定制数据传输到vue-kline, ref="callMethods"绑定一个DOM事件 用于调用接口  --->
        <Vue-kline
          :klineParams="klineParams"
          :klineData="klineData"
          ref="callMethods"
        ></Vue-kline>
      </el-col>
      <el-col :span="8">
        <el-card shadow="never">
          <div class="yoyo">
            <div class="title">
              <h4 style="display: inline">{{traNm}}</h4>
              <h3 style="display: inline">{{price}}</h3>
              <span>+10%</span>
            </div>
          </div>
        </el-card>
        <el-card shadow="never">
          <div class="yoyo">
            <div>title</div>
          </div>
        </el-card>
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
        width: 780, // k线窗口宽
        height: 650, // k线窗口高
        theme: "light", // 主题颜色
        language: "zh-cn", //语言
        ranges: ["1w", "1d", "1h", "30m", "15m", "5m", "1m", "line"], // 聚合选项
        symbol: "CDC", // 交易代号
        symbolName: "CDC/USDT", // 交易名称
        intervalTime: 3000, // k线更新周期 毫秒
        depthWidth: 50 // 深度图宽度
      },
      klineData: {},
      traNm: 'CDC/USDT',
      price: 400
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
        this.klineData = myData;
      }, 800);
    }
  },
  computed: {}
};
</script>

<style lang="less" scoped>
.yoyo {
  height: 285px;
  // background-color: rgb(136, 120, 120);
}
.title {
  width: 60%;
  margin: 0 auto;
  background-color: rgb(175, 90, 90);
}
</style>
