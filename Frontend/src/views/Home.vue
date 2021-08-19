<template>
  <section class="background-img">
    <div class="img-cover"></div>
    <div class="content">
      <img
        class="mx-auto"
        src="@/assets/LandingPage/logo1.png"
        alt="in-line logo"
      />
      <h1 class="text-xl lg:text-3xl font-bold mx-auto">
        선 안에 있는, 사람을 잇는
      </h1>
    </div>
  </section>
  <!--///////////////////////////////////////////////////////////////////////-->
  <section
    class="flex flex-col bg-gray-50 items-center justify-center gap-10 py-20"
  >
    <div class="mx-4">
      <p class="sub-text">
        단일 회의실에 불과했던<br />
        기존의 가상회의 서비스를 개선한<br />
        <span class="text-blue-600">웹 사무실 플랫폼</span>입니다.<br />
        재택근무 환경과 관리자의 편의성,<br />
        <span class="text-blue-600">인라인</span>으로 업무 효율성을 높여보세요!
      </p>
    </div>
    <!-- <div class="flex justify-between w-full">
      <div class="icon">
        <div class="icon-image">
          <img src="@/assets/LandingPage/video-call.png" alt="화상 로고" />
        </div>
        <div class="icon-explain">
          <span></span>
        </div>
      </div>
      <div class="icon">
        <div class="icon-image">
          <img src="@/assets/LandingPage/chat-box.png" alt="채팅 로고" />
        </div>
        <div class="icon-explain">
          <span></span>
        </div>
      </div>
      <div class="icon">
        <div class="icon-image">
          <img src="@/assets/LandingPage/bar-chart.png" alt="인사이트 로고" />
        </div>
        <div class="icon-explain">
          <span></span>
        </div>
      </div>
    </div> -->
  </section>
  <!--///////////////////////////////////////////////////////////////////////-->
  <div class="container mx-auto">
    <section class="content-section">
      <div class="section__inner">
        <header>
          <h1>사무실</h1>
          <p class="">
            회의실 관리부터<br />
            근무 상태 파악까지<br />
            간편하게
          </p>
        </header>
        <div class="img-container">
          <img
            class="lg:hidden first"
            src="@/assets/LandingPage/office_mobile-1.png"
            alt="홈페이지 사진1"
            data-aos="fade-up"
            data-aos-duration="1000"
            data-aos-anchor-placement="bottom-bottom"
          />
          <img
            class="lg:hidden second"
            src="@/assets/LandingPage/office_mobile-2.png"
            alt="홈페이지 사진2"
            data-aos="fade-up"
            data-aos-duration="1000"
            data-aos-anchor-placement="bottom-bottom"
          />
          <img
            class="hidden lg:block first"
            src="@/assets/LandingPage/office_pc-1.png"
            alt="홈페이지 사진1"
            data-aos="fade-up"
            data-aos-duration="1000"
            data-aos-anchor-placement="bottom-bottom"
          />
          <img
            class="hidden lg:block second"
            src="@/assets/LandingPage/office_pc-2.png"
            alt="홈페이지 사진2"
            data-aos="fade-up"
            data-aos-duration="1000"
            data-aos-anchor-placement="bottom-bottom"
          />
        </div>
      </div>
    </section>
    <!--///////////////////////////////////////////////////////////////////////-->
    <section class="content-section">
      <div class="section__inner">
        <header>
          <h1>구성원</h1>
          <p class="item-text">
            구성원 관리를<br />
            스마트하게
          </p>
        </header>
        <div class="img-container">
          <img
            class="lg:hidden first"
            src="@/assets/LandingPage/member_mobile-1.png"
            alt="홈페이지 사진1"
            data-aos="fade-right"
            data-aos-duration="1000"
            data-aos-anchor-placement="bottom-bottom"
          />
          <img
            class="lg:hidden second"
            src="@/assets/LandingPage/member_mobile-2.png"
            alt="홈페이지 사진2"
            data-aos="fade-left"
            data-aos-duration="1000"
            data-aos-anchor-placement="bottom-bottom"
          />
          <img
            class="hidden lg:block first"
            src="@/assets/LandingPage/member_pc-1.png"
            alt="홈페이지 사진1"
            data-aos="fade-right"
            data-aos-duration="1000"
            data-aos-anchor-placement="bottom-bottom"
          />
          <img
            class="hidden lg:block second"
            src="@/assets/LandingPage/member_pc-2.png"
            alt="홈페이지 사진2"
            data-aos="fade-left"
            data-aos-duration="1000"
            data-aos-anchor-placement="bottom-bottom"
          />
        </div>
      </div>
    </section>
    <!--///////////////////////////////////////////////////////////////////////-->
    <section class="bg-gray-50 content-section">
      <div class="section__inner">
        <header>
          <h1>관리</h1>
          <p>
            인사이트 파악과<br />
            근태 관리를 손쉽게
          </p>
        </header>
        <div class="img-container">
          <img
            class="lg:hidden first"
            src="@/assets/LandingPage/admin_mobile-1.png"
            alt="홈페이지 사진1"
            data-aos="fade-up-right"
            data-aos-duration="1000"
            data-aos-anchor-placement="bottom-bottom"
          />
          <img
            class="lg:hidden second"
            src="@/assets/LandingPage/admin_mobile-2.png"
            alt="홈페이지 사진2"
            data-aos="fade-up-left"
            data-aos-duration="1000"
            data-aos-anchor-placement="bottom-bottom"
          />
          <img
            class="hidden lg:block first"
            src="@/assets/LandingPage/admin_pc-1.png"
            alt="홈페이지 사진1"
            data-aos="fade-up-right"
            data-aos-duration="1000"
            data-aos-anchor-placement="bottom-bottom"
          />
          <img
            class="hidden lg:block second"
            src="@/assets/LandingPage/admin_pc-2.png"
            alt="홈페이지 사진2"
            data-aos="fade-up-left"
            data-aos-duration="1000"
            data-aos-anchor-placement="bottom-bottom"
          />
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import AOS from "aos"

export default {
  name: "Home",
  setup() {
    AOS.init()
  },
}
</script>

<style scoped lang="scss">
/* image animation */
.img-enter-from,
.img-leave-to {
  transform: translateY(40px);
  @apply opacity-0;
}

.img-enter-active,
.img-leave-active {
  @apply transition-all duration-1000 delay-100;
}

.background-img {
  position: relative;
  background-position: center;
  background-image: url("../assets/LandingPage/handhand.png");
  height: 100vh;
  background-size: cover;
  .img-cover {
    position: absolute;
    height: 100%;
    width: 100%;
    background-color: rgba(0, 0, 0, 0.7);
    z-index: 1;
  }
  .content {
    transform: translate(-50%, -50%);
    @apply absolute top-1/2 left-1/2 text-7xl text-white z-10 text-center w-full;
  }
}

.sub-text {
  word-break: keep-all;
  white-space: pre-wrap;
  @apply text-center font-bold text-xl lg:text-2xl;
}

.icon {
  .icon-image {
    @apply mx-auto w-20 h-20;
  }
  .icon-explain {
    @apply mx-10 text-2xl;
  }
}

.content-section {
  @apply flex flex-col gap-4 py-20 lg:py-40 h-screen;

  .section__inner {
    @apply mx-4 grid gap-10;

    header {
      @apply grid gap-2;

      h1 {
        @apply text-blue-600 text-xl font-bold;
      }

      p {
        @apply text-2xl font-bold md:text-3xl;
      }
    }

    .img-container {
      @apply relative;

      img {
        @apply max-w-xs md:max-w-md lg:max-w-xl xl:max-w-3xl 2xl:max-w-4xl shadow-2xl;
      }

      img.first {
        @apply h-96 md:h-auto absolute -top-6 md:-top-32 right-0;
      }

      img.second {
        @apply h-96 md:h-auto absolute top-10 left-0;
      }
    }
  }
}
</style>
