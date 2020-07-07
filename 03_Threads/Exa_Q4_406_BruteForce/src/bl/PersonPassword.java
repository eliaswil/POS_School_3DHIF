/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

/**
 *
 * @author Elias Wilfinger
 */
public class PersonPassword {
    private Person person;
    private String password;

    public PersonPassword(Person person, String password) {
        this.person = person;
        this.password = password;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PersonPassword{" + "firstname=" + person.getFirstName() + ", lastname=" + person.getLastName() + ", password=" + password + '}';
    }
    
    
    
}
