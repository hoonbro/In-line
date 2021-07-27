// 영수
import axios from "axios"

export const landing = {
  namespaced: true,
  state: () => ({}),
  getters: {},
  mutations: {},
  actions: {
    registerOffice: async (context, formData) => {
      try {
        const res = await axios({
          method: "post",
          url: "http://localhost:3000/api/v1/office",
          data: formData,
        })
        // const response = await axios.post(
        //   "http://localhost:3000/office",
        //   formData
        // )
        console.log(res)
        return new Promise((resolve, reject) => resolve(res))
      } catch (error) {
        console.error(error)
      }
    },
  },
}
