<template>
  <div>
    <div class="createBlock">
      <label for="positionx">Coordinate X</label><input style="width: 80px" id="positionx" type="text"
                                                        v-model="positionx"><br>
      <label for="positiony">Coordinate Y</label><input style="width: 80px" id="positiony" type="text"
                                                        v-model="positiony"><br>
      <label for="world">World</label><select id="world" v-model="worldId">
      <option v-for="world in worlds" v-bind:key="world.id" v-bind:value="world.id">
        {{ world.name }}
      </option>
    </select><br>
      <label for="caste">Caste</label><select id="caste" v-model="casteId">
      <option v-for="caste in castes" v-bind:key="caste.id" v-bind:value="caste.id">
        {{ caste.name }}
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
  name: "settlement-form",
  components: {Message},
  data() {
    return {
      population: 0,
      positionx: '',
      positiony: '',
      worldId: '',
      casteId: '',
      worlds: [],
      castes: [],
      url: '/api',
      message: '',
      color: ''
    }
  },
  created() {
    let casteApi = this.$resource(this.url + '/caste/readAll');
    let worldApi = this.$resource(this.url + '/world/readAll');
    casteApi.get().then(result => {
      result.json().then(data => {
        data.forEach(caste => {
          this.castes.push(caste)
        })
      })
    });
    worldApi.get().then(result => {
      result.json().then(data => {
        data.forEach(world => {
          this.worlds.push(world)
        })
      })
    });
  },
  methods: {
    create() {
      if (this.positionx.trim() === '' || this.positiony.trim() === '' || this.worldId.toString().trim() === '' || this.casteId.toString().trim() ==='') {
        this.color = 'color: red';
        this.message = "Fill in all the fields";
      } else {
        let settlement = {
          population: this.population, positionX: this.positionx, positionY: this.positiony,
          worldId: this.worldId, casteId: this.casteId
        }
        let settlementApi = this.$resource(this.url + '/settlement/create');
        settlementApi.save({}, settlement).then(response => {
          console.log(response);
          this.color = 'color: white';
          this.message = "New settlement successfully created";
        }, response => {
          console.log(response);
          this.color = 'color: red';
          this.message = "Failed to create new settlement";
        })
      }
    }
  }
}
</script>

<style scoped>

</style>