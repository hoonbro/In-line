<template>
  <router-view />
</template>

<script>
import { useStore } from "vuex"
export default {
  setup() {
    const store = useStore()
    if (localStorage.getItem("accessToken")) {
      store.commit("auth/setToken", localStorage.getItem("accessToken"))
    }
    if (localStorage.getItem("user")) {
      store.commit("auth/setUser", JSON.parse(localStorage.getItem("user")))
    }
  },
}
</script>

<style lang="scss">
body {
  background: white;
  font-family: "Noto Sans KR", sans-serif;
}
</style>
