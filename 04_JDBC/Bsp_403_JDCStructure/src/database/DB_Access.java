/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import beans.Student;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SF <htlkaindorf.at>
 */
public class DB_Access {

  /**
   * implement class as singleton
   */
  private static DB_Access theInstance = null;
  private DB_Database db;
  
  private final String insertStudentString = "INSERT INTO student (catalognr, firstname, lastname, dateofbirth) "
            + "VALUES ( ? , ? , ? , ?);";
  private PreparedStatement insertStudentpStat = null;
  
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
   * get all students of one class from database
   */
  public List<Student> getAllStudents() throws SQLException {
    String sqlString = "SELECT * FROM student;";
    Statement stat = db.getStatement();
    ResultSet rs = stat.executeQuery(sqlString);
    List<Student> studentList = new ArrayList<>();
    while (rs.next()) {
      int id = rs.getInt("student_id");
      int catNo = rs.getInt("catalognr");
      String firstname = rs.getString("firstname");
      String lastname = rs.getString("lastname");
      LocalDate dateOfBirth = rs.getDate("dateofbirth").toLocalDate();
      // ToDo: get Values from ResultSet
      studentList.add(new Student(id, catNo, firstname, lastname, dateOfBirth));
    }
    db.releaseStatement(stat);
    return studentList;
  }

  /**
   *
   * @param student
   * @return true if insert was successfull - otherwise false
   */
  public boolean insertStudent(Student student) throws SQLException {
//    String sqlString = "INSERT INTO student (catalognr, firstname, lastname, dateofbirth) "
//            + "VALUES ( ? , ? , ? , ?);";
//    PreparedStatement pStat = db.getConnection().prepareStatement(sqlString);

    if (insertStudentpStat == null) {
      insertStudentpStat = db.getConnection().prepareStatement(insertStudentString);
    }
    insertStudentpStat.setInt(1, student.getCatNo());
    insertStudentpStat.setString(2, student.getFirstname());
    insertStudentpStat.setString(3, student.getLastname());
    insertStudentpStat.setDate(4, Date.valueOf(student.getDateOfBirth()));
    int numDataSets = insertStudentpStat.executeUpdate();
    return numDataSets > 0;
  }

  public static void main(String[] args) {
    try {
      DB_Access dba = DB_Access.getInstance();
      dba.insertStudent(new Student(0, 16, "Dagobert", "Duck", LocalDate.now()));
//      for (Student student : dba.getAllStudents()) {
//        System.out.println(student);
//      }
    } catch (SQLException ex) {
      System.out.println(ex.toString());
    }
  }

}
