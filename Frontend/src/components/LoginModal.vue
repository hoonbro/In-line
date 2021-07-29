<template>
  <div class="backdrop" @click.self="$emit('close')">
    <div class="modal-container">
      <div class="flex justify-center">
        <h1 class="text-3xl font-bold">로그인</h1>
      </div>
      <div class="input-list">
        <TextInput
          v-for="(field, key) in formData"
          :key="key"
          :name="key"
          v-model="field.value"
          :field="field"
          :formData="formData"
        />
        <div>
          <router-link to="#" class="text-sm">
            😅비밀번호를 잊으셨나요?
          </router-link>
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
        <button class="common-btn bg-yellow-400">
          카카오로 시작하기
        </button>
        <button class="common-btn bg-gray-100">
          구글로 시작하기
        </button>
        <button class="common-btn bg-green-500">
          네이버로 시작하기
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, ref } from "vue"
import { useStore } from "vuex"
import { useRouter } from "vue-router"
import { loginRequiredValidator, emailValidator } from "@/lib/validator"
import TextInput from "@/components/TextInput.vue"

export default {
  name: "LoginModal",
  components: { TextInput },
  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()

    const willStayLogin = ref(false)
    const willRememberEamil = ref(false)

    const formData = reactive({
      email: {
        label: "이메일",
        type: "email",
        value: "",
        validators: [loginRequiredValidator, emailValidator],
        errors: {},
      },
      password: {
        label: "비밀번호",
        type: "password",
        value: "",
        validators: [loginRequiredValidator],
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
    @apply shadow-xl bg-white rounded-xl w-full md:w-1/2 max-w-lg py-16 px-20 grid gap-10;

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
