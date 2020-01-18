package jdbc_pool_study.ui.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jdbc_pool_study.dao.DepartmentDao;
import jdbc_pool_study.dao.DepartmentDaoImpl;
import jdbc_pool_study.dao.EmployeeDao;
import jdbc_pool_study.dao.EmployeeDaoImpl;
import jdbc_pool_study.ds.MySqlDataSource;
import jdbc_pool_study.dto.Department;
import jdbc_pool_study.dto.Employee;
import jdbc_pool_study.util.LogUtil;

public class TransactionService {

	public int transAddEmpAndDept(Employee emp, Department dept) {
		LogUtil.prnLog("transactionInsertEmployeeAndDepartment()");
		String deptSql = "insert into department values(?, ?, ?)";
		String empSql = "insert into employee(empno, empname, title, manager, salary, dno) values(?, ?, ?, ?, ?, ?)";
		PreparedStatement deptPstmt = null;
		PreparedStatement empPstmt = null;

		int result = 0;
		Connection con = null;
		try {
			con = MySqlDataSource.getConnection();
			con.setAutoCommit(false);

			deptPstmt = con.prepareStatement(deptSql);
			deptPstmt.setInt(1, dept.getDeptNo());
			deptPstmt.setString(2, dept.getDeptName());
			deptPstmt.setInt(3, dept.getFloor());
			LogUtil.prnLog(deptPstmt);
			result += deptPstmt.executeUpdate();
			empPstmt = con.prepareStatement(empSql);
			empPstmt.setInt(1, emp.getEmpNo());
			empPstmt.setString(2, emp.getEmpName());
			empPstmt.setString(3, emp.getTitle());
			empPstmt.setInt(4, emp.getManager().getEmpNo());
			empPstmt.setInt(5, emp.getSalary());
			empPstmt.setInt(6, emp.getDept().getDeptNo());
			LogUtil.prnLog(empPstmt);
			result += empPstmt.executeUpdate();

			if (result == 2) {
				con.commit();
				con.setAutoCommit(true);
				LogUtil.prnLog("result " + result + " commit()");
			} else {
				throw new SQLException();
			}
		} catch (SQLException e) {
			try {
				con.rollback();
				con.setAutoCommit(true);
				LogUtil.prnLog("result " + result + " rollback()");
			} catch (SQLException ex) {
				ex.printStackTrace();
				LogUtil.prnLog(ex.getMessage());
			}
		} finally {
			try {
				if (empPstmt != null)
					empPstmt.close();
				if (deptPstmt != null)
					deptPstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException ex) {
				LogUtil.prnLog(ex.getMessage());
			}
		}
		return result;
	}

	private DepartmentDao deptDao;
	private EmployeeDao empDao;
	
	public TransactionService() {
		deptDao = new DepartmentDaoImpl();
		empDao = new EmployeeDaoImpl();
	}

	public int transAddEmpAndDeptWithConnection(Employee emp, Department dept) {
		int result = 0;
		Connection con = null;
		try {
			con = MySqlDataSource.getConnection();
			con.setAutoCommit(false);
			result = deptDao.insertDepartmentWithConnection(con, dept);
			result += empDao.insertEmployee(con, emp);
			if (result == 2) {
				con.commit();
				con.setAutoCommit(true);
				LogUtil.prnLog("result " + result + " commit()");
			} else {
				throw new SQLException();
			}
		} catch (SQLException e) {
			try {
				con.rollback();
				con.setAutoCommit(true);
				LogUtil.prnLog("result " + result + " rollback()");
			} catch (SQLException ex) {
				LogUtil.prnLog(ex.getMessage());
			}
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException ex) {
				LogUtil.prnLog(ex.getMessage());
			}
		}
		return result;
	}
}
