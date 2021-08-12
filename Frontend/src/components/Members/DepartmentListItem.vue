<template>
  <li class="department-list-item" :class="{ selected }">
    <p class="name">{{ name }}</p>
    <div class="count">
      <span>{{ count }}</span>
    </div>
  </li>
</template>

<script>
export default {
  name: "DepartmentListItem",
  props: {
    selected: Boolean,
    name: String,
    count: Number,
  },
}
</script>

<style scoped lang="scss">
.department-list-item {
  @apply border-b flex justify-between py-4 px-6 cursor-pointer select-none;

  &:hover {
    @apply bg-gray-50;
  }

  &.selected {
    @apply bg-blue-50;
  }

  .name {
    @apply font-medium;
  }
  .count {
    @apply bg-blue-100 rounded text-xs font-medium px-2 h-6 flex justify-center items-center;
  }
}
</style>
