<template>
  <li class="department-list-item">
    <p class="name">{{ name }}</p>
    <p class="count">{{ count }}</p>
  </li>
</template>

<script>
export default {
  name: "DepartmentListItem",
  props: {
    name: String,
    count: Number,
  },
}
</script>

<style scoped lang="scss">
.department-list-item {
  @apply border-b flex justify-between py-4 pl-9 pr-4 cursor-pointer select-none;

  .name {
    @apply font-bold;
  }
  .count {
    @apply bg-gray-100 text-sm w-6 h-6 align-middle text-center;
  }
}
</style>
