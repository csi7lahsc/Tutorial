package leaveApplicationSys.test.testI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import leaveApplicationSys.Staff;
import leaveApplicationSys.ui.LeaveDateDialog;

import org.junit.Before;
import org.junit.Test;

/**
 * 申请请假日期对话框的测试
 */
public class LeaveDateDialogTest {
	private LeaveDateDialog dialog;
	
	@Before
	public void setUp(){
		dialog = new LeaveDateDialog(new Staff("jack"));
	}

	@Test
	public void testCreate(){
		assertLabelTest(LeaveDateDialog.STARTDATE_LABEL, LeaveDateDialog.STARTDATE_LABEL_TEXT);
		assertEmptyField(LeaveDateDialog.STARTDATE_FIELD);
		assertLabelTest(LeaveDateDialog.ENDDATE_LABEL, LeaveDateDialog.ENDDATE_LABEL_TEXT);
		assertEmptyField(LeaveDateDialog.ENDDATE_FIELD);
		assertButtonTest(LeaveDateDialog.COMFIRM_BUTTON_NAME, LeaveDateDialog.COMFIRM_BUTTON_TEXT);
		assertButtonTest(LeaveDateDialog.CANCEL_BUTTON_NAME, LeaveDateDialog.CANCEL_BUTTON_TEXT);
	}
	
	@Test
	public void testCancelToCloseDialog(){
		LeaveDateDialog.show(dialog);
		assertTrue(dialog.isVisible());
		JButton cancelButton = dialog.getButton(LeaveDateDialog.CANCEL_BUTTON_NAME);
		cancelButton.doClick();
		assertFalse(dialog.isVisible());
	}
	
	@Test
	public void testComfirmToSaveTextAndApply(){
		Staff peter = new Staff("peter");
		peter.setSupervisor(new Staff("anne"));
		dialog = new LeaveDateDialog(peter);
		//1.取得两个日期字符串并保存到对应员工对象
		dialog.setText(LeaveDateDialog.STARTDATE_FIELD, "2001-01-01");
		dialog.setText(LeaveDateDialog.ENDDATE_FIELD, "2001-02-03");
		JButton comfirmButton = dialog.getButton(LeaveDateDialog.COMFIRM_BUTTON_NAME);
		comfirmButton.doClick();
		Staff staff = dialog.getStaff();
		assertEquals(peter, staff);
		assertEquals("anne", staff.getSupervisor().getName());
		assertEquals("2001-01-01", staff.getStartDate());
		assertEquals("2001-02-03", staff.getEndDate());
		//2.保存到table中（可不做）
		//3.调用handleRequest方法并传递两个日期
		//4.handleRequest方法打开一个确认框并显示两个日期
		
	}
	
	//重复代码，待合并
	private void assertEmptyField(String addstaffFieldName) {
		JTextField field = dialog.getField(addstaffFieldName);
		assertEquals("", field.getText());
	}

	private void assertButtonTest(String addButtonName, String addButtonText) {
		JButton button = dialog.getButton(addButtonName);
		assertEquals(addButtonText, button.getText());
	}

	private void assertLabelTest(String labelName, String labelText) {
		JLabel label = dialog.getLabel(labelName);
		assertEquals(labelText, label.getText());
	}
}
