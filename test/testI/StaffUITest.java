package leaveApplicationSys.test.testI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import leaveApplicationSys.Staff;
import leaveApplicationSys.ui.StaffPanel;
import leaveApplicationSys.ui.StaffUI;
import leaveApplicationSys.ui.Util;

import org.junit.Before;
import org.junit.Test;

/**
 * 员工请假系统界面类测试
 */
public class StaffUITest {
	private StaffUI staffUI;
	private JFrame frame;
	private StaffPanel panel;
	private Robot robot;
	@Before
	public void setUp() throws AWTException{
		staffUI = new StaffUI();
		frame = staffUI.getFrame();
		panel = (StaffPanel) Util.getComponent(frame, StaffPanel.NAME);
		robot = new Robot();
	}
	@Test
	public void testCreate(){
		final double tolerance = 0.05;
		assertEquals(StaffUI.WIDTH, frame.getSize().getWidth(), tolerance);
		assertEquals(StaffUI.HEIGHT, frame.getSize().getHeight(), tolerance);
		assertEquals(JFrame.EXIT_ON_CLOSE, frame.getDefaultCloseOperation());
		assertTrue(containsStaffPanel(frame));
		assertNotNull(Util.getComponent(frame, StaffPanel.NAME));
		assertEquals(StaffUI.TITLE, frame.getTitle());
	}
	
	@Test
	public void testAddStaff(){
		//完整的添加新员工测试，作为用户测试
		/* 不应该在测试类中依赖太多的类，用户测试应该是黑盒测试
		 * 将中间的各种调用方法封装在简明直观的方法里
		 */
		panel = (StaffPanel) Util.getComponent(frame, StaffPanel.NAME);
		panel.setText(StaffPanel.ADDSTAFF_FIELD_NAME, "Peter");//输入框输入员工名
//		panel.setText(StaffPanel.ADDSUPERVISOR_FIELD_NAME, "Anne");//输入框输入员工的上级
		JButton button = panel.getButton(StaffPanel.ADD_BUTTON_NAME);
		button.setEnabled(true);//将按钮设置为开启
		button.doClick();//单击按钮后将Staff对象加入到list中
		Staff staff = panel.getStaff(0);//从list中取出Staff对象
		assertEquals("Peter", staff.getName());
//		assertEquals("Anne", staff.getSupervisor().getName());
		assertFalse(button.isEnabled());//添加员工后清空输入框
		assertEquals("", panel.getText(StaffPanel.ADDSTAFF_FIELD_NAME));
//		assertEquals("", panel.getText(StaffPanel.ADDSUPERVISOR_FIELD_NAME));
		panel.setText(StaffPanel.ADDSTAFF_FIELD_NAME, "Anne");//第二位员工
		//comboBox默认已选中第一个员工
		button.setEnabled(true);
		button.doClick();
		Staff staff2 = panel.getStaff(1);
		assertEquals("Anne", staff2.getName());
		assertEquals("Peter", staff2.getSupervisor().getName());
	}
	
	@Test
	public void addFirstStaff(){
		//添加第一个员工默认为director,不用输入上级即可添加
//		panel = (StaffPanel) Util.getComponent(frame, StaffPanel.NAME);
//		panel.setText(StaffPanel.ADDSTAFF_FIELD_NAME, "Boss");//输入框输入员工名,无法触发监听
//		JButton button = panel.getButton(StaffPanel.ADD_BUTTON_NAME);
//		assertTrue(button.isEnabled());
//		button.doClick();
//		Staff staff = panel.getStaff(0);//从list中取出Staff对象
//		assertEquals("Boss", staff.getName());
//		//以后添加的员工都必须输入上级
//		panel.setText(StaffPanel.ADDSTAFF_FIELD_NAME, "Peter");
//		assertFalse(button.isEnabled());
	}
	
	@Test
	public void testIsStaffExist(){
		
	}
	
	@Test
	public void testDeleteStaff(){
		
	}
	
	/**robot模拟鼠标键盘无法自动化测试*/
//	@Test
//	public void testKeyListener(){
//		staffUI.show();//必须显示界面才能使用robot
//		JButton button = panel.getButton(StaffPanel.ADD_BUTTON_NAME);
//		assertFalse(button.isEnabled());//默认初始状态按钮关闭
//		selectField(StaffPanel.ADDSTAFF_FIELD_NAME);
//		type('b');
//		selectField(StaffPanel.ADDSUPERVISOR_FIELD_NAME);
//		type('r');
//		assertTrue(button.isEnabled());
//	}
	
//	private void type(int c) {
//		robot.keyPress(c);
//		robot.keyRelease(c);
//	}
//	private void selectField(String fieldName) {
//		JTextField field = panel.getField(fieldName);
//		Point point = field.getLocationOnScreen();
//		robot.mouseMove(point.x, point.y);
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//	}
	@Test
	public void testSetStaffSupervisor(){
		//第一个员工默认为director，以后新增员工都要指定上级
		
	}

	private boolean containsStaffPanel(JFrame frame) {
		for(Component component: frame.getContentPane().getComponents()){
			if(component instanceof StaffPanel){
				return true;
			}
		}
		return false;
	}
}
