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
  @apply p-6 rounded-lg bg-white flex flex-col justify-between shadow;

  .title {
    @apply text-xl font-bold;
  }

  &.large {
    height: 240px;

    .title {
      @apply text-2xl font-bold;
    }
  }
}
</style>
