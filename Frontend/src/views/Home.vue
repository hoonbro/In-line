<template>
  <section class="background-img">
    <div class="img-cover"></div>
    <div class="content">
      <img
        class="mx-auto"
        src="@/assets/LandingPage/logo1.png"
        alt="in-line logo"
      />
      <h1 class="text-4xl mx-auto">선 안에 있는, 사람을 잇는</h1>
    </div>
  </section>
  <!--///////////////////////////////////////////////////////////////////////-->
  <section class="py-24 border-2 bg-gray-50">
    <div class="font mb-12">
      단일 회의실에 불과했던 기존의 <br />
      가상회의 서비스를 개선한 웹 사무실 플랫폼입니다. <br />재택근무 소통
      환경과 관리자의 편의성, <br />인-라인을 통해 업무 효율성을 높여보세요!
    </div>
    <div class="flex justify-center gap-64">
      <div class="icon">
        <div class="icon-image w-24">
          <img src="@/assets/LandingPage/video-call.png" alt="화상 로고" />
        </div>
        <div class="icon-explain">
          <span></span>
        </div>
      </div>
      <div class="icon">
        <div class="icon-image w-24">
          <img src="@/assets/LandingPage/chat-box.png" alt="채팅 로고" />
        </div>
        <div class="icon-explain">
          <span></span>
        </div>
      </div>
      <div class="icon">
        <div class="icon-image w-24">
          <img src="@/assets/LandingPage/bar-chart.png" alt="인사이트 로고" />
        </div>
        <div class="icon-explain">
          <span></span>
        </div>
      </div>
    </div>
  </section>
  <!--///////////////////////////////////////////////////////////////////////-->
  <section class="section-box">
    <div class="flex justify-center gap-4">
      <div>
        <h1>Home</h1>
        <p class="item-text">
          회의실 관리부터 근무 상태 파악까지 간편하게
        </p>
      </div>
      <div class="py-12 pl-4">
        <img src="@/assets/LandingPage/home.png" alt="홈페이지 사진" />
      </div>
    </div>
  </section>
  <!--///////////////////////////////////////////////////////////////////////-->
  <section class="section-box bg-gray-50">
    <div class="flex justify-center gap-4">
      <div class="py-12 pr-4">
        <img src="@/assets/LandingPage/members.png" alt="구성원페이지 사진" />
      </div>
      <div>
        <h1>Members</h1>
        <p class="item-text">
          구성원 관리를 스마트하게
        </p>
      </div>
    </div>
  </section>
  <!--///////////////////////////////////////////////////////////////////////-->
  <section class="section-box">
    <div class="flex justify-center gap-4">
      <div>
        <h1>Admin</h1>
        <p class="item-text">인사이트 파악과 근태 관리를 손쉽게</p>
      </div>
      <div class="py-12 pl-4">
        <img src="@/assets/LandingPage/home.png" alt="홈페이지 사진" />
      </div>
    </div>
  </section>
  <!--///////////////////////////////////////////////////////////////////////-->
  <footer class="bg-gray-200">
    <div class="flex mx-auto justify-center gap-24">
      <div>
        <img class="my-10" src="@/assets/LandingPage/logo2.png" alt="" />
      </div>
      <div class="my-auto">
        <p>
          ⓒ 인-라인 | 070-1234-5678 |
          <a
            href="mailto:iamlegend_@naver.com?subject=인라인에 문의하기&body=필요한 사항이 있다면 메일을 보내주세요."
            >iamlegend_@naver.com</a
          >
        </p>
        <p>
          사업자 등록 번호 123-123-123 | 서비스 이용 약관 | 개인정보 처리 방침
        </p>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: "Home",
}
</script>

<style scoped lang="scss">
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap");

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
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    font-size: 5rem;
    color: white;
    z-index: 2;
    text-align: center;
  }
}

.font {
  @apply text-center text-4xl;
  font-family: "Noto Sans KR", sans-serif;
  font-weight: 600;
  line-height: 8vh;
}
.icon {
  .icon-image {
    @apply mx-auto mb-4;
  }
  .icon-explain {
    @apply mx-10 text-2xl;
  }
}

.section-box {
  @apply py-24 px-12;

  h1 {
    @apply text-5xl mb-4 text-blue-800 font-bold;
  }
  .item-text {
    @apply w-52 text-3xl leading-10 font-bold;
  }
  img {
    @apply border-t-2 border-r-2 shadow-md rounded-xl;
  }
}
</style>
