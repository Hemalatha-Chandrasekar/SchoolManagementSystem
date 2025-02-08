package sba.sms.dao;


import sba.sms.models.Course;

import java.util.List;

public interface CourseI {
    Course getCourseById(Integer id);
    void create(Course course);
    List<Course> getAllCourses();
}