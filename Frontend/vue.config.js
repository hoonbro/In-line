module.exports = {
  // publicPath: "/",
  devServer: {
    // https: true,
    port: 8080,
    proxy: {
      "/api/v1": {
        target: "http://localhost:3000",
      },
    },
  },
}
