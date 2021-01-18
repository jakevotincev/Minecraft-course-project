<template>
  <div>
    <div class = "createBlock">
      <label for="name">Name</label>
      <input style="width: 200px" id="name" type="text" v-model="name">
      <br>
      <button @click="create" class="button">Create!</button>
    </div>
    <div>
      <message :message="message" :color="color"></message>
    </div>
  </div>
</template>

<script>
import Message from "@/components/message";

export default {
  name: "world-form",
  components: {Message},
  data() {
    return {
      name: '',
      url: 'http://localhost:8080/api',
      message: '',
      color: ''
    }
  },
  methods: {
    create() {
      let name = this.name;
      //validation
      let world = {name: name};
      let worldApi = this.$resource(this.url + '/world/create');
      worldApi.save({}, world).then(result => {
            console.log(result);
            this.color = 'color: white';
            this.message = "World \"" + name + "\" successfully created";
          }, result => {
            console.log(result);
            this.color = 'color: red';
            this.message = "Failed to create world \"" + name + "\"";
          }
      )

    }
  },

}
</script>

<style scoped>

</style>