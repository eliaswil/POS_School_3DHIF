/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import beans.Grade;
import beans.Student;
import io.IO_Access;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Elias Wilfinger
 */
public class DB_Access {
    private static DB_Access theInstance = null;
    private DB_Database db;
    private boolean isConnected = false;
    
    private final String insertStudentString = "INSERT INTO student (classid, catno, firstname, surname, gender, dateofbirth) "
                                                + "VALUES ( ? , ? , ? , ? , ? , ?);";
    private final String insertGradeString = "INSERT INTO grade (classname) "
                                                + "VALUES ( ? );";
    private final String updateStudentString =  "UPDATE student\n" +
                                                "SET catno = ? \n" +
                                                "WHERE firstname = ? AND surname = ? AND dateofbirth = ? ;";
    
    private PreparedStatement insertUpdatePreparedStatmentStudent = null; // table student
    private PreparedStatement insertPreparedStatmentGrade = null; // table grade
    
    
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
     * manually connect do DB
     * @throws SQLException 
     */
    public void connect() throws SQLException{
        db.connect();
        this.isConnected = true;
        System.out.println(">>> Connected to schooldb.");
    }
    /** 
     * disconnect DB
     * @throws SQLException 
     */
    public void disconnect() throws SQLException{
        db.disconnect();
        this.isConnected = false;
        System.out.println(">>> Disconnected schooldb.");
    }
    /**
     * delete whole DB content when importing students
     * @return status
     * @throws SQLException 
     */
    public boolean deleteDBContent() throws SQLException{
        String sqlString = "DELETE FROM student; DELETE FROM grade;";
        java.sql.Statement statement = db.getStatement();
        boolean status = statement.execute(sqlString);
        db.releaseStatement(statement);
        System.out.println(">>> Deleted DB content.");
        return status;
    }
    /**
     * import stutents from .csv
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws SQLException 
     */
    public boolean importStudents(Path path) throws FileNotFoundException, SQLException, IllegalArgumentException{
        Grade.grades = new ArrayList<>();
        List<Grade> grades = IO_Access.loadStudents(path);
        
        deleteDBContent();
        
        insertUpdatePreparedStatmentStudent = db.getConnection().prepareStatement(insertStudentString);
        insertPreparedStatmentGrade = db.getConnection().prepareStatement(insertGradeString);
        
        
        int numDataSets = 0;
        Grade.updateGrades = true;
        for (Grade grade : grades) {
            // if there are new grades --> insert them into 'grade'
            if(Grade.updateGrades){
                numDataSets += insertGradeIntoDB(grade);
            }
        }
        
        updateLocalGradeIDs();
        
        for (Grade grade : grades) {            
            for (Student student : grade.getStudents()) {
                numDataSets += insertStudentIntoDB(student); // insert students
            }
        }
        
        if(Grade.updateGrades){
            Grade.updateGrades = false;
        }
        
        System.out.println(">>> Inserted " + numDataSets + " datasets.");
        
        return numDataSets > 0;
    }
    
    /**
     * updated the local Grade IDs
     * @throws SQLException 
     */
    public void updateLocalGradeIDs() throws SQLException{
        Statement statement = db.getStatement();
        
        String sqlString = "SELECT classid, classname FROM grade;";
        ResultSet rs = statement.executeQuery(sqlString);
        Map<String, Integer> gradeMapper = new HashMap<>();
        while(rs.next()){
            int classid = rs.getInt("classid");
            String classname = rs.getString("classname");
            gradeMapper.put(classname, classid);
        }
        int counter = 0;
        for (Grade grade : Grade.grades) {
            grade.setClassID(gradeMapper.get(grade.getClassName()));
            counter++;
        }
        db.releaseStatement(statement);
        
        System.out.println(">>> updated " + counter + " classIDs.");
    }
    
    /**
     * insert one student into DB
     * @param student
     * @return
     * @throws SQLException 
     */
    private int insertStudentIntoDB(Student student) throws SQLException{
        int numDataSets;
        
        // (classid, catno, firstname, surname, gender, dateofbirth)
        String gender = student.getGender() == 0 ? "-" : Character.toString(student.getGender());
        insertUpdatePreparedStatmentStudent.setInt(1, student.getGrade().getClassID());
        insertUpdatePreparedStatmentStudent.setInt(2, student.getCatNo());
        insertUpdatePreparedStatmentStudent.setString(3, student.getFirstname());
        insertUpdatePreparedStatmentStudent.setString(4, student.getSurname());
        insertUpdatePreparedStatmentStudent.setString(5, gender);
        insertUpdatePreparedStatmentStudent.setDate(6, java.sql.Date.valueOf(student.getDateOfBirth()));
        numDataSets = insertUpdatePreparedStatmentStudent.executeUpdate();
        return numDataSets;
    }
    /**
     * update catalog numbers in table 'student'
     * @param student
     * @return
     * @throws SQLException 
     */
    private int updateStudentDB(Student student) throws SQLException{
        int numDataSets;
        //"SET catno = ? \n" + "WHERE firstname = ? AND surname = ? AND dateofbirth = ? ;";
        insertUpdatePreparedStatmentStudent.setInt(1, student.getCatNo());
        insertUpdatePreparedStatmentStudent.setString(2, student.getFirstname()); 
        insertUpdatePreparedStatmentStudent.setString(3, student.getSurname()); 
        insertUpdatePreparedStatmentStudent.setDate(4, java.sql.Date.valueOf(student.getDateOfBirth())); 
        numDataSets = insertUpdatePreparedStatmentStudent.executeUpdate();
        return numDataSets;
    }
    /**
     * insert a new grade into table 'grade'
     * @param grade
     * @return
     * @throws SQLException 
     */
    private int insertGradeIntoDB(Grade grade) throws SQLException{
        int numDataSets;
        
        //(classname)
        insertPreparedStatmentGrade.setString(1, grade.getClassName());
        numDataSets = insertPreparedStatmentGrade.executeUpdate();
        return numDataSets;
    }
    
    public boolean addNewGrade(String gradeName) throws SQLException{
        Grade grade = Grade.of(gradeName);
        return insertGradeIntoDB(grade) > 0;
    }
    
    /**
     * insert a single student into DB
     * @param student
     * @return
     * @throws SQLException 
     */
    public boolean insertStudent(Student student) throws SQLException{
        Grade grade = student.getGrade();
        int numDatasets = 0;
        
        if(Grade.updateGrades){
            if(this.insertPreparedStatmentGrade == null){
                insertPreparedStatmentGrade = db.getConnection().prepareStatement(insertGradeString);
            }
            numDatasets += insertGradeIntoDB(grade);
        }
        
        insertUpdatePreparedStatmentStudent = db.getConnection().prepareStatement(insertStudentString);
        int tempCatNo = student.getCatNo(); // avoiding identical values ({catno, classid} must be unique)
        student.setCatNo(0);
        numDatasets += insertStudentIntoDB(student);
        student.setCatNo(tempCatNo);
        
        
        insertUpdatePreparedStatmentStudent = db.getConnection().prepareStatement(updateStudentString);
        List<Student> students = grade.getStudents().subList(student.getCatNo()-1, grade.getStudents().size())
                .stream()
                .sorted(Comparator.comparing(Student::getSurname, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Student::getFirstname, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Student::getDateOfBirth).reversed())
                .collect(Collectors.toList());
        
        for (Student student1 : students) {
            numDatasets += updateStudentDB(student1);
        }
        System.out.println(">>> " + numDatasets + " datasets changed.");
        return numDatasets > 0;
    }

    /**
     * get whole DB content --> Grade.grades
     * @return
     * @throws SQLException 
     */
    public boolean getGradesFromDB() throws SQLException{
        List<Grade> grades = new ArrayList<>();
        
        // get grades
        Statement statement = db.getStatement();
        String sqlString = "SELECT * FROM grade;";
        int noGrades = 0;
        
        ResultSet rs = statement.executeQuery(sqlString);
        while(rs.next()){
            int classid = rs.getInt("classid");
            String classname = rs.getString("classname");
            grades.add(new Grade(classname, classid));
            noGrades++;
        }
        
        if(noGrades == 0){
            return false;
        }
        
        // update local grades
        Grade.grades = grades;
        Grade.updateGrades = false;
        Grade.nextGradeID = grades.get(grades.size()-1).getClassID() + 1;
        
        // get students
        sqlString = "SELECT classid, catno, firstname, surname, gender, dateofbirth\n" +
                    "FROM student "
                    + "ORDER BY surname, firstname;";
        int noStudents = 0;
        rs.close();
        rs = statement.executeQuery(sqlString);
        while(rs.next()){
            int classid = rs.getInt("classid");
            int catno = rs.getInt("catno");
            String firstname = rs.getString("firstname");
            String surname = rs.getString("surname");
            char gender = rs.getString("gender").charAt(0);
            LocalDate dateOfBirth = rs.getDate("dateofbirth").toLocalDate();
            Student student = new Student(catno, firstname, surname, gender, dateOfBirth);
            Grade.grades.forEach((Grade grade) -> {
                if(grade.getClassID() == classid){
                    grade.addStudentWithoutSorting(student);
                    student.setGrade(grade);
                }
            });
            noStudents++;
        }
        
        rs.close();
        db.releaseStatement(statement);
        System.out.println(">>> Imported " + noGrades + " grades and " + noStudents + " students from DB.");
        return true;
    }
    
    /**
     * export DB content to .csv
     * @param path
     * @return
     * @throws SQLException
     * @throws IOException 
     */
    public boolean exportStudents(Path path) throws SQLException, IOException{
        this.getGradesFromDB();
        IO_Access.exportStudents(path, Grade.grades);
        
        return false;
    }

    public boolean isIsConnected() {
        return isConnected;
    }
    
    

    
    public static void main(String[] args) {
        try {

            DB_Access dba = new DB_Access();
            dba.importStudents(Paths.get(System.getProperty("user.dir"), "src", "res", "Studentlist_3xHIF.csv"));
            boolean status = dba.insertStudent(Student.of("4DHIF;Muster;Max;m;2000-01-01"));
            dba.getGradesFromDB();
            dba.exportStudents(Paths.get(System.getProperty("user.dir"), "src", "res", "export_Studentlist_3xHIF.csv"));
            dba.disconnect();
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    
}
