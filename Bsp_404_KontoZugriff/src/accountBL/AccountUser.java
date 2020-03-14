/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accountBL;

import java.util.Random;
import javax.swing.JTextArea;

/**
 *
 * @author sf
 */
public class AccountUser extends Thread
{
  private final Account account;
  private final String userName;
  private final JTextArea taLog;

  public AccountUser(Account account, String name, JTextArea taLog)
  {
    this.account = account;
    this.userName = name;
    this.taLog = taLog;
    start();
  }

  @Override
  public String toString()
  {
    return userName;
  }

  @Override
  public void run()
  {
    log(" starts test <<<<\n");
    Random rand = new Random();
    OUTER:
    for (int i = 0; i < 10; i++)
    {
      int amount = rand.nextInt(41) + 10;
      amount = amount * (rand.nextBoolean() ? -1 : 1);
      synchronized (account)
      {
        int count = 0;
        while (account.getBalance() + amount < 0)
        {
          try
          {
            log(" has to wait to withdraw " + amount + " \n");
            account.wait(2000);
            count++;
            log(" has finished waiting\n");
          } catch (InterruptedException ex)
          {
          }
          if (count == 3)
          {
            log(" has been interrupted to avoid deadlock <<<<\n");
            break OUTER;
          }
        }
        log(" makes withdrawel: " + amount + "\n");
        account.makeWithdrawel(amount);
        account.notifyAll();
      }
      try
      {
        Thread.sleep(rand.nextInt(1000));
      } catch (InterruptedException ex)
      {
      }
    }
    log(" has finished <<<<\n");
  }

  private void log(String logText)
  {
    synchronized (taLog)
    {
      taLog.append(userName + logText);
    }
  }
}
