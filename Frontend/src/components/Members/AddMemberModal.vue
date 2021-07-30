<template>
  <Modal>
    <template v-slot:modal-body>
      <div class="header">
        <h3 class="title" @click="$emit('close')">구성원 추가</h3>
        <p class="detail">구미 2반 7팀의 구성원을 추가합니다.</p>
      </div>
      <div class="add-form">
        <div class="input-list">
          <TextInput
            v-for="(field, key) in formData"
            :key="key"
            :name="key"
            v-model="field.value"
            :field="field"
            :formData="formData"
          />
        </div>
        <button
          class="submit-btn"
          :class="{ disabled: !formIsValid }"
          :disabled="!formIsValid"
          @click="submitForm"
        >
          구성원 추가하기
        </button>
      </div>
    </template>
  </Modal>
</template>

<script>
import { reactive } from "@vue/reactivity"
import { computed } from "@vue/runtime-core"
import axios from "axios"
import {
  requiredValidator,
  emailValidator,
  confirmPasswordValidator,
  passwordSecurityValidator,
} from "@/lib/validator"
import TextInput from "@/components/TextInput.vue"
import Modal from "@/components/Common/Modal.vue"

export default {
  name: "AddMemberModal",
  components: {
    Modal,
    TextInput,
  },
  setup(_, { emit }) {
    // const requiredValidator = key => {
    //   if (formData[key].value < 1) {
    //     formData[key].errors.required = "필수 입력 요소입니다."
    //     return false
    //   }
    //   delete formData[key].errors.required
    //   return true
    // }

    // const emailValidator = key => {
    //   if (
    //     !/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/.test(
    //       formData[key].value
    //     )
    //   ) {
    //     formData[key].errors.invalidEmail = "올바른 이메일 주소를 입력해주세요."
    //     return false
    //   }
    //   delete formData[key].errors.invalidEmail
    //   return true
    // }

    // const confirmPasswordValidator = key => {
    //   if (formData[key].value !== formData.password.value) {
    //     formData[key].errors.notMatch = "비밀번호가 일치하지 않습니다."
    //     return false
    //   }
    //   delete formData[key].errors.notMatch
    //   return true
    // }

    // const passwordSecurityValidator = key => {
    //   if (
    //     !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/.test(
    //       formData[key].value
    //     )
    //   ) {
    //     formData[key].errors.weekPassword =
    //       "대소문자, 숫자, 특수문자 조합으로 8자리 이상으로 작성하세요."
    //     return false
    //   }
    //   delete formData[key].errors.weekPassword
    //   return true
    // }

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
        validators: [requiredValidator, emailValidator],
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

    const allFieldIsFilled = computed(() => {
      return Object.keys(formData).every(key => formData[key].value)
    })

    const allFieldDoesNotHaveError = computed(() => {
      return Object.keys(formData).every(
        key => !Object.keys(formData[key].errors).length
      )
    })

    const formIsValid = computed(() => {
      return allFieldIsFilled.value && allFieldDoesNotHaveError.value
    })

    const submitForm = () => {
      const submitData = {}
      Object.keys(formData).forEach(key => {
        if (key === "confirmPassword") return
        submitData[key] = formData[key].value
      })
      alert("Post 요청")
      axios({
        url: "/api/v1/users",
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        data: submitData,
      }).then(res => {
        console.log(res)
        emit("close")
      })
    }

    return {
      formData,
      submitForm,
      allFieldIsFilled,
      allFieldDoesNotHaveError,
      formIsValid,
    }
  },
}
</script>

<style scoped lang="scss">
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
    @apply py-4 rounded-xl bg-blue-400 text-white font-bold;

    &.disabled {
      @apply bg-gray-400;
    }
  }
}
</style>
