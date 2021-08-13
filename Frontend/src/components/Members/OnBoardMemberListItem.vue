<template>
  <div class="wrapper">
    <div class="member-item">
      <div class="profile-container">
        <span>{{ member.name[0] }}</span>
      </div>
      <div class="infos">
        <p class="font-bold text-gray-900">{{ member.name }}</p>
        <p>{{ member.deptEntity.deptName }}</p>
        <p>{{ member.jobEntity.jobName }}</p>
        <p class="select-text">{{ member.email }}</p>
      </div>
    </div>
    <div class="flex">
      <button class="delete-btn" @click="deleteOnBoardMember">삭제</button>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex"

export default {
  name: "OnBoardMemberListItem",
  props: {
    member: Object,
  },
  setup(props) {
    const store = useStore()

    const deleteOnBoardMember = async () => {
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
    return { deleteOnBoardMember }
  },
}
</script>

<style scoped lang="scss">
.wrapper {
  @apply flex justify-between p-4;

  &:hover {
    @apply bg-gray-100 select-none;
  }

  .member-item {
    @apply flex items-center;

    .profile-container {
      @apply w-10 h-10 rounded-full overflow-hidden bg-blue-600 flex items-center justify-center text-sm font-bold mr-4 text-white;
    }
    .infos {
      @apply flex items-center text-sm text-gray-400;

      p {
        @apply text-lg;
      }
      p:not(:last-child) {
        @apply mr-2;
      }
    }
  }

  .delete-btn {
    @apply bg-red-600 rounded px-4 py-2 h-auto self-center text-white;

    &:hover {
      @apply font-bold;
    }
  }
}
</style>
