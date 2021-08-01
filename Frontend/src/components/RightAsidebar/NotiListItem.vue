<template>
  <div class="noti-list-item">
    <div class="content-container">
      <div class="contents">
        <p>
          <b>{{ noti.user }}</b
          >님이 회의실로 초대하였습니다.
        </p>
        <p class="time">{{ noti.created }}</p>
      </div>
      <button class="close-btn" @click="$emit('delete', noti.id)">
        <span class="material-icons">close</span>
      </button>
    </div>
    <button class="join-room-btn">회의실 이동 ({{ noti.roomId }})</button>
  </div>
</template>

<script>
export default {
  name: "NotiListItem",
  props: {
    noti: Object,
  },
}
</script>

<style scoped lang="scss">
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
</style>
