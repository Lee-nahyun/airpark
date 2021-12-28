package kr.airport.parking.reduction.service;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public interface SoapService {
	
	public SOAPMessage makeSoapMassage() throws SOAPException,NullPointerException,Exception;
	public String getXmlStringFromSoapMessageObject(SOAPMessage soapMsg)  throws SOAPException,NullPointerException,Exception;
	public SOAPBodyElement makeBodyElement(SOAPEnvelope envelope,String methodName,String nameSpace)  throws SOAPException,NullPointerException,Exception;
	public String requestMessageEncrypt(String methodName,String nameSpace,String xml,String targetServerId) throws Exception;
	public String responseMessageDecrypt(String methodName,String nameSpace,String xml) throws Exception;
}
