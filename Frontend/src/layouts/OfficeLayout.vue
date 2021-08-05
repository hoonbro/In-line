<template>
  <div class="layout">
    <MainNav />
    <main>
      <LeftAsidebar />
      <router-view class="flex-1" />
      <RightAsidebar />
    </main>
  </div>
</template>

<script>
import { useStore } from "vuex"
import { computed, onMounted, ref } from "vue"
import Stomp from "webstomp-client"
import SockJS from "sockjs-client"
import MainNav from "@/components/Nav/MainNav.vue"
import RightAsidebar from "@/components/RightAsidebar.vue"
import LeftAsidebar from "@/components/LeftAsidebar.vue"

export default {
  name: "OfficeLayout",
  components: {
    MainNav,
    RightAsidebar,
    LeftAsidebar,
  },
  setup() {
    const store = useStore()
    const officeId = computed(() => store.state.auth.user.officeId)
    const userId = computed(() => store.state.auth.user.userId)
    const userName = computed(() => store.state.auth.user.name)
    const stompClient = ref(null)

    const connectStomp = () => {
      const serverURL = "/api/v1/stomp"
      const socket = new SockJS(serverURL)
      stompClient.value = Stomp.over(socket)
      // store.commit("socket/setStompClient", Stomp.over(socket))
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      stompClient.value.connect(
        {},
        frame => {
          stompClient.value.connected = true
          store.commit("socket/setStompClient", stompClient.value)
          console.log("소켓 연결 성공", frame)
          console.log(stompClient.value)
          stompClient.value.subscribe(`/sub/${officeId.value}`, res => {
            console.log("구독으로 받은 메시지 입니다.", JSON.parse(res.body))
            // 받은 데이터를 파싱하여 전체 채팅 리스트에 넣어줍니다.
            const message = JSON.parse(res.body)
            message.send
            store.commit("socket/addOfficeChat", JSON.parse(res.body))
          })
        },
        error => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error)
          alert("소켓 연결 실패!")
          stompClient.value.connected = false
          store.commit("socket/setStompClient", stompClient.value)
        }
      )
    }

    onMounted(() => {
      connectStomp()
      store.dispatch("socket/getAllOfficeChat")
    })

    return { officeId, userId, userName }
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
