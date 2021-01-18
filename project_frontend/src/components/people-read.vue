<template>
  <div class="searchBlock">
    <div class="overflow">
      <table>
        <tr>
          <td>Id</td>
          <td>Name</td>
          <td>Health</td>
          <td>Strength</td>
          <td>Money</td>
          <td>Pregnant?</td>
          <td>Gestational Age</td>
          <td>SettlementId</td>
          <td>Caste</td>
        </tr>
        <tr v-for="human in people" v-bind:key="human.id">
          <td>{{ human.id }}</td>
          <td>{{ human.name }}</td>
          <td>{{ human.health }}</td>
          <td>{{ human.strength }}</td>
          <td>{{ human.money }}</td>
          <td>
            <p v-if="human.isPregnant">Yes</p>
            <p v-else>No</p>
          </td>
          <td>{{ human.gestationalAge }}</td>
          <td>{{ human.settlementId }}</td>
          <td>{{ human.casteId }}</td>
        </tr>
      </table>
    </div>
    <button class="button" @click="loadPeople">Reload</button>
  </div>
</template>

<script>
export default {
  name: "people-read",
  data() {
    return {
      people: [],
      url: 'http://localhost:8080/api',
      castes: []
    }
  },
  methods: {
    loadCastes(){
      this.castes = [];
      let castesApi = this.$resource(this.url + '/caste/readAll');
      castesApi.get().then(response => {
        response.json().then(castes => {
          castes.forEach(caste => {
            this.castes.push(caste);
          })
        })
      })
    },
    loadPeople() {
      this.people = [];
      this.loadCastes();
      let peopleApi = this.$resource(this.url + '/people/readAll');
      peopleApi.get().then(response => {
        response.json().then(people => {
          people.forEach(human => {
            human.casteId = this.castes[human.casteId-1].name;
            this.people.push(human);
          })
        })
      })
    }
  },
  created() {
    this.loadPeople();
  }
}
</script>

<style scoped>

</style>