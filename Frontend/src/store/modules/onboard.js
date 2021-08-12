import axios from "axios"

export const onboard = {
  namespaced: true,
  state: {},
  getters: {},
  mutations: {},
  actions: {
    registerMember: async (context, formData) => {
      try {
        console.log(formData)
        const res = await axios({
          url: "/api/v1/on-board/user",
          method: "POST",
          headers: {
            accessToken: context.rootState.auth.accessToken,
          },
          data: formData,
        })
        console.log(res)
      } catch (error) {
        console.log(error)
        const { status } = error.response
        switch (status) {
          case 409: {
            throw Error("이미 가입한 이메일이에요!")
          }
          case 400: {
            throw Error("이미 메일을 전송했습니다!")
          }
          default: {
            throw Error("메일 전송 실패 ㅜㅜ")
          }
        }
      }
    },
    getInitData: (context, email) => {
      return axios.get(`/api/v1/on-board/user/${email}`)
    },
  },
}
