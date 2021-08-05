<template>
  <div class="chat-container">
    <div class="chat-list-container">
      <div class="chat-list" ref="chatListEl">
        <div
          class="chat-list-item"
          :class="{ 'my-chat': chat.userId === userId }"
          v-for="(chat, idx) in chatList"
          :key="idx"
        >
          <p class="user-name">{{ chat.userName }}</p>
          <div class="content">
            <p class="message">{{ chat.content }}</p>
            <p class="created">{{ chat.sendTime }}</p>
          </div>
        </div>
      </div>
    </div>
    <div class="chat-input-container">
      <textarea
        v-model="content"
        placeholder="채팅하려면 여기에 내용을 입력하세요."
        type="text"
        @keyup.enter="sendMessage"
      />
    </div>
  </div>
</template>

<script>
import { computed, onMounted, onUpdated, ref } from "vue"
import { useStore } from "vuex"
export default {
  name: "Chat",
  setup() {
    const store = useStore()
    const officeId = computed(() => store.state.auth.user.officeId)
    const userId = computed(() => store.state.auth.user.userId)
    const userName = computed(() => store.state.auth.user.name)
    const chatList = computed(() => {
      return store.state.socket.officeChatList.map(chat => {
        const AMPM = +chat.sendTime.slice(0, 2) < 12 ? "AM" : "PM"
        chat.sendTime = `${chat.sendTime.slice(0, 5)} ${AMPM}`
        return chat
      })
    })
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

      .chat-list-item {
        @apply grid gap-1 px-4;

        .content {
          @apply flex items-end gap-2;

          .message {
            max-width: 224px;
            @apply py-2 px-4 bg-gray-50 text-sm;
          }

          .created {
            @apply text-xs text-gray-400;
          }
        }

        &.my-chat {
          .user-name {
            @apply hidden;
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
