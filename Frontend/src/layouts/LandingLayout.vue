<template>
  <LandingNav
    @openOfficeModal="isRegistOfficeModalActivation = true"
    @openLoginModal="isLoginModalActivation = true"
  />
  <router-view />
  <RegistOfficeModal
    v-if="isRegistOfficeModalActivation"
    @close="isRegistOfficeModalActivation = false"
  />
  <LoginModal
    v-if="isLoginModalActivation"
    @close="isLoginModalActivation = false"
  />
</template>

<script>
import { ref } from "vue"
import { useRoute } from "vue-router"
import LandingNav from "@/components/Nav/LandingNav.vue"
import RegistOfficeModal from "@/components/RegistOfficeModal.vue"
import LoginModal from "@/components/LoginModal.vue"

export default {
  name: "LandingLayout",
  components: { RegistOfficeModal, LoginModal, LandingNav },
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

<style scoped lang="scss"></style>
