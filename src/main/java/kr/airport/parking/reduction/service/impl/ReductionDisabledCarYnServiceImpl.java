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
import kr.airport.parking.reduction.service.ReductionDisabledCarYnService;
import kr.airport.parking.reduction.service.ReductionTsCarInfoService;
import kr.airport.parking.reduction.service.SoapService;
import kr.airport.parking.reduction.vo.message.CommonHeader;
import kr.airport.parking.reduction.vo.message.ReductionDisabledCarYnRequestBody;
import kr.airport.parking.reduction.vo.message.ReductionDisabledCarYnResponseBody;
import kr.airport.parking.reduction.vo.table.AdminInfomationParkingCar;
import kr.airport.parking.reduction.vo.table.RealTimeParkingCar;
import kr.airport.parking.util.common.CommonUtil;

@Service
public class ReductionDisabledCarYnServiceImpl implements ReductionDisabledCarYnService{
	
	private static final Logger LOG = LoggerFactory.getLogger(ReductionTsCarInfoService.class);
	
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
	
			
	@Value("#{appConfig['reductionDisabledCarYn.schedulerYn']}")	
	private String schedulerYn;
	
	@Value("#{appConfig['reductionDisabledCarYn.URI']}")	
	private String uri;	
	
	@Value("#{appConfig['reductionDisabledCarYn.certServerId']}")	
	private String certServerId;	
		
	@Value("#{appConfig['reductionDisabledCarYn.methodName']}")	
	private String methodName;	
	
	@Value("#{appConfig['reductionDisabledCarYn.serviceName']}")	
	private String serviceName;
	
	@Value("#{appConfig['reductionDisabledCarYn.nameSpace']}")	
	private String nameSpace;
	
	@Value("#{appConfig['reductionDisabledCarYn.targetServerId']}")	
	private String targetServerId;
	
	@Value("#{appConfig['reductionDisabledCarYn.body.reqOrgCd']}")	
	private String reqOrgCd;
	
	@Value("#{appConfig['reductionDisabledCarYn.body.reqBizCd']}")	
	private String reqBizCd;
	

	/*
	 * 경차 차량 여부를 실시간으로 조회하고 결과를 업데이트 함.
	 */
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
			LOG.info("["+carNo+"] ReductionDisabledCarYn serviceName " + serviceName);
			LOG.info("["+carNo+"] ReductionDisabledCarYn methodName " + methodName);
			LOG.info("["+carNo+"] ReductionDisabledCarYn certServerId " + certServerId);
			LOG.info("["+carNo+"] ReductionDisabledCarYn nameSpace " + nameSpace);
			LOG.info("["+carNo+"] ReductionDisabledCarYn userDeptCode " + userDeptCode);
			LOG.info("["+carNo+"] ReductionDisabledCarYn userName " + userName);
			LOG.info("["+carNo+"] ReductionDisabledCarYn useSystemCode " + useSystemCode);
			LOG.info("===================================================================");
			
			commonHeader.setTransactionUniqueId(commonHeaderService.getTransactionUniqueIdFromTime());
			header = commonHeaderService.makeCommonHeaderToSOAPHeader(header,commonHeader);
			LOG.info("["+carNo+"] ReductionDisabledCarYn TransactionUniqueId :" + commonHeader.getTransactionUniqueId());
		
			/*
			 * XML BODY 부분 작성
			 */
			ReductionDisabledCarYnRequestBody body = new ReductionDisabledCarYnRequestBody();
			body.setCarNo(carNo);
			body.setReqOrgCd(reqOrgCd);
			body.setReqBizCd(reqBizCd);
			
			LOG.info("===================================================================");
			LOG.info("["+carNo+"] ReductionDisabledCarYn carNo " + carNo);
			LOG.info("["+carNo+"] ReductionDisabledCarYn reqOrgCd " + reqOrgCd);
			LOG.info("["+carNo+"] ReductionDisabledCarYn reqBizCd " + reqBizCd);
			LOG.info("===================================================================");
			
			SOAPPart part = soapMsg.getSOAPPart();
			SOAPBodyElement soapBodyElement = soapService.makeBodyElement(part.getEnvelope(),methodName,nameSpace);
			soapBodyElement = makeReductionDisabledCarYnBody(soapBodyElement, body);
			
			/*
			 * SOAP XML Object -> String 반환
			 */
			soapMsg.saveChanges();
			requestXml = soapService.getXmlStringFromSoapMessageObject(soapMsg);
			requestXml = requestXml.replace("#userName#", userName );
			requestXml = requestXml.replace("#CAR_NO#", body.getCarNo() );
			adminInfomationParkingCar.setReqSoapXml(requestXml);
//			LOG.debug("["+carNo+"] ReductionDisabledCarYn requestXml [" + requestXml + "]");
			
			/*
			 * XML 메세지 Body 부분 암호화 처리
			 */
			if("Y".equals(useEncrypt)) {
				requestXml = soapService.requestMessageEncrypt(methodName, nameSpace, requestXml, targetServerId);
//				LOG.debug("["+carNo+"] ReductionDisabledCarYn requestXml Encrypt [" + requestXml + "]");
			}
			
			/*
			 * Http Client 통신으로 연계처리 후 응답 Xml 반환
			 */
			responseXml = communicationService.communicate(uri, requestXml, parkingManageNumber, serviceName, airportCode, carNo);
//			LOG.debug("["+carNo+"] ReductionDisabledCarYn responseXml [" + responseXml + "]");
			
			if(null == responseXml) {
				beforeAdminInfomationParkingCar.setReqSoapXml(requestXml);
				
				beforeAdminInfomationParkingCar.setIfRsltYn("N");			
				beforeAdminInfomationParkingCar.setIfTime(adminInfomationParkingCar.getIfTime());
				beforeAdminInfomationParkingCar.setIfDt(adminInfomationParkingCar.getIfDt());
				beforeAdminInfomationParkingCar.setIfType("01");	
				beforeAdminInfomationParkingCar.setErrorChk(Boolean.TRUE);
				beforeAdminInfomationParkingCar.setCarNo(carNo);
				
				LOG.info("["+carNo+"] ReductionDisabledCarYn responseXml null");
				return beforeAdminInfomationParkingCar;
			}
			
			/*
			 * XML 메세지 Body 부분 복호화 처리
			 */
			if("Y".equals(useEncrypt)) {
				responseXml = soapService.responseMessageDecrypt(methodName, nameSpace,responseXml);
//				LOG.debug("["+carNo+"] ReductionDisabledCarYn responseXml Decrypt [" + responseXml + "]");
			}
			adminInfomationParkingCar.setResSoapXml(responseXml);
			
			/*
			 * XML 응답메세지 Body VO 매핑 처리
			 */
			ReductionDisabledCarYnResponseBody reductionDisabledCarYnResponseBody = parsingResultFromResponseXml(responseXml, airportCode, parkingManageNumber, carNo);
			
			adminInfomationParkingCar.setIfTime(CommonUtil.getIfTime());
			adminInfomationParkingCar.setIfDt(CommonUtil.getIfDate());
			
			/*
			 * 응답 결과 
			 * carNo 	차량번호		문자
			 * tgtrNm	대상자이름		문자
			 * obsLvCla	장애등급		장애등급 숫자로된 문자
			 * inqrDt	기준일자		년월일 8자리 문자
			 * qufyYn	사실여부		Y : 장애인 대상, N : 장애인 비대상-차량번호를 제외한 나머지 NULL
			 */
			LOG.info(" ReductionDisabledCarYn ResponseBody =======================================");
			LOG.info(" reductionDisabledCarYnResponseBody  carNo : " + reductionDisabledCarYnResponseBody.getCarNo());
			LOG.info(" reductionDisabledCarYnResponseBody  tgtrNm : " + reductionDisabledCarYnResponseBody.getTgtrNm());
			LOG.info(" reductionDisabledCarYnResponseBody  obsLvCla : " + reductionDisabledCarYnResponseBody.getObsLvCla());
			LOG.info(" reductionDisabledCarYnResponseBody  inqrDt : " + reductionDisabledCarYnResponseBody.getInqrDt());
			LOG.info(" reductionDisabledCarYnResponseBody  qufyYn : " + reductionDisabledCarYnResponseBody.getQufyYn());
			LOG.info(" ========================= =======================================");
			
			if("Y".equals(reductionDisabledCarYnResponseBody.getQufyYn())) {
				adminInfomationParkingCar.setIfRsltYn("Y");
				adminInfomationParkingCar.setErrorChk(Boolean.FALSE);
				adminInfomationParkingCar.setCarNo(carNo);
				
				adminInfomationParkingCar.setReductionYn("Y");
				adminInfomationParkingCar.setDisabledCarYn("Y");
			}
			else {
				adminInfomationParkingCar.setErrorChk(Boolean.TRUE);
				adminInfomationParkingCar.setCarNo(carNo);
				adminInfomationParkingCar.setIfRsltYn("N");
				
				adminInfomationParkingCar.setReductionYn("N");
				adminInfomationParkingCar.setDisabledCarYn("N");
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
	
	private SOAPBodyElement makeReductionDisabledCarYnBody(SOAPBodyElement element, ReductionDisabledCarYnRequestBody body) throws SOAPException,NullPointerException,Exception{
		element.addChildElement("ReqOrgCd").addTextNode(body.getReqOrgCd());
		element.addChildElement("ReqBizCd").addTextNode(body.getReqBizCd());
		element.addChildElement("CAR_NO").addTextNode("#CAR_NO#");
		return element;
	}
	
	private ReductionDisabledCarYnResponseBody parsingResultFromResponseXml(String responseXml, String airportCode, String pakMgmtNo, String carNo) throws Exception{
		
		ReductionDisabledCarYnResponseBody reductionDisabledCarYnResponseBody = new ReductionDisabledCarYnResponseBody();
		try {
			reductionDisabledCarYnResponseBody.setCarNo(responseXml.split("<CAR_NO>")[1].split("</CAR_NO>")[0]);
			if (responseXml.contains("<TGTR_NM/>")) {
				reductionDisabledCarYnResponseBody.setTgtrNm("");
			} else {
				reductionDisabledCarYnResponseBody.setTgtrNm(responseXml.split("<TGTR_NM>")[1].split("</TGTR_NM>")[0]);
			}
			if (responseXml.contains("<OBS_LV_CLA/>")) {
				reductionDisabledCarYnResponseBody.setObsLvCla("");
			} else {
				reductionDisabledCarYnResponseBody.setObsLvCla(responseXml.split("<OBS_LV_CLA>")[1].split("</OBS_LV_CLA>")[0]);
			}
			if (responseXml.contains("<INQR_DT/>")) {
				reductionDisabledCarYnResponseBody.setInqrDt("");
			} else {
				reductionDisabledCarYnResponseBody.setInqrDt(responseXml.split("<INQR_DT>")[1].split("</INQR_DT>")[0]);
			}
			if (responseXml.contains("<QUFY_YN/>")) {
				reductionDisabledCarYnResponseBody.setQufyYn("");
			} else {
				reductionDisabledCarYnResponseBody.setQufyYn(responseXml.split("<QUFY_YN>")[1].split("</QUFY_YN>")[0]);
			}
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {			
			LOG.error("["+airportCode+"]["+pakMgmtNo+"]["+carNo+"] " + e.getMessage());
			
		}
		
		return reductionDisabledCarYnResponseBody;		
	}
	

}
