package com.sba.sms.dao;

import Course;
import java.util.List;

public interface CourseI {
    Course getCourseById(Integer id);
    void create(Course course);
    List<Course> getAllCourses();
}