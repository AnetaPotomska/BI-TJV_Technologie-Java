package cz.cvut.fit.tjv.server.service;

import cz.cvut.fit.tjv.server.domain.Student;
import cz.cvut.fit.tjv.server.exception.ApiNotFoundException;
import cz.cvut.fit.tjv.server.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // -----------------------------------------------------------------------------------

    // GET
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    // GET
    public Student getStudent(Long id) {
        if(!studentRepository.existsById(id)) {
            throw new ApiNotFoundException("student with id " + id + " does not exists");
        }
        return studentRepository.findById(id).get();
    }

    // POST
    public Student addNewStudent(Student student) {
        student.setEmail(student.determineEmail());
        return studentRepository.save(student);
    }

    // DELETE
    public void deleteStudent(Long id) {
        if(!studentRepository.existsById(id)) {
            throw new ApiNotFoundException("student with id " + id + " does not exists");
        }
        studentRepository.deleteById(id);
    }

    // PUT
    @Transactional // save all at once in the end
    public Student updateStudent(Long id, Student student) {
        if(!studentRepository.existsById(id)) {
            throw new ApiNotFoundException("student with id " + id + " does not exists");
        }
        Student existingStudent = studentRepository.findById(id).get();
        if(student.getFirstName() != existingStudent.getFirstName() || student.getLastName() != existingStudent.getLastName()) {
            existingStudent.setEmail(student.determineEmail());
        }
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setClassName(student.getClassName());
        return studentRepository.save(existingStudent);
    }
}
