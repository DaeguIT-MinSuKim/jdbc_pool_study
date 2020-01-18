package jdbc_pool_study.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import jdbc_pool_study.dto.Department;
import jdbc_pool_study.exception.NotItemSelectedException;
import jdbc_pool_study.ui.content.PanelDepartment;
import jdbc_pool_study.ui.service.DepartmentService;
import jdbc_pool_study.ui.table.DepartmentTablePanel;

@SuppressWarnings("serial")
public class DepartmentUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private DepartmentService service;
	private JButton btnAdd;
	private PanelDepartment pDept;
	private DepartmentTablePanel pDeptList;
	private int selectedRowIdx;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DepartmentUI frame = new DepartmentUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DepartmentUI() {
		service = new DepartmentService();
		initComponents();
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		pDept = new PanelDepartment("부서");
		contentPane.add(pDept);

		JPanel pBtns = new JPanel();
		contentPane.add(pBtns);

		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtns.add(btnAdd);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		pBtns.add(btnCancel);

		pDeptList = new DepartmentTablePanel();
		pDeptList.loadData(service.listDeparments());
		pDeptList.setPopupMenu(createPopupMenu());
		contentPane.add(pDeptList);
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
					Department dept = pDeptList.getSelectedItem();
					service.removeDepartment(dept);
					pDeptList.removeRow();
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (NotItemSelectedException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
			
			if (e.getActionCommand().contentEquals("수정")) {
				Department dept = pDeptList.getSelectedItem();
				selectedRowIdx = pDeptList.getSelectedRowIndex();
				pDept.setItem(dept);
				btnAdd.setText("수정");
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
		Department dept = pDept.getItem();
		try {
			service.modifyDepartment(dept);
			pDeptList.updateRow(dept, selectedRowIdx);
			btnAdd.setText("추가");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		pDept.clearComponent(-1);
	}

	protected void actionPerformedBtnAdd(ActionEvent e)  {
		Department dept = pDept.getItem();
		try {
			service.addDepartment(dept);
			pDeptList.addRow(dept);
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, e1.getMessage());
		} 
		pDept.clearComponent(-1);
	}
	
	protected void actionPerformedBtnCancel(ActionEvent e) {
		pDept.clearComponent(-1);
	}
}
