<template>
  <Modal>
    <template v-slot:modal-body>
      <div class="grid gap-5 mb-10">
        <div class="flex justify-between">
          <h1 class="text-3xl font-bold">회사 등록</h1>
          <div
            class="py-1 px-4 flex justify-center items-center rounded-full bg-blue-400 text-sm text-white"
          >
            {{ step }} / 2
          </div>
        </div>
        <p>인-라인에 오신 것을 환영합니다.</p>
      </div>
      <div v-if="step === 1">
        <div class="input-list">
          <TextInput
            v-for="(field, key) in officeFormData"
            :key="key"
            :name="key"
            v-model="field.value"
            :formData="officeFormData"
            :field="field"
            @update:modelValue="officeFormError = ''"
          />

          <div class="grid gap-1">
            <button
              class="regist-btn"
              :class="{
                disabled: !officeFormIsValid,
                error: officeFormError,
              }"
              :disabled="!officeFormIsValid"
              @click="checkOfficeNameIsValid"
            >
              다음 단계로
            </button>

            <p class="submit-error">
              {{ officeFormError }}
            </p>
          </div>
        </div>
      </div>
      <div v-else>
        <div class="input-list">
          <TextInput
            v-for="(field, key) in managerFormData"
            :key="key"
            :name="key"
            v-model="field.value"
            :formData="managerFormData"
            :field="field"
            @update:modelValue="managerFormError = ''"
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
          <div class="grid gap-1">
            <button
              class="regist-btn"
              :class="{ disabled: !formIsValid, error: managerFormError }"
              :disabled="!formIsValid"
              @click="registerOffice"
            >
              회사 등록하기
            </button>
            <p class="submit-error">
              {{ managerFormError }}
            </p>
          </div>
        </div>
      </div>
    </template>
  </Modal>
</template>

<script>
import axios from "axios"
import { computed, reactive, ref } from "vue"
import { useStore } from "vuex"
import {
  requiredValidator,
  emailValidator,
  confirmPasswordValidator,
  passwordSecurityValidator,
} from "@/lib/validator"
import TextInput from "@/components/TextInput.vue"
import Modal from "@/components/Common/Modal.vue"

export default {
  name: "RegistOfficeModal",
  components: { TextInput, Modal },
  setup(props, { emit }) {
    const store = useStore()
    const step = ref(1)
    const officeFormData = reactive({
      officeName: {
        label: "회사 이름",
        type: "text",
        // value: "",
        value: "asdf",
        validators: [requiredValidator],
        errors: {},
      },
    })
    const officeFormError = ref("")
    const managerFormData = reactive({
      email: {
        label: "담당자 이메일",
        type: "email",
        // value: "",
        value: "asdf@asdf.asdf",
        validators: [requiredValidator, emailValidator],
        errors: {},
      },
      deptName: {
        label: "담당자 소속",
        type: "text",
        // value: "",
        value: "인사",
        validators: [requiredValidator],
        errors: {},
      },
      jobName: {
        label: "담당자 역할",
        type: "text",
        // value: "",
        value: "팀원",
        validators: [requiredValidator],
        errors: {},
      },
      name: {
        label: "담당자 이름",
        type: "text",
        // value: "",
        value: "테스터",
        validators: [requiredValidator],
        errors: {},
      },
      phone: {
        label: "담당자 휴대전화",
        type: "text",
        // value: "",
        value: "00",
        validators: [requiredValidator],
        errors: {},
      },
      password: {
        label: "담당자 비밀번호",
        type: "password",
        // value: "",
        value: "q1w2e3r4!@",
        validators: [requiredValidator, passwordSecurityValidator],
        errors: {},
      },
      confirmPassword: {
        label: "담당자 비밀번호 확인",
        type: "password",
        // value: "",
        value: "q1w2e3r4!@",
        validators: [requiredValidator, confirmPasswordValidator],
        errors: {},
      },
    })
    const managerFormError = ref("")
    const formData = computed(() => {
      return { ...officeFormData, ...managerFormData }
    })

    const officeFormIsValid = computed(() => {
      return Boolean(
        officeFormData.officeName.value &&
          !Object.keys(officeFormData.officeName.errors).length
      )
    })

    const checkOfficeNameIsValid = async () => {
      try {
        await axios.get(
          `/api/v1/office/duplicate/${officeFormData.officeName.value}`
        )
        step.value = 2
      } catch (error) {
        if (error.response.status === 409) {
          officeFormError.value = "이미 회사로 등록된 이름이에요 😅"
        } else {
          alert(error)
        }
      }
    }

    const managerFormIsFilled = computed(() => {
      return Object.keys(managerFormData).every(
        key => managerFormData[key].value
      )
    })

    const managerFormNoError = computed(() => {
      return Object.keys(managerFormData).every(key => {
        return !Object.keys(managerFormData[key].errors).length
      })
    })

    const formIsValid = computed(() => {
      return (
        officeFormIsValid.value &&
        managerFormIsFilled.value &&
        managerFormNoError.value &&
        term.value
      )
    })

    const term = ref(false)

    const registerOffice = async () => {
      const submitData = { term: term.value }
      Object.keys(formData.value).forEach(
        key => (submitData[key] = formData.value[key].value)
      )
      try {
        await store.dispatch("office/registerOffice", submitData)
      } catch (error) {
        if (error.response.status === 409) {
          managerFormError.value = "이미 존재하는 이메일이에요!"
        } else {
          console.log(error)
          alert(error)
        }
      }
    }

    return {
      step,
      officeFormData,
      officeFormError,
      officeFormIsValid,
      checkOfficeNameIsValid,
      managerFormData,
      managerFormError,
      formIsValid,
      term,
      registerOffice,
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
        @apply bg-blue-600 rounded-xl py-3 text-white font-medium;

        &.disabled {
          @apply bg-gray-400;
        }

        &.error {
          @apply bg-red-600;
        }
      }

      .submit-error {
        @apply text-red-600 text-sm font-medium mx-auto;
      }
    }
  }
}
</style>
