<template>
  <div class="container">
    <div class="container d-flex justify-content-between align-items-center">
      <h3>List of Courses</h3>
      <button
          class="btn btn-primary"
          @click="redirectToCourseFormAdd"
      >Add Course</button>
    </div>
    <table class="table table-hover table-striped text-center">
      <thead>
      <th>Id</th>
      <th>Name</th>
      <th>Occupied</th>
      <th>Max Capacity</th>
      <th>Edit</th>
      </thead>
      <tbody>
      <tr v-for="course in courses" v-bind:key = "course.id">
        <td>{{course.id}}</td>
        <td>{{course.name}}</td>
        <td>{{course.occupied}}</td>
        <td>{{course.maxCapacity}}</td>
      <td>
        <button @click="goToEditPage(course.id, 'basic')" class="btn btn-primary me-2 btn-sm">Basic</button>
        <button @click="goToEditPage(course.id, 'students')" class="btn btn-primary me-2 btn-sm">Students</button>
        <button @click="goToEditPage(course.id, 'study-materials')" class="btn btn-primary btn-sm">Study Materials</button>
      </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
const router = useRouter();
function redirectToCourseFormAdd() {
  router.push('/courses/add');
}
function goToEditPage(id, page) {
  router.push({path: `/courses/edit/${id}/${page}`});
}
</script>

<script>
import CourseService from "@/services/CourseService";

export default {
  data() {
    return {
      courses : []
    }
  },
  methods: {
    async getCourses() {
      try {
        const response = await CourseService.getCourses();
        this.courses = response.data;
        console.log('Courses loaded:', response.data);
      } catch (error) {
        alert(error.response.data.message);
        console.error('Error getting courses:', error);
      }
    }
  },
  created() {
    this.getCourses()
  }
}
</script>

<style scoped>
thead th {
  background-color: #4c4c4c;
  color: white;
}
</style>