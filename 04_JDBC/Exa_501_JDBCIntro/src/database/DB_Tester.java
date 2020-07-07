/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import beans.Student;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.sql.ResultSet;
import java.util.List;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Elias Wilfinger
 */
public class DB_Tester {
    private Connection connection;
    
    public DB_Tester() throws ClassNotFoundException {
        // load postgres database driver (optional)
        Class.forName("org.postgresql.Driver");
    }
    
    /**
     * Connect to postgres database dbName
     * @param dbName 
     */
    public void connect(String dbName) throws SQLException{
        String url = "jdbc:postgresql://localhost:5432/" + dbName; // port is optional
        String username = "postgres";
        String password = "postgres";
        connection = DriverManager.getConnection(url, username, password);
    }
    
    public void disconnect() throws SQLException{
        if(connection != null){
            connection.close();
        }
    }
    
    public void createDatabase(String databasename) throws SQLException{
        String sqlString = "CREATE DATABASE " + databasename.toLowerCase();
        
        // create statment from connection
        Statement statement = connection.createStatement();
        statement.execute(sqlString);
        statement.close();   
    }
    
    public void createTable() throws SQLException{
        String sqlString = "CREATE TABLE students (\n" +
                "    student_id serial PRIMARY KEY,\n" +
                "    cat_nr INTEGER NOT NULL,\n" +
                "    firstname VARCHAR(100) NOT NULL,\n" +
                "    lastname VARCHAR(100) NOT NULL,\n" +
                "    birthdate DATE NOT NULL\n" +
                ")";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sqlString);
        statement.close();
    }
    
    public void insertStudent(Student student) throws SQLException{
        Statement statement = connection.createStatement();
        java.sql.Date sqlDate = java.sql.Date.valueOf(student.getDateOfBirth());
        String sqlString = "INSERT INTO students (cat_nr, firstname, lastname, birthdate)\n" 
                + "VALUES(" + student.getCatalognr() 
                + ", '"+ student.getFirstname() + "', '" + student.getLastname() + "'"
                + ", '" + sqlDate.toString() + "');";
        
        statement.executeUpdate(sqlString);
        statement.close();
        
    }
    
    public List<Student> getTableContent() throws SQLException{
        List<Student> students = new ArrayList<>();
        Statement statement = connection.createStatement();
        String sqlQuery = "SELECT * FROM students";
        
        ResultSet rs = statement.executeQuery(sqlQuery);
        while(rs.next()){
            int id = rs.getInt(1); // Spaltenindex (erste Spalte = 1 (und nicht 0)) !!!!
            int catnr = rs.getInt("cat_nr");
            String firstname = rs.getString("firstname");
            String lastname = rs.getString("lastname");
            Date birthdate = rs.getDate("birthdate");
            
            Student student = new Student(id, catnr, firstname, lastname, birthdate.toLocalDate());
            students.add(student);
        }
        
        statement.close();
        
        return students;
    }
    
    
    
    
    
    public static void main(String[] args) {
        try {
            DB_Tester dbt = new DB_Tester();
            dbt.connect("postgres");
            try{
                dbt.createDatabase("studentdb");
                
            }catch(SQLException ex){
                System.out.println("db already exists");
            }
            
            dbt.disconnect();
            dbt.connect("studentdb");
            
            try{
                dbt.createTable();
                
            }catch(SQLException ex){
                System.out.println("table already exists");
            }
            
//            dbt.insertStudent(new Student(0, 1, "Leon", "Anghel", LocalDate.of(2002, Month.OCTOBER, 24)));
//            dbt.insertStudent(new Student(0, 2, "Nico", "Baumann", LocalDate.of(2002, Month.JULY, 31)));
//            dbt.insertStudent(new Student(0, 2, "Adrian", "Berner", LocalDate.of(2003, Month.JUNE, 12)));

            dbt.getTableContent().stream().forEach(System.out::println);
            
            
            dbt.disconnect();
            
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
        }
      
        
    }

}
