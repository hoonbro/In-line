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
    :shouldChangePassword="shouldChangePassword"
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

    const shouldChangePassword = ref(Boolean(route.params.shouldChangePassword))

    if (route.params.shouldLogin) isLoginModalActivation.value = true

    return {
      isRegistOfficeModalActivation,
      isLoginModalActivation,
      shouldChangePassword,
    }
  },
}
</script>

<style scoped lang="scss"></style>
