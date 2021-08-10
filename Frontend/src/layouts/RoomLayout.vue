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
import MainNav from "@/components/Nav/MainNav.vue"
import RoomRightAsidebar from "@/components/RightAsidebar.vue"
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

    // 첫 로그인 시 Room으로 바로 접근하는 경우, 접속을 확인하기 위한 용도

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
