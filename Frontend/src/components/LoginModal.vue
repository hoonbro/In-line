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
        <TextInput v-model="formData.email" label="이메일" type="email" />
        <div>
          <TextInput
            v-model="formData.password"
            label="비밀번호"
            type="password"
          />
          <a href="#" class="text-sm">😒비밀번호를 까먹었나요?</a>
        </div>
        <button class="common-btn login-btn" @click="">
          로그인하기
        </button>
        <div class="flex justify-between">
          <div>
            <input
              class="mr-1"
              type="checkbox"
              name="isStayLogIn"
              id="isStayLogIn"
              v-model="formData.isStayLogIn"
            />
            <label class="text-sm font-medium" for="isStayLogIn"
              >로그인 상태 유지</label
            >
          </div>
          <div>
            <input
              class="mr-1"
              type="checkbox"
              name="isRemeberEmail"
              id="isRemeberEmail"
              v-model="formData.isRemeberEmail"
            />
            <label class="text-sm font-medium" for="isRemeberEmail"
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
import { reactive } from "vue"

export default {
  name: "LoginModal",
  components: { TextInput },
  setup() {
    const formData = reactive({
      officeName: "SSAFY",
      email: "",
      department: "",
      position: "",
      name: "",
      phone: "",
      password: "",
      password2: "",
      isStayLogin: false,
      isRemeberEmail: false,
    })

    return {
      formData,
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
