<template>
  <div class="wrapper">
    <div class="inner">
      <h1 class="text-3xl font-bold">비밀번호 재설정</h1>
      <div>
        <p>인-라인에서 사용하는 비밀번호를 잊으셨다면,</p>
        <p>이메일을 통해 임시 비밀번호를 보내드릴게요!</p>
      </div>
      <TextInput
        v-for="(field, key) in formData"
        :key="key"
        v-model="formData.email.value"
        :name="key"
        :field="formData.email"
        :formData="formData"
        @update:modelValue="handleInput"
        @update:validate="handleUpdateValidate(formData, $event)"
        @submit="sendTempPassword"
      />
      <button
        class="send-btn"
        :class="{ disabled: !formIsValid }"
        :disabled="!formIsValid"
        @click="sendTempPassword"
      >
        임시 비밀번호 발송
      </button>
    </div>
  </div>
</template>

<script>
import { reactive, ref } from "vue"
import TextInput from "@/components/TextInput.vue"
import {
  requiredValidator,
  emailValidator,
  handleUpdateValidate,
} from "@/lib/validator"
import { useRouter } from "vue-router"
import { useStore } from "vuex"

export default {
  name: "ResetPassword",
  components: {
    TextInput,
  },
  setup() {
    const router = useRouter()
    const store = useStore()

    const formData = reactive({
      email: {
        label: "이메일 주소",
        type: "email",
        value: "",
        validators: [requiredValidator, emailValidator],
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
      if (!formIsValid.value) return
      // api 요청
      try {
        await store.dispatch("auth/resetPassword", {
          email: formData.email.value,
        })
        store.commit("landing/addAlertModalList", {
          type: "warning",
          message:
            "이메일을 발송했습니다.\n임시비밀번호로 로그인 후 비밀번호를 꼭 바꿔주세요!.",
        })
        router.push({
          name: "Home",
          params: { shouldLogin: true },
        })
      } catch (error) {
        store.commit("landing/addAlertModalList", {
          type: "error",
          message: error.message,
        })
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

    .send-btn {
      @apply bg-blue-600 text-white rounded-lg py-3;

      &.disabled {
        @apply bg-gray-400;
      }
    }
  }
}
</style>
