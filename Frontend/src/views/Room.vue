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
        <div id="participants"></div>
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
// import Participant from "@/lib/participant.js"
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

    const state = reactive({
      room: "",
      name: store.state.auth.user.name,
    })

    const register = () => {
      const message = {
        id: "joinRoom",
        // 얘가 닉네임
        name: "내가 김두환이다",
        //
        room: "전체 회의방",
        roomId: 2,
      }

      sendMessage(message)
    }

    let ws = new WebSocket(`wss://i5d207.p.ssafy.io:8995/groupcall`)

    ws.onopen = function(event) {
      register()
      //   var message = {
      //     id: "joinRoom",
      //     name: "Kim",
      //     room: "전체 회의방",
      //     roomId: 2,
      //   }

      //   sendMessage(message)
      // ws.send("TEST!")
    }

    function sendMessage(message) {
      const jsonMessage = JSON.stringify(message)
      console.log("Sending message: " + jsonMessage)
      ws.send(jsonMessage)
    }

    // onMounted(() => {
    //   register()
    // })

    const participants = {}

    // window.onbeforeunload = function() {
    //   ws.close()
    // }

    ws.onmessage = function(message) {
      let parsedMessage = JSON.parse(message.data)
      console.info("Received message: " + message.data)

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
                console.error("Error adding candidate: " + error)
                return
              }
            }
          )
          break
        default:
          console.error("Unrecognized message", parsedMessage)
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
        audio: true,
        video: {
          mandatory: {
            maxWidth: 320,
            maxFrameRate: 15,
            minFrameRate: 15,
          },
        },
      }
      console.log(state.name + " registered in room " + state.room)
      let participant = new Participant(state.name)
      participants[state.name] = participant
      let video = participant.getVideoElement()

      let options = {
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
      container.id = name
      let span = document.createElement("span")
      let video = document.createElement("video")
      let rtcPeer

      container.appendChild(video)
      container.appendChild(span)
      container.onclick = switchContainerClass
      document.getElementById("participants").appendChild(container)

      span.appendChild(document.createTextNode(name))

      video.id = "video-" + name
      video.autoplay = true
      video.controls = false

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
        console.log("Local candidate" + JSON.stringify(candidate))

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
