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
			 * SOAP XML HEADER 공통 헤더부 작성
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
			 * SOAP XML BODY 부분 작성
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
			 * SOAP XML Object -> String 반환
			 */
			requestXml = soapService.getXmlStringFromSoapMessageObject(soapMsg);
			requestXml = requestXml.replace("#userName#", userName );
			requestXml = requestXml.replace("#carNumber#",body.getCarNumber() );
			adminInfomationParkingCar.setReqSoapXml(requestXml);
//			LOG.debug("["+carNo+"] LowPollutionCarYnService requestXml [" + requestXml + "]");
			
			/*
			 * XML 메세지 Body 부분 암호화 처리
			 */
			if("Y".equals(useEncrypt)) {
				requestXml = soapService.requestMessageEncrypt(methodName, nameSpace, requestXml, targetServerId);
//				LOG.debug("["+carNo+"] LowPollutionCarYnService EnCrypt requestXml [" + requestXml + "]");
			}
			
			/*
			 * Http Client 통신으로 연계처리 후 응답 Xml 반환
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
			 * XML 메세지 Body 부분 복호화 처리
			 */
			if("Y".equals(useEncrypt)) {
				responseXml = soapService.responseMessageDecrypt(methodName, nameSpace,responseXml);
//				LOG.debug("["+carNo+"] LowPollutionCarYnService responseXml Decrypt [" + responseXml + "]");
			}
			adminInfomationParkingCar.setResSoapXml(responseXml);
			
			/*
			 * XML 응답메세지 Body VO 매핑 처리
			 */
			LowPollutionCarYnResponseBody lowPollutionCarYnResponseBody = parsingResultFromResponseXml(responseXml, airportCode, parkingManageNumber, carNo);
			
			adminInfomationParkingCar.setIfTime(CommonUtil.getIfTime());
			adminInfomationParkingCar.setIfDt(CommonUtil.getIfDate());
			
			/*
			 * 저공해 차량의 경우 D_MISC1 필드에 1종 -> 1, 2종 -> 2, 3종 -> 3 업데이트 
			 * 1종, 2종의 경우 20% 감면 , 3종의 경우 50% 감면
			 * 응답 결과 
			 * lowpoulltionCar 	저공해차 유무확인 		TRUE : 저공해차량, FALSE : 일반차량 혹은 기타오류
			 * lowpoulltionCode	저공해차량 코드		1: 1종, 2: 2종, 3: 3종, 4: 4종(저공해차 아님) 5:조회결과 없음, 6:차량번호 오류, 7:연계기관코드 에러, 8:수도권외일 경우 2013-06-23 이전차량, 9:조례미재정, 0: 시스템 에러
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
