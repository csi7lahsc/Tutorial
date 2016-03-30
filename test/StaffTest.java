package leaveApplicationSys.test;

import junit.framework.TestCase;
import leaveApplicationSys.Staff;

/**
 * 员工类测试
 */
public class StaffTest extends TestCase{
	private Staff staff;
	private Staff staffB;
	
	protected void setUp(){
		staff = new Staff("Peter");
		staffB = new Staff("Anne");
	}
	public void testCreateStaff(){
		assertEquals("Peter", staff.getName());
	}
	//员工分等级，一个员工是另一个的上级
	public void testSupervisor(){
		Staff s = new Staff("S");
		Staff t = new Staff("T");
//		hr.add(s);
//		hr.add(t);
		//TODO 新建员工时确定等级还是加入人事时添加等级
		t.setSupervisor(s);//s是t的上级
		//没有设置上级的即为boss
		assertEquals(s, t.getSupervisor());
//		Date startDate = new Date();
//		Date endDate = new Date(startDate.getTime()+24*3600);
//		//封装了请假日期的请求
//		Request request = new Request(startDate,endDate);
//		t.handleRequest(request);
//		//临时测试：s打印请求并附上新增的字符串
	}
	
	public void testStaffEquals(){
		Staff s = new Staff("S");
		Staff s2 = new Staff("S");
		assertEquals(s, s2);
	}
}
