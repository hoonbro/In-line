module.exports = {
  // publicPath: "/",
  devServer: {
    port: 8083,
    proxy: {
      "/api/v1": {
        target: process.env.VUE_APP_API_BASE_URL,
      },
      // "/groupcall": {
      //   target: "https://13.124.47.223:8995",
      //   ws: true,
      //   // changeOrigin: true,
      //   logLevel: "debug",
      // },
    },
    overlay: true,
  },
}
