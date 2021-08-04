<template>
  <div class="wrapper">
    <div class="inner">
      <h1 class="text-3xl font-bold">비밀번호 변경</h1>
      <div>
        <p>
          기존에 사용하던 비밀번호와 변경할 비밀번호를 입력하여, 비밀번호를
          변경할 수 있어요.
        </p>
      </div>
      <TextInput
        v-for="(field, key) in formData"
        :key="key"
        v-model="field.value"
        :name="key"
        :field="field"
        :formData="formData"
        @update:validate="handleUpdateValidate(formData, $event)"
      />
      <button
        class="change-btn"
        :class="{ disabled: !formIsValid }"
        @click="changePassword"
      >
        비밀번호 변경 완료
      </button>
    </div>
  </div>
</template>

<script>
import { computed, reactive } from "vue"
import TextInput from "@/components/TextInput.vue"
import {
  requiredValidator,
  confirmPasswordValidator,
  passwordSecurityValidator,
  handleUpdateValidate,
} from "@/lib/validator"
import { useStore } from "vuex"
import { useRouter } from "vue-router"

export default {
  name: "ChangePassword",
  components: {
    TextInput,
  },
  setup() {
    const store = useStore()
    const router = useRouter()

    const formData = reactive({
      oldPassword: {
        label: "이전 비밀번호",
        type: "password",
        value: store.state.auth.shouldChangePassword ? "asd" : "",
        validators: [requiredValidator],
        errors: {},
      },
      password: {
        label: "새 비밀번호",
        type: "password",
        value: "",
        validators: [requiredValidator, passwordSecurityValidator],
        errors: {},
      },
      confirmPassword: {
        label: "새 비밀번호 확인",
        type: "password",
        value: "",
        validators: [requiredValidator, confirmPasswordValidator],
        errors: {},
      },
    })

    const changePassword = async () => {
      try {
        await store.dispatch("auth/changePassword", {
          currentPassword: formData.oldPassword.value,
          newPassword: formData.password.value,
        })
        router.push({ name: "Office" })
      } catch (error) {
        console.log(error)
        console.log(error.message)
      }
    }

    const formIsFilled = computed(() => {
      return Object.keys(formData).every(key => {
        return formData[key].value
      })
    })

    const formNoError = computed(() => {
      return Object.keys(formData).every(key => {
        return !Object.keys(formData[key].errors).length
      })
    })

    const formIsValid = computed(() => {
      return formIsFilled.value && formNoError.value
    })

    return {
      formData,
      formIsValid,
      changePassword,
      handleUpdateValidate,
    }
  },
}
</script>

<style lang="scss" scoped>
.wrapper {
  @apply h-screen bg-gray-100 flex items-center justify-center;

  .inner {
    @apply bg-white shadow-lg p-8 grid gap-4 rounded-lg w-96;

    .change-btn {
      @apply bg-blue-400 text-white rounded-lg py-3;
    }
    .disabled {
      @apply bg-gray-400;
    }
  }
}
</style>
