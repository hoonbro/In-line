import axios from "axios"

export const onboard = {
  namespaced: true,
  state: {
    onBoardList: [],
  },
  getters: {},
  mutations: {
    setOnBoardList: (state, data) => {
      state.onBoardList = data
    },
    deleteOnBoardItem: (state, data) => {
      const idx = state.onBoardList.indexOf(data)
      state.onBoardList.splice(idx, 1)
      console.log(state.onBoardList)
    },
    appendOnBoardItem: (state, data) => {
      state.onBoardList.push(data)
    },
  },
  actions: {
    registerMember: async (context, formData) => {
      try {
        const res = await axios({
          url: "/api/v1/on-board/user",
          method: "POST",
          headers: {
            accessToken: context.rootState.auth.accessToken,
          },
          data: formData,
        })
        context.commit("appendOnBoardItem", res.data)
      } catch (error) {
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
    getOnBoardList: async (context, officeId) => {
      try {
        const res = await axios.get(`/api/v1/on-board/${officeId}`)
        context.commit("setOnBoardList", res.data)
      } catch (error) {
        throw Error("에러 발생!")
      }
    },
    deleteOnBoardMember: async (context, member) => {
      try {
        const res = await axios.delete(`/api/v1/on-board/user/${member.email}`)
        context.commit("deleteOnBoardItem", member)
      } catch (error) {
        console.log(error)
        throw Error("삭제 실패")
      }
    },
  },
}
