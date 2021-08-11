<template>
  <div class="modal-container" :class="type">
    {{ message }}
  </div>
</template>

<script>
export default {
  name: "AlertModal",
  props: {
    message: {
      type: String,
      default: "Message",
    },
    type: {
      type: String,
      default: "success",
    },
  },
}
</script>

<style scoped lang="scss">
.modal-container {
  width: 260px;
  @apply font-bold text-white p-4 shadow-md rounded select-none;

  &.success {
    @apply bg-blue-400;
  }

  &.error {
    @apply bg-red-400;
  }
}
</style>
