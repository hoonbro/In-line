export const landing = {
  namespaced: true,
  state: {
    alertModalList: [],
  },
  getters: {},
  mutations: {
    addAlertModalList(state, alertModalItem) {
      state.alertModalList.push({ ...alertModalItem, created: Date.now() })
      setTimeout(() => {
        state.alertModalList.shift()
      }, 5000)
    },
  },
  actions: {},
}
