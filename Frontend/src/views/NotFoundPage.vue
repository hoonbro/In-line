<template>
  <div class="background">
    <img src="@/assets/404.png" alt="404 아이콘" />
    <h1>여기가 어디죠?</h1>
    <p>잘못된 경로로 접근했어요. 다시 돌아가볼까요?</p>
    <router-link :to="{ name: 'Home' }">
      원래 궤도로 돌아가기
    </router-link>
  </div>
</template>

<script>
export default {
  name: "NotFound",
}
</script>

<style scoped lang="scss">
.background {
  @apply min-h-screen flex flex-col items-center justify-center;

  img {
    @apply w-32 mb-4;
  }

  h1 {
    @apply text-3xl font-bold mb-2;
  }

  p {
    @apply mb-6;
  }

  a {
    @apply inline-block py-2 px-4 bg-blue-600 text-white rounded;
  }
}
</style>
