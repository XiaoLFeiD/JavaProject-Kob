<template>
  <NavBar/>
  <router-view></router-view>
</template>

<script>
import NavBar from "./components/NavBar.vue"
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"


export default{
  //存储当前template中用到的组件
  //导航栏
  components:{
    NavBar
  },

}
</script>
<style>
  body{
    background-image: url("@/assets/images/1.JPG");
    background-size: cover;
  }
</style>
