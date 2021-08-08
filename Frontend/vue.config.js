module.exports = {
  devServer: {
    proxy: {
      "/api/v1": {
        target: process.env.VUE_APP_API_BASE_URL,
      },
    },
    overlay: true,
  },
}
