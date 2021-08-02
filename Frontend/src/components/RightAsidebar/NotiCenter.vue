<template>
  <div class="noti-container">
    <div class="noti-list">
      <NotiListItem
        v-for="notification in notifications"
        :key="notification.id"
        :noti="notification"
        @delete="deleteNotification"
      />
    </div>
  </div>
</template>

<script>
import { onMounted, computed } from "vue"
import NotiListItem from "@/components/RightAsidebar/NotiListItem.vue"
import { useStore } from "vuex"

export default {
  name: "NotiCenter",
  components: {
    NotiListItem,
  },
  setup() {
    const store = useStore()

    const notifications = computed(() => {
      return store.state.office.notifications
    })

    const deleteNotification = notiId => {
      store.dispatch("office/deleteNotification", notiId)
    }

    onMounted(() => {
      store.dispatch("office/getNotifications")
    })
    return {
      notifications,
      deleteNotification,
    }
  },
}
</script>

<style lang="scss" scoped>
.noti-container {
  width: 280px;
  @apply p-4 h-full overflow-hidden;

  .noti-list {
    @apply grid gap-2 h-full content-start overflow-auto;
  }
}
</style>
