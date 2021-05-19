package se.lexicon.course_manager_assignment.data.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.model.Course;


import java.time.LocalDate;
import java.util.List;

@Service
public class CourseManager implements CourseService {

    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final Converters converters;

    @Autowired
    public CourseManager(CourseDao courseDao, StudentDao studentDao, Converters converters) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.converters = converters;
    }

    @Override
    public CourseView create(CreateCourseForm form) {

        Course course = courseDao.createCourse(form.getCourseName(), form.getStartDate(), form.getWeekDuration());

        return new CourseView(course.getId(), form.getCourseName(), form.getStartDate(), form.getWeekDuration(), null);
    }

    @Override
    public CourseView update(UpdateCourseForm form) {

        courseDao.findById(form.getId()).setCourseName(form.getCourseName());
        courseDao.findById(form.getId()).setStartDate(form.getStartDate());
        courseDao.findById(form.getId()).setWeekDuration(form.getWeekDuration());

        return new CourseView(form.getId(),form.getCourseName(),form.getStartDate(), form.getWeekDuration(),converters.studentsToStudentViews(courseDao.findById(form.getId()).getStudents())) ;

    }

    @Override
    public List<CourseView> searchByCourseName(String courseName) {

        return converters.coursesToCourseViews(courseDao.findByNameContains(courseName));

    }

    @Override
    public List<CourseView> searchByDateBefore(LocalDate end) {

        return converters.coursesToCourseViews(courseDao.findByDateBefore(end));

    }

    @Override
    public List<CourseView> searchByDateAfter(LocalDate start) {

        return converters.coursesToCourseViews(courseDao.findByDateAfter(start));

    }

    @Override
    public boolean addStudentToCourse(int courseId, int studentId) {

        return courseDao.findById(courseId).enrollStudent(studentDao.findById(studentId));

    }

    @Override
    public boolean removeStudentFromCourse(int courseId, int studentId) {

        return courseDao.findById(courseId).unrollStudent(studentDao.findById(studentId));
    }

    @Override
    public CourseView findById(int id) {

        Course course = courseDao.findById(id);

        return new CourseView(course.getId(), course.getCourseName(), course.getStartDate(), course.getWeekDuration(), converters.studentsToStudentViews(course.getStudents()));
    }

    @Override
    public List<CourseView> findAll() {

        return converters.coursesToCourseViews(courseDao.findAll());
    }

    @Override
    public List<CourseView> findByStudentId(int studentId) {

        return converters.coursesToCourseViews(courseDao.findByStudentId(studentId));
    }

    @Override
    public boolean deleteCourse(int id) {

        return courseDao.removeCourse(courseDao.findById(id));

    }
}
