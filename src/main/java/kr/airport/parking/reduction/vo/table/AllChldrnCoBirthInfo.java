package kr.airport.parking.reduction.vo.table;


public class AllChldrnCoBirthInfo extends SoapXml {
	
	private String airportCode;
	private String id;
	private String name;
	
	
	private String ifRsltYn;
	private String ifTime;
	private String ifDt;
	private String ifType;
	private Boolean errorChk;
	
	private String reductionYn;
	
	private String childrenCnt;
	private String birthDay;
	private String serviceResult;
	private String serviceResultName;
	
	
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
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
	public String getReductionYn() {
		return reductionYn;
	}
	public String getIfRsltYn() {
		return ifRsltYn;
	}
	public void setIfRsltYn(String ifRsltYn) {
		this.ifRsltYn = ifRsltYn;
	}
	public String getIfTime() {
		return ifTime;
	}
	public void setIfTime(String ifTime) {
		this.ifTime = ifTime;
	}
	public String getIfDt() {
		return ifDt;
	}
	public void setIfDt(String ifDt) {
		this.ifDt = ifDt;
	}
	public String getIfType() {
		return ifType;
	}
	public void setIfType(String ifType) {
		this.ifType = ifType;
	}
	public Boolean getErrorChk() {
		return errorChk;
	}
	public void setErrorChk(Boolean errorChk) {
		this.errorChk = errorChk;
	}
	public void setReductionYn(String reductionYn) {
		this.reductionYn = reductionYn;
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
	public String getServiceResultName() {
		return serviceResultName;
	}
	public void setServiceResultName(String serviceResultName) {
		this.serviceResultName = serviceResultName;
	}
	
	
	
}
