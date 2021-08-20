<template>
  <div class="navbar" :class="{ top: isNavTop }">
    <router-link class="home-btn" :to="{ name: 'Home' }">
      <!-- 검은색 인-라인 -->
      <img
        src="@/assets/LandingPage/logo2.png"
        alt=""
        class="w-24"
        v-if="isNavTop"
      />
      <!-- 흰색 인-라인 -->
      <img src="@/assets/LandingPage/logo3.png" alt="" class="w-24" v-else />
    </router-link>
    <ul class="btn-list">
      <li>
        <router-link class="btn" :to="{ name: 'MemberIntroduce' }"
          >팀 소개</router-link
        >
      </li>
      <li>
        <a class="btn cursor-pointer" @click="$emit('openOfficeModal')">
          회사 등록
        </a>
      </li>
      <li>
        <a class="btn cursor-pointer" @click="$emit('openLoginModal')">
          로그인
        </a>
      </li>
    </ul>
  </div>
</template>

<script>
import { onMounted, ref } from "@vue/runtime-core"
export default {
  name: "LandingNav",
  emits: ["openOfficeModal", "openLoginModal"],
  setup() {
    const isNavTop = ref(true)

    onMounted(() => {
      window.addEventListener("scroll", () => {
        isNavTop.value = window.scrollY === 0 ? true : false
      })
    })
    return {
      isNavTop,
    }
  },
}
</script>

<style scoped lang="scss">
.navbar {
  height: 60px;
  @apply flex justify-between items-center font-bold fixed top-0 left-0 w-full px-4 bg-gray-900 text-white border-b transition-all duration-500 z-50;

  &.top {
    @apply bg-white text-gray-900;

    .btn:hover {
      @apply bg-gray-900 text-white;
    }
  }

  .home-btn {
    @apply text-2xl;
  }

  .btn-list {
    @apply flex md:gap-4;

    .btn {
      @apply py-2 px-2 md:px-4 rounded-md font-bold;

      &:hover {
        @apply bg-white text-gray-900;
      }
    }
  }
}
</style>
