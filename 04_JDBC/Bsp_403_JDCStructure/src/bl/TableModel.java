/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bl;

import beans.Student;
import database.DB_Access;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author SF <htlkaindorf.at>
 */
public class TableModel extends AbstractTableModel {
  private DB_Access dba = DB_Access.getInstance();
  private List<Student> students;
  private String[] colNames = {"cat-no", "name", "date of birth"};

  public TableModel() throws SQLException {
    students = dba.getAllStudents();
  }
  
  @Override
  public int getRowCount() {
    return students.size();
  }

  @Override
  public int getColumnCount() {
    return colNames.length;
  }

  @Override
  public String getColumnName(int column) {
    return colNames[column];
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Student student = students.get(rowIndex);
    switch (columnIndex) {
      case 0: return student.getCatNo();
      case 1: return String.format("%s, %s", student.getLastname(), student.getFirstname());
      case 2: return student.getDateOfBirth();
    }
    return null;
  }
  
}
