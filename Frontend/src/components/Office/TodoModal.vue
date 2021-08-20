<template>
  <Modal ref="modalEl">
    <template v-slot:modal-body>
      <header class="mb-6">
        <h1 class="text-3xl font-bold">{{ todoData.userName }}'s Todo</h1>
      </header>
      <div class="mb-10">
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-xl font-bold">InProgress👍</h3>
          <button
            @click="
              expandedSection =
                expandedSection === 'inProgress' ? 'done' : 'inProgress'
            "
          >
            <span
              class="material-icons"
              v-if="expandedSection === 'inProgress'"
            >
              keyboard_arrow_up
            </span>
            <span class="material-icons" v-else>
              keyboard_arrow_down
            </span>
          </button>
        </div>
        <div class="todo-list" v-if="expandedSection === 'inProgress'">
          <p v-if="workInProgress && !workInProgress.length">
            얘는 일을 안하나봐요!
          </p>
          <div v-for="(todo, idx) in workInProgress" :key="todo.todoId">
            <p
              class="text-md font-bold py-2"
              :style="`z-index: ${idx}`"
              v-if="
                idx === 0 ||
                  workInProgress[idx].day !== workInProgress[idx - 1].day
              "
            >
              {{ todo.day }}
            </p>
            <div class="py-2 px-4 bg-gray-50">
              <h4 class="text-lg font-bold mb-1">{{ todo.title }}</h4>
              <p class="text-sm">{{ todo.content }}</p>
            </div>
          </div>
        </div>
      </div>
      <div>
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-xl font-bold">Done✅✅</h3>
          <button
            @click="
              expandedSection =
                expandedSection === 'done' ? 'inProgress' : 'done'
            "
          >
            <span class="material-icons" v-if="expandedSection === 'done'">
              keyboard_arrow_up
            </span>
            <span class="material-icons" v-else>
              keyboard_arrow_down
            </span>
          </button>
        </div>
        <div class="todo-list" v-if="expandedSection === 'done'">
          <p v-if="doneTodos && !doneTodos.length">얘는 일을 안하나봐요!</p>
          <div v-for="(todo, idx) in doneTodos" :key="todo.todoId">
            <p
              class="text-md font-bold py-2"
              :style="`z-index: ${idx}`"
              v-if="idx === 0 || doneTodos[idx].day !== doneTodos[idx - 1].day"
            >
              {{ todo.day }}
            </p>
            <div class="py-2">
              <h4 class="text-lg font-bold mb-1">{{ todo.title }}</h4>
              <p>{{ todo.content }}</p>
            </div>
          </div>
        </div>
      </div>
    </template>
  </Modal>
</template>

<script>
import Modal from "@/components/Common/Modal.vue"
import { ref } from "@vue/reactivity"
import { computed, onMounted } from "@vue/runtime-core"
import { useStore } from "vuex"
export default {
  name: "TodoModal",
  components: {
    Modal,
  },
  props: {
    todoData: {
      type: Object,
    },
  },
  setup(props) {
    const store = useStore()
    const modalEl = ref(null)
    const todos = ref([])
    const expandedSection = ref("inProgress")

    const workInProgress = computed(() =>
      todos.value
        .filter(todo => !todo.done)
        .sort((a, b) => (a.day === b.day ? 0 : a.day < b.day ? -1 : 1))
    )
    const doneTodos = computed(() =>
      todos.value
        .filter(todo => todo.done)
        .sort((a, b) => (a.day === b.day ? 0 : a.day < b.day ? -1 : 1))
    )

    onMounted(async () => {
      todos.value = await store.dispatch(
        "office/getTodos",
        props.todoData.userId
      )
    })

    return {
      expandedSection,
      modalEl,
      workInProgress,
      doneTodos,
    }
  },
}
</script>

<style scoped lang="scss">
.todo-list {
  @apply h-60 overflow-auto relative grid gap-4 p-2;
}
</style>
