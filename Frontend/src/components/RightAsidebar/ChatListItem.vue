<template>
  <div class="chat-list-item" :class="{ 'my-chat': chat.userId === userId }">
    <p class="user-name">{{ chat.userName }}</p>
    <div class="content">
      <p class="message">{{ chat.content }}</p>
      <p class="created">{{ chat.sendTime }}</p>
    </div>
  </div>
</template>

<script>
import { computed } from "vue"
import { useStore } from "vuex"
export default {
  name: "ChatListItem",
  props: {
    chat: Object,
  },
  setup() {
    const store = useStore()
    const userId = computed(() => store.state.auth.user.userId)
    return { userId }
  },
}
</script>

<style lang="scss" scoped>
.chat-list-item {
  @apply grid gap-1 px-4;

  .content {
    @apply flex items-end gap-2 whitespace-pre;

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
</style>
