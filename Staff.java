package leaveApplicationSys;

import javax.swing.JOptionPane;

/**
 * 员工类
 */
public class Staff extends Handler{

	private String name;
	
	public Staff(String name) {
		this.setName(name);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	/**设置上级*/
	public void setSupervisor(Staff staff) {
		setHandler(staff);
	}
	/**返回上级对象*/
	public Staff getSupervisor(){
		return (Staff) getHandler();
	}

	/**使用一个实体类包装申请者姓名，开始日期，结束日期，
	 * 将此实体类传入此方法，弹出确认对话框来做确认，
	 * 根据结果判断是继续调用还是终止，
	 * 直到弹出消息对话框*/
	@Override
	public void handleRequest(LeaveRequest request) {
		System.out.println("the dates:"
				+request.getStartDate()+" and "
				+request.getEndDate()+ " .");
		boolean isAgree = false;
		//如果此员工有上级，让上级调用此方法(将消息传给上级)
		if(getHandler() != null){
			isAgree = createComfirmDialog(request);
			if(isAgree){
				System.out.println(getSupervisor().name+" is agree.");
				getHandler().handleRequest(request);
			}else{
				createNoticeDialog("apply failed!");
				System.out.println("apply failed!");
			}
		}else {
			//TODO 弹窗提示通过，保存日期到table
			createNoticeDialog("apply successed!");
			System.out.println("apply successed!");
		}
	}

	private void createNoticeDialog(String message) {
		JOptionPane.showMessageDialog(null, message,
				"notice", JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean createComfirmDialog(LeaveRequest request) {
		int isAgree = 0;
		isAgree = JOptionPane.showConfirmDialog(null,
				"the apply dates for "+request.getApplicantName()+" are:\n"+
				request.getStartDate()+" to "+ request.getEndDate(),
				"Apply Leave Dates", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		return isAgree == JOptionPane.YES_OPTION;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Staff other = (Staff) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
