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
import { exitOffice, disconnectStomp } from "@/lib/websocket"
import AlertModal from "@/components/Common/AlertModal.vue"
import { setupVuexData } from "./lib/common"

export default {
  components: { AlertModal },
  setup() {
    const store = useStore()
    const user = computed(() => store.state.auth.user)
    const stompClient = computed(() => store.state.socket.stompClient)
    // AlertModal
    const alertModalList = computed(() => store.state.landing.alertModalList)

    onMounted(async () => {
      // 2. 데이터 불러오기 & 연결하기
      if (store.getters["auth/accessToken"] && store.getters["auth/officeId"]) {
        await setupVuexData()
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
  background: rgba(46, 46, 51, 0.6);
  @apply fixed -top-10 -left-10 -right-10 -bottom-10 flex items-center justify-center;
}

.alertModalContainer {
  z-index: 1000;
  @apply fixed top-6 right-6 flex flex-col gap-2;
}

/* Transition */
/* fade */
.fade-enter-active,
.fade-leave-active {
  @apply transition-all;
}

.fade-enter-from,
.fade-leave-to {
  transform: translateY(-32px);
  @apply opacity-0;
}

/* confirmModal */
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
