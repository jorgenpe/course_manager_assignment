package se.lexicon.course_manager_assignment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {

    private Student student2;
    private Student student;
    private Course course;
    @BeforeEach
    void setup(){

        StudentSequencer.setStudentSequencer(0);
        CourseSequencer.setCourseSequencer(0);

        course = new Course(CourseSequencer.nextCourseId(), "Java", LocalDate.of(2021,5,28), 5);
        student = new Student(StudentSequencer.nextStudentId(), "Kalle", "k@hotmail.com", "Horngatan 13");
        student2 = new Student(StudentSequencer.nextStudentId(),"Olle", "f@hotmail.com", "Regeringsgatan 12");
    }

    @Test
    void course(){

        Course testCourse = new Course(CourseSequencer.nextCourseId(), " Java next", LocalDate.of(2021,8,28), 6);
        Course expected = new Course(testCourse.getId(), testCourse.getCourseName(), testCourse.getStartDate(), testCourse.getWeekDuration());

        assertEquals(expected, testCourse);
    }

    @Test
    void setCourse(){

        course.setCourseName("Next Java");
        String result = course.getCourseName();

        assertEquals("Next Java", result);

    }

    @Test
    void setStartDate(){

        course.setStartDate(LocalDate.of(2021,7,25));
        LocalDate result = course.getStartDate();

        assertEquals(LocalDate.of(2021,7,25), result);

    }

    @Test
    void setWeekDuration(){

        course.setWeekDuration(6);
        int result = course.getWeekDuration();

        assertEquals(6, result);

    }

    @Test
    void enrollStudent(){

        boolean result = course.enrollStudent(student);

        assertTrue(result);
    }

    @Test
    void enrollStudent2(){

        course.enrollStudent(student);
        boolean result = course.enrollStudent(student);

        assertFalse(result);
    }


    @Test
    void unenrollStudent(){

        course.enrollStudent(student);
        course.enrollStudent(student2);
        boolean result = course.unenrollStudent(student);

        assertTrue(result);
    }

    @Test
    void unenrollStudent2(){

        boolean result = course.unenrollStudent(student);

        assertFalse(result);
    }

    @Test
    void equals(){

        assertTrue(course.equals(course));

    }

    @Test
    void equals2(){

        Course testCourse = new Course(CourseSequencer.nextCourseId(), " Java next", LocalDate.of(2021,8,28), 6);

        assertFalse(course.equals(testCourse));

    }



}
