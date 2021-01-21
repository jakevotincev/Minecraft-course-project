<template>
  <div class="searchBlock">
    <div class="overflow">
      <table>
        <tr>
          <td>Id</td>
          <td>Name</td>
          <td>Health</td>
          <td>Damage</td>
          <td>Pregnant?</td>
          <td>Gestational Age</td>
          <td>Owner</td>
        </tr>
        <tr v-for="animal in animals" v-bind:key="animal.id">
          <td>{{ animal.id }}</td>
          <td>{{ animal.name }}</td>
          <td>{{ animal.health }}</td>
          <td>{{ animal.damage }}</td>
          <td>
            <p v-if="animal.isPregnant">Yes</p>
            <p v-else>No</p>
          </td>
          <td>{{ animal.gestationalAge }}</td>
          <td>{{ animal.ownerId }}</td>
        </tr>
      </table>
    </div>
    <button class="button" @click="loadAnimals">Reload</button>
  </div>
</template>

<script>
export default {
  name: "animal-read",
  data() {
    return {
      animals: [],
      url: '/api',
      people: []
    }
  },
  methods: {
    loadAnimals() {
      this.people = [];
      this.animals = [];
      let peopleApi = this.$resource(this.url + '/people/readAll');
      peopleApi.get().then(response => {
        response.json().then(people => {
          people.forEach(human => {
            this.people.push(human);
          })
          let animalsApi = this.$resource(this.url + '/animal/readAll');
          animalsApi.get().then(response => {
            response.json().then(animals => {
              animals.forEach(animal => {
                let human = this.people.find(human => human.id === animal.ownerId);
                if (typeof human == "undefined") animal.ownerId = "nobody";
                else animal.ownerId = human.name;
                if (!animal.isPregnant) animal.gestationalAge = '-';
                this.animals.push(animal);
              })
            })
          })

        })
      })
    },
  },
  created() {
    this.loadAnimals();
  }
}

</script>

<style scoped>

</style>