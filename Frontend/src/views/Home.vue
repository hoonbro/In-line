<template>
  <section class="home">
    <article class="article">
      <h2 class="title">서비스 소개 1</h2>
      <div class="content">
        <div class="item-left-img"></div>
        <div class="item-right-text">
          <div class="bg-green-100"></div>
          <div class="bg-green-100"></div>
          <div class="bg-green-100"></div>
          <div class="bg-green-100"></div>
        </div>
      </div>
    </article>

    <article class="article">
      <h2 class="title">서비스 소개 2</h2>
      <div class="content">
        <div class="item-left-text">
          <div class="bg-Teal-100"></div>
          <div class="bg-Teal-100"></div>
          <div class="bg-Teal-100"></div>
          <div class="bg-Teal-100"></div>
          <div class="bg-Teal-100"></div>
        </div>
        <div class="item-right-img"></div>
      </div>
    </article>

    <article class="article">
      <h2 class="title">서비스 소개 3</h2>
      <div class="content">
        <div class="item-left-img"></div>
        <div class="item-right-text">
          <div class="bg-green-100"></div>
          <div class="bg-green-100"></div>
          <div class="bg-green-100"></div>
        </div>
      </div>
    </article>
  </section>
</template>

<script>
export default {
  name: "Home",
  components: {},
}
</script>

<style scoped lang="scss">
.home {
  @apply container mx-auto mb-20;

  .article {
    @apply mt-20;

    .title {
      @apply text-2xl font-bold mb-4;
    }

    .content {
      @apply grid grid-cols-12 h-80 gap-6;

      .item-left-img {
        @apply bg-green-200 col-start-1 col-end-4;
      }
      .item-right-text {
        @apply col-start-4 col-end-13 grid gap-4;
      }

      .item-right-img {
        @apply bg-Teal-200 col-start-10 col-end-13;
      }
      .item-left-text {
        @apply col-start-1 col-end-10 grid gap-4;
      }
    }
  }
}
</style>
