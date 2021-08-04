<template>
  <teleport to="body">
    <div class="backdrop" @mousedown.self="$emit('close')">
      <div class="modal-container">
        <slot name="modal-header"></slot>
        <div class="modal-body">
          <slot name="modal-body"></slot>
        </div>
      </div>
    </div>
  </teleport>
</template>

<script>
export default {
  name: "Modal",
  emits: ["close"],
}
</script>

<style scoped lang="scss">
.backdrop {
  z-index: 999;
  background: rgba(46, 46, 51, 0.6);
  @apply fixed inset-0 flex items-center justify-center;

  .modal-container {
    max-width: 480px;
    @apply bg-white shadow-lg rounded-xl w-full;

    .modal-body {
      @apply p-10;
    }
  }
}
</style>
