<template>
  <!-- <section class="mt-20">
    <div class="intro text-center">
      <p>지역 반 팀</p>
      <p>프로젝트 주제 (1번 웹기술 WebRTC)</p>
      <p>프로젝트 기간 (210712 ~ 210820)</p>
      <p>서비스 명 (인라인)</p>
      <p>서비스 설명 (</p>
      <p>사용 기술</p>
    </div>
  </section> -->
  <section class="cards">
    <div v-for="member in members" :key="member" class="card">
      <div class="flex flex-col gap-2">
        <div class="flex flex-col md:flex-row">
          <div class="self-center ">
            <img
              class="m-2 w-48 h-48 rounded-full"
              :src="member.profile"
              alt="개인 프로필 사진"
            />
          </div>
          <div class="profiles flex justify-center gap-2 md:gap-0">
            <p>
              이름: <span>{{ member.name }}</span>
            </p>
            <!-- <p>나이: {{ member.age }}</p> -->
            <!-- 한줄 인사말..? 사용 기술스택..? -->
            <p>
              역할:
              <span
                class="text-white px-2 rounded-md"
                :class="{
                  frontend: member.part === 'Front-end',
                  backend: member.part === 'Back-end',
                  infra: member.part === 'Infra',
                }"
                >{{ member.part }}</span
              >
            </p>
            <p>
              MBTI:
              <span>{{ member.mbti }}</span>
            </p>
          </div>
        </div>
        <div>
          <hr />
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
        profile: require("@/assets/Member/tempImage.jpg"),
      },
      choi: {
        name: "최영수",
        age: "27",
        mbti: "INTP",
        part: "Front-end",
        github: "",
        email: "mailto:" + "",
        insta: "https://www.instagram.com/choi_young_su/",
        profile: require("@/assets/Member/tempImage.jpg"),
      },
    })
    return { members }
  },
}
</script>

<style scoped lang="scss">
.cards {
  @apply mt-20 grid gap-8 grid-cols-1 lg:grid-cols-2 2xl:grid-cols-3 mx-auto;
  width: 90vw;

  .card {
    @apply border-2 p-2 rounded-lg shadow-md;

    .profiles {
      @apply flex-grow grid content-evenly text-2xl font-bold;

      .frontend {
        @apply bg-red-400;
      }
      .backend {
        @apply bg-blue-400;
      }
      .infra {
        @apply bg-indigo-400;
      }
    }

    .icons {
      @apply flex justify-evenly;

      .icon {
        @apply w-8 h-8 mx-auto;
      }
    }
  }
}
</style>
