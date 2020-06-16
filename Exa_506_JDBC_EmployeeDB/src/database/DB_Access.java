/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import beans.DeptManager;
import beans.Employee;
import io.IO_Access;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elias Wilfinger
 */
public class DB_Access {
    private static DB_Access theInstance = null;
    private DB_Database db;
    private boolean isConnected = false;
    
    private static final String GENERAL_PATH = Paths.get(System.getProperty("user.dir"), "src", "res", "sql").toString();
    
    public static DB_Access getInstance() {
        if (theInstance == null) {
            theInstance = new DB_Access();
        }
        return theInstance;
    }
    
    private DB_Access() {
        try {
            db = new DB_Database();
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Database problem - possible cause: DB-Driver not found");
        } catch (SQLException ex) {
            throw new RuntimeException("Database problem - possible cause: " + ex.toString());
        }
    }
    

    /** 
     * disconnect DB
     * @throws SQLException 
     */
    public void disconnect() throws SQLException{
        db.disconnect();
        this.isConnected = false;
        System.out.println(">>> Disconnected booksdb.");
    }
    

    public List<String> getDepartments() throws FileNotFoundException, SQLException{
        Statement statement = db.getStatement();
        String statementString = IO_Access.getSQLStatementString(Paths.get(GENERAL_PATH, "getDepartments.sql").toFile());
        ResultSet rs = statement.executeQuery(statementString);
        
        List<String> departments = new ArrayList<>();
        while(rs.next()){
            departments.add(rs.getString("dept"));
        }
        
        db.releaseStatement(statement);
        System.out.println(">>> Returning " + departments.size() + " departments.");
        return departments;
    }
    
    public List<DeptManager> getManagersFromDepartment(String department) throws FileNotFoundException, SQLException{
        String sqlString = IO_Access.getSQLStatementString(Paths.get(GENERAL_PATH, "getManagerFromDepartment.sql").toFile());
        sqlString = sqlString.replaceAll("\\{dept_name\\}", department);
        
        Statement statement = db.getStatement();
        List<DeptManager> managers = new ArrayList<>();
        
        ResultSet rs = statement.executeQuery(sqlString);
        while(rs.next()){
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            LocalDate from_date = rs.getDate("from_date").toLocalDate();
            LocalDate to_date = rs.getDate("to_date").toLocalDate();
            DeptManager manager = new DeptManager(last_name, first_name, from_date, to_date);
            managers.add(manager);
        }
        db.releaseStatement(statement);
        System.out.println(">>> Returning " + managers.size() + " managers.");
        return managers;
    }

    public List<Employee> getEmployeesFromDepartment(String department, LocalDate birth_date_before, boolean male, boolean female) 
            throws FileNotFoundException, SQLException{
        
        String sqlString = IO_Access.getSQLStatementString(Paths.get(GENERAL_PATH, "getEmployeesFromDepartment.sql").toFile());
        sqlString = sqlString.replaceAll("\\{dept_name\\}", department)
                .replaceAll("\\{birth_date\\}", birth_date_before.format(DateTimeFormatter.ISO_DATE))
                .replaceAll("\\{gender_male\\}", Boolean.toString(male))
                .replaceAll("\\{gender_female\\}", Boolean.toString(female));
       
        Statement statement = db.getStatement();
        List<Employee> employees = new ArrayList<>();
        
        ResultSet rs = statement.executeQuery(sqlString);
        while(rs.next()){
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            LocalDate hire_date = rs.getDate("hire_date").toLocalDate();
            LocalDate birth_date = rs.getDate("birth_date").toLocalDate();
            char gender = rs.getString("gender").charAt(0);
            Employee employee = new Employee(last_name, first_name, gender, birth_date, hire_date);
            employees.add(employee);
        }
        
        db.releaseStatement(statement);
        System.out.println(">>> Returning " + employees.size() + " employees.");
        return employees;
    }
    
    public static void main(String[] args) {
        DB_Access dba = new DB_Access();
        try {
            System.out.println(dba.getDepartments().toString());
            System.out.println(dba.getManagersFromDepartment("Marketing").toString());
            dba.getEmployeesFromDepartment("Marketing", LocalDate.MIN, true, true);
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        
        try {
            dba.disconnect();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    
    
}
