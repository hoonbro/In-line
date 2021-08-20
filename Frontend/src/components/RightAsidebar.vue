<template>
  <aside>
    <div class="menu-content-container">
      <component :is="menuComponent" />
    </div>
    <div class="menu-container">
      <div class="menu-list">
        <template v-if="$route.fullPath.includes('rooms')">
          <button
            class="menu-list-item room-chat"
            :class="{ active: activeMenu === 'room-chat' }"
            @click="handleActiveMenu('room-chat')"
          >
            <span class="material-icons">
              forum
            </span>
          </button>
          <!-- <button
            class="menu-list-item users"
            :class="{ active: activeMenu === 'users' }"
            @click="handleActiveMenu('users')"
          >
            <span class="material-icons">
              people
            </span>
          </button> -->
          <div class="hr"></div>
        </template>
        <!-- <button
          class="menu-list-item noti"
          :class="{ active: activeMenu === 'noti' }"
          @click="handleActiveMenu('noti')"
        >
          <span class="material-icons">
            notifications
          </span>
        </button> -->
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
      </div>
    </div>
  </aside>
</template>

<script>
import { computed, defineAsyncComponent, ref } from "@vue/runtime-core"
export default {
  name: "RightAsidebar",
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
            import("@/components/RightAsidebar/RoomChat.vue")
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
aside {
  @apply flex-shrink-0 bg-white h-full shadow-xl flex relative;

  .menu-container {
    width: 60px;
    @apply py-4 mx-auto border-l border-gray-300;

    .menu-list {
      @apply grid gap-4 justify-center;

      .hr {
        @apply bg-gray-400 h-px w-full;
      }

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
