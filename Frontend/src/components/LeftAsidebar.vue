<template>
  <aside>
    <div class="infos">
      <p class="hello-message">
        {{ userName }}님,
        <br />
        안녕하세요! 🙌
      </p>
      <div class="workinfo">
        <div class="flex gap-2">
          <button
            class="work-btn"
            :class="{ comein: comeInTime }"
            @click="comeInOffice"
          >
            <span class="material-icons-outlined icon">
              alarm
            </span>
            <span>출근</span>
            <span>{{ comeInTime || "-" }}</span>
          </button>
          <button
            class="work-btn"
            :class="{ comeout: comeOutTime }"
            @click="comeOutOffice"
          >
            <span class="material-icons-outlined icon">
              directions_run
            </span>
            <span>퇴근</span>
            <span>{{ comeOutTime || "-" }}</span>
          </button>
        </div>
      </div>
    </div>
    <hr />
    <div class="members">
      <MemberListItem
        v-for="member in members"
        :key="member.userId"
        :member="member"
        @click="handleMemberClick(member.userId, member.name)"
      />
    </div>
  </aside>
  <!-- Commute Modal -->
  <ConfirmModal ref="confirmModal" :content="confirmModalContent" />
</template>

<script>
import { computed, ref } from "@vue/runtime-core"
import { useStore } from "vuex"
import MemberListItem from "@/components/LeftAsidebar/MemberListItem.vue"

export default {
  name: "LeftAsidebar",
  components: {
    MemberListItem,
  },
  emits: ["click:openTodoModal"],
  setup(_, { emit }) {
    const store = useStore()
    const userName = computed(() => store.state.auth.user.name)
    const members = computed(
      () => store.getters["office/sortedMembersByOnline"]
    )
    const commute = computed(() => store.state.auth.commute)
    const comeInTime = computed(
      () =>
        `${commute.value.comeIn.slice(0, 2)}시
        ${commute.value.comeIn.slice(3, 5)}분`
    )
    const comeOutTime = computed(
      () =>
        `${commute.value.comeOut.slice(0, 2)}시
        ${commute.value.comeOut.slice(3, 5)}분`
    )

    const confirmModal = ref(null)
    const confirmModalContent = ref([])

    const workType = computed(() => {
      if (!commute.value.comeIn) {
        return "beforeStart"
      } else if (!commute.value.comeOut) {
        return "doing"
      } else {
        return "done"
      }
    })

    const comeInOffice = async () => {
      const now = new Date(Date.now())
      const currentTime = `${now.getHours()}시 ${now.getMinutes()}분`

      confirmModalContent.value = [
        `현재 시간: ${currentTime}`,
        "출근하시겠습니까?",
      ]
      confirmModal.value.isVisible = true
      const ok = await confirmModal.value.show()
      if (ok) {
        store.dispatch("auth/comeInOffice")
      }
      confirmModalContent.value = []
    }

    const comeOutOffice = async () => {
      const now = new Date(Date.now())
      const currentTime = `${now.getHours()}시 ${now.getMinutes()}분`

      confirmModalContent.value = [
        `현재 시간: ${currentTime}`,
        "퇴근하시겠습니까?",
      ]
      confirmModal.value.isVisible = true
      const ok = await confirmModal.value.show()
      if (ok) {
        store.dispatch("auth/comeOutOffice")
      }
      confirmModalContent.value = []
    }

    const handleMemberClick = (userId, userName) => {
      console.log(userId)
      emit("click:openTodoModal", { userId, userName })
    }

    return {
      userName,
      members,
      comeInTime,
      comeOutTime,
      workType,
      comeInOffice,
      comeOutOffice,
      confirmModalContent,
      confirmModal,
      handleMemberClick,
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
      @apply text-xl font-bold;
    }

    .workinfo {
      .work-btn {
        @apply grid gap-1 content-start text-sm font-bold w-full py-2 border rounded outline-none;

        .icon {
          font-size: 20px;
        }

        &.comein {
          @apply border-blue-600 text-blue-600 bg-blue-100;
        }

        &.comeout {
          @apply border-red-600 text-red-600 bg-red-100;
        }
      }
    }
  }

  hr {
    @apply w-full my-6;
  }

  .members {
    @apply grid gap-2 overflow-auto;
  }
}
</style>
