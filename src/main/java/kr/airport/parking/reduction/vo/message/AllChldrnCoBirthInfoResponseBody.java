package kr.airport.parking.reduction.vo.message;

public class AllChldrnCoBirthInfoResponseBody {
	
	private String orgCode;
	private String id;
	private String name;
	private String childrenCnt;
	private String birthDay;
	private String serviceResult;
	
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChildrenCnt() {
		return childrenCnt;
	}
	public void setChildrenCnt(String childrenCnt) {
		this.childrenCnt = childrenCnt;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public String getServiceResult() {
		return serviceResult;
	}
	public void setServiceResult(String serviceResult) {
		this.serviceResult = serviceResult;
	}
	
	
	
}
