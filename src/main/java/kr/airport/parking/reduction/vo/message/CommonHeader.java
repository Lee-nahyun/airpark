package kr.airport.parking.reduction.vo.message;

public class CommonHeader {
	
	private String serverId;
	private String serviceName;
	private String nameSpace;
	private String userName;
	private String userDeptCode;
	private String useSystemCode;
	private String transactionUniqueId;
	private String methodName;
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getUserDeptCode() {
		return userDeptCode;
	}
	public void setUserDeptCode(String userDeptCode) {
		this.userDeptCode = userDeptCode;
	}
	public String getUseSystemCode() {
		return useSystemCode;
	}
	public void setUseSystemCode(String useSystemCode) {
		this.useSystemCode = useSystemCode;
	}
	public String getTransactionUniqueId() {
		return transactionUniqueId;
	}
	public void setTransactionUniqueId(String transactionUniqueId) {
		this.transactionUniqueId = transactionUniqueId;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
