<template>
  <div class="member" :class="{ online: member.connected }">
    <img
      v-if="member.profileImage"
      :src="`/images/${member.profileImage}`"
      alt="프로필"
    />
    <div class="fake-img" :class="bgColor" v-else>{{ member.name[0] }}</div>
    <div>
      <div class="name">
        <span>{{ member.name }}</span>
        <!-- <span v-if="member.connected">({{ member.roomId }}번방)</span> -->
      </div>
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
.member {
  @apply flex p-2 relative rounded items-center select-none cursor-pointer;

  &.online::before {
    @apply bg-green-400;
  }

  &::before {
    content: "";
    @apply absolute z-10 top-2 bg-gray-400 left-2 w-2 h-2 rounded-full;
  }

  img,
  .fake-img {
    @apply w-9 h-9 object-cover object-center rounded-full mr-2 relative;
  }

  .fake-img {
    @apply flex justify-center items-center text-white font-bold;
  }

  p,
  span {
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
