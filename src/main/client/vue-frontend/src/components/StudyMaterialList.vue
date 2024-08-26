<template>
  <div class="container">
    <div class="container d-flex justify-content-between align-items-center">
      <h3>List of Study Materials</h3>
      <button
          class="btn btn-primary"
          @click="redirectToStudyMaterialFormAdd"
      >Add Study Material</button>
    </div>
    <table class="table table-hover table-striped text-center">
      <thead>
      <th>Id</th>
      <th>Name</th>
      <th>Author Name</th>
      </thead>
      <tbody>
      <tr v-for="studyMaterial in studyMaterials" v-bind:key = "studyMaterial.id" @click="goToEditPage(studyMaterial.id)">
        <td>{{studyMaterial.id}}</td>
        <td>{{studyMaterial.name}}</td>
        <td>{{studyMaterial.authorName}}</td>
      </tr>
      </tbody>
    </table>
  </div>

</template>

<script setup>
import { useRouter } from 'vue-router';
const router = useRouter();
function redirectToStudyMaterialFormAdd() {
  router.push('/study-materials/add');
}
function goToEditPage(id) {
  router.push({path: `/study-materials/edit/${id}`});
}
</script>

<script>
import StudyMaterialService from "@/services/StudyMaterialService";
export default {
  data() {
    return {
      studyMaterials : []
    }
  },
  methods: {
    async getStudyMaterials() {
      try {
        const response = await StudyMaterialService.getStudyMaterials();
        this.studyMaterials = response.data;
        console.log('Study materials loaded:', response.data);
      } catch (error) {
        alert(error.response.data.message);
        console.error('Error getting study materials:', error);
      }
    }
  },
  created() {
    this.getStudyMaterials()
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