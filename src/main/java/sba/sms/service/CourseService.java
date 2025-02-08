package com.sba.sms.service;

import com.sba.sms.dao.CourseDAO;
import com.sba.sms.dao.CourseI;
import Course;

import java.util.List;

public class CourseService {
    private CourseI courseDAO = new CourseDAO();

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