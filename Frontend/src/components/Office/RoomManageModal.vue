<template>
  <Modal>
    <template v-slot:modal-header>
      <header>
        <h3 class="text-3xl font-bold select-none">회의실 관리</h3>
      </header>
    </template>
    <template v-slot:modal-body>
      <div class="modal-body">
        <div class="modal-explain select-none">
          <p>
            {{ user.officeEntity.officeName }}의 회의실을 관리할 수 있습니다.
          </p>
          <p>기본적으로 생성된 회의실은 수정할 수 없습니다.</p>
        </div>
        <ul class="room-list">
          <li
            v-for="(room, index) in displayRoomList"
            :key="room.roomId"
            class="room-list-item"
            :class="{ edit: activeEdit === room.roomId }"
            :room="room"
          >
            <div class="info">
              <span v-if="activeEdit !== room.roomId">
                {{ room.roomName }}
              </span>
              <!-- 아직도 value는..못했따.. -->
              <input
                class="edit-input"
                v-else
                v-model="newName"
                :placeholder="room.roomName"
                maxlength="20"
                @keyup.enter="editRoom(room.roomId)"
                @keyup.esc="editMode()"
              />
            </div>
            <!-- ---------------icons----------------- -->
            <!-- 기본 회의실은 수정 불가 -->
            <div class="icons" v-if="index > 1">
              <span
                v-if="activeEdit !== room.roomId"
                class="material-icons edit"
                @click="editMode(room.roomId, room.roomName)"
              >
                edit
              </span>
              <span
                v-if="activeEdit !== room.roomId"
                class="material-icons delete"
                @click="deleteRoom(room.roomId, room.roomName)"
              >
                close
              </span>
              <span
                v-if="activeEdit === room.roomId"
                class="material-icons done"
                @click="editRoom(room.roomId)"
              >
                done
              </span>
            </div>
          </li>
        </ul>
      </div>
    </template>
    <template v-slot:modal-footer>
      <div class="modal-footer">
        <div class="create-room" v-if="activeCreate">
          <TextInput
            v-for="(field, key) in formData"
            :key="key"
            class="mb-4"
            :name="key"
            v-model="field.value"
            :field="field"
            :formData="formData"
            :maxlength="field.maxlength"
            @update:validate="handleUpdateValidate(formData, $event)"
          />
        </div>
        <div class="flex flex-col gap-2">
          <button
            class="create-btn mx-auto"
            v-if="activeCreate === false"
            @click="activeCreate = true"
          >
            회의실 추가
          </button>
          <!--  -->
          <template v-else>
            <button
              class="create-btn active"
              :class="{ disabled: !createFormIsValid }"
              :disabled="!createFormIsValid"
              @click="createRoom()"
            >
              회의실 추가하기
            </button>
            <button
              @click="activeCreate = false"
              class="text-sm text-gray-400 font-medium mx-auto"
            >
              취소
            </button>
          </template>
        </div>
      </div>
    </template>
  </Modal>
</template>

<script>
import { ref, reactive, computed } from "vue"
import { requiredValidator, handleUpdateValidate } from "@/lib/validator"
import { useStore } from "vuex"

import Modal from "@/components/Common/Modal.vue"
import TextInput from "@/components/TextInput.vue"

export default {
  name: "RoomManageModal",
  components: {
    Modal,
    TextInput,
  },
  props: {
    userId: Number,
  },
  setup() {
    const store = useStore()
    const rooms = computed(() => store.state.office.rooms)
    const user = JSON.parse(localStorage.getItem("user"))
    const displayRoomList = computed(() => {
      return rooms.value.filter(room => room.roomName !== "로비")
    })
    // 방 생성 Input (원래 안보임)
    const activeCreate = ref(false)
    const activeEdit = ref("")
    const newName = ref("")

    const formData = reactive({
      name: {
        label: "새 회의실 이름",
        type: "text",
        value: "",
        // 유효성과 에러,,필요할까?
        validators: [requiredValidator],
        errors: {},
        maxlength: 20,
      },
    })

    // 방 생성하기를 누르면 Input이 보이면서 해당 버튼은 없어지게
    // template에서 바로 상태 변경 (false => true)

    // 방 생성할 때, 유효성 체크
    const createFormIsValid = computed(() => {
      return formData.name.value && !Object.keys(formData.name.errors).length
    })

    // 방 생성할 때 최대 길이 제한 걸어야 함 (Ok)
    const createRoom = async () => {
      // 회의실 이름이 비어있으면 alert 출력하고
      if (!formData.name.value) {
        alert("회의실 이름은 공백으로 할 수 없습니다.")
        // 멈춰!
        return
      }
      try {
        const room = {
          roomName: formData.name.value,
          officeId: user.officeId,
        }
        await store.dispatch("office/createRoom", room)
        activeCreate.value = !activeCreate.value
      } catch (error) {
        console.log(error)
      }
    }

    const editMode = (roomId, roomName) => {
      activeEdit.value = roomId
      newName.value = roomName
    }

    const editRoom = async roomId => {
      if (!newName.value) {
        alert("회의실 이름은 공백으로 할 수 없습니다.")
        return
      }
      try {
        const room = {
          name: newName.value,
        }
        await store.dispatch("office/editRoom", { room, roomId })
      } catch (error) {
        console.log(error)
      }
      activeEdit.value = ""
    }

    const deleteRoom = (roomId, roomName) => {
      if (confirm(`회의실 이름: ${roomName}\n회의실을 삭제하시겠어요?`)) {
        store.dispatch("office/deleteRoom", roomId)
      }
    }

    return {
      user,
      formData,
      activeCreate,
      activeEdit,
      editMode,
      newName,
      rooms,
      createRoom,
      editRoom,
      deleteRoom,
      handleUpdateValidate,
      displayRoomList,
      createFormIsValid,
    }
  },
}
</script>

<style scoped lang="scss">
header {
  @apply flex px-8 pt-12 justify-between;
}

.modal-body {
  @apply mb-10;

  .modal-explain {
    @apply mb-10;
  }
  .room-list {
    @apply grid gap-2 overflow-y-auto h-48;

    .room-list-item {
      // flex 박스 안에 요소가 많아져도 크기를 유지하는 flex-shrink-0(0은 false, 1은 true)
      @apply bg-gray-50 py-2 pl-9 pr-6 select-none rounded items-center flex justify-between relative;

      &::before {
        content: "";
        transform: translateY(-50%);
        @apply absolute w-1 h-1 bg-green-400 top-1/2 left-4 rounded-full;
      }

      &:hover {
        @apply bg-gray-100;
      }

      &.edit {
        @apply border-blue-600 border-2;
        // border: 2px
      }

      .icons {
        @apply flex gap-1;

        .material-icons {
          font-size: 18px;
          @apply cursor-pointer;

          &.edit {
            @apply text-gray-300 hover:text-gray-500;
          }

          &.delete {
            @apply text-red-300 hover:text-red-500;
          }
        }
      }

      .info {
        @apply w-full;

        input {
          @apply outline-none bg-transparent w-full;
        }
      }
    }

    .room-list-item-add {
      @apply bg-blue-500 w-full h-10 rounded my-1 items-center px-4 flex justify-between;
    }

    .label {
      @apply font-medium text-gray-400 w-40;
    }
  }
}

.modal-footer {
  .create-btn {
    @apply text-center font-bold rounded-xl;

    &.active {
      @apply py-2 bg-blue-500 text-white;
    }

    &.disabled {
      @apply bg-gray-400;
    }
  }
}
</style>
