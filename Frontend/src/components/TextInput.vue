<template>
  <div class="grid gap-1">
    <div
      class="input-container"
      :class="{
        error: field.errors && Object.keys(field.errors).length,
      }"
    >
      <input
        :type="field.type"
        :value="modelValue"
        ref="input"
        @input="handleInput"
        @focus="labelActive = true"
        @blur="handleBlur"
        autocomplete="off"
        :maxlength="maxlength"
        :disabled="disabled"
        :class="{ 'select-none': disabled }"
      />
      <label class="label" :class="{ active: labelActive }">
        {{ field.label }}
      </label>
    </div>
    <transition-group>
      <div
        class="error-list"
        v-if="field.errors && Object.keys(field.errors).length"
      >
        <p
          v-for="(error, key) in field.errors"
          :key="key"
          class="error-list-item"
        >
          {{ error }}
        </p>
      </div>
    </transition-group>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity"
export default {
  name: "TextInput2",
  props: {
    modelValue: {
      type: String,
    },
    name: {
      type: String,
    },
    formData: Object,
    field: Object,
    maxlength: {
      type: Number,
      default: 200,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  emits: ["update:modelValue", "update:validate"],
  setup(props, { emit }) {
    const labelActive = ref(Boolean(props.modelValue))
    const input = ref(null)

    const validate = () => {
      props.field.validators.forEach(validator => {
        const res = validator(props.formData, props.name, input.value.value)
        emit("update:validate", res)
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
        validate()
      }
    }

    return {
      input,
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
.error-list {
  @apply text-sm text-red-500 font-medium grid gap-1;

  .error-list-item {
    @apply relative pl-3;

    &::before {
      content: "";
      transform: translateY(-50%);
      @apply absolute top-1/2 left-0 w-1 h-1 bg-red-400 rounded-full;
    }
  }
}
</style>
