<template>
  <Modal>
    <template v-slot:modal-header>
      <header>
        <button @click="$emit('close')">
          <span class="material-icons">close</span>
        </button>
      </header>
    </template>
    <template v-slot:modal-body>
      <div class="modal-body">
        <div class="profile-img-wrapper">
          <span>
            {{ profile.name.value }}
          </span>
        </div>
      </div>
      <div>
        <div class="header">
          <h3>기본정보</h3>
          <button class="edit-btn" v-if="true">
            <span class="material-icons ">edit</span>
          </button>
        </div>
        <ul class="info-list">
          <li class="info-list-item" v-for="(field, key) in profile" :key="key">
            <p class="label">{{ field.label }}</p>
            <p class="">{{ field.value }}</p>
          </li>
        </ul>
      </div>
      <div class="change-pwd-btn-container" v-if="true">
        <button>비밀번호 변경</button>
      </div>
    </template>
  </Modal>
</template>

<script>
import { reactive } from "@vue/reactivity"
import { onMounted } from "@vue/runtime-core"
import axios from "axios"

import Modal from "@/components/Common/Modal.vue"

export default {
  name: "ProfileModal",
  components: {
    Modal,
  },
  props: {
    userId: Number,
  },
  setup(props) {
    const profile = reactive({
      department: {
        label: "부서",
        value: "",
      },
      position: {
        label: "직무",
        value: "",
      },
      email: {
        label: "이메일",
        value: "",
      },
      name: {
        label: "이름",
        value: "",
      },
      nickName: {
        label: "닉네임",
        value: "",
      },
      phone: {
        label: "휴대전화",
        value: "",
      },
    })

    onMounted(async () => {
      const res = await axios.get(`http://localhost:3000/users/${props.userId}`)
      console.log(res)
      if (res.status === 200) {
        Object.keys(profile).forEach(key => {
          profile[key].value = res.data[key]
        })
      }
    })

    return {
      profile,
    }
  },
}
</script>

<style scoped lang="scss">
header {
  @apply border-b border-gray-300 flex p-2 justify-end;

  button {
    @apply p-2 w-10 h-10;
  }
}

.modal-body {
  @apply mb-10 flex justify-center;

  .profile-img-wrapper {
    @apply bg-green-200 rounded-full w-32 h-32 flex items-center justify-center;

    span {
      @apply text-gray-600 font-medium;
    }
  }

  .header {
    @apply flex items-center justify-between mb-4;

    h3 {
      @apply text-xl font-bold;

      .edit-btn {
        @apply p-1 w-8 h-8;

        .material-icons {
          @apply text-gray-500;
        }
      }
    }
  }
  .info-list {
    @apply grid;

    .info-list-item {
      @apply flex items-center py-4 border-b;

      .label {
        @apply font-medium text-gray-400 w-40;
      }
    }
  }
  .change-pwd-btn-container {
    @apply mt-4 flex justify-end;

    button {
      @apply font-medium text-sm text-gray-600;
    }
  }
}
</style>
