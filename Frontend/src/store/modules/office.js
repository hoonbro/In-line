import axios from "axios"
import { auth } from "./auth"

const notiAPI = axios.create({
  baseURL: `/api/v1/notifications`,
  headers: {
    accessToken: auth.state.accessToken,
  },
})

const todoAPI = axios.create({
  baseURL: `/api/v1/todos`,
  headers: {
    accessToken: auth.state.accessToken,
  },
})

const userAPI = axios.create({
  baseURL: `/api/v1/users`,
  headers: {
    accessToken: auth.state.accessToken,
  },
})

const roomAPI = axios.create({
  baseURL: `${process.env.VUE_APP_API_BASE_URL}/rooms`,
  headers: {
    accessToken: `Bearer jwt`,
  },
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
    async registerOffice(context, formData) {
      return officeAPI.post("", formData)
    },
    async getNotifications({ commit }) {
      try {
        const res = await notiAPI()
        commit("setNotifications", res.data)
      } catch (error) {
        console.log(error)
      }
    },
    async deleteNotification({ commit, state }, notiId) {
      try {
        await notiAPI({
          url: `/${notiId}`,
          method: "DELETE",
        })
        const notis = state.notifications.filter(noti => noti.id !== notiId)
        commit("setNotifications", notis)
      } catch (error) {
        console.log(error)
      }
    },
    async getTodos({ commit }) {
      try {
        const res = await todoAPI({
          params: {
            userId: auth.state.user.userId,
          },
        })
        commit("setTodos", res.data)
      } catch (error) {
        console.log(error)
      }
    },
    async createTodo({ commit, state }, todoData) {
      try {
        const res = await todoAPI({
          method: "POST",
          data: todoData,
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
        await todoAPI({
          url: `/${todoId}`,
          method: "DELETE",
        })
        const todos = state.todos.filter(todo => todo.id !== todoId)
        commit("setTodos", todos)
      } catch (error) {
        console.log(error)
      }
    },
    async toggleTodoDone({ commit, state }, { todoId, currentDone }) {
      await todoAPI({
        url: `/${todoId}`,
        method: "PATCH",
        data: {
          done: !currentDone,
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
      console.log(rootState)
      try {
        const res = await userAPI({
          params: {
            officeId: rootState.auth.user.officeId,
          },
        })
        console.log(res)
        commit("setMembers", res.data)
      } catch (error) {
        console.log(error)
      }
    },
    getMember(context, userId) {
      return userAPI({
        url: `/${userId}`,
      })
    },
    // Rooms
    async getRooms({ commit }) {
      try {
        const res = await roomAPI()
        commit("setRooms", res.data)
      } catch (error) {
        console.log(error)
      }
    },
    async createRoom({ commit, state }, roomData) {
      try {
        const res = await roomAPI({
          method: "POST",
          data: roomData,
        })
        const rooms = [...state.rooms]
        rooms.push(res.data)
        commit("setRooms", rooms)
      } catch (error) {
        console.log(error)
      }
    },

    async editRoom({ commit, state }, { room, roomId }) {
      try {
        const res = await roomAPI({
          url: `/${roomId}`,
          method: "PUT",
          data: { name: room.name },
        })
        // 얕은 카피
        const rooms = [...state.rooms]

        rooms.forEach(item => {
          if (item.id === roomId) {
            item.name = room.name
          }
        })
        commit("setRooms", rooms)
        // alert("회의실 수정이 완료됐습니다.")
      } catch (error) {
        console.log(error)
      }
    },

    async deleteRoom({ commit, state }, roomId) {
      try {
        await roomAPI({
          url: `/${roomId}`,
          method: "DELETE",
        })
        // DB에서는 삭제됐으나 front에서는 삭제가 안된 상태로 렌더링 되므로
        // filter를 이용해서 렌더링에서 제외시켜버린다
        console.log(`${roomId}번 회의실이 삭제됨니덩`)
        const rooms = state.rooms.filter(room => room.id !== roomId)
        commit("setRooms", rooms)
      } catch (error) {
        console.log(error)
      }
    },
  },
}
