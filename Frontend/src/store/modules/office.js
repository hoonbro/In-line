import axios from "axios"

const notiAPI = axios.create({
  baseURL: `/api/v1/notifications`,
})

const todoAPI = axios.create({
  baseURL: `/api/v1/todos`,
})

const userAPI = axios.create({
  baseURL: `/api/v1/users`,
})

const roomAPI = axios.create({
  // Local test URL
  // baseURL: `http://i5d207.p.ssafy.io:8997/rooms`,

  // deploy URL
  // 로컬에서는 CORS 에러 발생
  baseURL: `https://i5d207.p.ssafy.io:8995/rooms`,
})

const officeAPI = axios.create({
  baseURL: `/api/v1/office`,
})

// 병훈
export const office = {
  namespaced: true,
  state: {
    notifications: [],
    todos: [],
    members: [],
    rooms: [],
  },
  mutations: {
    setNotifications(state, notis) {
      state.notifications = notis
    },
    setTodos(state, todos) {
      state.todos = todos
    },
    setMembers(state, members) {
      state.members = members
    },
    setRooms(state, rooms) {
      state.rooms = rooms
    },
  },
  getters: {
    user(state) {
      return state.user
    },
  },
  actions: {
    async registerOffice({ rootState }, formData) {
      return officeAPI({
        method: "POST",
        url: "",
        data: formData,
        headers: {
          accessToken: rootState.auth.accessToken,
        },
      })
    },
    async getNotifications({ commit, rootState }) {
      try {
        const res = await notiAPI({
          method: "GET",
          url: "",
          headers: {
            accessToken: rootState.auth.accessToken,
          },
        })
        commit("setNotifications", res.data)
      } catch (error) {
        console.log(error)
      }
    },
    async deleteNotification({ commit, state, rootState }, notiId) {
      try {
        await notiAPI({
          method: "DELETE",
          url: `/${notiId}`,
          headers: {
            accessToken: rootState.auth.accessToken,
          },
        })
        const notis = state.notifications.filter(noti => noti.id !== notiId)
        commit("setNotifications", notis)
      } catch (error) {
        console.log(error)
      }
    },
    async getTodos({ commit, rootState }) {
      try {
        const res = await todoAPI({
          method: "",
          url: "",
          params: {
            userId: rootState.auth.user.userId,
          },
          headers: {
            accessToken: rootState.auth.accessToken,
          },
        })
        commit("setTodos", res.data)
      } catch (error) {
        console.log(error)
      }
    },
    async createTodo({ commit, state, rootState }, todoData) {
      try {
        const res = await todoAPI({
          method: "POST",
          data: {
            officeId: rootState.auth.user.officeId,
            userId: rootState.auth.user.userId,
            ...todoData,
          },
          headers: {
            accessToken: rootState.auth.accessToken,
          },
        })
        const todos = [...state.todos]
        todos.push(res.data)
        commit("setTodos", todos)
      } catch (error) {
        console.log(error)
      }
    },
    async deleteTodo({ commit, state, rootState }, todoId) {
      try {
        await todoAPI({
          method: "DELETE",
          url: `/${todoId}`,
          headers: {
            accessToken: rootState.auth.accessToken,
          },
        })
        const todos = state.todos.filter(todo => todo.id !== todoId)
        commit("setTodos", todos)
      } catch (error) {
        console.log(error)
      }
    },
    async toggleTodoDone({ commit, state, rootState }, todoId) {
      console.log(todoId)
      await todoAPI({
        method: "PUT",
        url: `/${todoId}`,
        headers: {
          accessToken: rootState.auth.accessToken,
        },
      })
      const todos = state.todos.map(todo => {
        if (todo.id === todoId) {
          todo.done = !todo.done
        }
        return todo
      })
      commit("setTodos", todos)
    },
    async getMembers({ commit, rootState }) {
      try {
        const res = await userAPI({
          params: {
            officeId: rootState.auth.user.officeId,
          },
          headers: {
            accessToken: rootState.auth.accessToken,
          },
        })
        commit("setMembers", res.data)
      } catch (error) {
        console.log(error)
      }
    },
    getMember({ rootState }, userId) {
      return userAPI({
        url: `/${userId}`,
        headers: {
          accessToken: rootState.auth.accessToken,
        },
      })
    },

    async getRooms({ commit, rootState }, officeId) {
      try {
        const res = await roomAPI({
          method: "GET",
          params: {
            officeId,
          },
          headers: {
            accessToken: rootState.auth.accessToken,
          },
        })
        commit("setRooms", res.data)
      } catch (error) {
        console.dir(error)
      }
    },
    // --------------------------------------------------------------------------------
    async createRoom({ commit, state, rootState }, roomData) {
      try {
        const res = await roomAPI({
          method: "POST",
          url: ``,
          data: roomData,
          headers: {
            accessToken: rootState.auth.accessToken,
          },
        })
        const rooms = [...state.rooms]
        rooms.push(res.data)
        commit("setRooms", rooms)
      } catch (error) {
        console.log(error)
      }
    },
    // --------------------------------------------------------------------------------
    async editRoom({ commit, state, rootState }, { room, roomId }) {
      try {
        const res = await roomAPI({
          method: "PUT",
          url: `/${roomId}`,
          data: { roomName: room.name },
          headers: {
            accessToken: rootState.auth.accessToken,
          },
        })
        // 얕은 카피
        const rooms = [...state.rooms]

        rooms.forEach(item => {
          if (item.roomId === roomId) {
            item.roomName = res.data.roomName
          }
        })
        commit("setRooms", rooms)
        // alert("회의실 수정이 완료됐습니다.")
      } catch (error) {
        console.log(error)
      }
    },

    async deleteRoom({ commit, state, rootState }, roomId) {
      try {
        await roomAPI({
          method: "DELETE",
          url: `/${roomId}`,
          headers: {
            accessToken: rootState.auth.accessToken,
          },
        })
        // DB에서는 삭제됐으나 front에서는 삭제가 안된 상태로 렌더링 되므로
        // filter를 이용해서 렌더링에서 제외시켜버린다
        console.log(`${roomId}번 회의실이 삭제됨니덩`)
        const rooms = state.rooms.filter(room => room.roomId !== roomId)
        commit("setRooms", rooms)
      } catch (error) {
        console.log(error)
      }
    },
  },
}
