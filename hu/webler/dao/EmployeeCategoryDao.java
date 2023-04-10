package hu.webler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hu.webler.model.EmployeeCategory;
import hu.webler.utils.Database;

public class EmployeeCategoryDao implements IEmployeeCategory {

	private Connection con = new Database().getCon();
	private ResultSet rs = null;
	private PreparedStatement pstmt = null;
	
	@Override
	public List<EmployeeCategory> getAllActive() {
		List<EmployeeCategory> employeeCategories = new ArrayList<EmployeeCategory>();
		String sql = "SELECT * FROM employee_category WHERE deleted = 0;";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				employeeCategories.add(getObjectFromRs(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employeeCategories;
	}

	@Override
	public List<String> getAllNames() {
		List<String> employeeCategoryNames = new ArrayList<String>();
		String sql = "SELECT name FROM employee_category WHERE deleted = 0;";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				employeeCategoryNames.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employeeCategoryNames;
	}

	@Override
	public EmployeeCategory getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeCategory getObjectFromRs(ResultSet rs) {
		EmployeeCategory empCategoryObj = null;
		try {
			empCategoryObj = new EmployeeCategory(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getBoolean("status"), 
					rs.getBoolean("deleted")
				);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return empCategoryObj;
	}

}
