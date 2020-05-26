/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Elias Wilfinger
 */
public class Book {
    private String title;
    private String isbnNr;
    private List<String> authors;
    private String publisher;
    private int pages;
    private float rating;
    private List<String> genres;
    private LocalDate publishedDate;

    public Book(String title, String isbnNr, List<String> authors, String publisher, int pages, float rating, List<String> genres, LocalDate publishedDate) {
        this.title = title;
        this.isbnNr = isbnNr;
        this.authors = authors;
        this.publisher = publisher;
        this.pages = pages;
        this.rating = rating;
        this.genres = genres;
        this.publishedDate = publishedDate;
    }

    

    public Book() {
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.title);
        hash = 97 * hash + Objects.hashCode(this.isbnNr);
        hash = 97 * hash + Objects.hashCode(this.authors);
        hash = 97 * hash + Objects.hashCode(this.publisher);
        hash = 97 * hash + this.pages;
        hash = 97 * hash + Float.floatToIntBits(this.rating);
        hash = 97 * hash + Objects.hashCode(this.genres);
        hash = 97 * hash + Objects.hashCode(this.publishedDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (this.pages != other.pages) {
            return false;
        }
        if (Float.floatToIntBits(this.rating) != Float.floatToIntBits(other.rating)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.isbnNr, other.isbnNr)) {
            return false;
        }
        if (!Objects.equals(this.publisher, other.publisher)) {
            return false;
        }
        if (!Objects.equals(this.authors, other.authors)) {
            return false;
        }
        if (!Objects.equals(this.genres, other.genres)) {
            return false;
        }
        if (!Objects.equals(this.publishedDate, other.publishedDate)) {
            return false;
        }
        return true;
    }

    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbnNr() {
        return isbnNr;
    }

    public void setIsbnNr(String isbnNr) {
        this.isbnNr = isbnNr;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public String toString() {
        return "Book{" + "title=" + title + ", isbnNr=" + isbnNr + ", authors=" + authors + ", publisher=" + publisher + ", pages=" + pages + ", rating=" + rating + ", genres=" + genres + ", publishedDate=" + publishedDate + '}';
    }

    
    
    

    
    
    
}
