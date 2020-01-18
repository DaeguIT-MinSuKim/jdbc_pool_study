package jdbc_pool_study.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdbc_pool_study.dto.Department;
import jdbc_pool_study.dto.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
	final Logger LOG = LogManager.getLogger();
	
	@Override
	public int deleteEmployee(Connection con, Employee employee) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertEmployee(Connection con, Employee employee) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEmployee(Connection con, Employee employee) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Employee> selectEmployeeByAll(Connection con) throws SQLException {
		List<Employee> lists = new ArrayList<>();
		String sql = "select empno, empname, title, manager, salary, dno from employee";
		try (PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			LOG.trace(pstmt);
			while (rs.next()) {
				lists.add(getEmployee(rs, false));
			}
		} 
		return lists;
	}

	@Override
	public Employee selectEmployeeByNo(Connection con, Employee employee) throws SQLException {
		Employee selectedEmployee = null;
		String sql = "select empno, empname, title, manager, salary, dno, pic from employee where empno=?";
		try (PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			LOG.trace(pstmt);
			while (rs.next()) {
				selectedEmployee = getEmployee(rs, true);
			}
		}
		return selectedEmployee;
	}

	private Employee getEmployee(ResultSet rs, boolean isFull) throws SQLException {
		int empNo = rs.getInt("empno");
		String empName = rs.getString("empname");
		String title = rs.getString("title");
		Employee manager = new Employee(rs.getInt("manager"));
		int salary = rs.getInt("salary");
		Department dept = new Department(rs.getInt("dno"));

		if (isFull) {
			byte[] pic = rs.getBytes("pic");
			return new Employee(empNo, empName, title, manager, salary, dept, pic);
		}else {
			return new Employee(empNo, empName, title, manager, salary, dept);
		}
			
	}
}
