<template>
  <teleport to="body">
    <div class="backdrop">
      <div class="modal-container">
        <div class="header">
          <h3 class="title">구성원 추가</h3>
          <p class="detail">구미 2반 7팀의 구성원을 추가합니다.</p>
        </div>
        <div class="add-form">
          <div class="input-list">
            <TextInput
              v-for="(field, key) in formData"
              :key="key"
              :label="field.label"
              v-model="field.value"
              :type="field.type"
            />
          </div>
          <button class="submit-btn" @click="submitForm">
            구성원 추가하기
          </button>
        </div>
      </div>
    </div>
  </teleport>
</template>

<script>
import TextInput from "@/components/Members/TextInput.vue"
import { reactive, ref } from "@vue/reactivity"

export default {
  name: "AddMemberModal",
  components: {
    TextInput,
  },
  setup() {
    const formDataIsValid = ref(false)

    const requiredValidator = key => {
      if (formData[key].value < 1) {
        formData[key].errors.required = "필수 입력 요소입니다."
        return false
      }
      formData[key].errors.required && delete formData[key].errors.required
      return true
    }

    const formData = reactive({
      name: {
        label: "이름",
        type: "text",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      email: {
        label: "이메일",
        type: "email",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      department: {
        label: "소속",
        type: "text",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      position: {
        label: "역할(직무)",
        type: "text",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      password: {
        label: "비밀번호",
        type: "password",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      confirmPassword: {
        label: "비밀번호 확인",
        type: "password",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
    })

    const validateFormData = () => {
      // 모든 field를 순회하며 검증 작업을 시작한다.
      Object.keys(formData).forEach(key => {
        const field = formData[key]
        field.validators.forEach(validator => {
          return validator(key)
        })
      })
    }

    const submitForm = () => {
      validateFormData()
      alert("검증 완료")
    }

    return {
      formData,
      submitForm,
    }
  },
}
</script>

<style scoped lang="scss">
.backdrop {
  z-index: 999;
  background: rgba(46, 46, 51, 0.6);
  @apply absolute inset-0 flex items-center justify-center;

  .modal-container {
    max-width: 480px;
    @apply p-10 bg-white shadow-lg rounded-xl w-full;

    .header {
      @apply grid gap-6 mb-10;

      .title {
        @apply text-3xl font-bold;
      }
    }

    .add-form {
      @apply grid gap-10;

      .input-list {
        @apply grid gap-4;
      }
      .submit-btn {
        @apply py-4 rounded-xl bg-gray-400 text-white font-bold;
      }
    }
  }
}
</style>
