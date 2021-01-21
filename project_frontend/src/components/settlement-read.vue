<template>
  <div class="searchBlock">
    <div class="overflow">
      <table>
        <tr>
          <td>Id</td>
          <td>Population</td>
          <td>Coordinate X</td>
          <td>Coordinate Y</td>
          <td>World</td>
          <td>Caste</td>
        </tr>
        <tr v-for="settlement in settlements" v-bind:key="settlement.id">
          <td>{{ settlement.id }}</td>
          <td>{{ settlement.population }}</td>
          <td>{{ settlement.positionX }}</td>
          <td>{{ settlement.positionY }}</td>
          <td>{{ settlement.worldId }}</td>
          <td>{{ settlement.casteId }}</td>
        </tr>
      </table>
    </div>
    <button class="button" @click="loadSettlements">Reload</button>
  </div>
</template>

<script>

export default {
  name: "settlement-read",
  data() {
    return {
      worlds: [],
      url: '/api',
      settlements: [],
      castes: []
    }
  },
  methods: {
    loadSettlements() {
      this.worlds = [];
      this.castes = [];
      this.settlements = [];
      let worldsApi = this.$resource(this.url + '/world/readAll');
      worldsApi.get().then(response => {
        response.json().then(worlds => {
          worlds.forEach(world => {
            this.worlds.push(world);
          })
          let castesApi = this.$resource(this.url + '/caste/readAll');
          castesApi.get().then(response => {
            response.json().then(castes => {
              castes.forEach(caste => {
                this.castes.push(caste);
              })
              let settlementsApi = this.$resource(this.url + '/settlement/readAll');
              settlementsApi.get().then(response => {
                response.json().then(settlements => {
                  settlements.forEach(settlement => {
                    let caste = this.castes.find(caste => caste.id === settlement.casteId);
                    let world = this.worlds.find(world => world.id === settlement.worldId);
                    settlement.worldId = world.name;
                    settlement.casteId = caste.name;
                    this.settlements.push(settlement);
                  })
                })
              })
            })
          })
        })
      })
    },
  },
  created() {
    this.loadSettlements();
  }
}
</script>

<style scoped>

</style>