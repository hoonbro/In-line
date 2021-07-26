<template>
  <div class="navbar">
    <router-link class="home-btn" :to="{ name: 'Home' }">人-line</router-link>
    <ul class="btn-list">
      <li>
        <router-link class="btn" to="#">기능 소개</router-link>
      </li>
      <li>
        <a class="btn cursor-pointer">회사 등록</a>
      </li>
      <li>
        <router-link class="btn" :to="{ name: 'Login2' }">로그인</router-link>
      </li>
    </ul>
  </div>
  <router-view />
</template>

<script>
export default {
  name: "LandingLayout",
  components: {},
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
