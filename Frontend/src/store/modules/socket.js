import axios from "axios"

const chatAxios = axios.create({
  baseURL: "/api/v1/chat",
})

export const socket = {
  namespaced: true,
  state: {
    stompClient: null,
    // subscription: null,
    officeChatList: [],
  },
  getters: {
    stompClient(state) {
      return state.stompClient
    },
  },
  mutations: {
    setStompClient(state, payload) {
      state.stompClient = payload
    },
    initOfficeChatList(state, chatList) {
      state.officeChatList = chatList
    },
    addOfficeChat(state, chat) {
      state.officeChatList.push(chat)
    },
    // setSubscription(state, subscription) {
    //   state.subscription = subscription
    // },
  },
  actions: {
    async getAllOfficeChat({ commit, rootState }) {
      const res = await chatAxios({
        method: "GET",
        url: `/${rootState.auth.user.officeId}`,
        headers: {
          accessToken: rootState.auth.accessToken,
        },
      })
      commit("initOfficeChatList", res.data)
    },
  },
}
