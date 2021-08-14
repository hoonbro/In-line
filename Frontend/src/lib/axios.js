import axios from "axios"

export const apiAxios = axios.create({
  baseURL: "/api/v1",
})

export const roomAxios = axios.create({
  baseURL: "/rooms",
})

// export const authAPI = axios.create({
//   baseURL: "/api/v1/users",
// })

// export const commuteAPI = axios.create({
//   baseURL: "/api/v1/commute",
// })

// export const notiAPI = axios.create({
//   baseURL: `/api/v1/notifications`,
// })

// export const todoAPI = axios.create({
//   baseURL: `/api/v1/todos`,
// })

// export const roomAPI = axios.create({
//   // Local test URL
//   // baseURL: `http://i5d207.p.ssafy.io:8997/rooms`,

//   // deploy URL
//   baseURL: `https://i5d207.p.ssafy.io:8995/rooms`,
// })

// export const officeAPI = axios.create({
//   baseURL: `/api/v1/office`,
// })

export const setAxiosConfig = AUTH_TOKEN => {
  axios.defaults.headers.common["accessToken"] = AUTH_TOKEN
  apiAxios.defaults.headers.common["accessToken"] = AUTH_TOKEN
  roomAxios.defaults.headers.common["accessToken"] = AUTH_TOKEN
}
