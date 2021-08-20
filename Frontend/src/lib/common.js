import store from "@/store"
import { connectStomp, enterOffice } from "@/lib/websocket"

export const setupVuexData = () => {
  return new Promise(async (resolve, reject) => {
    try {
      // 회원 목록 가져오기
      await store.dispatch("office/getMembers")

      // 방 목록 가져오기
      await store.dispatch("office/getRooms")

      // 소켓 연결
      await connectStomp(
        store.getters["auth/userId"],
        store.getters["auth/officeId"]
      )
      // 사무실 입장
      enterOffice(
        store.getters["socket/stompClient"],
        store.getters["auth/user"]
      )
      resolve("setupVuexData Success")
    } catch (error) {
      reject(error)
    }
  })
}
