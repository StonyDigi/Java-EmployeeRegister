package hu.webler.view;

public interface IViewListener {

	public void onButtonClickedCreateFrame();
	public void onButtonClickedSaveEmployee();
	public void onMouseClickedRowSelected(int id);
	public void onButtonClickedCloseUpdateOrDeleteFrame();
	public void onButtonClickedDeleteEmployee();
	public void onButtonClickedUpdateEmployee();
	public void onMouseClickedExit();
	
}
