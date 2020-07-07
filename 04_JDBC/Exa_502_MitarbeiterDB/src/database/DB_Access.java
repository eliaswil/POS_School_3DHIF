/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import beans.Employee;
import bl.Loader;
import java.io.FileNotFoundException;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private Connection connection;
    private String url = "jdbc:postgresql://localhost:5432/";
    private String databaseName = "mitarbeiterdb";
    private String tableName = "mitarbeiter";

    public DB_Access() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
    }
    
    public void connect() throws SQLException{
        String username = "postgres";
        String password = "postgres";
        connection = DriverManager.getConnection(url + databaseName, username, password);
    }
    
    
    
    public void dsconnect() throws SQLException{
        if(connection != null){
            connection.close();
        }
    }
    
    public boolean createDB(){
        try{
            connection = DriverManager.getConnection(url + "postgres", "postgres", "postgres");
            Statement statement = connection.createStatement();
            
            String sqlString = "CREATE DATABASE " + databaseName;
            statement.execute(sqlString);
            statement.close();
            this.dsconnect();
            
            return true;
        } catch (SQLException ex) {
            return false;
        }
        
        
    }
    
    public boolean dropDB(){
        try {
            connection = DriverManager.getConnection(url + "postgres", "postgres", "postgres");
            String sqlString = "DROP DATABASE mitarbeiterdb;";
            Statement statement = connection.createStatement();
            statement.execute(sqlString);
            statement.close();
            this.dsconnect();
        } catch (SQLException ex) {
            return false;
        }
        
        return true;
    }
    public boolean dropTable(){
        try (Statement statement = connection.createStatement()) {
            connection = DriverManager.getConnection(url + "postgres", "postgres", "postgres");
            String sqlString = "DROP TABLE mitarbeiter;";
            statement.execute(sqlString);
        } catch (SQLException ex) {
            return false;
        }
        
        return true;
    }
    
    public boolean createTable(){
        try {
            String sqlString = Loader.loadStatementFromFile("createTable.sql");
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlString);
            statement.close();
            
        } catch (FileNotFoundException | SQLException ex) {
            return false;
        }
        return true;
    }
    
    public List<Employee> getEmployeesFromDepartment(int department){
        List<Employee> employees = new ArrayList<>();
        try {
            String sqlQuery = Loader.loadStatementFromFile("getEmployeesFromDepartment.sql")
                    .replaceFirst("\\{department\\}", Integer.toString(department));
            Statement s = connection.createStatement();
            
            ResultSet rs = s.executeQuery(sqlQuery);
            while(rs.next()){
                int pers_nr = rs.getInt("pers_nr");
                String name = rs.getString("name");
                String vorname = rs.getString("vorname");
                LocalDate geb_datum = rs.getDate("geb_datum").toLocalDate();
                int gehalt = rs.getInt("gehalt");
                int abt_nr = rs.getInt("abt_nr");
                char geschlecht = rs.getString("geschlecht").charAt(0);
                employees.add(new Employee(pers_nr, name, vorname, geb_datum, geschlecht, gehalt, abt_nr));
                
            }
            
            s.close();
            
        } catch (FileNotFoundException | SQLException ex) {
            return null;
        }
        return employees;
    }
    
    public Employee getEmployee(int pers_nr){
        try {
            String sqlQuery = String.format(Loader.loadStatementFromFile("getEmployee.sql"), pers_nr);
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(sqlQuery);
            if(rs.next()){
                pers_nr = rs.getInt("pers_nr");
                String name = rs.getString("name");
                String vorname = rs.getString("vorname");
                LocalDate geb_datum = rs.getDate("geb_datum").toLocalDate();
                int gehalt = rs.getInt("gehalt");
                int abt_nr = rs.getInt("abt_nr");
                char geschlecht = rs.getString("geschlecht").charAt(0);
                return new Employee(pers_nr, name, vorname, geb_datum, geschlecht, gehalt, abt_nr);
            }
        } catch (FileNotFoundException | SQLException ex) {
            Logger.getLogger(DB_Access.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public double getAverageSalery(char gender){
        try {
            String sqlQuery = Loader.loadStatementFromFile("salary.sql")
                    .replaceFirst("\\{geschlecht\\}", Character.toString(gender));
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(sqlQuery);
            if(rs.next()){
                return rs.getDouble("avg");
            }
            
        } catch (FileNotFoundException | SQLException ex) {
            Logger.getLogger(DB_Access.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public boolean insertEmployee(Employee employee){
        try {
            int nextPersNr = getPersNr() + 1;
            String sqlString = Loader.loadStatementFromFile("insertEmployee.sql");
            sqlString = String.format(sqlString, nextPersNr, employee.getName(), 
                    employee.getVorname(), employee.getGet_datum().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    Double.toString(employee.getGehalt()), employee.getAbt_nr(), employee.getGeschlecht());
            
            Statement s = connection.createStatement();
            s.executeUpdate(sqlString);
            s.close();
           
        } catch (SQLException | FileNotFoundException ex) {
            return false;
        }
        return true;
    }
    public int insertEmployees(){
        try {
            int counter = 0;
            List<Employee> employees = Loader.loadEmployees();
            for(Employee e : employees){
                if(this.insertEmployee(e)){
                    counter++;
                }
            }
            return counter;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DB_Access.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    private int getPersNr() throws SQLException{
        String sqlQuery = "SELECT MAX(pers_nr) AS \"max\"\n" +
                            "FROM mitarbeiter;";
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(sqlQuery);
        return rs.next() ? rs.getInt("max") : -1;
    }
    
    public boolean removeEmployee(Employee employee){
        try {
            boolean didEmpActuallyExist = getEmployee(employee.getPers_nr()) != null;
            String sqlString = String.format("DELETE FROM %s WHERE pers_nr = %d;", tableName, employee.getPers_nr());
            Statement s = connection.createStatement();
            s.executeUpdate(sqlString);
            s.close();
            System.out.println(didEmpActuallyExist);
            return didEmpActuallyExist;
        } catch (SQLException ex) {
            return false;
        }
    }
}
