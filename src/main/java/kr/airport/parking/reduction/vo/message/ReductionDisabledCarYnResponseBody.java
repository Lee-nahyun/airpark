package kr.airport.parking.reduction.vo.message;

public class ReductionDisabledCarYnResponseBody {
	
	private String carNo;
	private String tgtrNm;
	private String obsLvCla;
	private String inqrDt;
	private String qufyYn;
	
	
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getObsLvCla() {
		return obsLvCla;
	}
	public void setObsLvCla(String obsLvCla) {
		this.obsLvCla = obsLvCla;
	}
	public String getTgtrNm() {
		return tgtrNm;
	}
	public void setTgtrNm(String tgtrNm) {
		this.tgtrNm = tgtrNm;
	}
	public String getInqrDt() {
		return inqrDt;
	}
	public void setInqrDt(String inqrDt) {
		this.inqrDt = inqrDt;
	}
	public String getQufyYn() {
		return qufyYn;
	}
	public void setQufyYn(String qufyYn) {
		this.qufyYn = qufyYn;
	}

	
	
}
