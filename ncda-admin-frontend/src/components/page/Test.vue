<template>
  <div>
    <iframe
      :src="reportUrl"
      frameborder="0"
      style="width:100%; height: 700px;"
      id="iframeBox"
    ></iframe>
  </div>
</template>

<script>
export default {
  data() {
    return {
      reportUrl: "/sosob/sosobMarket.html",
    };
  },
  created() {},
  mounted() {},
  methods: {}
};
</script>

<style scoped lang="less"></style>
