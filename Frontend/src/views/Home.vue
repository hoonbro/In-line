<template>
  <div class="back-ground"></div>
  <section class="home">
    <!--  -->
    <article class="">
      <h2 class="title">인-라인[명사] : 선 안에 있는, 사람을 잇는</h2>
      <div class="content">
        <div class="item-img-container">
          <img src="@/assets/home-image.png" alt="" />
        </div>
        <div class="item-text-container">
          <div class="">
            <p class="item-title">In-Line</p>
            <p class="item-content">
              단일 회의실에 불과했던 기존의 <br />
              가상회의 서비스를 개선한 웹 사무실 플랫폼입니다.
            </p>
          </div>
          <div class="">
            <p class="item-title">人-Line</p>
            <p class="item-content">
              직원들의 재택근무 소통 환경을 개선하고, <br />관리자의 관리
              편의성을 높이는 서비스입니다.
            </p>
          </div>
        </div>
      </div>
    </article>
    <!--  -->
    <article
      data-aos="fade-left"
      data-aos-duration="2000"
      data-aos-delay="1000"
    >
      <h2 class="title text-right">Office</h2>
      <div class="content">
        <div class="item-text-container">
          <div class="main-explain">
            <p class="explain">
              아직도 회의실로 출근하시나요?<br />
              이제부터는 '인-라인'입니다.
            </p>
          </div>
        </div>
        <div class="item-img-container">
          <img src="@/assets/home-image2.png" alt="" />
        </div>
      </div>
    </article>
    <!--  -->
    <article
      data-aos="fade-right"
      data-aos-duration="2000"
      data-aos-delay="1000"
    >
      <h2 class="title">Room</h2>
      <div class="content">
        <div class="item-img-container">
          <img src="@/assets/home-image3.png" alt="" />
        </div>
        <div class="item-text-container">
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
    <article data-aos="fade-up" data-aos-duration="2000" data-aos-delay="1000">
      <h2 class="title text-right">Admin</h2>
      <div class="content">
        <div class="item-text-container">
          <div class="main-explain">
            <span class="explain"
              >비대면으로 불편해진 인사 관리?<br />

              오늘부터는 '인-라인'입니다.
            </span>
          </div>
        </div>
        <div class="item-img-container">
          <img src="@/assets/home-image4.png" alt="" />
        </div>
      </div>
    </article>
    <article class="grid grid-cols-3">
      <div
        class="icon"
        data-aos="zoom-in"
        data-aos-duration="3000"
        data-aos-delay="2000"
      >
        <div class="icon-image w-36">
          <img src="@/assets/video-call.png" alt="엑박" />
        </div>
        <div class="icon-explain">
          <span
            >오프라인 사무실 환경을 온라인에서 구축하기 위해 최고 수준의 화상
            회의를 지원합니다</span
          >
        </div>
      </div>
      <div
        class="icon"
        data-aos="zoom-in"
        data-aos-duration="3000"
        data-aos-delay="2500"
      >
        <div class="icon-image w-36">
          <img src="@/assets/chat-box.png" alt="엑박" />
        </div>
        <div class="icon-explain">
          <span
            >원활한 소통을 위해 팀원들의 실시간 접속 상태를 확인하고 채팅을 할
            수 있습니다.
          </span>
        </div>
      </div>
      <div
        class="icon"
        data-aos="zoom-in"
        data-aos-duration="3000"
        data-aos-delay="3000"
      >
        <div class="icon-image w-36">
          <img src="@/assets/bar-chart.png" alt="엑박" />
        </div>
        <div class="icon-explain">
          <span
            >사원들의 근태 관리, 나아가 전체 인원 관리를 쉽게 할 수 있습니다.
          </span>
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
  @apply container mt-40 px-4 mx-auto mb-20 grid gap-40;

  img {
    @apply border-2 border-black;
  }

  article {
    @apply bg-gray-100 rounded px-4 py-6 md:p-10 my-12;

    .title {
      @apply text-2xl font-bold mb-6 md:text-4xl md:mb-10;
    }

    .content {
      @apply grid gap-10 lg:grid-cols-12 lg:gap-6;
    }

    .item-img-container {
      @apply w-full rounded-xl lg:col-span-6;

      img {
        @apply rounded-lg;
      }
    }
    .icon {
      .icon-image {
        @apply mx-auto mb-4;
      }
      .icon-explain {
        @apply mx-10 text-2xl;
      }
    }

    .item-text-container {
      @apply grid gap-6 lg:col-span-6;

      .item-title {
        @apply text-xl font-bold mb-2 md:text-2xl;
      }

      .item-content {
        @apply text-lg font-medium md:text-xl;
      }
    }

    .main-explain {
      @apply text-xl font-bold md:text-2xl;
    }
  }

  .article {
    @apply bg-gray-100 rounded p-4;

    .explain {
      @apply text-6xl;
    }

    .title {
      @apply text-xl md:text-3xl font-bold;
    }

    .content {
      @apply grid grid-cols-12 h-80 gap-6;

      .main-explain {
        @apply p-1;
        line-height: 220px;
      }
      .item-left-img {
        @apply col-span-12 md:col-start-1 md:col-end-6 mt-10;
      }
      .item-right-text {
        @apply col-span-12 md:col-start-6 md:col-end-13 grid gap-4;

        .big {
          @apply md:text-6xl;
        }

        .medium {
          @apply md:text-4xl;
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
