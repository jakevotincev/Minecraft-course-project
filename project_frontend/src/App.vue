<template>
  <div id="app">
    <div id="nav">
      <router-link to="/">Home</router-link>
      |
      <router-link to="/main">Main</router-link>
    </div>
    <router-view/>
  </div>
</template>

<style>
@font-face {
  font-family: "Pixel";
  src: url("./fonts/myfont.otf") format("opentype");
}


@font-face {
  font-family: 'Minecraft';
  src: url('./fonts/mcfont.eot');
  src: url('./fonts/mcfont.eot?#iefix') format('embedded-opentype'),
  url('./fonts/mcfont.woff') format('woff'),
  url('./fonts/mcfont.ttf')  format('truetype');
}

#app {
  font-family: "Minecraft",serif;
  text-align: center;
  color: white;
  font-size: 20px;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: white;
}

#nav a.router-link-exact-active {
  color: #1850a5;
}

</style>
