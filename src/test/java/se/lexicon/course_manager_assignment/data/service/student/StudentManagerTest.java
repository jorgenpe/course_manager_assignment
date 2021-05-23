package se.lexicon.course_manager_assignment.data.service.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.dao.CourseCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.StudentCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.data.service.converter.ModelToDto;
import se.lexicon.course_manager_assignment.dto.forms.CreateStudentForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager_assignment.dto.views.StudentView;


import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {StudentManager.class, CourseCollectionRepository.class, StudentCollectionRepository.class, ModelToDto.class})
public class StudentManagerTest {

    @Autowired
    private StudentService testObject;
    @Autowired
    private StudentDao studentDao;

    @BeforeEach
    void setup(){
        StudentSequencer.setStudentSequencer(0);
        studentDao.clear();

    }

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
        assertNotNull(studentDao);
    }

    @Test
    void Create(){

        CreateStudentForm form  = new CreateStudentForm();
        form.setName("Anna Olson");
        form.setEmail("f@hotmail.com");
        form.setAddress("Regeringsgatan 4");
        StudentView result = testObject.create(form);
        StudentView expected = new StudentView(1, form.getName(), form.getEmail(), form.getAddress());

        assertEquals(expected,result);
    }

    @Test
    void update(){
        CreateStudentForm form  = new CreateStudentForm();
        form.setName("Anna Olson");
        form.setEmail("f@hotmail.com");
        form.setAddress("Regeringsgatan 4");
        testObject.create(form);
        UpdateStudentForm updateForm = new UpdateStudentForm();

        updateForm.setId(1);
        updateForm.setName("Anna Nilson");
        updateForm.setEmail("f@hotmail.com");
        updateForm.setAddress("Regeringsgatan 4");

        StudentView result = testObject.update(updateForm);
        StudentView expected = new StudentView(updateForm.getId(), updateForm.getName(), updateForm.getEmail(), updateForm.getAddress());

        assertEquals(expected, result);

    }

    @Test
    void findById(){
        CreateStudentForm form  = new CreateStudentForm();
        form.setName("Anna Olson");
        form.setEmail("f@hotmail.com");
        form.setAddress("Regeringsgatan 4");
        testObject.create(form);

        form.setName("Anders Olson");
        form.setEmail("d@hotmail.com");
        form.setAddress("Regeringsgatan 4");
        testObject.create(form);

        StudentView result = testObject.findById(2);
        StudentView expected = new StudentView(2, "Anders Olson", "d@hotmail.com", "Regeringsgatan 4");

        assertEquals(expected,result);

    }

    @Test
    void searchByEmail(){
        CreateStudentForm form  = new CreateStudentForm();
        form.setName("Anna Olson");
        form.setEmail("f@hotmail.com");
        form.setAddress("Regeringsgatan 4");
        testObject.create(form);

        form.setName("Anders Olson");
        form.setEmail("d@hotmail.com");
        form.setAddress("Regeringsgatan 4");
        testObject.create(form);

        StudentView result = testObject.searchByEmail("d@hotmail.com");
        StudentView expected = new StudentView(2, "Anders Olson", "d@hotmail.com", "Regeringsgatan 4");

        assertEquals(expected,result);

    }

    @Test
    void searchByName(){
        CreateStudentForm form  = new CreateStudentForm();
        form.setName("Anders Olson");
        form.setEmail("f@hotmail.com");
        form.setAddress("Regeringsgatan 4");
        testObject.create(form);

        form.setName("Anders Olson");
        form.setEmail("d@hotmail.com");
        form.setAddress("Regeringsgatan 4");
        testObject.create(form);

        List<StudentView> result = testObject.searchByName("Anders Olson");
        int  expected = 2;

        assertEquals(expected,result.size() );

    }

    @Test
    void findAll(){
        CreateStudentForm form  = new CreateStudentForm();
        form.setName("Anna Olson");
        form.setEmail("f@hotmail.com");
        form.setAddress("Regeringsgatan 4");
        testObject.create(form);

        form.setName("Anders Olson");
        form.setEmail("d@hotmail.com");
        form.setAddress("Regeringsgatan 4");
        testObject.create(form);

        List<StudentView>result = testObject.findAll();
        int expected = 2;

        assertEquals(expected,result.size());

    }

    @Test
    void deleteStudent(){

        CreateStudentForm form  = new CreateStudentForm();
        form.setName("Anna Olson");
        form.setEmail("f@hotmail.com");
        form.setAddress("Regeringsgatan 4");
        testObject.create(form);

        form.setName("Anders Olson");
        form.setEmail("d@hotmail.com");
        form.setAddress("Regeringsgatan 4");
        testObject.create(form);

        StudentView testStudent = testObject.searchByEmail("d@hotmail.com");

        boolean result = testObject.deleteStudent(2);


        assertTrue(result);

    }


    @AfterEach
    void tearDown() {
        StudentSequencer.setStudentSequencer(0);
        studentDao.clear();
    }
}
