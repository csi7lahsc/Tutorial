package leaveApplicationSys.ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import leaveApplicationSys.Staff;

/**
 * 自定义TableModel类
 * 因为使用list,表格显示顺序和list保存顺序一致，添加删除方法就默认索引号一致
 * 需要的是:在删除某个员工后，遍历其他所有员工，将其上级为此删除员工的改成此删除员工的上级
 */
public class StaffTableModel extends AbstractTableModel{

	public static final String STAFFTITLE = "staff name";
	public static final String SUPERVISORTITLE = "supervisor name";
	private String[] titles = {STAFFTITLE, SUPERVISORTITLE};
	private List<Staff> staffs = new ArrayList<Staff>();

	@Override
	public int getRowCount() {
		return staffs.size();
	}

	@Override
	public int getColumnCount() {
		return titles.length;
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		Staff staff = staffs.get(rowIndex);
		String fieldName = titles[columnIndex];
		if(fieldName.equals(STAFFTITLE))
			return staff.getName();
		else if(fieldName.equals(SUPERVISORTITLE)){
			if(staff.getSupervisor() == null)
				return null;
			return staff.getSupervisor().getName();
		}
		return null;
	}

	@Override
	public String getColumnName(int column){
		return titles[column];
	}

	public void add(Staff staff) {
		staffs.add(staff);
		fireTableRowsInserted(staffs.size()-1, staffs.size());//在界面上显示
	}

	public Staff get(int i) {
		return staffs.get(i);
	}

	public Staff remove(int index) {
		Staff removedStaff = staffs.get(index);
		staffs.remove(index);
		fireTableRowsUpdated(staffs.size()-1, staffs.size());//刷新table界面
		fireTableRowsDeleted(staffs.size()-1, staffs.size());//删除多余的行
		return removedStaff;
	}

	public Staff getStaffByName(String name) {
		for (Staff s : staffs) {
			if(name.equals(s.getName())){
				return s;
			}
		}
		return null;
	}
	
}
