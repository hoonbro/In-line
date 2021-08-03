<template>
  <div class="office">
    <div class="rooms-row">
      <div class="rooms-container basic">
        <RoomLink :title="'전체회의'" :roomId="1" :large="true" />
        <RoomLink :title="'전체회의'" :roomId="2" :large="true" />
      </div>
    </div>
    <div class="rooms-row">
      <div class="rooms-container">
        <RoomLink :title="'Developer'" :roomId="3" />
        <RoomLink :title="'Marketing'" :roomId="4" />
        <RoomLink :title="'Developer'" :roomId="5" />
        <RoomLink :title="'Marketing'" :roomId="6" />
        <RoomLink :title="'MiniRoom'" :roomId="7" />
        <RoomLink :title="'Marketing'" :roomId="8" />
      </div>
    </div>
    <div class="button-group">
      <button
        class="room-manage-btn"
        v-if="true"
        @click="roomManageModalOpen = true"
      >
        회의실 관리
      </button>
    </div>
    <RoomManageModal
      v-if="roomManageModalOpen"
      @close="roomManageModalOpen = false"
    />
  </div>
</template>

<script>
import { ref } from "vue"
import RoomLink from "@/components/Office/RoomLink.vue"
import RoomManageModal from "@/components/Office/RoomManageModal.vue"

export default {
  name: "Office",
  components: {
    RoomLink,
    RoomManageModal,
  },
  setup() {
    const roomManageModalOpen = ref(false)
    return { roomManageModalOpen }
  },
}
</script>

<style scoped lang="scss">
.office {
  scrollbar-width: none;
  @apply bg-gray-100 p-10 grid gap-10 content-start overflow-auto;

  &::-webkit-scrollbar {
    display: none;
  }

  .rooms-row {
    @apply w-full grid gap-4;

    &:last-child {
      @apply mb-10;
    }

    .rooms-title {
      @apply text-2xl font-bold;
    }

    .rooms-container {
      @apply w-full grid grid-cols-2 gap-8 lg:grid-cols-3;

      &.basic {
        @apply grid-cols-2;
      }
    }
  }
  .button-group {
    @apply flex justify-end;

    button {
      @apply w-40 h-9 bg-blue-800 text-white rounded-xl mr-0;
    }
  }
}
</style>
