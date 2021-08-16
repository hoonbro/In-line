module.exports = {
  devServer: {
    proxy: {
      "/api/v1": {
        // 로컬의 백엔드에서 작업하고 싶다면
        // target: "http://localhost:8080",
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
