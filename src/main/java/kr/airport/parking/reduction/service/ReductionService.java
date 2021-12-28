package kr.airport.parking.reduction.service;

import kr.airport.parking.reduction.vo.table.AdminInfomationParkingCar;
import kr.airport.parking.reduction.vo.table.AllChldrnCoBirthInfo;
import kr.airport.parking.reduction.vo.table.RealTimeParkingCar;

/**
 * 즉시감면서비스  관련 Job Service
 *
 *
 * @version 1.0
 * @Project : Reduction
 * @Name :  ReductionService
 * @Desc :  즉시감면서비스 Service
 */
public interface ReductionService {

		
		
	/**
	 * @Comment("즉시감면 대상 자격여부 처리 웹호출-경차여부")
	 * 
	 */
	public AdminInfomationParkingCar getReductionTsCarInfo(RealTimeParkingCar realTimeParkingCar);
	
	/**
	 * @Comment("즉시감면 대상 자격여부 처리 웹호출-경차여부-신규")
	 * 
	 */
	public AdminInfomationParkingCar getReductionTsCarNewInfo(RealTimeParkingCar realTimeParkingCar);	
	
	/**
	 * @Comment("즉시감면 대상 자격여부 처리 웹호출-저공해차량여부")
	 * 
	 */
	public AdminInfomationParkingCar getLowPollutionCarYn(RealTimeParkingCar realTimeParkingCar);
	
	/**
	 * @Comment("즉시감면 대상 자격여부 처리 웹호출-(신규)저공해차량여부")
	 * 
	 */
	public AdminInfomationParkingCar getNewLowPollutionCarYn(RealTimeParkingCar realTimeParkingCar);

	/**
	 * @Comment("즉시감면 대상 자격여부 처리 웹호출-국가유공자차량여부")
	 * 
	 */
	public AdminInfomationParkingCar getMeritCarYn(RealTimeParkingCar realTimeParkingCar);
	
	/**
	 * @Comment("즉시감면 대상 자격여부 처리 웹호출-장애인여부")
	 * 
	 */
	public AdminInfomationParkingCar getReductionDisabledCarYn(RealTimeParkingCar realTimeParkingCar);
	
	/**
	 * @Comment("즉시감면 대상 자격여부 처리 웹호출-다자녀정보")
	 * 
	 */
	public AllChldrnCoBirthInfo getAllChldrnCoBirthInfo(AllChldrnCoBirthInfo allChldrnCoBirthInfo);
	
	public void callReductionProc(String airportCode, String iId, String carNo);
	
}
