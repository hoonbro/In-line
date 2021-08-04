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
  },
}
