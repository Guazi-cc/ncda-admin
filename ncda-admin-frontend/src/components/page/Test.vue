<template>
  <div style="height: 90%">
    <iframe
      :src="reportUrl"
      frameborder="0"
      style="width:100%; height: 100%;"
      id="iframeBox"
    ></iframe>
  </div>
</template>

<script>
export default {
  data() {
    return {
      reportUrl: "/sosob/sosobMarket.html",
      screenHeight: 0
    };
  },
  created() {},
  mounted() {
    this.getScreenHeight();
  },
  methods: {
    getScreenHeight() {
      this.screenHeight = document.documentElement.clientHeight;
      window.onresize = () => {
        return (() => {
          this.screenHeight = document.documentElement.clientHeight;
        })();
      };
    }
  }
};
</script>

<style scoped lang="less"></style>
