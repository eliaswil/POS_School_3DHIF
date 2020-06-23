/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import beans.Employee;
import bl.EmployeeModel;
import database.DB_Access;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.RowSorterEvent;

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
    private EmployeeModel em = new EmployeeModel(this);
    
    private final long LIMIT_INCREASE = 900;
    private long limit = LIMIT_INCREASE;
    private boolean isFetching = false;
    private List<SortKey> sortOrder = new ArrayList<>();

    public EmployeeGUI(String title) throws HeadlessException, FileNotFoundException, SQLException {
        super(title);
        dba = DB_Access.getInstance();
        initComponents();
        fillComponentsWithData();
    }
    
    public DB_Access getDB_Access(){
        return dba;
    }
    
    /**
     * initializes all Components
     */
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
                    
                    cbMale.setSelected(true);
                    cbFemale.setSelected(true);
                    
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
                    taEmployees.setAutoCreateRowSorter(true); // automatically sort table by column
                    taEmployees.getRowSorter().addRowSorterListener(this::onSort);
                    
                spTable.setViewportView(taEmployees);
                spTable.getVerticalScrollBar().addAdjustmentListener(this::onScroll);
            
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
    
    /**
     * fills the combobox with data (Departments)
     * @throws FileNotFoundException
     * @throws SQLException 
     */
    private void fillComponentsWithData() throws FileNotFoundException, SQLException{
        cbmDepartment.removeAllElements();
        cbmDepartment.addAll(dba.getDepartments());
        if(cbmDepartment.getSize() > 0){
            cbDepartment.setSelectedIndex(0);
        }
    }

    public void onExit(){
        try {
            dba.disconnect();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }
    
    /**
     * handler for the combobox (selecting)
     * @param e 
     */
    public void onSelectDepartment(ActionEvent e){
        // set Management
        String department = cbDepartment.getSelectedItem().toString();
        String htmlString = "<html><body style=\"padding: 10px;\">";
        try {
            htmlString = dba.getManagersFromDepartment(department)
                    .stream().map((manager) -> manager.toString() + "\n")
                    .reduce(htmlString, String::concat);
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        htmlString += "</body></html>";
        
        lbManagement.setText(htmlString);
        setEmployees();
    }
    
    /**
     * whenever any filter criteria has changed this mehtod should be called,
     * to update the employees in the table
     */
    private void setEmployees(){
        String department = cbDepartment.getSelectedItem().toString();
        LocalDate birth_date_before = LocalDate.now();
        if(cbBirthDateBefore.isSelected()){
            if(!tfBirthDate.getText().isBlank()){
                try{
                    birth_date_before = LocalDate.parse(tfBirthDate.getText(), Employee.DTF);
                }catch(DateTimeParseException ex){
                    JOptionPane.showMessageDialog(this, "Wrong Date Format!\nPlease use: dd.MM.yyyy", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }  
        }
        
        try {
            // set Employees - TODO
            em.setEmployeesForDepartment(dba.getEmployeesFromDepartment(department, birth_date_before, 
                    cbMale.isSelected(), cbFemale.isSelected(), limit, sortOrder));
            em.fireTableDataChanged();
        } catch (FileNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * handler for the checkbox 'Birthdate before'
     * @param e 
     */
    public void onCheckBirthDate(ActionEvent e){
        setEmployees();
    }
    
    /**
     * whenever the user changes the birthdate and the checkbox is selected --> update employees (table)
     * @param e 
     */
    public void onChangeBirthDate(DocumentEvent e){
        if(tfBirthDate.getText().length() >= 10 && cbBirthDateBefore.isSelected()){
            setEmployees();
        }
    }
    
    public void onChangeGenderSelection(ActionEvent e){
        setEmployees();
    }
    
    public void onScroll(AdjustmentEvent e){
        if (!e.getValueIsAdjusting()) {
            JScrollBar scrollBar = (JScrollBar) e.getAdjustable();
            int extent = scrollBar.getModel().getExtent();
            int maximum = scrollBar.getModel().getMaximum();
            if (extent + e.getValue() == maximum && !isFetching) {
                isFetching = true;
                limit += LIMIT_INCREASE;
                setEmployees();
                isFetching = false;
            }
        }
    }
    
    public void onSort(RowSorterEvent e){
        if(e.getType().equals(javax.swing.event.RowSorterEvent.Type.SORT_ORDER_CHANGED)){
            sortOrder = (List<SortKey>) taEmployees.getRowSorter().getSortKeys();
            setEmployees();
        }
                
    }
    
    
    public static void main(String[] args) {
        try {
            EmployeeGUI employeeGUI = new EmployeeGUI("EmployeeDB");
            employeeGUI.setVisible(true);
        } catch (HeadlessException | FileNotFoundException | SQLException | DateTimeParseException ex) {
            ex.printStackTrace();
        }
    }
    
}
