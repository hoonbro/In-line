import { apiAxios } from "@/lib/axios"

export const admin = {
  namespaced: true,
  state: () => ({
    attendances: [],
    retires: [],
    years: [],
  }),
  mutations: {
    setAttendances(state, attendances) {
      state.attendances = attendances
    },
    setRetires(state, retires) {
      state.retires = retires
    },

    setYears(state, years) {
      state.years = years
    },
  },
  getters: {},
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
    async getYears({ commit }, officeId) {
      try {
        const res = await apiAxios.get(`/admin/jobyear/${officeId}`)
        commit("setYears", res.data)
      } catch (error) {
        console.log(error)
      }
    },
    async deleteUser({ dispatch, commit }, payload) {
      try {
        await apiAxios.put(`/admin/user/${payload.userId}`)
        // 회원 정보 업데이트
        commit("office/deleteMember", null, { root: true })
        // await dispatch("office/getMembers", null, { root: true })
        // 부서 정보 업데이트
        await dispatch("office/getOrganization", null, { root: true })
      } catch (error) {
        throw Error(error)
      }
    },
  },
}
