import axios from "axios"
import { auth } from "./auth"

const notiAPI = axios.create({
  baseURL: `${process.env.VUE_APP_API_BASE_URL}/notifications`,
  headers: {
    Authorization: `Bearer ${auth.state.token}`,
  },
})

const todoAPI = axios.create({
  baseURL: `${process.env.VUE_APP_API_BASE_URL}/todos`,
  headers: {
    Authorization: `Bearer ${auth.state.token}`,
  },
})

const userAPI = axios.create({
  baseURL: `${process.env.VUE_APP_API_BASE_URL}/users`,
  headers: {
    Authorization: `Bearer ${auth.state.token}`,
  },
})

const officeAPI = axios.create({
  baseURL: `/api/v1/office`,
})

// 병훈
export const office = {
  namespaced: true,
  state: {
    user: {},
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
      return officeAPI.post("", {
        data: formData,
      })
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
        const res = await todoAPI()
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
    async getMembers({ commit }) {
      try {
        const res = await userAPI()
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
