<template>
  <div id="app">
    <navBar/>
    <router-view></router-view>
  </div>
</template>

<script>
import navBar from "@/components/NavBar.vue";
export default {
  name: 'App',
  components: {
    navBar
  }
}
</script>