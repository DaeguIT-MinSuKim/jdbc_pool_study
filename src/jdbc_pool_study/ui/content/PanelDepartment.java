package jdbc_pool_study.ui.content;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import jdbc_pool_study.dto.Department;

@SuppressWarnings("serial")
public class PanelDepartment extends AbstractPanel<Department> {
	private JTextField tfDeptNo;
	private JTextField tfDeptName;
	private JTextField tfFloor;

	public PanelDepartment(String title) {
		super(title);
	}

	@Override
	protected void initComponents(String title) {
		setBorder(new TitledBorder(null, title + " 정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(0, 2, 10, 10));

		JLabel lblDeptNo = new JLabel("부서 번호");
		lblDeptNo.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblDeptNo);

		tfDeptNo = new JTextField();
		add(tfDeptNo);
		tfDeptNo.setColumns(10);

		JLabel lblDeptName = new JLabel("부서명");
		lblDeptName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblDeptName);

		tfDeptName = new JTextField();
		tfDeptName.setColumns(10);
		add(tfDeptName);
		
		JLabel lblFloor = new JLabel("위치(층)");
		lblFloor.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblFloor);
		
		tfFloor = new JTextField();
		tfFloor.setColumns(10);
		add(tfFloor);
	}

	public void setItem(Department dept) {
		tfDeptNo.setText(String.format("%03d", dept.getDeptNo()));
		tfDeptName.setText(dept.getDeptName());
		tfFloor.setText(dept.getFloor()+"");
		tfDeptNo.setEditable(false);
	}

	public Department getItem() {
		int deptNo = Integer.parseInt(tfDeptNo.getText().trim());
		String deptName = tfDeptName.getText().trim();
		int floor = Integer.parseInt(tfFloor.getText().trim());
		return new Department(deptNo, deptName, floor);
	}

	public void clearComponent(int nextNo) {
		if (nextNo == -1) {
			tfDeptNo.setText("");
		}else {
			tfDeptNo.setText(String.format("%03d", nextNo));
		}
		tfDeptName.setText("");
		tfFloor.setText("");
		tfDeptNo.setEditable(true);
	}

}
