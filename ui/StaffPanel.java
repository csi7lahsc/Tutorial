package leaveApplicationSys.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import leaveApplicationSys.Staff;

/**
 * 新增删除员工面板类，
 * 只有显示功能，将控制功能放在StaffUI
 * 将上级输入框改成单选下拉框，内容来自列表中的staff name
 * 使用Tree保存staff对象
 * 增加请假按钮，在table中选中员工后可点击按钮，
 * 弹出对话框，输入两个日期，提交或取消，
 * 若提交则弹出第二个对话框，确认或否决
 * 已完成输入对话框LeaveDateDialog，增加按钮调用其即可
 */
public class StaffPanel extends JPanel{
	public static final String NAME = "staffPanel";
	public static final String STAFF_LABEL_TEXT = "staff:";
	public static final String STAFF_LIST_NAME = "staffList";
	public static final String ADD_BUTTON_NAME = "addButton";
	public static final String ADD_BUTTON_TEXT = "add";
	public static final String ADDSTAFF_LABEL_NAME = "addStaffLabel";
	public static final String ADDSTAFF_LABEL_TEXT = "staff name:";
	public static final String ADDSTAFF_FIELD_NAME = "addStaffField";
	public static final String ADDSUPERVISOR_LABEL_NAME = "addSupervisorLabel";
	public static final String ADDSUPERVISOR_LABEL_TEXT = "supervisor:";
//	public static final String ADDSUPERVISOR_FIELD_NAME = "addSupervisorField";
	public static final String STAFF_TABLE_NAME = "staffTable";
	public static final String DELETE_BUTTON_NAME = "deleteButton";
	public static final String DELETE_BUTTON_TEXT = "delete";
	public static final String STAFF_COMBOBOX_NAME = "staffComboBox";
	public static final String APPLY_BUTTON_NAME = "applyButton";
	public static final String APPLY_BUTTON_TEXT = "i need a vacation";
	private JButton addButton;
	private JButton deleteButton;
	private JButton applyButton;
	private JTable staffTable;
	private StaffTableModel staffTableModel = new StaffTableModel();
	private DefaultComboBoxModel<String> comboBoxModel 
										= new DefaultComboBoxModel<String>();

	public StaffPanel(){
		setName(NAME);
		createLayout();
	}
	
	//使用工厂方法简化创建语句,add(Component comp)父类Container的方法
	private void createLayout() {
		staffTable = createStaffTable(STAFF_TABLE_NAME, staffTableModel);
		JScrollPane staffScroll = new JScrollPane(staffTable);//列表界面添加滚动条
		staffScroll.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		final int pad = 6;//控件与边框间距
		Border emptyBorder = BorderFactory.createEmptyBorder(pad, pad, pad, pad);
		Border titleBorder = BorderFactory.createTitledBorder(STAFF_LABEL_TEXT);
		setBorder(BorderFactory.createCompoundBorder(emptyBorder, titleBorder));
		
		setLayout(new BorderLayout());
		add(staffScroll, BorderLayout.CENTER);
		add(createBottomPanel(), BorderLayout.SOUTH);
	}

	private JTable createStaffTable(String staffTableName, StaffTableModel tableModel) {
		JTable table = new JTable(tableModel);
		table.setName(staffTableName);
		table.setShowGrid(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return table;
	}

	private JPanel createBottomPanel() {
		addButton = createButton(ADD_BUTTON_NAME, ADD_BUTTON_TEXT);
		deleteButton = createButton(DELETE_BUTTON_NAME, DELETE_BUTTON_TEXT);
		applyButton = createButton(APPLY_BUTTON_NAME, APPLY_BUTTON_TEXT);
		JPanel panel = new JPanel();
		panel.add(createFieldPanel(), BorderLayout.NORTH);
		panel.add(addButton, BorderLayout.SOUTH);
		panel.add(deleteButton,BorderLayout.SOUTH);
		panel.add(applyButton, BorderLayout.SOUTH);
		return panel;
	}

	private JPanel createFieldPanel() {
		int columns = 10;//输入框长度
		JLabel addStaffLabel = createLabel(ADDSTAFF_LABEL_NAME, ADDSTAFF_LABEL_TEXT);
		JTextField staffField = createField(ADDSTAFF_FIELD_NAME, columns);
		JLabel addSupervisorLabel = createLabel(ADDSUPERVISOR_LABEL_NAME, ADDSUPERVISOR_LABEL_TEXT);
//		JTextField SupervisorField = createField(ADDSUPERVISOR_FIELD_NAME,columns);
		//改成combobox下拉框
		JComboBox<String> comboBox = createComboBox(STAFF_COMBOBOX_NAME);
		
		int rows = 2;
		int cols = 2;
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(rows, cols));
		panel.add(addStaffLabel);
		panel.add(staffField);
		panel.add(addSupervisorLabel);
		panel.add(comboBox);
		return panel;
	}

	private JComboBox<String> createComboBox(String staffComboboxName) {
		JComboBox<String> comboBox = new JComboBox<String>(comboBoxModel);
		comboBox.setName(staffComboboxName);
		return comboBox;
	}

	private JTextField createField(String addstaffFieldName, int columns) {
		JTextField staffField = new JTextField(columns);
		staffField.setName(addstaffFieldName);
		return staffField;
	}

	private JButton createButton(String buttonName, String buttonText) {
		JButton button = new JButton(buttonText);
		button.setName(buttonName);
		return button;
	}

	private JLabel createLabel(String staffLabelName, String staffLabelText) {
		JLabel staffLabel = new JLabel(staffLabelText);
		staffLabel.setName(staffLabelName);
		return staffLabel;
	}

	public static void main(String[] args) {
		show(new StaffPanel());//仅用作显示测试
	}

	private static void show(StaffPanel staffPanel) {
		JFrame frame = new JFrame();
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().add(staffPanel);
	}

	public JLabel getLabel(String labelName) {
		return (JLabel) Util.getComponent(this, labelName);
	}

	public JButton getButton(String addButtonName) {
		return (JButton) Util.getComponent(this, addButtonName);
	}

	public JTextField getField(String addstaffFieldName) {
		return (JTextField) Util.getComponent(this, addstaffFieldName);
	}

	public JComboBox<String> getComboBox(String staffComboboxName) {
		return (JComboBox<String>) Util.getComponent(this, staffComboboxName);
	}

	/**将事件触发和业务逻辑分离，保持界面类对业务逻辑的无知就是解耦，
	 * 便于维护和扩展*/
	public void addStaffAddListener(ActionListener actionListener) {
		addButton.addActionListener(actionListener);
	}
	
	public void deleteStaffAddListener(ActionListener actionListener) {
		deleteButton.addActionListener(actionListener);
	}
	
	public void applyLeaveAddListener(ActionListener actionListener) {
		applyButton.addActionListener(actionListener);
	}

	/**用一个公共方法封装成员变量的add方法就可以将其他类和数据结构的具体调用解耦*/
	public void addStaff(Staff staff) {
		staffTableModel.add(staff);
		comboBoxModel.addElement(staff.getName());
	}

	/**测试用，将字符串传入文本框*/
	public void setText(String fieldName, String name) {
		JTextField field = getField(fieldName);
		field.setText(name);
	}

	public String getText(String addstaffFieldName) {
		JTextField field = (JTextField) Util.getComponent(this, addstaffFieldName);
		return field.getText();
	}
	
	public JTable getTable(String tableName) {
		return 	(JTable) Util.getComponent(this, tableName);
	}

	public Staff getStaff(int i) {
		return staffTableModel.get(i);
	}

	/**设置按钮是否可点击*/
	public void setEnabled(String buttonName, boolean state) {
		getButton(buttonName).setEnabled(state);
	}
	
	/**设置输入框是否可输入*/
	public void setFieldEnabled(String fieldName, boolean state) {
		getField(fieldName).setEnabled(state);
	}

	/**输入框增加按键监听*/
	public void addFieldListener(String fieldName, KeyListener listener) {
		getField(fieldName).addKeyListener(listener);
	}

	/**添加员工后清空输入框*/
	public void clearAllText() {
		JTextField staffField = getField(ADDSTAFF_FIELD_NAME);
		staffField.setText("");
//		JTextField supervisorField = getField(ADDSUPERVISOR_FIELD_NAME);
//		supervisorField.setText("");
	}

	/**列表增加鼠标监听*/
	public void addTableListener(MouseListener listener){
		staffTable.addMouseListener(listener);
	}

	/**表格删除指定行*/
	public void deleteStaff(int row) {
		Staff removedStaff = staffTableModel.remove(row);
		comboBoxModel.removeElement(removedStaff.getName());
	}

	/**返回选中的行数*/
	public int getSelectedTableRow() {
		return staffTable.getSelectedRow();
	}

	/**返回选中的上级*/
	public String getSelectedComboBoxStaff() {
		return (String) comboBoxModel.getSelectedItem();
	}

	public Staff getStaffByName(String name) {
		return staffTableModel.getStaffByName(name);
	}

}
