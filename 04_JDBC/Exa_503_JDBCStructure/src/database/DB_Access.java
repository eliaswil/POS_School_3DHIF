/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import beans.Student;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.util.List;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Elias Wilfinger
 */
public class DB_Access {
    
    /**
     * implement class as singleton
     */
    private static DB_Access theInstance = null;
    private DB_Database db;
    
    public static DB_Access getInstance(){
        if(theInstance == null){
            theInstance = new DB_Access();
        }
        return theInstance;
    }
    
    private DB_Access() {
        try {
            db = new DB_Database();
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("database problem - possbile cause: dirver not found");
        } 
        
    }
    
    public List<Student> getAllStudents() throws SQLException{
        String sqlString = String.format("SELECT * FROM students;");
        Statement stat = db.getStatement();
        ResultSet rs = stat.executeQuery(sqlString);
        List<Student> studentList = new ArrayList<>();
        while(rs.next()){
            int id = rs.getInt("student_id");
            int catNo = rs.getInt("catalognr");
            String firstname = rs.getString("firstname");
            String lastname = rs.getString("lastname");
            LocalDate dateOfBirth = rs.getDate("dateofbirth").toLocalDate();
            studentList.add(new Student(id, catNo, firstname, lastname, dateOfBirth));
        }
        db.releaseStatement(stat);
        return studentList;
    }
    
    public boolean insertStudent(Student student){
        // todo
        return true;
    }
    
    public static void main(String[] args) {
        try {
            DB_Access dba = DB_Access.getInstance();
            
            for (Student student : dba.getAllStudents()) {
                System.out.println(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB_Access.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
