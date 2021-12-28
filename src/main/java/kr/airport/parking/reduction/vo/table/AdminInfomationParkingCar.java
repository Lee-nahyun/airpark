package kr.airport.parking.reduction.vo.table;

import java.sql.Timestamp;

public class AdminInfomationParkingCar extends SoapXml {
	
	private String airportCode;						//AIRPORT_CODE 공항코드
	private String iId;								//I_ID 순번
	private String iLotArea;						//I_LOT_AREA 주차장번호
	private String acPlate1;						//AC_PLATE1 차량번호
	private String carNo;							//차량번호	
	
	private String ifType;
	private String ifDt;
	private String ifTime;
	private String ifRsltYn;
	
	private String reductionYn;
	
	private String tsCarYn;
	private String disabledCarYn;
	private String meritCarYn;
	private String lowPollutionCarYn;
	private String lowPoulltionCode;
	private String bigCarYn;
	
	private Timestamp regDate;				
	private Boolean errorChk;
	
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getiId() {
		return iId;
	}
	public void setiId(String iId) {
		this.iId = iId;
	}
	public String getiLotArea() {
		return iLotArea;
	}
	public void setiLotArea(String iLotArea) {
		this.iLotArea = iLotArea;
	}
	public String getAcPlate1() {
		return acPlate1;
	}
	public void setAcPlate1(String acPlate1) {
		this.acPlate1 = acPlate1;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getIfType() {
		return ifType;
	}
	public void setIfType(String ifType) {
		this.ifType = ifType;
	}
	public String getIfDt() {
		return ifDt;
	}
	public void setIfDt(String ifDt) {
		this.ifDt = ifDt;
	}
	public String getIfTime() {
		return ifTime;
	}
	public void setIfTime(String ifTime) {
		this.ifTime = ifTime;
	}
	public String getIfRsltYn() {
		return ifRsltYn;
	}
	public void setIfRsltYn(String ifRsltYn) {
		this.ifRsltYn = ifRsltYn;
	}
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	public Boolean getErrorChk() {
		return errorChk;
	}
	public void setErrorChk(Boolean errorChk) {
		this.errorChk = errorChk;
	}
	public String getReductionYn() {
		return reductionYn;
	}
	public void setReductionYn(String reductionYn) {
		this.reductionYn = reductionYn;
	}
	public String getTsCarYn() {
		return tsCarYn;
	}
	public void setTsCarYn(String tsCarYn) {
		this.tsCarYn = tsCarYn;
	}
	public String getDisabledCarYn() {
		return disabledCarYn;
	}
	public void setDisabledCarYn(String disabledCarYn) {
		this.disabledCarYn = disabledCarYn;
	}
	public String getMeritCarYn() {
		return meritCarYn;
	}
	public void setMeritCarYn(String meritCarYn) {
		this.meritCarYn = meritCarYn;
	}
	public String getLowPollutionCarYn() {
		return lowPollutionCarYn;
	}
	public void setLowPollutionCarYn(String lowPollutionCarYn) {
		this.lowPollutionCarYn = lowPollutionCarYn;
	}
	public String getLowPoulltionCode() {
		return lowPoulltionCode;
	}
	public void setLowPoulltionCode(String lowPoulltionCode) {
		this.lowPoulltionCode = lowPoulltionCode;
	}
	public String getBigCarYn() {
		return bigCarYn;
	}
	public void setBigCarYn(String bigCarYn) {
		this.bigCarYn = bigCarYn;
	}	
}
