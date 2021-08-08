<template>
  <router-view />
</template>

<script>
import { computed, onUnmounted } from "@vue/runtime-core"
import { useStore } from "vuex"
import { exitOffice, disconnectStomp } from "@/lib/websocket"
export default {
  setup() {
    const store = useStore()
    const user = computed(() => store.state.auth.user)
    const stompClient = computed(() => store.state.socket.stompClient)
    // 새로고침했을 때 LocalStorage => vuex
    if (localStorage.getItem("accessToken")) {
      store.commit("auth/setToken", localStorage.getItem("accessToken"))
    }
    if (localStorage.getItem("user")) {
      store.commit("auth/setUser", JSON.parse(localStorage.getItem("user")))
    }
    if (localStorage.getItem("commute")) {
      store.commit(
        "auth/setCommute",
        JSON.parse(localStorage.getItem("commute"))
      )
    }

    // 연결 끊기
    onUnmounted(() => {
      if (stompClient.value.connected === true) {
        exitOffice(stompClient.value, user.value)
        disconnectStomp()
        // store.state.socket.subscription.unsubscribe()
      }
    })
  },
}
</script>

<style lang="scss">
body {
  background: white;
  font-family: "Noto Sans KR", sans-serif;
}
</style>
