/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.classroster.dao;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author nicoleozkan
 */
@SpringBootTest
public class StudentDaoDBTest 
{
    @Autowired
    TeacherDAO teacherDao;
    
    @Autowired
    StudentDAO studentDao;
    
    @Autowired
    CourseDAO courseDao;
    
    public StudentDaoDBTest() 
    {
    }
    
    @BeforeAll
    public static void setUpClass() 
    {
    }
    
    @AfterAll
    public static void tearDownClass() 
    {
    }
    
    @BeforeEach
    public void setUp() 
    {
        List<Teacher> teachers = teacherDao.getAllTeachers();
        for(Teacher teacher : teachers)
        {
            teacherDao.deleteTeacherById(teacher.getId());
        }
        
        List<Student> students = studentDao.getAllStudents();
        for(Student student : students)
        {
            studentDao.deleteStudentById(student.getId());
        }
        
        List<Course> courses = courseDao.getAllCourses();
        for(Course course : courses)
        {
            courseDao.deleteCourseById(course.getId());
        }
    }
    
    @AfterEach
    public void tearDown() 
    {
    }

    /**
     * Test of getStudentById method, of class StudentDaoDB.
     */
    @Test
    public void testAddAndGetStudent() 
    {
        Student student = new Student();
        student.setFirstName("Test Student First");
        student.setLastName("Test Student Last");
        student = studentDao.addStudent(student);
        
        Student fromDao = studentDao.getStudentById(student.getId());
        assertEquals(student, fromDao);
    }

    /**
     * Test of getAllStudents method, of class StudentDaoDB.
     */
    @Test
    public void testGetAllStudents() 
    {
        Student student = new Student();
        student.setFirstName("Test Student First");
        student.setLastName("Test Student Last");
        student = studentDao.addStudent(student);
        
        Student student2 = new Student();
        student2.setFirstName("Test Student First 2");
        student2.setLastName("Test Student Last 2");
        student2 = studentDao.addStudent(student2);
        
        List<Student> students = studentDao.getAllStudents();
        
        assertEquals(2, students.size());
        assertTrue(students.contains(student));
        assertTrue(students.contains(student2));
    }

    /**
     * Test of updateStudent method, of class StudentDaoDB.
     */
    @Test
    public void testUpdateStudent() 
    {
        Student student = new Student();
        student.setFirstName("Test Student First");
        student.setLastName("Test Student Last");
        student = studentDao.addStudent(student);
        
        Student fromDao = studentDao.getStudentById(student.getId());
        assertEquals(student, fromDao);
        
        student.setFirstName("New Test Student First");
        studentDao.updateStudent(student);
        
        assertNotEquals(student, fromDao);
        
        fromDao = studentDao.getStudentById(student.getId());
        
        assertEquals(student, fromDao);
    }

    /**
     * Test of deleteStudentById method, of class StudentDaoDB.
     */
    @Test
    public void testDeleteStudentById() 
    {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Test First");
        teacher.setLastName("Test Last");
        teacher.setSpecialty("Test Specialty");
        teacher = teacherDao.addTeacher(teacher);
        
        Student student = new Student();
        student.setFirstName("Test Student First");
        student.setLastName("Test Student Last");
        student = studentDao.addStudent(student);
        List<Student> students = new ArrayList<>();
        students.add(student);
        
        Course course = new Course();
        course.setName("Test Course");
        course.setTeacher(teacher);
        course.setStudents(students);
        course = courseDao.addCourse(course);
        
        Student fromDao = studentDao.getStudentById(student.getId());
        assertEquals(student, fromDao);
        
        studentDao.deleteStudentById(student.getId());
        
        fromDao = studentDao.getStudentById(student.getId());
        assertNull(fromDao);
    } 
}