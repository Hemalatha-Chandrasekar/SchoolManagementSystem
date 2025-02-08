package com.sba.sms;

import com.sba.sms.util.CommandLine;
import Course;
import Student;
import com.sba.sms.service.CourseService;
import com.sba.sms.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) {
        CommandLine.addData(); // Add initial data

        Scanner scanner = new Scanner(System.in);
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();

        while (true) {
            System.out.println("Select # from menu:");
            System.out.println("1. Student");
            System.out.println("2. Quit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 2) {
                System.out.println("session ended!");
                break;
            }

            if (choice == 1) {
                System.out.print("Enter student email: ");
                String email = scanner.nextLine();

                System.out.print("Enter " + email + "'s password: ");
                String password = scanner.nextLine();

                Student student = studentService.getStudentByEmail(email);

                if (student != null && student.getPassword().equals(password)) {
                    System.out.println(email + " courses:");
                    System.out.println("--------------------------------");
                    System.out.printf("%-5s| %-20s| %-20s\n", "ID", "Course", "Instructor");
                    System.out.println("--------------------------------");

                    if (student.getCourses().isEmpty()) {
                        System.out.println("No courses to view:");
                    } else {
                        for (Course course : student.getCourses()) {
                            System.out.printf("%-5d| %-20s| %-20s\n", course.getId(), course.getName(), course.getInstructor());
                        }
                    }

                    while (true) {
                        System.out.println("select # from menu:");
                        System.out.println("1.Register " + student.getName() + " to class:");
                        System.out.println("2.Logout");

                        int studentMenuChoice = scanner.nextInt();
                        scanner.nextLine();  // Consume newline

                        if (studentMenuChoice == 2) {
                            break; // Logout
                        }

                        if (studentMenuChoice == 1) {
                            System.out.println("all courses:");
                            System.out.println("--------------------------------");
                            System.out.printf("%-5s| %-20s| %-20s\n", "ID", "Course", "Instructor");
                            System.out.println("--------------------------------");

                            List<Course> allCourses = courseService.getAllCourses();
                            for (Course course : allCourses) {
                                System.out.printf("%-5d| %-20s| %-20s\n", course.getId(), course.getName(), course.getInstructor());
                            }

                            System.out.print("select course #: ");
                            int courseId = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            courseService.registerStudentToCourse(email, courseId);
                            System.out.println("successfully register " + student.getName() + " to " + courseService.getCourseById(courseId).getName());

                            System.out.println(email + " courses:");
                            System.out.println("--------------------------------");
                            System.out.printf("%-5s| %-20s| %-20s\n", "ID", "Course", "Instructor");
                            System.out.println("--------------------------------");
                            student = studentService.getStudentByEmail(email); // Refresh student data
                            for (Course course : student.getCourses()) {
                                System.out.printf("%-5d| %-20s| %-20s\n", course.getId(), course.getName(), course.getInstructor());
                            }
                        }
                    }
                } else {
                    System.out.println("Invalid email or password.");
                }
            }
        }
    }
}