package cz.cvut.fit.tjv.server.service;

import cz.cvut.fit.tjv.server.domain.Student;
import cz.cvut.fit.tjv.server.exception.ApiNotFoundException;
import cz.cvut.fit.tjv.server.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

// This will get mock to work
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    /**
     * I am going to use mock, because we are focusing on testing service not repository
     */
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;

    @BeforeEach
        // for every time clean database
    void setUp() {
        studentService = new StudentService(studentRepository);
    }

    @Test
    void canGetStudents() {
        // when
        studentService.getStudents();

        // then
        verify(studentRepository).findAll(); // test that this (findAll) method was used in tested method
    }

    @Test
    void willSaveStudent() {
        // given
        String firstName = "Monika";
        String lastName = "Smetana";
        String className = "4.A";
        Student student = new Student(
                firstName,
                lastName,
                className
        );

        // configure mock repository to return the same student when saved
        when(studentRepository.save(student)).thenReturn(student);

        // when
        Student savedStudent = studentService.addNewStudent(student);

        // then
            // verify that the save method was called with the expected student
        verify(studentRepository).save(student);

            // verify that the returned student matches the one we provided
        assertThat(firstName).isEqualTo(savedStudent.getFirstName());
        assertThat(lastName).isEqualTo(savedStudent.getLastName());
        assertThat(className).isEqualTo(savedStudent.getClassName());
    }

    @Test
    void willThrowWhenStudentWithIdDoesntExist() {
        // given
        Long id = 1L;
        given(studentRepository.existsById(id)).willReturn(false);

        // then
        assertThatThrownBy(() -> studentService.deleteStudent(id))
                .isInstanceOf(ApiNotFoundException.class)
                .hasMessageContaining("student with id " + id + " does not exists");

        // assures that "studentRepository.deleteById(id);" code will never be called
        verify(studentRepository, never()).deleteById(any());
    }

    @Test
    void willDeleteExistingStudent() {
        // given
        Long studentId = 1L;

        // configure mock repository to return true for existsById
        when(studentRepository.existsById(studentId)).thenReturn(true);

        // when
        studentService.deleteStudent(studentId);

        // then
            // verify that the deleteById method was called with the expected studentId
        verify(studentRepository).deleteById(studentId);
    }
}