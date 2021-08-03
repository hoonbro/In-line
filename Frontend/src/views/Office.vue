<template>
  <div class="office">
    <div class="rooms-row">
      <div class="rooms-container">
        <RoomLink :title="'전체회의'" :roomId="1" :large="true" />
      </div>
    </div>
    <div class="rooms-row">
      <h1 class="rooms-title">팀별 미팅룸</h1>
      <div class="rooms-container">
        <RoomLink :title="'Developer'" :roomId="2" :large="true" />
        <RoomLink :title="'Marketing'" :roomId="3" :large="true" />
      </div>
    </div>
    <div class="rooms-row">
      <h1 class="rooms-title">소규모 회의실</h1>
      <div class="rooms-container mini">
        <RoomLink :title="'Developer'" :roomId="4" />
        <RoomLink :title="'Marketing'" :roomId="5" />
        <RoomLink :title="'MiniRoom'" :roomId="6" />
        <RoomLink :title="'Marketing'" :roomId="7" />
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
      @apply w-full grid grid-cols-1 gap-8 md:grid-cols-2;

      &.mini {
        @apply grid-cols-3;
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
