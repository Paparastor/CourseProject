package ui;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private int rowCount;
	private int columnCount;
	private ArrayList<String[]> values;
	private String[] columnsNames;

	public MyTableModel(ResultSet data) { 
		try {
			ResultSetMetaData metaData = data.getMetaData();
			columnCount = metaData.getColumnCount();
			columnsNames = new String[columnCount];
			for (int i=0; i<columnCount;i++){
				columnsNames[i] = metaData.getColumnName(i+1);
			}
			ArrayList<String[]> values = new ArrayList<String[]>();

			int i = 0;
			while (data.next()) {
				String[] tempValues = new String[columnCount];
				for (int j = 0; j < columnCount; j++) {
					tempValues[j] = data.getString(j + 1);
				}
				values.add(tempValues);
				i++;
			}
			rowCount = i;
			this.values =  values;
		} catch (SQLException e) {
			System.out.println("Error:constructor error.");
			this.values = null;
		}
	}
	
	@Override
	public String getColumnName(int column) { return columnsNames[column]; }
	
	@Override
	public int getRowCount() { return rowCount; }

	@Override
	public int getColumnCount() { return columnCount; }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) { return values.get(rowIndex)[columnIndex]; }

}
