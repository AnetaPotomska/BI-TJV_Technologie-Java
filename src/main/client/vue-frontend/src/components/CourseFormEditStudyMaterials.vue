<template>
  <div class="container">
    <div class="d-flex justify-content-between">
      <h3>List of Study materials for This Course</h3>
      <button
          class="btn btn-secondary"
          @click="redirectBack"
      >Go back</button>
    </div>
    <form class="container mb-3">
      <div class="mb-3 text-start">
        <label class="form-label" for="name">
          Add study material (type study material id)
        </label>
        <input
            type="text"
            class="form-control"
            placeholder="Study material id"
            v-model="studyMaterialIdToAdd"
        />
      </div>
      <div>
        <button
            type="button"
            class="btn btn-primary me-2"
            :disabled="isFormInvalid"
            @click="addStudyMaterial"
        >Add Study Material</button>
      </div>
    </form>

    <table class="table table-hover table-striped text-center">
      <thead>
      <th>Id</th>
      <th>Name</th>
      <th>Author Name</th>
      <th>Action</th>
      </thead>
      <tbody>
      <tr v-for="studyMaterial in studyMaterialTest" v-bind:key = "studyMaterial.id">
        <td>{{studyMaterial.id}}</td>
        <td>{{studyMaterial.name}}</td>
        <td>{{studyMaterial.authorName}}</td>
        <td>
          <button class="btn btn-danger me-2 btn-sm" @click="deleteStudyMaterialFromCourse(studyMaterial.id)">Delete from course</button>
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

const router = useRouter();
const route = useRoute();

const studyMaterialTest = ref([]);

/*const studyMaterialData = ref({
  name: '',
  authorName: ''
});*/

let courseId = ref('');
let studyMaterialIdToAdd = ref('');

function redirectBack() {
  router.back();
}

async function addStudyMaterial() {
  try {
    const response = await CourseService.putStudyMaterial(courseId, studyMaterialIdToAdd.value);
    console.log('Study material added:', response.data);
    await loadStudyMaterialData();
  } catch (error) {
    console.error('Study material student:', error);
    alert(error.response.data.message);
  }
}

async function deleteStudyMaterialFromCourse(studyMaterialId) {
  try {
    const response = await CourseService.deleteStudyMaterial(courseId, studyMaterialId);
    console.log('Study material deleted:', response.data);
    await loadStudyMaterialData();
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error deleting study material:', error);
  }
}

async function loadStudyMaterialData() {
  try {
    const response = await CourseService.getStudyMaterials(courseId);
    studyMaterialTest.value = [...response.data];
    console.log('Type:', typeof response.data);
    console.log('Study materials loaded:', response.data);
  } catch (error) {
    alert(error.response.data.message);
    console.error('Error getting study materials:', error);
  }
}

onMounted(async () => {
  courseId = route.params.courseId;
  await loadStudyMaterialData();
});

const isFormInvalid = computed(() => { return !studyMaterialIdToAdd.value; })


</script>

<style scoped>
thead th {
  background-color: #4c4c4c;
  color: white;
}
</style>