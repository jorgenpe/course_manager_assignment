package se.lexicon.course_manager_assignment.data.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {StudentCollectionRepository.class})
public class StudentCollectionRepositoryTest {

    @Autowired
    private StudentDao testObject;

    @BeforeEach
    void setup(){

        StudentSequencer.setStudentSequencer(0);
        testObject.clear();

    }

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(testObject == null);
    }

    @Test
    void createStudent(){

        Student result = testObject.createStudent("Anna Olson","f@hotmail.com", "Regeringsgatan 5");
        Student expected = new Student(1, "Anna Olson","f@hotmail.com", "Regeringsgatan 5");

        assertEquals(expected,result);
    }

    @Test
    void findByEmailIgnoreCase(){

        Student student = testObject.createStudent("Anna Olson","f@hotmail.com", "Regeringsgatan 5");
        Student result = testObject.findByEmailIgnoreCase("f@hotmail.com");

        assertEquals(student,result);
    }

    @Test
    void findByNameContains(){

        testObject.createStudent("Anna Olson","f@hotmail.com", "Regeringsgatan 5");
        testObject.createStudent("Anders Olson","d@hotmail.com", "Regeringsgatan 5");
        testObject.createStudent("Anna Olson","k@hotmail.com", "Regeringsgatan 7");

        Collection<Student> result = testObject.findByNameContains("Anna Olson");
        int expected = 2;

        assertEquals(expected,result.size());

    }

    @Test
    void findById(){

        testObject.createStudent("Anna Olson","f@hotmail.com", "Regeringsgatan 5");
        testObject.createStudent("Anders Olson","d@hotmail.com", "Regeringsgatan 5");
        Student expected = testObject.createStudent("Anna Olson","k@hotmail.com", "Regeringsgatan 7");
        Student result = testObject.findById(3);

        assertEquals(expected,result);
    }

    @Test
    void findAll(){

        testObject.createStudent("Anna Olson","f@hotmail.com", "Regeringsgatan 5");
        testObject.createStudent("Anders Olson","d@hotmail.com", "Regeringsgatan 5");
        testObject.createStudent("Anna Olson","k@hotmail.com", "Regeringsgatan 7");

        Collection<Student> result = testObject.findAll();
        int expected = 3;

        assertEquals(expected,result.size());

    }

    @Test
    void removeStudent(){

        testObject.createStudent("Anna Olson","f@hotmail.com", "Regeringsgatan 5");
        Student student = testObject.createStudent("Anders Olson","d@hotmail.com", "Regeringsgatan 5");
        testObject.createStudent("Anna Olson","k@hotmail.com", "Regeringsgatan 7");

        boolean result = testObject.removeStudent(student);

        assertTrue(result);

    }

    @Test
    void removeStudent2(){

        testObject.createStudent("Anna Olson","f@hotmail.com", "Regeringsgatan 5");
        testObject.createStudent("Anders Olson","d@hotmail.com", "Regeringsgatan 5");
        testObject.createStudent("Anna Olson","k@hotmail.com", "Regeringsgatan 7");

        Student student = new Student(1, "Ann Olson","f@hotmail.com", "Regeringsgatan 5");

        boolean result = testObject.removeStudent(student);

        assertFalse(result);

    }


    @AfterEach
    void tearDown() {
        testObject.clear();
        StudentSequencer.setStudentSequencer(0);
    }
}
