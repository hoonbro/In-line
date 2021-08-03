module.exports = {
  // publicPath: "/",
  devServer: {
    host: "localhost",
    port: 8083,
    proxy: {
      "/api/v1": {
        target: "http://i5d207.p.ssafy.io:8080",
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
