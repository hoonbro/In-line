<template>
  <router-view />
  <transition-group class="alertModalContainer" tag="div" name="list">
    <AlertModal
      v-for="modal in alertModalList"
      :key="modal.created"
      :message="modal.message"
      :type="modal.type"
    />
  </transition-group>
</template>

<script>
import { computed, onMounted, onUnmounted } from "@vue/runtime-core"
import { useStore } from "vuex"
import {
  connectStomp,
  enterOffice,
  exitOffice,
  disconnectStomp,
} from "@/lib/websocket"
import AlertModal from "@/components/Common/AlertModal.vue"

export default {
  components: { AlertModal },
  setup() {
    const store = useStore()
    const user = computed(() => store.state.auth.user)
    const stompClient = computed(() => store.state.socket.stompClient)
    // AlertModal
    const alertModalList = computed(() => store.state.landing.alertModalList)

    onMounted(async () => {
      // 새로고침했을 때
      // 1. LocalStorage => vuex
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
      // 2. 데이터 불러오기 & 연결하기
      if (store.getters["auth/accessToken"] && store.getters["auth/officeId"]) {
        await store.dispatch("office/getMembers")

        if (!stompClient.value || stompClient.value.connected === false) {
          await connectStomp(store.getters["auth/officeId"])
          enterOffice(stompClient.value, user.value)
        }
      }
    })

    // 연결 끊기
    onUnmounted(() => {
      if (stompClient.value.connected === true) {
        exitOffice(stompClient.value, user.value)
        disconnectStomp()
      }
    })

    return {
      alertModalList,
    }
  },
}
</script>

<style lang="scss">
body {
  background: white;
  font-family: "Noto Sans KR", sans-serif;
  @apply text-gray-900;
}

.backdrop {
  z-index: 999;
  @apply fixed inset-0 flex justify-center;
}

.alertModalContainer {
  z-index: 1000;
  @apply fixed top-6 right-6 flex flex-col gap-2;
}

.list-enter-active,
.list-leave-active,
.list-move {
  @apply transition-all duration-1000;
}

.list-leave-active {
  @apply absolute;
}

.list-enter-from {
  transform: translateY(24px);
  @apply opacity-0;
}

.list-leave-to {
  transform: translateX(80px);
  @apply opacity-0;
}
</style>
