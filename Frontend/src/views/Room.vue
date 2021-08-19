<template>
  <div class="layout">
    <div class="wrapper">
      <div class="video-chat text-center">
        <!-- ------------------------------------------------------------------------- -->
        <!-- <video id="screen-share" autoplay></video> -->
        <!-- 이 안에 participant가 들어온다 -->
        <div id="participants" class="video-part"></div>
        <!-- ------------------------------------------------------------------------- -->

        <div class="bar-part">
          <!-- <button id="start" @click="startCapture()">start</button>
          <button id="stop" @click="stopCapture()">stop</button> -->
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
  onMounted,
  onUnmounted,
  reactive,
  ref,
  computed,
} from "@vue/runtime-core"
import { useStore } from "vuex"
import { useRouter } from "vue-router"
import SockJS from "sockjs-client"
import Stomp from "webstomp-client"
import kurentoUtils from "kurento-utils"

export default {
  name: "Room",
  userId: "",
  components: {
    Video,
  },
  props: {
    roomId: [String || Number],
  },
  // props한거 쓰고싶으면 인자로 넣어줘야함
  setup(props) {
    const store = useStore()

    const router = useRouter()

    const room = ref("")
    store.state.office.rooms.forEach(item => {
      if (item.roomId == props.roomId) {
        room.value = item.roomName
      }
    })
    // const roomStompClient = ref(store.state.socket.roomStompClient)

    const state = reactive({
      room: room.value,
      name: store.state.auth.user.name,
      userId: store.state.auth.user.userId,
      officeId: store.state.auth.user.officeId,
      profileImage: store.state.auth.user.profileImage,
    })

    //////////////////////////////////room chat 추가한 부분//////////////////////////////////
    const connectRoomChat = () => {
      const serverURL = `${process.env.VUE_APP_GROUPCALL_BASE_URL}/chatStomp`
      const socket = new SockJS(serverURL)
      const roomStompClient = Stomp.over(socket)
      roomStompClient.connect(
        {},
        frame => {
          roomStompClient.connected = true
          store.commit("socket/setRoomStompClient", roomStompClient)
          roomStompClient.subscribe(
            `/sub/${state.officeId}/${props.roomId}`,
            res => {
              console.log(JSON.parse(res.body))
              store.commit("socket/addRoomChat", JSON.parse(res.body))
            }
          )
        },
        error => {
          store.commit("landing/addAlertModalList", {
            type: "error",
            message: "소켓 연결이 끊겼어요.",
          })
          roomStompClient.connected = false
          store.commit("socket/setStompClient", roomStompClient)
        }
      )
    }

    const disconnectStomp = () => {
      return new Promise((resolve, reject) => {
        console.group("disconnectStomp")
        store.state.socket.roomStompClient.disconnect(() => {
          console.log("stomp 연결을 해제합니다.")
          store.commit("socket/setRoomStompClient", null)
        })
        store.commit("socket/resetRoomChat")
        console.groupEnd()
        resolve(true)
      })
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    // 동명이인 처리 어떻게 할건지
    const register = () => {
      if (state.profileImage == null) state.profileImage = ""
      const message = {
        id: "joinRoom",
        userId: state.userId,
        userName: state.name,
        roomName: state.room,
        roomId: props.roomId,
        officeId: state.officeId,
        profileImage: state.profileImage,
      }

      sendMessage(message)
    }

    onMounted(() => connectRoomChat())
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

    const participants = reactive({})

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
          participants[parsedMessage.userId].rtcPeer.addIceCandidate(
            parsedMessage.candidate,
            function(error) {
              if (error) {
                // console.error("Error adding candidate: " + error)
                return
              }
            }
          )
          break
        case "invalidate":
          router.push("/notFound")
          break
        default:
        // console.error("Unrecognized message", parsedMessage)
      }
    }

    function onNewParticipant(request) {
      receiveVideo(request.userId, request.userName)
    }

    function receiveVideoResponse(result) {
      participants[result.userId].rtcPeer.processAnswer(
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
            minWidth: 450,
            maxWidth: 450,
            minHeight: 320,
            maxHeight: 320,
            maxFrameRate: 30,
            minFrameRate: 30,
          },
        },
      }

      console.log(state.name + " registered in room " + state.room)
      console.log(msg)
      let participant = new Participant(state.userId, state.name + "(나)")
      participants[state.userId] = participant
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

      // msg.forEach(receiveVideo(msg.userId, msg.userName))
      var len = msg.userId.length
      for (let i = 0; i < len; i++) {
        receiveVideo(msg.userId[i], msg.userName[i])
      }
      // msg.userId.forEach(receiveVideo)
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
      disconnectStomp()
      router.push({ name: "Office" })
    }

    function receiveVideo(userId, userName) {
      let participant = new Participant(userId, userName)
      participants[userId] = participant
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
      console.log("Participant " + request.userId + " left")
      let participant = participants[request.userId]
      participant.dispose()
      delete participants[request.userId]
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

    function Participant(userId, userName) {
      this.userId = userId
      let container = document.createElement("div")
      // PARTICIPANT_MAIN_CLASS가 없을 때
      container.className = isPresentMainParticipant()
        ? PARTICIPANT_CLASS
        : PARTICIPANT_MAIN_CLASS
      container.classList.add("pointer-events-none")
      container.id = userId
      let span = document.createElement("span")
      span.classList.add("w-full", "h-full", "bg-gray-200", "inline-block")

      let video = document.createElement("video")
      let rtcPeer

      container.appendChild(video)
      container.appendChild(span)
      container.onclick = switchContainerClass
      document.getElementById("participants").appendChild(container)

      if (userName != null) span.appendChild(document.createTextNode(userName))

      // 이 부분이 video-id가 됨
      // 이거를 해석해야 할 것 같음.---------------------------------------------------------------------------------
      video.id = "video-" + userId
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
        var msg = {
          id: "receiveVideoFrom",
          sender: userId,
          sdpOffer: offerSdp,
        }
        sendMessage(msg)
      }

      this.onIceCandidate = function(candidate, wp) {
        // console.log("Local candidate" + JSON.stringify(candidate))

        var message = {
          id: "onIceCandidate",
          candidate: candidate,
          userId: userId,
        }
        sendMessage(message)
      }

      Object.defineProperty(this, "rtcPeer", { writable: true })

      this.dispose = function() {
        console.log("Disposing participant " + this.userId)
        this.rtcPeer.dispose()
        container.parentNode.removeChild(container)
      }
    }
    // ===========================================================================
    // participant 끝
    // ===========================================================================

    // -------------------------- 화면 공유 코드 --------------------------
    // 화면 공유 시작
    // async function startCapture() {
    //   const videoElement = document.querySelector("#screen-share")
    //   try {
    //     const displayMediaOptions = {
    //       audio: false,
    //       video: { cursor: "always" },
    //     }
    //     const captureStream = await navigator.mediaDevices.getDisplayMedia(
    //       displayMediaOptions
    //     )
    //     videoElement.srcObject = captureStream
    //     // startButton.disabled = true
    //     // stopButton.disabled = false
    //   } catch (err) {
    //     console.error(err)
    //   }
    // }

    // // 화면 공유 중지
    // function stopCapture() {
    //   const videoElement = document.querySelector("#screen-share")
    //   const tracks = videoElement.srcObject.getTracks()
    //   tracks.forEach(track => track.stop())
    //   videoElement.srcObject = null
    //   // startButton.disabled = false
    //   // stopButton.disabled = true
    // }

    // -------------------------------------------------------------------------------
    // 여기서부터
    const switchMic = ref(true)
    const switchCam = ref(true)

    const changeMic = () => {
      switchMic.value = !switchMic.value
      // console.log(switchMic.value)
      participants[state.userId].rtcPeer.audioEnabled = !participants[
        state.userId
      ].rtcPeer.audioEnabled
    }
    const changeCam = () => {
      switchCam.value = !switchCam.value
      // console.log(switchCam.value)
      participants[state.userId].rtcPeer.videoEnabled = !participants[
        state.userId
      ].rtcPeer.videoEnabled
    }
    console.log(state.userId)

    // console.log(participants[store.state.auth.user.name].rtcPeer)

    return {
      switchMic,
      switchCam,
      changeMic,
      changeCam,
      leaveRoom,
      participants,
      // startCapture,
      // stopCapture,
    }
  },
}
</script>

<style scoped lang="scss">
.layout {
  @apply h-screen flex flex-col overflow-hidden;

  .wrapper {
    @apply h-full overflow-hidden;

    .video-chat {
      width: 90%;
      margin: auto;

      .video-part {
        @apply grid grid-cols-3 mx-20 mt-20 gap-3 place-items-center;
      }

      .bar-part {
        @apply flex fixed left-1/3 bottom-5;

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
  }
}
.side-bar ul li.active {
  @apply text-blue-700;
}
</style>
