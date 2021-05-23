package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


public class CourseCollectionRepository implements CourseDao{

    private Collection<Course> courses;


    public CourseCollectionRepository(Collection<Course> courses) {
        this.courses = courses;
    }

    @Override
    public Course createCourse(String courseName, LocalDate startDate, int weekDuration) {

        int tempSequencer = CourseSequencer.nextCourseId();

        Course temp = new Course(tempSequencer, courseName , startDate, weekDuration);

        if(!courses.contains(temp)){

            courses.add(temp);
        }

         return temp;
    }

    @Override
    public Course findById(int id) {


        for(Course m : courses){

            if(m.getId() == id){

                return m;

            }
        }

        return null;
    }

    @Override
    public Collection<Course> findByNameContains(String name) {

        Collection<Course> temp = new ArrayList<>();

        for(Course m : courses){

            if(m.getCourseName().equalsIgnoreCase(name)){

                 temp.add(m);

            }
        }
        return temp;
    }

    @Override
    public Collection<Course> findByDateBefore(LocalDate end) {

        Collection<Course> temp = new ArrayList<>();


        for(Course m : courses){


            if(m.getStartDate().equals(end.minusWeeks(m.getWeekDuration()))){

                temp.add(m);

            }
        }

        return temp;
    }

    @Override
    public Collection<Course> findByDateAfter(LocalDate start) {

        Collection<Course> temp = new ArrayList<>();

        for(Course m : courses){

            if(m.getStartDate().equals(start)){

                        temp.add(m);

            }
        }

        return temp;
    }

    @Override
    public Collection<Course> findAll() {

        return courses;

    }

    @Override
    public Collection<Course> findByStudentId(int studentId) {

        Collection<Course> temp = new ArrayList<>();

        for(Course m : courses){

            for(Student n : m.getStudents())

                if(n.getId() == (studentId)){

                    temp.add(m);

                }
        }

        return temp;

    }

    @Override
    public boolean removeCourse(Course course) {


        return courses.remove(course);

    }

    @Override
    public void clear() {
        this.courses = new HashSet<>();
    }
}
