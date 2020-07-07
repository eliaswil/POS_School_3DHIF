package at.htlkaindorf.exa_development.Collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListCompTest {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Marge", "Simpsons"));
        students.add(new Student("Bart", "Simpsons"));
        students.add(new Student("Donald", "Duck"));
        students.add(new Student("Dagobert", "Duck"));


        /*
          Comparing with lambda and Method-Reference
         */
        students.sort((o1, o2) -> o1.getFirstname().compareTo(o2.getFirstname()));
        students.sort(Comparator.comparing(Student::getLastname).thenComparing(Student::getFirstname).reversed());

       /* students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getLastname().compareTo(o2.getLastname());
            }
        });*/

        for (Student student : students) {
            System.out.println(student);
        }

    }
}
