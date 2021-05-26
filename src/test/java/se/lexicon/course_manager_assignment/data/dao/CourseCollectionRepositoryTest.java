package se.lexicon.course_manager_assignment.data.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CourseCollectionRepository.class})
public class CourseCollectionRepositoryTest {

    @Autowired
    private CourseDao testObject;

    @BeforeEach
    void setup(){

        CourseSequencer.setCourseSequencer(0);
        StudentSequencer.setStudentSequencer(0);
        testObject.clear();

    }

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(testObject == null);
    }

    @Test
    void createCourse(){

        Course expected = new Course(1, "Java", LocalDate.of(2021,6,6), 4);
        Course result = testObject.createCourse("Java", LocalDate.of(2021,6,6), 4);

        assertEquals(expected,result);
    }

    @Test
    void findById(){
        testObject.createCourse("Java", LocalDate.of(2021,6,6), 4);
        Course expected = testObject.createCourse("Java next", LocalDate.of(2021,7,6), 4);
        testObject.createCourse("Java for beginner", LocalDate.of(2021,6,6), 4);
        Course result = testObject.findById(2);

        assertEquals(expected,result);

    }


    @Test
    void findByNameContains(){

        testObject.createCourse("Java", LocalDate.of(2021,6,6), 4);
        testObject.createCourse("Java", LocalDate.of(2021,7,6), 4);
        testObject.createCourse("Java for beginner", LocalDate.of(2021,6,6), 4);

        Collection<Course> result = testObject.findByNameContains("Java");
        int expected = 2;

        assertEquals(expected,result.size());

    }

    @Test
    void findByDateBefore(){

        testObject.createCourse("Java", LocalDate.of(2021,6,6), 1);
        testObject.createCourse("Java", LocalDate.of(2021,7,6), 4);
        testObject.createCourse("Java for beginner", LocalDate.of(2021,6,6), 1);

        Collection<Course> result = testObject.findByDateBefore(LocalDate.of(2021,6,13));
        int expected = 2;

        assertEquals(expected,result.size());

    }

    @Test
    void findByDateAfter(){

        testObject.createCourse("Java", LocalDate.of(2021,6,6), 1);
        testObject.createCourse("Java", LocalDate.of(2021,6,6), 4);
        testObject.createCourse("Java for beginner", LocalDate.of(2021,6,6), 1);

        Collection<Course> result = testObject.findByDateAfter(LocalDate.of(2021,6,6));
        int expected = 3;

        assertEquals(expected,result.size());
    }

    @Test
    void findAll(){

        testObject.createCourse("Java", LocalDate.of(2021,6,6), 1);
        testObject.createCourse("Java", LocalDate.of(2021,6,6), 4);
        testObject.createCourse("Java for beginner", LocalDate.of(2021,6,6), 1);

        Collection<Course> result = testObject.findAll();
        int expected = 3;

        assertEquals(expected,result.size());
    }

    @Test
    void findByStudentId(){

        testObject.createCourse("Java", LocalDate.of(2021,6,6), 1);
        testObject.createCourse("Java", LocalDate.of(2021,6,6), 4);
        testObject.createCourse("Java for beginner", LocalDate.of(2021,6,6), 1);
        Student student= new Student(1, "Anna Olson","f@hotmail.com", "Regeringsgatan 5");
        testObject.findById(1).enrollStudent(student);
        Collection<Course> result = testObject.findByStudentId(1);
        int expected = 1;

        assertEquals(expected, result.size());

    }

    @Test
    void removeCourse(){

        testObject.createCourse("Java", LocalDate.of(2021,6,6), 1);
        testObject.createCourse("Java", LocalDate.of(2021,6,6), 4);
        testObject.createCourse("Java for beginner", LocalDate.of(2021,6,6), 1);

        boolean result = testObject.removeCourse(testObject.findById(2));

        assertTrue(result);
    }

    @Test
    void removeCourse2(){

        testObject.createCourse("Java", LocalDate.of(2021,6,6), 1);
        testObject.createCourse("Java", LocalDate.of(2021,6,6), 4);
        testObject.createCourse("Java for beginner", LocalDate.of(2021,6,6), 1);
        Course course = new Course(1,"Java for beginner", LocalDate.of(2021,6,6), 1);
        boolean result = testObject.removeCourse(course);

        assertFalse(result);
    }


    @AfterEach
    void tearDown() {
        testObject.clear();
        CourseSequencer.setCourseSequencer(0);
    }
}
