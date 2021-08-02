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
                <span>{{ room.roomName }}</span>
              </div>
              <!-- ---------------icons----------------- -->
              <div class="icons">
                <span
                  class="material-icons text-gray-500"
                  @click="editRoom(id)"
                >
                  edit </span
                ><span
                  class="material-icons text-red-500"
                  @click="deleteRoom(id)"
                >
                  close
                </span>
              </div>
            </li>
            <!-- ------------------------------------------ -->
            <li class="room-list-item-add">
              <div class="info"><span>회의실 추가하기</span></div>
              <div class="icons">
                <span class="material-icons" @click="showCreateButton()">
                  add
                </span>
              </div>
            </li>
          </ul>
        </div>
      </div>
      <hr />
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
        <!-- <div class="text-center" v-if="activeCreate === false">
          <span @click="showCreateButton()">회의실 추가</span>
        </div> -->
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
import axios from "axios"
import { onMounted, ref, reactive } from "vue"
import { requiredValidator } from "@/lib/validator"

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
  setup(props) {
    const profile = reactive({})

    const formData = reactive({
      name: {
        label: "새 회의실 이름",
        type: "text",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
    })
    // const rooms = 5

    const rooms = ref([])

    const getRooms = async () => {
      const res = await axios.get("http://localhost:3000/rooms")
      rooms.value = res.data
      console.log(rooms)
    }

    const activeCreate = ref(false)

    const showCreateButton = () => {
      activeCreate.value = !activeCreate.vaule
    }
    // 방 생성할 때 최대 길이 제한 걸어야 함
    const createRoom = async () => {
      try {
        const res = await axios({
          url: "http://localhost:3000/rooms",
          method: "POST",
          data: {
            roomName: formData.name,
          },
        })
      } catch (error) {
        console.log(error)
      }
      activeCreate.value = false
    }

    const editRoom = async () => {
      const res = await axios({
        url: "http://localhost:3000/rooms",
        // PUT ? PATCH ? API에는 일단 PUT으로 나와 있음
        method: "PUT",
        data: {
          roomName: formData.name,
        },
      })
    }

    const deleteRoom = async id => {
      const res = await axios({
        url: `http://localhost:3000/rooms/${id}`,
        method: "DELETE",
      })
      console.log(res)
    }

    // api/v1/room/{roomId} PUT => 수정
    // api/v1/room/{roomId} DELETE => 삭제

    // 노션 API에 room 조회와 전체 room 리스트 두개로 나눠져있는데
    // 뭘 해야 할지 모르겠다. axios로 get요청을 보내서 가져온다음
    // 해당 데이터의 개수만큼 위의 room-list-item을 반복시키자
    // 회의실 생성하기를 아래쪽에 text로 둘 지, room list item에 추가하기 버튼으로 둘지
    // 고민 해보자..

    onMounted(() => {
      // const res = await axios.get(`http://localhost:3000/room/list`)
      // console.log(res)
      // if (res.status === 200) {
      //   Object.keys(profile).forEach(key => {
      //     profile[key].value = res.data[key]
      //   })
      // }
      getRooms()
    })

    return {
      // profile,
      activeCreate,
      rooms,
      createRoom,
      deleteRoom,
      formData,
      showCreateButton,
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
    @apply flex flex-col;

    .room-list-item {
      @apply bg-gray-50 w-full h-10 rounded my-1 items-center px-4 flex justify-between;
    }

    .room-list-item-add {
      @apply bg-blue-500 w-full h-10 rounded my-1 items-center px-4 flex justify-between;
    }
    .material-icons {
      @apply w-2 h-2 mx-1 px-2 cursor-pointer;
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
