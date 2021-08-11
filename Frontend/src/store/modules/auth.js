import axios from "axios"

const authAPI = axios.create({
  baseURL: "/api/v1/users",
})

const commuteAPI = axios.create({
  baseURL: "/api/v1/commute",
})

export const auth = {
  namespaced: true,
  state: {
    user: null,
    accessToken: "",
    commute: null,
    // 임시비밀번호 메일이 발송되면 이 state가 true로 바뀜
    // 비밀번호를 변경하면 이 state가 false로 바뀜
    // 이 state가 true이면 로그인 후, 비밀번호 변경 페이지로 이동
    shouldChangePassword: false,
  },
  mutations: {
    setToken(state, accessToken) {
      state.accessToken = accessToken
      localStorage.setItem("accessToken", accessToken)
    },
    setUser(state, userData) {
      state.user = userData
      localStorage.setItem("user", JSON.stringify(userData))
    },
    setCommute(state, commuteData) {
      state.commute = commuteData
      localStorage.setItem("commute", JSON.stringify(commuteData))
    },
    setShouldChangePassword(state, data) {
      // data : boolean type
      state.shouldChangePassword = data
    },
  },
  actions: {
    async login({ commit }, formData) {
      try {
        const res = await authAPI.post("/login", formData)
        commit("setToken", res.data.accessToken)
        commit("setUser", res.data.userDto)
        commit("setCommute", res.data.commuteEntity)
      } catch (error) {
        const { status } = error.response
        switch (status) {
          case 400: {
            throw Error("이 이메일로 가입한 계정을 찾을 수 없어요.")
          }
          case 401: {
            throw Error("입력한 비밀번호를 다시 확인해주세요.")
          }
          case 500: {
            throw Error("서버에 문제가 생긴 것 같아요..!")
          }
          default: {
            throw Error("무슨 문제가 생긴 것 같은데, 저도 잘 모르겠네요 0ㅅ0")
          }
        }
      }
    },
    async resetPassword({ commit }, email) {
      try {
        await authAPI.put("/reset-password", email)
        commit("setShouldChangePassword", true)
      } catch (error) {
        console.log(error)
        console.log(error.response.status)
        throw Error("🥲 이메일 전송에 실패했습니다.")
      }
    },
    async changePassword({ commit, state }, passwordForm) {
      try {
        console.log(passwordForm)
        const res = await axios({
          method: "put",
          url: "/api/v1/users/change-password",
          data: passwordForm,
          headers: {
            accessToken: state.accessToken,
          },
        })
        console.log(res)
        commit("setShouldChangePassword", false)
      } catch (error) {
        const { status } = error.response
        switch (status) {
          case 401: {
            throw Error("🤨 이전 비밀번호를 다시 확인해주세요.")
          }
          default: {
            throw Error("🥲 무슨 문제가 생긴 것 같은데, 저도 잘 모르겠네요 0ㅅ0")
          }
        }
      }
    },
    async commuteIn({ commit, state }) {
      try {
        const res = await commuteAPI({
          method: "PUT",
          url: `/${state.commute.commuteId}/in`,
          headers: {
            accessToken: state.accessToken,
          },
        })
        console.log(res)
        const commuteData = {
          ...state.commute,
          comeIn: res.data,
        }
        commit("setCommute", commuteData)
      } catch (error) {
        throw Error(error)
      }
    },
    async commuteOut({ commit, state }) {
      try {
        const res = await commuteAPI({
          method: "PUT",
          url: `/${state.commute.commuteId}/out`,
          headers: {
            accessToken: state.accessToken,
          },
        })
        const commuteData = {
          ...state.commute,
          comeOut: res.data,
        }
        commit("setCommute", commuteData)
      } catch (error) {
        throw Error(error)
      }
    },
    async updateProfileImage({ state }, formData) {
      try {
        const { data: newProfileImg } = await authAPI({
          method: "PUT",
          url: "/profile",
          data: formData,
          headers: {
            accessToken: state.accessToken,
            "Content-Type": "multipart/form-data",
          },
        })
        console.log(newProfileImg)
        return newProfileImg
      } catch (error) {
        console.log(error)
        throw Error("이미지 업로드에 실패했습니다.")
      }
    },
    async updateProfile({ commit, state }, { userId, form }) {
      try {
        const res = await authAPI({
          method: "PUT",
          url: `/${userId}`,
          data: form,
          headers: {
            accessToken: state.accessToken,
          },
        })
        return res.data
      } catch (error) {
        throw Error("프로필 수정에 실패했어요.")
      }
    },
  },
  getters: {
    isAdmin(state) {
      return state.user.auth === "ROLE_ADMIN"
    },
    officeId(state) {
      return state.user.officeId
    },
    accessToken(state) {
      return state.accessToken
    },
    userId(state) {
      return state.user.userId
    },
    user(state) {
      return state.user
    },
  },
}
