package cz.cvut.fit.tjv.server.controller;

import cz.cvut.fit.tjv.server.domain.Course;
import cz.cvut.fit.tjv.server.domain.Student;
import cz.cvut.fit.tjv.server.domain.StudyMaterial;
import cz.cvut.fit.tjv.server.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/course")
@CrossOrigin("http://localhost:3000")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @GetMapping
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @GetMapping(path = "{courseId}")
    public Course getCourse(@PathVariable("courseId") Long id) {
        return courseService.getCourse(id);
    }

    @GetMapping(path = "{courseId}/student")
    public List<Student> getStudents(@PathVariable("courseId") Long id) {
        return courseService.getStudents(id);
    }

    @GetMapping(path = "{courseId}/study-material")
    public List<StudyMaterial> getStudyMaterials(@PathVariable("courseId") Long id) {
        return courseService.getStudyMaterials(id);
    }

    @PostMapping
    public ResponseEntity<Course> createNewCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.addNewCourse(course));
    }

    @DeleteMapping(path = "{courseId}")
    public void deleteCourse(@PathVariable("courseId") Long id) {
        courseService.deleteCourse(id);
    }

    @DeleteMapping(path = "{courseId}/student/{studentId}")
    public void deleteStudent(@PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId) {
        courseService.deleteStudent(courseId, studentId);
    }

    @DeleteMapping(path = "{courseId}/study-material/{studyMaterialId}")
    public void deleteStudyMaterial(@PathVariable("courseId") Long courseId, @PathVariable("studyMaterialId") Long studyMaterialId) {
        courseService.deleteStudyMaterial(courseId, studyMaterialId);
    }

    @PutMapping(path = "{courseId}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable("courseId") Long id,
            @RequestBody Course course) {
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    @PutMapping(path = "{courseId}/student/{studentId}")
    public ResponseEntity<Course> updateStudentsForCourse (
            @PathVariable Long courseId,
            @PathVariable Long studentId) {
        return ResponseEntity.ok(courseService.enrollStudent(courseId, studentId));
    }

    @PutMapping(path = "{courseId}/study-material/{studyMaterialId}")
    public ResponseEntity<Course> updateStudyMaterials (
            @PathVariable Long courseId,
            @PathVariable Long studyMaterialId) {
        return ResponseEntity.ok(courseService.addStudyMaterial(courseId, studyMaterialId));
    }
}
