<template>
  <router-link
    :to="{ name: 'Room', params: { roomId } }"
    class="room"
    :class="{ large: large }"
  >
    <p class="title">{{ title }}</p>
    <div>
      참가자 이미지
    </div>
    <img
      class="img"
      :class="{ large: large }"
      :src="`https://picsum.photos/seed/room-${roomId}/100`"
      alt="이미지"
    />
  </router-link>
</template>

<script>
export default {
  name: "RoomLink",
  props: {
    title: String,
    roomId: [String, Number],
    large: {
      type: Boolean,
      default: false,
    },
  },
}
</script>

<style scoped lang="scss">
.room {
  height: 160px;
  @apply p-6 rounded-lg bg-white flex flex-col justify-between shadow relative transition-colors;

  &:hover {
    @apply bg-blue-50;
  }

  .title {
    @apply text-xl font-bold;
  }

  .img {
    @apply absolute w-16 h-16 top-6 right-6 rounded-full;
  }

  &.large {
    height: 240px;

    .title {
      @apply text-2xl font-bold;
    }

    .img {
      @apply w-24 h-24;
    }
  }
}
</style>
