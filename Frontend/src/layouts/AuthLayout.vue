<template>
  <AuthNav />
  <router-link :to="{ name: 'ResetPassword' }">reset</router-link> |
  <router-link :to="{ name: 'ChangePassword' }">change</router-link>
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
