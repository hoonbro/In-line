import axios from "axios"

const authAPI = axios.create({
  baseURL: "/api/v1/users",
})

export const auth = {
  namespaced: true,
  state: {
    user: {},
    accessToken:
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2RmQGFzZGYuYXNkZiIsImV4cCI6MTYyODc1NjM5Mn0.7qOf3aR0qTCOVqt7M6mk9F2TN_wuP335fsO63K0ay-I",
    // 임시비밀번호 메일이 발송되면 이 state가 true로 바뀜
    // 비밀번호를 변경하면 이 state가 false로 바뀜
    // 이 state가 true이면 로그인 후, 비밀번호 변경 페이지로 이동
    shouldChangePassword: false,
  },
  mutations: {
    setToken(state, payload) {
      state.accessToken = payload
      localStorage.setItem("accessToken", payload)
    },
    setUser(state, payload) {
      state.user = payload
      localStorage.setItem("user", JSON.stringify(payload))
    },
    setShouldChangePassword(state, data) {
      // data : boolean type
      state.shouldChangePassword = data
    },
  },
  actions: {
    async login({ commit }, formData) {
      try {
        const res = await authAPI.post("/login", formData)
        commit("setToken", res.data.accessToken)
        commit("setUser", res.data.userDto)
      } catch (error) {
        const { status } = error.response
        switch (status) {
          case 404: {
            throw Error("이 이메일로 가입한 계정을 찾을 수 없어요.")
          }
          case 401: {
            throw Error("입력한 비밀번호를 다시 확인해주세요.")
          }
          case 500: {
            throw Error("서버에 문제가 생긴 것 같아요..!")
          }
          default: {
            throw Error("무슨 문제가 생긴 것 같은데, 저도 잘 모르겠네요 0ㅅ0")
          }
        }
      }
    },
    async resetPassword({ commit }, email) {
      try {
        await authAPI.put("/reset-password", email)
        commit("setShouldChangePassword", true)
      } catch (error) {
        console.log(error)
        console.log(error.response.status)
        throw Error("이메일 전송에 실패했습니다")
      }
    },
    async changePassword({ commit }, passwordForm) {
      try {
        console.log(passwordForm)
        const res = await axios({
          method: "put",
          url: "/api/v1/users/change-password",
          data: passwordForm,
          headers: {
            accessToken: `${localStorage.getItem("accessToken")}`,
          },
        })
        console.log(res)
        commit("setShouldChangePassword", false)
      } catch (error) {
        console.log(error)
        console.log(error.response.status)
        throw Error("비밀번호 변경에 실패했습니다")
      }
    },
  },
  getters: {},
}
