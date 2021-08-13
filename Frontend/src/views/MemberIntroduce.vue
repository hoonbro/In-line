<template>
  <section class="mt-16">
    <div></div>
    <div class="wrapper">
      <div class="box"><p>선명준</p></div>
      <div class="box"><p>변지훈</p></div>
      <div class="box"><p>김병훈</p></div>
      <div class="box"><p>조여래</p></div>
      <div class="box"><p>최영수</p></div>
      <div class="box"><p>이원우</p></div>
    </div>
  </section>
</template>

<script>
export default {
  name: "MemberIntroduce",
}
</script>

<style scoped lang="scss">
.wrapper {
  @apply grid grid-cols-2 gap-16 mx-60;
  .box {
    @apply bg-blue-100;
    height: 50vh;
    border: 2px solid red;
  }
}
</style>
