<template>
  <div>
    <div class="face" v-if="videoList <= 9">
      <img
        src="http://placeimg.com/400/240/any?t=1627265125999"
        alt=""
        class="picture"
      />
    </div>
    <div class="face2" v-if="videoList > 9">
      <img
        src="http://placeimg.com/400/240/any?t=1627265125999"
        alt=""
        class="picture"
      />
    </div>
    <!-- <p>{{ videoList }}</p> -->
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

<style>
.face {
  @apply grid grid-rows-3;
  width: 400px;
  height: 240px;
  display: flex;
  /* border: 5px solid skyblue; */
  border-radius: 15px;
}

.face2 {
  width: 300px;
  height: 180px;
  display: flex;
  /* border: 5px solid skyblue; */
  border-radius: 15px;
}

.picture {
  width: 395px;
  height: 230px;
  border-radius: 10px;
}
</style>
