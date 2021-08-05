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
    setOfficeChatList(state, payload) {
      console.log(payload)
      state.officeChatList.push(payload)
    },
  },
  actions: {
    async getAllOfficeChat({ commit }) {
      const res = await chatAxios.get(`/${auth.state.user.officeId}`)
      console.log(res.data)
      commit("setOfficeChatList", res.data)
    },
  },
}
