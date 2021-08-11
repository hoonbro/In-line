<template>
  <Modal>
    <template v-slot:modal-body>
      <div class="header">
        <h3 class="title" @click="$emit('close')">구성원 추가</h3>
        <p class="detail">{{ officeName }}의 구성원을 추가합니다.</p>
      </div>
      <div class="add-form">
        <div class="input-list">
          <div v-for="(field, key) in formData" :key="key">
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
              :formData="formData"
              :field="field"
              @update:validate="handleUpdateValidate(formData, $event)"
              @submit="submitForm"
            />
          </div>
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
    const store = useStore()

    const officeName = JSON.parse(localStorage.getItem("user")).officeEntity
      .officeName

    store.dispatch("office/getDepts")
    store.dispatch("office/getJobs")
    const depts = computed(() => store.state.office.depts)
    const jobs = computed(() => store.state.office.jobs)

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
      deptName: {
        label: "소속",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      jobName: {
        label: "역할",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
    })

    // ===================================================================
    // 유효성 검사
    // ===================================================================
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

    // ===================================================================
    // api 요청
    // ===================================================================
    const submitForm = async () => {
      if (!formIsValid.value) return
      const submitData = {
        officeId: store.state.auth.user.officeId,
      }
      Object.keys(formData).forEach(key => {
        submitData[key] = formData[key].value
      })
      try {
        console.log(submitData)
        const res = await axios({
          url: "/api/v1/on-board/user",
          method: "POST",
          headers: {
            accessToken: store.state.auth.accessToken,
          },
          data: submitData,
        })
        store.commit("landing/addAlertModalList", {
          message: "전송 성공",
        })
        emit("close")
      } catch (error) {
        store.commit("landing/addAlertModalList", {
          type: "error",
          message: "전송 실패",
        })
        console.log(error)
      }
    }

    return {
      officeName,
      depts,
      jobs,
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

    .select-box {
      @apply w-full bg-gray-50 py-4 outline-none rounded-md border border-gray-300 px-3 focus:border-blue-600;
    }
    .selectLabel {
      @apply text-gray-600;
    }
  }
  .submit-btn {
    @apply py-4 rounded-xl bg-blue-400 text-white font-bold;

    &.disabled {
      @apply bg-gray-400;
    }
  }
}
</style>
