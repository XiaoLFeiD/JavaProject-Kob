<template>
    <div class="playground">
        <GameMap />
        <div class="alert alert-info" role="alert" v-if="$store.state.pk.a_id == $store.state.user.id">
            本局你出现在蓝色方
        </div>
        <div class="alert alert-info" role="alert" v-if="$store.state.pk.b_id == $store.state.user.id">
            本局你出现在红色方
        </div>
    </div>
</template>

<script>

import GameMap from './GameMap.vue'
export default {
    components: {
        GameMap,
    }
}
</script>

<style scoped>
div.playground {
    width: 60vw;
    height: 70vh;
    margin: 40px auto;
    /* background-color: lightblue; */
}
div.alert{
    margin-top: 20px;
}
</style>
