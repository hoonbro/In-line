<template>
  <router-link
    :to="{ name: 'Room', params: { roomId } }"
    class="room"
    :class="{ large: large }"
  >
    <div class="flex justify-between gap-4">
      <p class="title">{{ title }}</p>
      <img class="img" :class="{ large: large }" :src="imgUrl" alt="이미지" />
    </div>
    <div class="users-container">
      <template v-for="user in roomUserList" :key="user.userId">
        <div class="user-profile-container">
          <img
            v-if="user.profileImage"
            :src="`/images/${user.profileImage}`"
            :alt="user.userName"
          />
          <div v-else class="default-img">
            {{ user.userName[0] }}
          </div>
          <div class="tooltip">{{ user.userName }}</div>
        </div>
      </template>
    </div>
  </router-link>
</template>

<script>
import { computed } from "@vue/runtime-core"
export default {
  name: "RoomLink",
  props: {
    title: String,
    roomId: [String, Number],
    roomUserList: Array,
    large: {
      type: Boolean,
      default: false,
    },
  },
  setup(props) {
    const imgUrl = computed(() =>
      require(`@/assets/rooms/main_${props.roomId % 10}.png`)
    )
    return { imgUrl }
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
    @apply w-16 h-16 rounded-xl;
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

  .users-container {
    @apply flex gap-2 w-full;

    .user-profile-container {
      @apply w-8 h-8 relative;

      img {
        @apply w-full h-full rounded-full object-cover;
      }

      .default-img {
        @apply w-full h-full rounded-full bg-gray-50 flex items-center justify-center;
      }

      .tooltip {
        @apply hidden;
      }

      &:hover .tooltip {
        @apply flex absolute p-1 text-sm  bg-white rounded-lg shadow-md top-10 left-0 w-16 justify-center;
      }
    }
  }
}
</style>
