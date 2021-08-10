<template>
  <div class="layout">
    <MainNav />
    <main>
      <LeftAsidebar />
      <router-view class="flex-1" />
      <RightAsidebar />
    </main>
  </div>
</template>

<script>
import MainNav from "@/components/Nav/MainNav.vue"
import RightAsidebar from "@/components/RightAsidebar.vue"
import LeftAsidebar from "@/components/LeftAsidebar.vue"

export default {
  name: "OfficeLayout",
  components: {
    MainNav,
    RightAsidebar,
    LeftAsidebar,
  },
  setup() {
    return {}
  },
}
</script>

<style scoped lang="scss">
.layout {
  @apply h-screen flex flex-col overflow-hidden;
  main {
    @apply h-full overflow-hidden flex;
  }
}
</style>
