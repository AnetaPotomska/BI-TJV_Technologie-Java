package cz.cvut.fit.tjv.server.repository;

import cz.cvut.fit.tjv.server.domain.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * It will use h2 in-memory database, different from main database.
 */
@DataJpaTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    // after every test, database will be clean
    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void itShouldFindStudentByFirstNameAndLastName() {
        // given
        String firstName = "Monika";
        String lastName = "Smetana";
        Student student = new Student(
                firstName,
                lastName,
                "4.A"
        );
        studentRepository.save(student);


        // when
        List<Student> students = studentRepository.findStudentByFirstNameAndLastName(firstName, lastName);

        // then
        assertThat(students.size()).isEqualTo(1);
    }

    @Test
    void itShouldNotFindAnyStudentByFirstNameAndLastName() {
        // given
        String firstName = "Monika";
        String lastName = "Smetana";

        // when
        List<Student> students = studentRepository.findStudentByFirstNameAndLastName(firstName, lastName);

        // then
        assertThat(students.size()).isEqualTo(0);
    }
}