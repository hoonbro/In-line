<template>
  <Modal>
    <template v-slot:modal-header>
      <header class="border-b border-gray-300 flex p-2 justify-end">
        <button @click="$emit('close')" class="p-2 w-10 h-10">
          <span class="material-icons">close</span>
        </button>
      </header>
    </template>
    <template v-slot:modal-body>
      <div class="mb-10 flex justify-center">
        <div class="bg-green-200 rounded-full w-32 h-32"></div>
      </div>
      <div>
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-xl font-bold">기본정보</h3>
          <button class="p-1 w-8 h-8" v-if="true">
            <span class="material-icons text-gray-500">edit</span>
          </button>
        </div>
        <ul class="grid">
          <li class="flex items-center py-4 border-b">
            <p class="font-medium text-gray-400 w-40">부서</p>
            <p class="">FE</p>
          </li>
          <li class="flex items-center py-4 border-b">
            <p class="font-medium text-gray-400 w-40">직무</p>
            <p class="">팀원</p>
          </li>
          <li class="flex items-center py-4 border-b">
            <p class="font-medium text-gray-400 w-40">이메일</p>
            <p class="">figma@kakao.com</p>
          </li>
          <li class="flex items-center py-4 border-b">
            <p class="font-medium text-gray-400 w-40">이름</p>
            <p class="">김병훈</p>
          </li>
          <li class="flex items-center py-4 border-b">
            <p class="font-medium text-gray-400 w-40">닉네임</p>
            <p class="">Vue</p>
          </li>
          <li class="flex items-center py-4 border-b">
            <p class="font-medium text-gray-400 w-40">휴대전화</p>
            <p class="">010-3294-3270</p>
          </li>
        </ul>
      </div>
      <div class="mt-4 flex justify-end" v-if="true">
        <button class="font-medium text-gray-600">비밀번호 변경</button>
      </div>
    </template>
  </Modal>
</template>

<script>
import Modal from "@/components/Common/Modal.vue"

export default {
  name: "ProfileModal",
  components: {
    Modal,
  },
}
</script>

<style scoped lang="scss"></style>
