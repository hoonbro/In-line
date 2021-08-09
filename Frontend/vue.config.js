module.exports = {
  devServer: {
    proxy: {
      "/api/v1": {
        // target: process.env.VUE_APP_API_BASE_URL,
        target: "http://i5d207.p.ssafy.io:8080",
      },
    },
    overlay: true,
  },
}
