/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import javax.swing.JLabel;

/**
 *
 * @author Elias Wilfinger
 */
public class Account {
    private double balance;
    private JLabel lbAccountBalance;

    public Account(double balance, JLabel lbAccountBalance) {
        this.balance = balance;
        this.lbAccountBalance = lbAccountBalance;
        this.lbAccountBalance.setText(String.format("%1.2f Euro", this.balance));
    }
    
    public void withdrawMoney(double amount){
        this.balance += amount;
        this.lbAccountBalance.setText(String.format("%1.2f Euro", this.balance));
    }

    public double getBalance() {
        return balance;
    }
    
    

    
    
    
    
}
