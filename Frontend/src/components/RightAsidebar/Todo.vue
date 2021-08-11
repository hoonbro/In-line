<template>
  <div class="todo-container">
    <div class="todo-form">
      <div class="input-container" v-show="editMode">
        <input type="text" placeholder="제목" v-model="formData.title" />
        <textarea placeholder="내용" v-model="formData.content"></textarea>
        <input type="date" placeholder="마감기한" v-model="formData.day" />
      </div>
      <div class="button-container">
        <button class="form-btn add" @click="handleAddBtnClick">
          할 일 추가
        </button>
        <button
          class="form-btn cancel"
          v-show="editMode"
          @click="editMode = false"
        >
          취소
        </button>
      </div>
    </div>
    <hr class="border-gray-300 my-4" />
    <div class="todo-list">
      <TodoListItem
        v-for="todo in todos"
        :key="todo.todoId"
        :todo="todo"
        @toggleComplete="handleToggleComplete"
        @delete="deleteTodo"
      />
    </div>
  </div>
</template>

<script>
import { reactive, ref, onMounted, computed } from "vue"
import TodoListItem from "@/components/RightAsidebar/TodoListItem.vue"
import { useStore } from "vuex"

export default {
  name: "Todo",
  components: {
    TodoListItem,
  },
  setup() {
    // const todos = ref([])
    const store = useStore()
    const todos = computed(() => {
      return store.state.office.todos
    })
    const formData = reactive({
      title: "",
      content: "",
      day: "",
    })
    const editMode = ref(false)

    const getTodos = () => {
      store.dispatch("office/getTodos")
    }

    const createTodos = async () => {
      if (!formData.title || !formData.content) {
        alert("내용을 입력하세요")
        return
      }

      try {
        const todo = {
          title: formData.title,
          content: formData.content,
          day: formData.day,
          done: false,
        }
        store.dispatch("office/createTodo", todo)
        Object.keys(formData).forEach(key => {
          formData[key] = ""
        })
      } catch (error) {
        console.log(error)
      }
    }

    const handleAddBtnClick = () => {
      if (editMode.value) {
        createTodos()
      } else {
        editMode.value = true
      }
    }

    const handleToggleComplete = async todoId => {
      store.dispatch("office/toggleTodoDone", todoId)
    }

    const deleteTodo = todoId => {
      store.dispatch("office/deleteTodo", todoId)
    }

    onMounted(() => {
      getTodos()
    })

    return {
      todos,
      formData,
      editMode,
      handleAddBtnClick,
      handleToggleComplete,
      deleteTodo,
    }
  },
}
</script>

<style lang="scss" scoped>
.todo-container {
  width: 360px;
  @apply h-full overflow-hidden flex flex-col py-6 px-4;

  .todo-form {
    @apply grid gap-4;

    .input-container {
      @apply grid gap-2;

      input,
      textarea {
        @apply bg-gray-100 py-2 px-4 text-sm rounded;
      }

      textarea {
        @apply resize-none;
      }
    }

    .button-container {
      @apply flex gap-1;

      .form-btn {
        @apply text-sm font-medium text-white flex-1 py-2 flex items-center justify-center rounded-lg;

        &.add {
          @apply bg-blue-800;
        }

        &.cancel {
          @apply bg-gray-400;
        }
      }
    }
  }

  .todo-list {
    @apply grid gap-4 max-h-full overflow-auto;

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
  }
}
</style>
