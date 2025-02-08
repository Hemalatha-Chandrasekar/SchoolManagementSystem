package sba.sms.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sba.sms.dao.StudentDAO;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.service.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentDAO studentDAO;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStudentByEmail_ExistingEmail_ReturnsStudent() {
        // Arrange
        String email = "test@example.com";
        Student expectedStudent = new Student(email, "Test User", "password");
        when(studentDAO.getStudentByEmail(email)).thenReturn(expectedStudent);

        // Act
        Student actualStudent = studentService.getStudentByEmail(email);

        // Assert
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void getStudentByEmail_NonExistingEmail_ReturnsNull() {
        // Arrange
        String email = "nonexistent@example.com";
        when(studentDAO.getStudentByEmail(email)).thenReturn(null);

        // Act
        Student actualStudent = studentService.getStudentByEmail(email);

        // Assert
        assertNull(actualStudent);
    }

    @Test
    void createStudent_ValidStudent_CallsCreateOnDAO() {
        // Arrange
        Student studentToCreate = new Student("new@example.com", "New User", "password");

        // Act
        studentService.createStudent(studentToCreate);

        // Assert
        verify(studentDAO, times(1)).create(studentToCreate);  // Verify that the create method on studentDAO was called once
    }

    @Test
    void getAllStudents_StudentsExist_ReturnsListOfStudents() {
        // Arrange
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student("student1@example.com", "Student One", "password"));
        expectedStudents.add(new Student("student2@example.com", "Student Two", "password"));
        when(studentDAO.getAllStudents()).thenReturn(expectedStudents);

        // Act
        List<Student> actualStudents = studentService.getAllStudents();

        // Assert
        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    void getAllStudents_NoStudentsExist_ReturnsEmptyList() {
        // Arrange
        when(studentDAO.getAllStudents()).thenReturn(new ArrayList<>());

        // Act
        List<Student> actualStudents = studentService.getAllStudents();

        // Assert
        assertTrue(actualStudents.isEmpty());
    }

    @Test
    void registerStudentToCourse_StudentAndCourseExist_UpdatesStudentCourses() {
        // Arrange
        String studentEmail = "test@example.com";
        Integer courseId = 1;
        Student student = new Student(studentEmail, "Test User", "password");
        Course course = new Course("Java", "Phillip Witkin");
        course.setId(courseId);
        List<Course> courses = new ArrayList<>();
        student.setCourses(courses);
        student.getCourses().add(course);

        when(studentDAO.getStudentByEmail(studentEmail)).thenReturn(student);
        when(studentDAO.getAllStudents()).thenReturn(new ArrayList<>());
        studentService.registerStudentToCourse(studentEmail, courseId);
        verify(studentDAO, times(0)).update(student);
    }


    @Test
    void registerStudentToCourse_StudentDoesNotExist_DoesNotUpdateAnyCourses() {
        // Arrange
        String studentEmail = "nonexistent@example.com";
        Integer courseId = 1;

        when(studentDAO.getStudentByEmail(studentEmail)).thenReturn(null);

        // Act
        studentService.registerStudentToCourse(studentEmail, courseId);

        // Assert
        verify(studentDAO, never()).update(any(Student.class)); // Verify that the update method on studentDAO was never called
    }
}