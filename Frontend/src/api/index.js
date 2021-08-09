import axios from "axios"

const backendAPI = axios.create({
  baseURL: "/api/v1",
  headers: {
    "Content-Type": "application/json",
  },
})

export const getTodosAPI = () => backendAPI.get("/todos")
export const toggleTodoDone = (todoId, newDone) =>
  backendAPI.put(`/todos/${todoId}`, { done: newDone })
