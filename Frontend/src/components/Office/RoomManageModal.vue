<template>
  <Modal>
    <template v-slot:modal-header>
      <header>
        <h3 class="text-3xl">회의실 관리</h3>
        <button class="close-btn" @click="$emit('close')">
          <span class="material-icons">close</span>
        </button>
      </header>
    </template>
    <template v-slot:modal-body>
      <div class="modal-body">
        <div class="modal-explain">
          <span
            >구미_2반_7팀의 회의실을 관리할 수 있습니다.<br />
            기본적으로 생성된 회의실은 수정할 수 없습니다.
          </span>
        </div>
        <div>
          <ul class="room-list">
            <li
              class="room-list-item"
              :class="{ edit: activeEdit === room.id }"
              v-for="room in rooms"
              :key="room.id"
              :room="room"
            >
              <div class="info">
                <span v-if="activeEdit !== room.id">{{ room.name }}</span>
                <!-- 아직도 value는..못했따.. -->
                <!-- editmode에 진입 시 테두리 -->
                <input
                  id="input-tag"
                  v-if="activeEdit === room.id"
                  v-model="newName"
                  :placeholder="room.name"
                  @keyup.enter="editRoom(room.id)"
                  @keyup.esc="editMode()"
                />
              </div>

              <!-- ---------------icons----------------- -->
              <div class="icons" v-if="room.id > 2">
                <span
                  v-if="activeEdit !== room.id"
                  class="material-icons text-gray-500"
                  @click="editMode(room.id)"
                >
                  edit </span
                ><span
                  v-if="activeEdit !== room.id"
                  class="material-icons text-red-500"
                  @click="deleteRoom(room.id)"
                >
                  close
                </span>
                <span
                  v-if="activeEdit === room.id"
                  class="material-icons"
                  @click="editRoom(room.id)"
                >
                  done
                </span>
              </div>
            </li>
          </ul>
        </div>
      </div>
      <div class="modal-footer my-3">
        <div class="create-room">
          <template v-if="activeCreate">
            <TextInput
              v-for="(field, key) in formData"
              :key="key"
              :name="key"
              v-model="field.value"
              :field="field"
              :formData="formData"
              :maxlength="field.maxlength"
              @update:validate="handleUpdateValidate(formData, $event)"
            />
          </template>
        </div>
        <div class="text-center create-btn" v-if="activeCreate === false">
          <span class="cursor-pointer" @click="showCreateButton()"
            >회의실 추가</span
          >
        </div>
        <!--  -->
        <div>
          <button class="create-btn" v-if="activeCreate" @click="createRoom()">
            회의실 추가하기
          </button>
        </div>
      </div>
    </template>
  </Modal>
</template>

<script>
import { onMounted, ref, reactive, computed } from "vue"
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

    const rooms = computed(() => {
      return store.state.office.rooms
    })
    // 방 생성 Input (원래 안보임)
    const activeCreate = ref(false)

    const activeEdit = ref("")

    const newName = ref("")

    // 방 가져오기
    const getRooms = () => {
      store.dispatch("office/getRooms")
    }

    const formData = reactive({
      name: {
        label: "새 회의실 이름",
        type: "text",
        value: "",
        // 유효성과 에러,,필요할까?
        validators: [requiredValidator],
        errors: {},
        maxlength: 16,
      },
    })

    // 방 생성하기를 누르면 Input이 보이면서 해당 버튼은 없어지게
    const showCreateButton = () => {
      activeCreate.value = !activeCreate.vaule
    }

    // 방 생성할 때 최대 길이 제한 걸어야 함
    const createRoom = () => {
      // 회의실 이름이 비어있으면 alert 출력하고
      if (!formData.name.value) {
        alert("회의실 이름은 공백으로 할 수 없습니다.")
        // 멈춰!
        return
      }
      try {
        const room = {
          name: formData.name.value,
        }
        store.dispatch("office/createRoom", room)
        activeCreate.value = !activeCreate.value
      } catch (error) {
        console.log(error)
      }
    }

    const editMode = roomId => {
      activeEdit.value = roomId
      newName.value = ""
    }

    const editRoom = roomId => {
      if (!newName.value) {
        alert("회의실 이름은 공백으로 할 수 없습니다.")
        return
      }
      try {
        const room = {
          name: newName.value,
        }
        store.dispatch("office/editRoom", { room, roomId })
      } catch (error) {
        console.log(error)
      }

      activeEdit.value = ""
    }

    const deleteRoom = roomId => {
      store.dispatch("office/deleteRoom", roomId)
    }

    onMounted(() => {
      getRooms()
    })

    return {
      formData,
      activeCreate,
      activeEdit,
      showCreateButton,
      editMode,
      newName,
      rooms,
      createRoom,
      editRoom,
      deleteRoom,
      handleUpdateValidate,
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
    @apply flex flex-col overflow-y-auto h-48;

    .edit {
      @apply border-blue-600 border-2;
      // border: 2px
    }

    .room-list-item {
      // flex 박스 안에 요소가 많아져도 크기를 유지하는 flex-shrink-0(0은 false, 1은 true)
      @apply bg-gray-50 w-96 h-10 flex-shrink-0 rounded my-1 items-center px-4 flex justify-between;

      input {
        @apply outline-none bg-gray-50;
      }
    }

    .room-list-item-add {
      @apply bg-blue-500 w-full h-10 rounded my-1 items-center px-4 flex justify-between;
    }
    .material-icons {
      @apply w-2 h-2 ml-2 mr-2 px-2 cursor-pointer;
    }

    .label {
      @apply font-medium text-gray-400 w-40;
    }
  }
}

.modal-footer {
  .create-room {
  }

  .create-btn {
    @apply w-full h-14 bg-gray-400 text-white rounded-xl py-4 mt-4;
  }
}
</style>
