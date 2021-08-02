<template>
  <router-view />
</template>

<script>
export default {
  setup() {
    console.log(process.env)
  },
}
</script>

<style lang="scss">
body {
  background: white;
  font-family: "Noto Sans KR", sans-serif;
}
</style>
