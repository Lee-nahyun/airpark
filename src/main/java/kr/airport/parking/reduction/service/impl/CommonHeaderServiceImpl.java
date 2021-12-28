package kr.airport.parking.reduction.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.airport.parking.reduction.service.CommonHeaderService;
import kr.airport.parking.reduction.vo.message.CommonHeader;

@Service
public class CommonHeaderServiceImpl implements CommonHeaderService{
	
	private static final Logger LOG = LoggerFactory.getLogger(CommonHeaderService.class);

	@Value("#{appConfig['useEncrypt']}")	
	private String useEncrypt;
	
	@Value("#{appConfig['useSystemCode']}")	
	private String useSystemCode;
	
	@Value("#{appConfig['userName']}")	
	private String userName;
	
	@Value("#{appConfig['userDeptCode']}")	
	private String userDeptCode;
	
	
	@Override
	public SOAPHeader makeCommonHeaderToSOAPHeader(SOAPHeader header ,CommonHeader commonHeader) {
		
		
		try {
			
			QName qname = new QName(commonHeader.getNameSpace(), "commonHeader");
		    SOAPHeaderElement headerElement = header.addHeaderElement(qname);
		    headerElement.addChildElement("serviceName").addTextNode(commonHeader.getServiceName());
		    headerElement.addChildElement("useSystemCode").addTextNode(commonHeader.getUseSystemCode());
		    headerElement.addChildElement("certServerId").addTextNode(commonHeader.getServerId());
		    headerElement.addChildElement("transactionUniqueId").addTextNode(commonHeader.getTransactionUniqueId());
		    headerElement.addChildElement("userDeptCode").addTextNode(commonHeader.getUserDeptCode());
			headerElement.addChildElement("userName").addTextNode("#userName#");
			
			
		} catch (SOAPException soapException) {
			LOG.error(soapException.getMessage());
		
		} catch (NullPointerException nullException) {
			LOG.error(nullException.getMessage());
			
		} catch (Exception e) {
			LOG.error(e.getMessage());
		
		}				
		return header;
	}


	
	/*
	 * 수신용 TransactionUniqueKey 생성 
	 */	
	@Override
	public String getTransactionUniqueIdFromTime()
	{
		String rnd1 = Double.toString(java.lang.Math.random()).substring(2, 6);
		String rnd2 = Double.toString(java.lang.Math.random()).substring(2, 6);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.KOREA);
		String cur = sdf.format(new Date());
		
		LOG.info(" TransactionUniqueId Make [" + cur + rnd1 + rnd2 + "]");
		return cur + rnd1 + rnd2;
	}
	

}
