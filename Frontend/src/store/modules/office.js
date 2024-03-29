import { apiAxios, roomAxios } from "@/lib/axios"

export const office = {
  namespaced: true,
  state: {
    notifications: [],
    todos: [],
    members: [],
    rooms: [],
    depts: [],
    jobs: [],
    organization: {
      deptUserCount: [],
      officeUserCount: [],
    },
  },
  mutations: {
    setNotifications(state, notis) {
      state.notifications = notis
    },
    setTodos(state, todos) {
      state.todos = todos
    },
    setMembers(state, members) {
      state.members = members.map(member => {
        return { ...member, connected: false }
      })
    },
    updateMember(state, newMember) {
      state.members.forEach(member => {
        if (member.userId === newMember.userId) {
          member = { ...member, ...newMember }
        }
      })
    },
    deleteMember(state, memberId) {
      state.members = state.members.filter(member => member.userId !== memberId)
    },
    updateProfileOfMembers(state, newUser) {
      state.members = state.members.map(member => {
        if (member.userId === newUser.userId) {
          return {
            ...member,
            ...newUser,
          }
        }
        return member
      })
    },
    updateMemberProfileImage(state, { userId, newProfileImage }) {
      state.members.forEach(member => {
        if (member.userId === userId) {
          member.profileImage = newProfileImage
        }
      })
    },
    updateConnectionOfMembers(state, targetMembers) {
      const targetMemberIdList = Object.keys(targetMembers).map(
        memberId => +memberId
      )
      state.members.forEach(member => {
        if (targetMemberIdList.includes(member.userId)) {
          member.connected = true
          member.roomId = targetMembers[`${member.userId}`].roomId
        } else {
          member.connected = false
        }
      })
    },
    setRooms(state, rooms) {
      state.rooms = rooms
    },
    addRoom(state, room) {
      state.rooms.push(room)
    },
    updateRoom(state, { roomId, roomName }) {
      state.rooms.forEach(room => {
        if (room.roomId === roomId) {
          room.roomName = roomName
          console.log(room)
        }
      })
    },
    removeRoom(state, roomId) {
      state.rooms = state.rooms.filter(room => room.roomId !== roomId)
    },
    setDepts(state, depts) {
      state.depts = depts
    },
    setJobs(state, jobs) {
      state.jobs = jobs
    },
    setOrganization(state, payload) {
      state.organization = payload
    },
  },
  getters: {
    lobbyId(state) {
      if (!state.rooms) return
      return state.rooms[0].roomId
    },
    user(state) {
      return state.user
    },
    sortedMembersByOnline(state) {
      const members = [...state.members]
      return members.sort((a, b) => {
        return a.connected === b.connected ? 0 : a.connected ? -1 : 1
      })
    },
    sortedTodosByDone(state) {
      const todos = [...state.todos]
      return todos.sort((todo1, todo2) => {
        return todo1.done === todo2.done
          ? todo1.day > todo2.day
            ? -1
            : 1
          : todo1.done
          ? 1
          : -1
      })
    },
    memberCountOndept(state) {
      return state.organization.deptUserCount
    },
    totalMemberCount(state) {
      return state.organization.officeUserCount[0]
    },
  },
  actions: {
    async getDepts({ commit }) {
      try {
        const res = await apiAxios.get(`/office/depts`)
        commit("setDepts", res.data)
        return res.data
      } catch (error) {
        console.log(error)
        throw Error("부서 목록을 불러오는 데 실패했습니다.")
      }
    },
    async getJobs({ commit }) {
      try {
        const res = await apiAxios.get(`/office/jobs`)
        commit("setJobs", res.data)
        return res.data
      } catch (error) {
        console.log(error)
        throw Error("역할 목록을 불러오는 데 실패했습니다.")
      }
    },
    async registerOffice(context, formData) {
      try {
        const res = await apiAxios.post("/office", formData)
        return res
      } catch (error) {
        console.log("error:", error)
        throw Error("회사등록 실패")
      }
    },
    // async getNotifications({ commit, rootState }) {
    //   try {
    //     const res = await notiAPI({
    //       method: "GET",
    //       url: "",
    //       headers: {
    //         accessToken: rootState.auth.accessToken,
    //       },
    //     })
    //     commit("setNotifications", res.data)
    //   } catch (error) {
    //     console.log(error)
    //   }
    // },
    // async deleteNotification({ commit, state, rootState }, notiId) {
    //   try {
    //     await notiAPI({
    //       method: "DELETE",
    //       url: `/${notiId}`,
    //       headers: {
    //         accessToken: rootState.auth.accessToken,
    //       },
    //     })
    //     const notis = state.notifications.filter(noti => noti.notiId !== notiId)
    //     commit("setNotifications", notis)
    //   } catch (error) {
    //     console.log(error)
    //   }
    // },
    async getTodos({ commit, rootState }, userId) {
      try {
        const res = await apiAxios.get("/todos", {
          params: {
            userId,
          },
        })
        if (rootState.auth.user.userId === userId) {
          commit("setTodos", res.data)
        }
        return res.data
      } catch (error) {
        console.log(error)
      }
    },
    async createTodo({ commit, state, rootState }, todoData) {
      try {
        const res = await apiAxios.post("/todos", {
          officeId: rootState.auth.user.officeId,
          userId: rootState.auth.user.userId,
          ...todoData,
        })
        const todos = [...state.todos]
        todos.push(res.data)
        commit("setTodos", todos)
      } catch (error) {
        console.log(error)
      }
    },
    async deleteTodo({ commit, state }, todoId) {
      try {
        await apiAxios.delete(`/todos/${todoId}`)
        const todos = state.todos.filter(todo => todo.todoId !== todoId)
        commit("setTodos", todos)
      } catch (error) {
        console.log(error)
      }
    },
    async toggleTodoDone({ commit, state }, todoId) {
      await apiAxios.put(`/todos/${todoId}`)
      const todos = state.todos.map(todo => {
        if (todo.todoId === todoId) {
          todo.done = !todo.done
        }
        return todo
      })
      commit("setTodos", todos)
    },
    async getMembers({ commit, rootState }) {
      try {
        const res = await apiAxios.get(`/users`, {
          params: {
            officeId: rootState.auth.user.officeId,
          },
        })
        commit("setMembers", res.data)
      } catch (error) {
        console.log(error)
        throw Error(error)
      }
    },
    getMember(context, userId) {
      return apiAxios.get(`/users/${userId}`)
    },
    async getRooms({ commit, rootState }) {
      try {
        const res = await roomAxios.get("", {
          params: {
            officeId: rootState.auth.user.officeId,
          },
        })
        commit("setRooms", res.data)
      } catch (error) {
        console.log(error)
      }
    },
    // --------------------------------------------------------------------------------
    async createRoom({ commit }, roomData) {
      try {
        const res = await roomAxios.post("", roomData)
        const room = res.data
        commit("addRoom", room)
      } catch (error) {
        throw Error("❌ 방 생성에 실패했습니다.")
      }
    },
    // --------------------------------------------------------------------------------
    async editRoom({ commit }, { room, roomId }) {
      try {
        const res = await roomAxios.put(`/${roomId}`, {
          roomName: room.name,
        })
        commit("updateRoom", {
          roomId: res.data.roomId,
          roomName: res.data.roomName,
        })
      } catch (error) {
        throw Error("회의실을 수정하다 문제가 생겼어요.")
      }
    },
    async deleteRoom({ commit }, roomId) {
      try {
        await roomAxios.delete(`/${roomId}`)
        // DB에서는 삭제됐으나 front에서는 삭제가 안된 상태로 렌더링 되므로
        // filter를 이용해서 렌더링에서 제외시켜버린다
        commit("removeRoom", roomId)
      } catch (error) {
        throw Error("회의실을 삭제하던 중 문제가 발생했어요.")
      }
    },
    async getOrganization({ rootGetters, commit }) {
      try {
        const res = await apiAxios.get(
          `/office/dashboard/${rootGetters["auth/officeId"]}`
        )
        commit("setOrganization", res.data)
        return res.data
      } catch (error) {
        throw Error(error)
      }
    },
  },
}
