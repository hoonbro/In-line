<template>
  <div class="chat-container">
    <div class="chat-list-container">
      <div class="chat-list" ref="chatListEl">
        <div class="chat-list-item" v-for="i in 4" :key="i">
          <p class="user-name">
            선명준
          </p>
          <div class="content">
            <p class="message">채팅 메세지채팅 메세지채팅 메세지채팅 메세지</p>
            <p class="created">11:10 AM</p>
          </div>
        </div>
        <div class="chat-list-item" v-for="(chat, idx) in chatList" :key="idx">
          <p class="user-">{{ chat.userName }}</p>
          <div class="content">
            <p class="message">{{ chat.content }}</p>
          </div>
        </div>
      </div>
    </div>
    <div class="chat-input-container">
      <textarea
        v-model="content"
        placeholder="채팅하려면 여기에 내용을 입력하세요."
        type="text"
        @keydown.enter="sendMessage"
      />
    </div>
  </div>
</template>

<script>
import { computed, onMounted, ref } from "vue"
import { useStore } from "vuex"
export default {
  name: "Chat",
  setup() {
    const store = useStore()
    const officeId = computed(() => store.state.auth.user.officeId)
    const userId = computed(() => store.state.auth.user.userId)
    const userName = computed(() => store.state.auth.user.name)
    const chatList = computed(() => store.state.socket.officeChatList)
    const stompClient = computed(() => store.state.socket.stompClient)
    const chatListEl = ref(null)
    const content = ref("")

    const sendMessage = event => {
      console.log(`Send message: ${content.value}`)
      if (stompClient.value && stompClient.value.connected) {
        const msg = {
          officeId: officeId.value,
          userId: userId.value,
          userName: userName.value,
          content: content.value,
        }
        stompClient.value.send(
          `/pub/${officeId.value}`,
          JSON.stringify(msg),
          {}
        )
        content.value = ""
      }
    }

    onMounted(() => {
      if (chatListEl.value) {
        console.log(chatListEl.value)
        chatListEl.value.scrollTo({
          top: chatListEl.value.scrollHeight,
          behavior: "smooth",
        })
      }
    })
    return {
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

      .chat-list-item {
        @apply grid gap-1;

        .content {
          @apply flex items-end;

          .message {
            max-width: 224px;
            @apply py-2 px-4 bg-gray-50 text-sm mr-1;
          }

          .created {
            @apply text-xs text-gray-400;
          }
        }

        &.my-chat {
          .user-name {
            @apply text-right;
          }

          .content {
            @apply flex-row-reverse;

            .message {
              @apply bg-yellow-50;
            }
          }
        }
      }
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
