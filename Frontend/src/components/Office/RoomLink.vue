<template>
  <router-link
    :to="{ name: 'Room', params: { roomId } }"
    class="room"
    :class="{ large: large }"
  >
    <div class="flex justify-between gap-4">
      <p class="title">{{ title }}</p>
      <img
        class="img"
        :class="{ large: large }"
        :src="`https://picsum.photos/seed/room-${roomId}/100`"
        alt="이미지"
      />
    </div>
    <div>
      참가자 이미지
    </div>
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
  @apply p-6 rounded-lg bg-white flex flex-col justify-between shadow relative transition-colors col-span-12 lg:col-span-6 xl:col-span-4;

  &:hover {
    @apply bg-blue-50;
  }

  .title {
    @apply text-xl font-bold;
  }

  .img {
    @apply w-16 h-16 rounded-full;
  }

  &.large {
    height: 240px;
    @apply col-span-12 lg:col-span-6;

    .title {
      @apply text-2xl font-bold;
    }

    .img {
      @apply w-24 h-24;
    }
  }
}
</style>
