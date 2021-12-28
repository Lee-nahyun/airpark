package kr.airport.parking.reduction.service;

import kr.airport.parking.reduction.vo.table.AdminInfomationParkingCar;
import kr.airport.parking.reduction.vo.table.RealTimeParkingCar;

/**
 * 즉시감면서비스  관련 Job Service
 *
 *
 * @version 1.0
 * @Project : RightShare
 * @Name :  RightShareService
 * @Desc :  즉시감면서비스 Service
 *
 */
public interface RightShareService {

		
	/**
	 * @Comment("즉시감면 대상 자격여부 처리 경차")
	 * 
	 */
	public void procReductCarInfo(String siteId); 
	/**
	 * @Comment("즉시감면 대상 자격여부 처리 웹호출-경차여부")
	 * 
	 */
	public AdminInfomationParkingCar getReductionTsCarInfo(RealTimeParkingCar realTimeParkingCar); 
}
