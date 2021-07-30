<template>
  <div class="box" :class="{ talking: idx === 1 }">
    <img :src="srcPath" alt="엑박" />
  </div>
  <!-- <p>{{ idx }}</p> -->
</template>

<script>
export default {
  name: "Video",
  props: {
    videoList: {
      type: Number,
    },
    idx: {
      type: Number,
    },
  },
  setup(props) {
    const srcPath = require("@/assets/image-" + props.idx + ".png")
    return {
      srcPath,
    }
  },
}
</script>

<style scoped lang="scss">
// img {
//   @apply rounded-xl;
// }

img {
  @apply w-96;
  // width: 400px;
  // height: 225px;;
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

.talking {
  @apply border-4 border-blue-700;
}

.box {
  @apply w-96;
  // width: 20vw;
}
</style>
