<template>
  <div class="container">
  <h3 class="text-start">Add Course</h3>
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
    </div>
  </form>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { computed, ref } from 'vue';
import CourseService from "@/services/CourseService";
const router = useRouter();
const courseData = ref({
  name: '',
  description: '',
  maxCapacity: 0
});

function redirectBack() {
  router.back();
}
async function saveForm() {
  try {
    const response = await CourseService.postCourse(courseData.value);
    console.log('Course saved:', response.data);
    redirectBack();
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error saving course:', error);
  }
}

const isFormInvalid = computed(() => { return !courseData.value.name || !courseData.value.description || courseData.value.maxCapacity < 0; })

</script>