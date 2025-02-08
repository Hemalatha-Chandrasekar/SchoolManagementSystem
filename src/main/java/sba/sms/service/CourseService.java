package sba.sms.service;

import sba.sms.dao.CoureseDAO;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;


import java.util.List;

public class CourseService {
    private CourseI courseDAO = new CoureseDAO();

    public Course getCourseById(Integer id) {
        return courseDAO.getCourseById(id);
    }

    public void createCourse(Course course) {
        courseDAO.create(course);
    }

    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }
}