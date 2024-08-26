package cz.cvut.fit.tjv.server.service;

import cz.cvut.fit.tjv.server.domain.Course;
import cz.cvut.fit.tjv.server.domain.Student;
import cz.cvut.fit.tjv.server.exception.ApiBadRequestException;
import cz.cvut.fit.tjv.server.exception.ApiNotFoundException;
import cz.cvut.fit.tjv.server.repository.CourseRepository;
import cz.cvut.fit.tjv.server.repository.StudentRepository;
import cz.cvut.fit.tjv.server.repository.StudyMaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudyMaterialRepository studyMaterialRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        courseService = new CourseService(courseRepository, studentRepository, studyMaterialRepository);
    }

    @Test
    void willDeleteStudentFromCourse() {
        // given
        Long courseId = 1L;
        Long studentId = 2L;

        Course course = new Course();
        Student studentToRemove = new Student();
        studentToRemove.setId(studentId);
        course.getStudents().add(studentToRemove);

        when(courseRepository.existsById(courseId)).thenReturn(true);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // when
        courseService.deleteStudent(courseId, studentId);

        // then
            // verify that courseRepository.findById was called with the correct courseId
        verify(courseRepository).findById(courseId);
            // verify that studentToRemove was removed from course successfully
        assertThat(course.getStudents().isEmpty()).isTrue();
            // verify that courseRepository.save was called with the updated course
        verify(courseRepository).save(course);
    }

    @Test
    void willThrowApiBadRequestExceptionBecauseStudentIsntInCourse() {
        // given
        Long courseId = 1L;
        Long studentId = 2L;

        Course course = new Course();

        when(courseRepository.existsById(courseId)).thenReturn(true);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // then
            // verify that calling deleteStudent with a student not in the course throws ApiBadRequestException
        assertThatThrownBy(() -> courseService.deleteStudent(courseId, studentId))
                .isInstanceOf(ApiBadRequestException.class)
                .hasMessageContaining("student with id " + studentId + " isn't present in course with id " + courseId);
    }

    @Test
    void willUpdateCourseWithNewCapacity() {
        // given
        Long oldCourseId = 1L;

        Course oldCourse = new Course(
                "nameOld",
                "descriptionOld",
                2
        );
        Course newCourse = new Course(
                "nameNew",
                "descriptionNew",
                5
        );

        when(courseRepository.existsById(oldCourseId)).thenReturn(true);
        when(courseRepository.findById(oldCourseId)).thenReturn(Optional.of(oldCourse));

        // when
        courseService.updateCourse(oldCourseId, newCourse);

        // then
        assertThat(oldCourse.getName()).isEqualTo(newCourse.getName());
        assertThat(oldCourse.getDescription()).isEqualTo(newCourse.getDescription());
        assertThat(oldCourse.getMaxCapacity()).isEqualTo(newCourse.getMaxCapacity());
    }

    @Test
    void willThrowApiBadRequestExceptionBecauseNewCapacityIsNegative() {
        // given
        Long oldCourseId = 1L;

        Course oldCourse = new Course(
                "nameOld",
                "descriptionOld",
                2
        );
        Course newCourse = new Course(
                "nameNew",
                "descriptionNew",
                -1
        );

        when(courseRepository.existsById(oldCourseId)).thenReturn(true);
        when(courseRepository.findById(oldCourseId)).thenReturn(Optional.of(oldCourse));

        // then
        assertThatThrownBy(() -> courseService.updateCourse(oldCourseId, newCourse))
                .isInstanceOf(ApiBadRequestException.class)
                .hasMessageContaining("max capacity cannot be negative");
    }

    @Test
    void willThrowApiBadRequestExceptionBecauseNewMaxCapacityIsLessThanOccupied() {
        // given
        Long oldCourseId = 1L;

        Course oldCourse = new Course(
                "nameOld",
                "descriptionOld",
                2
        );

        oldCourse.getStudents().add(new Student("Pepa", "Novák", "4.A"));
        assertThat(oldCourse.getOccupied()).isEqualTo(1);
        Course newCourse = new Course(
                "nameNew",
                "descriptionNew",
                0
        );

        when(courseRepository.existsById(oldCourseId)).thenReturn(true);
        when(courseRepository.findById(oldCourseId)).thenReturn(Optional.of(oldCourse));

        // then
        assertThatThrownBy(() -> courseService.updateCourse(oldCourseId, newCourse))
                .isInstanceOf(ApiBadRequestException.class)
                .hasMessageContaining("course with id " + oldCourseId + " is occupied by " + oldCourse.getOccupied() + " students, co capacity " + newCourse.getMaxCapacity() + " cannot be accepted");
    }

    @Test
    void willEnrollNewStudent() {
        // given
        Long courseId = 1L;
        Long studentId = 2L;

        Course course = new Course(
                "nameOld",
                "descriptionOld",
                1
        );

        assertThat(course.getOccupied()).isEqualTo(0);

        Student student = new Student(
                "John",
                "Norris",
                "7.A"
        );


        when(courseRepository.existsById(courseId)).thenReturn(true);
        when(studentRepository.existsById(studentId)).thenReturn(true);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // when
        courseService.enrollStudent(courseId, studentId);

        // then
        assertThat(course.getOccupied()).isEqualTo(1);
        Student studentOfCourse = course.getStudents().iterator().next(); // to get first student
        assertThat(studentOfCourse.getFirstName()).isEqualTo(student.getFirstName());
        assertThat(studentOfCourse.getLastName()).isEqualTo(student.getLastName());
        assertThat(studentOfCourse.getClassName()).isEqualTo(student.getClassName());
    }

    @Test
    void willThrowApiBadRequestExceptionBecauseMaxCapacityWouldBeLessThanOccupied() {
        // given
        Long courseId = 1L;
        Long studentId = 2L;

        Course course = new Course(
                "nameOld",
                "descriptionOld",
                1
        );
        course.getStudents().add(new Student("Pepa", "Novák", "4.A"));
        assertThat(course.getOccupied()).isEqualTo(1);

        Student student = new Student(
                "John",
                "Norris",
                "7.A"
        );


        when(courseRepository.existsById(courseId)).thenReturn(true);
        when(studentRepository.existsById(studentId)).thenReturn(true);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        // then
        assertThatThrownBy(() -> courseService.enrollStudent(courseId, studentId))
                .isInstanceOf(ApiBadRequestException.class)
                .hasMessageContaining("course with id " + courseId + " doesn't have any space left, max capacity: " + course.getMaxCapacity());
    }
}