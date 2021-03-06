package se.lexicon.course_manager_assignment.data.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateStudentForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.*;


@Service
public class StudentManager implements StudentService {

    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final Converters converters;

    @Autowired
    public StudentManager(StudentDao studentDao, CourseDao courseDao, Converters converters) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.converters = converters;
    }

    @Override
    public StudentView create(CreateStudentForm form) {

        Student student = studentDao.createStudent(form.getName(), form.getEmail(),form.getAddress());

        return new StudentView(student.getId(), form.getName(), form.getEmail(),form.getAddress());


    }

    // In update we create a new ArrayList and copy in the collection studentDao. We do this to be able to update the collection.
    // When ArrayList is updated the old list is deleted and the ArrayList is transformed to a Collection again.
    @Override
    public StudentView update(UpdateStudentForm form) {

        List<Student> tempStudents = new ArrayList<>(studentDao.findAll());

        Student student = studentDao.findById(form.getId());


        for(Student m : tempStudents){

            if(m.equals(student)){

                m.setName(form.getName());
                m.setEmail(form.getEmail());
                m.setAddress(form.getAddress());
            }
        }

        studentDao.clear();

        studentDao.findAll().addAll(new HashSet<>(tempStudents));

        student = studentDao.findById(form.getId());


        return new StudentView(student.getId(), student.getName(), student.getEmail(), student.getAddress());


    }

    @Override
    public StudentView findById(int id) {

        Student student = studentDao.findById(id);
        if(student == null){
            return null;
        }
        return new StudentView(student.getId(), student.getName(), student.getEmail(), student.getAddress());
    }

    @Override
    public StudentView searchByEmail(String email) {


        Student student = studentDao.findByEmailIgnoreCase(email);
        if(student == null){
            return null;
        }

        return new StudentView(student.getId(), student.getName(), student.getEmail(), student.getAddress());
    }

    @Override
    public List<StudentView> searchByName(String name) {

        return converters.studentsToStudentViews(studentDao.findByNameContains(name));

    }

    @Override
    public List<StudentView> findAll() {

        return converters.studentsToStudentViews(studentDao.findAll());
    }

    @Override
    public boolean deleteStudent(int id) {


        Student student = studentDao.findById(id);

        if(!studentDao.findAll().isEmpty()) {
            for (Course m : courseDao.findAll()) {

                m.unenrollStudent(student);

            }
        }

        return studentDao.removeStudent(student);
    }
}
