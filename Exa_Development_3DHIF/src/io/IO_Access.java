package io;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IO_Access {
    private List<Student> students = new ArrayList<>();

    Path path = Paths.get(System.getProperty("user.dir"), "src", "res", "students.ser");

    public IO_Access() {
        students.add(new Student("Leon", "Anghel", LocalDate.now().minusYears(16)));
        students.add(new Student("Nico", "Baumann", LocalDate.now().minusYears(17)));
    }

    public void writeToFile() throws IOException {
        FileOutputStream fos = new FileOutputStream(path.toFile());
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(students);

        /*for (Student student : students) {
            oos.writeObject(student); // Serialisiert nur Instanzvariablen
        }*/
        oos.close();
    }

    public void readFile() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path.toFile());
        ObjectInputStream ois = new ObjectInputStream(fis);
        List<Student> studentsFromFile = (List<Student>)ois.readObject();
        /*try{
            while(true){
                Student student = (Student) ois.readObject();
                studentsFromFile.add(student);
            }
        }catch (EOFException e){
            System.out.println("Error: " + e.getMessage());
        }*/
        for (Student student : studentsFromFile) {
            System.out.println(student);
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello");
        IO_Access ioa = new IO_Access();
        try {
           // ioa.writeToFile();
            ioa.readFile();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
