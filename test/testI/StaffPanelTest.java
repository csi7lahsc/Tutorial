package leaveApplicationSys.test.testI;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextField;
import leaveApplicationSys.Staff;
import leaveApplicationSys.ui.StaffPanel;
import leaveApplicationSys.ui.StaffTableModel;

import org.junit.Before;
import org.junit.Test;

/**
 * 新增删除员工界面组建类测试
 */
public class StaffPanelTest {
	private StaffPanel panel;
	private boolean wasClicked;
	private Staff staff;
	private Staff supervisor;
	@Before
	public void setUp(){
		panel = new StaffPanel();
		staff = new Staff("Peter");
		supervisor = new Staff("Anne");
	}
	
	@Test
	public void testCreate2(){
		assertEmptyTable(StaffPanel.STAFF_TABLE_NAME);
		assertButtonTest(StaffPanel.ADD_BUTTON_NAME,StaffPanel.ADD_BUTTON_TEXT);
		assertLabelTest(StaffPanel.ADDSTAFF_LABEL_NAME,StaffPanel.ADDSTAFF_LABEL_TEXT);
		assertEmptyField(StaffPanel.ADDSTAFF_FIELD_NAME);
		assertLabelTest(StaffPanel.ADDSUPERVISOR_LABEL_NAME,StaffPanel.ADDSUPERVISOR_LABEL_TEXT);
//		assertEmptyField(StaffPanel.ADDSUPERVISOR_FIELD_NAME);
		assertEmptyComboBox(StaffPanel.STAFF_COMBOBOX_NAME);
	}
	
	private void assertEmptyComboBox(String staffComboboxName) {
		JComboBox<String> combo = panel.getComboBox(staffComboboxName);
		assertEquals(0, combo.getModel().getSize());
	}

	private void assertEmptyTable(String staffTableName) {
		JTable table = panel.getTable(staffTableName);
		assertEquals(0, table.getModel().getRowCount());
	}
	
	@Test
	public void testAddComboBoxWhileAddStaff(){
		panel.addStaff(staff);
		JComboBox<String> comboBox = panel.getComboBox(StaffPanel.STAFF_COMBOBOX_NAME);
		DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboBox.getModel();
		assertEquals(1, model.getSize());
		assertEquals(staff.getName(), model.getElementAt(0));
	}
	@Test
	public void testDeleteComboBoxWhileDeleteStaff(){
		panel.addStaff(staff);
		JComboBox<String> comboBox = panel.getComboBox(StaffPanel.STAFF_COMBOBOX_NAME);
		DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboBox.getModel();
		panel.deleteStaff(0);
		assertEquals(0, model.getSize());
	}
	
	@Test
	public void testAddSupervisorFormComboBox(){
		panel.addStaff(staff);//先加上director
		Staff staff2 = new Staff("anne");
		String supervisorName = panel.getSelectedComboBoxStaff();
		staff2.setSupervisor(new Staff(supervisorName));
		panel.addStaff(staff2);
		JTable table = panel.getTable(StaffPanel.STAFF_TABLE_NAME);
		StaffTableModel model = (StaffTableModel) table.getModel();
		assertEquals(2, model.getRowCount());
		assertEquals(staff2.getName(), panel.getStaff(1).getName());
		assertEquals(staff.getName(), panel.getStaff(1).getSupervisor().getName());
	}
	/**最后一个测试*/
	@Test
	public void testApplyButton(){
		//1.增加请假申请按钮
		assertButtonTest(StaffPanel.APPLY_BUTTON_NAME, StaffPanel.APPLY_BUTTON_TEXT);
		//2.选中一个员工后申请按钮才可点击
		//3.点击后弹出LeaveDateDialog对话框
	}

	@Test
	public void testAddStaff2(){
		//数据结构换成staffTableModel
		staff.setSupervisor(supervisor);
		panel.addStaff(staff);
		JTable table = panel.getTable(StaffPanel.STAFF_TABLE_NAME);
		StaffTableModel model = (StaffTableModel) table.getModel();
		assertSame(staff, model.get(0));
	}
	
	@Test
	public void testAddButtonClick(){
		//添加员工是两个事件：1.通知业务类，2.界面显示新员工
		JButton button = panel.getButton(StaffPanel.ADD_BUTTON_NAME);
		wasClicked = false;
		panel.addStaffAddListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wasClicked = true;
			}
		});
		button.doClick();
		assertTrue(wasClicked);
	}
	
	@Test
	public void testChooseStaffOnTable(){
		staff.setSupervisor(supervisor);
		panel.addStaff(staff);
		JTable table = panel.getTable(StaffPanel.STAFF_TABLE_NAME);
		StaffTableModel model = (StaffTableModel) table.getModel();
		//robot无法模拟鼠标
	}
	
	@Test
	public void testAddStaff(){
		//第二个事件：界面显示新员工，稍后加上员工的上级
		staff.setSupervisor(supervisor);
		panel.addStaff(staff);
		assertEquals("Peter", panel.getStaff(0).getName());
		assertEquals("Anne", panel.getStaff(0).getSupervisor().getName());
	}
	
	@Test
	public void testOnAndOffButton(){
		panel.setEnabled(StaffPanel.ADD_BUTTON_NAME, false);
		assertFalse(panel.getButton(StaffPanel.ADD_BUTTON_NAME).isEnabled());
		panel.setEnabled(StaffPanel.ADD_BUTTON_NAME, true);
		assertTrue(panel.getButton(StaffPanel.ADD_BUTTON_NAME).isEnabled());
	}
	
	@Test
	public void testAddListener(){
		KeyListener listener = new KeyAdapter() {};
		panel.addFieldListener(StaffPanel.ADDSTAFF_FIELD_NAME, listener);
		JTextField field = panel.getField(StaffPanel.ADDSTAFF_FIELD_NAME);
		KeyListener[] listeners = field.getKeyListeners();
		assertEquals(1, listeners.length);
		assertSame(listener, listeners[0]);
	}
	
	private void assertEmptyField(String addstaffFieldName) {
		JTextField field = panel.getField(addstaffFieldName);
		assertEquals("", field.getText());
	}

	private void assertButtonTest(String addButtonName, String addButtonText) {
		JButton button = panel.getButton(addButtonName);
		assertEquals(addButtonText, button.getText());
	}

	private void assertLabelTest(String labelName, String labelText) {
		JLabel label = panel.getLabel(labelName);
		assertEquals(labelText, label.getText());
	}
	
}
