const path = require('path')
module.exports = {
  pluginOptions: {
    "style-resources-loader": {
      preProcessor: "less",
      patterns: [path.resolve(__dirname, "src/assets/css/variable.less")]
    }
  },
  devServer: {
    //被认为是索引文件的文件名
    index: "index.html",
    port: 6251,
    //dev-server在服务器启动后打开浏览器
    open: true,
    // 跨域代理
    proxy: {
      //配置跨域
      "/api": {
        target: "http://localhost:4536",
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          "^/api": "",
        },
      },
    },
  },
};
