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

        int tempSequencer = StudentSequencer.nextStudentId();
        Student temp = new Student(tempSequencer, name, email, address);
        if(!students.contains(temp)){

            students.add(temp);

        }


        return temp;
    }

    @Override
    public Student findByEmailIgnoreCase(String email) {



        for(Student m :students){

            if(m.getEmail().equalsIgnoreCase(email)){

                return m;

            }

        }

        return null;
    }

    @Override
    public Collection<Student> findByNameContains(String name) {

        Collection<Student> temp = new ArrayList<>();

        for(Student m : students){

            if(m.getName().equals(name)){

                temp.add(m);

            }

        }

        return temp;
    }

    @Override
    public Student findById(int id) {


        for(Student m :students){

            if(m.getId() == id){

                return m;

            }

        }

        return null;

    }

    @Override
    public Collection<Student> findAll() {

        return students;

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
