<template>
  <div class="container">
  <h3 class="text-start">Add Study Material</h3>
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
    </div>
  </form>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { computed, ref } from 'vue';
import StudyMaterialService from "@/services/StudyMaterialService";
const router = useRouter();
const studyMaterialData = ref({
  name: '',
  authorName: ''
});
function redirectBack() {
  router.back();
}
async function saveForm() {
  try {
    const response = await StudyMaterialService.postStudyMaterial(studyMaterialData.value);
    console.log('Study material saved:', response.data);
    redirectBack();
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error saving study material:', error);
  }
}

const isFormInvalid = computed(() => { return !studyMaterialData.value.name || !studyMaterialData.value.authorName; })

</script>