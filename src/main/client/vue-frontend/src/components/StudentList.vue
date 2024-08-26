<template>
  <div class="container">
    <div class="container d-flex justify-content-between align-items-center">
      <h3>List of Students</h3>
      <button
          class="btn btn-primary"
          @click="redirectToStudentFormAdd"
      >Add Student</button>
    </div>
    <table class="table table-hover table-striped text-center">
      <thead>
        <th>Id</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Class Name</th>
        <th>Email</th>
      </thead>
      <tbody>
        <tr v-for="student in students" v-bind:key = "student.id" @click="goToEditPage(student.id)">
          <td>{{student.id}}</td>
          <td>{{student.firstName}}</td>
          <td>{{student.lastName}}</td>
          <td>{{student.className}}</td>
          <td>{{student.email}}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
const router = useRouter();
function redirectToStudentFormAdd() {
  router.push('/students/add');
}
function goToEditPage(id) {
  router.push({path: `/students/edit/${id}`});
}
</script>

<script>
import StudentService from "@/services/StudentService";

  export default {
    data() {
      return {
        students : []
      }
    },
    methods: {
      async getStudents() {
        try {
          const response = await StudentService.getStudents();
          this.students = response.data;
          console.log('Students loaded:', response.data);
        } catch (error) {
          alert(error.response.data.message);
          console.error('Error getting student:', error);
        }
      }
    },
    created() {
      this.getStudents()
    }
  }
</script>

<style scoped>
.table.table-hover tr:hover {
  cursor: pointer;
}
thead th {
  background-color: #4c4c4c;
  color: white;
}
</style>