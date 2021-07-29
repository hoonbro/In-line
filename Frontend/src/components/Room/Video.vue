<template>
  <div class="box">
    <img
      src="https://img1.daumcdn.net/thumb/C500x500.fpng/?fname=http://t1.daumcdn.net/brunch/service/user/azBb/image/30IyjiaExqw30pVdBvuEnAzjr2M.png"
      alt=""
    />
  </div>
</template>

<script>
export default {
  name: "Video",
  props: {
    videoList: {
      type: Number,
    },
  },
  setup() {
    return {}
  },
}
</script>

<style scoped lang="scss">
img {
  width: 400px;
  height: 225px;
}

.picture {
  @apply rounded-2xl;
  width: 336px;
  height: 189px;
  border: 3px solid black;
  background-color: red;
}

.picture2 {
  @apply rounded-2xl;
  width: 416px;
  height: 234px;
  border: 3px solid white;
}

.picture3 {
  @apply rounded-2xl;
  width: 416px;
  height: 234px;
  border: 3px solid white;
}

.picture4 {
  @apply rounded-2xl;
  width: 800px;
  height: 450px;
  border: 3px solid white;
}
</style>
