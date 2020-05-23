/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import beans.Book;
import beans.SearchType;
import io.IO_Access;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.Statement;
import java.sql.ResultSet;
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
    
    public List<Book> getFilteredBooks(List<SearchType> searchingOrder, String publisher, String genre, String searchPattern){
        throw new UnsupportedOperationException("TODO");
    }
    
    public List<String> getAllPublishers(){
        throw new UnsupportedOperationException("TODO");
    }
    
    public List<String> getAllGenres(){
        // remove "Genres" in sql statement and add 'All' at pos=0
        throw new UnsupportedOperationException("TODO");
    }
    
    public List<String> getFilteredGenres(String publisher, String searchPattern, SearchType searchType) throws FileNotFoundException, SQLException{
        List<String> genres = new ArrayList<>();
        String sqlString = IO_Access.getSQLStatementString(Paths.get(generalPath, "getFilteredGenres.sql").toFile());
        
        sqlString = sqlString.replaceAll("\\{publisherName\\}", publisher);
        if(SearchType.valueOf("AUTHOR").equals(searchType)){
            sqlString = sqlString.replace("{searchStringBookTitle}", "").replaceAll("\\{searchStringAuthor\\}", searchPattern);
        }else{
            sqlString = sqlString.replace("{searchStringBookTitle}", searchPattern).replaceAll("\\{searchStringAuthor\\}", "");
        }
        
        Statement statement = db.getStatement();
        ResultSet rs = statement.executeQuery(sqlString);
        while(rs.next()){
            genres.add(rs.getString("Genres"));
        }
        db.releaseStatement(statement);
        genres.add(0, "All");
        
        System.out.println(">>> Returning " + (genres.size()-1) + " filtered genres.");
        return genres;
    }
    
    public List<String> getFilteredPublishers(String genre, String searchPattern, SearchType searchType) throws FileNotFoundException, SQLException{
        List<String> publishers = new ArrayList<>();
        String sqlString = IO_Access.getSQLStatementString(Paths.get(generalPath, "getFilteredPublishers.sql").toFile());
        
        sqlString = sqlString.replaceAll("\\{genre\\}", genre);
        if(SearchType.valueOf("AUTHOR").equals(searchType)){
            sqlString = sqlString.replace("{searchStringBookTitle}", "").replaceAll("\\{searchStringAuthor\\}", searchPattern);
        }else{
            sqlString = sqlString.replace("{searchStringBookTitle}", searchPattern).replaceAll("\\{searchStringAuthor\\}", "");
        }
        
        Statement statement = db.getStatement();
        ResultSet rs = statement.executeQuery(sqlString);
        while(rs.next()){
            publishers.add(rs.getString("Publishers"));
        }
        db.releaseStatement(statement);
        publishers.add(0, "All");
        
        System.out.println(">>> Returning " + (publishers.size()-1) + " filtered publishers.");
        return publishers;
    }
    
    public List<String> getFilteredBookTitles(String genre, String publisher, String searchPattern, SearchType searchType) throws FileNotFoundException, SQLException{
        List<String> bookTitles = new ArrayList<>();
        String sqlString = IO_Access.getSQLStatementString(Paths.get(generalPath, "getFilteredBooks.sql").toFile());
        
        sqlString = sqlString.replaceAll("\\{genre\\}", genre).replaceAll("\\{publisherName\\}", publisher);
        if(SearchType.valueOf("AUTHOR").equals(searchType)){
            sqlString = sqlString.replace("{searchStringBookTitle}", "").replaceAll("\\{searchStringAuthor\\}", searchPattern);
        }else{
            sqlString = sqlString.replace("{searchStringBookTitle}", searchPattern).replaceAll("\\{searchStringAuthor\\}", "");
        }
        
        Statement statement = db.getStatement();
        ResultSet rs = statement.executeQuery(sqlString);
        while(rs.next()){
            bookTitles.add(rs.getString("Title"));
        }
        db.releaseStatement(statement);
        
        System.out.println(">>> Returning " + bookTitles.size() + " filtered books.");
        return bookTitles;
    }

    public Book getBookDetails(String title){
        List<String> authors = new ArrayList<>();
        List<String> genres = new ArrayList<>();
        // ¿¿ Request also parent-genres ?? ???????????????????????????????????????????????????????????????
        throw new UnsupportedOperationException("TODO");
    }
    
    
    

    
    public static void main(String[] args) {
        DB_Access dba = new DB_Access();
        try {
            List<String> genres = dba.getFilteredGenres("", "", SearchType.BOOK);
            System.out.println(genres + "\n");
            List<String> publishers = dba.getFilteredPublishers("", "", SearchType.AUTHOR);
            System.out.println(publishers + "\n");
            List<String> bookTitles = dba.getFilteredBookTitles("Programming", "", "Bat", SearchType.AUTHOR);
            System.out.println(bookTitles + "\n");
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
