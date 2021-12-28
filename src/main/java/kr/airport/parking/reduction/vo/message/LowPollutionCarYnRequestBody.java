package kr.airport.parking.reduction.vo.message;

public class LowPollutionCarYnRequestBody {
		
	private String reqId;	
	private String carNumber;
	
	
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	
	

}
