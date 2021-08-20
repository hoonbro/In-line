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
  width: 300px;
  @apply p-4 shadow-md rounded select-none;

  &.success {
    @apply bg-blue-500 text-white font-bold;
  }

  &.error {
    @apply bg-red-400 text-white font-bold;
  }

  &.warning {
    @apply bg-yellow-400 text-gray-900 font-bold;
  }
}
</style>
