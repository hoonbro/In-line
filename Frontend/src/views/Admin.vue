<template>
  <div class="admin">
    <div class="top-box-wrapper">
      <h1 class="">회사</h1>
      <div class="top-box">
        <div class="top-box-item">
          <p>전체 구성원 수</p>
          <p>{{ members.officeUserCount }} 명</p>
        </div>
        <div class="top-box-item">
          <p>퇴사자 수</p>
          <p>{{ retires }} 명</p>
        </div>
        <div class="top-box-item">
          <ul v-for="(depts, index) in members">
            <li v-for="dept in depts">
              <p v-if="index.length !== 15">
                {{ dept.deptName }} : {{ dept.cnt }} 명
              </p>
            </li>
          </ul>
        </div>
        <div class="top-box-item"><p>어쩌구저쩌구</p></div>
      </div>
    </div>
    <div class="bottom-box-wrapper">
      <h1 class="">근태 관리</h1>
      <div class="bottom-box">
        <div class="bottom-box-item left-box">
          <div class="content"><span>월별</span></div>
          <div class="hr"></div>
          <ul class="month-list">
            <li v-for="(month, index) in attendances" class="month-list-item">
              <div class="content">
                <span class="asd cursor-pointer" @click="changeActive(index)"
                  >{{ index.slice(0, 4) }}년 {{ index.slice(5, 8) }}월</span
                >
              </div>
              <div class="hr"></div>
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
          <!-- <div v-if="active === null" class="grid grid-cols-3 gap-2">
            <div v-for="(month, idx) in year">
              <div
                class="h-24 bg-gray-200 rounded-xl text-center"
                @click="changeActive()"
              >
                {{ idx + 1 }}월
              </div>
            </div>
          </div> -->
          <ul>
            <div v-for="(attendance, index) in attendances">
              <div v-if="active === index">
                <h2>{{ index.slice(0, 4) }}년 {{ index.slice(5, 8) }} 월</h2>
                <div
                  v-for="(item, idx) in attendance"
                  :key="item.commuteId"
                  class=""
                >
                  <p
                    class="day p-1  mt-2"
                    v-if="
                      idx === 0 ||
                        attendance[idx].ymd !== attendance[idx - 1].ymd
                    "
                  >
                    {{ item.ymd.slice(5, 7) }}월 {{ item.ymd.slice(8, 11) }}일
                  </p>
                  <div class="grid grid-cols-3 p-1 h-9">
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
                  <div class="hr"></div>
                </div>
              </div>
            </div>
          </ul>
          <!-- 아래에서 '일'을 반복하고 '일'안에서 '멤버'반복 -->
        </div>
      </div>
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

    // 얜 더미야!
    // const members = 16

    const active = ref(null)

    const changeActive = index => {
      active.value = index
      console.log(active.value)
    }

    const year = ref(12)

    const attendances = ref()

    watch(attendances, value => {
      if (!value) return
      return Object.keys(value).forEach(key => {
        value[key].sort((a, b) => {
          a.ymd === b.ymd ? 0 : a.ymd < b.ymd ? -1 : 1
        })
      })
    })

    const retires = computed(() => {
      return store.state.admin.retires
    })

    const members = computed(() => {
      return store.state.admin.members
    })

    const years = computed(() => {
      return store.state.admin.years
    })

    // getAttendances, getRetires, getMembers 실행
    onMounted(async () => {
      attendances.value = await store.dispatch(
        "admin/getAttendances",
        user.officeId
      )
      store.dispatch("admin/getRetires", user.officeId)
      store.dispatch("admin/getMembers", user.officeId)
      store.dispatch("admin/getYears", user.officeId)
    })
    return { user, attendances, retires, members, active, changeActive, year }
  },
}
</script>

<style scoped lang="scss">
// 400, 700 가져옴
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap");

* {
  font-family: "Noto Sans KR", sans-serif;
  // 얘를 700으로 조절가능
  font-weight: 400;
}

.admin {
  // 스크롤은 유지되지만 스크롤바가 안보임
  scrollbar-width: none;
  @apply bg-gray-100 p-10 grid gap-10 content-start overflow-auto;

  h1 {
    @apply mb-6 ml-1 text-3xl font-bold;
  }

  h2 {
    @apply text-2xl font-bold;
  }
  // 스크롤은 유지되지만 스크롤바가 안보임
  // &::-webkit-scrollbar {
  //   display: none;
  // }
  .top-box-wrapper {
    .top-box {
      @apply grid grid-cols-5 gap-4;
      .top-box-item {
        @apply h-40 bg-white shadow rounded-lg p-4;
      }
    }
  }

  .bottom-box-wrapper {
    .bottom-box {
      @apply grid grid-cols-12 gap-4;
      height: 50vh;

      .content {
        @apply px-4 py-2;
      }
      .bottom-box-item {
        @apply bg-white shadow rounded-lg overflow-scroll;

        .day {
          @apply text-xl;
        }

        .hr {
          @apply bg-gray-200 h-px w-full;
        }
        .month-list {
        }
      }
      .left-box {
        @apply col-start-1 col-end-3 h-full;
        .hr {
          @apply bg-gray-200 h-px w-full;
        }
      }
    }

    .right-box {
      @apply col-start-3 col-end-11 h-full px-4 py-2 overflow-scroll;
    }
  }
}
</style>
