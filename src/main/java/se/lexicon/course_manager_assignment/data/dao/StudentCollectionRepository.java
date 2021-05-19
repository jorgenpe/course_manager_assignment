package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


public class StudentCollectionRepository implements StudentDao {

    private Collection<Student> students;

    public StudentCollectionRepository(Collection<Student> students) {
        this.students = students;
    }

    @Override
    public Student createStudent(String name, String email, String address) {


        return new Student(StudentSequencer.nextStudentId(), name, email, address);
    }

    @Override
    public Student findByEmailIgnoreCase(String email) {



        while(students.iterator().hasNext()){

            if(students.iterator().next().getEmail().equalsIgnoreCase(email)){

                return students.iterator().next();

            }

        }

        return null;
    }

    @Override
    public Collection<Student> findByNameContains(String name) {

        Collection<Student> temp = new ArrayList<>();

        while(students.iterator().hasNext()){

            if(students.iterator().next().getName().equals(name)){

                temp.add(students.iterator().next());

            }

        }

        return temp;
    }

    @Override
    public Student findById(int id) {


        while(students.iterator().hasNext()){

            if(students.iterator().next().getId() == id){

                return students.iterator().next();

            }

        }

        return null;

    }

    @Override
    public Collection<Student> findAll() {

        return new ArrayList<>(students);

    }

    @Override
    public boolean removeStudent(Student student) {


        return students.remove(student);
    }

    @Override
    public void clear() {
        this.students = new HashSet<>();
    }
}
