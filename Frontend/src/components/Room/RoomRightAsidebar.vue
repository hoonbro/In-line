<template>
  <aside
    :class="[
      { test1: activeMenu === 'noti' },
      { test: activeMenu === 'chat' },
      { test: activeMenu === 'todo' },
      { test: activeMenu === 'room-chat' },
      { test2: activeMenu === 'room-people' },
    ]"
  >
    <div class="menu-content-container">
      <component :is="menuComponent" />
    </div>
    <div class="menu-container">
      <div class="menu-list">
        <button
          class="menu-list-item noti"
          :class="{ active: activeMenu === 'noti' }"
          @click="handleActiveMenu('noti')"
        >
          <span class="material-icons">
            notifications
          </span>
        </button>
        <button
          class="menu-list-item chat"
          :class="{ active: activeMenu === 'chat' }"
          @click="handleActiveMenu('chat')"
        >
          <span class="material-icons">
            chat
          </span>
        </button>
        <button
          class="menu-list-item todo"
          :class="{ active: activeMenu === 'todo' }"
          @click="handleActiveMenu('todo')"
        >
          <span class="material-icons">
            done
          </span>
        </button>
        <hr />
        <!-- 여기서부터 내가 추가해야하는거 -->
        <button
          class="menu-list-item room-chat"
          :class="{ active: activeMenu === 'room-chat' }"
          @click="handleActiveMenu('room-chat')"
        >
          <span class="material-icons">
            chat
          </span>
        </button>
        <!--  -->
        <button
          class="menu-list-item room-people"
          :class="{ active: activeMenu === 'room-people' }"
          @click="handleActiveMenu('room-people')"
        >
          <span class="material-icons">
            people
          </span>
        </button>
      </div>
    </div>
  </aside>
</template>

<script>
import { computed, defineAsyncComponent, ref } from "@vue/runtime-core"
export default {
  name: "RoomRightAsidebar",
  setup() {
    const activeMenu = ref(null)
    const menuComponent = computed(() => {
      switch (activeMenu.value) {
        case "noti": {
          return defineAsyncComponent(() =>
            import("@/components/RightAsidebar/NotiCenter.vue")
          )
        }
        case "chat": {
          return defineAsyncComponent(() =>
            import("@/components/RightAsidebar/Chat.vue")
          )
        }
        case "todo": {
          return defineAsyncComponent(() =>
            import("@/components/RightAsidebar/Todo.vue")
          )
        }
        case "room-chat": {
          return defineAsyncComponent(() =>
            import("@/components/Room/RoomChat.vue")
          )
        }
        case "room-people": {
          return defineAsyncComponent(() =>
            import("@/components/Room/RoomPeople.vue")
          )
        }
        default:
          return
      }
    })
    const handleActiveMenu = menu => {
      switch (menu) {
        case activeMenu.value: {
          activeMenu.value = null
          break
        }
        default: {
          activeMenu.value = menu
        }
      }
    }

    return {
      menuComponent,
      handleActiveMenu,
      activeMenu,
    }
  },
}
</script>

<style scoped lang="scss">
.test {
  width: 360px;
}
.test1 {
  width: 280px;
}

.test2 {
  width: 220px;
}

aside {
  @apply flex-shrink-0 bg-white h-full shadow-xl flex relative;

  .menu-container {
    width: 60px;
    @apply py-4 border-l border-gray-300 h-screen ml-auto;

    .menu-list {
      @apply grid gap-4 justify-center;

      .menu-list-item {
        @apply p-2 text-gray-400 relative;

        &.active {
          @apply text-blue-500;
        }

        &.noti {
          &::before {
            content: "";
            @apply absolute w-2 h-2 top-3 left-3 bg-red-500 rounded-full;
          }
        }
      }
    }
  }

  .menu-content-container {
    right: 60px;
    @apply h-full overflow-hidden absolute top-0 bg-white shadow-xl;
  }
}
</style>
