import axios from "axios"

const chatAxios = axios.create({
  baseURL: "/api/v1/chat",
})

export const socket = {
  namespaced: true,
  state: {
    stompClient: null,
    roomStompClient: null,
    // subscription: null,
    officeChatList: [],
    roomChatList: [],
  },
  getters: {
    stompClient(state) {
      return state.stompClient
    },
    roomStompClient(state) {
      return state.roomStompClient
    },
  },
  mutations: {
    setStompClient(state, payload) {
      state.stompClient = payload
    },
    setRoomStompClient(state, payload) {
      state.roomStompClient = payload
    },
    initOfficeChatList(state, chatList) {
      state.officeChatList = chatList
    },
    addRoomChat(state, chat) {
      state.roomChatList.push(chat)
      console.log(state.roomChatList )
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
