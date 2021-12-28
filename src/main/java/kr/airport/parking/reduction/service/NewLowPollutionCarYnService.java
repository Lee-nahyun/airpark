package kr.airport.parking.reduction.service;

import kr.airport.parking.reduction.vo.table.AdminInfomationParkingCar;
import kr.airport.parking.reduction.vo.table.RealTimeParkingCar;

public interface NewLowPollutionCarYnService {

	public AdminInfomationParkingCar proc(RealTimeParkingCar realTimeParkingCar,Boolean dbProc,AdminInfomationParkingCar beforeAdminInfomationParkingCar);
}

