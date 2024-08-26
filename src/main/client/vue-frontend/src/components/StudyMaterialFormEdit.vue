<template>
  <div class="container">
    <h3 class="text-start">Edit Study Material</h3>
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
          v-model="studyMaterialData.name"
      />
    </div>
    <div class="mb-3 text-start">
      <label class="form-label" for="authorName">
        Author Name
      </label>
      <input
          type="text"
          class="form-control"
          placeholder="Author Name"
          v-model="studyMaterialData.authorName"
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
import StudyMaterialService from "@/services/StudyMaterialService";
const router = useRouter();
const route = useRoute();

const studyMaterialData = ref({
  name: '',
  authorName: ''
});
let studyMaterialId = ref('');

function redirectBack() {
  router.back();
}

async function saveForm() {
  try {
    const response = await StudyMaterialService.putStudyMaterial(studyMaterialId, studyMaterialData.value);
    console.log('Study material saved:', response.data);
    redirectBack();
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error saving study material:', error);
  }
}

async function deleteStudent() {
  try {
    const response = await StudyMaterialService.deleteStudyMaterial(studyMaterialId);
    console.log('Study material deleted:', response.data);
    redirectBack();
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error deleting study material:', error);
  }
}

onMounted(async () => {
  studyMaterialId = route.params.studyMaterialId;
  try {
    const response = await StudyMaterialService.getStudyMaterial(studyMaterialId);
    studyMaterialData.value = response.data;
    console.log('Study material loaded:', response.data);
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error fetching study material data:', error);
  }
});

const isFormInvalid = computed(() => { return !studyMaterialData.value.name || !studyMaterialData.value.authorName; })

</script>