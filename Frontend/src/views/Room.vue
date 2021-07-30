<template>
  <div class="layout">
    <div class="wrapper">
      <div class="video-chat">
        <!-- ------------------------------------------------------------------------- -->
        <div class="video-part" v-if="videoList > 16">
          <Video
            v-for="video in videoList"
            :key="video"
            :videoList="videoList"
          />
        </div>
        <div class="video-part2" v-else-if="videoList > 9">
          <Video
            v-for="video in videoList"
            :key="video"
            :videoList="videoList"
          />
        </div>
        <div class="video-part3" v-else-if="videoList > 4">
          <Video
            v-for="(video, idx) in videoList"
            :key="video"
            :idx="idx"
            :videoList="videoList"
          />
        </div>
        <div class="video-part4" v-else>
          <Video
            v-for="video in videoList"
            :key="video"
            :videoList="videoList"
          />
        </div>
        <!-- ------------------------------------------------------------------------- -->

        <div class="bar-part">
          <div
            class="mic-button"
            @click="changeMic()"
            v-if="switchMic === true"
          >
            <span class="material-icons"> mic </span> 마이크 끄기
          </div>
          <div
            class="mic-button"
            @click="changeMic()"
            v-if="switchMic === false"
          >
            <span class="material-icons"> mic_off </span> 마이크 켜기
          </div>
          <div
            class="cam-button"
            @click="changeCam()"
            v-if="switchCam === true"
          >
            <span class="material-icons"> videocam </span> 카메라 끄기
          </div>
          <div
            class="cam-button"
            @click="changeCam()"
            v-if="switchCam === false"
          >
            <span class="material-icons"> videocam_off </span> 카메라 켜기
          </div>
          <div class="close-button">
            <router-link :to="{ name: 'Office' }"><span>✕</span></router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Video from "@/components/Room/Video.vue"
import { ref } from "@vue/runtime-core"

export default {
  name: "Room",
  components: {
    Video,
  },
  setup() {
    const videoList = 6

    // 여기서부터
    const switchMic = ref(true)
    const switchCam = ref(true)

    const changeMic = () => {
      switchMic.value = !switchMic.value
      console.log(switchMic.value)
    }
    const changeCam = () => {
      switchCam.value = !switchCam.value
      console.log(switchCam.value)
    }
    // 여기까지 마이크, 캠 껐다 켰다하기

    return {
      videoList,
      switchMic,
      switchCam,
      changeMic,
      changeCam,
    }
  },
}
</script>

<style scoped lang="scss">
.layout {
  @apply h-screen flex flex-col overflow-hidden;

  main {
    @apply h-full overflow-hidden flex;
  }
}
.wrapper {
  @apply h-full overflow-hidden;

  .video-chat {
    .video-part {
      @apply grid grid-cols-5 m-12 bg-Teal-300;
    }
    .video-part2 {
      @apply grid grid-cols-4 m-12 bg-red-500;
    }

    .video-part3 {
      @apply grid lg:grid-cols-3 md:grid-cols-2 mx-40 mt-28 gap-5;
      // grid-column: 1/3;
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

  // .people-list {
  //   @apply bg-white w-full h-full;
  //   display: none;
  // }
  // .icon-list {
  //   .icon {
  //     @apply p-4 inline-block justify-center cursor-pointer;
  //   }
  // }
}
.side-bar ul li.active {
  @apply text-blue-700;
}
</style>
