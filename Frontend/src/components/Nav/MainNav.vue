<template>
  <nav>
    <router-link class="logo" :to="{ name: 'Home' }">인-라인</router-link>
    <div class="links">
      <router-link :to="{ name: 'Office' }">홈</router-link>
      <router-link :to="{ name: 'Members' }">구성원</router-link>
      <router-link :to="{ name: 'Admin' }">관리자</router-link>
    </div>
    <button class="logout" @click="logout">
      로그아웃
    </button>
  </nav>
</template>

<script>
import { useRouter } from "vue-router"
import { useStore } from "vuex"
import { disconnectStomp, exitOffice } from "@/lib/websocket"
import { computed } from "@vue/runtime-core"

export default {
  name: "MainNav",
  setup() {
    const router = useRouter()
    const store = useStore()
    const stompClient = computed(() => store.state.socket.stompClient)
    const user = computed(() => store.state.auth.user)

    const logout = async () => {
      const yes = confirm("로그아웃 하시겠습니까?")
      if (yes) {
        store.commit("auth/setToken", "")
        exitOffice(stompClient.value, user.value)
        disconnectStomp()
        router.push({ name: "Home" })
      }
    }

    return {
      logout,
    }
  },
}
</script>

<style lang="scss" scoped>
nav {
  height: 60px;
  @apply px-4 flex items-center shadow-md relative z-50 flex-shrink-0;

  .logo {
    @apply text-2xl font-bold;
  }

  .links {
    @apply flex self-center mx-auto;

    a {
      @apply py-2 px-4 font-bold rounded inline-block mr-6;

      &.router-link-exact-active {
        @apply text-blue-900 bg-blue-200;
      }

      &:last-child {
        @apply mr-0;
      }
    }
  }

  .logout {
    @apply py-2 px-6 font-bold;
  }
}
</style>
