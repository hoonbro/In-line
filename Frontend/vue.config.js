module.exports = {
  devServer: {
    proxy: {
      "/api/v1": {
        // 로컬의 백엔드에서 작업하고 싶다면
<<<<<<< HEAD
        // target: process.env.VUE_APP_API_BASE_URL,
=======
        // target: "http://localhost:8080",
>>>>>>> 7580ff9b3ffc0f1192aa47fe4e5d3d92a798ed0d
        // ec2에 있는 백엔드에서 작업하고 싶다면,
        target: "http://i5d207.p.ssafy.io:8080",
      },
      "/images": {
        target: "https://i5d207.p.ssafy.io",
      },
      "/rooms": {
        target: "https://i5d207.p.ssafy.io:8995",
      },
      "/chatStomp": {
        target: "https://i5d207.p.ssafy.io:8995",
      },
    },
    overlay: true,
  },
}
