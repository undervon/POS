package com.soap.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student getStudentById(Long id);

    List<Student> findByNameAndAge(String name, Integer age);
}
