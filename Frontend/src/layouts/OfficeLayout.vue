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
import { computed, onMounted, ref, watchEffect } from "vue"
import { enterOffice, connectStomp } from "@/lib/websocket"
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
    const stompClient = computed(() => store.state.socket.stompClient)

    // watch & watchEffect
    // stompClient가 변경되었을 때 수행
    watchEffect(() => {
      enterOffice(stompClient.value, user.value)
    })

    onMounted(() => {
      store.dispatch("office/getMembers")
      if (!stompClient.value || stompClient.value.connected === false) {
        connectStomp(user.value.officeId)
      }
    })

    return { stompClient }
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
