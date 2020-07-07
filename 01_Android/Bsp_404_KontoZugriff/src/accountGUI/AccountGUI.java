/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accountGUI;

import accountBL.Account;
import accountBL.AccountUser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author sf
 */
public class AccountGUI extends JFrame
{

  private DefaultListModel dlm = new DefaultListModel();
  private Account account;
  private JLabel lbAccount;
  private JList liUser;
  private JTextArea taLog;

  public AccountGUI(String title) throws HeadlessException
  {
    super(title);
    this.setSize(500, 400);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    initComponents();
  }

  private void initComponents()
  {
    this.setLayout(new BorderLayout());
    Container cp = this.getContentPane();

    // Left Panel:
    JPanel paLeft = new JPanel();
    paLeft.setBorder(BorderFactory.createTitledBorder("User"));
    paLeft.setLayout(new BorderLayout());
    liUser = new JList();
    liUser.setModel(dlm);
    paLeft.add(liUser, BorderLayout.CENTER);
    paLeft.setPreferredSize(new Dimension(120, 300));
    cp.add(paLeft, BorderLayout.WEST);

    // Right Panel:
    JPanel paRight = new JPanel();
    paRight.setBorder(BorderFactory.createTitledBorder("Log-output"));
    paRight.setLayout(new BorderLayout());
    JScrollPane scrollPane = new JScrollPane();
    taLog = new JTextArea();
    taLog.setFont(new Font("Courier new", Font.BOLD, 13));
    taLog.setText("use context menu to create accout\n");
    scrollPane.setViewportView(taLog);
    paRight.add(scrollPane, BorderLayout.CENTER);
    cp.add(paRight, BorderLayout.CENTER);

    // Top Panel:
    JPanel paTop = new JPanel();
    paTop.setBorder(BorderFactory.createTitledBorder("Account-Balance"));
    paTop.setLayout(new BorderLayout());
    lbAccount = new JLabel("0,00 Euro");
    lbAccount.setHorizontalAlignment(JLabel.RIGHT);
    lbAccount.setFont(new Font("Courier new", Font.BOLD, 20));
    paTop.add(lbAccount, BorderLayout.CENTER);
    cp.add(paTop, BorderLayout.SOUTH);

    // Add contextmenu for user-list and account-test
    JPopupMenu pm = new JPopupMenu();
    JMenuItem miAddUser = new JMenuItem("add user");
    miAddUser.addActionListener(e -> onAddUser());
    pm.add(miAddUser);

    JMenuItem miAccTest = new JMenuItem("perform account test");
    miAccTest.addActionListener(e -> onPerfromAccountTest());
    pm.add(miAccTest);
    liUser.setComponentPopupMenu(pm);

    // Add contextmenu for account creation
    JPopupMenu pmAcc = new JPopupMenu();
    final JMenuItem miCreateAcc = new JMenuItem("create new account");
    miCreateAcc.addActionListener( e -> onAddAccount(e));
    pmAcc.add(miCreateAcc);
    taLog.setComponentPopupMenu(pmAcc);
  }

  private void onAddUser()
  {
    String userName = JOptionPane.showInputDialog("Please insert user name:");
    dlm.addElement(userName);
  }

  private void onAddAccount(ActionEvent e)
  {
    account = new Account(50.0, lbAccount);
    taLog.setText("new account created\n");
  }

  /**
   * Run Account-Test with seleted user
   */
  private void onPerfromAccountTest()
  {
    if (liUser.getSelectedValue() != null)
    {
      ArrayList<String> userNames = (ArrayList<String>) liUser.getSelectedValuesList();
      userNames.forEach(name -> new AccountUser(account, name, taLog));
    }
  }

  public static void main(String[] args)
  {
    new AccountGUI("Konto-Verwaltung").setVisible(true);
  }
}
