<template>
  <Modal>
    <template v-slot:modal-body>
      <div class="header">
        <div class="upper">
          <div class="left">
            <button v-if="step > 1" @click="step -= 1">
              <span class="material-icons-outlined">
                arrow_back
              </span>
            </button>
            <h1>회사 등록</h1>
          </div>
          <div class="step">{{ step }} / 3</div>
        </div>
        <p>인-라인에 오신 것을 환영합니다.</p>
      </div>
      <!-- step 1 : 회사이름 입력 -->
      <div v-if="step === 1">
        <div class="input-list">
          <TextInput
            v-for="(field, key) in officeFormData"
            :key="key"
            v-model="field.value"
            :name="key"
            :formData="officeFormData"
            :field="field"
            :maxlength="field.maxlength"
            @update:modelValue="officeFormError = ''"
            @update:validate="handleUpdateValidate(officeFormData, $event)"
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
      <!-- step 2 : 관리자 정보 입력 -->
      <div v-else-if="step === 2">
        <div class="input-list">
          <div v-for="(field, key) in managerFormData" :key="key">
            <select
              v-if="key === 'deptName'"
              v-model="field.value"
              class="select-box"
              :class="{ selectLabel: !field.value }"
            >
              <option disabled value="">{{ field.label }}</option>
              <option v-for="dept in depts" :key="dept.deptId">
                {{ dept.deptName }}
              </option>
            </select>

            <select
              v-else-if="key === 'jobName'"
              v-model="field.value"
              class="select-box"
              :class="{ selectLabel: !field.value }"
            >
              <option disabled value="">{{ field.label }}</option>
              <option v-for="job in jobs" :key="job.jobId">
                {{ job.jobName }}
              </option>
            </select>

            <TextInput
              v-else
              v-model="field.value"
              :name="key"
              :formData="managerFormData"
              :field="field"
              @update:modelValue="managerFormError = ''"
              @update:validate="handleUpdateValidate(managerFormData, $event)"
            />
          </div>
          <div class="grid gap-1">
            <button
              class="regist-btn"
              :class="{
                disabled: !managerFormIsValid,
                error: managerFormError,
              }"
              :disabled="!managerFormIsValid"
              @click="checkEmailIsValid"
            >
              다음 단계로
            </button>
            <p class="submit-error">
              {{ managerFormError }}
            </p>
          </div>
        </div>
      </div>
      <!-- step 3 : 비밀번호 입력 -->
      <div v-else>
        <div class="input-list">
          <div v-for="(field, key) in passwordFormData" :key="key">
            <TextInput
              v-model="field.value"
              :name="key"
              :formData="passwordFormData"
              :field="field"
              @update:modelValue="passwordFormError = ''"
              @update:validate="handleUpdateValidate(passwordFormData, $event)"
            />
          </div>
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
              :class="{ disabled: !formIsValid, error: passwordFormError }"
              :disabled="!formIsValid"
              @click="registerOffice"
            >
              회사 등록하기
            </button>
            <p class="submit-error">
              {{ passwordFormError }}
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
  handleUpdateValidate,
} from "@/lib/validator"
import TextInput from "@/components/TextInput.vue"
import Modal from "@/components/Common/Modal.vue"

export default {
  name: "RegistOfficeModal",
  components: { TextInput, Modal },
  setup(props, { emit }) {
    const store = useStore()

    const step = ref(1)

    // ===============================================================
    // step 1
    // ===============================================================
    const officeFormData = reactive({
      officeName: {
        label: "회사 이름",
        type: "text",
        value: "",
        validators: [requiredValidator],
        errors: {},
        maxlength: 20,
      },
    })

    const officeFormError = ref("")

    const officeFormIsValid = computed(() => {
      return Boolean(
        officeFormData.officeName.value &&
          !Object.keys(officeFormData.officeName.errors).length
      )
    })

    const checkOfficeNameIsValid = async () => {
      try {
        const res = await axios.get(
          `/api/v1/office/duplicate/${officeFormData.officeName.value}`
        )
        console.log(res)
        step.value = 2
      } catch (error) {
        if (error.response.status === 409) {
          officeFormError.value = "이미 회사로 등록된 이름이에요 😅"
        } else {
          alert(error)
        }
      }
    }
    // ===============================================================
    // step 2
    // ===============================================================
    store.dispatch("office/getDepts")
    store.dispatch("office/getJobs")
    const depts = computed(() => store.state.office.depts)
    const jobs = computed(() => store.state.office.jobs)

    const managerFormData = reactive({
      email: {
        label: "담당자 이메일",
        type: "email",
        value: "",
        validators: [requiredValidator, emailValidator],
        errors: {},
      },
      deptName: {
        label: "담당자 소속",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      jobName: {
        label: "담당자 직무",
        type: "text",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      name: {
        label: "담당자 이름",
        type: "text",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      phone: {
        label: "담당자 휴대전화",
        type: "text",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
    })

    const managerFormError = ref("")

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

    const managerFormIsValid = computed(() => {
      return (
        officeFormIsValid.value &&
        managerFormIsFilled.value &&
        managerFormNoError.value
      )
    })

    const checkEmailIsValid = async () => {
      try {
        // true면 중복o -> 사용 불가능
        // false면 중복x -> 사용 가능
        const res = await axios.get(
          `api/v1/users/duplicate/${managerFormData.email.value}`
        )
        console.log(res)
        step.value = 3
      } catch (error) {
        if (error.response.status === 409) {
          managerFormError.value = "이미 존재하는 이메일이에요!"
        } else {
          alert(error)
        }
      }
    }
    // ===============================================================
    // step 3
    // ===============================================================
    const passwordFormData = reactive({
      password: {
        label: "담당자 비밀번호",
        type: "password",
        value: "",
        validators: [requiredValidator, passwordSecurityValidator],
        errors: {},
      },
      confirmPassword: {
        label: "담당자 비밀번호 확인",
        type: "password",
        value: "",
        validators: [requiredValidator, confirmPasswordValidator],
        errors: {},
      },
    })
    const passwordFormError = ref("")

    const passwordFormIsFilled = computed(() => {
      return Object.keys(passwordFormData).every(
        key => passwordFormData[key].value
      )
    })

    const passwordFormNoError = computed(() => {
      return Object.keys(passwordFormData).every(key => {
        return !Object.keys(passwordFormData[key].errors).length
      })
    })

    const passwordFormIsValid = computed(() => {
      return (
        officeFormIsValid.value &&
        managerFormIsFilled.value &&
        managerFormNoError.value &&
        passwordFormIsFilled.value &&
        passwordFormNoError.value
      )
    })

    const term = ref(false)

    // ===============================================================
    // 전체 form (step 1 ~ step 3)
    // ===============================================================
    const formData = computed(() => {
      return { ...officeFormData, ...managerFormData, ...passwordFormData }
    })

    const formIsValid = computed(() => {
      return (
        officeFormIsValid.value &&
        managerFormIsFilled.value &&
        managerFormNoError.value &&
        passwordFormIsFilled.value &&
        passwordFormNoError.value &&
        term.value
      )
    })

    // ===============================================================
    // 회사 등록 api 요청
    // ===============================================================
    const registerOffice = async () => {
      const submitData = { term: term.value }
      Object.keys(formData.value).forEach(
        key => (submitData[key] = formData.value[key].value)
      )
      try {
        console.log(submitData)
        await store.dispatch("office/registerOffice", submitData)
        alert("회사 등록을 완료했어요!")
        emit("close")
      } catch (error) {
        console.log(error)
        console.log(error.response)
        if (error.response.status === 409) {
          // managerFormError.value = "이미 존재하는 이메일이에요!"
        } else {
          alert(error)
        }
      }
    }

    return {
      depts,
      jobs,
      step,
      officeFormData,
      officeFormError,
      officeFormIsValid,
      checkOfficeNameIsValid,
      managerFormData,
      managerFormError,
      managerFormIsValid,
      checkEmailIsValid,
      passwordFormData,
      passwordFormError,
      passwordFormIsFilled,
      passwordFormNoError,
      passwordFormIsValid,
      formIsValid,
      term,
      registerOffice,
      handleUpdateValidate,
    }
  },
}
</script>

<style scoped lang="scss">
.header {
  @apply grid gap-4 mb-10;

  .upper {
    @apply flex justify-between items-start;

    .left {
      @apply flex flex-col items-start gap-1;

      h1 {
        @apply text-3xl font-bold;
      }
    }
  }
  .step {
    @apply py-1 px-4 flex justify-center items-center rounded-full bg-blue-400 text-sm text-white;
  }
}

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

  .select-box {
    @apply w-full bg-gray-50 py-4 outline-none rounded-md border border-gray-300 px-3 focus:border-blue-600;
  }
  .selectLabel {
    @apply text-gray-600;
  }

  .submit-error {
    @apply text-red-600 text-sm font-medium mx-auto;
  }
}
</style>
