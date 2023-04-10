package hu.webler.dao;

import java.sql.ResultSet;
import java.util.List;

import hu.webler.model.EmployeeCategory;

public interface IEmployeeCategory {

	List<EmployeeCategory> getAllActive();
	List<String> getAllNames();
	EmployeeCategory getById(int id);
	EmployeeCategory getObjectFromRs(ResultSet rs);
	
}
