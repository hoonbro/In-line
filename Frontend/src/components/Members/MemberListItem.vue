<template>
  <li class="user-list-item">
    <div class="icon">
      <span>Icon</span>
    </div>
    <div class="infos">
      <p class="name">{{ member.name }}</p>
      <p>{{ member.deptName }}</p>
      <p>{{ member.jobName }}</p>
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

  .icon {
    @apply w-10 h-10 rounded-full bg-green-400 flex items-center justify-center text-sm font-bold mr-8 text-white;
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
