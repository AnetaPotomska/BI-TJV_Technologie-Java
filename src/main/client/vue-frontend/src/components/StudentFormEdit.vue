<template>
  <div class="container">
  <h3 class="text-start">Edit Student</h3>
  </div>
  <form class="container mb-3">
    <div class="mb-3 text-start">
      <label class="form-label" for="firstName">
        First Name
      </label>
      <input
          type="text"
          class="form-control"
          placeholder="First Name"
          v-model="studentData.firstName"
      />
    </div>
    <div class="mb-3 text-start">
      <label class="form-label" for="lastName">
        Last Name
      </label>
      <input
          type="text"
          class="form-control"
          placeholder="Last Name"
          v-model="studentData.lastName"
      />
    </div>
    <div class="mb-3 text-start">
      <label class="form-label" for="className">
        Class Name
      </label>
      <input
          type="text"
          class="form-control"
          placeholder="Class Name"
          v-model="studentData.className"
      />
    </div>
    <div class="d-flex  justify-content-between">
      <div>
        <button
            type="submit"
            class="btn btn-primary me-2"
            :disabled="isFormInvalid"
            @click.prevent="saveForm"
        >Save</button>
        <button
            class="btn btn-secondary"
            @click.prevent="redirectBack"
        >Cancel</button>
      </div>
      <div>
        <button
            class="btn btn-danger"
            @click.prevent="deleteStudent"
        >Delete</button>
      </div>
    </div>
  </form>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router';
import {ref, onMounted, computed} from 'vue';
import StudentService from "@/services/StudentService";

const router = useRouter();
const route = useRoute();

const studentData = ref({
  firstName: '',
  lastName: '',
  className: ''
});

let studentId = ref('');

function redirectBack() {
  router.back();
}

async function saveForm() {
  try {
    const response = await StudentService.putStudent(studentId, studentData.value);
    console.log('Student saved:', response.data);
    redirectBack();
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error saving student:', error);
  }
}

async function deleteStudent() {
  try {
    const response = await StudentService.deleteStudent(studentId);
    console.log('Student deleted:', response.data);
    redirectBack();
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error deleting student:', error);
  }
}

onMounted(async () => {
  studentId = route.params.studentId;
  try {
    const response = await StudentService.getStudent(studentId);
    studentData.value = response.data;
    console.log('Student loaded:', response.data);
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error fetching student data:', error);
  }
});

const isFormInvalid = computed(() => { return !studentData.value.firstName || !studentData.value.lastName || !studentData.value.className; })

</script>