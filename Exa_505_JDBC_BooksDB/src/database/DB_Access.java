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
    
    /**
     * get a list of all publishers
     * @return
     * @throws SQLException 
     */
    public List<String> getAllPublishers() throws SQLException{
        List<String> publishers = new ArrayList<>();
        
        String sqlString = "SELECT name AS \"name\" FROM publishers "
                + "ORDER BY name;";
        
        Statement statement = db.getStatement();
        ResultSet rs = statement.executeQuery(sqlString);
        while(rs.next()){
            publishers.add(rs.getString("name"));
        }
        db.releaseStatement(statement);
        
        System.out.println(">>> Requested " + publishers.size() + " publishers.");
        publishers.add(0, "All");
        return publishers;
    }
    
    /**
     * get a list of all genres
     * @return
     * @throws SQLException 
     */
    public List<String> getAllGenres() throws SQLException{
        // remove "Genres" in sql statement and add 'All' at pos=0
        List<String> genres = new ArrayList<>();
        
        String sqlString = "SELECT genre AS \"genre\" FROM genres "
                + "WHERE NOT genre = 'Genres' "
                + "ORDER BY genre;";
        
        Statement statement = db.getStatement();
        ResultSet rs = statement.executeQuery(sqlString);
        while(rs.next()){
            genres.add(rs.getString("genre"));
        }
        db.releaseStatement(statement);
        
        System.out.println(">>> Requested " + genres.size() + " genres.");
        genres.add(0, "All");
        return genres;
    }
    
    /**
     * get a list of all available genres (when filtering with a given publisher)
     * @param publisher
     * @return
     * @throws FileNotFoundException
     * @throws SQLException 
     */
    public List<String> getFilteredGenres(String publisher) throws FileNotFoundException, SQLException{
        List<String> genres = new ArrayList<>();
        String sqlString = IO_Access.getSQLStatementString(Paths.get(generalPath, "getFilteredGenres.sql").toFile());
        
        sqlString = sqlString.replaceAll("\\{publisherName\\}", publisher.replaceAll("'", "''"));
        
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
    
    /**
     * get a list of all available publishers (when filtering with a given genre)
     * @param genre
     * @return
     * @throws FileNotFoundException
     * @throws SQLException 
     */
    public List<String> getFilteredPublishers(String genre) throws FileNotFoundException, SQLException{
        List<String> publishers = new ArrayList<>();
        String sqlString = IO_Access.getSQLStatementString(Paths.get(generalPath, "getFilteredPublishers.sql").toFile());
        
        sqlString = sqlString.replaceAll("\\{genre\\}", genre.replaceAll("'", "''"));
        
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
    
    /**
     * get a list of filtered book titles that match the given filter criteria
     * @param genre
     * @param publisher
     * @param searchPattern either earching for author or title
     * @param searchType determines whether to search for an author's name or for a book title
     * @return
     * @throws FileNotFoundException
     * @throws SQLException 
     */
    public List<String> getFilteredBookTitles(String genre, String publisher, String searchPattern, SearchType searchType) throws FileNotFoundException, SQLException{
        List<String> bookTitles = new ArrayList<>();
        String sqlString = IO_Access.getSQLStatementString(Paths.get(generalPath, "getFilteredBooks.sql").toFile());
        
        sqlString = sqlString.replaceAll("\\{genre\\}", genre.replaceAll("'", "''")).replaceAll("\\{publisherName\\}", 
                publisher.replaceAll("'", "''"));
        searchPattern = searchPattern.replaceAll("'", "''");
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

    /**
     * Request information of a book with a given title
     * @param title
     * @return
     * @throws FileNotFoundException
     * @throws SQLException 
     */
    public Book getBookDetails(String title) throws FileNotFoundException, SQLException{
        List<String> authors = new ArrayList<>();
        List<String> genres = new ArrayList<>();
        String sqlString = IO_Access.getSQLStatementString(Paths.get(generalPath, "getBookDetails.sql").toFile());
        sqlString = sqlString.replaceAll("\\{bookTitle\\}", title.replaceAll("'", "''"));
        
        Statement statement = db.getStatement();
        ResultSet rs = statement.executeQuery(sqlString);
        String isbn = "";
        int total_pages = -1;
        float rating = -1.0f;
        LocalDate publishedDate = null;
        String publisher_name = "";
        while(rs.next()){
            String last_name = rs.getString("last_name");
            String middle_name = rs.getString("middle_name");
            String first_name = rs.getString("first_name");
            String name = (last_name == null ? "" : last_name + ", " )
                    +  (middle_name == null ? "" : middle_name + ", ")
                    + first_name;
            if(!authors.contains(name)) authors.add(name);
            isbn = rs.getString("isbn");
            total_pages = rs.getInt("total_pages");
            String genre = rs.getString("genre");
            if(!genres.contains(genre)) genres.add(genre);
            rating = rs.getFloat("rating");
            publishedDate = rs.getDate("published_date").toLocalDate();
            publisher_name = rs.getString("publisher_name");
        }
        db.releaseStatement(statement);
        
        
             
        Book book = new Book(title, isbn, authors, publisher_name, total_pages, rating, genres, publishedDate);
        System.out.println(">>> Requested book details of book: " + title);
        return book;
    }
    
    
    

    
    public static void main(String[] args) {
        DB_Access dba = new DB_Access();
        try {
//            List<String> genres = dba.getFilteredGenres("", "", SearchType.BOOK);
//            System.out.println(genres + "\n");
//            List<String> publishers = dba.getFilteredPublishers("", "", SearchType.AUTHOR);
//            System.out.println(publishers + "\n");
            List<String> bookTitles = dba.getFilteredBookTitles("Programming", "", "Bat", SearchType.AUTHOR);
            bookTitles = dba.getFilteredBookTitles("", "", "", SearchType.AUTHOR);
            System.out.println(bookTitles + "\n");
            
            System.out.println(dba.getBookDetails("SCJP Sun Certified Programmer for Java 6 Study Guide"));
        } catch (FileNotFoundException | SQLException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        try {
            dba.disconnect();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    
    
}
