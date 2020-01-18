package jdbc_pool_study.ui.service;

import java.sql.SQLException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jdbc_pool_study.dao.DepartmentDao;
import jdbc_pool_study.dao.DepartmentDaoImpl;
import jdbc_pool_study.dao.EmployeeDao;
import jdbc_pool_study.dao.EmployeeDaoImpl;
import jdbc_pool_study.ds.MySqlDataSource;
import jdbc_pool_study.dto.Department;
import jdbc_pool_study.dto.Employee;
import jdbc_pool_study.util.LogUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransactionServiceTest {
	static TransactionService ts;
	static DepartmentDao deptDao;
	static EmployeeDao empDao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		ts = new TransactionService();
		deptDao = new DepartmentDaoImpl();
		empDao = new EmployeeDaoImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		ts = null;
		deptDao = null;
		empDao = null;
	}

	@Test
	public void test1TransAddEmpAndDept() throws SQLException {
		LogUtil.prnLog("TransAddEmpAndDept - Department fail");
		Department dept = new Department(1, "영업", 17);
		Employee emp = new Employee(1005, "이유영", "사원", new Employee(1003), 1500000, dept);

		int result = ts.transAddEmpAndDept(emp, dept);
		LogUtil.prnLog(String.format("result %d", result));

		Assert.assertEquals(0, result);
		listEmpDept();
	}
	
	@Test
	public void test2TransAddEmpAndDept() throws SQLException {
		LogUtil.prnLog("TransAddEmpAndDept - Employee fail");
		Department dept = new Department(5, "인사", 17);
		Employee emp = new Employee(1003, "이유영", "사원", new Employee(1003), 1500000, dept);

		int result = ts.transAddEmpAndDept(emp, dept);
		LogUtil.prnLog(String.format("result %d", result));

		Assert.assertEquals(1, result);
		listEmpDept();
	}

	@Test
	public void test3TransAddEmpAndDept() throws SQLException {
		LogUtil.prnLog("TransAddEmpAndDept - Department , Employee Success");
		Department dept = new Department(5, "인사", 17);
		Employee emp = new Employee(1005, "이유영", "사원", new Employee(1003), 1500000, dept);

		int result = ts.transAddEmpAndDept(emp, dept);
		LogUtil.prnLog(String.format("result %d", result));

		Assert.assertEquals(2, result);
		listEmpDept();
		removeEmpDept(emp, dept);
	}

	@Test
	public void test4TransAddEmpAndDeptWithConnection() throws SQLException {
		LogUtil.prnLog("TransAddEmpAndDeptWithConnection - Department fail");
		Department dept = new Department(1, "영업", 17);
		Employee emp = new Employee(1005, "이유영", "사원", new Employee(1003), 1500000, dept);

		int result = ts.transAddEmpAndDeptWithConnection(emp, dept);
		LogUtil.prnLog(String.format("result %d", result));

		Assert.assertEquals(0, result);
		listEmpDept();
	}
	
	@Test
	public void test5TransAddEmpAndDeptWithConnection() throws SQLException {
		LogUtil.prnLog("TransAddEmpAndDeptWithConnection - Employee fail");
		Department dept = new Department(5, "인사", 17);
		Employee emp = new Employee(1003, "이유영", "사원", new Employee(1003), 1500000, dept);

		int result = ts.transAddEmpAndDeptWithConnection(emp, dept);
		LogUtil.prnLog(String.format("result %d", result));

		Assert.assertEquals(1, result);
		listEmpDept();
	}
	
	@Test
	public void test6TransAddEmpAndDeptWithConnection() throws SQLException {
		LogUtil.prnLog("TransAddEmpAndDeptWithConnection - Department , Employee Success");
		Department dept = new Department(5, "인사", 17);
		Employee emp = new Employee(1005, "이유영", "사원", new Employee(1003), 1500000, dept);

		int result = ts.transAddEmpAndDeptWithConnection(emp, dept);
		LogUtil.prnLog(String.format("result %d", result));

		Assert.assertEquals(2, result);

		listEmpDept();
		removeEmpDept(emp, dept);
	}


	private void listEmpDept() throws SQLException {
		List<Department> departments = deptDao.selectDepartmentByAll();
		for (Department d : departments) {
			LogUtil.prnLog(d);
		}
		List<Employee> lists;
		lists = empDao.selectEmployeeByAll(MySqlDataSource.getConnection());
		for(Employee emp : lists) {
			LogUtil.prnLog(emp);
		}
	}
	
	private void removeEmpDept(Employee emp, Department dept) {
		try {
			empDao.deleteEmployee(MySqlDataSource.getConnection(), emp);
			deptDao.deleteDepartment(dept.getDeptNo());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
