<template>
  <div class="main">
    <div>
      <h2 style="font-size: 20px">What do you want to create?</h2>
      <div>
        <button v-for="tab in tabs" v-bind:key="tab" v-on:click="currentTab = tab" v-bind:class="['button', {active: currentTab === tab}]" >
          {{tab}}
        </button>
      </div>
      <component v-bind:is="currentTabComponent"></component>
      <div>
    </div>
      <h2 style="font-size: 20px">What do you want to explore?</h2>
      <div>
        <button v-on:click="currentSearchTab = 'World'" v-bind:class="['button', {active: currentSearchTab === 'World'}]">Worlds</button>
        <button v-on:click="currentSearchTab = 'People'" v-bind:class="['button', {active: currentSearchTab === 'People'}]">People</button>
        <button v-on:click="currentSearchTab = 'Animal'" v-bind:class="['button', {active: currentSearchTab === 'Animal'}]">Animals</button>
        <button v-on:click="currentSearchTab = 'Settlement'" v-bind:class="['button', {active: currentSearchTab === 'Settlement'}]">Settlements</button>
      </div>
      <component v-bind:is="currentSearchTabComponent"></component>
    </div>
    <new-people-comp/>
  </div>
</template>


<script>
import WorldForm from "@/components/world-form";
import PeopleForm from "@/components/people-form";
import SettlementForm from "@/components/settlement-form";
import AnimalForm from "@/components/animal-form";
import WorldRead from "@/components/world-read";
import PeopleRead from "@/components/people-read";
import AnimalRead from "@/components/animal-read";
import SettlementRead from "@/components/settlement-read";
import NewPeopleComp from "@/components/new-people-comp";
export default {
  name:"MainPage",
  components: {
    NewPeopleComp,
    SettlementRead,
    AnimalRead,
    PeopleRead,
    WorldRead,
    AnimalForm,
    SettlementForm,
    PeopleForm,
    WorldForm
     },
  data(){
    return{
      currentTab: 'World',
      currentSearchTab: 'World',
      tabs: ['World', 'People', 'Animal', 'Settlement']
    }
  },
  computed:{
    currentTabComponent (){
      return this.currentTab.toLowerCase() + "-form";
    },
    currentSearchTabComponent(){
      return this.currentSearchTab.toLowerCase() + "-read";
    }
  }
}
</script>

<style>
input,select {
  height: 40px;
  outline: 2px solid black;
  position: relative;
  margin: 10px;
  display: inline-block;
  background-color: #6f6f6f;
  background-repeat: repeat;
  padding: 0;
  font-family: Minecraft, serif;
  text-align: center;
  color: white;
  border-bottom: 2px solid #565656;
  border-right: 2px solid #565656;
  border-left: 2px solid #AAA;
  border-top: 2px solid #AAA;
  box-sizing: border-box;
  font-size: 16px;
  text-shadow: 3px 3px #4C4C4C;
}

button {
  height: 40px;

  outline: 2px solid black;
  position: relative;
  margin: 10px;
  display: inline-block;
  background-image: url('http://charliecowan.co.uk/mcbuttongenerator/button_center.png');
  background-repeat: repeat;
  padding: 5px;
  font-family: Minecraft, serif;
  text-align: center;
  color: white;
  text-shadow: 3px 3px #4C4C4C;
  border-bottom: 4px solid #565656;
  border-right: 2px solid #565656;
  border-left: 2px solid #AAA;
  border-top: 2px solid #AAA;
  box-sizing: border-box;
  font-size: 16px;
}

.button.active{
  border-bottom: 4px solid #59639A;
  border-right: 2px solid #59639A;
  border-left: 2px solid #BDC6FF;
  border-top: 2px solid #BDC6FF;
  background-image: url('http://charliecowan.co.uk/mcbuttongenerator/button_active_center.png');
  color: #FFFFA0;
}

.button:hover {
  border-bottom: 4px solid #59639A;
  border-right: 2px solid #59639A;
  border-left: 2px solid #BDC6FF;
  border-top: 2px solid #BDC6FF;
  background-image: url('http://charliecowan.co.uk/mcbuttongenerator/button_active_center.png');
  color: #FFFFA0;
}

.createBlock{
  border-bottom: 6px solid #565656;
  border-right: 6px solid #565656;
  border-left: 4px solid lightgrey;
  border-top: 4px solid lightgrey;
  background-color: #AAAAAA;
  width: 400px;
  margin-left: auto;
  margin-right: auto;
  margin-top: 15px;
  border-radius: 5px;
}
.searchBlock{
  border-bottom: 6px solid #565656;
  border-right: 6px solid #565656;
  border-left: 4px solid lightgrey;
  border-top: 4px solid lightgrey;
  background-color: #AAAAAA;
  margin-left: auto;
  margin-right: auto;
  margin-top: 15px;
  border-radius: 5px;
  text-align: center;
  display: inline-block;
  padding: 20px;
}
.overflow{
  height: 400px;
  overflow-y: auto;
}
table{
  margin-left: auto;
  margin-right: auto;
  border: white 3px;
}
td{
  padding: 8px;
}
::-webkit-scrollbar {
  width: 15px;
}
::-webkit-scrollbar-thumb {

  background-color: rgba(0,0,0,0.5);
  -webkit-box-shadow: 0 0 1px rgba(255,255,255,0.5);
}
</style>