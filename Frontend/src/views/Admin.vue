<template>
  <h1>Admin</h1>
  <button @click="sendMessage">sendMessage</button>
</template>

<script>
import { computed } from "@vue/runtime-core"
import { useStore } from "vuex"
export default {
  name: "Admin",
  setup() {
    const store = useStore()
    const stompClient = computed(() => store.state.socket.stompClient)
    const sendMessage = () => {
      const msg = {
        userId: 999,
        userName: "김병훈",
        roomId: 123123123,
      }
      stompClient.value.send(`/notification/${53}`, JSON.stringify(msg), {})
    }
    return { sendMessage }
  },
}
</script>

<style></style>
