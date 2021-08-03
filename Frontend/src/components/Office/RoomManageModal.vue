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
            >{Officename}의 회의실을 관리할 수 있습니다.<br />
            기본적으로 생성된 회의실은 수정할 수 없습니다.
          </span>
        </div>
        <div>
          <ul class="room-list">
            <li
              class="room-list-item"
              v-for="room in rooms"
              :key="room.id"
              :room="room"
            >
              <div class="info">
                <span v-if="activeEdit !== room.id"
                  >{{ room.name }}{{ room.id }}</span
                >
                <!-- 여기에 value="" 느면 될거같은데..내일 작업해야지 -->
                <input
                  v-if="activeEdit === room.id"
                  v-model="newName"
                  :placeholder="room.name"
                />
              </div>

              <!-- ---------------icons----------------- -->
              <div class="icons" v-if="room.id > 2">
                <span
                  v-if="activeEdit !== room.id"
                  class="material-icons text-gray-500"
                  @click="check(room.id)"
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
          <TextInput
            v-if="activeCreate"
            v-for="(field, key) in formData"
            :key="key"
            :name="key"
            v-model="field.value"
            :field="field"
            :formData="formData"
          />
        </div>
        <!-- 얘도 버튼화 시켜야함 -->
        <div class="text-center" v-if="activeCreate === false">
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
import { requiredValidator } from "@/lib/validator"
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

    const formData = reactive({
      name: {
        label: "새 회의실 이름",
        type: "text",
        value: "",
        // 유효성과 에러,,필요할까?
        validators: [requiredValidator],
        errors: {},
      },
    })

    const newFormData = reactive({
      name: {
        label: "새 회의실 이름",
        type: "text",
        value: "",
        // 유효성과 에러,,필요할까?
        validators: [requiredValidator],
        errors: {},
      },
    })

    // 방 가져오기
    const getRooms = () => {
      store.dispatch("office/getRooms")
    }
    // 방 생성 Input (원래 안보임)
    const activeCreate = ref(false)

    const activeEdit = ref("")

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

    const newName = ref("")

    const editRoom = roomId => {
      if (!newName.value) {
        alert("회의실 이름은 공백으로 할 수 없습니다.")
        return
      }
      try {
        const room = {
          name: newName,
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
      newName,
      formData,
      newFormData,
      activeCreate,
      activeEdit,
      showCreateButton,
      rooms,
      createRoom,
      editRoom,
      deleteRoom,
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

    .room-list-item {
      @apply bg-gray-50 w-full h-10 flex-shrink-0 rounded my-1 items-center px-4 flex justify-between;
      // flex 박스 안에 요소가 많아져도 크기를 유지하는 flex-shrink-0(0은 false, 1은 true)
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
