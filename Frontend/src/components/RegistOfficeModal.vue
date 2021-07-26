<template>
  <div class="backdrop">
    <div class="modal-container">
      <h1 class="text-4xl font-bold">회사 등록</h1>
      <div class="input-list">
        <TextInput
          v-model="formData.officeName"
          label="회사 이름"
          type="text"
        />
        <hr />
        <TextInput v-model="formData.email" label="이메일" type="email" />
        <TextInput v-model="formData.department" label="소속" type="text" />
        <TextInput v-model="formData.position" label="직무" type="text" />
        <TextInput v-model="formData.name" label="이름" type="text" />
        <TextInput v-model="formData.phone" label="휴대전화" type="text" />
        <hr />
        <TextInput
          v-model="formData.password"
          label="비밀번호"
          type="password"
        />
        <TextInput
          v-model="formData.password2"
          label="비밀번호 확인"
          type="password"
        />
        <div>
          <input
            class="mr-1"
            type="checkbox"
            name="term"
            id="term"
            v-model="formData.term"
          />
          <label class="text-sm font-medium" for="term"
            >이용약관 및 개인정보처리방침 동의</label
          >
        </div>
        <button class="regist-btn" @click="registerOffice">
          회사 등록하기
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import TextInput from "@/components/TextInput.vue"
import { reactive } from "vue"
import { useStore } from "vuex"

export default {
  name: "RegistOfficeModal",
  components: { TextInput },
  setup(props, { emit }) {
    const store = useStore()

    const formData = reactive({
      officeName: "SSAFY",
      email: "",
      department: "",
      position: "",
      name: "",
      phone: "",
      password: "",
      password2: "",
      term: false,
    })

    const registerOffice = async () => {
      // 유효성 검사 여기서 ?
      const res = await store.dispatch("landing/registerOffice", formData)
      emit("close")
    }

    return {
      formData,
      store,
      registerOffice,
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

      .regist-btn {
        @apply bg-gray-400 rounded-xl py-4 text-white font-bold;
      }
    }
  }
}
</style>
