<template>
  <div>
    <h2 style="font-size: 20px">In the minecraft world, sometimes people make new people. </h2>
    <h2 style="font-size: 20px">If the are ready, you can help them. Chose settlement id
      and click.</h2>
    <label for="id">Id</label>
    <input style="width: 80px" id="id" type="text" v-model="settlementId" @change = countPeople>
    <button class="button" @click="increasePopulation">Increase Population</button>
    <div id="modalWindow" class="modal">
      <div class="modal-content">
        <modal-message :close="close" :message="message"/>
      </div>
    </div>
  </div>
</template>

<script>
import ModalMessage from "@/components/modal-message";

export default {
  name: "new-people-comp",
  components: {ModalMessage},
  data() {
    return {
      settlementId: '',
      url: 'http://localhost:8080/api',
      message: '',
      people: []
    }
  },
  methods: {
    countPeople() {
      this.people = [];
      let peopleApi = this.$resource(this.url + '/people/readAll');
      peopleApi.get().then(response => {
        response.json().then(people => {
          people.forEach(human => {
            this.people.push(human);
          });
        })
      })

    },
    increasePopulation() {
      let count = this.people.length;

      let populationApi = this.$resource(this.url + '/logic/increasePopulation');
      populationApi.get({settlementId: this.settlementId}).then(response => {
        console.log(response);
        this.people = [];
        let peopleApi = this.$resource(this.url + '/people/readAll');
        peopleApi.get().then(response => {
          response.json().then(people => {
            people.forEach(human => {
              this.people.push(human);
            });
            let diff = this.people.length - count;
            if (diff > 1) this.message = diff + " people have just been born. You can check them in explore section."
            else if (diff === 1) this.message = diff + " human have just been born. You can check him in explore section."
            else this.message = "Nobody have just been born. Add new pregnant people or change settlement."
            this.show();
          })
        })
      }, response => {
        console.log(response);
        this.message = "Something went wrong("
        this.show();
      })
    },
    show() {
      document.getElementById('modalWindow').style.display = "block";
    }
    ,
    close() {
      document.getElementById('modalWindow').style.display = "none";
    }
  }
}
</script>

<style scoped>
.modal {
  display: none;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.8);
}

.modal-content {
  margin: 250px auto;
  padding: 30px;
  width: 30%;
  border-bottom: 6px solid #565656;
  border-right: 6px solid #565656;
  border-left: 4px solid lightgrey;
  border-top: 4px solid lightgrey;
  background-color: #AAAAAA;
  border-radius: 5px;
}
</style>