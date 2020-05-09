/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Elias Wilfinger
 */
public class Grade {
    private int classID;
    private String className;
    private List<Student> students = new ArrayList<>();
    private int nextCatalogNumber = 1;
    public static int nextGradeID = 1;
    
    public static List<Grade> grades = new ArrayList<>();
    public static boolean updateGrades = false;
    
    /**
     * used for generating new grades coming from import/user input
     * @param className
     * @return 
     */
    public static Grade of(String className){
        Grade g = new Grade(className);
        if(!grades.contains(g)){
            grades.add(g);
            updateGrades = true;
        }else{
            nextGradeID--;
            return grades.get(grades.indexOf(g));
        }
        return g;
    }
    
    /**
     * used for generating grades coming from DB
     * @param className
     * @param classID 
     */
    public Grade(String className) {
        this.className = className;
    }
    public Grade(String className, int classID) {
        this.className = className;
        this.classID = classID;
    }

    
    /**
     * adds a student to a grade and updates (all) student's catalog numbers
     * @param student
     * @return status
     */
    public boolean addStudent(Student student){
        if(students.contains(student)){
            return false;
        }
        boolean status = students.add(student);
        
        students.sort(Comparator.comparing(Student::getSurname, String.CASE_INSENSITIVE_ORDER).thenComparing(Student::getFirstname, String.CASE_INSENSITIVE_ORDER));
        nextCatalogNumber = 1;
        for (Student student1 : students) {
            student1.setCatNo(nextCatalogNumber++);
        }
        
        return status;
    }
    
    public boolean addStudentWithoutSorting(Student student){
        if(students.contains(student)){
            return false;
        }
        boolean status = students.add(student);
        nextCatalogNumber = student.getCatNo() + 1;
        return status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.className);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Grade other = (Grade) obj;
        if (!Objects.equals(this.className, other.className)) {
            return false;
        }
        return true;
    }

    
    public static List<String> getAllAvailableClasses(){
        List<String> classes = new ArrayList<>();
        Grade.grades.forEach(g -> classes.add(g.getClassName()));
        return classes;
    }
    
    
    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "\nGrade{" + "classID=" + classID + ", className=" + className 
                + ", students=" + students + '}';
    }
    
    
    
    
    

    
    

    
    
    
}
