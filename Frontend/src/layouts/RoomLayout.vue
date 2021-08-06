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
import Stomp from "webstomp-client"
import SockJS from "sockjs-client"
import { computed, onMounted, ref } from "@vue/runtime-core"
import { useStore } from "vuex"
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
    const stompClient = ref(null)
    // 마이크 끄기, 카메라 끄기 버튼 눌렀을 때, 아이콘 전환되게 만들기

    // Room에서 새로고침해서 전체 채팅 소켓이 끊기는 걸 방지
    const connectStomp = () => {
      const serverURL = "/api/v1/stomp"
      const socket = new SockJS(serverURL)
      stompClient.value = Stomp.over(socket)
      stompClient.value.connect(
        {},
        frame => {
          stompClient.value.connected = true
          store.commit("socket/setStompClient", stompClient.value)
          stompClient.value.subscribe(`/sub/${user.value.officeId}`, res => {
            const data = JSON.parse(res.body)
            if (data.type === "CHAT") {
              store.commit("socket/addOfficeChat", JSON.parse(res.body))
            } else if (data.type === "ENTER") {
            }
          })
        },
        error => {
          // 소켓 연결 실패
          alert("소켓 연결 실패!")
          stompClient.value.connected = false
          store.commit("socket/setStompClient", stompClient.value)
        }
      )
    }

    onMounted(() => {
      if (!store.state.socket.stompClient) {
        connectStomp()
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
