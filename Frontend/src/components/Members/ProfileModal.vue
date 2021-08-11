<template>
  <Modal>
    <template v-slot:modal-body>
      <div class="profile-img-container">
        <div class="profile-img-wrapper">
          <img
            v-if="profileImg"
            :src="`/images/${profileImg}`"
            alt="프로필 이미지"
          />
          <span v-else>{{ profileForm.name.value[0] }}</span>
          <input
            type="file"
            accept="image/*"
            v-show="false"
            ref="fileInputEl"
            @change="updateProfileImage"
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
            <button class="edit-btn" v-else @click="resetProfileForm">
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
        <template v-for="(field, key) in profileForm" :key="key">
          <SelectInput
            v-if="key === 'dept'"
            v-model="field.value"
            :name="key"
            :field="field"
            :items="depts"
          />
          <SelectInput
            v-else-if="key === 'job'"
            v-model="field.value"
            :name="key"
            :field="field"
            :items="jobs"
          />

          <TextInput
            v-else
            :name="key"
            v-model="field.value"
            :formData="profileForm"
            :field="field"
            :maxlength="field.maxlength"
            :disabled="field.disabled"
            @update:modelValue="profileFormError = ''"
            @update:validate="handleUpdateValidate(profileForm, $event)"
          />
        </template>
        <button class="w-full bg-blue-500 text-white py-4 rounded-xl">
          적용하기
        </button>
      </form>
    </template>
  </Modal>
</template>

<script>
import { reactive, ref } from "@vue/reactivity"
import { computed, onMounted } from "@vue/runtime-core"
import { useStore } from "vuex"

import {
  requiredValidator,
  emailValidator,
  handleUpdateValidate,
} from "@/lib/validator"
import Modal from "@/components/Common/Modal.vue"
import TextInput from "@/components/TextInput.vue"
import SelectInput from "@/components/SelectInput.vue"

export default {
  name: "ProfileModal",
  components: {
    Modal,
    TextInput,
    SelectInput,
  },
  props: {
    userId: Number,
  },
  setup(props) {
    const store = useStore()
    const isAdmin = computed(() => store.getters["auth/isAdmin"])
    const isMine = computed(() => store.state.auth.user.userId === props.userId)
    const originData = reactive({})
    const profileForm = reactive({
      email: {
        label: "이메일",
        type: "email",
        value: "",
        validators: [requiredValidator, emailValidator],
        errors: {},
        disabled: true,
      },
      dept: {
        label: "부서",
        type: "text",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      job: {
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
    const depts = ref(null)
    const jobs = ref(null)

    const resetProfileForm = () => {
      profileForm.email.value = originData.email
      profileForm.dept.value = originData.deptEntity.deptName
      profileForm.job.value = originData.jobEntity.jobName
      profileForm.name.value = originData.name
      profileForm.nickName.value = originData.nickName
      profileForm.phone.value = originData.phone

      editMode.value = false
    }

    const updateProfile = async () => {
      try {
        const form = {
          officeId: store.getters["auth/officeId"],
          deptName: profileForm.dept.value,
          jobName: profileForm.job.value,
          name: profileForm.name.value,
          nickName: profileForm.nickName.value,
          phone: profileForm.phone.value,
        }
        // 서버에 변경 요청
        const user = await store.dispatch("auth/updateProfile", {
          userId: originData.userId,
          form,
        })

        // ProfileModal 컴포넌트에서 관리하고 있던 값 업데이트
        Object.keys(user).forEach(key => {
          originData[key] = user[key]
        })

        // Vuex의 Members 업데이트
        store.commit("office/updateProfileOfMembers", user)

        store.commit("landing/addAlertModalList", {
          message: "프로필을 수정했어요!",
        })
      } catch (error) {
        // 기존 정보로 리셋
        store.commit("landing/addAlertModalList", {
          type: "error",
          message: error.message,
        })
      } finally {
        resetProfileForm()
      }
    }

    const updateProfileImage = async () => {
      const image = fileInputEl.value.files[0]
      const formData = new FormData()
      formData.append("userId", store.getters["auth/userId"])
      formData.append("file", image)
      try {
        // BackEnd 요청
        const newProfileImage = await store.dispatch(
          "auth/updateProfileImage",
          formData
        )
        // 현재 데이터 업데이트
        profileImg.value = newProfileImage
        // 백업 데이터 업데이트 (없어도 될 듯)
        originData.profileImage = newProfileImage
        // Members 데이터 업데이트
        store.commit("office/updateMemberProfileImage", {
          userId: store.getters["auth/userId"],
          newProfileImage,
        })
        store.commit("landing/addAlertModalList", {
          message: "이미지 업로드 성공",
        })
      } catch (error) {
        store.commit("landing/addAlertModalList", {
          type: "error",
          message: error.message,
        })
      }
    }

    onMounted(async () => {
      try {
        // 프로필 유저 정보 요청
        const res = await store.dispatch("office/getMember", props.userId)
        Object.keys(res.data).forEach(key => {
          originData[key] = res.data[key]
        })
        console.log(originData)
        // 프로필 이미지는 별도로 관리
        profileImg.value = originData.profileImage
        // Form에 데이터 넣기
        resetProfileForm()

        // 프로필 수정 시 사용되는 데이터 요청
        depts.value = await store.dispatch("office/getDepts")
        jobs.value = await store.dispatch("office/getJobs")
      } catch (error) {
        console.log(error)
      }
    })

    return {
      isAdmin,
      isMine,
      depts,
      jobs,
      profileForm,
      editMode,
      profileImg,
      fileInputEl,
      clickInputEl,
      resetProfileForm,
      updateProfileImage,
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
    @apply bg-blue-600 w-32 h-32 rounded-full flex items-center justify-center relative text-white text-lg font-bold;

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

  .select-box {
    appearance: none;
    @apply w-full bg-gray-50 py-4 outline-none rounded-md border border-gray-300 px-3 focus:border-blue-600;
  }
}

.change-pwd-btn-container {
  @apply mt-4 flex justify-end;

  button {
    @apply font-medium text-sm text-gray-600;
  }
}
</style>
