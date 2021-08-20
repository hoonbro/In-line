<template>
  <div class="relative select-container">
    <select
      :value="field.value"
      :class="{ 'select-label': !field.value }"
      @change="handleChange"
    >
      <option disabled value="">{{ field.label }}</option>
      <option v-for="item in items" :key="item[`${name}Id`]">
        {{ item[`${name}Name`] }}
      </option>
    </select>
    <span class="material-icons icon">
      keyboard_arrow_down
    </span>
  </div>
</template>

<script>
export default {
  name: "SelectInput",
  props: {
    modelValue: {
      type: String,
    },
    name: {
      type: String,
    },
    field: {
      type: Object,
    },
    items: {
      type: Array,
    },
  },
  emits: ["update:modelValue"],
  setup(_, { emit }) {
    const handleChange = event => {
      emit("update:modelValue", event.target.value)
    }
    return { handleChange }
  },
}
</script>

<style scoped lang="scss">
.select-container {
  @apply w-full bg-gray-50 rounded-md;

  select {
    appearance: none;
    @apply w-full bg-transparent outline-none px-3 py-4 border rounded-md border-gray-300 focus:border-blue-600;

    option {
      @apply py-2;
    }
  }

  .icon {
    transform: translateY(-50%);
    @apply select-none absolute top-1/2 right-3;
  }
}
.select-label {
  @apply text-gray-600;
}
</style>
