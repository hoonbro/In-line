// 영수
import axios from "axios"

export const landing = {
  namespaced: true,
  state: () => ({}),
  getters: {},
  mutations: {},
  actions: {
    registerOffice: async (context, formData) => {
      return axios({
        method: "post",
        url: "/api/v1/office",
        data: formData,
      })
    },
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
      }
    },
  },
}
