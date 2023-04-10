package hu.webler.dao;

import java.sql.ResultSet;
import java.util.List;

import hu.webler.model.Employee;

public interface IEmployee {

	List<Employee> getAllActive();	
	Employee getById(int id);
	void save(Employee employee);
	void update(Employee employee);
	void delete(Employee employee);
	Employee getObjectFromRs(ResultSet rs);
}
