<template>
  <div class="all">
    <div class="shot">
      <div class="videoChat">
        <Video v-for="video in videoList" :key="video" :videoList="videoList" />
      </div>
      <div class="bottomBar">
        <div class="micButton">
          <span class="material-icons"> mic </span> 마이크 끄기
        </div>
        <div class="camButton">
          <span class="material-icons"> videocam </span> 카메라 끄기
        </div>
        <div class="closeButton">
          <router-link :to="{ name: 'Office' }"><span>X</span></router-link>
        </div>
      </div>
    </div>

    <div class="sidebar">
      <div class="icons">
        <ul>
          <li
            :class="{ active: activeMenu === 'chat' }"
            @click="changeActiveMenu('chat')"
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
            :class="{ active: activeMenu === 'chat2' }"
            @click="changeActiveMenu('chat2')"
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
      <div class="chatList" v-if="activeMenu === 'chat'">채팅 목록입니다</div>
      <div class="peopleList" v-if="activeMenu === 'people'">
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
    const videoList = 6
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

<style>
.all {
  @apply w-full flex bg-purple-500;
}

.shot {
  @apply flex grid;
}

.bottomBar {
  @apply flex justify-center align-middle;
}
.micButton {
  @apply flex bg-blue-900 rounded-full h-10 w-36 text-white justify-center mx-2 place-items-center cursor-pointer;
}

.camButton {
  @apply flex bg-blue-900 rounded-full h-10 w-36 text-white justify-center mx-2 place-items-center cursor-pointer;
}

.closeButton {
  @apply flex bg-red-600 rounded-full h-10 w-10 text-white justify-center mx-2 place-items-center;
}

.videoChat {
  @apply flex-1 grid grid-cols-3 m-28;
}

.sidebar {
  @apply flex flex-row-reverse w-96 h-screen ml-auto border-yellow-400 border-2;
}

.chatList {
  @apply flex bg-white w-full h-full;
}

.peopleList {
  @apply flex bg-white w-full h-full;
}

.icon {
  @apply p-4 inline-block justify-center cursor-pointer;
}

.icons {
  border: 1px solid gray;
}

.sidebar ul li.active {
  @apply text-blue-700;
}
</style>
