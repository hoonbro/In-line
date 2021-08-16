<template>
  <section class="top">
    <div class="intro text-center">
      <p>프로젝트 명</p>
      <p>기술스택</p>
      <p>제작기간</p>
      <p>어쩌구저쩌구</p>
      <p>어쩌구저쩌구</p>
      <p>어쩌구저쩌구</p>
      <p>어쩌구저쩌구</p>
      <p>어쩌구저쩌구</p>
      <p>어쩌구저쩌구</p>
      <p>어쩌구저쩌구</p>
      <p>어쩌구저쩌구</p>
      <p>하하</p>
      <p>하하</p>
      <p>하하</p>
    </div>
  </section>
  <section class="cards">
    <div v-for="member in members" :key="member" class="card">
      <div class="flex">
        <div>
          <img
            class="w-60 rounded-3xl m-3"
            :src="member.profile"
            alt="개인 프로필 사진"
          />
        </div>
        <div class="profiles">
          <p>이름: {{ member.name }}</p>
          <!-- <p>나이: {{ member.age }}</p> -->
          <!-- 한줄 인사말..? 사용 기술스택..? -->
          <p>역할: {{ member.part }}</p>
          <p>MBTI: {{ member.mbti }}</p>
        </div>
      </div>
      <div>
        <hr class="mb-4" />
        <!-- '무언가 내용을 넣을까 말까' -->
      </div>
      <div class="icons">
        <div>
          <a :href="member.github">
            <img
              class="icon"
              src="@/assets/Member/github.png"
              alt="Github link"
            />
          </a>
        </div>
        <div>
          <a :href="member.email">
            <img class="icon" src="@/assets/Member/email.png" alt="" />
          </a>
        </div>
        <div>
          <a :href="member.insta">
            <img class="icon" src="@/assets/Member/instagram.png" alt="" />
          </a>
        </div>
      </div>
    </div>
  </section>
  <div class="h-20"></div>
</template>

<script>
import { reactive } from "@vue/reactivity"
export default {
  name: "MemberIntroduce",
  setup() {
    const members = reactive({
      sun: {
        name: "선명준",
        age: "27",
        mbti: "ISFP",
        part: "Back-end",
        github: "https://github.com/SunMyoungJun",
        email: "mailto:" + "audwns101@naver.com",
        insta: "https://www.instagram.com/sunmyoungjun/",
        profile: require("@/assets/Member/jello.jpeg"),
      },
      kim: {
        name: "김병훈",
        age: "30",
        mbti: "ISFJ",
        part: "Front-end",
        github: "https://github.com/byunghun-jake",
        email: "mailto:" + "figma@kakao.com",
        insta: "https://www.instagram.com/kim.gam.ja/",
        profile: require("@/assets/Member/jello.jpeg"),
      },
      byeon: {
        name: "변지훈",
        age: "27",
        mbti: "INTP",
        part: "Back-end",
        github: "https://github.com/hoonze",
        email: "mailto:" + "wlgns1588@naver.com",
        insta: "https://www.instagram.com/ho_on.e/",
        profile: require("@/assets/Member/jello.jpeg"),
      },
      lee: {
        name: "이원우",
        age: "28",
        mbti: "INTJ",
        part: "Front-end",
        github: "",
        email: "",
        insta: "",
        profile: require("@/assets/Member/jello.jpeg"),
      },
      jo: {
        name: "조여래",
        age: "28",
        mbti: "ENTP",
        part: "Infra",
        github: "https://github.com/steven9408",
        email: "mailto:" + "whdufo@gmail.com",
        insta: "https://www.instagram.com/yeorae_insaeng",
        profile: require("@/assets/Member/jello.jpeg"),
      },
      choi: {
        name: "최영수",
        age: "27",
        mbti: "INTP",
        part: "Front-end",
        github: "",
        email: "mailto:" + "",
        insta: "https://www.instagram.com/choi_young_su/",
        profile: require("@/assets/Member/jello.jpeg"),
      },
    })
    return { members }
  },
}
</script>

<style scoped lang="scss">
.top {
  @apply bg-red-50 mx-64 my-36 border-4;
  height: 50vh;
}

.cards {
  @apply mx-8 mt-20 grid gap-8 grid-cols-1 md:grid-cols-2 xl:grid-cols-3 mx-auto;
  width: 90%;

  .card {
    @apply border-2 p-2 rounded-lg shadow-md;

    .profiles {
      @apply flex-grow grid content-evenly text-2xl font-bold ml-3;
    }

    .icons {
      @apply flex justify-evenly;

      .icon {
        @apply w-12 h-12 mx-auto;
      }
    }
  }
}
</style>
