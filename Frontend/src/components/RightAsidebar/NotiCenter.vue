<template>
  <div class="noti-container">
    <div class="noti-list">
      <div
        class="noti-list-item"
        v-for="notification in notifications"
        :key="notification.id"
      >
        <div class="content-container">
          <div class="contents">
            <p>
              <b>{{ notification.user }}</b
              >님이 회의실로 초대하였습니다.
            </p>
            <p class="time">{{ notification.created }}</p>
          </div>
          <button
            class="close-btn"
            @click="deleteNotification(notification.id)"
          >
            <span class="material-icons">close</span>
          </button>
        </div>
        <button class="join-room-btn">
          회의실 이동 ({{ notification.roomId }})
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity"
import axios from "axios"
import { onMounted } from "@vue/runtime-core"
export default {
  name: "NotiCenter",
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

    .noti-list-item {
      @apply bg-gray-50 p-4 rounded-lg grid gap-4 select-none;

      .content-container {
        @apply flex items-start;

        .contents {
          @apply grid gap-1 flex-1 mr-2;

          p {
            word-break: keep-all;
          }

          .time {
            @apply text-gray-400;
          }
        }

        .close-btn {
          @apply outline-none text-gray-300;

          &:hover {
            @apply text-red-500;
          }
        }
      }

      .join-room-btn {
        @apply bg-blue-800 text-white text-sm font-medium w-full py-2 rounded-full outline-none;

        &:hover {
          @apply bg-blue-700;
        }
      }
    }
  }
}
</style>
