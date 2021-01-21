<template>
  <div class="searchBlock">
    <div class="overflow">
      <table>
        <tr>
          <td>Id</td>
          <td>Name</td>
        </tr>
        <tr v-for="world in worlds" v-bind:key="world.id">
          <td>{{ world.id }}</td>
          <td>{{ world.name }}</td>
        </tr>
      </table>
    </div>
    <button  class="button" @click="loadWorlds">Reload</button>
  </div>
</template>

<script>
export default {
  name: "world-read",
  data() {
    return {
      worlds: [],
      url: 'http://localhost:8080/api'
    }
  },
  methods: {
    loadWorlds() {
      this.worlds = [];
      let worldsApi = this.$resource(this.url + '/world/readAll');
      worldsApi.get().then(response => {
        response.json().then(worlds => {
          worlds.forEach(world => {
            this.worlds.push(world);
          })
        })
      })
    }
  },
  created() {
    this.loadWorlds();
  }

}
</script>

<style scoped>

</style>