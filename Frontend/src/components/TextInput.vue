<template>
  <div class="grid gap-2">
    <div
      class="input-container"
      :class="{
        error: field.errors && Object.keys(field.errors).length,
      }"
    >
      <input
        :type="field.type"
        :value="modelValue"
        @input="handleInput"
        @focus="labelActive = true"
        @blur="handleBlur"
        autocomplete="off"
      />
      <label class="label" :class="{ active: labelActive }">
        {{ field.label }}
      </label>
    </div>
    <div class="text-sm text-red-500 font-medium grid gap-1">
      <p v-for="(error, key) in field.errors" :key="key">
        {{ error }}
      </p>
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity"
export default {
  name: "TextInput",
  props: {
    modelValue: {
      type: String,
    },
    name: {
      type: String,
    },
    field: Object,
    formData: Object,
  },
  emits: ["update:modelValue"],
  setup(props, { emit }) {
    const labelActive = ref(Boolean(props.modelValue))

    const validate = () => {
      props.field.validators.forEach(validator => {
        validator(props.formData, props.name)
      })
    }

    const handleBlur = () => {
      validate()
      labelActive.value = props.modelValue ? true : false
    }

    const handleInput = event => {
      // emit 이벤트를 먼저 발생시키지 않으면, 한 타이밍 늦게 error 관련 이벤트가 처리된다.
      emit("update:modelValue", event.target.value)
      // Error가 있는 경우, Input Event 발생 시 매번 검사
      if (props.field.errors && Object.keys(props.field.errors).length) {
        console.log("input")
        validate()
      }
    }

    return {
      labelActive,
      handleBlur,
      handleInput,
    }
  },
}
</script>

<style scoped lang="scss">
.input-container {
  @apply w-full bg-gray-50 relative;

  label {
    transform: translateY(-50%);
    @apply absolute top-1/2 left-4 transition-all text-gray-600;
  }

  label.active {
    transform: translateY(0);
    @apply top-2 text-xs font-medium text-gray-400;
  }

  input {
    @apply w-full pt-6 pb-2 px-4 bg-transparent z-10 relative outline-none rounded-md border border-gray-300 focus:border-blue-600;
  }
  &.error {
    @apply bg-red-50;

    input {
      @apply border-red-500;
    }
  }
}
</style>