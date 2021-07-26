<template>
  <aside>
    <div class="infos">
      <p class="hello-message">
        김병훈님,
        <br />
        안녕하세요! 🙌
      </p>
      <div class="workinfo">
        <p class="label">오늘 근무 일정</p>
        <p class="time">09:00 - 18:00</p>
        <button
          class="work-btn"
          :class="{
            start: workType === 'start',
            doing: workType === 'doing',
            done: workType === 'done',
          }"
          @click="changeWorkType"
        >
          <span class="material-icons-outlined icon">
            alarm
          </span>
          <span v-if="workType === 'start'">업무 시작</span>
          <span v-else-if="workType === 'doing'">업무 중</span>
          <span v-else>업무 종료</span>
        </button>
      </div>
    </div>
    <hr />
    <div class="members">
      <div class="member online" v-for="i in 4" :key="i">
        <img src="https://picsum.photos/200/200" alt="프로필" />
        <div>
          <p class="name">김병훈</p>
          <p class="department">Develop</p>
        </div>
      </div>
      <div class="member" v-for="i in 10" :key="i">
        <img src="https://picsum.photos/200/200" alt="프로필" />
        <div>
          <p class="name">김병훈</p>
          <p class="department">Develop</p>
        </div>
      </div>
    </div>
  </aside>
</template>

<script>
import { ref } from "@vue/reactivity"
export default {
  name: "RightAsidebar",
  setup() {
    const workType = ref("start")

    const changeWorkType = () => {
      const workTypes = ["start", "doing", "done"]
      const current = workTypes.findIndex(item => {
        return item === workType.value
      })
      workType.value = workTypes[(current + 1) % 3]
    }
    return {
      workType,
      changeWorkType,
    }
  },
}
</script>

<style scoped lang="scss">
aside {
  width: 240px;
  @apply bg-gray-50 border-r border-gray-300 py-8 px-6 overflow-auto h-full flex flex-col;

  .infos {
    @apply grid gap-6;

    .hello-message {
      @apply text-lg font-bold;
    }

    .workinfo {
      p {
        @apply text-lg;
      }

      .label {
        @apply font-bold mb-1;
      }

      .time {
        @apply font-medium mb-4;
      }

      .work-btn {
        @apply flex items-center justify-center text-sm font-bold w-full py-2 border rounded outline-none;

        .icon {
          font-size: 20px;
          @apply mr-2;
        }

        &.start {
          @apply border-blue-600 text-blue-600 bg-blue-100;
        }

        &.doing {
          @apply border-green-600 text-green-600 bg-green-100;
        }

        &.end {
          @apply border-gray-600 text-gray-600 bg-gray-100;
        }
      }
    }
  }

  hr {
    @apply w-full my-6;
  }

  .members {
    @apply grid gap-2 overflow-auto;

    .member {
      @apply flex p-2 relative rounded bg-white items-center select-none cursor-pointer;

      &::before {
        content: "";
        @apply absolute z-10 top-2 left-2 w-2 h-2 bg-gray-400 rounded-full;
      }

      &.online::before {
        @apply bg-green-400;
      }

      img {
        @apply w-9 h-9 object-cover object-center rounded-full mr-2 relative;
      }

      p {
        @apply text-sm;
      }

      .name {
        @apply mb-1;
      }

      .department {
        @apply text-gray-500;
      }
    }
  }
}
</style>
