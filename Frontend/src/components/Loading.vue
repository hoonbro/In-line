<template>
  <div>
    <div id="loading">
      Spinner 1
      <div class="spinner"></div>
    </div>

    <div id="loading2">spinner 2</div>
  </div>
</template>

<script>
export default {
  name: "Loading",
  components: {},
  setup() {
    return {}
  },
}
</script>

<style lang="scss" scoped>
#loading {
  display: none;
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
}
#loading .spinner {
  box-sizing: border-box;
  position: absolute;
  top: 50%;
  left: 50%;
  width: 64px;
  height: 64px;
  margin-top: -32px;
  margin-left: -32px;
  border-radius: 50%;
  border: 8px solid transparent;
  border-top-color: #f19022;
  border-bottom-color: #f19022;
  animation: spinner 0.8s ease infinite;
}

#loading2 {
  position: absolute;
  top: 50px;
  right: 0px;
  width: 200px;
  height: 100px;
  border: 1px solid red;
}

.spinner2::after {
  content: "";
  box-sizing: border-box;
  position: absolute;
  top: 50%;
  left: 50%;
  width: 64px;
  height: 64px;
  margin-top: -32px;
  margin-left: -32px;
  border-radius: 50%;
  border: 4px solid lightgrey;
  border-top-color: blue;
  animation: spinner 0.8s linear infinite;
}
</style>
