/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.Employee;
import gui.EmployeeGUI;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Elias Wilfinger
 */
public class EmployeeModel extends AbstractTableModel{
    private List<Employee> employees = new ArrayList<>();
    EmployeeGUI empGUI;

    public EmployeeModel(EmployeeGUI e) {
        this.empGUI = e;
    }
    
    
    
    public void setEmployeesForDepartment(List<Employee> employees){
        this.employees.clear();
        this.employees.addAll(employees);
    }

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        return switch(column){
            case 0 -> "Name";
            case 1 -> "Gender";
            case 2 -> "Birthdate";
            case 3 -> "Hiredate";
            default -> "error";
        };
    }
    
    
    /**
     * 
     * @param arg0 row index
     * @param arg1 column index
     * @return 
     */
    @Override
    public Object getValueAt(int arg0, int arg1) {
        Employee e = employees.get(arg0);
        
        return switch (arg1) {
            case 0 -> e.getLast_name() + ", " + e.getFirst_name();
            case 1 -> Character.toString(e.getGender()).toUpperCase();
            case 2 -> e.getBirth_date().format(Employee.DTF);
            case 3 -> e.getHire_date().format(Employee.DTF);
            default -> "error";
        };
        
    }

    /**
     * 
     * @param aValue either name or hiredate
     * @param rowIndex
     * @param columnIndex
     * @throws DateTimeParseException 
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) throws DateTimeParseException{
        boolean success = false;
        Employee oldEmployee = null;
        try {
            oldEmployee = (Employee)employees.get(rowIndex).clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        switch(columnIndex){
            case 0 -> {
                String name = aValue.toString();
                String splitter = name.contains(",") ? name.contains(" ") ? ",[\\s]" : "," : "[\\s]";
                employees.get(rowIndex).setLast_name(name.split(splitter)[0]);
                employees.get(rowIndex).setFirst_name(name.split(splitter)[1]);
                success = true;
            }
            case 3 -> {
                String date = aValue.toString();
                if(date != null && !date.isBlank()){
                    try{
                        LocalDate hiredate = LocalDate.parse(date, Employee.DTF);
                        employees.get(rowIndex).setHire_date(hiredate);  
                        success = true;
                    }catch(DateTimeParseException ex){
                        JOptionPane.showMessageDialog(empGUI, "Wrong Date Format!\nPlease use: dd.MM.yyyy", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            default -> System.out.println(">>> Error: EmployeeModel::setValueAt, this message should never appear!");      
        }
        
        if(success){
            try {
                empGUI.getDB_Access().updateEmployee(employees.get(rowIndex), oldEmployee);
                fireTableRowsUpdated(0, employees.size()-1);
                employees.sort(Comparator.comparing(Employee::getLast_name)
                        .thenComparing(Employee::getFirst_name)
                        .thenComparing(Employee::getBirth_date));
            } catch (FileNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex == 0 || columnIndex == 3);
    }    
}
