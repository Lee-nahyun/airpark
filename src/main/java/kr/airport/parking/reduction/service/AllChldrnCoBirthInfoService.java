package kr.airport.parking.reduction.service;

import kr.airport.parking.reduction.vo.table.AllChldrnCoBirthInfo;

public interface AllChldrnCoBirthInfoService {

	public AllChldrnCoBirthInfo proc(AllChldrnCoBirthInfo allChldrnCoBirthInfo, Boolean dbProc, AllChldrnCoBirthInfo beforeAllChldrnCoBirthInfo);
}

