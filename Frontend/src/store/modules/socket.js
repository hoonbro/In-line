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
    SetOfficeChatList(state, payload) {
      console.log(payload)
      state.officeChatList.push(payload)
    },
  },
  actions: {},
}
