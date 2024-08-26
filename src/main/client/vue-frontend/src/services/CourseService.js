import axios from "axios";

const COURSES_API_BASE_URL = "http://localhost:8080/course"

class CourseService {
    getCourses() {
        return axios.get(COURSES_API_BASE_URL);
    }
    getCourse(courseId) {
        return axios.get(`${COURSES_API_BASE_URL}/${courseId}`);
    }
    getStudents(courseId) {
        return axios.get(`${COURSES_API_BASE_URL}/${courseId}/student`);
    }
    getStudyMaterials(courseId) {
        return axios.get(`${COURSES_API_BASE_URL}/${courseId}/study-material`);
    }
    postCourse(newCourse) {
        return axios.post(COURSES_API_BASE_URL, newCourse);
    }
    putCourse(courseId, newCourse) {
        const courseToSent = {
            name: newCourse.name,
            description: newCourse.description,
            maxCapacity: newCourse.maxCapacity
        };
        return axios.put(`${COURSES_API_BASE_URL}/${courseId}`, courseToSent);
    }
    putStudent(courseId, studentId) {
        return axios.put(`${COURSES_API_BASE_URL}/${courseId}/student/${studentId}`);
    }
    putStudyMaterial(courseId, studyMaterialId) {
        return axios.put(`${COURSES_API_BASE_URL}/${courseId}/study-material/${studyMaterialId}`);
    }
    deleteCourse(courseId) {
        return axios.delete(`${COURSES_API_BASE_URL}/${courseId}`);
    }
    deleteStudent(courseId, studentId) {
        return axios.delete(`${COURSES_API_BASE_URL}/${courseId}/student/${studentId}`);
    }
    deleteStudyMaterial(courseId, studyMaterialId) {
        return axios.delete(`${COURSES_API_BASE_URL}/${courseId}/study-material/${studyMaterialId}`);
    }
}

export default new CourseService();