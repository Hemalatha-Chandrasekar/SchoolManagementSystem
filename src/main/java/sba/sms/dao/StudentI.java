package sba.sms.dao;


import sba.sms.models.Student;

import java.util.List;

public interface StudentI {
    Student getStudentByEmail(String email);

    void create(Student student);

    List<Student> getAllStudents();

    void update(Student student);
}