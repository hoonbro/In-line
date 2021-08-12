<template>
  <div class="wrapper">
    <div class="inner">
      <div class="grid gap-3">
        <h1 class="text-3xl font-bold">{{ name }}님 안녕하세요!</h1>
        <div>
          <p>{{ officeName }}의 구성원이 되기 위한 마지막 단계에요.</p>
        </div>
      </div>
      <div class="input-list">
        <TextInput
          v-for="(field, key) in formData"
          :key="key"
          :name="key"
          v-model="field.value"
          :formData="formData"
          :field="field"
          :disabled="field.disabled"
          @update:modelValue="formError = ''"
          @update:validate="handleUpdateValidate(formData, $event)"
        />
      </div>
      <button
        class="send-btn"
        :class="{ disabled: !formIsValid }"
        :disabled="!formIsValid"
        @click="signUp"
      >
        등록 완료
      </button>
    </div>
  </div>
</template>

<script>
import { reactive, ref, onMounted, computed } from "vue"
import TextInput from "@/components/TextInput.vue"
import {
  requiredValidator,
  emailValidator,
  handleUpdateValidate,
  confirmPasswordValidator,
  passwordSecurityValidator,
} from "@/lib/validator"
import { useRouter, useRoute } from "vue-router"
import { useStore } from "vuex"

export default {
  name: "Signup",
  components: {
    TextInput,
  },
  setup() {
    const router = useRouter()
    const route = useRoute()
    const store = useStore()

    const officeId = ref("")
    const officeName = ref("")
    const name = ref("")

    const formData = reactive({
      email: {
        label: "이메일",
        type: "email",
        value: "",
        validators: [requiredValidator, emailValidator],
        errors: {},
        disabled: true,
      },
      deptName: {
        label: "소속",
        value: "",
        validators: [requiredValidator],
        errors: {},
        disabled: true,
      },
      jobName: {
        label: "직무",
        type: "text",
        value: "",
        validators: [requiredValidator],
        errors: {},
        disabled: true,
      },
      phone: {
        label: "휴대전화",
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

    // onBoard에 올라가 있는 데이터 가져오기
    onMounted(async () => {
      try {
        const res = await store.dispatch(
          "onboard/getInitData",
          route.query.email
        )
        console.log(res)
        if (res.status === 200) {
          officeId.value = res.data.officeId
          officeName.value = res.data.officeName
          name.value = res.data.name
          formData.email.value = res.data.email
          formData.deptName.value = res.data.deptName
          formData.jobName.value = res.data.jobName
        }
      } catch (error) {
        console.log(error)
        // router.push({ name: "Home", params: { shouldLogin: true } })
      }
    })

    const formError = ref("")

    const formIsFilled = computed(() => {
      return Object.keys(formData).every(key => formData[key].value)
    })

    const formNoError = computed(() => {
      return Object.keys(formData).every(key => {
        return !Object.keys(formData[key].errors).length
      })
    })

    const formIsValid = computed(() => {
      return formIsFilled.value && formNoError.value
    })

    const signUp = async () => {
      const submitData = {
        email: formData.email.value,
        deptName: formData.deptName.value,
        jobName: formData.jobName.value,
        // officeId: officeId.value,
        officeName: officeName.value,
        name: name.value,
        password: formData.password.value,
        phone: formData.phone.value,
      }
      console.log(submitData)
      const res = await store.dispatch("auth/signUp", submitData)
    }

    return {
      officeName,
      name,
      formData,
      formIsValid,
      formError,
      signUp,
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

    .input-list {
      @apply grid gap-3 w-full;
    }

    .send-btn {
      @apply bg-blue-600 text-white rounded-lg py-3;

      &.disabled {
        @apply bg-gray-400;
      }
    }
  }
}
</style>
