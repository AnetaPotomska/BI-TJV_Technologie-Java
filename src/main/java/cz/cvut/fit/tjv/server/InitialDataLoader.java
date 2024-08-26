package cz.cvut.fit.tjv.server;

import cz.cvut.fit.tjv.server.domain.Course;
import cz.cvut.fit.tjv.server.domain.Student;
import cz.cvut.fit.tjv.server.domain.StudyMaterial;
import cz.cvut.fit.tjv.server.repository.CourseRepository;
import cz.cvut.fit.tjv.server.repository.StudentRepository;
import cz.cvut.fit.tjv.server.repository.StudyMaterialRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * This class will be always run in start of app.
 * It is because of this class implements CommandLineRunner and that this class overrides run method.
 * It could have been done better through controllers, but this will do its job so there are some data in start of frontend.
 */
@Component
public class InitialDataLoader implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudyMaterialRepository studyMaterialRepository;

    public InitialDataLoader(StudentRepository studentRepository, CourseRepository courseRepository,
                             StudyMaterialRepository studyMaterialRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studyMaterialRepository = studyMaterialRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // creation of students
        Student student1 = new Student("John", "Doe", "1.A");
        studentRepository.save(student1);

        Student student2 = new Student("Jane", "Smith", "2.B");
        studentRepository.save(student2);

        // creation of courses
        Course course1 = new Course("Programming 101", "Introduction to programming", 1);
        courseRepository.save(course1);

        Course course2 = new Course("Database Design", "Basics of database design",5);
        courseRepository.save(course2);

        // creation of study materials
        StudyMaterial material1 = new StudyMaterial("Introduction to Java", "Java basics");
        studyMaterialRepository.save(material1);

        StudyMaterial material2 = new StudyMaterial("Database Normalization", "Normalization concepts");
        studyMaterialRepository.save(material2);

        // enroll student to course
        course1.getStudents().add(student1);
        courseRepository.save(course1);
    }
}