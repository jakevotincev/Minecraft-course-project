<template>
  <div class="contacts">
    <hr color="white" size="3" width="50%"/>
    <div>
      Frontend  Vue.js  by  Votintsev Evgenii
      <a href="https://github.com/ezNNP/Course-Project-BD/tree/main/project_frontend" target="_blank"><img class="gitPic" src="../assets/gitlogo.png" ></a>
    </div>
    <div>
      Backend  Spring  by  Javoronkov Grigory
      <a href="https://github.com/ezNNP/Course-Project-BD/tree/main/project_backend" target="_blank"><img class="gitPic" src="../assets/gitlogo.png" ></a>
      </div>
  </div>
</template>

<script>
export default {
  name: "contacts"
}
</script>

<style scoped>
.contacts {
  top: 85%;
  position: absolute;
  width: 99%;
  font-size: 25px;
}

hr {
  border-radius: 100px;
}
.gitPic{
  width: 35px;
}
</style>