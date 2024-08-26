<template>
  <nav class="navbar navbar-expand navbar-dark bg-dark">
    <div class="container-fluid">
      <a class="navbar-brand">
        <router-link to="/" class="nav-link" active-class="active" aria-current="page">School App</router-link>
      </a>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <router-link to="/students" class="nav-link" aria-current="page">Students</router-link>
          </li>
          <li class="nav-item">
            <router-link to="/courses" class="nav-link" aria-current="page">Courses</router-link>
          </li>
          <li class="nav-item">
            <router-link to="/study-materials" class="nav-link" aria-current="page">Study Materials</router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'nav-bar'
}
</script>

<style scoped>
.navbar {
  margin-bottom: 20px;
}
</style>