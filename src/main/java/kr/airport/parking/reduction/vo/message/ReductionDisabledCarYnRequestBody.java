package kr.airport.parking.reduction.vo.message;

public class ReductionDisabledCarYnRequestBody {

	private String carNo;
	private String reqBizCd;
	private String reqOrgCd;

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getReqOrgCd() {
		return reqOrgCd;
	}

	public void setReqOrgCd(String reqOrgCd) {
		this.reqOrgCd = reqOrgCd;
	}

	public String getReqBizCd() {
		return reqBizCd;
	}

	public void setReqBizCd(String reqBizCd) {
		this.reqBizCd = reqBizCd;
	}
	
	
	
}
