<template>
  <div class="wrapper">
    <div class="member-item">
      <div class="flex items-center gap-4">
        <div class="profile-container">
          <span>{{ member.name[0] }}</span>
        </div>
        <p class="name">{{ member.name }}</p>
      </div>
      <div class="infos">
        <p class="email">{{ member.email }}</p>
        <p>{{ member.deptEntity.deptName }}</p>
        <p>{{ member.jobEntity.jobName }}</p>
      </div>
    </div>
    <button class="delete-btn" @click="deleteOnBoardMember">
      <span class="material-icons">delete</span>
    </button>
  </div>
  <ConfirmModal
    ref="confirmModal"
    :content="['등록 예정인 구성원을 삭제하시겠어요?']"
  />
</template>

<script>
import { ref } from "@vue/reactivity"
import { useStore } from "vuex"

export default {
  name: "OnBoardMemberListItem",
  props: {
    member: Object,
  },
  setup(props) {
    const store = useStore()
    const confirmModal = ref(null)

    const deleteOnBoardMember = async () => {
      const ok = await confirmModal.value.show()
      if (!ok) return

      try {
        await store.dispatch("onboard/deleteOnBoardMember", props.member)
        store.commit("landing/addAlertModalList", {
          type: "success",
          message: "성공적으로 삭제되었습니다!",
        })
      } catch (error) {
        store.commit("landing/addAlertModalList", {
          type: "error",
          message: error.message,
        })
      }
    }
    return { confirmModal, deleteOnBoardMember }
  },
}
</script>

<style scoped lang="scss">
.wrapper {
  @apply flex justify-between items-baseline p-4;

  &:hover {
    @apply bg-blue-100 select-none;
  }

  .member-item {
    @apply grid gap-2 items-center;

    .profile-container {
      @apply w-10 h-10 rounded-full overflow-hidden bg-blue-400 flex items-center justify-center text-sm font-bold text-white;
    }
    .name {
      @apply font-medium text-gray-900 text-base;
    }
    .infos {
      @apply flex gap-2 items-center text-sm text-gray-400;

      .email {
        @apply select-text text-gray-500;
      }

      p {
        @apply text-sm;
      }
    }
  }

  .delete-btn {
    @apply rounded p-2 w-10 h-10 bg-gray-50 my-auto text-gray-400 transition-all;

    &:hover {
      @apply font-bold text-white bg-red-400;
    }
  }
}
</style>
