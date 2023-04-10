package hu.webler.app;

import javax.swing.SwingUtilities;

import hu.webler.dao.EmployeeCategoryDao;
import hu.webler.dao.EmployeeDao;
import hu.webler.presenter.EmployeePresenter;
import hu.webler.view.View;

public class App {

	public App() {
		final View view = new View();
		final EmployeeDao employeeDao = new EmployeeDao();
		final EmployeeCategoryDao employeeCategoryDao = new EmployeeCategoryDao();
		EmployeePresenter employeePresenterObj = new EmployeePresenter(view, employeeDao, employeeCategoryDao);
		employeePresenterObj.fillTableEmployee();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new App();
				}
		});
	}

	//Code Challange:
	// 1. EmployeeCategory CRUD
	// 2. Filter -> vezet�kn�v, keresztn�v, beoszt�s alapj�n, 
	//fizet�s alapj�n (t�l ig - intervallum)
	
	
}
