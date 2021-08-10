<template>
  <div class="wrapper">
    <div class="inner">
      <div class="grid gap-3">
        <h1 class="text-3xl font-bold">OOO님 안녕하세요!</h1>
        <div>
          <p>OOO의 구성원이 되기 위한 마지막 단계에요.</p>
        </div>
      </div>
      <div class="input-list">
        <TextInput
          v-for="(field, key) in formData"
          :key="key"
          v-model="field.value"
          :name="key"
          :field="field"
          :formData="formData"
          @update:modelValue="handleInput"
          @update:validate="handleUpdateValidate(formData, $event)"
        />
      </div>
      <button
        class="send-btn"
        :class="{ disabled: !formIsValid }"
        @click="sendTempPassword"
      >
        등록 완료
      </button>
    </div>
  </div>
</template>

<script>
import { computed, reactive, ref } from "vue"
import TextInput from "@/components/TextInput.vue"
import {
  requiredValidator,
  emailValidator,
  handleUpdateValidate,
  confirmPasswordValidator,
  passwordSecurityValidator,
} from "@/lib/validator"
import { useRouter } from "vue-router"
import { useStore } from "vuex"
import axios from "axios"

export default {
  name: "RegisterMember",
  components: {
    TextInput,
  },
  setup() {
    const router = useRouter()
    const store = useStore()

    const getOnBoardUser = async () => {
      console.log("asdf")
      try {
        const res = await axios.get(`api/v1/on-board/user/soc4585@naver.com`)
        console.log(res)
        const { name, officeName, email, jobName } = res.data
      } catch (error) {
        console.log(error)
      }
    }
    getOnBoardUser()

    const formData = reactive({
      email: {
        label: "이메일",
        type: "email",
        value: "",
        validators: [requiredValidator, emailValidator],
        errors: {},
      },
      deptName: {
        label: "소속",
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

    const formIsValid = ref(false)

    const handleInput = value => {
      formIsValid.value = formData.email.validators.every(validator => {
        return validator(formData, "email", value).status
      })
    }

    const sendTempPassword = async () => {
      // api 요청
      try {
        await store.dispatch("auth/resetPassword", {
          email: formData.email.value,
        })
        alert(
          "이메일을 발송했습니다.\n임시비밀번호로 로그인 후 비밀번호를 꼭 바꿔주세요!"
        )
        router.push({
          name: "Home",
          params: { shouldLogin: true },
        })
      } catch (error) {
        alert(error.message)
      }
    }

    return {
      formData,
      formIsValid,
      handleInput,
      sendTempPassword,
      handleUpdateValidate,
    }
  },
}
</script>

<style scoped lang="scss">
.wrapper {
  @apply h-screen bg-gray-100 flex items-center justify-center;

  .inner {
    @apply bg-white shadow-lg p-8 grid gap-8 rounded-lg;

    .input-list {
      @apply grid gap-3 w-full;
    }

    .send-btn {
      @apply bg-blue-600 text-white rounded-lg py-3;

      &.disabled {
        @apply bg-gray-400;
      }
    }
  }
}
</style>
