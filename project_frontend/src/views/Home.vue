<template>
  <div>
    <div id="heading">
      <h1>Course Project</h1>
      <h2>Minecraft</h2>
    </div>
    <contacts/>
  </div>
</template>

<script>

import Contacts from "@/components/contacts";

export default {
  name: 'Home',
  components: {
    Contacts
  }
}
</script>

<style>
.back {
  background-image: url("../assets/back.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  margin: 0;
}
h1 {
  font-size: 50px;
}
#heading{
  position: absolute;
  top: 40%;
  width: 99%;
}
</style>
