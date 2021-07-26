<template>
  <div class="noti-container">
    <div class="noti-list">
      <div class="noti-list-item" v-for="i in 20" :key="i">
        <div class="content-container">
          <div class="contents">
            <p><b>김병훈</b>님이 회의실로 초대하였습니다.</p>
            <p class="time">10분 전</p>
          </div>
          <button class="close-btn">
            <span class="material-icons">close</span>
          </button>
        </div>
        <button class="join-room-btn">회의실 이동</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "NotiCenter",
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
