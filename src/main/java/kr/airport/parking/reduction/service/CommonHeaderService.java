package kr.airport.parking.reduction.service;

import javax.xml.soap.SOAPHeader;

import kr.airport.parking.reduction.vo.message.CommonHeader;

public interface CommonHeaderService {

	public SOAPHeader makeCommonHeaderToSOAPHeader(SOAPHeader header,CommonHeader commonHeader);
	
	public String getTransactionUniqueIdFromTime();
}
