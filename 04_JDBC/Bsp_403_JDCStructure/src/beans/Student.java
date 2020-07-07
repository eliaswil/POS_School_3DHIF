/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.time.LocalDate;

/**
 *
 * @author SF <htlkaindorf.at>
 */
public class Student {
  private int id;
  private int catNo;
  private String firstname;
  private String lastname;
  private LocalDate dateOfBirth;

  public Student(int id, int catNo, String firstname, String lastname, LocalDate dateOfBirth) {
    this.id = id;
    this.catNo = catNo;
    this.firstname = firstname;
    this.lastname = lastname;
    this.dateOfBirth = dateOfBirth;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCatNo() {
    return catNo;
  }

  public void setCatNo(int catNo) {
    this.catNo = catNo;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  @Override
  public String toString() {
    return "Student{" + "id=" + id + ", catNo=" + catNo + ", firstname=" + firstname + ", lastname=" + lastname + ", dateOfBirth=" + dateOfBirth + '}';
  }
  
  
  
}
