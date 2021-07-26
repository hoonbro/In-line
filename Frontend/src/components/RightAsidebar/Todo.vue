<template>
  <div class="todo-container">
    <div class="todo-form">
      <input type="text" placeholder="제목" />
      <input type="text" placeholder="내용" />
      <input type="date" placeholder="마감기한" />
    </div>
  </div>
</template>

<script>
export default {
  name: "Todo",
}
</script>

<style lang="scss" scoped>
.todo-container {
  width: 360px;
  @apply h-full overflow-hidden flex flex-col;
}
</style>
