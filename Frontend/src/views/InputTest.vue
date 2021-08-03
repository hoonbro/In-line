<template>
  <TextInput2
    :field="formData.officeName"
    :formData="formData"
    v-model="formData.officeName.value"
    :name="'officeName'"
    :maxlength="20"
    @update:validate="handleUpdateValidate"
  />
</template>

<script>
import TextInput2 from "@/components/TextInput2.vue"
import { requiredValidator, emailValidator } from "@/lib/validator2"
import { reactive } from "@vue/reactivity"

export default {
  name: "InputTest",
  components: { TextInput2 },
  setup() {
    const formData = reactive({
      officeName: {
        label: "회사 이름",
        type: "text",
        value: "",
        // value: "asdf",
        validators: [emailValidator],
        errors: {},
      },
    })

    const handleUpdateValidate = data => {
      const { key, type, status, message } = data
      if (status) {
        delete formData[key].errors[type]
      } else {
        formData[key].errors[type] = message
      }
    }

    return {
      formData,
      handleUpdateValidate,
    }
  },
}
</script>

<style></style>
