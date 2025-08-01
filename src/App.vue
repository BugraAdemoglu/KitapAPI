<template>
  <div id="app-container">

    <div class="main-title-container">
      <h1>Mini Kitap Kulübüne Hoşgeldiniz</h1>
    </div>
    <header class="app-header">
      <nav>
        <RouterLink to="/">Kitaplar</RouterLink>
        <RouterLink to="/add">Kitap Ekle</RouterLink>
      </nav>
    </header>

    <RouterView />
  </div>
</template>

<script setup>

import { onMounted } from 'vue';
import { useUserStore } from './stores/user';

import { RouterLink, RouterView } from 'vue-router'


const userStore = useUserStore();

onMounted(() => {
  userStore.fetchUsers();

});

</script>

<style>

.main-title-container {
  margin-bottom: 2rem;
  text-align: center;
}

.main-title-container h1 {
  font-size: 2.2rem;
  color: #f5f5f5;
  font-weight: 300;
  margin: 0;
  border-bottom: 1px solid #444;
  padding-bottom: 1rem;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
  background-color: #212529;
  color: #e0e0e0;
  margin: 0;
}
#app {
  min-height: 100vh;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

#app-container {
  max-width: 900px;
  margin: 0;
  padding: 2rem;
  width: 100%;
}

.app-header {
  background-color: #1e1e1e;
    padding: 1rem 2rem;
    border-radius: 8px;
    margin-bottom: 2rem;
    box-shadow: 0 2px 4px rgba(0,0,0,0.5);
    border: 1px solid #333;
}
nav {
  display: flex;
  justify-content: center;
  gap: 2rem;
}

nav a {
  font-weight: bold;
  color: #bdbdbd;
  text-decoration: none;
  font-size: 1.1rem;
  padding: 0.5rem;
  border-radius: 4px;
  background-color: transparent;
  border-bottom: 2px solid transparent;
  transition: all 0.2s ease-in-out;
}

nav a:hover {
  color: #ffffff;
  background-color: #2c2c2c;
}

nav a.router-link-exact-active {
  color: #ffffff;
  border-bottom-color: #ffffff;
  background-color: transparent;
}

nav a.router-link-exact-active:hover {
    background-color: transparent;
    cursor: default;
}


.user-selector {
  background-color: #1e1e1e;
  padding: 0.75rem 1.5rem;
  margin-bottom: 2rem;
  border-radius: 8px;
  display: flex;
  align-items: center;
  gap: 1rem;
  border: 1px solid #333;
}
.user-selector label {
  font-weight: bold;
  color: #bdbdbd;
}
.user-selector select {
  padding: 0.5rem;
  background-color: #333;
  color: #fff;
  border: 1px solid #444;
  border-radius: 4px;
}
</style>
