import axios from "axios";

const STUDY_MATERIAL_API_BASE_URL = "http://localhost:8080/study-material"

class StudyMaterialService {
    getStudyMaterials() {
        return axios.get(STUDY_MATERIAL_API_BASE_URL);
    }
    getStudyMaterial(studyMaterialId) {
        return axios.get(`${STUDY_MATERIAL_API_BASE_URL}/${studyMaterialId}`);
    }
    postStudyMaterial(newStudyMaterial) {
        return axios.post(STUDY_MATERIAL_API_BASE_URL, newStudyMaterial);
    }
    putStudyMaterial(studyMaterialId, newStudyMaterial) {
        const studyMaterialToSent = {
            name: newStudyMaterial.name,
            authorName: newStudyMaterial.authorName
        };
        return axios.put(`${STUDY_MATERIAL_API_BASE_URL}/${studyMaterialId}`, studyMaterialToSent);
    }
    deleteStudyMaterial(studyMaterialId) {
        return axios.delete(`${STUDY_MATERIAL_API_BASE_URL}/${studyMaterialId}`);
    }
}

export default new StudyMaterialService();