import { apiAxios } from "@/lib/axios"
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
      console.log(state.roomChatList)
    },
    resetRoomChat(state) {
      state.roomChatList = []
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
      const res = await apiAxios.get(`/chat/${rootState.auth.user.officeId}`)
      commit("initOfficeChatList", res.data)
    },
  },
}
