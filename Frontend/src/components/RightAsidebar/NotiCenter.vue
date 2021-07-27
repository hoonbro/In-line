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
import { ref, onMounted } from "vue"
import axios from "axios"
import NotiListItem from "@/components/RightAsidebar/NotiListItem.vue"

export default {
  name: "NotiCenter",
  components: {
    NotiListItem,
  },
  setup() {
    const notifications = ref([])

    const getNotifications = async () => {
      const res = await axios({
        url: "http://localhost:3000/notifications",
      })
      notifications.value = res.data
    }

    const deleteNotification = async notiId => {
      const res = await axios({
        url: `http://localhost:3000/notifications/${notiId}`,
        method: "DELETE",
      })
      notifications.value = notifications.value.filter(
        noti => noti.id !== notiId
      )
    }

    onMounted(() => {
      getNotifications()
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
