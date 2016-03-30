package leaveApplicationSys;
/**
 * 申请处理抽象类
 * 还有设置等级
 */
public abstract class Handler {
	private Handler handler;//上级的引用
	private String startDate;
	private String endDate;

	protected Handler getHandler() {
		return handler;
	}

	protected void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public abstract void handleRequest(LeaveRequest request);

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
