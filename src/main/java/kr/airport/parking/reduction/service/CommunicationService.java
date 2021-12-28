package kr.airport.parking.reduction.service;

public interface CommunicationService {
	
	public String communicate(String serviceUrl,String requestXml,String keyString,String serviceName) throws Exception;
	public String communicate(String serviceUrl,String requestXml,String keyString,String serviceName,String siteId,String carNo) throws Exception;
}
