<template>
  <div class="backdrop">
    <div class="modal-container">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "BaseModal",
}
</script>

<style scoped lang="scss">
.modal-container {
  @apply p-10 bg-white shadow-lg;
}
</style>
