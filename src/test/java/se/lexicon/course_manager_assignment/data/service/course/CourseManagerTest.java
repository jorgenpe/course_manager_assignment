package se.lexicon.course_manager_assignment.data.service.course;

import ch.qos.logback.core.pattern.Converter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.dao.CourseCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.data.service.converter.ModelToDto;
import se.lexicon.course_manager_assignment.data.service.student.StudentManager;
import se.lexicon.course_manager_assignment.data.service.student.StudentService;
import se.lexicon.course_manager_assignment.dto.forms.CreateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.CreateStudentForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Student;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CourseManager.class, CourseCollectionRepository.class, ModelToDto.class, StudentCollectionRepository.class})
public class CourseManagerTest {

    @Autowired
    private CourseService testObject;

    @Autowired
    private CourseDao courseDao;
    @BeforeEach
    void setup(){

        CourseSequencer.setCourseSequencer(0);
        StudentSequencer.setStudentSequencer(0);

        CreateCourseForm form = new CreateCourseForm();
        courseDao.clear();




    }

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
        assertNotNull(courseDao);
    }

    @Test
    void create(){


        CreateCourseForm form = new CreateCourseForm();

        form.setCourseName("Java");
        form.setStartDate(LocalDate.of(2021,6,6));
        form.setWeekDuration(4);

        testObject.create(form);
        int expected = 1;

        assertEquals(expected,testObject.findAll().size());

    }

    @Test
    void update(){

        CreateCourseForm form = new CreateCourseForm();

        form.setCourseName("Java");
        form.setStartDate(LocalDate.of(2021,6,6));
        form.setWeekDuration(4);

        testObject.create(form);

        UpdateCourseForm updateForm = new UpdateCourseForm();

        updateForm.setId(1);
        updateForm.setCourseName("Java1");
        updateForm.setStartDate(LocalDate.of(2021, 6, 6));
        updateForm.setWeekDuration(5);

        CourseView result = testObject.update(updateForm);
        CourseView expected = testObject.findById(1);

        assertEquals(expected,result);

    }

    @Test
    void searchByCourseName(){

        CreateCourseForm form = new CreateCourseForm();

        form.setCourseName("Java");
        form.setStartDate(LocalDate.of(2021,6,6));
        form.setWeekDuration(4);

        testObject.create(form);

        form.setCourseName("Java");
        form.setStartDate(LocalDate.of(2021,7,6));
        form.setWeekDuration(4);

        testObject.create(form);


        List<CourseView> result = testObject.searchByCourseName("Java");
        int expected = 2;

        assertEquals(expected, result.size());

    }

    @Test
    void searchByDateBefore(){

        CreateCourseForm form = new CreateCourseForm();

        form.setCourseName("Java");
        form.setStartDate(LocalDate.of(2021,6,6));
        form.setWeekDuration(4);

        testObject.create(form);

        form.setCourseName("Java");
        form.setStartDate(LocalDate.of(2021,7,6));
        form.setWeekDuration(1);

        testObject.create(form);

        List<CourseView> result = testObject.searchByDateBefore(LocalDate.of(2021,7,13));

        assertTrue(result.contains(testObject.findById(2)));

    }

    @Test
    void searchByDateAfter(){

        CreateCourseForm form = new CreateCourseForm();

        form.setCourseName("Java");
        form.setStartDate(LocalDate.of(2021,6,6));
        form.setWeekDuration(4);

        testObject.create(form);

        form.setCourseName("Java");
        form.setStartDate(LocalDate.of(2021,7,6));
        form.setWeekDuration(1);

        testObject.create(form);

        List<CourseView> result = testObject.searchByDateBefore(LocalDate.of(2021,6,6));

        assertFalse(result.contains(testObject.findById(2)));

    }

    @Test
    void addStudentToCourse(){

        Converters convert = new ModelToDto();
        StudentDao studentDao = new StudentCollectionRepository(new HashSet<>());
        studentDao.createStudent("Anna", "f@hotmail.com", "Regeringsgatan 5");
        courseDao.clear();
        testObject = new CourseManager(courseDao,studentDao,convert);

        CreateCourseForm form = new CreateCourseForm();

        form.setCourseName("Java");
        form.setStartDate(LocalDate.of(2021,6,6));
        form.setWeekDuration(4);

        testObject.create(form);

        boolean result = testObject.addStudentToCourse(1,1);

        assertTrue(result);

    }

    @Test
    void removeStudentFromCourse(){

        Converters convert = new ModelToDto();
        StudentDao studentDao = new StudentCollectionRepository(new HashSet<>());
        studentDao.createStudent("Anna", "f@hotmail.com", "Regeringsgatan 5");
        courseDao.clear();
        testObject = new CourseManager(courseDao,studentDao,convert);


        CreateCourseForm form = new CreateCourseForm();
        form.setCourseName("Java");
        form.setStartDate(LocalDate.of(2021,6,6));
        form.setWeekDuration(4);


        testObject.create(form);

        testObject.addStudentToCourse(1,1);

        boolean result = testObject.removeStudentFromCourse(1,1);

        assertTrue(result);

    }

    @Test
    void findById(){

        CreateCourseForm form = new CreateCourseForm();
        form.setCourseName("Java");
        form.setStartDate(LocalDate.of(2021,6,6));
        form.setWeekDuration(4);

        testObject.create(form);

        CourseView result = testObject.findById(1);

        assertEquals("Java", result.getCourseName());

    }

    @Test
    void findAll(){

        CreateCourseForm form = new CreateCourseForm();

        form.setCourseName("Java");
        form.setStartDate(LocalDate.of(2021,6,6));
        form.setWeekDuration(4);

        testObject.create(form);

        form.setCourseName("Java");
        form.setStartDate(LocalDate.of(2021,7,6));
        form.setWeekDuration(1);

        testObject.create(form);

        List<CourseView> result = testObject.findAll();
        int expected = 2;

        assertEquals(expected, result.size());

    }

    @Test
    void findByStudentId(){

        Converters convert = new ModelToDto();
        StudentDao studentDao = new StudentCollectionRepository(new HashSet<>());
        studentDao.createStudent("Anna", "f@hotmail.com", "Regeringsgatan 5");
        courseDao.clear();
        testObject = new CourseManager(courseDao,studentDao,convert);

        CreateCourseForm form = new CreateCourseForm();


        form.setCourseName("Java");
        form.setStartDate(LocalDate.of(2021,6,6));
        form.setWeekDuration(4);

        testObject.create(form);
        form.setCourseName("Java");
        form.setStartDate(LocalDate.of(2021,7,6));
        form.setWeekDuration(1);

        testObject.create(form);
        testObject.addStudentToCourse(1,1);

        List<CourseView> result = testObject.findByStudentId(1);
        int expected = 1;

        assertEquals(expected, result.size());

    }

    @Test
    void deleteCourse(){

        CreateCourseForm form = new CreateCourseForm();


        form.setCourseName("Java");
        form.setStartDate(LocalDate.of(2021,6,6));
        form.setWeekDuration(4);

        testObject.create(form);

        boolean result = testObject.deleteCourse(2);

        assertFalse(result);

    }



    @AfterEach
    void tearDown() {

        courseDao.clear();
        CourseSequencer.setCourseSequencer(0);
    }
}
