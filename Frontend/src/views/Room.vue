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
import { computed, onMounted, reactive, ref } from "@vue/runtime-core"
import { useStore } from "vuex"

export default {
  name: "Room",
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

    // const register = () => {
    //   const message = {
    //     id: "JoinRoom",
    //     name: state.name,
    //     room: state.room,
    //   }

    //   sendMessage(message)
    // }

    let ws = new WebSocket(`wss://http://i5d207.p.ssafy.io/:8995/groupcall`)
    // const participants = {}

    // window.onbeforeunload = function() {
    //   ws.close()
    // }

    // ws.onmessage = function(message) {
    //   let parsedMessage = JSON.parse(message.data)
    //   console.info("Received message: " + message.data)

    //   switch (parsedMessage.id) {
    //     case "existingParticipants":
    //       onExistingParticipants(parsedMessage)
    //       break
    //     case "newParticipantArrived":
    //       onNewParticipant(parsedMessage)
    //       break
    //     case "participantLeft":
    //       onParticipantLeft(parsedMessage)
    //       break
    //     case "receiveVideoAnswer":
    //       receiveVideoResponse(parsedMessage)
    //       break
    //     case "iceCandidate":
    //       participants[parsedMessage.name].rtcPeer.addIceCandidate(
    //         parsedMessage.candidate,
    //         function(error) {
    //           if (error) {
    //             console.error("Error adding candidate: " + error)
    //             return
    //           }
    //         }
    //       )
    //       break
    //     default:
    //       console.error("Unrecognized message", parsedMessage)
    //   }
    // }

    // function onNewParticipant(request) {
    //   receiveVideo(request.name)
    // }

    // function receiveVideoResponse(result) {
    //   participants[result.name].rtcPeer.processAnswer(
    //     result.sdpAnswer,
    //     function(error) {
    //       if (error) return console.error(error)
    //     }
    //   )
    // }

    // // 안씀...
    // function callResponse(message) {
    //   if (message.response != "accepted") {
    //     console.info("Call not accepted by peer. Closing call")
    //     stop()
    //   } else {
    //     webRtcPeer.processAnswer(message.sdpAnswer, function(error) {
    //       if (error) return console.error(error)
    //     })
    //   }
    // }

    // function onExistingParticipants(msg) {
    //   let constraints = {
    //     audio: true,
    //     video: {
    //       mandatory: {
    //         maxWidth: 320,
    //         maxFrameRate: 15,
    //         minFrameRate: 15,
    //       },
    //     },
    //   }
    //   console.log(state.name + " registered in room " + state.room)
    //   let participant = new Participant(state.name)
    //   participants[state.name] = participant
    //   let video = participant.getVideoElement()

    //   let options = {
    //     localVideo: video,
    //     mediaConstraints: constraints,
    //     onicecandidate: participant.onIceCandidate.bind(participant),
    //   }
    //   participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(
    //     options,
    //     function(error) {
    //       if (error) {
    //         return console.error(error)
    //       }
    //       this.generateOffer(participant.offerToReceiveVideo.bind(participant))
    //     }
    //   )

    //   msg.data.forEach(receiveVideo)
    // }

    // // RoomComponent
    // function leaveRoom() {
    //   sendMessage({
    //     id: "leaveRoom",
    //   })

    //   for (let key in participants) {
    //     participants[key].dispose()
    //   }

    //   // document.getElementById("join").style.display = "block"
    //   // document.getElementById("room").style.display = "none"

    //   // ws.close();
    // }

    // function receiveVideo(sender) {
    //   let participant = new Participant(sender)
    //   participants[sender] = participant
    //   let video = participant.getVideoElement()

    //   let options = {
    //     remoteVideo: video,
    //     onicecandidate: participant.onIceCandidate.bind(participant),
    //   }

    //   participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(
    //     options,
    //     function(error) {
    //       if (error) {
    //         return console.error(error)
    //       }
    //       this.generateOffer(participant.offerToReceiveVideo.bind(participant))
    //     }
    //   )
    // }

    // function onParticipantLeft(request) {
    //   console.log("Participant " + request.name + " left")
    //   let participant = participants[request.name]
    //   participant.dispose()
    //   delete participants[request.name]
    // }

    // function sendMessage(message) {
    //   const jsonMessage = JSON.stringify(message)
    //   console.log("Sending message: " + jsonMessage)
    //   ws.send(jsonMessage)
    //   console.log("Sending message: " + jsonMessage)
    // }

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
