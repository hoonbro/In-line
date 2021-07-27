<template>
  <div class="backdrop" @click.self="$emit('close')">
    <div class="modal-container">
      <div class="flex justify-between">
        <h1 class="text-4xl font-bold">회사 등록</h1>
        <button @click="$emit('close')">
          <span class="material-icons-outlined">
            close
          </span>
        </button>
      </div>
      <div class="input-list">
        <TextInput
          v-model="formData.officeName"
          label="회사 이름"
          type="text"
          :required="true"
          :shouldEmitEvent="true"
          @blur="validation($event, 'officeName')"
        />
        <hr />
        <TextInput
          v-model="formData.email"
          label="이메일"
          type="email"
          :shouldEmitEvent="true"
          @blur="validation($event, 'email')"
        />
        <TextInput
          v-model="formData.deptName"
          label="소속"
          type="text"
          :shouldEmitEvent="true"
          @blur="validation($event, 'deptName')"
        />
        <TextInput
          v-model="formData.jobname"
          label="직무"
          type="text"
          :shouldEmitEvent="true"
          @blur="validation($event, 'jobName')"
        />
        <TextInput
          v-model="formData.name"
          label="이름"
          type="text"
          :shouldEmitEvent="true"
          @blur="validation($event, 'name')"
        />
        <TextInput
          v-model="formData.phone"
          label="휴대전화"
          type="text"
          :shouldEmitEvent="true"
          @blur="validation($event, 'phone')"
        />
        <hr />
        <TextInput
          v-model="formData.password"
          label="비밀번호"
          type="password"
          :shouldEmitEvent="true"
          @blur="validation($event, 'password')"
        />
        <TextInput
          v-model="formData.password2"
          label="비밀번호 확인"
          type="password"
          :shouldEmitEvent="true"
          @blur="validation($event, 'password2')"
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
      officeName: "",
      email: "",
      deptName: "",
      jobname: "",
      name: "",
      phone: "",
      password: "",
      password2: "",
      term: false,
    })

    const registerOffice = async () => {
      const res = await store.dispatch("landing/registerOffice", formData)
      console.log("res: ", res)
      emit("close")
    }

    const validation = (event, field) => {
      const data = event.target.value
      // 유효성 검사
      // 1. 필수 입력
      //  - 모두
      if (!data) {
        // tag를 빨간색으로 바꾸고, 밑에 안내 메시지 보여주기
        //
      }
      // 2. 중복검사
      //  - officeName, email
      //    -> rest API로 검사 요청 (DB TABLE의 해당 field에 해당 data가 존재하는지)
      if (field === "officeName" || field === "email") {
        console.log("중복검사를 해야합니다.")
      }
    }

    return {
      store,
      formData,
      registerOffice,
      validation,
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
