package leaveApplicationSys;
/**
 * 请假请求信息实体类
 */
public class LeaveRequest {
	private String applicantName;
	private String startDate;
	private String endDate;
	
	public LeaveRequest(String name, String startDate, String endDate) {
		this.applicantName = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
