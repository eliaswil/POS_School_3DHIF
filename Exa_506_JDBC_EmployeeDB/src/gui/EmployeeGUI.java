/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import bl.EmployeeModel;
import database.DB_Access;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author Elias Wilfinger
 */
public class EmployeeGUI extends JFrame{
    private DB_Access dba;
    private JComboBox cbDepartment;
    private DefaultComboBoxModel cbmDepartment = new DefaultComboBoxModel();
    private JCheckBox cbBirthDateBefore;
    private JCheckBox cbMale;
    private JCheckBox cbFemale;
    private JTextField tfBirthDate;
    private JLabel lbManagement;
    private JTable taEmployees;
    private EmployeeModel em = new EmployeeModel();

    public EmployeeGUI(String title) throws HeadlessException {
        super(title);
        dba = DB_Access.getInstance();
        initComponents();
    }
    
    
    private void initComponents(){
        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        
            // Left
            JPanel paLeft = new JPanel(new BorderLayout());
            paLeft.setPreferredSize(new Dimension(500, 600));
            
                // Filter
                JPanel paFilter = new JPanel(new GridLayout(3, 2, 10, 10));
                paFilter.setBorder(new TitledBorder("Filter"));
                paFilter.setPreferredSize(new Dimension(400, 250));
                
                    JLabel lbDepartment = new JLabel("Department");
                    cbDepartment = new JComboBox(cbmDepartment);
                    cbBirthDateBefore = new JCheckBox("Birthdate before");
                    tfBirthDate = new JTextField();
                    cbMale = new JCheckBox("Male");
                    cbFemale = new JCheckBox("Female");
                    
                    cbDepartment.addActionListener(this::onSelectDepartment);
                    cbBirthDateBefore.addActionListener(this::onCheckBirthDate);
                    tfBirthDate.getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent arg0) {
                            onChangeBirthDate(arg0);
                        }

                        @Override
                        public void removeUpdate(DocumentEvent arg0) {
                            onChangeBirthDate(arg0);
                        }

                        @Override
                        public void changedUpdate(DocumentEvent arg0) {
                            onChangeBirthDate(arg0);
                        }
                    });
                    cbMale.addActionListener(this::onChangeGenderSelection);
                    cbFemale.addActionListener(this::onChangeGenderSelection);
                    
                    
                paFilter.add(lbDepartment);
                paFilter.add(cbDepartment);
                paFilter.add(cbBirthDateBefore);
                paFilter.add(tfBirthDate);
                paFilter.add(cbMale);
                paFilter.add(cbFemale);
                
                // Management
                JPanel paManagement = new JPanel(new BorderLayout());
                paManagement.setBorder(new TitledBorder("Management"));
                paManagement.setPreferredSize(new Dimension(400, 200));
                
                    lbManagement = new JLabel("<html><body style=\"padding: 5px;\">asdf</body></html>");
                
                paManagement.add(lbManagement);
                
            paLeft.add(paFilter, BorderLayout.NORTH);
            paLeft.add(paManagement, BorderLayout.CENTER);
            
            
            // Main
            JPanel paTableMain = new JPanel(new BorderLayout());
            paTableMain.setPreferredSize(new Dimension(500, 600));
            
                JScrollPane spTable = new JScrollPane();
                    
                    taEmployees = new JTable(em);
                    taEmployees.getSelectionModel().addListSelectionListener(this::onSelectRow);
                    
                spTable.setViewportView(taEmployees);
                spTable.getViewport().addChangeListener(this::onScroll);
            
            paTableMain.add(spTable);
            
        this.add(paLeft, BorderLayout.WEST);
        this.add(paTableMain, BorderLayout.CENTER);
        
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });
            
        
        
    }
    
    
    public void onExit(){
        try {
            dba.disconnect();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }
    
    public void onSelectDepartment(ActionEvent e){
        throw new UnsupportedOperationException("TODO: onSelectDepartment");
    }
    
    public void onCheckBirthDate(ActionEvent e){
        throw new UnsupportedOperationException("TODO: onCheckBirthDate");
    }
    
    public void onChangeBirthDate(DocumentEvent e){
        throw new UnsupportedOperationException("TODO: onChangeBirthDate");
    }
    
    public void onChangeGenderSelection(ActionEvent e){
        throw new UnsupportedOperationException("TODO: onChangeGenderSelection");
    }
    
    public void onScroll(ChangeEvent e){
        System.out.println("TODO: onScroll, scroll, scroll, ..");
//        throw new UnsupportedOperationException("TODO: onScroll");
    }
    
    public void onSelectRow(ListSelectionEvent e){
        throw new UnsupportedOperationException("TODO: onSelectRow");
    }
    
    
    public static void main(String[] args) {
        EmployeeGUI employeeGUI = new EmployeeGUI("EmployeeDB");
        employeeGUI.setVisible(true);
    }
    
}
