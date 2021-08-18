import Stomp from "webstomp-client"
import SockJS from "sockjs-client"
import store from "@/store/index"

let stompClient

export const connectStomp = (userId, officeId) => {
  const serverURL = "/api/v1/stomp"
  const socket = new SockJS(serverURL)
  stompClient = Stomp.over(socket)
  return new Promise((res, rej) => {
    stompClient.connect(
      {},
      frame => {
        stompClient.connected = true
        store.commit("socket/setStompClient", stompClient)
        const officeSubscription = stompClient.subscribe(
          `/sub/${officeId}`,
          res => {
            console.group("subscription")
            const data = JSON.parse(res.body)
            console.log(data)
            if (data.type === "CHAT") {
              store.commit("socket/addOfficeChat", data.chatDto)
            } else if (data.type === "userUpdate") {
              console.log(data.members)
              store.commit("office/updateConnectionOfMembers", data.members)
              setTimeout(() => {
                store.dispatch("office/getRooms")
              }, 1000)
            }
            console.groupEnd()
          }
        )
        const mySubscription = stompClient.subscribe(
          `/queue/${userId}`,
          res => {
            console.log(res)
          }
        )
        // store.commit("socket/setSubscription", officeSubscription)
        res("성공")
      },
      error => {
        store.commit("landing/addAlertModalList", {
          type: "error",
          message: "소켓 연결이 끊겼어요.",
        })
        stompClient.connected = false
        store.commit("socket/setStompClient", stompClient)
        rej("망했어요")
      }
    )
  })
}

export const disconnectStomp = () => {
  return new Promise((resolve, reject) => {
    console.group("disconnectStomp")
    store.state.socket.stompClient.disconnect(() => {
      console.log("stomp 연결을 해제합니다.")
      store.commit("socket/setStompClient", null)
    })
    console.groupEnd()
    resolve(true)
  })
}

export const enterOffice = (stompClient, user) => {
  console.group("enterOffice")
  console.log({ ...stompClient })
  if (stompClient && stompClient.connected) {
    console.log(stompClient.connected)
    console.log(user)
    const msg = {
      type: "ENTER",
      officeId: user.officeId,
      userId: user.userId,
      userName: user.name,
      roomId: user.roomId,
    }
    console.log(msg)
    stompClient.send(`/pub/${user.officeId}`, JSON.stringify(msg), {})
  }
  console.groupEnd()
}

export const exitOffice = (stompClient, user) => {
  console.group("exitOffice")
  if (stompClient && stompClient.connected) {
    const msg = {
      type: "EXIT",
      officeId: user.officeId,
      userId: user.userId,
      userName: user.userName,
    }
    console.log("ExitMessage 전달")
    stompClient.send(`/pub/${user.officeId}`, JSON.stringify(msg), {})
  }
  console.groupEnd()
}

export const moveRoom = roomId => {
  const user = store.getters["auth/user"]
  const stompClient = store.getters["socket/stompClient"]
  console.group("WS: MOVE")
  console.log(roomId)
  if (stompClient && stompClient.connected) {
    const msg = {
      type: "MOVE",
      officeId: user.officeId,
      userId: user.userId,
      userName: user.name,
      roomId: +roomId,
    }
    console.log("MOVE 이벤트 전달")
    console.log(msg)
    stompClient.send(`/pub/${user.officeId}`, JSON.stringify(msg), {})
  }
  console.groupEnd()
}
