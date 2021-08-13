<template>
  <li class="user-list-item">
    <div class="profile-container">
      <img
        v-if="member.profileImage"
        :src="`/images/${member.profileImage}`"
        alt="프로필 이미지"
      />
      <div class="fake-img" :class="bgColor" v-else>{{ member.name[0] }}</div>
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
  setup(props) {
    const colorList = [
      "gray",
      "red",
      "yellow",
      "green",
      "teal",
      "blue",
      "indigo",
      "purple",
      "pink",
    ]
    const bgColor = `bg-${colorList[props.member.userId % 9]}-400`
    return {
      bgColor,
    }
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
    @apply w-10 h-10 rounded-full overflow-hidden text-sm font-bold mr-8 text-white;

    img {
      @apply w-full h-full object-cover;
    }
    .fake-img {
      @apply w-full h-full flex items-center justify-center;
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
