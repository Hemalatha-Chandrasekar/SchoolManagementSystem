package sba.sms.service;

import sba.sms.dao.StudentDAO;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;

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
            student.getCourses().add(course);
            studentDAO.update(student);
        }
    }
}