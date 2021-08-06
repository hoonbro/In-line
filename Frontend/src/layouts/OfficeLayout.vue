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
    const user = computed(() => store.state.auth.user)
    const stompClient = ref(null)

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
      if (stompClient.value && stompClient.value.connected == true) {
        return
      }
      connectStomp()
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
