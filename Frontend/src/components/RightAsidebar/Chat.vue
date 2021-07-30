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
        <div class="chat-list-item" v-for="i in 4" :key="i">
          <p class="user-name">
            선명준
          </p>
          <div class="content">
            <p class="message">채팅 메세지</p>
            <p class="created">11:10 AM</p>
          </div>
        </div>
        <div class="chat-list-item my-chat" v-for="i in 2" :key="i">
          <p class="user-name">
            김병훈
          </p>
          <div class="content">
            <p class="message">채팅 메세지채팅 메세지채팅 메세지채팅 메세지</p>
            <p class="created">11:10 AM</p>
          </div>
        </div>
        <div class="chat-list-item my-chat" v-for="i in 2" :key="i">
          <p class="user-name">
            김병훈
          </p>
          <div class="content">
            <p class="message">채팅 메세지</p>
            <p class="created">11:10 AM</p>
          </div>
        </div>
      </div>
    </div>
    <div class="chat-input-container">
      <textarea
        placeholder="채팅하려면 여기에 내용을 입력하세요."
        type="text"
      />
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity"
import { onMounted } from "@vue/runtime-core"
export default {
  name: "Chat",
  setup() {
    const chatListEl = ref(null)

    onMounted(() => {
      if (chatListEl) {
        console.log(chatListEl)
        chatListEl.value.scrollTo({
          top: chatListEl.value.scrollHeight,
          behavior: "smooth",
        })
      }
    })
    return {
      chatListEl,
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
      @apply h-full overflow-auto grid gap-4;

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
