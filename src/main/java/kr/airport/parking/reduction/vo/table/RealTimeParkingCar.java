package kr.airport.parking.reduction.vo.table;

import java.util.Date;

public class RealTimeParkingCar {
	
	private String airportCode;						//AIRPORT_CODE 공항코드
	private String iId;								//I_ID 순번
	private String iLotArea;						//I_LOT_AREA 주차장번호
	private String acPlate1;						//AC_PLATE1 차량번호
	private String carNo;							//차량번호						
	private String iTicket;							//I_TICKET 정기권카드번호
	private Date dtInDate;							//DT_IN_DATE 입차일시
	private Date dtOutDate;							//DT_OUT_DATE 출차일시
	private String iRate;							//I_RATE 요금종별 ( 감면 대상 종류 코드  )
	private String acRateKeyName;					//AC_RATE_KEY_NAME 요금_종별_명칭
	private String dMisc1;							//D_MISC1 기타요금 ( 저공해 차량 1,2종 일 경우 셋팅 )
	
	
	/*작업을 위함 임의 추가 컬럼 */
	private String rightShareYn;
	private Date rightShareDtm;
	
	
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
	public String getiTicket() {
		return iTicket;
	}
	public void setiTicket(String iTicket) {
		this.iTicket = iTicket;
	}
	public Date getDtInDate() {
		return dtInDate;
	}
	public void setDtInDate(Date dtInDate) {
		this.dtInDate = dtInDate;
	}
	public Date getDtOutDate() {
		return dtOutDate;
	}
	public void setDtOutDate(Date dtOutDate) {
		this.dtOutDate = dtOutDate;
	}
	public String getiRate() {
		return iRate;
	}
	public void setiRate(String iRate) {
		this.iRate = iRate;
	}
	public String getAcRateKeyName() {
		return acRateKeyName;
	}
	public void setAcRateKeyName(String acRateKeyName) {
		this.acRateKeyName = acRateKeyName;
	}
	public String getdMisc1() {
		return dMisc1;
	}
	public void setdMisc1(String dMisc1) {
		this.dMisc1 = dMisc1;
	}
	public String getRightShareYn() {
		return rightShareYn;
	}
	public void setRightShareYn(String rightShareYn) {
		this.rightShareYn = rightShareYn;
	}
	public Date getRightShareDtm() {
		return rightShareDtm;
	}
	public void setRightShareDtm(Date rightShareDtm) {
		this.rightShareDtm = rightShareDtm;
	}
	
	
	
}
