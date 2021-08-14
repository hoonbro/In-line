<template>
  <Modal>
    <template v-slot:modal-body>
      <!-- 로딩 스피너 -->
      <Loading v-if="loading" />
      <div class="header">
        <h3 class="title" @click="$emit('close')">구성원 추가</h3>
        <p class="detail">{{ officeName }}의 구성원을 추가합니다.</p>
      </div>
      <div class="add-form">
        <div class="input-list">
          <div v-for="(field, key) in formData" :key="key">
            <SelectInput
              v-if="key === 'dept'"
              v-model="field.value"
              :name="key"
              :field="field"
              :items="depts"
            />
            <SelectInput
              v-else-if="key === 'job'"
              v-model="field.value"
              :name="key"
              :field="field"
              :items="jobs"
            />
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
import { computed, onMounted } from "@vue/runtime-core"
import { useStore } from "vuex"
import {
  requiredValidator,
  emailValidator,
  handleUpdateValidate,
} from "@/lib/validator"
import TextInput from "@/components/TextInput.vue"
import Modal from "@/components/Common/Modal.vue"
import Loading from "@/components/Common/Loading.vue"

export default {
  name: "AddMemberModal",
  components: {
    Modal,
    TextInput,
    Loading,
  },
  setup(_, { emit }) {
    const store = useStore()

    const officeName = computed(
      () => store.state.auth.user.officeEntity.officeName
    )

    const depts = ref(null)
    const jobs = ref(null)

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
      dept: {
        label: "소속",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
      job: {
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
      Object.keys(formData).forEach(key => {
        // job과 dept는 selectInput을 위해 field 명을 변경해주어야 합니다.
        if (key === "job" || key === "dept") {
          submitData[`${key}Name`] = formData[key].value
        } else {
          submitData[key] = formData[key].value
        }
      })
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

    onMounted(async () => {
      try {
        depts.value = await store.dispatch("office/getDepts")
        jobs.value = await store.dispatch("office/getJobs")
      } catch (error) {
        console.log(error)
      }
    })

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
</style>
