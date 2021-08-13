<template>
  <nav>
    <router-link class="logo" :to="{ name: 'Home' }">인-라인</router-link>
    <div class="links">
      <router-link :to="{ name: 'Office' }">홈</router-link>
      <router-link :to="{ name: 'Members' }">구성원</router-link>
      <router-link :to="{ name: 'Admin' }" v-if="isAdmin">관리자</router-link>
    </div>
    <button class="logout" @click="logout">
      로그아웃
    </button>
  </nav>
  <ConfirmModal
    ref="modalEl"
    :content="['로그아웃 하시겠습니까?']"
    :confirmButton="'네'"
    :cancelButton="'아니요'"
  />
</template>

<script>
import { useRouter } from "vue-router"
import { useStore } from "vuex"
import { disconnectStomp, exitOffice } from "@/lib/websocket"
import { computed, ref } from "@vue/runtime-core"

export default {
  name: "MainNav",
  setup() {
    const router = useRouter()
    const store = useStore()
    const isAdmin = computed(() => store.getters["auth/isAdmin"])
    const stompClient = computed(() => store.state.socket.stompClient)
    const user = computed(() => store.state.auth.user)
    const modalEl = ref(null)

    const logout = async () => {
      modalEl.value.isVisible = true
      const yes = await modalEl.value.show()
      if (yes) {
        store.commit("auth/setToken", "")
        exitOffice(stompClient.value, user.value)
        disconnectStomp()
        router.push({ name: "Home" })
      }
    }

    return {
      logout,
      isAdmin,
      modalEl,
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
