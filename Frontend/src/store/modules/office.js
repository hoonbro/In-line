import axios from "axios"
import { auth } from "./auth"

const notiAPI = axios.create({
  baseURL: `/api/v1/notifications`,
  headers: {
    Authorization: `${auth.state.accessToken}`,
  },
})

const todoAPI = axios.create({
  baseURL: `/api/v1/todos`,
  headers: {
    Authorization: `${auth.state.accessToken}`,
  },
})

const userAPI = axios.create({
  baseURL: `/api/v1/users`,
  headers: {
    Authorization: `${auth.state.accessToken}`,
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
    async getMembers({ commit, state }) {
      console.log(state)
      try {
        const res = await userAPI({
          params: {
            officeId: 1,
          },
        })
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
  },
}
