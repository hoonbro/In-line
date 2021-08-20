<template>
  <div class="chat-container">
    <div class="chat-list-container">
      <div class="chat-list" ref="chatListEl">
        <ChatListItem v-for="(chat, idx) in chatList" :key="idx" :chat="chat" />
      </div>
    </div>
    <div class="chat-input-container">
      <textarea
        v-model="content"
        placeholder="채팅하려면 여기에 내용을 입력하세요."
        type="text"
        @keydown.enter.exact.prevent="sendMessage"
        @keyup.enter.exact.prevent
        @keydown.enter.shift.exact="newLine"
      />
    </div>
  </div>
</template>

<script>
import { computed, onMounted, onUpdated, ref } from "vue"
import { useStore } from "vuex"
import { useRoute } from "vue-router"
import ChatListItem from "@/components/RightAsidebar/ChatListItem.vue"

export default {
  name: "RoomChat",
  components: { ChatListItem },
  setup() {
    // ===========================================
    const route = useRoute()
    const path = route.fullPath
    console.log(route.fullPath) // '/rooms/177'
    // console.log(path.slice(7)) // '177'
    const roomId = path.slice(7)
    // ===========================================

    const store = useStore()
    const officeId = computed(() => store.state.auth.user.officeId)
    const userId = computed(() => store.state.auth.user.userId)
    const userName = computed(() => store.state.auth.user.name)
    const chatList = computed(() => {
      return store.state.socket.roomChatList.map(chat => {
        const AMPM = +chat.sendTime.slice(0, 2) < 12 ? "AM" : "PM"
        const formatedTime = `${chat.sendTime.slice(0, 5)} ${AMPM}`
        return { ...chat, sendTime: formatedTime }
      })
    })
    const roomStompClient = computed(() => store.state.socket.roomStompClient)
    const chatListEl = ref(null)
    const content = ref("")

    const newLine = () => {
      content.value = `${content.value}\n`
    }

    const sendMessage = event => {
      if (
        content.value &&
        roomStompClient.value &&
        roomStompClient.value.connected
      ) {
        console.log(`Send message: ${content.value}`)
        const msg = {
          officeId: officeId.value,
          userId: userId.value,
          userName: userName.value,
          content: content.value,
        }
        roomStompClient.value.send(
          `/pub/${officeId.value}/${roomId}`,
          JSON.stringify(msg),
          {}
        )
      }
      content.value = ""
    }

    onUpdated(() => {
      chatListEl.value.scrollTo({
        top: chatListEl.value.scrollHeight,
      })
    })

    onMounted(() => {
      if (chatListEl.value) {
        chatListEl.value.scrollTo({
          top: chatListEl.value.scrollHeight,
          behavior: "smooth",
        })
      }
    })
    return {
      userId,
      content,
      chatList,
      chatListEl,
      sendMessage,
    }
  },
}
</script>

<style lang="scss" scoped>
.chat-container {
  width: 360px;
  @apply h-full overflow-hidden flex flex-col;

  .chat-list-container {
    @apply flex-1 overflow-hidden p-2;

    .chat-list {
      @apply h-full overflow-auto grid gap-4 content-start;
    }
  }

  .chat-input-container {
    @apply p-2 pt-0;

    textarea {
      height: 72px;
      @apply p-2 bg-gray-50 border border-gray-400 rounded w-full resize-none;
    }
  }
}
</style>
