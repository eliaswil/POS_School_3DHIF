/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accountBL;

import javax.swing.JLabel;

/**
 *
 * @author sf
 */
public class Account
{
  private double balance = 0.0;
  private JLabel lbAccount;

  public Account(double balance, JLabel lbAccount)
  {
    this.balance = balance;
    this.lbAccount = lbAccount;
    lbAccount.setText(String.format("%1.2f €",balance));
  }
  
  public void makeWithdrawel(double amount)
  {
    balance += amount;
    lbAccount.setText(String.format("%1.2f e",balance));
  }

  public double getBalance()
  {
    return balance;
  }
  
}
