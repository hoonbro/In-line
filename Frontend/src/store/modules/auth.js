import axios from "axios"

export const auth = {
  namespaced: true,
  state: {
    token:
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2RmQGFzZGYuYXNkZiIsImV4cCI6MTYyODc1NjM5Mn0.7qOf3aR0qTCOVqt7M6mk9F2TN_wuP335fsO63K0ay-I",
    // 임시비밀번호 메일이 발송되면 이 state가 true로 바뀝니다.
    // 이 state가 true이면 로그인 후, 비밀번호 변경 페이지로
    shouldChangePassword: false,
  },
  getters: {},
  mutations: {
    setShouldChangePassword(state, data) {
      // data : boolean type
      state.shouldChangePassword = data
    },
  },
  actions: {
    login: async ({ commit }, formData) => {
      try {
        const res = await axios({
          method: "post",
          url: "/api/v1/users/login",
          data: formData,
        })
        localStorage.setItem("jwt", res.data.accessToken)
        localStorage.setItem("user", res.data.userDto)
        return new Promise(resolve => resolve(res))
      } catch (error) {
        console.log(error)
        return
      }
    },
    async resetPassword({ commit }, email) {
      try {
        const res = await axios({
          method: "put",
          url: "/api/v1/users/reset-password",
          data: email,
        })
        commit("setShouldChangePassword", true)
        return res
      } catch (reason) {
        console.log(reason)
        return
      }
    },
  },
}
