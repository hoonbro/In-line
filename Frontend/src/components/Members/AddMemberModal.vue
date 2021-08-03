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
            v-model="field.value"
            :name="key"
            :formData="formData"
            :field="field"
            @update:validate="handleUpdateValidate(formData, $event)"
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
import { useStore } from "vuex"
import {
  requiredValidator,
  emailValidator,
  handleUpdateValidate,
} from "@/lib/validator2"
import TextInput from "@/components/TextInput2.vue"
import Modal from "@/components/Common/Modal.vue"

export default {
  name: "AddMemberModal",
  components: {
    Modal,
    TextInput,
  },
  setup(_, { emit }) {
    const store = useStore()
    const formData = reactive({
      name: {
        label: "이름",
        type: "text",
        value: "김병훈",
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
        value: "인사",
        validators: [requiredValidator],
        errors: {},
      },
      jobName: {
        label: "역할",
        type: "text",
        value: "사원",
        validators: [requiredValidator],
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

    const submitForm = async () => {
      const submitData = {
        officeId: store.state.auth.user.officeId,
      }
      Object.keys(formData).forEach(key => {
        submitData[key] = formData[key].value
      })
      try {
        await axios({
          url: "/api/v1/on-board/user",
          method: "POST",
          headers: {
            accessToken: store.state.auth.accessToken,
          },
          data: submitData,
        })
        alert("전송 성공")
      } catch (error) {
        alert("전송 실패")
        console.log(error)
      }
    }

    return {
      formData,
      submitForm,
      allFieldIsFilled,
      allFieldDoesNotHaveError,
      formIsValid,
      handleUpdateValidate,
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
