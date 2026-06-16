<template>
    <PlayGround />
    <button type="button" class="btn btn-outline-info btn-lg" @click="back">返回</button>
</template>

<script>

import PlayGround from "../../components/PlayGround.vue"
import router from "../../router/index"
export default {
    components: {
        PlayGround,
    },
    setup() {
        const back = () => {
            router.push({name:"record_index"})
        }   
        return {
            back,
        }
    },
   
}
</script>

<style scoped>
.btn{
    float: right;
    margin-right: 30px;
}
</style>
