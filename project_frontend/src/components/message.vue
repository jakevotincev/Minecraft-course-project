<template>
<p :style = "color" >{{message}}</p>
</template>

<script>
export default {
  props: ['message', 'color'],
  name: "message"
}
</script>

<style scoped>

</style>