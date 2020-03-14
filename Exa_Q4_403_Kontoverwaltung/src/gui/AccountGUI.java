/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import bl.Account;
import bl.AccountUser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Elias Wilfinger
 */
public class AccountGUI extends JFrame{
    private DefaultListModel dlm = new DefaultListModel();
    private JLabel lbAccountBalance;
    private JTextArea taLoggingOutput;
    private JList liUsers;
    private Account account;

    public AccountGUI(String title) throws HeadlessException {
        super(title);
        this.setSize(1000, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        initComponents();
        
    }
    
    private void initComponents(){
        this.setLayout(new BorderLayout(5, 5));
        
        // User-panel (left)
        JPanel userPanel = new JPanel(new BorderLayout());
        liUsers = new JList(dlm);
        userPanel.add(liUsers, BorderLayout.CENTER);
        userPanel.setBorder(new TitledBorder("User"));
        userPanel.setPreferredSize(new Dimension(150, 400));
        
        this.add(userPanel, BorderLayout.WEST);
        
        // Account ( current balance; bottom)
        JPanel accountPanel = new JPanel(new BorderLayout());
        lbAccountBalance = new JLabel("0,00 Euro");
        lbAccountBalance.setHorizontalAlignment(JLabel.TRAILING);
        lbAccountBalance.setFont(new Font("Monospaced", Font.PLAIN, 25));
        accountPanel.setPreferredSize(new Dimension(600, 100));
        accountPanel.setBorder(new TitledBorder("Account"));
        accountPanel.add(lbAccountBalance, BorderLayout.CENTER);
        
        this.add(accountPanel, BorderLayout.SOUTH);
        
        // Logging Panel (output; top-right)
        JPanel loggingPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        
        taLoggingOutput = new JTextArea("ready to create a bank account!\n");
        taLoggingOutput.setEditable(false);
        scrollPane.setViewportView(taLoggingOutput);
        loggingPanel.add(scrollPane, BorderLayout.CENTER);
        
        loggingPanel.setPreferredSize(new Dimension(425, 375));
        loggingPanel.setBorder(new TitledBorder("Log-Output"));
        
        this.add(loggingPanel, BorderLayout.CENTER);
        
        
       // context menues
        
        // create bank account
        JPopupMenu accountMenue = new JPopupMenu();
        JMenuItem miCreateAccount = new JMenuItem("create bank account");
        
        accountMenue.add(miCreateAccount);
        
        miCreateAccount.addActionListener(this::onCreateBankAccount);
        
        taLoggingOutput.setComponentPopupMenu(accountMenue);
        
        // User Menue + Test
        JPopupMenu userMenue = new JPopupMenu();
        JMenuItem miAddUser = new JMenuItem("add user");
        JMenuItem miPerformTest = new JMenuItem("perform test");
        
        userMenue.add(miAddUser);
        userMenue.add(miPerformTest);
        
        miAddUser.addActionListener(this::onAddUser);
        miPerformTest.addActionListener(this::onPerformTest);
        
        liUsers.setComponentPopupMenu(userMenue);
 
    }
    
    private void onCreateBankAccount(ActionEvent evt){
        Account account = new Account(50.0, lbAccountBalance);
        taLoggingOutput.setText("created new bank account\n");
        this.account = account;
    }
    
    private void onAddUser(ActionEvent evt){
        String name = JOptionPane.showInputDialog(this.getContentPane(), "Please enter the name of the user", "Add user", JOptionPane.PLAIN_MESSAGE);
        
        if(name.isEmpty()){
            JOptionPane.showMessageDialog(this.getContentPane(), "No user created!", "Empty Name", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(dlm.contains(name)){
            JOptionPane.showMessageDialog(this.getContentPane(), "User already exists!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        dlm.addElement(name);
    }
    
    private void onPerformTest(ActionEvent evt){
        List<String> selectedUsersNames = liUsers.getSelectedValuesList();
        
        if(account == null){
            JOptionPane.showMessageDialog(this.getContentPane(), "You need to create a bank account first!", "No Bank Account", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(selectedUsersNames.isEmpty()){
            JOptionPane.showMessageDialog(this.getContentPane(), "Please select at least 1 user!", "No users selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        selectedUsersNames.forEach(username ->{
            AccountUser user = new AccountUser(username, this.account, taLoggingOutput);
            Thread thread = new Thread(user, username);
            thread.setPriority(Thread.NORM_PRIORITY + 1);
            thread.start();
            
        });
        
    }
    
    
    
    
    
    
    public static void main(String[] args) {
        new AccountGUI("Kontoverwaltung").setVisible(true);
     
    }
    
}
