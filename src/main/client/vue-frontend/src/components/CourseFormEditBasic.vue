<template>
  <div class="container">
    <h3 class="text-start">Edit Course</h3>
  </div>
  <form class="container mb-3">
    <div class="mb-3 text-start">
      <label class="form-label" for="name">
        Name
      </label>
      <input
          type="text"
          class="form-control"
          placeholder="Name"
          v-model="courseData.name"
      />
    </div>
    <div class="mb-3 text-start">
      <label class="form-label" for="description">
        Description
      </label>
      <input
          type="text"
          class="form-control"
          placeholder="Description"
          v-model="courseData.description"
      />
    </div>
    <div class="mb-3 text-start">
      <label class="form-label" for="maxCapacity">
        Max Capacity
      </label>
      <input
          type="number"
          class="form-control"
          placeholder="10"
          v-model="courseData.maxCapacity"
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
            @click.prevent="deleteCourse"
        >Delete</button>
      </div>
    </div>
  </form>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router';
import { ref, onMounted, computed } from 'vue';
import CourseService from "@/services/CourseService";

const router = useRouter();
const route = useRoute();

const courseData = ref({
  name: '',
  description: '',
  maxCapacity: 0
});

let courseId = ref('');

function redirectBack() {
  router.back();
}

async function saveForm() {
  try {
    const response = await CourseService.putCourse(courseId, courseData.value);
    console.log('Course saved:', response.data);
    redirectBack();
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error saving course:', error);
  }
}

async function deleteCourse() {
  try {
    const response = await CourseService.deleteCourse(courseId);
    console.log('Course deleted:', response.data);
    redirectBack();
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error deleting course:', error);
  }
}

onMounted(async () => {
  courseId = route.params.courseId;
  try {
    const response = await CourseService.getCourse(courseId);
    courseData.value = response.data;
    console.log('Course loaded:', response.data);
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error fetching course data:', error);
  }
});

const isFormInvalid = computed(() => { return !courseData.value.name || !courseData.value.description || courseData.value.maxCapacity < 0; })

</script>