// 영수
export const landing = {
  namespaced: true,
  state: () => ({}),
  getters: {},
  mutations: {},
  actions: {
    registerOffice: async (context, formData) => {
      alert("registerOffice actions 실행!")
      try {
        // const response = await axios.post("/url", { data })
        // console.log(response)
        return new Promise((resolve, reject) => resolve())
      } catch (error) {
        console.error(error)
      }
    },
  },
}
