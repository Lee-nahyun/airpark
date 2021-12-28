package kr.airport.parking.reduction.service.impl;

import java.io.ByteArrayOutputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.airport.parking.reduction.component.GpkiFactory;
import kr.airport.parking.reduction.service.SoapService;

@Service
public class SoapServiceImpl implements SoapService {

	private static final Logger LOG = LoggerFactory.getLogger(SoapService.class);
	@Autowired
	GpkiFactory gpkiFactory;

	@Value("#{appConfig['SOAP.prefix']}")	
	private String prefix;
	
	
	
	@Override
	public SOAPMessage makeSoapMassage() throws SOAPException,NullPointerException,Exception{


		MessageFactory factory = MessageFactory.newInstance();	
		SOAPMessage soapMsg = factory.createMessage();
		SOAPPart part = soapMsg.getSOAPPart();
		SOAPEnvelope envelope = part.getEnvelope();
		SOAPHeader header = envelope.getHeader();
		SOAPBody body = envelope.getBody();		
		envelope.removeNamespaceDeclaration("SOAP-ENV");
		envelope.removeNamespaceDeclaration("soapenv");
		envelope.removeNamespaceDeclaration("soapenc");
		envelope.removeNamespaceDeclaration("xsi");
		envelope.removeNamespaceDeclaration("xsd");
		envelope.addNamespaceDeclaration("", "http://schemas.xmlsoap.org/soap/envelope/");
		envelope.setPrefix(prefix);
		header.setPrefix(prefix);
		body.setPrefix(prefix);	 
		soapMsg.saveChanges();
		
		return soapMsg;
		
	}

	@Override
	public String getXmlStringFromSoapMessageObject(SOAPMessage soapMsg) throws SOAPException,NullPointerException,Exception{
		String strMsg = null;
		
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
		 
	        soapMsg.writeTo(out);
	        
	        strMsg = new String(out.toByteArray());
	        
	        strMsg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +strMsg;
		
		
		return strMsg;
	}
	
	@Override
	public SOAPBodyElement makeBodyElement(SOAPEnvelope envelope,String methodName,String nameSpace) throws SOAPException,NullPointerException,Exception {
		
		SOAPBody body = envelope.getBody();		
        body.setPrefix(prefix);	        
        SOAPBodyElement element = body.addBodyElement(envelope.createName(methodName, prefix, nameSpace));
       
        return element;
	}
	
	@Override
	public String responseMessageDecrypt(String methodName,String nameSpace,String xml) throws Exception  {
	
		LOG.debug("start");
		String original = xml.split("<"+methodName+"Response xmlns=\"" +nameSpace+"\">")[1].split("</"+methodName+"Response>")[0];		
		LOG.debug(original);
		String decryted =  gpkiFactory.decrypt(original);
//		LOG.debug(decryted);
		xml = xml.replace(original,decryted);
//		LOG.debug(xml);
		LOG.debug("end" );
		return xml;
	}
	
	@Override
	public String requestMessageEncrypt(String methodName,String nameSpace,String xml,String targetServerId) throws Exception  {
		LOG.info("requestMessageEncrypt===========================================");
		LOG.info("methodName : " + methodName);
		LOG.info("nameSpace : " + nameSpace);
		LOG.info("targetServerId : " + targetServerId);
		LOG.info("============================================================");
		String original = xml.split("<"+methodName+" xmlns=\"" +nameSpace+"\">")[1].split("</"+methodName+">")[0];
        String encryted =  gpkiFactory.encrypt( original, targetServerId);
        xml = xml.replace(original, encryted);
		return xml;
	}

}
