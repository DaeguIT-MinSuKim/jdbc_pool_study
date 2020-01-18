package jdbc_pool_study.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import jdbc_pool_study.dao.EmployeeDao;
import jdbc_pool_study.dao.EmployeeDaoImpl;
import jdbc_pool_study.ds.MySqlDataSource;
import jdbc_pool_study.dto.Employee;
import jdbc_pool_study.exception.NotItemSelectedException;
import jdbc_pool_study.ui.table.EmployeeTablePanel;

@SuppressWarnings("serial")
public class EmployeeUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private EmployeeDao empDao;
	private JButton btnAdd;
	private EmployeeTablePanel pEmpList;
//	private int selectedRowIdx;
	private Connection con;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeUI frame = new EmployeeUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EmployeeUI() {
		try {
			con = MySqlDataSource.getConnection();
			empDao = new EmployeeDaoImpl();
			
			initComponents();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	private void initComponents() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		pEmpList = new EmployeeTablePanel("사원");
		pEmpList.loadData(empDao.selectEmployeeByAll(con));
		pEmpList.setPopupMenu(createPopupMenu());
		contentPane.add(pEmpList);
	}

	private JPopupMenu createPopupMenu() {
		JPopupMenu popMenu = new JPopupMenu();
		JMenuItem deleteItem = new JMenuItem("삭제");
		deleteItem.addActionListener(menuActionListener);
		popMenu.add(deleteItem);
		
		JMenuItem updateItem = new JMenuItem("수정");
		updateItem.addActionListener(menuActionListener);
		popMenu.add(updateItem);
		
		return popMenu;
	}

	ActionListener menuActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().contentEquals("삭제")) {
				try {
					Employee emp = pEmpList.getSelectedItem();
					empDao.deleteEmployee(con, emp);
					pEmpList.removeRow();
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (NotItemSelectedException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
			
			if (e.getActionCommand().contentEquals("수정")) {
				Employee emp = pEmpList.getSelectedItem();
				System.out.println(emp);
				//TO-Do
			}
		}
	};
	
	private JButton btnCancel;

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			actionPerformedBtnCancel(e);
		}
		if (e.getSource() == btnAdd) {
			if (e.getActionCommand().contentEquals("추가")) {
				actionPerformedBtnAdd(e);
			}else {
				actionPerformedBtnUpdate(e);
			}
		}
	}

	private void actionPerformedBtnUpdate(ActionEvent e){
//		Department dept = pDept.getItem();
//		try {
//			service.modifyDepartment(dept);
//			pEmpList.updateRow(dept, selectedRowIdx);
//			btnAdd.setText("추가");
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		pDept.clearComponent(-1);
	}

	protected void actionPerformedBtnAdd(ActionEvent e)  {
//		Department dept = pDept.getItem();
//		try {
//			service.addDepartment(dept);
//			pEmpList.addRow(dept);
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//			JOptionPane.showMessageDialog(null, e1.getMessage());
//		} 
//		pDept.clearComponent(-1);
	}
	
	protected void actionPerformedBtnCancel(ActionEvent e) {
//		pDept.clearComponent(-1);
	}
}
