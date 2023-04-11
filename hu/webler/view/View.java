package hu.webler.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import hu.webler.model.Employee;

@SuppressWarnings("serial")
public class View extends JFrame {

	
	//final - nem lehet késõbb a tartalmát felülírni
			private final ArrayList<IViewListener> listeners;
			private JFrame empListFrame;
			private JScrollPane scrollPane;
			private JTable tableEmployee;
			private JFrame empNewFrame;
			private JButton btnCreateEmpNewFrame;
			
			private JFrame empUpdateOrDeleteFrame;
			
			private JTextField tfFirstName;
			private JTextField tfLastName;
			private JTextField tfBirthOfDate;
			private JTextField tfIdentityCard;
			private JTextField tfSalary;
			@SuppressWarnings("rawtypes")
			private JComboBox cbEmpCategory;
			private JCheckBox chBoxStatus;
			private JLabel lblFirstName;
			private JLabel lblLastName;
			private JLabel lblBirthOfDate;
			private JLabel lblIdentityCard;
			private JLabel lblSalary;
			private JLabel lblEmpCategory;
			@SuppressWarnings("unused")
			private JLabel lblStatus;
			private JButton btnEmpSave;
			private JLabel lblHiddenId;
			private JButton btnClose;
			private JButton btnEmpDelete;
			private JButton btnEmpUpdate;
			private JButton btnExit;

	public View() {
		
			empListFrame = new JFrame();
			empListFrame.setBounds(300,200,1020,650);
			empListFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			empListFrame.getContentPane().setLayout(null);
			empListFrame.setTitle("Dolgozói nyilvántartás");
			
			scrollPane = new JScrollPane();
			scrollPane.setBounds(35,21,921,180);
			empListFrame.getContentPane().add(scrollPane);
			
			tableEmployee = new JTable();
			scrollPane.setViewportView(tableEmployee);
			
			tableEmployee.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					int rowIndex = tableEmployee.rowAtPoint(e.getPoint());
					int id = getEmployeeIdToUpdateOrDelete(rowIndex);
					notifyListenersOnMouseClickedRowSelected(id);
				}
			
			});
			
			
			btnCreateEmpNewFrame = new JButton("Új dolgozó felvitel");
			btnCreateEmpNewFrame.setBounds(60,240,150,50);
			btnCreateEmpNewFrame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					notifyListenersOnButtonClickCreateFrame();
				}
			});
			
			btnExit = new JButton("KILÉP");
			btnExit.setBounds(800,450,150,50);
			btnExit.setBackground(Color.red);
			btnExit.setForeground(Color.white);
			btnExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					notifyListenersOnButtonClickExit();
				}
			});
			
			this.listeners = new ArrayList<IViewListener>();
			empListFrame.add(btnCreateEmpNewFrame);
			empListFrame.add(btnExit);
			empListFrame.setVisible(true);
		}
		
		public void setModelToTableEmployee(List<Employee> employees) {
			DefaultTableModel model = new DefaultTableModel();
			model.addColumn("Id");
			model.addColumn("Név");
			model.addColumn("Születési dátum");
			model.addColumn("Szig. szám");
			model.addColumn("Fizetés");
			model.addColumn("Beosztás");
			model.addColumn("Állapot");
			 
			for (Employee employee : employees) {
				model.addRow(new Object[] {
						employee.getId(),
						employee.getFullName(),
						employee.getBirthOfDate(),
						employee.getIdentityCard(),
						employee.getSalary(),
						employee.getEmployeeCategoryId(), 
						employee.getStatusText()
				});
			}
			
			tableEmployee.setModel(model);
			tableEmployee.setRowHeight(30);
			setTableCellCenter();
		}
		
		public void setTableCellCenter() {
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			for (int i = 0; i < tableEmployee.getModel().getColumnCount(); i++) {
				tableEmployee.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}
		}
		
		public void tableEmployeeDeleteRows() {
			int rowCount = tableEmployee.getModel().getRowCount();
			for (int i = rowCount-1; i >= 0; i--) {
				((DefaultTableModel)tableEmployee.getModel()).removeRow(i);
			}
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void setEmpNewFrame(List<String> employeeCategories) {
			empNewFrame = new JFrame();
			empNewFrame.setBounds(900,200,600,700);
			empNewFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			empNewFrame.getContentPane().setLayout(null);
			empNewFrame.setTitle("Új dolgozó");
			empNewFrame.setVisible(true);
			
			lblLastName = new JLabel("Vezetéknév: ");
			lblLastName.setBounds(100,60,190,45);
			lblLastName.setFont(new Font("Tahoma", Font.BOLD, 16));
			empNewFrame.add(lblLastName);
			
			tfLastName = new JTextField();
			tfLastName.setBounds(280,60,190,45);
			tfLastName.setFont(new Font("Tahoma", Font.BOLD, 16));
			tfLastName.setHorizontalAlignment(SwingConstants.CENTER);
			empNewFrame.add(tfLastName);
			
			lblFirstName = new JLabel("Keresztnév: ");
			lblFirstName.setBounds(100,120,190,45);
			lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 16));
			empNewFrame.add(lblFirstName);
			
			tfFirstName = new JTextField();
			tfFirstName.setBounds(280,120,190,45);
			tfFirstName.setFont(new Font("Tahoma", Font.BOLD, 16));
			tfFirstName.setHorizontalAlignment(SwingConstants.CENTER);
			empNewFrame.add(tfFirstName);
			
			lblBirthOfDate = new JLabel("Születési idõ: ");
			lblBirthOfDate.setBounds(100,180,190,45);
			lblBirthOfDate.setFont(new Font("Tahoma", Font.BOLD, 16));
			empNewFrame.add(lblBirthOfDate);
			
			tfBirthOfDate = new JTextField();
			tfBirthOfDate.setBounds(280,180,190,45);
			tfBirthOfDate.setFont(new Font("Tahoma", Font.BOLD, 16));
			tfBirthOfDate.setHorizontalAlignment(SwingConstants.CENTER);
			empNewFrame.add(tfBirthOfDate);
			
			lblIdentityCard = new JLabel("Szigszám: ");
			lblIdentityCard.setBounds(100,240,190,45);
			lblIdentityCard.setFont(new Font("Tahoma", Font.BOLD, 16));
			empNewFrame.add(lblIdentityCard);
			
			tfIdentityCard = new JTextField();
			tfIdentityCard.setBounds(280,240,190,45);
			tfIdentityCard.setFont(new Font("Tahoma", Font.BOLD, 16));
			tfIdentityCard.setHorizontalAlignment(SwingConstants.CENTER);
			empNewFrame.add(tfIdentityCard);
			
			lblSalary = new JLabel("Fizetés: ");
			lblSalary.setBounds(100,300,190,45);
			lblSalary.setFont(new Font("Tahoma", Font.BOLD, 16));
			empNewFrame.add(lblSalary);
			
			tfSalary = new JTextField();
			tfSalary.setBounds(280,300,190,45);
			tfSalary.setFont(new Font("Tahoma", Font.BOLD, 16));
			tfSalary.setHorizontalAlignment(SwingConstants.CENTER);
			empNewFrame.add(tfSalary);
			
			lblEmpCategory = new JLabel("Beosztás: ");
			lblEmpCategory.setBounds(100,360,190,45);
			lblEmpCategory.setFont(new Font("Tahoma", Font.BOLD, 16));
			empNewFrame.add(lblEmpCategory);
			
			cbEmpCategory = new JComboBox();
			cbEmpCategory.setBounds(280,360,190,45);
			cbEmpCategory.setModel(new DefaultComboBoxModel(employeeCategories.toArray()));
			cbEmpCategory.setFont(new Font("Tahoma", Font.BOLD, 16));
			empNewFrame.add(cbEmpCategory);
			
			chBoxStatus = new JCheckBox("Aktív");
			chBoxStatus.setBounds(280,420,190,45);
			chBoxStatus.setFont(new Font("Tahoma", Font.BOLD, 16));
			chBoxStatus.setSelected(true);
			empNewFrame.add(chBoxStatus);
			
			btnEmpSave = new JButton("MENTÉS");
			btnEmpSave.setBounds(280,480,190,45);
			empNewFrame.add(btnEmpSave);
			
			btnEmpSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					notifyListenersOnButtonClickSaveEmployee();
				}
			});
		}
		
		//ToDo employee Category Id javít
		//Ebben az Employee konstruktorban még nem szerepel az id!
		public Employee getEmpDataFromFrame() {
			String[] birtOfDateArray = tfBirthOfDate.getText().split("-");
			Employee employee = new Employee(
					tfFirstName.getText(),
					tfLastName.getText(),
					LocalDate.of(Integer.parseInt(birtOfDateArray[0]), Integer.parseInt(birtOfDateArray[1]), Integer.parseInt(birtOfDateArray[2])),
					tfIdentityCard.getText(),
					Integer.parseInt(tfSalary.getText()),
					cbEmpCategory.getSelectedIndex()+1,
					chBoxStatus.isSelected()
					);
			return employee;
		}
		
		//Ebben az Employee konstruktorban már szerepel az id!
		//törléshez elengedhetetlen!
		public Employee getEmpDataFromFrameToUpdate() {
			String[] birtOfDateArray = tfBirthOfDate.getText().split("-");
			Employee employee = new Employee(
					Integer.parseInt(lblHiddenId.getText()),
					tfFirstName.getText(),
					tfLastName.getText(),
					LocalDate.of(Integer.parseInt(birtOfDateArray[0]), Integer.parseInt(birtOfDateArray[1]), Integer.parseInt(birtOfDateArray[2])),
					tfIdentityCard.getText(),
					Integer.parseInt(tfSalary.getText()),
					cbEmpCategory.getSelectedIndex()+1,
					chBoxStatus.isSelected()
					);
			return employee;
		}
		
		
		public void addListener(final IViewListener listener) {
			listeners.add(listener);
		}
		
		private void notifyListenersOnButtonClickCreateFrame() {
			for (final IViewListener listener : listeners) {
				listener.onButtonClickedCreateFrame();
			}
		}
		
		private void notifyListenersOnMouseClickedRowSelected(int id) {
			for (final IViewListener listener : listeners) {
				listener.onMouseClickedRowSelected(id);
			}
		}
		private void notifyListenersOnButtonClickExit() {
			for (final IViewListener listener : listeners) {
				listener.onMouseClickedExit();
			}
		}
		
		private void notifyListenersOnButtonClickSaveEmployee() {
			for (final IViewListener listener : listeners) {
				listener.onButtonClickedSaveEmployee();
			}
		}
		
		private void notifyListenersOnButtonClickedCloseUpdateOrDeleteFrame() {
			for (final IViewListener listener : listeners) {
				listener.onButtonClickedCloseUpdateOrDeleteFrame();
			}
		}
		
		private void notifyListenersOnButtonClickedDeleteEmployee() {
			for (final IViewListener listener : listeners) {
				listener.onButtonClickedDeleteEmployee();
			}
		}
		private void notifyListenersOnButtonClickedUpdateEmployee() {
			for (final IViewListener listener : listeners) {
				listener.onButtonClickedUpdateEmployee();
			}
		}
		
		
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void setEmpUpdateOrDeleteFrame(Employee employeeObj, List<String> employeeCategories) {
			
			empUpdateOrDeleteFrame = new JFrame();
			empUpdateOrDeleteFrame.setBounds(900,200,600,700);
			empUpdateOrDeleteFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			empUpdateOrDeleteFrame.getContentPane().setLayout(null);
			empUpdateOrDeleteFrame.setTitle("Dolgozó módosítás vagy törlés (id: "+employeeObj.getId() +")");
			empUpdateOrDeleteFrame.setVisible(true);
			
			lblHiddenId = new JLabel();
			lblHiddenId.setText(employeeObj.getId() + "");
			
			lblLastName = new JLabel("Vezetéknév: ");
			lblLastName.setBounds(100,60,190,45);
			lblLastName.setFont(new Font("Tahoma", Font.BOLD, 16));
			empUpdateOrDeleteFrame.add(lblLastName);
			
			tfLastName = new JTextField();
			tfLastName.setBounds(280,60,190,45);
			tfLastName.setFont(new Font("Tahoma", Font.BOLD, 16));
			tfLastName.setHorizontalAlignment(SwingConstants.CENTER);
			tfLastName.setText(employeeObj.getLastName());
			empUpdateOrDeleteFrame.add(tfLastName);
			
			lblFirstName = new JLabel("Keresztnév: ");
			lblFirstName.setBounds(100,120,190,45);
			lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 16));
			empUpdateOrDeleteFrame.add(lblFirstName);
			
			tfFirstName = new JTextField();
			tfFirstName.setBounds(280,120,190,45);
			tfFirstName.setFont(new Font("Tahoma", Font.BOLD, 16));
			tfFirstName.setHorizontalAlignment(SwingConstants.CENTER);
			tfFirstName.setText(employeeObj.getFirstName());
			empUpdateOrDeleteFrame.add(tfFirstName);
			
			lblBirthOfDate = new JLabel("Születési idõ: ");
			lblBirthOfDate.setBounds(100,180,190,45);
			lblBirthOfDate.setFont(new Font("Tahoma", Font.BOLD, 16));
			empUpdateOrDeleteFrame.add(lblBirthOfDate);
			
			tfBirthOfDate = new JTextField();
			tfBirthOfDate.setBounds(280,180,190,45);
			tfBirthOfDate.setFont(new Font("Tahoma", Font.BOLD, 16));
			tfBirthOfDate.setHorizontalAlignment(SwingConstants.CENTER);
			tfBirthOfDate.setText(employeeObj.getBirthOfDate()+"");
			empUpdateOrDeleteFrame.add(tfBirthOfDate);
			
			lblIdentityCard = new JLabel("Szigszám: ");
			lblIdentityCard.setBounds(100,240,190,45);
			lblIdentityCard.setFont(new Font("Tahoma", Font.BOLD, 16));
			empUpdateOrDeleteFrame.add(lblIdentityCard);
			
			tfIdentityCard = new JTextField();
			tfIdentityCard.setBounds(280,240,190,45);
			tfIdentityCard.setFont(new Font("Tahoma", Font.BOLD, 16));
			tfIdentityCard.setHorizontalAlignment(SwingConstants.CENTER);
			tfIdentityCard.setText(employeeObj.getIdentityCard());
			empUpdateOrDeleteFrame.add(tfIdentityCard);
			
			lblSalary = new JLabel("Fizetés: ");
			lblSalary.setBounds(100,300,190,45);
			lblSalary.setFont(new Font("Tahoma", Font.BOLD, 16));
			empUpdateOrDeleteFrame.add(lblSalary);
			
			tfSalary = new JTextField();
			tfSalary.setBounds(280,300,190,45);
			tfSalary.setFont(new Font("Tahoma", Font.BOLD, 16));
			tfSalary.setHorizontalAlignment(SwingConstants.CENTER);
			tfSalary.setText(employeeObj.getSalary() + "");
			empUpdateOrDeleteFrame.add(tfSalary);
			
			lblEmpCategory = new JLabel("Beosztás: ");
			lblEmpCategory.setBounds(100,360,190,45);
			lblEmpCategory.setFont(new Font("Tahoma", Font.BOLD, 16));
			empUpdateOrDeleteFrame.add(lblEmpCategory);
			
			cbEmpCategory = new JComboBox();
			cbEmpCategory.setBounds(280,360,190,45);
			cbEmpCategory.setModel(new DefaultComboBoxModel(employeeCategories.toArray()));
			cbEmpCategory.setFont(new Font("Tahoma", Font.BOLD, 16));
			cbEmpCategory.setSelectedIndex(employeeObj.getEmployeeCategoryId()-1);
			empUpdateOrDeleteFrame.add(cbEmpCategory);
			
			chBoxStatus = new JCheckBox("Aktív");
			chBoxStatus.setBounds(280,420,190,45);
			chBoxStatus.setFont(new Font("Tahoma", Font.BOLD, 16));
			chBoxStatus.setSelected(false);
			if (employeeObj.isStatus()) {
				chBoxStatus.setSelected(true);
			}
			empUpdateOrDeleteFrame.add(chBoxStatus);
			
			btnEmpUpdate = new JButton("Módosítás");
			btnEmpUpdate.setBounds(360,480,120,45);
			btnEmpUpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
			empUpdateOrDeleteFrame.add(btnEmpUpdate);
			
			btnEmpUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Módosítás gombra kattintottunk
					notifyListenersOnButtonClickedUpdateEmployee();
				}
			});
			
			btnEmpDelete = new JButton("Törlés");
			btnEmpDelete.setBounds(140,480,120,45);
			btnEmpDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
			btnEmpDelete.setBackground(Color.RED);
			btnEmpDelete.setForeground(Color.white);
			empUpdateOrDeleteFrame.add(btnEmpDelete);
			
			btnEmpDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//törlés gombra kattintottunk
					notifyListenersOnButtonClickedDeleteEmployee();
				}
			});
			
			btnClose = new JButton("Bezár");
			btnClose.setBounds(360,560,120,45);
			btnClose.setFont(new Font("Tahoma", Font.BOLD, 16));
			btnClose.setBackground(Color.yellow);
			empUpdateOrDeleteFrame.add(btnClose);
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					notifyListenersOnButtonClickedCloseUpdateOrDeleteFrame();
				}
			});
			
		}
		
		public int getEmployeeIdToUpdateOrDelete(int rowIndex) {
			int id = Integer.parseInt(tableEmployee.getModel().getValueAt(rowIndex, 0).toString());
			return id;
		}
		
		public void disposeEmpUpdateOrDeleteFrame() {
			empUpdateOrDeleteFrame.dispose();
		}	
	}