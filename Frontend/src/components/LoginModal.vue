<template>
  <div class="backdrop" @click.self="$emit('close')">
    <div class="modal-container">
      <div class="flex justify-between">
        <h1 class="text-4xl font-bold">로그인</h1>
        <button @click="$emit('close')">
          <span class="material-icons-outlined">
            close
          </span>
        </button>
      </div>
      <div class="input-list">
        <TextInput
          v-model="formData.email.value"
          name="email"
          label="이메일"
          type="email"
          :errors="formData.email.errors"
          :validators="formData.email.validators"
        />
        <div>
          <TextInput
            v-model="formData.password.value"
            label="비밀번호"
            type="password"
            name="password"
            :errors="formData.password.errors"
            :validators="formData.password.validators"
          />
          <a href="#" class="text-sm">😅비밀번호를 잊으셨나요?</a>
        </div>
        <button class="common-btn login-btn" @click="login">
          로그인하기
        </button>
        <div class="flex justify-between">
          <div>
            <input
              class="mr-1"
              type="checkbox"
              name="willStayLogin"
              id="willStayLogin"
              v-model="willStayLogin"
            />
            <label class="text-sm font-medium" for="willStayLogin"
              >로그인 상태 유지</label
            >
          </div>
          <div>
            <input
              class="mr-1"
              type="checkbox"
              name="willRememberEamil"
              id="willRememberEamil"
              v-model="willRememberEamil"
            />
            <label class="text-sm font-medium" for="willRememberEamil"
              >이메일 기억하기</label
            >
          </div>
        </div>
        <hr />
        <h2 class="text-2xl font-bold text-center">SNS 로그인</h2>
        <button class="common-btn bg-yellow-400" @click="">
          카카오로 시작하기
        </button>
        <button class="common-btn bg-gray-100" @click="">
          구글로 시작하기
        </button>
        <button class="common-btn bg-green-500" @click="">
          네이버로 시작하기
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import TextInput from "@/components/TextInput.vue"
import { reactive, ref } from "vue"
import { useStore } from "vuex"
import { useRouter } from "vue-router"

export default {
  name: "LoginModal",
  components: { TextInput },
  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()

    const willStayLogin = ref(false)
    const willRememberEamil = ref(false)

    const requiredValidator = key => {
      if (formData[key].value < 1) {
        formData[key].errors.required =
          key === "email"
            ? "이메일을 입력해주세요."
            : "비밀번호를 입력해주세요."
        return false
      }
      delete formData[key].errors.required
      return true
    }

    const emailValidator = key => {
      if (
        !/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/.test(
          formData[key].value
        )
      ) {
        formData[key].errors.invalidEmail = "올바른 이메일 주소를 입력해주세요."
        return false
      }
      delete formData[key].errors.invalidEmail
      return true
    }

    const formData = reactive({
      email: {
        label: "이메일",
        type: "email",
        value: "",
        validators: [requiredValidator, emailValidator],
        errors: {},
      },
      password: {
        label: "비밀번호",
        type: "password",
        value: "",
        validators: [requiredValidator],
        errors: {},
      },
    })

    const login = async () => {
      const submitData = {}
      Object.keys(formData).forEach(key => {
        submitData[key] = formData[key].value
      })
      await store.dispatch("landing/login", submitData)
      emit("close")
      router.push({ name: "Office" })
    }

    return {
      formData,
      willStayLogin,
      willRememberEamil,
      login,
    }
  },
}
</script>

<style scoped lang="scss">
.backdrop {
  background: rgba(46, 46, 51, 0.6);
  @apply fixed z-50 left-0 top-0 w-full h-full flex items-center justify-center;

  .modal-container {
    @apply shadow-xl bg-white rounded-xl w-full md:w-1/2 max-w-lg p-10 grid gap-10;

    .input-list {
      @apply grid gap-4 w-full;

      .common-btn {
        @apply rounded-lg py-4 font-medium;
      }

      .login-btn {
        @apply rounded-xl bg-indigo-900 text-white font-bold;
      }
    }
  }
}
</style>
