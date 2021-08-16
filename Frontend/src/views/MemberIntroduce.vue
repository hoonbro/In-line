<template>
  <section class="top">
    <div class="intro text-center">
      <p>영수님</p>
      <p>영수님이 이 글을 읽고 있을 때</p>
      <p>아마 저는 자고있겠지요</p>
      <p>반응형으로 웹페이지를 만들려면</p>
      <p>가장 작은 사이즈부터 만들라고 하십니다</p>
      <p>영수님</p>
      <p>화이팅!</p>
      <p>기본 골자는</p>
      <p>이렇게 가시면 될 것 같습니다</p>
      <p>이름같은 경우는 바꾸셔도 됩니다</p>
      <p>그럼 이만</p>
      <p>하하</p>
      <p>하하</p>
      <p>하하</p>
    </div>
  </section>
  <section
    class="mx-8 mt-20 grid gap-8 grid-cols-1 md:grid-cols-2 xl:grid-cols-3"
  >
    <div
      v-for="member in members"
      :key="member"
      class="border-2 p-4 rounded-lg shadow-md"
    >
      <div class="flex">
        <div><img src="@/assets/asd.gif" alt="개인 프로필 사진" /></div>
        <div class="flex-grow grid content-evenly text-2xl font-bold">
          <p>이름: {{ member.name }}</p>
          <p>나이: {{ member.age }}</p>
          <p>역할: {{ member.part }}</p>
          <p>MBTI: {{ member.mbti }}</p>
        </div>
      </div>
      <div>
        'ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ'
      </div>
      <div class="flex justify-evenly">
        <div>
          <a :href="member.github">
            <img
              class="w-12 h-12 mx-auto"
              src="@/assets/Member/github.png"
              alt=""
            />
          </a>
        </div>
        <div>
          <a :href="member.email">
            <img
              class="w-12 h-12 mx-auto"
              src="@/assets/Member/email.png"
              alt=""
            />
          </a>
        </div>
        <div>
          <a :href="member.insta">
            <img
              class="w-12 h-12 mx-auto"
              src="@/assets/Member/instagram.png"
              alt=""
            />
          </a>
        </div>
      </div>
    </div>
  </section>
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
        mbti: "MBTI",
        part: "Back-end",
        github: "",
        email: "",
        insta: "",
      },
      kim: {
        name: "김병훈",
        age: "29",
        mbti: "MBTI",
        part: "Front-end",
        github: "",
        email: "",
        insta: "",
      },
      byeon: {
        name: "변지훈",
        age: "27",
        mbti: "MBTI",
        part: "Back-end",
        github: "",
        email: "",
        insta: "",
      },
      lee: {
        name: "이원우",
        age: "28",
        mbti: "MBTI",
        part: "Front-end",
        github: "",
        email: "",
        insta: "",
      },
      jo: {
        name: "조여래",
        age: "28",
        mbti: "MBTI",
        part: "Infra",
        github: "",
        email: "",
        insta: "",
      },
      choi: {
        name: "최영수",
        age: "27",
        mbti: "MBTI",
        part: "Front-end",
        github: "",
        email: "",
        insta: "",
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
</style>
