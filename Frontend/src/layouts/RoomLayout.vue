<template>
  <div class="layout">
    <MainNav />
    <main>
      <!-- <Room /> -->
      <router-view class="flex-1" />
      <RoomRightAsidebar />
    </main>
  </div>
</template>

<script>
import { computed, onMounted, ref, watchEffect } from "@vue/runtime-core"
import { useStore } from "vuex"
import { enterOffice, connectStomp } from "@/lib/websocket"
import MainNav from "@/components/Nav/MainNav.vue"
import RoomRightAsidebar from "@/components/RightAsidebar.vue"
import Room from "@/views/Room.vue"

export default {
  name: "RoomLayout",
  components: {
    MainNav,
    RoomRightAsidebar,
    Room,
  },
  setup() {
    const store = useStore()
    const user = computed(() => store.state.auth.user)
    const stompClient = computed(() => store.state.socket.stompClient)

    // 마이크 끄기, 카메라 끄기 버튼 눌렀을 때, 아이콘 전환되게 만들기

    // 첫 로그인 시 Room으로 바로 접근하는 경우, 접속을 확인하기 위한 용도

    // Room에서 새로고침해서 전체 채팅 소켓이 끊기는 걸 방지
    // const connectStomp = () => {
    //   const serverURL = "/api/v1/stomp"
    //   const socket = new SockJS(serverURL)
    //   stompClient.value = Stomp.over(socket)
    //   stompClient.value.connect(
    //     {},
    //     frame => {
    //       stompClient.value.connected = true
    //       store.commit("socket/setStompClient", stompClient.value)
    //       stompClient.value.subscribe(`/sub/${user.value.officeId}`, res => {
    //         const data = JSON.parse(res.body)
    //         if (data.type === "CHAT") {
    //           store.commit("socket/addOfficeChat", JSON.parse(res.body))
    //         } else if (data.type === "ENTER") {
    //           console.log(res.body)
    //         }
    //       })
    //     },
    //     error => {
    //       // 소켓 연결 실패
    //       alert("소켓 연결 실패!")
    //       stompClient.value.connected = false
    //       store.commit("socket/setStompClient", stompClient.value)
    //     }
    //   )
    // }

    watchEffect(() => {
      enterOffice(stompClient.value, user.value)
    })

    onMounted(() => {
      if (!store.state.socket.stompClient) {
        connectStomp(user.value.officeId)
      }
    })

    return {}
  },
}
</script>

<style scoped lang="scss">
.layout {
  @apply h-screen flex flex-col overflow-hidden;

  main {
    @apply h-full overflow-hidden flex;
  }
}
</style>
