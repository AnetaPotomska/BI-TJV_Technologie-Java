<template>
  <div class="container">
  <h3 class="text-start">Add Student</h3>
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
    </div>
  </form>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { computed, ref } from 'vue';
import StudentService from "@/services/StudentService";
const router = useRouter();
const studentData = ref({
  firstName: '',
  lastName: '',
  className: ''
});
function redirectBack() {
  router.back();
}
async function saveForm() {
  try {
    const response = await StudentService.postStudent(studentData.value);
    console.log('Student saved:', response.data);
    redirectBack();
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error saving student:', error);
  }
}

const isFormInvalid = computed(() => { return !studentData.value.firstName || !studentData.value.lastName || !studentData.value.className; })

</script>