<template>
  <div>
    <message :message="this.message" :color="color"/>
    <button class="button" @click="this.close">Close</button>
  </div>
</template>

<script>
import Message from "@/components/message";
export default {
  name: "modal-message",
  components: {Message},
  props: ['close', 'message'],
  data(){
    return{
      color: 'color: white'
    }
  }
}
</script>

<style scoped>

</style>