<template>
  <div main-container>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
* {
  box-sizing: border-box;
}
*:before,
*:after {
  box-sizing: inherit;
}
body,
ul {
  margin: 0;
  padding: 0;
}
ul {
  list-style: none;
}
#main-container {
  top: 0;
  left: 0;
  font: 14px/1.4em 'Helvetica Neue', Helvetica, 'Microsoft Yahei', Arial,
    sans-serif;
}
html,
body {
  height: 100%;
  overflow: hidden;
  margin: 0;
  padding: 0;
}
</style>
