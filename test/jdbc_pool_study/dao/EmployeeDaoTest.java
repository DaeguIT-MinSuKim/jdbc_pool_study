package jdbc_pool_study.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import jdbc_pool_study.dto.Employee;
import jdbc_pool_study.util.LogUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDaoTest {
	protected static Logger logger = LogManager.getLogger();
	static EmployeeDao dao;
	static File picsDir;
	static File imagesDir;
	static Connection con;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		dao = new EmployeeDaoImpl();
		con = MySqlDataSource.getConnection();
		picsDir = new File(System.getProperty("user.dir")+ System.getProperty("file.separator") + "pics" + System.getProperty("file.separator"));
		if (!picsDir.exists()) {
			picsDir.mkdir();
		}
		imagesDir = new File(System.getProperty("user.dir")+ System.getProperty("file.separator") + "images" + System.getProperty("file.separator"));
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		logger.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		dao = null;
		con.close();
	}

	@Test
	public void test05DeleteEmployee() throws SQLException {
		Employee emp = new Employee(1004);
		int res = dao.deleteEmployee(con, emp);
		logger.trace(res);
		Assert.assertEquals(1, res);
	}

	@Test
	public void test02InsertEmployee() {
		int result;
		try {
			Employee emp = new Employee(1004, "서현진", "사원", new Employee(1003), 1500000, new Department(1), getImage("seohyunjin.jpg"));
			result = dao.insertEmployee(con, emp);
			logger.trace(result);
			Assert.assertEquals(1, result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test04UpdateEmployee() {
		Employee emp = new Employee(1004, "이유영", "대리", new Employee(3426), 3500000, new Department(1));
		try {
			emp.setPic(getImage("leeyouyoung.jpg"));
			int res = dao.updateEmployee(con, emp);
			logger.trace(res);
			Assert.assertEquals(1, res);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test01SelectEmployeeByAll(){
		List<Employee> lists;
		try {
			lists = dao.selectEmployeeByAll(con);
			Assert.assertNotEquals(0, lists.size());
			logger.trace(String.format("lists size = %d", lists.size()));
			for(Employee emp : lists) {
				logger.trace(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 

	@Test
	public void test06ProcedureEmployeeByDeptNoWithCursor() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		List<Employee> lists;
		try {
			lists = dao.procedureEmployeeByDeptNoWithCursor(con, 1);
			Assert.assertNotEquals(0, lists.size());
			logger.trace(String.format("lists size = %d", lists.size()));
			for(Employee emp : lists) {
				logger.trace(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test07ProcedureEmployeeByDeptNo() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		List<Employee> lists;
		try {
			lists = dao.procedureEmployeeByDeptNo(con, 1);
			Assert.assertNotEquals(0, lists.size());
			logger.trace(String.format("lists size = %d", lists.size()));
			for(Employee emp : lists) {
				logger.trace(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test03SelectEmployeeByNo() {
		Employee emp;
		try {
			emp = dao.selectEmployeeByNo(con, new Employee(1004));
			getOrWritePicFile(emp);
			Assert.assertNotNull(emp);
			logger.trace(emp) ;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private File getOrWritePicFile(Employee emp) throws IOException, FileNotFoundException {
		File file = null;
		file = new File(picsDir, emp.getEmpNo() + ".jpg");
		try (FileOutputStream output = new FileOutputStream(file)) {
			output.write(emp.getPic());
		}
		return file;
	}

	public byte[] getImage(String imgName) throws FileNotFoundException, IOException {
		byte[] pic = null;
		File file = new File(imagesDir, imgName);
		try (InputStream is = new FileInputStream(file)){
			pic = new byte[is.available()];
			is.read(pic);
		}
		System.out.println("pic.length : " + pic.length);
		return pic;
	}

}
