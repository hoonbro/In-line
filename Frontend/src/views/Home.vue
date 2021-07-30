<template>
  <div class="back-ground"></div>
  <section class="home">
    <!--  -->
    <article class="article">
      <h2 class="title">인-라인[명사] : 선 안에 있는, 사람을 잇는</h2>
      <div class="content">
        <div class="item-left-img">
          <img src="@/assets/home-image.png" alt="" />
        </div>
        <div class="item-right-text">
          <div class="">
            <span class="big">In-Line</span><br />
            <span class="medium">
              단일 회의실에 불과했던 기존의 <br />
              가상회의 서비스를 개선한 웹 사무실 플랫폼입니다.</span
            >
          </div>
          <div class="">
            <span class="big">人-Line</span><br /><span class="medium">
              직원들의 재택근무 소통 환경을 개선하고, <br />관리자의 관리
              편의성을 높이는 서비스입니다.</span
            >
          </div>
        </div>
      </div>
    </article>
    <!--  -->
    <article class="article" data-aos="fade-left" data-aos-duration="2000">
      <h2 class="title text-right">Office</h2>
      <div class="content">
        <div class="item-left-text">
          <div class="main-explain">
            <span class="explain"
              >아직도 회의실로 출근하시나요?<br />
              이제부터는 '인-라인'입니다.</span
            >
          </div>
        </div>
        <div class="item-right-img">
          <img src="@/assets/home-image2.png" alt="" />
        </div>
      </div>
    </article>
    <!--  -->
    <article class="article" data-aos="fade-right" data-aos-duration="2000">
      <h2 class="title">Room</h2>
      <div class="content">
        <div class="item-left-img">
          <img src="@/assets/home-image3.png" alt="" />
        </div>
        <div class="item-right-text">
          <div class="main-explain">
            <span class="explain"
              >불필요하고 복잡한 UI? <br />
              지금부터는 '인-라인'입니다.</span
            >
          </div>
        </div>
      </div>
    </article>
    <!--  -->
    <article
      class="article"
      data-aos="fade-up"
      data-aos-duration="4000"
      data-aos-delay="500"
    >
      <h2 class="title text-right">Admin</h2>
      <div class="content">
        <div class="item-left-text">
          <div class="main-explain">
            <span class="explain"
              >비대면으로 불편해진 인사 관리?<br />

              오늘부터는 '인-라인'입니다.
            </span>
          </div>
        </div>
        <div class="item-right-img">
          <img src="@/assets/home-image4.png" alt="" />
        </div>
      </div>
    </article>
    <!--  -->
  </section>
</template>

<script>
import AOS from "aos"
import "aos/dist/aos.css"

AOS.init()

export default {
  name: "Home",
  components: {},
}
</script>

<style scoped lang="scss">
@import url("https://fonts.googleapis.com/css2?family=Gowun+Batang&display=swap");

* {
  font-family: "Gowun Batang", serif;
}
.home {
  @apply container mx-auto mb-20;

  img {
    @apply border-4 border-black;
  }

  .article {
    @apply mt-80 mb-56 bg-gray-50 rounded-2xl px-1;
    height: 500px;

    .explain {
      @apply text-6xl;
    }

    .title {
      @apply text-3xl font-bold mb-4 mx-4 pt-3;
    }

    .content {
      @apply grid grid-cols-12 h-80 gap-6;

      .main-explain {
        @apply p-1;
        line-height: 220px;
      }
      .item-left-img {
        @apply col-start-1 col-end-6 mt-10;
      }
      .item-right-text {
        @apply col-start-6 col-end-13 grid gap-4;

        .big {
          @apply text-6xl;
        }

        .medium {
          @apply text-4xl;
        }
      }

      .item-right-img {
        @apply col-start-8 col-end-13;
      }
      .item-left-text {
        @apply col-start-1 col-end-8 grid gap-4;
      }
    }
  }
}
</style>
