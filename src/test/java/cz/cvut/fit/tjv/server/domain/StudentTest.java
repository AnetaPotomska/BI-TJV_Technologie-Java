package cz.cvut.fit.tjv.server.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StudentTest {
    @Test
    void canAddNewStudentAndSetEmailCorrectly() {
        // given
        String firstName = "Monika";
        String lastName = "Smetana";
        String className = "4.A";
        Student student = new Student(
                firstName,
                lastName,
                className
        );
        assertThat(student.getEmail()).isEqualTo(firstName + "." + lastName + "." + className + "@skola.cz");
    }
}