/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import beans.Employee;
import database.DB_Access;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
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
public class EmployeesGUI extends JFrame{
    private DefaultListModel dlm = new DefaultListModel();
    private JList liMa;
    private JLabel lbDB;
    private JLabel lbTables;
    private JTextArea taData;
    private DB_Access db;
    private JMenuItem miConnectDB;
    private JMenuItem miDisconnectDB;
    private JMenuItem miDropDB;
    private JMenuItem miCreateDB;
    private JMenuItem miCreateTable;
    private JMenuItem miDropTable;
    private JMenuItem miViewEmps;
    private JMenuItem miAvgSal;
    private JMenuItem miInsertEmps;
    private JMenuItem miRemoveEmp;

    public EmployeesGUI(String title) throws HeadlessException {
        super(title);
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        initComponents();
        try {
            db = new DB_Access();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeesGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());
        

        JMenuBar mb = new JMenuBar();
        mb.setPreferredSize(new Dimension(600, 25));
        JMenu me = new JMenu("Menue");
        miCreateDB = new JMenuItem("Create Database mitarbeiterdb");
        miCreateDB.addActionListener((e) -> {
            if(!db.createDB()){
                db.dropDB();
                db.createDB();
            }
            lbDB.setText("mitarbeiterdb");
            miConnectDB.setEnabled(true);
            miDropDB.setEnabled(true);
            miCreateDB.setEnabled(false);
            taData.append(">>> Created DB.\n");
        });
        me.add(miCreateDB);
        mb.add(me);
        
        this.setJMenuBar(mb);
        
        //Main - CENTER
        JPanel paMain = new JPanel(new BorderLayout());
        paMain.setPreferredSize(new Dimension(600, 400));
        
        // WEST
        JPanel paWest = new JPanel(new BorderLayout());
        paWest.setPreferredSize(new Dimension(200, 350));
        
        // DB
        JPanel paDB = new JPanel(new BorderLayout());
        paDB.setPreferredSize(new Dimension(200, 150));
        paDB.setBorder(new TitledBorder("DB"));
        lbDB = new JLabel("no databases");
        lbDB.setEnabled(false);
        paDB.add(lbDB, BorderLayout.CENTER);
        
            //menue
            JPopupMenu pmDB = new JPopupMenu("DB");
            miConnectDB = new JMenuItem("Connect DB");
            miDisconnectDB = new JMenuItem("Disconnect DB");
            miDropDB = new JMenuItem("Drop DB");
            miCreateTable = new JMenuItem("Create Table");

            miConnectDB.setEnabled(false);
            miDisconnectDB.setEnabled(false);
            miDropDB.setEnabled(false);
            miCreateTable.setEnabled(false);

            miConnectDB.addActionListener((e) ->{
               try {
                   db.connect();
                   miDisconnectDB.setEnabled(true);
                   miConnectDB.setEnabled(false);
                   miDropDB.setEnabled(false);
                   miCreateTable.setEnabled(true);
                   lbDB.setEnabled(true);
                   taData.append(">>> Connected DB.\n");
               } catch (SQLException ex) {
                   Logger.getLogger(EmployeesGUI.class.getName()).log(Level.SEVERE, null, ex);
               }
            });
            miDisconnectDB.addActionListener((e) ->{
               try {
                   db.dsconnect();
                   miDisconnectDB.setEnabled(false);
                   miConnectDB.setEnabled(true);
                   miDropDB.setEnabled(true);
                   miCreateTable.setEnabled(false);
                   miViewEmps.setEnabled(false);
                   miDropTable.setEnabled(false);
                   miAvgSal.setEnabled(false);
                   miInsertEmps.setEnabled(false);
                   lbTables.setEnabled(false);
                   lbDB.setEnabled(false);
                   lbTables.setText("no tables");
                   miRemoveEmp.setEnabled(false);
                   taData.append(">>> Disonnected DB.\n");
               } catch (SQLException ex) {
                   Logger.getLogger(EmployeesGUI.class.getName()).log(Level.SEVERE, null, ex);
               }
            });
            miDropDB.addActionListener((e) ->{
                db.dropDB();
                miConnectDB.setEnabled(false);
                miDropDB.setEnabled(false);
                miDisconnectDB.setEnabled(false);
                miCreateDB.setEnabled(true);
                miCreateTable.setEnabled(false);
                miViewEmps.setEnabled(false);
                miAvgSal.setEnabled(false);
                miInsertEmps.setEnabled(false);
                miRemoveEmp.setEnabled(false);
                lbTables.setEnabled(false);
                lbDB.setText("no databases");
                lbTables.setText("no tables");
                taData.append(">>> Dropped DB.\n");
            });
            miCreateTable.addActionListener((e) ->{
                db.createTable();
                miDropTable.setEnabled(true);
                miCreateTable.setEnabled(false);
                miViewEmps.setEnabled(true);
                miAvgSal.setEnabled(true);
                miInsertEmps.setEnabled(true);
                miRemoveEmp.setEnabled(true);
                lbTables.setText("mitarbeiter");
                lbTables.setEnabled(true);
                taData.append(">>> Created table mitarbeiter\n");
            });

            pmDB.add(miConnectDB);
            pmDB.add(miDisconnectDB);
            pmDB.add(miDropDB);
            pmDB.add(miCreateTable);

            lbDB.setComponentPopupMenu(pmDB);
        
        paWest.add(paDB, BorderLayout.NORTH);
        
        // Tables
        JPanel paTables = new JPanel(new BorderLayout());
        paTables.setBorder(new TitledBorder("Tables"));
        paTables.setPreferredSize(new Dimension(200, 150));
        lbTables = new JLabel("no tables");
        lbTables.setEnabled(false);
        paTables.add(lbTables, BorderLayout.CENTER);
        
            // menue
            JPopupMenu pmTables = new JPopupMenu();
            miDropTable = new JMenuItem("drop table");
            miViewEmps = new JMenuItem("view employees");
            miAvgSal = new JMenuItem("view AVG salary");
            miInsertEmps = new JMenuItem("insert employees");
            miRemoveEmp = new JMenuItem("remove employee");
            
            miDropTable.setEnabled(false);
            miViewEmps.setEnabled(false);
            miAvgSal.setEnabled(false);
            miInsertEmps.setEnabled(false);
            miRemoveEmp.setEnabled(false);
            
            miDropTable.addActionListener((e) ->{
                db.dropTable();
                miDropTable.setEnabled(false);
                miCreateTable.setEnabled(true);
                miViewEmps.setEnabled(false);
                miAvgSal.setEnabled(false);
                miInsertEmps.setEnabled(false);
                miRemoveEmp.setEnabled(false);
                lbTables.setEnabled(false);
                lbTables.setText("no tables");
                taData.append(">>> Dropped table mitarbeiter.\n");
            });
            miViewEmps.addActionListener((e) ->{
                
                String input = JOptionPane.showInputDialog("Please enter the department id:");
                
                if(input != null && !input.isEmpty()){
                    try{
                        int department = Integer.parseInt(input);
                        List<Employee> employees = db.getEmployeesFromDepartment(department);
                        String s = ">>>Employees:\n" + employees.stream().map(Employee::toString).collect(Collectors.joining("\n")) + "\n";
                        taData.append(s);
                    }catch(NumberFormatException ex){
                        taData.append(">>> Wrong input!\n");
                    }
                    
                }
                
            });
            miAvgSal.addActionListener((e) ->{
                char gender = 'x';
                String g = JOptionPane.showInputDialog("Please enter the gender:");
                if(g != null && !g.isEmpty()){
                    gender = g.toUpperCase().charAt(0);
                    String s = ">>> AVG salary of " + gender + " : " + Double.toString(db.getAverageSalery(gender)) + "\n";
                    System.out.println(s);
                    taData.append(s);
                }
                
            });
            miInsertEmps.addActionListener((e) ->{
                int noEmps = db.insertEmployees();
                taData.append(">>> Inserted " + noEmps + " employees.\n");
            });
            miRemoveEmp.addActionListener((e) ->{
                String input = JOptionPane.showInputDialog("Please enter the personal id:");
                if(input != null && !input.isEmpty()){
                    try{
                        int id = Integer.parseInt(input);
                        if(db.removeEmployee(new Employee(id))){
                            taData.append(">>> Removed employee.\n"); // TODO
                        }else{
                            taData.append(">>> Didn't remove employee - Employee does not exist!\n");
                        }
                    }catch(NumberFormatException ex){
                        taData.append(">>> wrong input!\n");
                    }
                    
                }
                
            });
            
            pmTables.add(miDropTable);
            pmTables.add(miViewEmps);
            pmTables.add(miAvgSal);
            pmTables.add(miInsertEmps);
            pmTables.add(miRemoveEmp);
            
            lbTables.setComponentPopupMenu(pmTables);
            
        
        paWest.add(paTables, BorderLayout.SOUTH);
        
        
        paMain.add(paWest, BorderLayout.WEST);
        
        
        // CENTER - Data
        JPanel paData = new JPanel(new BorderLayout());
        paData.setPreferredSize(new Dimension(400, 350));
        
        JScrollPane spData = new JScrollPane();
        
        
        taData = new JTextArea();
        taData.setEditable(false);
        spData.setViewportView(taData);
        paData.add(spData, BorderLayout.CENTER);
        paMain.add(paData, BorderLayout.CENTER);
        
        
        
        
        this.add(paMain, BorderLayout.CENTER);
        
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    try {
                        db.dsconnect();
                    } catch (SQLException ex) {
                    }
                    System.exit(0);
                }
        });
        
        
    }
    
    
    public static void main(String[] args) {
        new EmployeesGUI("EmployeeGUI").setVisible(true);
    }
    
}
