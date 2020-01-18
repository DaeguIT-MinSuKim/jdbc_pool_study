
package jdbc_pool_study.ui.table;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import jdbc_pool_study.dto.Department;
import jdbc_pool_study.exception.NotItemSelectedException;

@SuppressWarnings("serial")
public class DepartmentTablePanel extends JPanel {
	private JTable table;
	private Object[] colNames;
	private JScrollPane scrollPane;
	private NonEditableModel model;
	
	public DepartmentTablePanel() {
		initComponents();
	}

	private void initComponents() {
		setBorder(new TitledBorder(null, "부서", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		scrollPane.setViewportView(table);
	}
	
	protected void setAlignWith() {
		tableCellAlignment(SwingConstants.CENTER, 0, 1, 2);
        tableSetWidth(100, 200, 100);            		
	}

	protected void tableCellAlignment(int align, int... idx) {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(align);

		TableColumnModel model = table.getColumnModel();
		for (int i = 0; i < idx.length; i++) {
			model.getColumn(idx[i]).setCellRenderer(dtcr);
		}
	}

	protected void tableSetWidth(int... width) {
		TableColumnModel cModel = table.getColumnModel();
		for (int i = 0; i < width.length; i++) {
			cModel.getColumn(i).setPreferredWidth(width[i]);
		}
	}
	
	public void loadData(List<Department> items) {
		colNames = new String[] { "부서 코드", "부서 명", "위치"};
		Object[][] rows = new Object[items.size()][];
		for (int i = 0; i < items.size(); i++) {
			rows[i] = toArray(items.get(i));
		}
		
		model = new NonEditableModel(rows, colNames);
		table.setModel(model);
		setAlignWith();
	}
	
	public void setPopupMenu(JPopupMenu popUpMenu) {
		scrollPane.setComponentPopupMenu(popUpMenu);
		table.setComponentPopupMenu(popUpMenu);
	}

	public Department getSelectedItem(){
		int row = getSelectedRowIndex();
		int deptNo=	(int) model.getValueAt(row, 0);
		String deptName = (String) model.getValueAt(row, 1);
		int floor = (int) model.getValueAt(row, 2);
		return new Department(deptNo, deptName, floor);
	}

	public int getSelectedRowIndex(){
		int row = table.getSelectedRow();
		if (row==-1) throw new NotItemSelectedException();
		return row;
	}
	
	public void updateRow(Department item, int row) throws SQLException {
		model.setValueAt(item.getDeptNo(), row, 0);
		model.setValueAt(item.getDeptName(), row, 1);
		model.setValueAt(item.getFloor(), row, 2);
	}

	public void addRow(Department item) throws SQLException {
		model.addRow(toArray(item));
	}

	//새로운 방법
	public void removeRow() throws SQLException{
		model.removeRow(getSelectedRowIndex());
	}

	protected Object[] toArray(Department item) {
		return new Object[] {item.getDeptNo(), item.getDeptName(), item.getFloor()};
	}
	
	protected class NonEditableModel extends DefaultTableModel {
		
		public NonEditableModel(Object[][] data, Object[] columnNames) {
			super(data, columnNames);
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
		
	}
	
}
