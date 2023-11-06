<template>
    <router-view />
</template>


<script lang='ts' setup>
</script>


<style lang='scss'>
body {
    width: 100vw;
    height: 100vh;
    color: white;
}

.header, .footer {
    background-color: #ffffff;
    border-right: none;
}

.aside {
    background-color: blue;
    border-right: none;
}

.main {
    background-color: yellow;
    color: #1a1a1a;
}

.header {
    height: 10vh;
    width: 100vw;
}

.aside {
    width: 10vw;
    height: 90vh;
}

.main {
    width: 90vw;
    height: 90vh;
}
</style>
