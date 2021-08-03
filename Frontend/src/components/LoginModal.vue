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
          :name="key"
          v-model="field.value"
          :field="field"
          :formData="formData"
        />
        <router-link
          :to="{ name: 'ResetPassword' }"
          class="text-sm inline-block mr-auto"
        >
          😅비밀번호를 잊으셨나요?
        </router-link>

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
            <label class="text-sm font-medium text-gray-700" for="willStayLogin"
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
            <label
              class="text-sm font-medium text-gray-700"
              for="willRememberEamil"
              >이메일 기억하기</label
            >
          </div>
        </div>
      </div>
      <hr class="my-6" />
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
      </div>
    </template>
  </Modal>
</template>

<script>
import { reactive, ref } from "vue"
import { useStore } from "vuex"
import { useRouter } from "vue-router"
import { loginRequiredValidator, emailValidator } from "@/lib/validator"
import TextInput from "@/components/TextInput.vue"
import Modal from "@/components/Common/Modal.vue"

export default {
  name: "LoginModal",
  components: { Modal, TextInput },
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
      const res = await store.dispatch("auth/login", submitData)
      // 로그인 성공
      if (res) {
        emit("close")
        console.log(
          "shouldChangePassword: " + store.state.auth.shouldChangePassword
        )
        if (store.state.auth.shouldChangePassword) {
          console.log("라우팅 : ChangePasssword.vue")
          router.push({ name: "ChangePassword" })
        } else {
          console.log("라우팅 : Office.vue")
          router.push({ name: "Office" })
        }
      }
      // 로그인 실패
      else {
        alert("로그인 실패")
      }
    }

    return { formData, willStayLogin, willRememberEamil, login }
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
  @apply rounded-xl  py-4 bg-indigo-900 text-white font-bold;
}

.sns-container {
  @apply grid gap-4;

  .sns-btn {
    @apply py-3;
  }
}
</style>
