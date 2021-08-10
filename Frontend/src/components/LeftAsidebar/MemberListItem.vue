<template>
  <div class="member" :class="{ online: member.connected }">
    <img
      :src="
        member.profileImage
          ? `/images/${member.profileImage}`
          : `https://picsum.photos/seed/user-2-${member.userId}/40`
      "
      alt="프로필"
    />
    <div>
      <p class="name">{{ member.name }}</p>
      <p class="department">{{ member.deptEntity.deptName }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: "MemberListItem",
  props: {
    member: {
      type: Object,
    },
  },
}
</script>

<style scoped lang="scss">
.member {
  @apply flex p-2 relative rounded items-center select-none cursor-pointer;

  &.online::before {
    @apply bg-green-400;
  }

  &::before {
    content: "";
    @apply absolute z-10 top-2 bg-gray-400 left-2 w-2 h-2 rounded-full;
  }

  img {
    @apply w-9 h-9 object-cover object-center rounded-full mr-2 relative;
  }

  p {
    @apply text-sm;
  }

  .name {
    @apply mb-1;
  }

  .department {
    @apply text-gray-500;
  }
}
</style>
