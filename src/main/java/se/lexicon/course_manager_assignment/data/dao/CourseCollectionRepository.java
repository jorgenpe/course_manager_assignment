package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.model.Course;

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


         return new Course(CourseSequencer.nextCourseId(), courseName , startDate, weekDuration);
    }

    @Override
    public Course findById(int id) {

        while(courses.iterator().hasNext()){

            if(courses.iterator().next().getId() == id){

                return courses.iterator().next();

            }
        }

        return null;
    }

    @Override
    public Collection<Course> findByNameContains(String name) {

        Collection<Course> temp = new ArrayList<>();

        while(courses.iterator().hasNext()){

            if(courses.iterator().next().getCourseName().equals(name)){

                 temp.add(courses.iterator().next());

            }
        }
        return temp;
    }

    @Override
    public Collection<Course> findByDateBefore(LocalDate end) {

        Collection<Course> temp = new ArrayList<>();


        while(courses.iterator().hasNext()){


            if(courses.iterator().next().getStartDate().equals(end.minusWeeks(courses.iterator().next().getWeekDuration()))){

                temp.add(courses.iterator().next());

            }
        }

        return temp;
    }

    @Override
    public Collection<Course> findByDateAfter(LocalDate start) {

        Collection<Course> temp = new ArrayList<>();

        while(courses.iterator().hasNext()){

            if(courses.iterator().next().getStartDate().equals(start)){

                temp.add(courses.iterator().next());

            }
        }

        return temp;
    }

    @Override
    public Collection<Course> findAll() {

        return new ArrayList<>(courses);
    }

    @Override
    public Collection<Course> findByStudentId(int studentId) {

        Collection<Course> temp = new ArrayList<>();

        while(courses.iterator().hasNext()){

            while(courses.iterator().next().getStudents().iterator().hasNext())

                if(courses.iterator().next().getStudents().iterator().next().getId() == (studentId)){

                    temp.add(courses.iterator().next());

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
