<template>
  <aside>
    <div class="infos">
      <p class="hello-message">
        {{ userName }}님,
        <br />
        안녕하세요! 🙌
      </p>
      <div class="workinfo">
        <p class="label">오늘 근무 일정</p>
        <p class="time">09:00 - 18:00</p>
        <button
          class="work-btn"
          :class="{
            beforeStart: workType === 'beforeStart',
            doing: workType === 'doing',
            done: workType === 'done',
          }"
          @click="changeWorkType"
        >
          <span class="material-icons-outlined icon">
            alarm
          </span>
          <span v-if="workType === 'beforeStart'">업무 시작</span>
          <span v-else-if="workType === 'doing'">업무 중</span>
          <span v-else>업무 종료</span>
        </button>
      </div>
    </div>
    <hr />
    <div class="members">
      <div class="member" v-for="member in members" :key="member.userId">
        <img
          :src="
            member.profileImage ||
              `https://picsum.photos/seed/user-2-${member.userId}/40`
          "
          alt="프로필"
        />
        <div>
          <p class="name">{{ member.name }}</p>
          <p class="department">{{ member.deptEntity.deptName }}</p>
        </div>
      </div>
    </div>
  </aside>
</template>

<script>
import { computed } from "@vue/runtime-core"
import { useStore } from "vuex"
export default {
  name: "LeftAsidebar",
  setup() {
    const store = useStore()
    const userName = computed(() => store.state.auth.user.name)
    const members = computed(() => store.state.office.members)
    const commute = computed(() => store.state.auth.commute)
    const workType = computed(() => {
      if (!commute.value.comeIn) {
        return "beforeStart"
      } else if (!commute.value.comeOut) {
        return "doing"
      } else {
        return "done"
      }
    })

    const changeWorkType = () => {
      const now = new Date(Date.now())
      const currentTime = `${now.getHours()}시 ${now.getMinutes()}분`
      let confirmRes = null
      switch (workType.value) {
        case "beforeStart": {
          confirmRes = confirm(`현재 시간: ${currentTime}\n출근하시겠습니까?`)
          if (confirmRes) {
            store.dispatch("auth/commuteIn")
          }
          break
        }
        case "doing": {
          confirmRes = confirm(`현재 시간: ${currentTime}\n퇴근하시겠습니까?`)
          if (confirmRes) {
            store.dispatch("auth/commuteOut")
          }
          break
        }
        case "done": {
          alert("오늘 업무는 종료되었습니다.")
        }
      }
    }
    return {
      userName,
      members,
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

        &.beforeStart {
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
