package sba.sms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sba.sms.dao.StudentDAO;
import sba.sms.models.Student;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    // Imagine StudentDAO as a database helper
    @Mock
    private StudentDAO studentDAO;

    // This is the thing we're testing
    @InjectMocks
    private StudentService studentService;

    // This runs before EACH test
    @BeforeEach
    void setUp() {
        // Prepare our fake objects (mocks)
        MockitoAnnotations.openMocks(this);
    }

    // TEST 1:  Finding a student by their email (if they exist)
    @Test
    void getStudentByEmail_IfEmailExists_ReturnsTheStudent() {
        // 1. ARRANGE
        String email = "test@example.com";
        Student expectedStudent = new Student(email, "Test User", "password");
        // Tell our fake database helper what to do
        when(studentDAO.getStudentByEmail(email)).thenReturn(expectedStudent);
        // 2. ACT (Do the thing we're testing)
        Student actualStudent = studentService.getStudentByEmail(email);
        // 3. ASSERT (Check if it worked correctly)
        assertEquals(expectedStudent, actualStudent);
    }

    // TEST 2: Finding a student by email (if they DON'T exist)
    @Test
    void getStudentByEmail_IfEmailDoesNotExist_ReturnsNull() {
        // 1. ARRANGE
        String email = "nonexistent@example.com";
        when(studentDAO.getStudentByEmail(email)).thenReturn(null);
        // 2. ACT
        Student actualStudent = studentService.getStudentByEmail(email);
        // 3. ASSERT
        assertNull(actualStudent);
    }
    // TEST 3: Creating a student
    @Test
    void createStudent_ShouldCallCreateOnDAO() {
        // 1. ARRANGE
        Student studentToCreate = new Student("new@example.com", "New User", "password");

        // 2. ACT
        studentService.createStudent(studentToCreate);

        // 3. ASSERT
        verify(studentDAO, times(1)).create(studentToCreate);
    }
    // TEST 4:  Getting all students (if there ARE students)
    @Test
    void getAllStudents_IfStudentsExist_ReturnsListOfStudents() {
        // 1. ARRANGE
        List<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(new Student("student1@example.com", "Student One", "password"));
        expectedStudents.add(new Student("student2@example.com", "Student Two", "password"));

        // Tell our fake database helper what to return
        when(studentDAO.getAllStudents()).thenReturn(expectedStudents);
        // 2. ACT
        List<Student> actualStudents = studentService.getAllStudents();
        // 3. ASSERT
        assertEquals(expectedStudents, actualStudents);
    }

    // TEST 5: Getting all students (if there are NO students)
    @Test
    void getAllStudents_IfNoStudentsExist_ReturnsEmptyList() {
        // 1. ARRANGE
        when(studentDAO.getAllStudents()).thenReturn(new ArrayList<>());
        // 2. ACT
        List<Student> actualStudents = studentService.getAllStudents();
        // 3. ASSERT
        assertTrue(actualStudents.isEmpty()); // Check if the list is empty
    }
}