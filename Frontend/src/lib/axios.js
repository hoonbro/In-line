import axios from "axios"

export const apiAxios = axios.create({
  baseURL: "/api/v1",
})

export const roomAxios = axios.create({
  // baseURL: "/rooms",
  baseURL: `${process.env.VUE_APP_GROUPCALL_BASE_URL}/rooms`,
  // baseURL: "https://i5d207.p.ssafy.io:8995/rooms",
})

export const setAxiosConfig = AUTH_TOKEN => {
  axios.defaults.headers.common["accessToken"] = AUTH_TOKEN
  apiAxios.defaults.headers.common["accessToken"] = AUTH_TOKEN
  roomAxios.defaults.headers.common["accessToken"] = AUTH_TOKEN
}

export const removeAxiosConfig = () => {
  delete axios.defaults.headers.common["accessToken"]
  delete apiAxios.defaults.headers.common["accessToken"]
  delete roomAxios.defaults.headers.common["accessToken"]
}
