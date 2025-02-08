package com.sba.sms.service;

import com.sba.sms.dao.StudentDAO;
import com.sba.sms.dao.StudentI;
import com.sba.sms.util.HibernateUtil;
import Course;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StudentService {
    private StudentI studentDAO = new StudentDAO();


    public Student getStudentByEmail(String email) {
        return studentDAO.getStudentByEmail(email);
    }

    public void createStudent(Student student) {
        studentDAO.create(student);
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }


    public void registerStudentToCourse(String studentEmail, Integer courseId) {
        Student student = studentDAO.getStudentByEmail(studentEmail);
        CourseService courseService = new CourseService();
        Course course = courseService.getCourseById(courseId);

        if (student != null && course != null) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();
                student = (Student) session.merge(student); // Re-attach to session
                course = (Course) session.merge(course);     // Re-attach to session
                student.getCourses().add(course);
                session.update(student);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public <__TMP__> __TMP__ createStudent() {
    }

    public <__TMP__> __TMP__ createStudent() {
    }