<template>
  <Modal>
    <template v-slot:modal-body>
      <div class="profile-img-container">
        <div class="profile-img-wrapper">
          <img
            :src="
              profileImg
                ? `/images/${profileImg}`
                : `https://picsum.photos/seed/user-2-${userId}/100`
            "
            alt="프로필 이미지"
          />
          <input
            type="file"
            accept="image/*"
            v-show="false"
            ref="fileInputEl"
            @change="onFileChange"
          />
          <button class="edit-btn" v-if="isMine" @click="clickInputEl">
            <span class="material-icons">edit</span>
          </button>
        </div>
      </div>
      <div>
        <div class="header">
          <h3>기본정보</h3>
          <template v-if="isMine || isAdmin">
            <button class="edit-btn" v-if="!editMode" @click="editMode = true">
              <span class="material-icons">edit</span>
            </button>
            <button class="edit-btn" v-else @click="editMode = false">
              <span class="material-icons">arrow_back</span>
            </button>
          </template>
        </div>
        <!-- Read Mode -->
        <ul class="info-list" v-if="!editMode">
          <li
            class="info-list-item"
            v-for="(field, key) in profileForm"
            :key="key"
          >
            <p class="label">{{ field.label }}</p>
            <p class="flex-1">{{ field.value }}</p>
          </li>
        </ul>
      </div>
      <div class="change-pwd-btn-container" v-if="isMine && !editMode">
        <router-link :to="{ name: 'ChangePassword' }">
          비밀번호 변경
        </router-link>
      </div>
      <!-- Edit Mode -->
      <form class="edit-form" v-if="editMode" @submit.prevent="updateProfile">
        <TextInput
          v-for="(field, key) in profileForm"
          :key="key"
          :name="key"
          v-model="field.value"
          :formData="profileForm"
          :field="field"
          :maxlength="field.maxlength"
          :disabled="field.disabled"
          @update:modelValue="profileFormError = ''"
          @update:validate="handleUpdateValidate(profileForm, $event)"
        />
        <button class="w-full bg-blue-500 text-white py-4 rounded-xl">
          적용하기
        </button>
      </form>
    </template>
  </Modal>
</template>

<script>
import { reactive, ref } from "@vue/reactivity"
import { computed, onMounted, onUnmounted } from "@vue/runtime-core"
import { useStore } from "vuex"
import axios from "axios"

import {
  requiredValidator,
  emailValidator,
  handleUpdateValidate,
} from "@/lib/validator"
import Modal from "@/components/Common/Modal.vue"
import TextInput from "@/components/TextInput.vue"

export default {
  name: "ProfileModal",
  components: {
    Modal,
    TextInput,
  },
  props: {
    userId: Number,
  },
  setup(props) {
    const store = useStore()
    const isAdmin = computed(() => store.getters["auth/isAdmin"])
    const isMine = computed(() => store.state.auth.user.userId === props.userId)
    const profileForm = reactive({
      email: {
        label: "이메일",
        type: "email",
        value: "",
        validators: [requiredValidator, emailValidator],
        errors: {},
        disabled: true,
      },
      department: {
        label: "부서",
        type: "text",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      position: {
        label: "역할",
        type: "text",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      name: {
        label: "이름",
        type: "text",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      nickName: {
        label: "닉네임",
        type: "text",
        value: "",
        validators: [],
        errors: {},
      },
      phone: {
        label: "휴대전화",
        type: "text",
        value: "",
        validators: [],
        errors: {},
      },
    })
    const profileImg = ref(null)
    const fileInputEl = ref(null)
    const editMode = ref(false)
    const profileFormError = ref("")
    const clickInputEl = () => {
      fileInputEl.value.click()
    }

    const updateProfile = () => {
      alert("프로필 수정하기")
      editMode.value = false
    }

    const onFileChange = async () => {
      const image = fileInputEl.value.files[0]
      const formData = new FormData()
      formData.append("userId", store.state.auth.user.userId)
      formData.append("file", image)
      try {
        const res = await axios({
          method: "PUT",
          url: "/api/v1/users/profile",
          data: formData,
          headers: {
            accessToken: store.state.auth.accessToken,
            "Content-Type": "multipart/form-data",
          },
        })
        console.log(res)
        profileImg.value = res.data
        console.log("이미지 업로드 성공")
      } catch (error) {
        console.log(error)
      }
    }

    onMounted(async () => {
      try {
        const res = await store.dispatch("office/getMember", props.userId)
        console.log(res)
        if (res.status === 200) {
          profileImg.value = res.data.profileImage
          // Form에 데이터 넣기
          profileForm.email.value = res.data.email
          profileForm.department.value = res.data.deptEntity.deptName
          profileForm.position.value = res.data.jobEntity.jobName
          profileForm.name.value = res.data.name
          profileForm.nickName.value = res.data.nickName
          profileForm.phone.value = res.data.phone
        }
      } catch (error) {
        console.log(error)
      }
    })

    onUnmounted(() => {
      try {
        store.dispatch("office/getMembers")
      } catch (error) {
        alert(error)
      }
    })

    return {
      isAdmin,
      isMine,
      profileForm,
      editMode,
      profileImg,
      fileInputEl,
      clickInputEl,
      onFileChange,
      profileFormError,
      updateProfile,
      handleUpdateValidate,
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

.profile-img-container {
  @apply mb-10 flex justify-center;

  .profile-img-wrapper {
    @apply bg-green-200 w-32 h-32 rounded-full flex items-center justify-center relative;

    img {
      @apply w-full h-full object-cover rounded-full;
    }

    button {
      @apply p-1 w-8 h-8 bg-white rounded-full shadow-sm border border-gray-100 absolute right-0 bottom-0 transition-colors;

      &:hover {
        @apply bg-blue-100;
      }

      .material-icons {
        @apply text-gray-500;
      }
    }
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
      @apply font-medium text-gray-400 w-32 flex-shrink-0;
    }
  }
}
.edit-form {
  @apply grid gap-2;
}

.change-pwd-btn-container {
  @apply mt-4 flex justify-end;

  button {
    @apply font-medium text-sm text-gray-600;
  }
}
</style>
