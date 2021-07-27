<template>
  <div class="wrapper">
    <div class="video-chat">
      <!-- ------------------------------------------------------------------------- -->
      <div class="video-part" v-if="videoList > 16">
        <Video v-for="video in videoList" :key="video" :videoList="videoList" />
      </div>
      <div class="video-part2" v-else-if="videoList > 9">
        <Video v-for="video in videoList" :key="video" :videoList="videoList" />
      </div>
      <div class="video-part3" v-else-if="videoList > 4">
        <Video v-for="video in videoList" :key="video" :videoList="videoList" />
      </div>
      <div class="video-part4" v-else>
        <Video v-for="video in videoList" :key="video" :videoList="videoList" />
      </div>
      <!-- ------------------------------------------------------------------------- -->
      <div class="bar-part">
        <div class="mic-button">
          <span class="material-icons"> mic </span> 마이크 끄기
        </div>
        <div class="cam-button">
          <span class="material-icons"> videocam </span> 카메라 끄기
        </div>
        <div class="close-button">
          <router-link :to="{ name: 'Office' }"><span>X</span></router-link>
        </div>
      </div>
    </div>

    <div class="side-bar">
      <div class="icon-list">
        <ul>
          <li
            :class="{ active: activeMenu === 'roomChat' }"
            @click="changeActiveMenu('roomChat')"
          >
            <span class="icon"
              ><span class="material-icons">
                chat
              </span></span
            >
          </li>
          <li
            :class="{ active: activeMenu === 'people' }"
            @click="changeActiveMenu('people')"
          >
            <span class="icon"
              ><span class="material-icons">
                people
              </span></span
            >
          </li>
          <hr />
          <li
            :class="{ active: activeMenu === 'notification' }"
            @click="changeActiveMenu('notification')"
          >
            <span class="icon"
              ><span class="material-icons">
                notifications
              </span></span
            >
          </li>
          <li
            :class="{ active: activeMenu === 'officeChat' }"
            @click="changeActiveMenu('officeChat')"
          >
            <span class="icon"
              ><span class="material-icons">
                chat
              </span></span
            >
          </li>
          <li
            :class="{ active: activeMenu === 'check' }"
            @click="changeActiveMenu('check')"
          >
            <span class="icon"
              ><span class="material-icons">
                check
              </span></span
            >
          </li>
        </ul>
      </div>
      <div class="chat-list" v-if="activeMenu === 'officeChat'">
        채팅 목록입니다
      </div>
      <div class="people-list" v-if="activeMenu === 'people'">
        사람 목록입니다
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue"
import Video from "@/components/Video.vue"

export default {
  name: "Room",
  components: {
    Video,
  },
  setup() {
    const videoList = 16
    const activeMenu = ref("")
    // 처음에 아무것도 활성화되어 있지 않아도 되므로 공백으로 남겨둠
    const changeActiveMenu = menuTitle => {
      activeMenu.value = menuTitle
    }

    // 마이크 끄기, 카메라 끄기 버튼 눌렀을 때, 아이콘 전환되게 만들기

    return {
      videoList,
      activeMenu,
      changeActiveMenu,
    }
  },
}
</script>

<style scoped lang="scss">
.wrapper {
  @apply h-full flex bg-purple-500 border-yellow-400 border-2;

  .video-chat {
    @apply border-red-500 border-2 flex-1;

    .video-part {
      @apply grid grid-cols-5 m-12 bg-Teal-300;
    }
    .video-part2 {
      @apply grid grid-cols-4 m-12 bg-red-500;
    }
    .video-part3 {
      @apply grid grid-cols-3 m-12 bg-yellow-300;
    }
    .video-part4 {
      @apply grid grid-cols-2 m-12 bg-black;
    }

    .bar-part {
      @apply flex fixed left-1/3 bottom-10;

      .mic-button {
        @apply flex bg-blue-900 rounded-full h-10 w-36 text-white justify-center mx-2 place-items-center cursor-pointer;
      }

      .cam-button {
        @apply flex bg-blue-900 rounded-full h-10 w-36 text-white justify-center mx-2 place-items-center cursor-pointer;
      }

      .close-button {
        @apply flex bg-red-600 rounded-full h-10 w-10 text-white justify-center mx-2 place-items-center;
      }
    }
  }

  .side-bar {
    @apply flex flex-row-reverse w-16 h-screen ml-auto border-yellow-400 border-2;

    .chat-list {
      @apply bg-white w-full h-full;
    }

    .people-list {
      @apply bg-white w-full h-full;
    }
    .icon-list {
      border: 1px solid gray;
      .icon {
        @apply p-4 inline-block justify-center cursor-pointer;
      }
    }
  }
  .side-bar ul li.active {
    @apply text-blue-700;
  }
}
</style>
