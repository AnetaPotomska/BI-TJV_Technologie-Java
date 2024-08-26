<template>
  <div class="container">
    <div class="d-flex justify-content-between">
      <h3>List of Students for This Course</h3>
      <button
          class="btn btn-secondary"
          @click="redirectBack"
      >Go back</button>
    </div>

    <form class="container mb-3">
      <div class="mb-3 text-start">
        <label class="form-label" for="name">
          Add student (type students id)
        </label>
        <input
            type="text"
            class="form-control"
            placeholder="Student id"
            v-model="studentIdToAdd"
        />
        <select v-model="studentIdToAdd">
          <option v-for="student in studentAllData" v-bind:key = "student.id" :value="student.id">{{ student.firstName }} {{ student.lastName }}</option>
        </select>
      </div>
      <div>
        <button
            type="button"
            class="btn btn-primary me-2"
            :disabled="isFormInvalid"
            @click="addStudent"
        >Add Student</button>
      </div>
    </form>

    <table class="table table-hover table-striped text-center">
      <thead>
      <th>Id</th>
      <th>First Name</th>
      <th>Last Name</th>
      <th>Class Name</th>
      <th>Email</th>
      <th>Action</th>
      </thead>
      <tbody>
      <tr v-for="student in studentData" v-bind:key = "student.id">
        <td>{{student.id}}</td>
        <td>{{student.firstName}}</td>
        <td>{{student.lastName}}</td>
        <td>{{student.className}}</td>
        <td>{{student.email}}</td>
        <td>
          <button class="btn btn-danger me-2 btn-sm" @click="deleteStudentFromCourse(student.id)">Delete from course</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>



<script setup>
import { useRouter, useRoute } from 'vue-router';
import {ref, onMounted, computed} from 'vue';
import CourseService from "@/services/CourseService";
import StudentService from "@/services/StudentService";

const router = useRouter();
const route = useRoute();

const studentData = ref({
  firstName: '',
  lastName: '',
  className: ''
});

const studentAllData = ref({
  firstName: '',
  lastName: '',
  className: ''
});

let courseId = ref('');
let studentIdToAdd = ref('');

function redirectBack() {
  router.back();
}

async function addStudent() {
  try {
    const response = await CourseService.putStudent(courseId, studentIdToAdd.value);
    console.log('Student added:', response.data);
    await loadStudentData();
  } catch (error) {
    console.error('Error adding student:', error);
    alert(error.response.data.message);
  }
}

async function deleteStudentFromCourse(studentId) {
  try {
    const response = await CourseService.deleteStudent(courseId, studentId);
    console.log('Student deleted:', response.data);
    await loadStudentData();
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error deleting student:', error);
  }
}

async function loadAllStudentData() {
  try {
    const response = await StudentService.getStudents(courseId);
    studentAllData.value = response.data;
    console.log('Students loaded:', response.data);
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error getting students:', error);
  }
}

async function loadStudentData() {
  try {
    const response = await CourseService.getStudents(courseId);
    studentData.value = response.data;
    console.log('Students loaded:', response.data);
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error getting students:', error);
  }
}

onMounted(async () => {
  courseId = route.params.courseId;
  await loadStudentData();
  await loadAllStudentData();
});

const isFormInvalid = computed(() => { return !studentIdToAdd.value; })


</script>

<style scoped>
thead th {
  background-color: #4c4c4c;
  color: white;
}
</style>