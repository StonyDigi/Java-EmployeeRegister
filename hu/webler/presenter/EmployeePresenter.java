package hu.webler.presenter;

import java.util.List;

import hu.webler.dao.EmployeeCategoryDao;
import hu.webler.dao.EmployeeDao;
import hu.webler.model.Employee;
import hu.webler.view.IViewListener;
import hu.webler.view.View;

public class EmployeePresenter implements IViewListener{

	private final View view;
	private final EmployeeDao employeeDao;
	private final EmployeeCategoryDao employeeCategoryDao;
		
	public EmployeePresenter(View view, EmployeeDao employeeDao, EmployeeCategoryDao employeeCategoryDao) {
		this.view = view;
		this.employeeDao = employeeDao;
		this.employeeCategoryDao = employeeCategoryDao;
		view.addListener(this);
	}

	@Override
	public void onButtonClickedCreateFrame() {
		view.setEmpNewFrame(employeeCategoryDao.getAllNames());
	}
	
	public void fillTableEmployee() {
		List<Employee> employees = employeeDao.getAllActive();
		view.setModelToTableEmployee(employees);
	}

	@Override
	public void onButtonClickedSaveEmployee() {
		employeeDao.save(view.getEmpDataFromFrame());
		refreshTable();
	}

	@Override
	public void onMouseClickedRowSelected(int id) {
		//System.out.println("Kiválasztottuk ezt az id-t: "+id);
		//System.out.println(employeeDao.getById(id));
		//System.out.println(employeeCategoryDao.getAllNames());
		view.setEmpUpdateOrDeleteFrame(employeeDao.getById(id), employeeCategoryDao.getAllNames());
	}

	@Override
	public void onButtonClickedCloseUpdateOrDeleteFrame() {
		view.disposeEmpUpdateOrDeleteFrame();
	}

	@Override
	public void onButtonClickedDeleteEmployee() {
		employeeDao.delete(view.getEmpDataFromFrameToUpdate());
		refreshTable();
		view.disposeEmpUpdateOrDeleteFrame();
	}

	@Override
	public void onButtonClickedUpdateEmployee() {
		employeeDao.update(view.getEmpDataFromFrameToUpdate());
		refreshTable();
		view.disposeEmpUpdateOrDeleteFrame();
	}

	@Override
	public void onMouseClickedExit() {
		System.exit(0);
	}
	
	public void refreshTable() {
		view.tableEmployeeDeleteRows();
		this.fillTableEmployee();
		
	}
}