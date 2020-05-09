/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import database.DB_Access;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import beans.Student;
import beans.Grade;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Elias Wilfinger
 */
public class SchoolAdminGUI extends JFrame{
    private JButton btToggleConnect;
    private JButton btImport;
    private JButton btExport;
    private JButton btExit;
    
    private JTextField tfCatNr;
    private JTextField tfGrade;
    private JTextField tfSurname;
    private JTextField tfFirstname;
    private JTextField tfDateOfBirth;
    private JTextField tfAge;
    
    private JButton btNewAndSave;
    private JButton btCancel;
    private JButton btPreviousClass;
    private JButton btPreviousStudent;
    private JButton btNextStudent;
    private JButton btNextClass;
    
    private DB_Access dba;
    
    private JComboBox<String> cbClasses;
    private DefaultComboBoxModel<String> cbm = new DefaultComboBoxModel<>();
    
    private int currentClassId = 0;
    private int currentStudentId = 0;
    private char newOrSave = 'n';
    private boolean last = false;


    public SchoolAdminGUI(String title) throws HeadlessException {
        super(title);
        this.setSize(600, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        initComponents();
    }
    
    private void initComponents() {
        this.setLayout(new BorderLayout());
        
        // "Menue"
        JPanel paMenue = new JPanel(new GridLayout(1, 3));
        btExit = new JButton("Beenden");
        btExport = new JButton("Export");
        btImport = new JButton("Import");
        btToggleConnect = new JButton("Connect");
        
        
        btExit.addActionListener(this::onExit);
        btExport.addActionListener(this::onExport);
        btImport.addActionListener(this::onImport);
        btToggleConnect.addActionListener(this::onToggleConnection);
        
        btImport.setEnabled(false);
        btExport.setEnabled(false);
        
        paMenue.add(btToggleConnect);
        paMenue.add(btImport);
        paMenue.add(btExport);
        paMenue.add(btExit);
        
        this.add(paMenue, BorderLayout.NORTH);
        
        
        // Main

        JPanel paMain = new JPanel(new BorderLayout());
        
            // Klasse
            JPanel paClass = new JPanel(new BorderLayout());
            
            JLabel lbClass = new JLabel("Klasse:", SwingConstants.RIGHT);
            lbClass.setBorder(new EmptyBorder(5, 0, 5, 10));
            cbClasses = new JComboBox<>(cbm);
            cbClasses.setPreferredSize(new Dimension(200, 40));
            cbClasses.setBorder(new EmptyBorder(5, 5, 5, 5));
            cbClasses.setEnabled(false);
            cbClasses.addActionListener(this::onSelectGrade);
            
            paClass.add(lbClass, BorderLayout.CENTER);
            paClass.add(cbClasses, BorderLayout.EAST);
            
        paMain.add(paClass, BorderLayout.NORTH);
        
        
            // Student Data
            JPanel paStudent = new JPanel(new BorderLayout());
            
                // Main 
                JPanel paStudentData = new JPanel(new GridLayout(3, 4, 5, 10));
                JLabel lbCatNr = new JLabel("Kat-Nr:", SwingConstants.RIGHT);
                JLabel lbFirstname = new JLabel("Vorname:", SwingConstants.RIGHT);
                JLabel lbSurname = new JLabel("Nachname:", SwingConstants.RIGHT);
                JLabel lbGrade = new JLabel("Klasse:", SwingConstants.RIGHT);
                JLabel lbDateOfBirth = new JLabel("Geb-Dat:", SwingConstants.RIGHT);
                JLabel lbAge = new JLabel("Alter:", SwingConstants.RIGHT);
                
                tfCatNr = new JTextField();
                tfGrade = new JTextField();
                tfSurname = new JTextField();
                tfFirstname = new JTextField();
                tfDateOfBirth = new JTextField();
                tfAge = new JTextField();
                
                tfCatNr.setEditable(false);
                tfGrade.setEditable(false);
                tfSurname.setEditable(false);
                tfFirstname.setEditable(false);
                tfDateOfBirth.setEditable(false);
                tfAge.setEditable(false);
                
                
                paStudentData.add(lbCatNr, 0);
                paStudentData.add(tfCatNr, 1);
                paStudentData.add(lbGrade, 2);
                paStudentData.add(tfGrade, 3);
                paStudentData.add(lbFirstname, 4);
                paStudentData.add(tfFirstname, 5);
                paStudentData.add(lbSurname, 6);
                paStudentData.add(tfSurname, 7);
                paStudentData.add(lbDateOfBirth, 8);
                paStudentData.add(tfDateOfBirth, 9);
                paStudentData.add(lbAge, 10);
                paStudentData.add(tfAge, 11);
                
                
                paStudent.add(paStudentData, BorderLayout.CENTER);
                
                // Navigation / Buttons
                
                JPanel paNavigation = new JPanel(new GridLayout(1, 6));
                paNavigation.setBorder(new EmptyBorder(10, 5, 5, 5));
                
                btNewAndSave = new JButton("Neu");
                btCancel = new JButton("Abbrechen");
                btPreviousClass = new JButton("<");
                btPreviousStudent = new JButton("<|");
                btNextStudent = new JButton("|>");
                btNextClass = new JButton(">");
                
                btNewAndSave.setEnabled(false);
                btCancel.setEnabled(false);
                btPreviousClass.setEnabled(false);
                btPreviousStudent.setEnabled(false);
                btNextClass.setEnabled(false);
                btNextStudent.setEnabled(false);

                
                btNewAndSave.addActionListener(this::onNewOrSave);
                btCancel.addActionListener(this::onCancel);
                btPreviousClass.addActionListener(this::onPreviousClass);
                btPreviousStudent.addActionListener(this::onPreviousStudent);
                btNextClass.addActionListener(this::onNextClass);
                btNextStudent.addActionListener(this::onNextStudent);
                
                paNavigation.add(btNewAndSave);
                paNavigation.add(btCancel);
                paNavigation.add(btPreviousStudent);
                paNavigation.add(btPreviousClass);
                paNavigation.add(btNextClass);
                paNavigation.add(btNextStudent);
                
                paStudent.add(paNavigation, BorderLayout.SOUTH);
                                
        
            paMain.add(paStudent, BorderLayout.CENTER);
        this.add(paMain, BorderLayout.CENTER); 
        
        dba = DB_Access.getInstance();
            
        
        
    }
    
    private void onNextStudent(ActionEvent e){
        currentStudentId++;
        if(currentClassId <= 0 || currentClassId > Grade.grades.size()){
            currentClassId = 0;
        }
        if(currentStudentId >= Grade.grades.get(currentClassId).getStudents().size()){
            onNextClass(e);
        }else{
            showStudent();
        }
    }
    
    private void onNextClass(ActionEvent e){
        currentStudentId = 0;
        currentClassId = (currentClassId + 1) % Grade.grades.size();
        cbClasses.setSelectedIndex(currentClassId);
    }
    
    private void onPreviousStudent(ActionEvent e){
        currentStudentId--;
        if(currentClassId <= 0 || currentClassId > Grade.grades.size()){
            currentClassId = 0;
        }
        if(currentStudentId < 0){
            onPreviousClass(e);
        }else{
            showStudent();
        }
    }
    
    private void onPreviousClass(ActionEvent e){
        currentClassId--;
        if(currentClassId < 0){
            currentClassId = Grade.grades.size() - 1;
        }
        currentStudentId = Grade.grades.get(currentClassId).getStudents().size() - 1;
        last = true;
        cbClasses.setSelectedIndex(currentClassId);
    }
    
    private void onCancel(ActionEvent e){
        btNewAndSave.setText("Neu");
        newOrSave = 'n';
        tfDateOfBirth.setEditable(false);
        tfFirstname.setEditable(false);
        tfSurname.setEditable(false);
        tfGrade.setEditable(false);
        showStudent();
        btCancel.setEnabled(false);
    }
    
    private void onNewOrSave(ActionEvent e){
        if(newOrSave == 'n'){
            tfDateOfBirth.setEditable(true);
            tfFirstname.setEditable(true);
            tfSurname.setEditable(true);
            tfGrade.setEditable(true);
            newOrSave = 's';
            tfCatNr.setText("");
            tfAge.setText("");
            btNewAndSave.setText("Speichern");
            btCancel.setEnabled(true);
        }else{
            if(tfSurname.getText() == null || tfSurname.getText().length() <= 0 || tfSurname.getText().length() >= 40){
                JOptionPane.showMessageDialog(this, "Invalid or uncomplete surname.");
                return;
            }
            String surname = tfSurname.getText();
            if(tfFirstname.getText() == null || tfFirstname.getText().length() <= 0 || tfFirstname.getText().length() >= 40){
                JOptionPane.showMessageDialog(this, "Invalid or uncomplete firstname.");
                return;
            }
            
            String firstname = tfFirstname.getText();
            if(tfGrade.getText() == null || tfGrade.getText().length() <= 0 || tfGrade.getText().length() >= 10){
                JOptionPane.showMessageDialog(this, "Invalid or uncomplete Grade.");
                return;
            }
            String grade = tfGrade.getText();
            if(!Grade.getAllAvailableClasses().contains(tfGrade.getText().toUpperCase())){
                int result = JOptionPane.showConfirmDialog(this, "Do you want to add thisn new class?");
                if(result == JOptionPane.OK_OPTION){
                    try {
                        if(!dba.addNewGrade(tfGrade.getText())){
                            return;
                        }
                        dba.updateLocalGradeIDs();
                        cbm.removeAllElements();
                        cbm.addAll(Grade.getAllAvailableClasses());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        return;
                    }
                }else{
                    return;
                }
            }
            
            if(tfDateOfBirth.getText() == null || tfDateOfBirth.getText().length() <= 0 || tfDateOfBirth.getText().length() > 10){
                JOptionPane.showMessageDialog(this, "Invalid or uncomplete dateOfBirth.");
                return;
            }
            LocalDate dateOfBirth;
            try{
                dateOfBirth = LocalDate.parse(tfDateOfBirth.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy")); 
            }catch (DateTimeParseException ex){
                JOptionPane.showMessageDialog(this, "Wrong Date Format. (dd.MM.yyyy)");
                return;
            }
            int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
            
            tfAge.setText(Integer.toString(age));
            
            Student s = Student.of(firstname, surname, '-', dateOfBirth, grade);
            if(s == null){
                JOptionPane.showMessageDialog(this, "Student does already exist!");
                return;
            }
            try {
                dba.insertStudent(s);
                dba.getGradesFromDB();
                // get student from list (indices)
                for (int i = 0; i < Grade.grades.size(); i++) {
                    for (int j = 0; j < Grade.grades.get(i).getStudents().size(); j++) {
                        if(Grade.grades.get(i).getStudents().get(j).equals(s)){
                            currentClassId = i;
                            currentStudentId = j;
                            btNewAndSave.setText("Neu");
                            newOrSave = 'n';
                            tfDateOfBirth.setEditable(false);
                            tfFirstname.setEditable(false);
                            tfSurname.setEditable(false);
                            tfGrade.setEditable(false);
                            btCancel.setEnabled(false);
                            last = true;
                            cbClasses.setSelectedIndex(currentClassId);
                            return;
                        }
                    }   
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }   
        }
    }
    
    private void onExit(ActionEvent e){
        try {
            if(dba.isIsConnected()){
                dba.disconnect();
            }
            System.out.println(">>> Terminated Application");
            System.exit(0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void onExport(ActionEvent e){
        JFileChooser fc = new JFileChooser(Paths.get(System.getProperty("user.dir"), "src", "res").toFile());
        if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();
            if(!file.getName().equals("")){
                try {
                    dba.exportStudents(file.toPath());
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    private void onImport(ActionEvent e){
        JFileChooser fc = new JFileChooser(Paths.get(System.getProperty("user.dir"), "src", "res").toFile());
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();
            if(file != null && !file.getName().equals("")){
                try {
                    if(dba.importStudents(file.toPath())){
                        btExport.setEnabled(true);
                        btNextClass.setEnabled(true);
                        btNextStudent.setEnabled(true);
                        btPreviousClass.setEnabled(true);
                        btPreviousStudent.setEnabled(true);
                        
                        cbClasses.setEnabled(true);
                        currentClassId = 0;
                        currentStudentId = 0;
                        cbm.removeAllElements();
                        cbm.addAll(Grade.getAllAvailableClasses());
                        cbClasses.setSelectedIndex(0);
                        
                        showStudent();
                    }
                    
                } catch (FileNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                } catch(IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        }
        
    }
    
    private void onToggleConnection(ActionEvent e){
        if(!dba.isIsConnected()){
            try {
                dba.connect();
                dba.getGradesFromDB();
                btToggleConnect.setText("Disconnect");
                btExport.setEnabled(!Grade.grades.isEmpty());
                btImport.setEnabled(true);
                btNewAndSave.setEnabled(true);
                btNextClass.setEnabled(!Grade.grades.isEmpty());
                btNextStudent.setEnabled(!Grade.grades.isEmpty());
                btPreviousStudent.setEnabled(!Grade.grades.isEmpty());
                btPreviousClass.setEnabled(!Grade.grades.isEmpty());
                cbClasses.setEnabled(!Grade.grades.isEmpty());
                
                if(!Grade.grades.isEmpty()){
                    cbClasses.setEnabled(true);
                    currentClassId = 0;
                    currentStudentId = 0;
                    cbm.removeAllElements();
                    cbm.addAll(Grade.getAllAvailableClasses());
                    cbClasses.setSelectedIndex(0);
                    showStudent();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }else{
            try {
                dba.disconnect();
                btToggleConnect.setText("Connect");
                btExport.setEnabled(false);
                btImport.setEnabled(false);
                btNewAndSave.setEnabled(false);
                btNextClass.setEnabled(false);
                btNextStudent.setEnabled(false);
                btPreviousStudent.setEnabled(false);
                btPreviousClass.setEnabled(false);
                cbClasses.setEnabled(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private boolean showStudent(){
        if(currentClassId < Grade.grades.size()){
            Grade g = Grade.grades.get(currentClassId);
            if(currentStudentId < g.getStudents().size()){
                Student student = g.getStudents().get(currentStudentId);
                tfAge.setText(Integer.toString(Period.between(student.getDateOfBirth(), LocalDate.now()).getYears()));
                tfCatNr.setText(Integer.toString(student.getCatNo()));
                tfDateOfBirth.setText(student.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                tfFirstname.setText(student.getFirstname());
                tfSurname.setText(student.getSurname());
                tfGrade.setText(student.getGrade().getClassName());
                return true;
            }  
        }
        return false;
    }
    
    private void onSelectGrade(ActionEvent e){
        int index = cbClasses.getSelectedIndex();
        currentClassId = index >= 0 ? index : 0;
        if(!last){
            currentStudentId = 0;
        }else{
            last = false;
        }
        showStudent();
    }
    
    public static void main(String[] args) {
        new SchoolAdminGUI("SchoolDB").setVisible(true);
    }

}
