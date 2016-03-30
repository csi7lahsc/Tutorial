package leaveApplicationSys.test.testI;

import static org.junit.Assert.*;
import leaveApplicationSys.Staff;
import leaveApplicationSys.ui.StaffTableModel;

import org.junit.Before;
import org.junit.Test;
/**
 * 自定义实现的model的测试
 */
public class StaffTableModelTest {
	private StaffTableModel model;

	@Before
	public void setUp(){
		model = new StaffTableModel();
	}
	
	@Test
	public void testCreate(){
		assertEquals(0, model.getRowCount());//默认初始行数
		assertEquals(2, model.getColumnCount());//默认初始列数
		assertEquals(StaffTableModel.STAFFTITLE, model.getColumnName(0));
		assertEquals(StaffTableModel.SUPERVISORTITLE, model.getColumnName(1));
	}
	
	@Test
	public void testAddRow(){
		Staff staff = new Staff("Peter");
		staff.setSupervisor(new Staff("Anne"));
		model.add(staff);
		assertEquals(1, model.getRowCount());
		final int row = 0;//第一行
		assertEquals("Peter", model.getValueAt(row, 0));
		assertEquals("Anne", model.getValueAt(row, 1));
	}
	
	@Test
	public void testRemoveRow(){
		Staff staff = new Staff("Peter");
		staff.setSupervisor(new Staff("Anne"));
		model.add(staff);
		assertEquals(1, model.getRowCount());
		//最后界面上是通过点击列表上的行来删除员工的，不是通过输入
		model.remove(0);//TODO 要不要入参，参数类型是什么待定
		assertEquals(0, model.getRowCount());
	}
	
	@Test
	public void testAddDirector(){
		//没有上级时设定为Null，而不是为空字符串""
		Staff boss = new Staff("Boss");
		boss.setSupervisor(null);
		model.add(boss);
		assertEquals(1, model.getRowCount());
		final int row = 0;
		assertEquals("Boss", model.getValueAt(row, 0));
		assertNull(model.getValueAt(row, 1));
	}
}
