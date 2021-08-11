<template>
  <li class="user-list-item">
    <div class="profile-container">
      <img
        v-if="member.profileImage"
        :src="`/images/${member.profileImage}`"
        alt="프로필 이미지"
      />
      <span v-else>{{ member.name[0] }}</span>
    </div>
    <div class="infos">
      <p class="name">{{ member.name }}</p>
      <p>{{ member.deptEntity.deptName }}</p>
      <p>{{ member.jobEntity.jobName }}</p>
    </div>
  </li>
</template>

<script>
export default {
  name: "MemberListItem",
  props: {
    member: Object,
  },
}
</script>

<style scoped lang="scss">
.user-list-item {
  @apply flex items-center p-4;

  &:hover {
    @apply bg-gray-100 cursor-pointer select-none;
  }

  .profile-container {
    @apply w-10 h-10 rounded-full overflow-hidden bg-blue-600 flex items-center justify-center text-sm font-bold mr-8 text-white;

    img {
      @apply w-full h-full object-cover;
    }
  }

  .infos {
    @apply flex items-center text-sm text-gray-400;

    .name {
      @apply text-base text-gray-900;
    }

    p:not(:last-child) {
      @apply mr-2;
    }
  }
}
</style>
