import axios from "axios";

const STUDENT_API_BASE_URL = "http://localhost:8080/student"

class StudentService {
    getStudents() {
        return axios.get(STUDENT_API_BASE_URL);
    }
    getStudent(studentId) {
        return axios.get(`${STUDENT_API_BASE_URL}/${studentId}`);
    }
    postStudent(newStudent) {
        return axios.post(STUDENT_API_BASE_URL, newStudent);
    }
    putStudent(studentId, newStudent) {
        const studentToSent = {
            firstName: newStudent.firstName,
            lastName: newStudent.lastName,
            className: newStudent.className
        };
        return axios.put(`${STUDENT_API_BASE_URL}/${studentId}`, studentToSent);
    }
    deleteStudent(studentId) {
        return axios.delete(`${STUDENT_API_BASE_URL}/${studentId}`);
    }
}

export default new StudentService();