<template>
  <div class="h-screen bg-gray-100 flex items-center justify-center">
    <div class="bg-white shadow-lg p-8 grid gap-8 rounded-lg w-96">
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
        :name="key"
        v-model="field.value"
        :field="field"
        :formData="formData"
      />
      <button
        class="bg-gray-400 text-white rounded-lg py-3"
        @click="changePassword"
      >
        비밀번호 변경 완료
      </button>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue"
import TextInput from "@/components/TextInput.vue"
import {
  requiredValidator,
  confirmPasswordValidator,
  passwordSecurityValidator,
} from "@/lib/validator"

export default {
  name: "ChangePassword",
  components: {
    TextInput,
  },
  setup() {
    const formData = reactive({
      oldPassword: {
        label: "이전 비밀번호",
        type: "password",
        value: "",
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

    const changePassword = () => {
      console.log(formData)
    }

    return {
      formData,
      changePassword,
    }
  },
}
</script>

<style></style>
