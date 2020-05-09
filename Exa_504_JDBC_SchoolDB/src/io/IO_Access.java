/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import beans.Grade;
import beans.Student;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Elias Wilfinger
 */
public class IO_Access {
    /**
     * loads students from FILE (csv)
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws IllegalArgumentException 
     */
    public static List<Grade> loadStudents(Path path) throws FileNotFoundException, IllegalArgumentException{
        if(!path.toFile().isFile()){
            throw new FileNotFoundException("Not a file");
        }
        List<Student> students = new BufferedReader(new FileReader(path.toFile()))
                .lines().skip(1)
                .map(Student::of).collect(Collectors.toList());
        
        return Grade.grades;
    }
    
    /** 
     * exports students to file (csv)
     * @param path = output filename
     * @param grades
     * @return
     * @throws IOException 
     */
    public static boolean exportStudents(Path path, List<Grade> grades) throws IOException{
        String content = "Klasse;Familienname;Vorname;Geschlecht;Geburtsdatum\n";
        for (Grade grade : grades) {
            for (Student student : grade.getStudents()) {
                content += String.format("%s;%s;%s;%s;%s\n", grade.getClassName(), 
                        student.getSurname(), student.getFirstname(), 
                        Character.toString(student.getGender()),
                        student.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(path.toFile()));
        bw.write(content);
        bw.close();
        System.out.println(">>> Exported students.");
        return true;
    }
}
