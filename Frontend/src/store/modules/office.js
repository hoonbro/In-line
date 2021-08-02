import axios from "axios"

const notiAPI = axios.create({
  baseURL: "http://localhost:3000/notifications",
  headers: {
    Authorization: `Bearer jwt`,
  },
})

const todoAPI = axios.create({
  baseURL: "http://localhost:3000/todos",
  headers: {
    Authorization: `Bearer jwt`,
  },
})

const userAPI = axios.create({
  baseURL: "http://localhost:3000/users",
  headers: {
    Authorization: `Bearer jwt`,
  },
})

// 병훈
export const office = {
  namespaced: true,
  state: {
    user: {
      address: null,
      auth: "ROLE_ADMIN",
      deptId: 102,
      email: "asdf@asdf.asdf",
      jobId: 1008,
      joinDate: "2021-07-29T04:53:56.000+00:00",
      login: false,
      name: "김병훈",
      nickName: null,
      officeId: 16,
      password: "$2a$10$aZSj5sCJIZjUbsIy69AbauBPGuuDrvN2TGGoTmS4kSeNxloshm5Be",
      phone: "1231231234",
      profileImage: null,
      roomId: 1,
      userId: 5,
    },
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
