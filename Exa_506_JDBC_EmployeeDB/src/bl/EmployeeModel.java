/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Elias Wilfinger
 */
public class EmployeeModel extends AbstractTableModel{

    @Override
    public int getRowCount() {
        System.out.println("TODO: EmployeeModel");
        return 100;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getColumnCount() {
        System.out.println("TODO: EmployeeModel");
        return 10;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {
        System.out.println("TODO: EmployeeModel");
        return "not supported Yet (table Model)";
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
