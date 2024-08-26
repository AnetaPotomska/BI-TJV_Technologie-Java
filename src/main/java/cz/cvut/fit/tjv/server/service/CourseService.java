package cz.cvut.fit.tjv.server.service;

import cz.cvut.fit.tjv.server.domain.Course;
import cz.cvut.fit.tjv.server.domain.Student;
import cz.cvut.fit.tjv.server.domain.StudyMaterial;
import cz.cvut.fit.tjv.server.exception.ApiBadRequestException;
import cz.cvut.fit.tjv.server.exception.ApiNotFoundException;
import cz.cvut.fit.tjv.server.repository.CourseRepository;
import cz.cvut.fit.tjv.server.repository.StudentRepository;
import cz.cvut.fit.tjv.server.repository.StudyMaterialRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    private final StudentRepository studentRepository;

    private final StudyMaterialRepository studyMaterialRepository;


    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository, StudyMaterialRepository studyMaterialRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.studyMaterialRepository= studyMaterialRepository;
    }

    // -----------------------------------------------------------------------------------

    // GET
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    // GET
    public Course getCourse(Long id) {
        if(!courseRepository.existsById(id)) {
            throw new ApiNotFoundException("course with id " + id + " does not exists");
        }
        return courseRepository.findById(id).get();
    }

    // GET
    public List<Student> getStudents(Long id) {
        if(!courseRepository.existsById(id)) {
            throw new ApiNotFoundException("course with id " + id + " does not exists");
        }
        return courseRepository.findById(id).get().getStudents().stream().toList();
    }

    // GET
    public List<StudyMaterial> getStudyMaterials(Long id) {
        if(!courseRepository.existsById(id)) {
            throw new ApiNotFoundException("course with id " + id + " does not exists");
        }
        return courseRepository.findById(id).get().getStudyMaterials().stream().toList();
    }

    // POST
    public Course addNewCourse(Course course) {
        return courseRepository.save(course);
    }

    // DELETE
    public void deleteCourse(Long id) {
        if(!courseRepository.existsById(id)) {
            throw new ApiNotFoundException("course with id " + id + " does not exists");
        }
        courseRepository.deleteById(id);
    }

    // DELETE
    public void deleteStudent(Long courseId, Long studentId) {
        if(!courseRepository.existsById(courseId)) {
            throw new ApiNotFoundException("course with id " + courseId + " does not exists");
        }
        Course course = courseRepository.findById(courseId).get();
        boolean isStudentPresent = false;
        Student studentToRemove = new Student();
        for(Student student : course.getStudents()) {
            if(Objects.equals(student.getId(), studentId)) {
                isStudentPresent = true;
                studentToRemove = student;
                break;
            }
        }
        if(isStudentPresent) {
            course.getStudents().remove(studentToRemove);
            courseRepository.save(course);
        }
        else {
            throw new ApiBadRequestException("student with id " + studentId + " isn't present in course with id " + courseId);
        }
    }

    // DELETE
    public void deleteStudyMaterial(Long courseId, Long studyMaterialId) {
        if(!courseRepository.existsById(courseId)) {
            throw new ApiNotFoundException("course with id " + courseId + " does not exists");
        }
        Course course = courseRepository.findById(courseId).get();
        boolean isStudyMaterialPresent = false;
        StudyMaterial studyMaterialToRemove = new StudyMaterial();
        for(StudyMaterial studyMaterial : course.getStudyMaterials()) {
            if(Objects.equals(studyMaterial.getId(), studyMaterialId)) {
                isStudyMaterialPresent = true;
                studyMaterialToRemove = studyMaterial;
                break;
            }
        }
        if(isStudyMaterialPresent) {
            studyMaterialToRemove.removeCourse();
            studyMaterialRepository.save(studyMaterialToRemove);
            courseRepository.save(course);
        }
        else {
            throw new ApiBadRequestException("study material with id " + studyMaterialId + " isn't present in course with id " + courseId);
        }
    }

    // PUT
    /**
     * Updates optionally name, description annd maxCapacity. Maximal capacity can be updated only if it is still larger/equal than occupied number.
     */
    @Transactional
    public Course updateCourse(Long id, Course course) {
        if(!courseRepository.existsById(id)) {
            throw new ApiNotFoundException("course with id " + id + " does not exists");
        }
        Course existingCourse = courseRepository.findById(id).get();
        existingCourse.setName(course.getName());
        existingCourse.setDescription(course.getDescription());
        if(course.getMaxCapacity() < 0) {
            throw new ApiBadRequestException("max capacity cannot be negative");
        }
        if(course.getMaxCapacity() < existingCourse.getOccupied()) {
            throw new ApiBadRequestException("course with id " + id + " is occupied by " + existingCourse.getOccupied() + " students, co capacity " + course.getMaxCapacity() + " cannot be accepted");
        }
        else {
            existingCourse.setMaxCapacity(course.getMaxCapacity());
        }
        return courseRepository.save(existingCourse);
    }

    // PUT
    /**
     * Student can be enrolled to course only if there is enough space.
     */
    public Course enrollStudent(Long courseId, Long studentId) {
        if(!courseRepository.existsById(courseId)) {
            throw new ApiNotFoundException("course with id " + courseId + " does not exists");
        }
        if(!studentRepository.existsById(studentId)) {
            throw new ApiNotFoundException("student with id " + studentId + " does not exists");
        }
        Course course = courseRepository.findById(courseId).get();
        Student student = studentRepository.findById(studentId).get();
        if(course.getStudents().contains(student)) {
            throw new ApiNotFoundException("student with id " + studentId + " is already enrolled in this course");
        }
        if(course.getOccupied() >= course.getMaxCapacity()) {
            throw new ApiBadRequestException("course with id " + courseId + " doesn't have any space left, max capacity: " + course.getMaxCapacity());
        }
        course.getStudents().add(student);
        return courseRepository.save(course);
    }

    // PUT

    /**
     * Study material can be assigned to course only if it weren't assigned to other course before.
     */
    public Course addStudyMaterial(Long courseId, Long studyMaterialId) {
        if(!courseRepository.existsById(courseId)) {
            throw new ApiNotFoundException("course with id " + courseId + " does not exists");
        }
        if(!studyMaterialRepository.existsById(studyMaterialId)) {
            throw new ApiNotFoundException("study material with id " + studyMaterialId + " does not exists");
        }
        Course course = courseRepository.findById(courseId).get();
        StudyMaterial studyMaterial = studyMaterialRepository.findById(studyMaterialId).get();
        if(courseRepository.existsCourseByStudyMaterialsContains(studyMaterial)) {
            throw new ApiBadRequestException("study material with id " + studyMaterialId + " is already assigned to course with id " + courseId);
        }
        studyMaterial.setCourse(course);
        studyMaterialRepository.save(studyMaterial);
        return courseRepository.save(course);
    }
}
