package kr.airport.parking.reduction.service.impl;

import javax.annotation.Resource;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.airport.parking.reduction.service.CommonHeaderService;
import kr.airport.parking.reduction.service.CommunicationService;
import kr.airport.parking.reduction.service.MeritCarYnService;
import kr.airport.parking.reduction.service.ReductionTsCarInfoService;
import kr.airport.parking.reduction.service.SoapService;
import kr.airport.parking.reduction.vo.message.CommonHeader;
import kr.airport.parking.reduction.vo.message.MeritCarYnRequestBody;
import kr.airport.parking.reduction.vo.message.MeritCarYnResponseBody;
import kr.airport.parking.reduction.vo.table.AdminInfomationParkingCar;
import kr.airport.parking.reduction.vo.table.RealTimeParkingCar;
import kr.airport.parking.util.common.CommonUtil;

@Service
public class MeritCarYnServiceImpl implements MeritCarYnService{
	
	private static final Logger LOG = LoggerFactory.getLogger(MeritCarYnServiceImpl.class);
	
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
	
	
	
	
			
	@Value("#{appConfig['meritCarYn.schedulerYn']}")	
	private String schedulerYn;
	
	@Value("#{appConfig['meritCarYn.URI']}")	
	private String uri;	
	
	@Value("#{appConfig['meritCarYn.certServerId']}")	
	private String certServerId;	
		
	@Value("#{appConfig['meritCarYn.methodName']}")	
	private String methodName;	
	
	@Value("#{appConfig['meritCarYn.serviceName']}")	
	private String serviceName;
	
	@Value("#{appConfig['meritCarYn.nameSpace']}")	
	private String nameSpace;
	
	@Value("#{appConfig['meritCarYn.targetServerId']}")	
	private String targetServerId;
	
	/*
	 * 국가유공자 차량 여부를 실시간으로 조회하고 결과를 업데이트 함.
	 */
	@Override
	public AdminInfomationParkingCar proc(RealTimeParkingCar realTimeParkingCar, Boolean dbProc, AdminInfomationParkingCar beforeAdminInfomationParkingCar) {
	
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
			LOG.info("["+carNo+"] MeritCarYn serviceName " + serviceName);
			LOG.info("["+carNo+"] MeritCarYn methodName " + methodName);
			LOG.info("["+carNo+"] MeritCarYn certServerId " + certServerId);
			LOG.info("["+carNo+"] MeritCarYn nameSpace " + nameSpace);
			LOG.info("["+carNo+"] MeritCarYn userDeptCode " + userDeptCode);
			LOG.info("["+carNo+"] MeritCarYn userName " + userName);
			LOG.info("["+carNo+"] MeritCarYn useSystemCode " + useSystemCode);
			LOG.info("===================================================================");
			
			commonHeader.setTransactionUniqueId(commonHeaderService.getTransactionUniqueIdFromTime());
			header = commonHeaderService.makeCommonHeaderToSOAPHeader(header,commonHeader);
			LOG.info("["+carNo+"] MeritCarYn TransactionUniqueId :" + commonHeader.getTransactionUniqueId());
		
			/*
			 * SOAP XML BODY 부분 작성
			 */
			MeritCarYnRequestBody body = new MeritCarYnRequestBody();
			body.setCarNo(realTimeParkingCar.getCarNo());
			
			LOG.info("===================================================================");
			LOG.info("["+carNo+"] MeritCarYn carNo " + carNo);
			LOG.info("===================================================================");
			
			SOAPPart part = soapMsg.getSOAPPart();
			SOAPBodyElement soapBodyElement = soapService.makeBodyElement(part.getEnvelope(),methodName,nameSpace);
			soapBodyElement = makeMeritCarYnBody(soapBodyElement,body);
			soapMsg.saveChanges();
			
			/*
			 * SOAP XML Object -> String 반환
			 */
			requestXml = soapService.getXmlStringFromSoapMessageObject(soapMsg);
			requestXml = requestXml.replace("#userName#", userName );
			requestXml = requestXml.replace("#carNo#",body.getCarNo() );
			adminInfomationParkingCar.setReqSoapXml(requestXml);
//			LOG.debug("["+carNo+"] MeritCarYnService requestXml [" + requestXml + "]");
			
			/*
			 * XML 메세지 Body 부분 암호화 처리
			 */
			if("Y".equals(useEncrypt)) {
				requestXml = soapService.requestMessageEncrypt(methodName, nameSpace, requestXml, targetServerId);
//				LOG.debug("["+carNo+"] MeritCarYnService EnCrypt requestXml [" + requestXml + "]");
			}
			
			/*
			 * Http Client 통신으로 연계처리 후 응답 Xml 반환
			 */
			responseXml = communicationService.communicate(uri, requestXml, parkingManageNumber, serviceName, airportCode, carNo);
//			LOG.debug("["+carNo+"] MeritCarYnService responseXml [" + responseXml + "]");
			
			if(null == responseXml) {
				beforeAdminInfomationParkingCar.setReqSoapXml(requestXml);
				
				beforeAdminInfomationParkingCar.setIfRsltYn("N");			
				beforeAdminInfomationParkingCar.setIfTime(adminInfomationParkingCar.getIfTime());
				beforeAdminInfomationParkingCar.setIfDt(adminInfomationParkingCar.getIfDt());
				beforeAdminInfomationParkingCar.setIfType("01");	
				beforeAdminInfomationParkingCar.setErrorChk(Boolean.TRUE);
				beforeAdminInfomationParkingCar.setCarNo(carNo);
				
				LOG.info("["+carNo+"] MeritCarYnService responseXml null return");
				return beforeAdminInfomationParkingCar;
			}
			
			/*
			 * XML 메세지 Body 부분 복호화 처리
			 */
			if("Y".equals(useEncrypt)) {
				responseXml = soapService.responseMessageDecrypt(methodName, nameSpace,responseXml);
			}
			adminInfomationParkingCar.setResSoapXml(responseXml);
			
			/*
			 * XML 응답메세지 Body VO 매핑 처리
			 */
			MeritCarYnResponseBody meritCarYnResponseBody = parsingResultFromResponseXml(responseXml, airportCode, parkingManageNumber, carNo);
			
			adminInfomationParkingCar.setIfTime(CommonUtil.getIfTime());
			adminInfomationParkingCar.setIfDt(CommonUtil.getIfDate());
			
			/*
			 * 응답 결과 
			 * resultCd 	결과코드 		Y / N
			 * resultMsg	결과메세지		SVR01 : 정상처리, ERR11 : 필수입력정보 누락, SVR99 : 기타 Exception 발생
			 */

			LOG.info(" MeritCarYn ResponseBody =======================================");
			LOG.info(" meritCarYnResponseBody  resultCd : " + meritCarYnResponseBody.getResultCd());
			LOG.info(" meritCarYnResponseBody  resultMsg : " + meritCarYnResponseBody.getResultMsg());
			LOG.info(" ========================= =======================================");
			
			if("Y".equals(meritCarYnResponseBody.getResultCd())) {
				adminInfomationParkingCar.setIfRsltYn("Y");
				adminInfomationParkingCar.setErrorChk(Boolean.FALSE);
				adminInfomationParkingCar.setCarNo(carNo);
				
				adminInfomationParkingCar.setReductionYn("Y");
				adminInfomationParkingCar.setMeritCarYn("Y");
				
			}
			else {
				if("ERR11".equals(meritCarYnResponseBody.getResultMsg()) || "SVR99".equals(meritCarYnResponseBody.getResultMsg()) ) {
					adminInfomationParkingCar.setErrorChk(Boolean.TRUE);
				}
				else {
					adminInfomationParkingCar.setErrorChk(Boolean.FALSE);
				}
				
				adminInfomationParkingCar.setCarNo(carNo);
				adminInfomationParkingCar.setIfRsltYn("N");
				
				adminInfomationParkingCar.setReductionYn("N");
				adminInfomationParkingCar.setMeritCarYn("N");
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
	
	private SOAPBodyElement makeMeritCarYnBody(SOAPBodyElement element, MeritCarYnRequestBody body) throws SOAPException,NullPointerException,Exception{
		element.addChildElement("carNo").addTextNode("#carNo#");
		return element;
	}
	
	private MeritCarYnResponseBody parsingResultFromResponseXml(String responseXml, String airportCode, String pakMgmtNo, String carNo) throws Exception{
		
		MeritCarYnResponseBody meritCarYnResponseBody = new MeritCarYnResponseBody();
		try {
			meritCarYnResponseBody.setResultCd(responseXml.split("<resultYn>")[1].split("</resultYn>")[0]);
			meritCarYnResponseBody.setResultMsg(responseXml.split("<resultMsg>")[1].split("</resultMsg>")[0]);
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {			
			meritCarYnResponseBody.setResultCd("N");
			meritCarYnResponseBody.setResultMsg("SVR99");
			LOG.error("["+airportCode+"]["+pakMgmtNo+"]["+carNo+"] " + e.getMessage());
		}
	
		
		return meritCarYnResponseBody;		
	}
	
}
