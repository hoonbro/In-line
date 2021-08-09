<template>
  <div class="todo-list-item" :class="{ completed: todo.done }">
    <div class="inner-top">
      <div class="content">
        <p class="title">{{ todo.title }}</p>
        <p class="detail">
          {{ todo.content }}
        </p>
      </div>
      <button class="done-btn" @click="$emit('toggleComplete', todo.todoId)">
        <span class="material-icons icon">done</span>
      </button>
    </div>
    <hr class="border-gray-300" />
    <div class="inner-bottom">
      <p class="duedate">{{ todo.day }}</p>
      <button class="delete-btn" @click="$emit('delete', todo.id)">
        <span class="material-icons icon">close</span>
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: "TodoListItem",
  props: {
    todo: Object,
  },
  emits: ["toggleComplete", "delete"],
}
</script>

<style scoped lang="scss">
.todo-list-item {
  @apply p-4 bg-gray-50 grid gap-2;

  .inner-top {
    @apply flex items-start;

    .content {
      @apply flex-1 mr-2 grid gap-1;

      .title {
        @apply font-bold;
      }

      .detail {
        @apply text-sm;
      }
    }

    .done-btn:hover .icon {
      @apply text-green-500;
    }
  }

  .inner-bottom {
    @apply flex;

    .duedate {
      @apply text-sm text-red-500 flex-1;
    }

    .delete-btn .icon {
      font-size: 1rem;
      @apply text-gray-400;

      &:hover {
        @apply text-red-500;
      }
    }
  }

  &.completed {
    @apply text-gray-400;

    .duedate {
      @apply text-gray-400;
    }
  }
}
</style>
