/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;
import javax.swing.JTextArea;

/**
 *
 * @author Elias Wilfinger
 */
public class AccountUser implements Runnable{
    private String name;
    private Account account;
    private JTextArea taLogging;

    public AccountUser(String name, Account account, JTextArea taLogging) {
        this.name = name;
        this.account = account;
        this.taLogging = taLogging;
    }
    
    

    @Override
    public void run() {
        int lockCounter;
        Random rand = new Random();
        
        OUTER:
        for (int i = 0; i < 10; i++) {
            lockCounter = 0;
            
            int amount = rand.nextInt(41) + 10;
            amount *= rand.nextBoolean() ? 1 : -1;
            
            synchronized(account){
                
                log(String.format("%s is trying to make the %d. withdrawal of %d €\n", name, i, amount));
                while(account.getBalance() + amount < 0){
                    
                    if(lockCounter > 3){
                        log(String.format("\tDue to deadlock danger, %s has aborted the %d. withdrawal of %d € [and continues with the next one]\n", name, i, amount));
                        continue OUTER;
                    }
                    
                    try {
                        log(String.format("\t%s has to wait before making a withdrawal of %d €\n", name, i, amount));
                        account.wait(2000);
                        lockCounter++;
                        
                        
                    } catch (InterruptedException ex) {
                    }
                }
                log(String.format("%s is performing the %d. withdrawal of %d €\n", name, i, amount));
                
                account.withdrawMoney(amount);
                
                log(String.format("%s is finished with the %d. transaction of %d €\n", name, i, amount));

                account.notifyAll();

                try {
                    Thread.sleep((rand.nextInt(1000) + 1));

                } catch (InterruptedException ex) {
                }
            } 
        }
    }
    
    private void log(String logMessage){
        synchronized(taLogging){
            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss.SSS:\t"));
            taLogging.append(dateTime + logMessage);
        }
    } 
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.account);
        hash = 47 * hash + Objects.hashCode(this.taLogging);
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
        final AccountUser other = (AccountUser) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.account, other.account)) {
            return false;
        }
        if (!Objects.equals(this.taLogging, other.taLogging)) {
            return false;
        }
        return true;
    }
  
    
    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }

    public JTextArea getTaLogging() {
        return taLogging;
    }

    
    
    
    
    
    
}
