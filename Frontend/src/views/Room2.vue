<template>
  <div class="layout">
    <div class="wrapper">
      <div id="participants"></div>
    </div>
  </div>
</template>

<script>
import Video from "@/components/Room/Video.vue"
import { computed, onMounted, reactive, ref } from "@vue/runtime-core"
import { useStore } from "vuex"

export default {
  name: "Room2",
  components: {
    Video,
  },
  props: {
    roomId: [String || Number],
  },
  setup() {
    const store = useStore()

    const state = reactive({
      room: "",
      name: store.state["user"].name,
    })

    let ws = new WebSocket(`wss://i5d207.p.ssafy.io:8995/groupcall`)
    ws.onopen = function(event) {
      ws.send("TEST!")
    }

    // -------------------------------------------------------------------------------
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

    onMounted(() => {
      // register()
    })

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
      @apply grid grid-cols-5 m-12 bg-teal-300;
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
