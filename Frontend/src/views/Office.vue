<template>
  <div class="office">
    <div class="rooms-row">
      <div class="rooms-container">
        <RoomLink
          v-for="room in displayRoomList"
          :key="room.roomId"
          :title="room.roomName"
          :roomId="room.roomId"
        />
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
import { useStore } from "vuex"
import { ref, computed } from "vue"
import RoomLink from "@/components/Office/RoomLink.vue"
import RoomManageModal from "@/components/Office/RoomManageModal.vue"

export default {
  name: "Office",
  components: {
    RoomLink,
    RoomManageModal,
  },
  setup() {
    const store = useStore()
    const roomManageModalOpen = ref(false)
    const user = JSON.parse(localStorage.getItem("user"))

    const rooms = computed(() => {
      return store.state.office.rooms
    })
    // 로비는 빼자
    const displayRoomList = computed(() => {
      return rooms.value.filter(room => room.roomName !== "로비")
    })

    // getRooms 실행시키자
    store.dispatch("office/getRooms", user.officeId)

    return {
      user,
      rooms,
      roomManageModalOpen,
      displayRoomList,
    }
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
      @apply w-full grid grid-cols-1 lg:grid-cols-2 gap-8 xl:grid-cols-3;
      &.basic {
        @apply grid-cols-2;
      }
    }
  }
  .button-group {
    @apply flex justify-end;
    button {
      @apply w-40 py-2 px-4 bg-blue-800 text-white rounded-lg mr-0;
    }
  }
}
</style>
