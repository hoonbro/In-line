import Stomp from "webstomp-client"
import SockJS from "sockjs-client"
import store from "@/store/index"

export const connectStomp = officeId => {
  const serverURL = "/api/v1/stomp"
  const socket = new SockJS(serverURL)
  let stompClient = Stomp.over(socket)
  let subscription
  stompClient.connect(
    {},
    frame => {
      stompClient.connected = true
      store.commit("socket/setStompClient", stompClient)
      subscription = stompClient.subscribe(`/sub/${officeId}`, res => {
        console.group("subscription")
        const data = JSON.parse(res.body)
        if (data.type === "CHAT") {
          store.commit("socket/addOfficeChat", data.chatDto)
        } else if (data.type === "ENTER" || data.type === "EXIT") {
          console.log(data.members)
          store.commit("office/updateConnectionOfMembers", data.members)
        }
        console.groupEnd()
      })
      store.commit("socket/setSubscription", subscription)
    },
    error => {
      alert("소켓 연결 실패!")
      stompClient.connected = false
      store.commit("socket/setStompClient", stompClient)
    }
  )
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
    const msg = {
      type: "ENTER",
      officeId: user.officeId,
      userId: user.userId,
      userName: user.name,
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
