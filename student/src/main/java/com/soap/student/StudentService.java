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
        log.info("getStudentById");

        return studentRepository.getStudentById(id);
    }

    public List<Student> getAllStudents() {
        log.info("getAllStudents");

        return studentRepository.findAll();
    }

    public boolean addNewStudent(Student student) {
        log.info("addNewStudent");

        if (studentRepository.findByNameAndAge(student.getName(), student.getAge()).isEmpty()) {
            studentRepository.save(student);
            return true;
        }
        return false;
    }

    public void updateStudent(Student student) {
        log.info("updateStudent");

        studentRepository.save(student);
    }

    public void deleteStudentById(Student student) {
        log.info("deleteStudentById");

        studentRepository.delete(student);
    }
}
