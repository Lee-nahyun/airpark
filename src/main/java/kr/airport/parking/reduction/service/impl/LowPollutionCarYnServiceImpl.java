package kr.airport.parking.reduction.service.impl;

import javax.annotation.Resource;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

//import kr.airport.parking.reduction.mapper.ParkingManageMapper;
import kr.airport.parking.reduction.service.CommonHeaderService;
import kr.airport.parking.reduction.service.CommunicationService;
import kr.airport.parking.reduction.service.LowPollutionCarYnService;
import kr.airport.parking.reduction.service.SoapService;
import kr.airport.parking.reduction.vo.message.CommonHeader;
import kr.airport.parking.reduction.vo.message.LowPollutionCarYnRequestBody;
import kr.airport.parking.reduction.vo.message.LowPollutionCarYnResponseBody;
import kr.airport.parking.reduction.vo.table.AdminInfomationParkingCar;
import kr.airport.parking.reduction.vo.table.RealTimeParkingCar;
import kr.airport.parking.util.common.CommonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LowPollutionCarYnServiceImpl implements LowPollutionCarYnService{
	
	private static final Logger LOG = LoggerFactory.getLogger(LowPollutionCarYnService.class);
	
	
//	@Resource
//	ParkingManageMapper mapper;
	
	@Resource
	CommonHeaderService commonHeaderService;
	
	@Resource
	CommunicationService communicationService;
	
	@Resource
	SoapService soapService;
	

	
	@Value("#{appConfig['useEncrypt']}")	
	private String useEncrypt;
	
	@Value("#{appConfig['useSystemCode']}")	
	private String useSystemCode;
	
	@Value("#{appConfig['userName']}")	
	private String userName;
	
	@Value("#{appConfig['userDeptCode']}")	
	private String userDeptCode;
			
	
	@Value("#{appConfig['lowPollutionCarYn.schedulerYn']}")	
	private String schedulerYn;
	
	@Value("#{appConfig['lowPollutionCarYn.URI']}")	
	private String uri;	
	
	@Value("#{appConfig['lowPollutionCarYn.certServerId']}")	
	private String certServerId;	
		
	@Value("#{appConfig['lowPollutionCarYn.methodName']}")	
	private String methodName;	
	
	@Value("#{appConfig['lowPollutionCarYn.serviceName']}")	
	private String serviceName;
	
	@Value("#{appConfig['lowPollutionCarYn.nameSpace']}")	
	private String nameSpace;
	
	@Value("#{appConfig['lowPollutionCarYn.body.reqId']}")	
	private String reqId;
	
	@Value("#{appConfig['reductionTsCarInfo.targetServerId']}")	
	private String targetServerId;
	

	@Override
	public AdminInfomationParkingCar proc(RealTimeParkingCar realTimeParkingCar,Boolean dbProc,AdminInfomationParkingCar beforeAdminInfomationParkingCar) {
	
		String requestXml;
		String responseXml;
		
		
		String airportCode = realTimeParkingCar.getAirportCode();
		String parkingManageNumber = realTimeParkingCar.getiId();
		String carNo = realTimeParkingCar.getCarNo();
		
		AdminInfomationParkingCar adminInfomationParkingCar = new AdminInfomationParkingCar();
		adminInfomationParkingCar.setAirportCode(airportCode);
		adminInfomationParkingCar.setiId(parkingManageNumber);
		adminInfomationParkingCar.setCarNo(carNo);
		
		adminInfomationParkingCar.setIfRsltYn("N");
		adminInfomationParkingCar.setErrorChk(Boolean.FALSE);
		adminInfomationParkingCar.setIfType("01");

		beforeAdminInfomationParkingCar = adminInfomationParkingCar;
		
		try {
			
			SOAPMessage soapMsg = soapService.makeSoapMassage();
			SOAPHeader header = soapMsg.getSOAPHeader();
			
			/*
			 * SOAP XML HEADER ?????? ????????? ??????
			 */
			CommonHeader commonHeader = new CommonHeader();
			
			commonHeader.setServiceName(serviceName);
			commonHeader.setMethodName(methodName);
			commonHeader.setServerId(certServerId);
			commonHeader.setNameSpace(nameSpace);
			commonHeader.setUserDeptCode(userDeptCode);
			commonHeader.setUserName(userName);
			commonHeader.setUseSystemCode(useSystemCode);
			
			LOG.info("========CommonHeader===========================================================");
			LOG.info("["+carNo+"] LowPollutionCarYn serviceName " + serviceName);
			LOG.info("["+carNo+"] LowPollutionCarYn methodName " + methodName);
			LOG.info("["+carNo+"] LowPollutionCarYn certServerId " + certServerId);
			LOG.info("["+carNo+"] LowPollutionCarYn nameSpace " + nameSpace);
			LOG.info("["+carNo+"] LowPollutionCarYn userDeptCode " + userDeptCode);
			LOG.info("["+carNo+"] LowPollutionCarYn userName " + userName);
			LOG.info("["+carNo+"] LowPollutionCarYn useSystemCode " + useSystemCode);
			LOG.info("===================================================================");
			
			commonHeader.setTransactionUniqueId(commonHeaderService.getTransactionUniqueIdFromTime());
			header = commonHeaderService.makeCommonHeaderToSOAPHeader(header,commonHeader);
			LOG.info("["+carNo+"] LowPollutionCarYn TransactionUniqueId :" + commonHeader.getTransactionUniqueId());
		
			/*
			 * SOAP XML BODY ?????? ??????
			 */
			LowPollutionCarYnRequestBody body = new LowPollutionCarYnRequestBody();
			body.setReqId(reqId);
			body.setCarNumber(carNo);
			
			LOG.info("===================================================================");
			LOG.info("["+carNo+"] LowPollutionCarYn reqId " + reqId);
			LOG.info("["+carNo+"] LowPollutionCarYn CarNumber " + carNo);
			LOG.info("===================================================================");
			
			SOAPPart part = soapMsg.getSOAPPart();
			SOAPBodyElement soapBodyElement = soapService.makeBodyElement(part.getEnvelope(),methodName,nameSpace);
			soapBodyElement = makeLowPollutionCarYnBody(soapBodyElement,body);
			soapMsg.saveChanges();
			
			/*
			 * SOAP XML Object -> String ??????
			 */
			requestXml = soapService.getXmlStringFromSoapMessageObject(soapMsg);
			requestXml = requestXml.replace("#userName#", userName );
			requestXml = requestXml.replace("#carNumber#",body.getCarNumber() );
			adminInfomationParkingCar.setReqSoapXml(requestXml);
//			LOG.debug("["+carNo+"] LowPollutionCarYnService requestXml [" + requestXml + "]");
			
			/*
			 * XML ????????? Body ?????? ????????? ??????
			 */
			if("Y".equals(useEncrypt)) {
				requestXml = soapService.requestMessageEncrypt(methodName, nameSpace, requestXml, targetServerId);
//				LOG.debug("["+carNo+"] LowPollutionCarYnService EnCrypt requestXml [" + requestXml + "]");
			}
			
			/*
			 * Http Client ???????????? ???????????? ??? ?????? Xml ??????
			 */
			responseXml = communicationService.communicate(uri, requestXml, parkingManageNumber, serviceName, airportCode, carNo);
//			LOG.debug("["+carNo+"] LowPollutionCarYnService responseXml [" + responseXml + "]");
			
			if(null == responseXml) {
				beforeAdminInfomationParkingCar.setReqSoapXml(requestXml);
				
				beforeAdminInfomationParkingCar.setIfRsltYn("N");			
				beforeAdminInfomationParkingCar.setIfTime(adminInfomationParkingCar.getIfTime());
				beforeAdminInfomationParkingCar.setIfDt(adminInfomationParkingCar.getIfDt());
				beforeAdminInfomationParkingCar.setIfType("01");	
				beforeAdminInfomationParkingCar.setErrorChk(Boolean.TRUE);
				beforeAdminInfomationParkingCar.setCarNo(carNo);
				
				LOG.info("["+carNo+"] LowPollutionCarYnService responseXml null");
				return beforeAdminInfomationParkingCar;
			}
			
			/*
			 * XML ????????? Body ?????? ????????? ??????
			 */
			if("Y".equals(useEncrypt)) {
				responseXml = soapService.responseMessageDecrypt(methodName, nameSpace,responseXml);
//				LOG.debug("["+carNo+"] LowPollutionCarYnService responseXml Decrypt [" + responseXml + "]");
			}
			adminInfomationParkingCar.setResSoapXml(responseXml);
			
			/*
			 * XML ??????????????? Body VO ?????? ??????
			 */
			LowPollutionCarYnResponseBody lowPollutionCarYnResponseBody = parsingResultFromResponseXml(responseXml, airportCode, parkingManageNumber, carNo);
			
			adminInfomationParkingCar.setIfTime(CommonUtil.getIfTime());
			adminInfomationParkingCar.setIfDt(CommonUtil.getIfDate());
			
			/*
			 * ????????? ????????? ?????? D_MISC1 ????????? 1??? -> 1, 2??? -> 2, 3??? -> 3 ???????????? 
			 * 1???, 2?????? ?????? 20% ?????? , 3?????? ?????? 50% ??????
			 * ?????? ?????? 
			 * lowpoulltionCar 	???????????? ???????????? 		TRUE : ???????????????, FALSE : ???????????? ?????? ????????????
			 * lowpoulltionCode	??????????????? ??????		1: 1???, 2: 2???, 3: 3???, 4: 4???(???????????? ??????) 5:???????????? ??????, 6:???????????? ??????, 7:?????????????????? ??????, 8:??????????????? ?????? 2013-06-23 ????????????, 9:???????????????, 0: ????????? ??????
			 */
			LOG.info(" LowPollutionCarYn ResponseBody =======================================");
			LOG.info(" lowPollutionCarYnResponseBody  LowPoulltionCar : " + lowPollutionCarYnResponseBody.getLowPoulltionCar());
			LOG.info(" lowPollutionCarYnResponseBody  LowPoulltionCode : " + lowPollutionCarYnResponseBody.getLowPoulltionCode());
			LOG.info(" ========================= =======================================");
			
			if("TRUE".equals(lowPollutionCarYnResponseBody.getLowPoulltionCar().toUpperCase())) {
				adminInfomationParkingCar.setIfRsltYn("Y");
				adminInfomationParkingCar.setErrorChk(Boolean.FALSE);
				adminInfomationParkingCar.setCarNo(carNo);
				
				adminInfomationParkingCar.setReductionYn("Y");
				adminInfomationParkingCar.setLowPollutionCarYn("Y");
				adminInfomationParkingCar.setLowPoulltionCode(lowPollutionCarYnResponseBody.getLowPoulltionCode());
			}
			else {
				adminInfomationParkingCar.setErrorChk(Boolean.FALSE);
				adminInfomationParkingCar.setCarNo(carNo);
				adminInfomationParkingCar.setIfRsltYn("N");
				
				adminInfomationParkingCar.setReductionYn("N");
				adminInfomationParkingCar.setLowPollutionCarYn("N");
				adminInfomationParkingCar.setLowPoulltionCode(lowPollutionCarYnResponseBody.getLowPoulltionCode());
			}
			
		} catch (SOAPException soapException){
			LOG.error("["+airportCode+"]["+adminInfomationParkingCar.getiId()+"] " + soapException.getMessage());
			adminInfomationParkingCar.setIfRsltYn("N");
			adminInfomationParkingCar.setCarNo(carNo);
		} catch (NullPointerException nullPointerException){
			LOG.error("["+airportCode+"]["+adminInfomationParkingCar.getiId()+"] " + nullPointerException.getMessage());
			adminInfomationParkingCar.setIfRsltYn("N");
			adminInfomationParkingCar.setCarNo(carNo);
		} catch (ArrayIndexOutOfBoundsException e) {
			LOG.error("["+airportCode+"]["+adminInfomationParkingCar.getiId()+"] " + e.getMessage());
			adminInfomationParkingCar.setIfRsltYn("N");
			adminInfomationParkingCar.setCarNo(carNo);
		} catch (Exception e){	
			LOG.error("["+airportCode+"]["+adminInfomationParkingCar.getiId()+"] " + e.getMessage());
			adminInfomationParkingCar.setIfRsltYn("N");
			adminInfomationParkingCar.setCarNo(carNo);
		
		} 
		
		return adminInfomationParkingCar;
	}
	
	private SOAPBodyElement makeLowPollutionCarYnBody(SOAPBodyElement element, LowPollutionCarYnRequestBody body) throws SOAPException,NullPointerException,Exception{
		element.addChildElement("reqId").addTextNode(body.getReqId()); 
		element.addChildElement("carNumber").addTextNode("#carNumber#");
		return element;
	}
	
	private LowPollutionCarYnResponseBody parsingResultFromResponseXml(String responseXml, String airportCode, String pakMgmtNo, String carNo) throws Exception{
		
		LowPollutionCarYnResponseBody lowPollutionCarYnResponseBody = new LowPollutionCarYnResponseBody();
		try {
			if (responseXml.contains("<DataList/>")) {
				lowPollutionCarYnResponseBody.setLowPoulltionCar("false");
			}
			else{
				lowPollutionCarYnResponseBody.setLowPoulltionCar(responseXml.split("<lowpoulltion_car>")[1].split("</lowpoulltion_car>")[0]);
				
				if (responseXml.contains("<lowpoulltion_code/>")) {
					lowPollutionCarYnResponseBody.setLowPoulltionCode("0");
				} else {
					lowPollutionCarYnResponseBody.setLowPoulltionCode(responseXml.split("<lowpoulltion_code>")[1].split("</lowpoulltion_code>")[0]);
				}
			}
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {			
			lowPollutionCarYnResponseBody.setLowPoulltionCar("false");
			LOG.error("["+airportCode+"]["+pakMgmtNo+"]["+carNo+"] " + e.getMessage());
			
		}
		return lowPollutionCarYnResponseBody;		
	}
	
	

}
