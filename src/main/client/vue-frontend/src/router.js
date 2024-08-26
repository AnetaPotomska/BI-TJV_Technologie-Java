import { createRouter, createWebHistory } from 'vue-router';CourseFormAdd
import StudentList from "@/components/StudentList.vue";
import CourseList from "@/components/CourseList.vue";
import StudyMaterialList from "@/components/StudyMaterialList.vue";
import StudentFormAdd from "@/components/StudentFormAdd.vue";
import HomePage from "@/components/HomePage.vue";
import StudentFormEdit from "@/components/StudentFormEdit.vue";
import StudyMaterialFormAdd from "@/components/StudyMaterialFormAdd.vue";
import StudyMaterialFormEdit from "@/components/StudyMaterialFormEdit.vue";
import CourseFormAdd from "@/components/CourseFormAdd.vue";
import CourseFormEditBasic from "@/components/CourseFormEditBasic.vue";
import CourseFormEditStudents from "@/components/CourseFormEditStudents.vue";
import CourseFormEditStudyMaterials from "@/components/CourseFormEditStudyMaterials.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: HomePage },
        { path: '/students', component: StudentList },
        { path: '/students/add', component: StudentFormAdd },
        { path: '/students/edit/:studentId', component: StudentFormEdit },
        { path: '/courses', component: CourseList },
        { path: '/courses/add', component: CourseFormAdd },
        { path: '/courses/edit/:courseId/basic', component: CourseFormEditBasic },
        { path: '/courses/edit/:courseId/students', component: CourseFormEditStudents },
        { path: '/courses/edit/:courseId/study-materials', component: CourseFormEditStudyMaterials },
        { path: '/study-materials', component: StudyMaterialList },
        { path: '/study-materials/add', component: StudyMaterialFormAdd },
        { path: '/study-materials/edit/:studyMaterialId', component: StudyMaterialFormEdit }
    ]
});

export default router;