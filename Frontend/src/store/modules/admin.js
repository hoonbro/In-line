import { apiAxios } from "@/lib/axios"

export const admin = {
  namespaced: true,
  state: () => ({
    attendances: [],
    retires: [],
    members: {
      deptUserCount: [],
      officeUserCount: [],
    },
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
    members(state) {
      return state.members
    },
    memberCountOndept(state) {
      console.log(state)
      return state.members.deptUserCount
    },
    totalMemberCount(state) {
      return state.members.officeUserCount[0]
    },
  },
  actions: {
    async getAttendances({ commit }, officeId) {
      try {
        const res = await apiAxios.get("/admin/members/commute", {
          params: {
            officeId,
          },
        })
        commit("setAttendances", res.data)
        return res.data
      } catch (error) {
        console.log(error)
      }
    },
    async getRetires({ commit }, officeId) {
      try {
        const res = await apiAxios.get(`/admin/retireRate/${officeId}`)
        commit("setRetires", res.data)
      } catch (error) {
        console.log(error)
      }
    },
    async getMembers({ commit }, officeId) {
      try {
        const res = await apiAxios.get(`/office/dashboard/${officeId}`)
        commit("setMembers", res.data)
      } catch (error) {
        console.log(error)
      }
    },
    async getYears({ commit }, officeId) {
      try {
        const res = await apiAxios.get(`/admin/jobyear/${officeId}`)
        commit("setYears", res.data)
      } catch (error) {
        console.log(error)
      }
    },
    async deleteUser({ dispatch }, payload) {
      try {
        await apiAxios.put(`/admin/user/${payload.userId}`)
        // 회원 정보 업데이트
        await dispatch("office/getMembers", null, { root: true })
        // 부서 정보 업데이트
        await dispatch("getOrganization")
      } catch (error) {
        throw Error(error)
      }
    },
    async getOrganization({ rootGetters, commit }) {
      try {
        const res = await apiAxios.get(
          `/office/dashboard/${rootGetters["auth/officeId"]}`
        )
        console.log(res.data)
        commit("setMembers", res.data)
        return res.data
      } catch (error) {
        throw Error(error)
      }
    },
  },
}
