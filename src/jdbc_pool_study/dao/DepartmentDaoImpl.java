package jdbc_pool_study.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdbc_pool_study.ds.MySqlDataSource;
import jdbc_pool_study.dto.Department;

public class DepartmentDaoImpl implements DepartmentDao {
	final Logger LOG = LogManager.getLogger();

	@Override
	public List<Department> selectDepartmentByAll() {
		List<Department> lists = new ArrayList<>();
		String sql = "SELECT deptno, deptname, floor from department";
		try (Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			LOG.trace(pstmt);
			while (rs.next()) {
				lists.add(getDepartment(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lists;
	}

	private Department getDepartment(ResultSet rs) throws SQLException {
		int deptNo = rs.getInt("deptno");
		String deptName = rs.getString("deptname");
		int floor = rs.getInt("floor");
		return new Department(deptNo, deptName, floor);
	}

	@Override
	public Department selectDepartmentByNo(Department department) throws SQLException {
		String sql = "select deptno, deptname, floor from department where deptno=?";
		Department selectedDepartment = null;
		try (Connection con = MySqlDataSource.getConnection(); 
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, department.getDeptNo());

			try (ResultSet rs = pstmt.executeQuery()) {
				LOG.trace(pstmt);
				if (rs.next()) {
					selectedDepartment = getDepartment(rs);
				}
			}

		}
		return selectedDepartment;
	}

	@Override
	public int insertDepartment(Department department) throws SQLException {
		String sql = "insert into department values(?, ?, ?)";
		int rowAffected = 0;
		try (Connection conn = MySqlDataSource.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, department.getDeptNo());
			pstmt.setString(2, department.getDeptName());
			pstmt.setInt(3, department.getFloor());
			LOG.trace(pstmt);
			rowAffected = pstmt.executeUpdate();
		}
		return rowAffected;

	}

	@Override
	public int updateDepartment(Department department) throws SQLException {
		String sql = "update department set deptname=?, floor=? where deptno=?";
		int rowAffected = 0;
		try (Connection conn = MySqlDataSource.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, department.getDeptName());
			pstmt.setInt(2, department.getFloor());
			pstmt.setInt(3, department.getDeptNo());
			LOG.trace(pstmt);
			rowAffected = pstmt.executeUpdate();
		}
		return rowAffected;
	}

	@Override
	public int deleteDepartment(int deptNo) throws SQLException {
		String sql = "delete from department where deptno=?";
		int rowAffected = 0;
		try (Connection conn = MySqlDataSource.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, deptNo);
			LOG.trace(pstmt);
			rowAffected = pstmt.executeUpdate();
		}
		return rowAffected;
	}

	@Override
	public int insertDepartmentWithConnection(Connection con, Department department) throws SQLException {
		String sql = "insert into department values(?, ?, ?)";
		int rowAffected = 0;
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, department.getDeptNo());
			pstmt.setString(2, department.getDeptName());
			pstmt.setInt(3, department.getFloor());
			LOG.trace(pstmt);
			rowAffected = pstmt.executeUpdate();
		}
		return rowAffected;
	}

}
