package cz.cvut.fit.tjv.server.repository;

import cz.cvut.fit.tjv.server.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * I feel like JpaRepository is overkill for any of my repositories, but more is better than less, right?
 * NOTE: JpaRepository > PagingAndSortingRepository > CrudRepository
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE s.firstName = :firstName AND s.lastName = :lastName")
    List<Student> findStudentByFirstNameAndLastName(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName
    );
    // same thing, but I wanted to write it myself, so I can test it
    // List<Student> findStudentByFirstNameAndLastName(String firstName, String lastName);
}
