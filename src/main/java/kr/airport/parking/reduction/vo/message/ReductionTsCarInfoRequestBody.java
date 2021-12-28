package kr.airport.parking.reduction.vo.message;

public class ReductionTsCarInfoRequestBody {
	
	
	private String cntcInfoCode;	
	private String chargerId;
	private String chargerNm;
	private String chargerIpAdres;
	private String vhrNo;
	
	
	public String getChargerNm() {
		return chargerNm;
	}
	public void setChargerNm(String chargerNm) {
		this.chargerNm = chargerNm;
	}
	public String getChargerIpAdres() {
		return chargerIpAdres;
	}
	public void setChargerIpAdres(String chargerIpAdres) {
		this.chargerIpAdres = chargerIpAdres;
	}
	public String getVhrNo() {
		return vhrNo;
	}
	public void setVhrNo(String vhrNo) {
		this.vhrNo = vhrNo;
	}
	public String getCntcInfoCode() {
		return cntcInfoCode;
	}
	public void setCntcInfoCode(String cntcInfoCode) {
		this.cntcInfoCode = cntcInfoCode;
	}
	public String getChargerId() {
		return chargerId;
	}
	public void setChargerId(String chargerId) {
		this.chargerId = chargerId;
	}
	
}
