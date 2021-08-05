<template>
  <AuthNav />
  <router-view />
</template>

<script>
import AuthNav from "@/components/Nav/AuthNav.vue"

export default {
  name: "AuthLayout",
  components: { AuthNav },
}
</script>

<style></style>
