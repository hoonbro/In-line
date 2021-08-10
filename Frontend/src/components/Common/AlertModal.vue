<template>
  <teleport to=".alertModalContainer">
    <transition name="fade">
      <div class="modal-container" :class="type">
        {{ message }}
      </div>
    </transition>
  </teleport>
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
/* css class for the transition */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 1s;
  position: absolute;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.modal-container {
  width: 260px;
  @apply font-bold text-white p-4 shadow-md rounded;

  &.success {
    @apply bg-blue-400;
  }

  &.error {
    @apply bg-red-400;
  }
}
</style>
