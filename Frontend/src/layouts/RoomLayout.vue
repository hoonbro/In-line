<template>
  <div class="layout">
    <MainNav />
    <main>
      <!-- <Room /> -->
      <router-view class="flex-1" />
      <RoomRightAsidebar />
    </main>
  </div>
</template>

<script>
import MainNav from "@/components/MainNav.vue"
import RoomRightAsidebar from "@/components/Room/RoomRightAsidebar.vue"
import Room from "@/views/Room.vue"

export default {
  name: "RoomLayout",
  components: {
    MainNav,
    RoomRightAsidebar,
    Room,
  },
  setup() {
    // 마이크 끄기, 카메라 끄기 버튼 눌렀을 때, 아이콘 전환되게 만들기

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
