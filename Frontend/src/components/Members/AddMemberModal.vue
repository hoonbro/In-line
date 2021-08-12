<template>
  <Modal>
    <template v-slot:modal-body>
      <div v-if="loading" class="backdrop">
        <!-- 로딩 스피너 -->
        <div class="lds-spinner">
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
        </div>
      </div>
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
        <div class="grid gap-1">
          <button
            class="submit-btn"
            :class="{
              disabled: !formIsValid,
              error: formError,
            }"
            :disabled="!formIsValid"
            @click="submitForm"
          >
            구성원 추가하기
          </button>
          <p class="submit-error">{{ formError }}</p>
        </div>
      </div>
    </template>
  </Modal>
</template>

<script>
import { reactive, ref } from "@vue/reactivity"
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
    const formError = ref("")
    const loading = ref(false)
    const submitForm = async () => {
      if (!formIsValid) return
      loading.value = true
      const submitData = { officeId: store.state.auth.user.officeId }
      Object.keys(formData).forEach(
        key => (submitData[key] = formData[key].value)
      )
      try {
        await store.dispatch("onboard/registerMember", submitData)
        emit("close")
        store.commit("landing/addAlertModalList", {
          type: "success",
          message: `${submitData.name}(${submitData.email})님께 메일을 보냈습니다!`,
        })
      } catch (error) {
        formError.value = error.message
        store.commit("landing/addAlertModalList", {
          type: "error",
          message: "메일 전송 실패",
        })
        console.log(error)
      }
      loading.value = false
    }

    return {
      officeName,
      depts,
      jobs,
      formData,
      formError,
      submitForm,
      allFieldIsFilled,
      allFieldDoesNotHaveError,
      formIsValid,
      handleUpdateValidate,
      loading,
    }
  },
}
</script>

<style scoped lang="scss">
.backdrop {
  z-index: 999;
  background: rgba(46, 46, 51, 0.6);
  @apply fixed inset-0 flex items-center justify-center;
}
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

    &.error {
      @apply bg-red-600;
    }
  }

  .submit-error {
    @apply text-red-600 text-sm font-medium mx-auto;
  }
}
// 로딩 스피너
.lds-spinner {
  color: official;
  display: inline-block;
  position: relative;
  width: 80px;
  height: 80px;
}
.lds-spinner div {
  transform-origin: 40px 40px;
  animation: lds-spinner 1.2s linear infinite;
}
.lds-spinner div:after {
  content: " ";
  display: block;
  position: absolute;
  top: 3px;
  left: 37px;
  width: 6px;
  height: 18px;
  border-radius: 20%;
  background: #fff;
}
.lds-spinner div:nth-child(1) {
  transform: rotate(0deg);
  animation-delay: -1.1s;
}
.lds-spinner div:nth-child(2) {
  transform: rotate(30deg);
  animation-delay: -1s;
}
.lds-spinner div:nth-child(3) {
  transform: rotate(60deg);
  animation-delay: -0.9s;
}
.lds-spinner div:nth-child(4) {
  transform: rotate(90deg);
  animation-delay: -0.8s;
}
.lds-spinner div:nth-child(5) {
  transform: rotate(120deg);
  animation-delay: -0.7s;
}
.lds-spinner div:nth-child(6) {
  transform: rotate(150deg);
  animation-delay: -0.6s;
}
.lds-spinner div:nth-child(7) {
  transform: rotate(180deg);
  animation-delay: -0.5s;
}
.lds-spinner div:nth-child(8) {
  transform: rotate(210deg);
  animation-delay: -0.4s;
}
.lds-spinner div:nth-child(9) {
  transform: rotate(240deg);
  animation-delay: -0.3s;
}
.lds-spinner div:nth-child(10) {
  transform: rotate(270deg);
  animation-delay: -0.2s;
}
.lds-spinner div:nth-child(11) {
  transform: rotate(300deg);
  animation-delay: -0.1s;
}
.lds-spinner div:nth-child(12) {
  transform: rotate(330deg);
  animation-delay: 0s;
}
@keyframes lds-spinner {
  0% {
    opacity: 1;
  }
  100% {
    opacity: 0;
  }
}
</style>
