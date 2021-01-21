<template>
  <div>
    <div class="createBlock">
      <label for="name">Name</label><input style="width: 135px" id="name" type="text" v-model="name"><br>
      <label for="isPreg">Pregnant?</label>
      <input id="isPreg" type="checkbox" v-model="isPregnant"><br>
      <label for="gestAge" v-show="isPregnant">Gestational age</label><input style="width: 80px" v-show="isPregnant"
                                                                             id="gestAge" type="text"
                                                                             v-model="gestationalAge">
      <br>
      <label for="owner">Owner</label>
      <input id="owner" style="width: 135px" type="text" v-model="ownerId" @change="loadHuman">
      <message :message="message" :color="color"></message>
      <button class="button" @click="create">Create!</button>
    </div>
    <div>
      <message :message="errM" :color="errColor"></message>
    </div>
  </div>
</template>

<script>
import Message from "@/components/message";

export default {
  name: "animal-form",
  components: {Message},
  data() {
    return {
      name: '',
      health: '30',
      damage: '1',
      isPregnant: false,
      gestationalAge: '',
      ownerId: '',
      people: [],
      url: '/api',
      ownerName: '',
      message: '',
      color: '',
      errM: '',
      errColor: ''
    }
  },
  created() {
    let peopleApi = this.$resource(this.url + '/people/readAll');
    peopleApi.get().then(result => {
      result.json().then(data => {
        data.forEach(human => {
          this.people.push(human)
        })
      })
    });
  },
  methods: {
    create() {
      if (!this.isPregnant) this.gestationalAge = -1;

      if (this.name.trim() === "" || this.gestationalAge.toString().trim() === "") {
        this.errColor = 'color: red';
        this.errM = "Fill in all the fields";
      } else {
        let ownerId = this.ownerId;

        if (ownerId.trim() === ""){
          ownerId = -1;
        }

        let animal = {
          name: this.name, health: this.health, damage: this.damage,
          isPregnant: this.isPregnant, gestationalAge: this.gestationalAge, ownerId: ownerId
        }
        console.log(animal);
        let animalApi = this.$resource(this.url + '/animal/create');
        animalApi.save({}, animal).then(response => {
              console.log(response);
              this.errColor = 'color: white';
              this.errM = "Animal\"" + this.name + "\" successfully created";
            }, response => {
              console.log(response);
              this.errColor = 'color: red';
              this.errM = "Failed to create animal \"" + this.name + "\"";
            }
        )
      }
    },
    loadHuman() {
      console.log(this.ownerId);
      if (this.ownerId.trim() === '') {
        this.message = '';
        this.color = '';
        return;
      }

      let peopleApi = this.$resource(this.url + '/people/read');
      peopleApi.get({id: this.ownerId}).then(response => {
        response.json().then(human => {
          this.color = "color: white";
          this.message = "Owner name: " + human.name;
        })

      }, response => {
        console.log(response);
        this.color = "color: white";
        this.message = "Human with id \"" + this.ownerId + "\" not found";
      })
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