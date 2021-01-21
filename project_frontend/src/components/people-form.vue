<template>
  <div>
    <div class="createBlock">
      <label for="name">Name</label><input style="width: 135px" id="name" type="text" v-model="name"><br>
      <label for="money">Money</label><input style="width: 120px" id="money" type="text" v-model="money"><br>
      <label for="isPreg">Pregnant?</label>
      <input id="isPreg" type="checkbox" v-model="isPregnant"><br>
      <label for="gestAge" v-show="isPregnant">Gestational age</label><input style="width: 80px" v-show="isPregnant"
                                                                             id="gestAge"
                                                                             type="text"
                                                                             v-model="gestationalAge">
      <br>
      <label for="settlement">Settlement</label>
      <select id="settlement" v-model="settlementId">
        <option v-for="settlement in settlements" v-bind:key="settlement.id" v-bind:value="settlement">
          Id: {{ settlement.id }} Caste: {{ settlement.caste }}
        </option>
      </select><br>
      <button class="button" @click="create">Create!</button>

    </div>
    <div>
      <message :message="message" :color="color"></message>
    </div>
  </div>
</template>

<script>
import Message from "@/components/message";

export default {
  name: "people-form",
  components: {Message},
  data() {
    return {
      name: '',
      health: '20',
      strength: '1',
      money: '',
      isPregnant: false,
      gestationalAge: '',
      settlementId: '',
      caste: '',
      settlements: [],
      castes: [],
      url: 'http://localhost:8080/api',
      message: '',
      color: ''
    }
  },
  created() {
    let casteApi = this.$resource(this.url + '/caste/readAll');
    let settlementApi = this.$resource(this.url + '/settlement/readAll');
    casteApi.get().then(result => {
      result.json().then(data => {
        data.forEach(caste => {
          this.castes.push(caste);
        })
        settlementApi.get().then(result => {
          result.json().then(data => {
            data.forEach(settlement => {
              settlement.caste = this.castes[settlement.casteId - 1].name;
              this.settlements.push(settlement)
            })
          })
        })
      })
    });


  },
  methods: {
    create() {
      let gestationalAge = this.gestationalAge;
      if (!this.isPregnant) gestationalAge = -1;

      if (this.name.trim() === "" || this.money.trim() === "" || gestationalAge.toString().trim() === "") {
        this.color = 'color: red';
        this.message = "Fill in all the fields";
      } else {
        let human = {
          name: this.name, health: this.health, strength: this.strength,
          money: this.money, isPregnant: this.isPregnant, gestationalAge: gestationalAge,
          settlementId: this.settlementId.id, casteId: this.settlementId.casteId
        };

        let peopleApi = this.$resource(this.url + '/people/create');
        peopleApi.save({}, human).then(response => {
          console.log(response);
          this.color = 'color: white';
          this.message = "Human\"" + this.name + "\" successfully created";
        }, response => {
          console.log(response);
          this.color = 'color: red';
          this.message = "Failed to create human \"" + this.name + "\"";
        })
      }
    }
  }
}
</script>

<style scoped>

#isPreg {
  width: 30px;
  height: 30px;
  outline: 0;
}

</style>
