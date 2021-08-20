<template>
  <LandingNav
    @openOfficeModal="isRegistOfficeModalActivation = true"
    @openLoginModal="isLoginModalActivation = true"
  />
  <router-view />
  <footer>
    <div class="footer_inner">
      <div class="logo_wrapper">
        <img src="@/assets/LandingPage/logo2.png" alt="" />
      </div>
      <div class="info_container">
        <!-- <p>ⓒ 인-라인</p> -->
        <div class="info_inner">
          <div class="flex items-center gap-1">
            <span class="material-icons">call</span>
            <span>070-1234-5678</span>
          </div>
          <div class="flex items-center gap-1">
            <span class="material-icons">email</span>
            <a
              href="mailto:iamlegend_@naver.com?subject=인라인에 문의하기&body=필요한 사항이 있다면 메일을 보내주세요."
            >
              iamlegend_@naver.com
            </a>
          </div>
        </div>
        <div class="info_inner">
          <p>사업자 등록번호: 123-123-123</p>
          <p>서비스 이용약관</p>
          <p>개인정보 처리방침</p>
        </div>
      </div>
    </div>
  </footer>
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

<style scoped lang="scss">
footer {
  @apply bg-gray-200 py-10 text-gray-600;

  .footer_inner {
    @apply container mx-auto px-4 flex flex-col justify-center gap-6;

    .logo_wrapper {
      @apply w-32;

      img {
        @apply w-full h-full object-contain;
      }
    }

    .info_container {
      @apply grid grid-cols-12 gap-1;

      .info_inner {
        @apply col-span-12 sm:col-span-6 grid gap-1 content-start;

        .material-icons {
          @apply text-gray-600;
        }
      }
    }
  }
}
</style>
