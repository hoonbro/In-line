<template>
  <Modal>
    <template v-slot:modal-body>
      <div class="flex mb-10">
        <h1 class="text-3xl font-bold">회사 등록</h1>
      </div>
      <div class="input-list">
        <TextInput
          v-for="(field, key) in formData"
          :key="key"
          :name="key"
          v-model="field.value"
          :formData="formData"
          :field="field"
        />
        <div>
          <input
            class="mr-1"
            type="checkbox"
            name="term"
            id="term"
            v-model="term"
          />
          <label class="text-sm font-medium" for="term">
            이용약관 및 개인정보처리방침 동의
          </label>
        </div>
        <button
          class="regist-btn"
          :class="{ disabled: !formIsValid }"
          :disabled="!formIsValid"
          @click="registerOffice"
        >
          회사 등록하기
        </button>
      </div>
    </template>
  </Modal>
</template>

<script>
import {
  requiredValidator,
  emailValidator,
  confirmPasswordValidator,
  passwordSecurityValidator,
} from "@/lib/validator"
import { computed, reactive, ref } from "vue"
import { useStore } from "vuex"
import TextInput from "@/components/TextInput.vue"
import Modal from "@/components/Common/Modal.vue"

export default {
  name: "RegistOfficeModal",
  components: { TextInput, Modal },
  setup(props, { emit }) {
    const store = useStore()

    const formData = reactive({
      officeName: {
        label: "회사 이름",
        type: "text",
        value: "asdf",
        validators: [requiredValidator],
        errors: {},
      },
      email: {
        label: "담당자 이메일",
        type: "email",
        value: "asdf@asdf.asdf",
        validators: [requiredValidator, emailValidator],
        errors: {},
      },
      deptName: {
        label: "담당자 소속",
        type: "text",
        value: "인사",
        validators: [requiredValidator],
        errors: {},
      },
      jobName: {
        label: "담당자 직무",
        type: "text",
        value: "팀원",
        validators: [requiredValidator],
        errors: {},
      },
      name: {
        label: "담당자 이름",
        type: "text",
        value: "테스터",
        validators: [requiredValidator],
        errors: {},
      },
      phone: {
        label: "담당자 휴대전화",
        type: "text",
        value: "00",
        validators: [requiredValidator],
        errors: {},
      },
      password: {
        label: "담당자 비밀번호",
        type: "password",
        value: "q1w2e3r4!@",
        validators: [requiredValidator, passwordSecurityValidator],
        errors: {},
      },
      confirmPassword: {
        label: "담당자 비밀번호 확인",
        type: "password",
        value: "q1w2e3r4!@",
        validators: [requiredValidator, confirmPasswordValidator],
        errors: {},
      },
    })

    const allFormIsFilled = computed(() => {
      return Object.keys(formData).every(key => formData[key].value)
    })

    const allFormIsValid = computed(() => {
      return Object.keys(formData).every(key => {
        return formData[key].validators.every(validator =>
          validator(formData, key)
        )
      })
    })

    const term = ref(false)

    const formIsValid = computed(() => {
      return allFormIsFilled.value && allFormIsValid.value && term.value
    })

    const registerOffice = async () => {
      const submitData = { term: term.value }
      Object.keys(formData).forEach(
        key => (submitData[key] = formData[key].value)
      )
      try {
        await store.dispatch("landing/registerOffice", submitData)
        emit("close")
      } catch (error) {
        console.log(error)
        alert(error)
      }
    }

    return {
      store,
      formData,
      term,
      registerOffice,
      formIsValid,
    }
  },
}
</script>

<style scoped lang="scss">
.backdrop {
  background: rgba(46, 46, 51, 0.6);
  @apply fixed z-50 left-0 top-0 w-full h-full flex items-center justify-center;
  z-index: 5;

  .modal-container {
    @apply shadow-xl bg-white rounded-xl w-full md:w-1/2 max-w-lg p-10 grid gap-10;

    .input-list {
      @apply grid gap-4 w-full;

      .regist-btn {
        @apply bg-blue-600 rounded-xl py-4 text-white font-bold;

        &.disabled {
          @apply bg-gray-400;
        }
      }
    }
  }
}
</style>
