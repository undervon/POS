package com.soap.student;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import student.soap.com.student.DeleteStudentRequest;
import student.soap.com.student.DeleteStudentResponse;
import student.soap.com.student.GetAllStudentsResponse;
import student.soap.com.student.GetStudentByIdRequest;
import student.soap.com.student.GetStudentByIdResponse;
import student.soap.com.student.PostStudentRequest;
import student.soap.com.student.PostStudentResponse;
import student.soap.com.student.PutStudentRequest;
import student.soap.com.student.PutStudentResponse;
import student.soap.com.student.ServiceStatus;
import student.soap.com.student.StudentInfo;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Endpoint
@RequiredArgsConstructor
public class StudentEndpoint {

    private static final String NAMESPACE_URI = "http://com.soap.student/student";

    private final StudentService studentService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStudentByIdRequest")
    @ResponsePayload
    public GetStudentByIdResponse getStudentById(@RequestPayload GetStudentByIdRequest getStudentByIdRequest) {
        log.info("[{}] -> getStudentById, id: {}", this.getClass().getSimpleName(), getStudentByIdRequest.getId());

        GetStudentByIdResponse getStudentByIdResponse = new GetStudentByIdResponse();
        StudentInfo studentInfo = new StudentInfo();

        Student student = studentService.getStudentById(getStudentByIdRequest.getId());

        BeanUtils.copyProperties(student, studentInfo);

        getStudentByIdResponse.setStudentInfo(studentInfo);

        return getStudentByIdResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllStudentsRequest")
    @ResponsePayload
    public GetAllStudentsResponse getAllStudents() {
        log.info("[{}] -> getAllStudents", this.getClass().getSimpleName());

        GetAllStudentsResponse getAllStudentsResponse = new GetAllStudentsResponse();
        List<StudentInfo> studentInfoList;

        List<Student> studentList = studentService.getAllStudents();

        studentInfoList = studentList.stream()
                .map(student -> {
                    StudentInfo studentInfo = new StudentInfo();
                    BeanUtils.copyProperties(student, studentInfo);
                    return studentInfo;
                })
                .collect(Collectors.toList());

        getAllStudentsResponse.getStudentInfo().addAll(studentInfoList);

        return getAllStudentsResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "postStudentRequest")
    @ResponsePayload
    public PostStudentResponse addNewStudent(@RequestPayload PostStudentRequest postStudentRequest) {
        log.info("[{}] -> addNewStudent", this.getClass().getSimpleName());

        PostStudentResponse postStudentResponse = new PostStudentResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        Student student = Student.builder()
                .name(postStudentRequest.getName())
                .age(postStudentRequest.getAge())
                .build();

        if (studentService.addNewStudent(student)) {
            StudentInfo studentInfo = new StudentInfo();
            BeanUtils.copyProperties(student, studentInfo);
            postStudentResponse.setStudentInfo(studentInfo);

            serviceStatus.setStatusCode(HttpStatus.CREATED.value());
            serviceStatus.setMessage(HttpStatus.CREATED.name());
        } else {
            serviceStatus.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            serviceStatus.setMessage(HttpStatus.NOT_ACCEPTABLE.name());
        }

        postStudentResponse.setServiceStatus(serviceStatus);

        return postStudentResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "putStudentRequest")
    @ResponsePayload
    public PutStudentResponse updateStudent(@RequestPayload PutStudentRequest putStudentRequest) {
        log.info("[{}] -> updateStudent", this.getClass().getSimpleName());

        PutStudentResponse putStudentResponse = new PutStudentResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        if (studentService.getStudentById(putStudentRequest.getStudentInfo().getId()) != null) {
            Student student = new Student();
            BeanUtils.copyProperties(putStudentRequest.getStudentInfo(), student);

            studentService.updateStudent(student);

            serviceStatus.setStatusCode(HttpStatus.NO_CONTENT.value());
            serviceStatus.setMessage(HttpStatus.NO_CONTENT.name());
        } else {
            serviceStatus.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            serviceStatus.setMessage(HttpStatus.NOT_ACCEPTABLE.name());
        }

        putStudentResponse.setServiceStatus(serviceStatus);

        return putStudentResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteStudentRequest")
    @ResponsePayload
    public DeleteStudentResponse deleteStudentById(@RequestPayload DeleteStudentRequest deleteStudentRequest) {
        log.info("[{}] -> deleteStudentById, id: {}", this.getClass().getSimpleName(), deleteStudentRequest.getId());

        DeleteStudentResponse deleteStudentResponse = new DeleteStudentResponse();
        ServiceStatus serviceStatus = new ServiceStatus();

        Student student = studentService.getStudentById(deleteStudentRequest.getId());

        if (student != null) {
            studentService.deleteStudentById(student);
            serviceStatus.setStatusCode(HttpStatus.NO_CONTENT.value());
            serviceStatus.setMessage(HttpStatus.NO_CONTENT.name());
        } else {
            serviceStatus.setStatusCode(HttpStatus.NOT_FOUND.value());
            serviceStatus.setMessage(HttpStatus.NOT_FOUND.name());
        }

        deleteStudentResponse.setServiceStatus(serviceStatus);

        return deleteStudentResponse;
    }
}