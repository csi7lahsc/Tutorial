package leaveApplicationSys.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import leaveApplicationSys.LeaveRequest;
import leaveApplicationSys.Staff;

public class LeaveDateDialog extends JDialog {
	public static final String STARTDATE_LABEL = "startDateLabel";
	public static final String STARTDATE_LABEL_TEXT = "start date:";
	public static final String STARTDATE_FIELD = "startDateField";
	public static final String ENDDATE_LABEL = "endDateLabel";
	public static final String ENDDATE_LABEL_TEXT = "end date:";
	public static final String ENDDATE_FIELD = "endDateField";
	public static final String COMFIRM_BUTTON_NAME = "comfirmButton";
	public static final String COMFIRM_BUTTON_TEXT = "comfirm";
	public static final String CANCEL_BUTTON_NAME = "cancelButton";
	public static final String CANCEL_BUTTON_TEXT = "cancel";
	private static final String TITLE = "Apply for leave date";
	private JButton comfirmButton;
	private JButton cancelButton;
	private Staff staff;
	
	private LeaveDateDialog(){
		setTitle(TITLE);
		createLayout();
	}
	
	public LeaveDateDialog(Staff staff) {
		this();
		this.staff = staff;
	}

	private void createLayout() {
		setLayout(new BorderLayout());
		add(createFieldPanel(), BorderLayout.CENTER);
		add(createBottomPanel(), BorderLayout.SOUTH);
		addListener();
		setModal(true);
	}

	private void addListener() {
		comfirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String startDate = getField(STARTDATE_FIELD).getText();
				//要加判断过滤时再用方法封装
				staff.setStartDate(startDate);
				String endDate = getField(ENDDATE_FIELD).getText();
				staff.setEndDate(endDate);
				LeaveRequest request = new LeaveRequest(staff.getName(),
										staff.getStartDate(),
										staff.getEndDate());
				setVisible(false);
				staff.handleRequest(request);
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}

	private JPanel createBottomPanel() {
		comfirmButton = createButton(COMFIRM_BUTTON_NAME, COMFIRM_BUTTON_TEXT);
		cancelButton = createButton(CANCEL_BUTTON_NAME, CANCEL_BUTTON_TEXT);
		JPanel panel = new JPanel();
		panel.add(comfirmButton, BorderLayout.SOUTH);
		panel.add(cancelButton,BorderLayout.SOUTH);
		return panel;
	}

	private JPanel createFieldPanel() {
		int columns = 10;//输入框长度
		JLabel startDateLabel = createLabel(STARTDATE_LABEL, STARTDATE_LABEL_TEXT);
		JTextField startDateField = createField(STARTDATE_FIELD, columns);
		JLabel endDateLabel = createLabel(ENDDATE_LABEL, ENDDATE_LABEL_TEXT);
		JTextField endDateField = createField(ENDDATE_FIELD, columns);
		
		int rows = 2;
		int cols = 2;
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(rows, cols));
		panel.add(startDateLabel, BorderLayout.NORTH);
		panel.add(startDateField, BorderLayout.NORTH);
		panel.add(endDateLabel, BorderLayout.NORTH);
		panel.add(endDateField, BorderLayout.NORTH);
		return panel;
	}

	private JTextField createField(String fieldName, int columns) {
		JTextField field = new JTextField(columns);
		field.setName(fieldName);
		return field;
	}

	private JButton createButton(String buttonName, String buttonText) {
		JButton button = new JButton(buttonText);
		button.setName(buttonName);
		return button;
	}

	private JLabel createLabel(String labelName, String staffLabelText) {
		JLabel label = new JLabel(staffLabelText);
		label.setName(labelName);
		return label;
	}

	public JLabel getLabel(String labelName) {
		return (JLabel) Util.getComponent(this, labelName);
	}

	public JButton getButton(String buttonName) {
		return (JButton) Util.getComponent(this, buttonName);
	}

	public JTextField getField(String fieldName) {
		return (JTextField) Util.getComponent(this, fieldName);
	}

	public static void main(String[] args) {
		show(new LeaveDateDialog());//仅用作显示测试
	}

	public static void show(LeaveDateDialog leaveDateDialog) {
		leaveDateDialog.setSize(300, 150);
		leaveDateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		leaveDateDialog.setVisible(true);
	}

	public void setText(String fieldName, String dateString) {
		getField(fieldName).setText(dateString);
	}

	public Staff getStaff() {
		return staff;
	}
}
