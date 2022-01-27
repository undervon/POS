package com.soap.student;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student getStudentById(long id) {
        log.info("[{}] -> getStudentById", this.getClass().getSimpleName());

        return studentRepository.getStudentById(id);
    }

    public List<Student> getAllStudents() {
        log.info("[{}] -> getAllStudents", this.getClass().getSimpleName());

        return studentRepository.findAll();
    }

    public boolean addNewStudent(Student student) {
        log.info("[{}] -> addNewStudent", this.getClass().getSimpleName());

        if (studentRepository.findByNameAndAge(student.getName(), student.getAge()).isEmpty()) {
            studentRepository.save(student);
            return true;
        }

        return false;
    }

    public void updateStudent(Student student) {
        log.info("[{}] -> updateStudent", this.getClass().getSimpleName());

        studentRepository.save(student);
    }

    public void deleteStudentById(Student student) {
        log.info("[{}] -> deleteStudentById", this.getClass().getSimpleName());

        studentRepository.delete(student);
    }
}
