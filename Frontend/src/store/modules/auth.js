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
  },
  mutations: {
    setToken(state, payload) {
      state.accessToken = payload
      localStorage.setItem("accessToken", payload)
    },
    setUser(state, payload) {
      state.user = payload
      localStorage.setItem("user", payload)
    },
  },
  actions: {
    async login({ commit }, formData) {
      try {
        const res = await authAPI.post("/login", formData)
        commit("setToken", res.data.accessToken)
        commit("setUser", res.data.userDto)
      } catch (error) {
        if (error.response.status === 404) {
          throw Error("이 이메일로 가입한 계정을 찾을 수 없어요.")
        } else if (error.response.status === 401) {
          throw Error("입력한 비밀번호를 다시 확인해주세요.")
        }
      }
    },
  },
  getters: {},
}
