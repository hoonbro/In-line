<template>
  <div class="layout">
    <MainNav />
    <main>
      <LeftAsidebar @click:openTodoModal="handleOpenTodoModal" />
      <router-view class="flex-1" />
      <RightAsidebar />
    </main>
  </div>
  <TodoModal
    v-if="todoModalOpen"
    :todoData="todoData"
    @close="todoModalOpen = false"
  />
</template>

<script>
import { ref, reactive } from "vue"
import MainNav from "@/components/Nav/MainNav.vue"
import RightAsidebar from "@/components/RightAsidebar.vue"
import LeftAsidebar from "@/components/LeftAsidebar.vue"
import TodoModal from "@/components/Office/TodoModal.vue"

export default {
  name: "OfficeLayout",
  components: {
    MainNav,
    RightAsidebar,
    LeftAsidebar,
    TodoModal,
  },
  setup() {
    const todoModalOpen = ref(false)
    const todoData = reactive({
      userId: null,
      userName: "",
    })

    const handleOpenTodoModal = payload => {
      console.log(payload)
      todoModalOpen.value = true
      todoData.userId = payload.userId
      todoData.userName = payload.userName
    }

    return {
      handleOpenTodoModal,
      todoModalOpen,
      todoData,
    }
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
