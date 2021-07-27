<template>
  <div class="input-container">
    <input
      :type="type"
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
      @focus="labelActive = true"
      @blur="labelActive = modelValue ? true : false"
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
  },
  emits: ["update:modelValue"],
  setup(props) {
    const labelActive = ref(Boolean(props.modelValue))

    return {
      labelActive,
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
    @apply w-full pt-6 pb-2 px-4 bg-transparent z-10 relative outline-none border focus:border-blue-600;
  }
}
</style>
