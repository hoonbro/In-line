<template>
  <Modal>
    <template v-slot:modal-body>
      <div class="flex justify-center mb-10">
        <h1 class="text-3xl font-bold">로그인</h1>
      </div>
      <div class="local-login-container">
        <TextInput
          v-for="(field, key) in formData"
          :key="key"
          v-model="field.value"
          :name="key"
          :formData="formData"
          :field="field"
          :maxlength="field.maxlength"
          @update:validate="handleUpdateValidate(formData, $event)"
          @submit="login"
        />
        <div>
          <input
            class="mr-1"
            type="checkbox"
            id="willRememberEamil"
            v-model="willRememberEamil"
          />
          <label
            class="text-sm font-medium text-gray-700"
            for="willRememberEamil"
          >
            이메일 기억하기
          </label>
        </div>

        <button
          class="common-btn login-btn"
          :class="{ disabled: !formDataIsValid, loading: loading }"
          :disabled="!formDataIsValid"
          @click="login"
        >
          {{ loading ? "로그인 중..." : "로그인하기" }}
        </button>

        <router-link
          :to="{ name: 'ResetPassword' }"
          class="text-sm inline-block mx-auto text-gray-400"
        >
          비밀번호를 잊으셨나요?
        </router-link>
      </div>
      <!-- <hr class="my-6" />
      <div class="sns-container">
        <h2 class="text-xl font-bold">SNS 로그인</h2>
        <button
          class="common-btn sns-btn bg-yellow-400 text-yellow-900 font-medium"
        >
          카카오로 시작하기
        </button>
        <button
          class="common-btn sns-btn bg-gray-100 text-gray-600 font-medium"
        >
          구글로 시작하기
        </button>
        <button
          class="common-btn sns-btn bg-green-500 text-green-900 font-medium"
        >
          네이버로 시작하기
        </button>
      </div> -->
    </template>
  </Modal>
</template>

<script>
import { computed, reactive, ref } from "vue"
import { useStore } from "vuex"
import { useRouter } from "vue-router"
import {
  loginRequiredValidator,
  emailValidator,
  handleUpdateValidate,
} from "@/lib/validator"
import TextInput from "@/components/Members/TextInput.vue"
import Modal from "@/components/Common/Modal.vue"
import { connectStomp } from "@/lib/websocket"

export default {
  name: "LoginModal",
  components: { Modal, TextInput },
  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()

    const willRememberEamil = ref(localStorage.getItem("email") ? true : false)
    const loading = ref(false)

    const formData = reactive({
      email: {
        label: "이메일",
        type: "email",
        value: localStorage.getItem("email") || "",
        validators: [loginRequiredValidator, emailValidator],
        errors: {},
        maxlength: 100,
      },
      password: {
        label: "비밀번호",
        type: "password",
        value: "",
        validators: [loginRequiredValidator],
        errors: {},
      },
    })

    const formDataIsValid = computed(() => {
      const keys = Object.keys(formData)
      return keys.every(key => {
        const errors = Object.keys(formData[key].errors)
        return formData[key].value && !errors.length
      })
    })

    const login = async () => {
      if (!formDataIsValid) {
        return
      }
      loading.value = true
      const submitData = {}
      Object.keys(formData).forEach(key => {
        submitData[key] = formData[key].value
      })
      try {
        // 로그인
        await store.dispatch("auth/login", submitData)
        // 회원 목록 가져오기
        await store.dispatch("office/getMembers")
        // 소켓 연결
        await connectStomp(store.state.auth.user.officeId)

        if (willRememberEamil.value) {
          localStorage.setItem("email", formData.email.value)
        } else {
          localStorage.removeItem("email")
        }
        if (store.state.auth.shouldChangePassword) {
          router.push({
            name: "ChangePassword",
            params: { tempPassword: submitData.password },
          })
        } else {
          router.push({ name: "Office" })
          store.commit("landing/addAlertModalList", { message: "안녕하세요!" })
        }
      } catch (error) {
        store.commit("landing/addAlertModalList", {
          type: "error",
          message: error.message,
        })
      }
      loading.value = false
    }

    return {
      formData,
      formDataIsValid,
      willRememberEamil,
      login,
      loading,
      handleUpdateValidate,
    }
  },
}
</script>

<style scoped lang="scss">
.local-login-container {
  @apply grid gap-4 w-full;
}
.common-btn {
  @apply rounded-lg font-medium w-full;
}

.login-btn {
  @apply rounded-xl py-4 bg-indigo-600 text-white font-bold;

  &.disabled {
    @apply bg-gray-400;
  }

  &.loading {
    background: linear-gradient(to left, #818cf8 0%, #a5b4fc 50%, #f9a8d4 100%);
    background-size: 300% 300%;
    animation: gradient-animation 6s linear infinite;
  }
}

.sns-container {
  @apply grid gap-4;

  .sns-btn {
    @apply py-3;
  }
}

@keyframes gradient-animation {
  0% {
    background-position-x: 0%;
  }
  25% {
    background-position-x: 200%;
  }
  50% {
    background-position-x: 400%;
  }
  75% {
    background-position-x: 200%;
  }
  100% {
    background-position-x: 0%;
  }
}
</style>
