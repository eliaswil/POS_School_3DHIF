/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

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
    
    private static String generalPath = Paths.get(System.getProperty("user.dir"), "src", "sql").toString();
    
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
    

    
    
    

    
    public static void main(String[] args) {
        DB_Access dba = new DB_Access();
        
        try {
            dba.disconnect();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    
    
}
