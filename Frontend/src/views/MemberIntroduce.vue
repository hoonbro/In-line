<template>
  <div class="banner">
    <p>SSAFY 구미 2반 7팀 소개</p>
    <p>210712 ~ 210820</p>
  </div>
  <main>
    <section class="cards">
      <div v-for="member in members" :key="member" class="card">
        <div class="info-container">
          <div class="img-container">
            <img :src="member.profile" alt="개인 프로필 사진" />
            <div class="back">
              {{ member.hidden }}
            </div>
          </div>
          <div class="content-container">
            <p class="text-xl font-bold">{{ member.name }}</p>
            <!-- <p>나이: {{ member.age }}</p> -->
            <!-- 한줄 인사말..? 사용 기술스택..? -->
            <div class="tags">
              <p
                class="tag"
                :class="{
                  frontend: member.part === 'Front-end',
                  backend: member.part === 'Back-end',
                  infra: member.part === 'Infra',
                }"
              >
                {{ member.part }}
              </p>
              <p class="tag mbti">{{ member.mbti }}</p>
            </div>
          </div>
        </div>
        <div class="icons">
          <div>
            <a :href="member.github" target="_blank" rel="noopener noreferrer">
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
            <a :href="member.insta" target="_blank" rel="noopener noreferrer">
              <img class="icon" src="@/assets/Member/instagram.png" alt="" />
            </a>
          </div>
        </div>
      </div>
    </section>
    <div class="h-20"></div>
  </main>
</template>

<script>
import { reactive } from "@vue/reactivity"
export default {
  name: "MemberIntroduce",
  setup() {
    const members = reactive({
      sun: {
        name: "선명준",
        hidden: "캡틴선",
        age: "27",
        mbti: "ISFP",
        part: "Back-end",
        github: "https://github.com/SunMyoungJun",
        email: "mailto:" + "audwns101@naver.com",
        insta: "https://www.instagram.com/sunmyoungjun/",
        profile: require("@/assets/Member/smj.jpg"),
      },
      kim: {
        name: "김병훈",
        hidden: "Figma 장인",
        age: "30",
        mbti: "ISFJ",
        part: "Front-end",
        github: "https://github.com/byunghun-jake",
        email: "mailto:" + "figma@kakao.com",
        insta: "https://www.instagram.com/kim.gam.ja/",
        profile: require("@/assets/Member/kbh.jpg"),
      },
      byeon: {
        name: "변지훈",
        hidden: "지훈갓",
        age: "27",
        mbti: "INTP",
        part: "Back-end",
        github: "https://github.com/hoonze",
        email: "mailto:" + "wlgns1588@naver.com",
        insta: "https://www.instagram.com/ho_on.e/",
        profile: require("@/assets/Member/bjh.jpg"),
      },
      lee: {
        name: "이원우",
        hidden: "개발빼고 다 잘함",
        age: "28",
        mbti: "INTJ",
        part: "Front-end",
        github: "https://github.com/ruralnerd",
        email: "mailto:" + "leeww0598@gmail.com",
        insta: "https://www.instagram.com/nerd_seeker/",
        profile: require("@/assets/Member/yww.jpg"),
      },
      jo: {
        name: "조여래",
        hidden: "열애차이",
        age: "28",
        mbti: "ENTP",
        part: "Infra",
        github: "https://github.com/steven9408",
        email: "mailto:" + "whdufo@gmail.com",
        insta: "https://www.instagram.com/yeorae_insaeng",
        profile: require("@/assets/Member/jyr.jpg"),
      },
      choi: {
        name: "최영수",
        hidden: "사진보다 실물이 나음",
        age: "27",
        mbti: "INTP",
        part: "Front-end",
        github: "https://github.com/cys4585",
        email: "mailto:" + "soc4585@naver.com",
        insta: "https://www.instagram.com/choi_young_su/",
        profile: require("@/assets/Member/cys.jpg"),
      },
    })
    return { members }
  },
}
</script>

<style scoped lang="scss">
.banner {
  margin-top: 60px;
  @apply w-full h-40 bg-white text-2xl font-bold flex flex-col gap-2 items-center justify-center;
}
main {
  @apply bg-gray-50 py-10;

  .cards {
    @apply max-w-5xl px-6 mx-auto grid gap-8 grid-cols-1 sm:grid-cols-2 md:grid-cols-3;

    .card {
      @apply border-2 p-4 rounded-lg shadow-md flex flex-col gap-6 bg-white relative;

      .info-container {
        @apply flex flex-col items-center gap-2;

        .img-container {
          @apply w-48 h-48 rounded-3xl overflow-hidden relative;

          img {
            @apply w-full h-full object-cover;
          }

          .back {
            background: rgba($color: #000000, $alpha: 0.7);
            @apply absolute inset-0 justify-center items-center text-white font-bold hidden;
          }

          &:hover .back {
            @apply flex;
          }
        }

        .content-container {
          @apply flex flex-col items-center justify-center gap-2;

          .tags {
            @apply flex gap-2;

            .tag {
              @apply text-white px-2 rounded-md;

              &.frontend {
                @apply bg-red-400;
              }
              &.backend {
                @apply bg-blue-400;
              }
              &.infra {
                @apply bg-indigo-400;
              }
              &.mbti {
                @apply bg-purple-400;
              }
            }
          }
        }
      }

      .icons {
        @apply flex gap-6 justify-center;

        .icon {
          @apply w-6 h-6 mx-auto;
        }
      }
    }
  }
}
</style>
