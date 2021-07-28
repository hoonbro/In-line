<template>
  <div class="backdrop" @click.self="$emit('close')">
    <div class="modal-container">
      <div class="flex justify-between">
        <h1 class="text-4xl font-bold">회사 등록</h1>
        <button @click="$emit('close')">
          <span class="material-icons-outlined">
            close
          </span>
        </button>
      </div>
      <div class="input-list">
        <TextInput
          v-for="(field, key) in formData"
          :key="key"
          :name="key"
          :label="field.label"
          v-model="field.value"
          :type="field.type"
          :errors="field.errors"
          :validators="field.validators"
        />
        <!-- <TextInput
          v-model="formData.officeName"
          label="회사 이름"
          type="text"
        />
        <hr />
        <TextInput v-model="formData.email" label="이메일" type="email" />
        <TextInput v-model="formData.deptName" label="소속" type="text" />
        <TextInput v-model="formData.jobName" label="직무" type="text" />
        <TextInput v-model="formData.name" label="이름" type="text" />
        <TextInput v-model="formData.phone" label="휴대전화" type="text" />
        <hr />
        <TextInput
          v-model="formData.password"
          label="비밀번호"
          type="password"
        />
        <TextInput
          v-model="formData.confirmPassword"
          label="비밀번호 확인"
          type="password"
        /> -->
        <div>
          <input
            class="mr-1"
            type="checkbox"
            name="term"
            id="term"
            v-model="term"
          />
          <label class="text-sm font-medium" for="term"
            >이용약관 및 개인정보처리방침 동의</label
          >
        </div>
        <button class="regist-btn" @click="registerOffice">
          회사 등록하기
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import TextInput from "@/components/TextInput.vue"
import { reactive, ref } from "vue"
import { useStore } from "vuex"

export default {
  name: "RegistOfficeModal",
  components: { TextInput },
  setup(props, { emit }) {
    const store = useStore()

    const requiredValidator = key => {
      if (formData[key].value < 1) {
        formData[key].errors.required = "필수 입력 요소입니다."
        return false
      }
      delete formData[key].errors.required
      return true
    }

    const emailValidator = key => {
      if (
        !/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/.test(
          formData[key].value
        )
      ) {
        formData[key].errors.invalidEmail = "올바른 이메일 주소를 입력해주세요."
        return false
      }
      delete formData[key].errors.invalidEmail
      return true
    }

    const passwordSecurityValidator = key => {
      if (
        !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/.test(
          formData[key].value
        )
      ) {
        formData[key].errors.weekPassword =
          "대소문자, 숫자, 특수문자 조합으로 8자리 이상으로 작성하세요."
        return false
      }
      delete formData[key].errors.weekPassword
      return true
    }

    const confirmPasswordValidator = key => {
      if (formData[key].value !== formData.password.value) {
        formData[key].errors.notMatch = "비밀번호가 일치하지 않습니다."
        return false
      }
      delete formData[key].errors.notMatch
      return true
    }

    const formData = reactive({
      officeName: {
        label: "회사 이름",
        type: "text",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      email: {
        label: "이메일",
        type: "email",
        value: "",
        validators: [requiredValidator, emailValidator],
        errors: {},
      },
      deptName: {
        label: "소속",
        type: "text",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      jobName: {
        label: "직무",
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
      phone: {
        label: "휴대전화",
        type: "text",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      password: {
        label: "비밀번호",
        type: "password",
        value: "",
        validators: [requiredValidator, passwordSecurityValidator],
        errors: {},
      },
      confirmPassword: {
        label: "비밀번호 확인",
        type: "password",
        value: "",
        validators: [requiredValidator, confirmPasswordValidator],
        errors: {},
      },
    })

    const term = ref(false)

    const registerOffice = async () => {
      const submitData = { term: term.value }
      Object.keys(formData).forEach(
        key => (submitData[key] = formData[key].value)
      )
      await store.dispatch("landing/registerOffice", submitData)
      emit("close")
    }

    return {
      store,
      formData,
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

  .modal-container {
    @apply shadow-xl bg-white rounded-xl w-full md:w-1/2 max-w-lg p-10 grid gap-10;

    .input-list {
      @apply grid gap-4 w-full;

      .regist-btn {
        @apply bg-gray-400 rounded-xl py-4 text-white font-bold;
      }
    }
  }
}
</style>
