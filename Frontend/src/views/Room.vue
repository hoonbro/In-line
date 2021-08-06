<template>
  <div class="layout">
    <div class="wrapper">
      <div class="video-chat">
        <!-- ------------------------------------------------------------------------- -->
        <div id="participants" class="video-part">
          <!-- 이 안에 participant가 들어온다 -->
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
            <router-link :to="{ name: 'Office' }">
              <span class="cursor-pointer">✕</span></router-link
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Video from "@/components/Room/Video.vue"
import {
  computed,
  onMounted,
  onUnmounted,
  reactive,
  ref,
} from "@vue/runtime-core"
import { useStore } from "vuex"
import { useRouter } from "vue-router"
import kurentoUtils from "kurento-utils"

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

    const router = useRouter()

    const state = reactive({
      room: "전체 회의방",
      name: store.state.auth.user.name,
    })

    const register = () => {
      const message = {
        id: "joinRoom",
        // 얘가 닉네임
        name: state.name,
        //
        room: state.room,
        roomId: 2,
      }

      sendMessage(message)
    }

    onUnmounted(() => leaveRoom())

    let ws = new WebSocket(`wss://i5d207.p.ssafy.io:8995/groupcall`)

    ws.onopen = function(event) {
      register()
    }

    function sendMessage(message) {
      const jsonMessage = JSON.stringify(message)
      // console.log("Sending message: " + jsonMessage)
      ws.send(jsonMessage)
    }

    const participants = {}

    // window.onbeforeunload = function() {
    //   ws.close()
    // }

    ws.onmessage = function(message) {
      let parsedMessage = JSON.parse(message.data)
      // console.info("Received message: " + message.data)

      switch (parsedMessage.id) {
        case "existingParticipants":
          onExistingParticipants(parsedMessage)
          break
        case "newParticipantArrived":
          onNewParticipant(parsedMessage)
          break
        case "participantLeft":
          onParticipantLeft(parsedMessage)
          break
        case "receiveVideoAnswer":
          receiveVideoResponse(parsedMessage)
          break
        case "iceCandidate":
          participants[parsedMessage.name].rtcPeer.addIceCandidate(
            parsedMessage.candidate,
            function(error) {
              if (error) {
                // console.error("Error adding candidate: " + error)
                return
              }
            }
          )
          break
        default:
        // console.error("Unrecognized message", parsedMessage)
      }
    }

    function onNewParticipant(request) {
      receiveVideo(request.name)
    }

    function receiveVideoResponse(result) {
      participants[result.name].rtcPeer.processAnswer(
        result.sdpAnswer,
        function(error) {
          if (error) return console.error(error)
        }
      )
    }

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

    function onExistingParticipants(msg) {
      let constraints = {
        // 이거를 해석해야 할 것 같음.---------------------------------------------------------------------------------
        audio: true,
        video: {
          mandatory: {
            // minWidth: 1000,
            maxWidth: 420,
            minWidth: 420,
            // minHeight: 400,
            // maxHeight: 2000,
            maxFrameRate: 20,
            minFrameRate: 20,
          },
        },
      }

      console.log(state.name + " registered in room " + state.room)
      let participant = new Participant(state.name)
      participants[state.name] = participant
      let video = participant.getVideoElement()

      const options = {
        localVideo: video,
        mediaConstraints: constraints,
        onicecandidate: participant.onIceCandidate.bind(participant),
      }
      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(
        options,
        function(error) {
          if (error) {
            return console.error(error)
          }
          this.generateOffer(participant.offerToReceiveVideo.bind(participant))
        }
      )

      msg.data.forEach(receiveVideo)
    }

    // 얘도 떠날때임
    // RoomComponent
    function leaveRoom() {
      sendMessage({
        id: "leaveRoom",
      })

      for (let key in participants) {
        participants[key].dispose()
      }

      ws.close()
      router.push({ name: "Office" })
    }

    function receiveVideo(sender) {
      let participant = new Participant(sender)
      participants[sender] = participant
      let video = participant.getVideoElement()

      let options = {
        remoteVideo: video,
        onicecandidate: participant.onIceCandidate.bind(participant),
      }

      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(
        options,
        function(error) {
          if (error) {
            return console.error(error)
          }
          this.generateOffer(participant.offerToReceiveVideo.bind(participant))
        }
      )
    }
    //  떠날때임
    function onParticipantLeft(request) {
      console.log("Participant " + request.name + " left")
      let participant = participants[request.name]
      participant.dispose()
      delete participants[request.name]
    }

    // ===========================================================================
    // participant 시작
    // ===========================================================================

    // 이거를 해석해야 할 것 같음.---------------------------------------------------------------------------------
    const PARTICIPANT_MAIN_CLASS = "participant main"
    const PARTICIPANT_CLASS = "participant"

    /**
     * Creates a video element for a new participant
     *
     * @param {String} name - the name of the new participant, to be used as tag
     *                        name of the video element.
     *                        The tag of the new element will be 'video<name>'
     * @return
     */

    function Participant(name) {
      this.name = name
      let container = document.createElement("div")
      // PARTICIPANT_MAIN_CLASS가 없을 때
      container.className = isPresentMainParticipant()
        ? PARTICIPANT_CLASS
        : PARTICIPANT_MAIN_CLASS
      container.classList.add("text-center", "pointer-events-none")
      container.id = name
      let span = document.createElement("span")
      span.classList.add("text-blue-500", "w-12", "h-8", "bg-gray-50")

      let video = document.createElement("video")
      let rtcPeer

      container.appendChild(video)
      container.appendChild(span)
      container.onclick = switchContainerClass
      document.getElementById("participants").appendChild(container)

      span.appendChild(document.createTextNode(name))

      // 이 부분이 video-id가 됨
      // 이거를 해석해야 할 것 같음.---------------------------------------------------------------------------------
      video.id = "video-" + name
      video.autoplay = true
      video.controls = true

      this.getElement = function() {
        return container
      }

      this.getVideoElement = function() {
        return video
      }

      function switchContainerClass() {
        if (container.className === PARTICIPANT_CLASS) {
          var elements = Array.prototype.slice.call(
            document.getElementsByClassName(PARTICIPANT_MAIN_CLASS)
          )
          elements.forEach(function(item) {
            item.className = PARTICIPANT_CLASS
          })

          container.className = PARTICIPANT_MAIN_CLASS
        } else {
          container.className = PARTICIPANT_CLASS
        }
      }

      function isPresentMainParticipant() {
        return (
          document.getElementsByClassName(PARTICIPANT_MAIN_CLASS).length != 0
        )
      }

      this.offerToReceiveVideo = function(error, offerSdp, wp) {
        if (error) return console.error("sdp offer error")
        console.log("Invoking SDP offer callback function")
        var msg = { id: "receiveVideoFrom", sender: name, sdpOffer: offerSdp }
        sendMessage(msg)
      }

      this.onIceCandidate = function(candidate, wp) {
        // console.log("Local candidate" + JSON.stringify(candidate))

        var message = {
          id: "onIceCandidate",
          candidate: candidate,
          name: name,
        }
        sendMessage(message)
      }

      Object.defineProperty(this, "rtcPeer", { writable: true })

      this.dispose = function() {
        console.log("Disposing participant " + this.name)
        this.rtcPeer.dispose()
        container.parentNode.removeChild(container)
      }
    }
    // ===========================================================================
    // participant 끝
    // ===========================================================================

    // -------------------------------------------------------------------------------
    const videoList = 6

    // 여기서부터
    const switchMic = ref(true)
    const switchCam = ref(true)

    const changeMic = () => {
      switchMic.value = !switchMic.value
      console.log(switchMic.value)
      // participants[name].rtcPeer.videoEnabled = false
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
      leaveRoom,
    }
  },
}
</script>

<style scoped lang="scss">
.layout {
  @apply h-screen flex flex-col overflow-hidden;

  // main {
  //   apply h-full overflow-hidden flex;
  // }
}
.wrapper {
  @apply h-full overflow-hidden;

  .video-chat {
    width: 90%;

    margin: auto;

    .user-name {
    }

    .participant {
      // apply text-red-500;
    }

    .video-part {
      @apply grid grid-cols-3 mx-16 mt-20;
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

// .main {
//   border: 3px solid red;
// }

.main {
  border: 5px solid blue;
}
</style>
