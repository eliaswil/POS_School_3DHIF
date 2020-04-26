/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.Employee;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Elias Wilfinger
 */
public class Loader {
    public static String loadStatementFromFile(String filename) throws FileNotFoundException{
        Path path = Paths.get(System.getProperty("user.dir"), "src", "res", filename);
        return new BufferedReader(new FileReader(path.toFile())).lines()
                .map(String::new).collect(Collectors.joining("\n"));
    }
    
    public static List<Employee> loadEmployees() throws FileNotFoundException{
        Path path = Paths.get(System.getProperty("user.dir"), "src", "res", "employees.csv"); 
        return new BufferedReader(new FileReader(path.toFile())).lines().skip(1)
                .map(Employee::new).collect(Collectors.toList());
    }
    
    public static String loadMessage(String filename) throws FileNotFoundException{
        Path path = Paths.get(System.getProperty("user.dir"), "src", "res", filename);
        return new BufferedReader(new FileReader(path.toFile())).lines()
                .map(String::new).collect(Collectors.joining("\n"));
    }
    
}
