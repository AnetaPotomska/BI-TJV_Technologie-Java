package cz.cvut.fit.tjv.server.domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class StudyMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String authorName;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Course course;

    public StudyMaterial() {
    }

    public StudyMaterial(String name, String authorName) {
        this.name = name;
        this.authorName = authorName;
    }

    public void removeCourse() {
        course = null;
    }
}
