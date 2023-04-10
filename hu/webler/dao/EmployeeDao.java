package hu.webler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import hu.webler.model.Employee;
import hu.webler.utils.Database;

public class EmployeeDao implements IEmployee {

	private Connection con = new Database().getCon();
	private ResultSet rs = null;
	private PreparedStatement pstmt = null;
	
	@Override
	public List<Employee> getAllActive() {
		List<Employee> employees = new ArrayList<Employee>();
		String sql = "SELECT * FROM employee INNER JOIN "
				+ "employee_category ON employee.employee_category_id = employee_category.id "
				+ "WHERE employee.deleted = 0;";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				employees.add(getObjectFromRs(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employees;
	}

	@Override
	public Employee getById(int id) {
		Employee employee = null;
		String sql = "SELECT * FROM employee WHERE id = ?;";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				employee = getObjectFromRs(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public void save(Employee employee) {
//		 employee = new Employee(
//				-999,
//				"TesztHC_ker",
//				"TesztHC_vez",
//				LocalDate.of(1999,01,02),
//				"AA123456",
//				999,
//				1,
//				true,
//				false
//				);
		
		String sql = "INSERT INTO employee (first_name, last_name, birth_of_date, "
				+ "identity_card, salary, employee_category_id, status) "
				+ "VALUES (?,?,?,?,?,?,?);";
		try {
			pstmt = con.prepareStatement(sql);
			
			//bind: paraméterek összekötése a változókkal
			pstmt.setString(1, employee.getFirstName());
			pstmt.setString(2, employee.getLastName());
			pstmt.setString(3, employee.getBirthOfDate().toString());
			pstmt.setString(4, employee.getIdentityCard());
			pstmt.setInt(5, employee.getSalary());
			pstmt.setInt(6, employee.getEmployeeCategoryId());
			pstmt.setInt(7, employee.isStatus() ? 1 : 0);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Employee employee) {
		String sql = "UPDATE employee SET "
				+ "first_name = ?, last_name = ?, birth_of_date=?,"
				+ "identity_card = ?, salary = ?, employee_category_id = ?,"
				+ "status = ? WHERE id = ?;";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, employee.getFirstName());
			pstmt.setString(2, employee.getLastName());
			pstmt.setString(3, employee.getBirthOfDate().toString());
			pstmt.setString(4, employee.getIdentityCard());
			pstmt.setInt(5, employee.getSalary());
			pstmt.setInt(6, employee.getEmployeeCategoryId());
			pstmt.setInt(7, employee.isStatus() ? 1 :0);
			pstmt.setInt(8, employee.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Employee employee) {
		String sql = "UPDATE employee SET deleted = 1 WHERE id = ?;";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, employee.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Employee getObjectFromRs(ResultSet rs) {
		Employee empObj = null;
		try {
			empObj = new Employee(
					rs.getInt("id"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					rs.getDate("birth_of_date").toLocalDate(),
					rs.getString("identity_card"),
					rs.getInt("salary"),
					rs.getInt("employee_category_id"),
					rs.getBoolean("status"), 
					rs.getBoolean("deleted")
				);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		return empObj;
	}
}
