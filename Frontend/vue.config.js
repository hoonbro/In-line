module.exports = {
  // publicPath: "/",
  devServer: {
    // https: true,
    port: 8083,
    proxy: {
      "/api/v1": {
        target: "http://13.124.47.223:8080",
      },
    },
  },
}
