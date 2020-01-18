package jdbc_pool_study.ui.table;

import javax.swing.SwingConstants;

import jdbc_pool_study.dto.Department;
import jdbc_pool_study.dto.Employee;

@SuppressWarnings("serial")
public class EmployeeTablePanel extends AbstractTablePanel<Employee> {

	public EmployeeTablePanel(String title) {
		super(title);
	}

	@Override
	protected void setAlignWith() {
		// 사원번호(0), 사원명(1), 직책(2), 관리자(3), 급여(5), 부서(6)
		tableCellAlignment(SwingConstants.CENTER, 0, 1, 2, 3, 5);
		tableCellAlignment(SwingConstants.RIGHT, 4);

		// 직책번호, 직책명의 폭을 (100, 200)으로 가능하면 설정
		tableSetWidth(100, 150, 50, 100, 150, 100);
	}

	@Override
	protected void setColumnNames() {
		colNames = new String[] { "사원번호", "사원명", "직책", "관리자", "급여", "부서" };
	}

	@Override
	public Employee getSelectedItem() {
		int row = getSelectedRowIndex();
		int empNo = (int) model.getValueAt(row, 0);
		String empName = (String) model.getValueAt(row, 1);
		String title = (String) model.getValueAt(row, 2);
		int managerNo = (int) model.getValueAt(row, 3);
		Employee manager = new Employee(managerNo);
		int salary =  Integer.parseInt(((String) model.getValueAt(row, 4)).replaceAll(",", ""));
		int deptNo = (int) model.getValueAt(row, 5);
		Department dept = new Department(deptNo);
		return new Employee(empNo, empName, title, manager, salary, dept);
	}

	@Override
	public void updateRow(Employee item, int row) {
		model.setValueAt(item.getEmpNo(), row, 0);
		model.setValueAt(item.getEmpName(), row, 1);
		model.setValueAt(item.getTitle(), row, 2);
		model.setValueAt(item.getManager().getEmpNo(), row, 3);
		model.setValueAt(String.format("%,d", item.getSalary()), row, 4);
		model.setValueAt(item.getDept().getDeptNo(), row, 5);
	}

	@Override
	protected Object[] toArray(Employee item) {
		return new Object[] {item.getEmpNo(), item.getEmpName(), item.getTitle(),
				item.getManager().getEmpNo(), String.format("%,d", item.getSalary()), item.getDept().getDeptNo()};
	}
	
}
