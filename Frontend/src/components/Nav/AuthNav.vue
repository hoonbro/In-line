<template>
  <div class="navbar">
    <router-link class="text-2xl" :to="{ name: 'Home' }">인-라인</router-link>
  </div>
</template>

<script>
export default {}
</script>

<style scoped lang="scss">
.navbar {
  height: 60px;
  @apply flex items-center font-bold px-4 border-b;
}
</style>
