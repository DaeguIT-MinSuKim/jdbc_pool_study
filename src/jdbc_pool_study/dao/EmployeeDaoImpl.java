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
		String sql = "delete from employee where empno = ?";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, employee.getEmpNo());
			LOG.trace(pstmt);
			return pstmt.executeUpdate();
		} 
	}

	@Override
	public int insertEmployee(Connection con, Employee employee) throws SQLException {
		String sql;
		if (employee.getPic()==null) {
			sql = "insert into employee(empno, empname, title, manager, salary, dno) values (?, ?, ?, ?, ?, ?)";
		}else {
			sql = "insert into employee values(?, ?, ?, ?, ?, ?, ?)";
		}
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, employee.getEmpNo());
			pstmt.setString(2, employee.getEmpName());
			pstmt.setString(3, employee.getTitle());
			pstmt.setInt(4, employee.getManager().getEmpNo());
			pstmt.setInt(5, employee.getSalary());
			pstmt.setInt(6, employee.getDept().getDeptNo());
			LOG.trace(pstmt);
			if (employee.getPic()!=null) {
				pstmt.setBytes(7, employee.getPic());
			}
			return pstmt.executeUpdate();
		}
	}

	@Override
	public int updateEmployee(Connection con, Employee employee) throws SQLException {
		String sql = "update employee set empname=?, title=?, manager=?, salary=?, dno=?, pic=? "
				+ "where empno=?";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, employee.getEmpName());
			pstmt.setString(2, employee.getTitle());
			pstmt.setInt(3, employee.getManager().getEmpNo());
			pstmt.setInt(4, employee.getSalary());
			pstmt.setInt(5, employee.getDept().getDeptNo());
			pstmt.setInt(7, employee.getEmpNo());
			LOG.trace(pstmt);
			pstmt.setBytes(6, employee.getPic());
			return pstmt.executeUpdate();
		}
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
		String sql = "select empno, empname, title, manager, salary, dno, pic "
				+ "from employee "
				+ "where empno=?";
		try (PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, employee.getEmpNo());
			try(ResultSet rs = pstmt.executeQuery()){
				LOG.trace(pstmt);
				if (rs.next())
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

		Employee emp = new Employee(empNo, empName, title, manager, salary, dept);
		if (isFull) {
			byte[] pic = rs.getBytes("pic");
			emp.setPic(pic);
		}
		return emp;	
	}
}
