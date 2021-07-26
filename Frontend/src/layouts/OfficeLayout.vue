<template>
  <div class="layout">
    <MainNav />
    <main>
      <RightAsidebar />
    </main>
  </div>
</template>

<script>
import MainNav from "@/components/MainNav.vue"
import RightAsidebar from "@/components/RightAsidebar.vue"

export default {
  name: "OfficeLayout",
  components: {
    MainNav,
    RightAsidebar,
  },
}
</script>

<style scoped lang="scss">
.layout {
  @apply h-screen flex flex-col overflow-hidden;

  main {
    @apply h-full overflow-hidden;
  }
}
</style>
