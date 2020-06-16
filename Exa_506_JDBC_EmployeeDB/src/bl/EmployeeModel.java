/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.Employee;
import database.DB_Access;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Elias Wilfinger
 */
public class EmployeeModel extends AbstractTableModel{
    private List<Employee> employees = new ArrayList<>();
    
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

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("TODO: EmployeeModel");
//        super.setValueAt(aValue, rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        System.out.println("TODO: EmployeeModel::isCellEditable");
        return false;
//        throw new UnsupportedOperationException("TODO: EmployeeModel::isCellEditable");
//        return super.isCellEditable(rowIndex, columnIndex); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    
    
}
