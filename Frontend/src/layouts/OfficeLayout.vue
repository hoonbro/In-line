<template>
  <MainNav />
  <router-view />
</template>

<script>
import MainNav from "@/components/MainNav.vue"

export default {
  name: "OfficeLayout",
  components: {
    MainNav,
  },
}
</script>

<style></style>
