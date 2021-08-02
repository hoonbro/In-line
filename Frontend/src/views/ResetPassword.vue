<template>
  <div class="h-screen bg-gray-100 flex items-center justify-center">
    <div class="bg-white shadow-lg p-8 grid gap-8 rounded-lg">
      <h1 class="text-3xl font-bold">비밀번호 재설정</h1>
      <div>
        <p>인-라인에서 사용하는 비밀번호를 잊으셨다면,</p>
        <p>이메일을 통해 임시 비밀번호를 보내드릴게요!</p>
      </div>
      <TextInput
        v-model="formData.email.value"
        :name="Object.keys(formData)[0]"
        :field="formData.email"
        :formData="formData"
      />
      <button
        class="bg-gray-400 text-white rounded-lg py-3"
        @click="sendTempPassword"
      >
        임시 비밀번호 발송
      </button>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue"
import TextInput from "@/components/TextInput.vue"
import { requiredValidator, emailValidator } from "@/lib/validator"

export default {
  name: "ResetPassword",
  components: {
    TextInput,
  },
  setup() {
    const formData = reactive({
      email: {
        label: "이메일 주소",
        type: "email",
        value: "",
        validators: [requiredValidator, emailValidator],
        errors: {},
      },
    })

    const sendTempPassword = () => {
      console.log(formData.email)
      router
    }

    return {
      formData,
      sendTempPassword,
    }
  },
}
</script>

<style></style>
