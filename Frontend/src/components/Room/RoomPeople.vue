<template>
  <div class="people-container">
    <div class="people-list-container">
      <div class="members">
        <div class="member online">
          <img src="https://picsum.photos/200/200" alt="프로필" />
          <div>
            <p class="name">김병훈</p>
            <p class="department">Master</p>
          </div>
        </div>
        <div class="member online">
          <img src="https://picsum.photos/200/199" alt="프로필" />
          <div>
            <div class="test">
              <span class="material-icons">
                mic_off
              </span>
            </div>
            <p class="name">선명준</p>
            <p class="department">Captain</p>
          </div>
        </div>
        <div class="member online">
          <img src="https://picsum.photos/200/198" alt="프로필" />
          <div>
            <div class="test2">
              <span class="material-icons">
                videocam_off
              </span>
            </div>
            <p class="name">이원우</p>
            <p class="department">QA</p>
          </div>
        </div>
        <div class="member online">
          <img src="https://picsum.photos/200/196" alt="프로필" />
          <div>
            <p class="name">최영수</p>
            <p class="department">CEO</p>
          </div>
        </div>
        <div class="member online">
          <img src="https://picsum.photos/200/195" alt="프로필" />
          <div>
            <p class="name">변지훈</p>
            <p class="department">Midas</p>
          </div>
        </div>
        <div class="member online">
          <img src="https://picsum.photos/200/197" alt="프로필" />
          <div>
            <div class="test3">
              <span class="material-icons">
                mic_off
              </span>
              <span class="material-icons">
                videocam_off
              </span>
            </div>
            <p class="name">조여래</p>
            <p class="department">DevOps</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "RoomPeople",
  components: {},
  setup() {
    return {}
  },
}
</script>

<style lang="scss" scoped>
.people-container {
  width: 220px;
  @apply h-full overflow-hidden flex flex-col p-1;

  .people-list-container {
    @apply flex-1 overflow-hidden p-2;
    .members {
      @apply grid gap-2 overflow-auto;
      .test {
        @apply absolute ml-20 mt-3;
      }
      .test2 {
        @apply absolute mt-3;
        margin-left: 104px;
      }

      .test3 {
        @apply absolute ml-20 mt-3;
      }

      .member {
        @apply flex p-2 relative rounded bg-blue-200 items-center select-none cursor-pointer;

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
}
</style>
