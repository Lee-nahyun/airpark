package kr.airport.parking.reduction.vo.message;

public class LowPollutionCarYnResponseBody {
	

	private String lowPoulltionCar;
	
	/*
	 * 	1 : 1종
		2 : 2종
		3 : 3종
		4 : 4종(저공해차 아님)
		5 : 조회결과 없음
		6 : 차량번호 오류
		7 : 연계기관코드 에러
		8 : 수도권외일 경우 2013-05-23이전차량
		9 : 조례미재정
		0 : 시스템 에러
	 */
	private String lowPoulltionCode;

	public String getLowPoulltionCar() {
		return lowPoulltionCar;
	}

	public void setLowPoulltionCar(String lowPoulltionCar) {
		this.lowPoulltionCar = lowPoulltionCar;
	}

	public String getLowPoulltionCode() {
		return lowPoulltionCode;
	}

	public void setLowPoulltionCode(String lowPoulltionCode) {
		this.lowPoulltionCode = lowPoulltionCode;
	}	

}
