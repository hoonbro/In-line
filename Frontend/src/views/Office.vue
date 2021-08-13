<template>
  <div>
    <div class="office">
      <div class="rooms-row">
        <div class="rooms-container">
          <RoomLink
            v-for="room in defaultRoomList"
            :key="room.roomId"
            :title="room.roomName"
            :roomId="room.roomId"
            :roomUserList="room.roomUserList"
            :large="true"
          />
        </div>
      </div>
      <div class="rooms-row">
        <header>
          <h1>팀별 미팅룸🤷‍♀️</h1>
        </header>
        <div class="rooms-container">
          <RoomLink
            v-for="room in smallRoomList"
            :key="room.roomId"
            :title="room.roomName"
            :roomId="room.roomId"
          />
        </div>
      </div>
      <div class="rooms-row">
        <header>
          <h1>소규모 미팅룸🤩</h1>
        </header>
        <div class="rooms-container">
          <RoomLink
            v-for="room in smallRoomList"
            :key="room.roomId"
            :title="room.roomName"
            :roomId="room.roomId"
          />
        </div>
      </div>
      <div class="button-group">
        <button
          class="room-manage-btn"
          v-if="isAdmin"
          @click="roomManageModalOpen = true"
        >
          회의실 관리
        </button>
      </div>
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
    const isAdmin = computed(() => store.getters["auth/isAdmin"])

    const rooms = computed(() => {
      return store.state.office.rooms
    })
    // 로비는 빼자
    const displayRoomList = computed(() => {
      return rooms.value.filter(room => room.roomName !== "로비")
    })

    const defaultRoomList = computed(() => {
      return rooms.value.slice(1, 3)
    })

    const smallRoomList = computed(() => rooms.value.slice(3))

    return {
      user,
      rooms,
      isAdmin,
      roomManageModalOpen,
      defaultRoomList,
      smallRoomList,
    }
  },
}
</script>

<style scoped lang="scss">
.office {
  scrollbar-width: none;
  @apply p-10 grid gap-14;
  &::-webkit-scrollbar {
    display: none;
  }
  .rooms-row {
    @apply w-full grid gap-6;

    &:last-child {
      @apply mb-10;
    }

    header {
      h1 {
        @apply text-2xl font-bold;
      }
    }
    .rooms-container {
      @apply w-full grid grid-cols-12 gap-8;

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
