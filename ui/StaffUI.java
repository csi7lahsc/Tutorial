package leaveApplicationSys.ui;

import java.awt.event.*;

import javax.swing.JFrame;

import leaveApplicationSys.Staff;

/**
 * 请假系统UI类，作为界面控制类
 * 此类作为增删员工UI类，再增加一个总界面，
 * 包括两个按钮：“增删员工”，“我要请假”，
 * 分别打开这个UI和请假UI，增加“返回主界面”按钮，
 * 增加员工时要选择其上级，
 * (可以将员工列表放在选择ui类，在点击addButton后弹出)
 * 第一个员工无法选择，默认为boss
 * 
 * 另一种设计：要求并没说要做集成的系统，完全可以做分别独立的UI，
 * 不需要总界面，“我要请假”界面中审批界面做成弹出对话框
 */
public class StaffUI {
	public static final int WIDTH = 500;
	public static final int HEIGHT = 300;
	public static final String TITLE = "Staff List";

	private JFrame frame = new JFrame(TITLE);
	private StaffPanel panel;

	public StaffUI(){
		initialize();
	}
	
	private void initialize() {
		createStaffPanel();
		createKeyListeners();//两个输入框调用一个按键监听
		createMouseListener();
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
	}

	private void createMouseListener() {
		panel.addTableListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				setButtonState(StaffPanel.DELETE_BUTTON_NAME);
				setButtonState(StaffPanel.APPLY_BUTTON_NAME);
			}
		});
	}

	protected int getSelectedTableRow() {
		return panel.getSelectedTableRow();
	}

	/**选中一条后才能删除*/
	protected void setButtonState(String buttonName) {
		panel.setEnabled(StaffPanel.DELETE_BUTTON_NAME, getSelectedTableRow() >= 0);
	}

	private void createKeyListeners() {
		KeyListener listener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				//输入完成后判断是否可以添加员工
				setAddButtonState();
			}
			@Override
			public void keyPressed(KeyEvent e) {
				//TODO 如果是回车键调用addButton
			}
		};
		//将按键监听器实例赋给两个输入框
		panel.addFieldListener(StaffPanel.ADDSTAFF_FIELD_NAME, listener);
//		panel.addFieldListener(StaffPanel.ADDSUPERVISOR_FIELD_NAME, listener);
		setAddButtonState();//设置初始状态
		setButtonState(StaffPanel.DELETE_BUTTON_NAME);
		setButtonState(StaffPanel.APPLY_BUTTON_NAME);
	}

	/**当两个输入框都不为空时按钮才可用*/
	private void setAddButtonState() {
		boolean state = isFieldEmpty(StaffPanel.ADDSTAFF_FIELD_NAME);
//						|| isFieldEmpty(StaffPanel.ADDSUPERVISOR_FIELD_NAME);
		try{
			panel.getStaff(0);
		}catch (IndexOutOfBoundsException e) {
			state = isFieldEmpty(StaffPanel.ADDSTAFF_FIELD_NAME);
			panel.setEnabled(StaffPanel.ADD_BUTTON_NAME, !state);
//			panel.setFieldEnabled(StaffPanel.ADDSUPERVISOR_FIELD_NAME, false);
			return;
		}
		panel.setEnabled(StaffPanel.ADD_BUTTON_NAME, !state);
//		panel.setFieldEnabled(StaffPanel.ADDSUPERVISOR_FIELD_NAME, true);
	}

	private boolean isFieldEmpty(String fieldName) {
		String text = panel.getText(fieldName).trim();
		return "".equals(text);
	}

	private void createStaffPanel() {
		panel = new StaffPanel();
		//将监听的实现从界面类提到控制类
		panel.addStaffAddListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addStaff();
				clearAllText();
			}
		});
		panel.deleteStaffAddListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteSelectedStaff(getSelectedTableRow());
				setButtonState(StaffPanel.DELETE_BUTTON_NAME);
				setButtonState(StaffPanel.APPLY_BUTTON_NAME);
			}
		});
		panel.applyLeaveAddListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				applyLeave(getSelectedTableRow());
				setButtonState(StaffPanel.DELETE_BUTTON_NAME);
				setButtonState(StaffPanel.APPLY_BUTTON_NAME);
			}
		});
	}
	
	protected void applyLeave(int row) {
		Staff applicant = panel.getStaff(row);
		LeaveDateDialog.show(new LeaveDateDialog(applicant));
	}

	protected void deleteSelectedStaff(int row) {
		panel.deleteStaff(row);
	}

	protected void clearAllText() {
		panel.clearAllText();
		setAddButtonState();
	}

	protected void addStaff() {
		//TODO 要加上完整的上级对象，否则无法逐级调用
		//1.读取文本框的字符串并创建员工对象
		String name = panel.getText(StaffPanel.ADDSTAFF_FIELD_NAME);
		Staff staff = new Staff(name);
		if(panel.getSelectedComboBoxStaff() != null){
			int index = 0;//下拉框的员工对应的table行数
			String superName = panel.getSelectedComboBoxStaff();
			Staff supervisor = panel.getStaffByName(superName);
			staff.setSupervisor(supervisor);
		}
//		String superStaffName = panel.getText(StaffPanel.ADDSUPERVISOR_FIELD_NAME);
//		staff.setSupervisor(new Staff(superStaffName));
		//2.将员工对象加入table
		panel.addStaff(staff);
	}

	public static void main(String[] args) {
		new StaffUI().show();
	}

	public void show() {
		frame.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}

}
