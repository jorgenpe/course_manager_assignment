package se.lexicon.course_manager_assignment.data.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.data.service.converter.ModelToDto;
import se.lexicon.course_manager_assignment.dto.forms.CreateStudentForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        StudentView temp = new StudentView(student.getId(), form.getName(), form.getEmail(),form.getAddress());

        return temp;


    }

    @Override
    public StudentView update(UpdateStudentForm form) {

        studentDao.findById(form.getId()).setName(form.getName());
        studentDao.findById(form.getId()).setEmail(form.getName());
        studentDao.findById(form.getId()).setAddress(form.getName());

        Student student = studentDao.findById(form.getId());
        if(student == null){
            return null;
        }
        student.setName(form.getName());
        student.setEmail(form.getEmail());
        student.setAddress(form.getAddress());



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
        boolean boolStudent;

        Student student = studentDao.findById(id);
        courseDao.findByStudentId(id).remove(student);

        boolStudent = studentDao.removeStudent(student);
        if(boolStudent){
            return true;
        }

        return false;
    }
}
