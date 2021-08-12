<template>
  <teleport to="body">
    <div class="backdrop" v-if="isVisible">
      <div class="modal-container">
        <div class="modal-content-container">
          <p v-for="text in content" :key="text">
            {{ text }}
          </p>
        </div>
        <div class="modal-btns-container">
          <slot name="modal-buttons">
            <button class="modal-btn confirm" @click="confirm">
              {{ confirmButton }}
            </button>
            <button class="modal-btn cancel" @click="cancel">
              {{ cancelButton }}
            </button>
          </slot>
        </div>
      </div>
    </div>
  </teleport>
</template>

<script>
import { ref } from "@vue/reactivity"
export default {
  name: "ConfirmModal",
  emits: ["confirm", "close"],
  props: {
    content: {
      type: Array,
      default: [],
    },
    confirmButton: {
      type: String,
      default: "확인",
    },
    cancelButton: {
      type: String,
      default: "취소",
    },
  },
  setup() {
    const isVisible = ref(false)
    const resolvePromise = ref(undefined)
    const rejectPromise = ref(undefined)

    // 부모 컴포넌트에서 접근하기 위해서는 setup 함수에서 리턴해주어야 한다.
    const show = async () => {
      return new Promise((resolve, reject) => {
        resolvePromise.value = resolve
        rejectPromise.value = reject
      })
    }

    // _confirm과 같이 _나 $로 시작해서는 안된다.
    const confirm = () => {
      isVisible.value = false
      resolvePromise.value(true)
    }

    const cancel = () => {
      isVisible.value = false
      resolvePromise.value(false)
    }

    return {
      isVisible,
      confirm,
      cancel,
      show,
      resolvePromise,
      rejectPromise,
    }
  },
}
</script>

<style scoped lang="scss">
.backdrop {
  z-index: 999;
  @apply fixed inset-0 flex justify-center;

  .modal-container {
    @apply max-w-xs w-full bg-white shadow-lg rounded p-6 grid gap-4 mt-20 mb-auto;

    .modal-content-container {
      @apply w-full grid gap-1 font-bold;
    }

    .modal-btns-container {
      @apply flex gap-2;

      .modal-btn {
        @apply flex-1 py-2 px-4 text-sm rounded;

        &.confirm {
          @apply text-white font-bold bg-blue-500;
        }

        &.cancel {
          @apply bg-gray-200;
        }
      }
    }
  }
}
</style>
