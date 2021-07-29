<template>
  <div class="navbar">
    <router-link class="home-btn" :to="{ name: 'Home' }">人-line</router-link>
    <ul class="btn-list">
      <li>
        <router-link class="btn" to="#">기능 소개</router-link>
      </li>
      <li>
        <a
          class="btn cursor-pointer"
          @click="isRegistOfficeModalActivation = true"
        >
          회사 등록
        </a>
      </li>
      <li>
        <a class="btn cursor-pointer" @click="isLoginModalActivation = true">
          로그인
        </a>
      </li>
    </ul>
  </div>
  <router-view />
  <RegistOfficeModal
    v-show="isRegistOfficeModalActivation"
    @close="isRegistOfficeModalActivation = false"
  />
  <LoginModal
    v-show="isLoginModalActivation"
    @close="isLoginModalActivation = false"
  />
</template>

<script>
import { ref } from "vue"
import RegistOfficeModal from "@/components/RegistOfficeModal.vue"
import LoginModal from "@/components/LoginModal.vue"
import { useRoute } from "vue-router"

export default {
  name: "LandingLayout",
  components: { RegistOfficeModal, LoginModal },
  setup() {
    const route = useRoute()

    const isRegistOfficeModalActivation = ref(false)
    const isLoginModalActivation = ref(false)

    if (route.params.shouldLogin) isLoginModalActivation.value = true

    return {
      isRegistOfficeModalActivation,
      isLoginModalActivation,
    }
  },
}
</script>

<style scoped lang="scss">
.navbar {
  @apply flex justify-between items-center bg-gray-100 font-bold;

  .home-btn {
    @apply text-2xl px-4 py-2;
  }

  .btn-list {
    @apply flex;

    .btn {
      @apply py-2 px-4 rounded-md font-bold hover:bg-white mx-4;
    }
  }
}
</style>
