import axios from "axios"
import { auth } from "@/store/modules/auth"

const chatAxios = axios.create({
  baseURL: "/api/v1/chat",
  headers: {
    accessToken: auth.state.accessToken,
  },
})

export const socket = {
  namespaced: true,
  state: {
    stompClient: null,
    officeChatList: [],
  },
  mutations: {
    setStompClient(state, payload) {
      state.stompClient = payload
    },
    initOfficeChatList(state, chatList) {
      state.officeChatList = []
      state.officeChatList.push(...chatList)
    },
    addOfficeChat(state, chat) {
      state.officeChatList.push(chat)
    },
  },
  actions: {
    async getAllOfficeChat({ commit }) {
      const res = await chatAxios.get(`/${auth.state.user.officeId}`)
      commit("initOfficeChatList", res.data)
    },
  },
}
