/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

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
    private String genre;

    public Book(String title, String isbnNr, List<String> authors, String publisher, int pages, float rating, String genre) {
        this.title = title;
        this.isbnNr = isbnNr;
        this.authors = authors;
        this.publisher = publisher;
        this.pages = pages;
        this.rating = rating;
        this.genre = genre;
    }

    public Book() {
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.title);
        hash = 43 * hash + Objects.hashCode(this.isbnNr);
        hash = 43 * hash + Objects.hashCode(this.authors);
        hash = 43 * hash + Objects.hashCode(this.publisher);
        hash = 43 * hash + this.pages;
        hash = 43 * hash + Float.floatToIntBits(this.rating);
        hash = 43 * hash + Objects.hashCode(this.genre);
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
        if (!Objects.equals(this.genre, other.genre)) {
            return false;
        }
        if (!Objects.equals(this.authors, other.authors)) {
            return false;
        }
        return true;
    }
    
    
}
