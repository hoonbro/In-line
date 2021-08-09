module.exports = {
  devServer: {
    proxy: {
      "/api/v1": {
        // 로컬의 백엔드에서 작업하고 싶다면
        // target: process.env.VUE_APP_API_BASE_URL,
        // ec2에 있는 백엔드에서 작업하고 싶다면,
        target: "http://i5d207.p.ssafy.io:8080",
      },
      "/images": {
        target: "https://i5d207.p.ssafy.io",
      },
    },
    overlay: true,
  },
}
