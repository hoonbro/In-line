<template>
  <div class="input-container" :class="{ redbox: required && isEmpty }">
    <input
      :type="type"
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
      @focus="labelActive = true"
      @blur="onBlur"
      autocomplete="off"
    />
    <label class="label" :class="{ active: labelActive }">{{ label }}</label>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity"
export default {
  name: "TextInput",
  props: {
    type: {
      type: String,
      default: "text",
    },
    modelValue: {
      type: String,
    },
    label: {
      type: String,
      default: "라벨",
    },
    required: {
      type: Boolean,
      default: false,
    },
    shouldEmitEvent: {
      type: Boolean,
      default: false,
    },
  },
  emits: ["update:modelValue", "blur"],
  setup(props, { emit }) {
    const labelActive = ref(Boolean(props.modelValue))

    const isEmpty = ref(false)

    const onBlur = e => {
      labelActive.value = props.modelValue ? true : false
      const inputValue = e.target.value
      console.log("onBlur 실행")
      if (props.required) {
        console.log("required 요소")
        isEmpty.value = !inputValue ? true : false
        console.log(isEmpty.value)
      }
      if (props.shouldEmitEvent) emit("blur", e)
    }

    return {
      labelActive,
      isEmpty,
      onBlur,
    }
  },
}
</script>

<style scoped lang="scss">
.input-container {
  @apply w-full bg-gray-50 rounded-md border border-gray-300 relative;

  label {
    transform: translateY(-50%);
    @apply absolute top-1/2 left-4 transition-all text-gray-600;
  }
  label.active {
    transform: translateY(0);
    @apply top-2 text-xs font-medium text-gray-400;
  }
  input {
    @apply w-full pt-6 pb-2 px-4 bg-transparent z-10 relative outline-none ring-red-900;
  }
}

.redbox {
  @apply bg-red-50 border-red-400;
}
</style>
