package cz.cvut.fit.tjv.server.domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int maxCapacity;

    @ManyToMany
    @JoinTable(
            name = "course_attend",
            joinColumns = @JoinColumn(name = "course_id"), // owner
            inverseJoinColumns = @JoinColumn(name = "student_id"))

    /**
     * These two guys are here because some related issues with recursion in JSON.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    /**
     * Absolutely necessary JsonIgnore, because it will exclude mentions about students in JSON.
     * Without it there are issues related to JsonIdentityInfo.
     * If I understand it correctly JsonIdentityInfo is responsible for anti-recursion problems.
     * When object is firstly mentioned in JSON it will write him in full scale.
     * But second time JsonIdentityInfo will use only it's ID.
     * And this led to viewing issues in frontend. Frontend just didn't recieve full objects sometimes
     * (when this object where somewhere in JSON before).
     */
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    // Same as above, this JsonIgnore is very important
    @JsonIgnore
    Set<StudyMaterial> studyMaterials = new HashSet<>();

    public Course() {
    }

    public Course(String name, String description, Integer maxCapacity) {
        this.name = name;
        this.description = description;
        this.maxCapacity = maxCapacity;
    }

    public int getOccupied() {
        return students.size();
    }
}
