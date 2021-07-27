<template>
  <div>
    <div class="face" v-if="videoList > 16">
      <div class="picture"></div>
      <!-- <img
        src="http://placeimg.com/400/240/any?t=1627265125999"
        alt=""
        class="picture"
      /> -->
    </div>
    <div class="face2" v-else-if="videoList > 9">
      <div class="picture2"></div>
      <!-- <img
        src="http://placeimg.com/400/240/any?t=1627265125999"
        alt=""
        class="picture2"
      /> -->
    </div>
    <div class="face3" v-else-if="videoList > 4">
      <div class="picture3"></div>
      <!-- <img
        src="http://placeimg.com/400/240/any?t=1627265125999"
        alt=""
        class="picture3"
      /> -->
    </div>
    <div class="face4" v-else>
      <div class="picture4"></div>
      <!-- <img
        src="http://placeimg.com/400/240/any?t=1627265125999"
        alt=""
        class="picture4"
      /> -->
    </div>
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
}
</script>

<style scoped lang="scss">
// @apply grid auto-cols-fr;
// .face {
//   @apply w-64 h-36;
// }

// .face2 {
//   @apply w-48 h-28;
// }

// .face2 {
//   @apply w-32 h-16;
// }

.picture {
  @apply rounded-2xl;
  width: 336px;
  height: 189px;
  border: 3px solid white;
}

.picture2 {
  @apply rounded-2xl;
  width: 416px;
  height: 234px;
  border: 3px solid white;
}

.picture3 {
  @apply rounded-2xl;
  width: 560px;
  height: 315px;
  border: 3px solid white;
}

.picture4 {
  @apply rounded-2xl;
  width: 800px;
  height: 450px;
  border: 3px solid white;
}
</style>
