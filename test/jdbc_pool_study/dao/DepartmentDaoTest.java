package jdbc_pool_study.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jdbc_pool_study.ds.MySqlDataSource;
import jdbc_pool_study.dto.Department;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentDaoTest {
	protected static Logger logger = LogManager.getLogger();

	private static DepartmentDao dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = new DepartmentDaoImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dao = null;
	}

	@Test
	public void test01SelectDepartmentByAll() {
		List<Department> departments = dao.selectDepartmentByAll();
		Assert.assertNotEquals(0, departments.size());
		for (Department d : departments) {
			logger.trace(d);
		}
	}
 
	@Test
	public void test02SelectDepartmentByNo() throws SQLException {
		Department devDept = new Department(3);
		Department selectedDepartment = dao.selectDepartmentByNo(devDept);
		Assert.assertNotNull(selectedDepartment);
		logger.trace(selectedDepartment);
	}

	@Test
	public void test03InsertDepartment() {
		try {
			Department newDept = new Department(10, "총무", 15);
			int id = dao.insertDepartment(newDept);
			logger.trace(String.format("A new Department with id %d has been inserted.", id));
			Assert.assertEquals(1, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test04updateDepartment() throws SQLException {
		Department beforeDept = dao.selectDepartmentByNo(new Department(2));
		
		Department updateDept = new Department(2, "경영", 10);
		int rowAffected = dao.updateDepartment(updateDept);
		logger.trace(String.format("A Department with id %d has been updated.", rowAffected));
		Assert.assertEquals(1, rowAffected);
		
		Department selectedDepartment = dao.selectDepartmentByNo(updateDept);
		logger.trace(String.format("amend %s to %s", beforeDept, selectedDepartment));
	}

	@Test
	public void test05DeleteDepartment() throws SQLException {
		int delDeptNo = 10;
		int id = dao.deleteDepartment(delDeptNo);
		logger.trace(String.format("A Department with id %d has been deleted.", id));
		Assert.assertEquals(1, id);
	}
	
	@Test
	public void test06insertDepartmentWithConnection() {
		try {
			Department newDept = new Department(10, "총무", 15);
			Connection con = MySqlDataSource.getConnection();
			int id = dao.insertDepartmentWithConnection(con, newDept);
			logger.trace(String.format("A new Department with id %d has been inserted.", id));
			Assert.assertEquals(1, id);
			
			dao.deleteDepartment(newDept.getDeptNo());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
