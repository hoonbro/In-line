<template>
  <div>
    <div class="admin">
      <Loading v-if="loading" />
      <template v-else>
        <div class="top-box-wrapper">
          <h1 class="">회사</h1>
          <div class="top-box">
            <div class="top-box-item large">
              <div class="flex flex-col h-full">
                <h3 class="title">
                  <span>전체 구성원 수</span>
                  <span>🤩</span>
                </h3>
                <div class="content">
                  <span>{{ totalMemberCount }}</span>
                </div>
              </div>
            </div>
            <div class="top-box-item large">
              <div class="flex flex-col h-full">
                <h3 class="title">퇴사자 수😅</h3>
                <p class="content">
                  <span>{{ retires }}</span>
                </p>
              </div>
            </div>
            <div class="top-box-item large">
              <div class="flex flex-col h-full">
                <h3 class="title">근속년수😎</h3>
                <p class="content">
                  <span>{{ years }}</span>
                </p>
              </div>
            </div>
          </div>
          <div class="top-box">
            <!-- <template
              v-for="dept in members.deptUserCount"
              :key="dept.deptName"
            >
              <div class="top-box-item">
                <div class="flex flex-col h-full">
                  <h3 class="title">{{ dept.deptName }}</h3>
                  <p class="content">
                    <span>{{ dept.cnt }}</span>
                  </p>
                </div>
              </div>
            </template> -->
          </div>
        </div>
        <div class="bottom-box-wrapper">
          <h1 class="">근태 관리</h1>
          <div class="bottom-box">
            <div class="bottom-box-item left-box">
              <div class="header">
                <span>월별</span>
              </div>
              <ul class="month-list">
                <li
                  v-for="(value, key) in attendances"
                  :key="key"
                  class="month-list-item"
                >
                  <div class="content" @click="changeActive(key)">
                    {{ key.slice(0, 4) }}년 {{ key.slice(5, 8) }}월
                  </div>
                </li>
              </ul>
            </div>
            <div class="bottom-box-item right-box">
              <div v-if="active === null" class="mx-auto mt-5">
                <img class="mx-auto" src="@/assets/asd.gif" alt="엑박" />
                <p class="text-center">
                  월별 근태 현황을 보시려면 왼쪽 목록에서 월을 눌러주세요
                </p>
              </div>
              <div v-for="(attendance, key) in attendances" :key="key">
                <template v-if="active === key">
                  <h2>{{ key.slice(0, 4) }}년 {{ key.slice(5, 8) }} 월</h2>
                  <div v-for="(item, idx) in attendance" :key="item.commuteId">
                    <template
                      v-if="
                        idx === 0 ||
                          attendance[idx].ymd !== attendance[idx - 1].ymd
                      "
                    >
                      <p class="day">
                        {{ item.ymd.slice(5, 7) }}월
                        {{ item.ymd.slice(8, 11) }}일
                      </p>
                      <div class="table-row label">
                        <p>이름</p>
                        <p>출근 시간</p>
                        <p>퇴근 시간</p>
                      </div>
                    </template>
                    <div class="table-row">
                      <p>{{ item.userEntity.name }}</p>
                      <p v-if="item.comeIn === null">{{ item.comeIn }}-</p>
                      <p v-else>
                        {{ item.comeIn.slice(0, 5) }}
                      </p>
                      <p v-if="item.comeOut === null">{{ item.comeOut }}-</p>
                      <p v-else>
                        {{ item.comeOut.slice(0, 5) }}
                      </p>
                    </div>
                  </div>
                </template>
              </div>
              <!-- 아래에서 '일'을 반복하고 '일'안에서 '멤버'반복 -->
            </div>
          </div>
        </div>
      </template>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex"
import { computed, onMounted, ref, watch } from "vue"

export default {
  name: "Admin",
  components: {},
  setup() {
    const store = useStore()
    const user = JSON.parse(localStorage.getItem("user"))
    const loading = ref(true)

    // 얜 더미야!
    // const members = 16

    const active = ref(null)

    const changeActive = index => {
      active.value = index
      console.log(active.value)
    }

    const attendances = ref()

    watch(attendances, value => {
      if (!value) return
      return Object.keys(value).forEach(key => {
        value[key].sort((a, b) => {
          a.ymd === b.ymd ? 0 : a.ymd < b.ymd ? -1 : 1
        })
      })
    })

    // const retires = computed(() => {
    //   return store.state.admin.retires
    // })
    const retires = ref(0)

    const members = ref(null)
    const totalMemberCount = ref(null)
    const memberCountOnDept = ref(null)

    const years = computed(() => {
      return store.state.admin.years
    })

    // getAttendances, getRetires, getMembers 실행
    onMounted(async () => {
      attendances.value = await store.dispatch(
        "admin/getAttendances",
        user.officeId
      )
      // await store.dispatch("admin/getRetires", user.officeId)
      await store.dispatch("admin/getMembers", user.officeId)
      await store.dispatch("admin/getYears", user.officeId)
      members.value = store.getters["admin/members"]
      totalMemberCount.value = store.getters["admin/totalMemberCount"]
      memberCountOnDept.value = store.getters["admin/memberCountOndept"]
      loading.value = false
    })
    return {
      loading,
      user,
      attendances,
      retires,
      members,
      active,
      changeActive,
      years,
      totalMemberCount,
      memberCountOnDept,
    }
  },
}
</script>

<style scoped lang="scss">
.admin {
  scrollbar-width: none;
  @apply bg-gray-100 p-10 grid gap-10 content-start overflow-auto;

  &::-webkit-scrollbar {
    display: none;
  }

  .top-box-wrapper {
    @apply grid gap-6;

    h1 {
      @apply text-3xl font-bold;
    }

    .top-box {
      @apply grid grid-cols-12 gap-4;

      .top-box-item {
        @apply h-32 lg:h-36 bg-white col-span-12 md:col-span-6 lg:col-span-4 xl:col-span-2 2xl:col-span-2 shadow rounded-lg p-4;

        &.large {
          @apply xl:col-span-3 2xl:col-span-2;
        }

        .title {
          @apply text-xl font-bold;
        }

        .content {
          @apply font-bold flex-1 flex items-end justify-end;

          span {
            @apply text-4xl;
          }
        }
      }
    }
  }

  .bottom-box-wrapper {
    @apply grid gap-6;

    h1 {
      @apply text-3xl font-bold;
    }

    .bottom-box {
      @apply grid grid-cols-12 gap-4 lg:h-96;

      .header {
        @apply p-4 text-lg font-bold bg-gray-50 border-b border-gray-300;
      }

      .content {
        @apply p-4 cursor-pointer border-b;

        &:hover {
          @apply bg-gray-50;
        }
      }
      .bottom-box-item {
        @apply bg-white shadow rounded-lg overflow-scroll;

        .hr {
          @apply bg-gray-200 h-px w-full;
        }
        .month-list {
        }
      }

      .left-box {
        @apply col-span-12 h-60 lg:col-span-4 xl:col-span-3 lg:h-full;

        .hr {
          @apply bg-gray-200 h-px w-full;
        }
      }
    }

    .right-box {
      @apply col-span-12 h-96 lg:col-span-8 lg:h-full xl:col-span-9 p-6 overflow-scroll;

      h2 {
        @apply text-2xl font-bold mb-4;
      }

      .day {
        @apply text-xl font-medium my-6 mb-2;
      }

      .table-row {
        @apply flex w-full py-2;

        &.label {
          @apply font-bold;
        }

        p {
          @apply flex-1;
        }
      }
    }
  }
}
</style>
