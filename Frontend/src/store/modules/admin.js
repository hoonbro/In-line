import axios from "axios"

const adminAPI = axios.create({
  baseURL: `/api/v1/admin`,
})

export const admin = {
  namespaced: true,
  state: () => ({
    attendances: [],
    retires: [],
    members: [],
    years: [],
  }),
  mutations: {
    setAttendances(state, attendances) {
      state.attendances = attendances
    },
    setRetires(state, retires) {
      state.retires = retires
    },
    setMembers(state, members) {
      state.members = members
    },
    setYears(state, years) {
      state.years = years
    },
  },
  getters: {
    user(state) {
      return state.user
    },
  },
  actions: {
    async getAttendances({ commit, rootState }, officeId) {
      try {
        const res = await adminAPI({
          method: "GET",
          url: `/members/commute`,
          params: {
            officeId,
          },
          headers: {
            accessToken: rootState.auth.accessToken,
          },
        })
        commit("setAttendances", res.data)
        return res.data
      } catch (error) {
        console.log(error)
      }
    },
    async getRetires({ commit, rootState }, officeId) {
      try {
        const res = await adminAPI({
          method: "GET",
          url: `/retireRate/${officeId}`,
          headers: {
            accessToken: rootState.auth.accessToken,
          },
        })
        commit("setRetires", res.data)
      } catch (error) {
        console.log(error)
      }
    },
    async getMembers({ commit, rootState }, officeId) {
      try {
        const res = await adminAPI({
          method: "GET",
          url: `/dashboard/${officeId}`,
          headers: {
            accessToken: rootState.auth.accessToken,
          },
        })
        commit("setMembers", res.data)
      } catch (error) {
        console.log(error)
      }
    },
    async getYears({ commit, rootState }, officeId) {
      try {
        const res = await adminAPI({
          method: "GET",
          url: `/jobyear/${officeId}`,
          headers: {
            accessToken: rootState.auth.accessToken,
          },
        })
        commit("setYears", res.data)
      } catch (error) {
        console.log(error)
      }
    },
  },
}
