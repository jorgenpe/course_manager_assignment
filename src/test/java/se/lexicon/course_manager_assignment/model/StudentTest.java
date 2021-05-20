package se.lexicon.course_manager_assignment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTest {

    private Student student;

    @BeforeEach
    void setup(){
        StudentSequencer.setStudentSequencer(0);
        student = new Student(StudentSequencer.nextStudentId(), "Kalle", "k@hotmail.com", "Horngatan 13");

    }

    @Test
    void Students(){

        Student result =new Student(StudentSequencer.nextStudentId(),"Olle", "f@hotmail.com", "Regeringsgatan 12");
        Student expected = new Student(result.getId(), result.getName(), result.getEmail(), result.getAddress());

        assertEquals(expected, result);

    }

    @Test
    void setName(){
        student.setName("Anna");
        String result = student.getName();

        assertEquals("Anna", result);

    }

    @Test
    void setEmail(){

        student.setEmail("q@hotmail.com");
        String result = student.getEmail();

        assertEquals("q@hotmail.com", result);
    }

    @Test
    void setAddress(){

        student.setAddress("Karlshams vägen 17");
        String result = student.getAddress();

        assertEquals("Karlshams vägen 17", result);
    }


    @Test
    void equals(){

        Student temp =new Student(StudentSequencer.nextStudentId(),"Olle", "f@hotmail.com", "Regeringsgatan 12");
        boolean result = student.equals(temp);

        assertEquals(false, result);
    }

    @Test
    void equals2(){

        Student temp =new Student(StudentSequencer.nextStudentId(),"Olle", "f@hotmail.com", "Regeringsgatan 12");
        boolean result = student.equals(student);

        assertEquals(true, result);
    }

}
