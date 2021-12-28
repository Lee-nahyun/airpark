package kr.airport.parking.reduction.service.impl;

import java.net.InetAddress;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import kr.airport.parking.reduction.service.CommonHeaderService;
import kr.airport.parking.reduction.service.CommunicationService;
import kr.airport.parking.reduction.service.ReductionTsCarInfoService;
import kr.airport.parking.reduction.service.SoapService;
import kr.airport.parking.reduction.vo.message.CommonHeader;
import kr.airport.parking.reduction.vo.message.ReductionTsCarInfoRequestBody;
import kr.airport.parking.reduction.vo.message.ReductionTsCarInfoResponseBody;
import kr.airport.parking.reduction.vo.table.AdminInfomationParkingCar;
import kr.airport.parking.reduction.vo.table.RealTimeParkingCar;
import kr.airport.parking.util.common.CommonUtil;

@Service
public class ReductionTsCarInfoServiceImpl implements ReductionTsCarInfoService{
	
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
	
	
			
	@Value("#{appConfig['reductionTsCarInfo.schedulerYn']}")	
	private String schedulerYn;
	
	@Value("#{appConfig['reductionTsCarInfo.URI']}")	
	private String uri;	
	
	@Value("#{appConfig['reductionTsCarInfo.certServerId']}")	
	private String certServerId;	
		
	@Value("#{appConfig['reductionTsCarInfo.methodName']}")	
	private String methodName;	
	
	@Value("#{appConfig['reductionTsCarInfo.serviceName']}")	
	private String serviceName;
	
	@Value("#{appConfig['reductionTsCarInfo.nameSpace']}")	
	private String nameSpace;
	
	@Value("#{appConfig['reductionTsCarInfo.targetServerId']}")	
	private String targetServerId;
	
	@Value("#{appConfig['reductionTsCarInfo.cntcInfoCode']}")	
	private String cntcInfoCode;
	
	@Value("#{appConfig['reductionTsCarInfo.chargerId']}")	
	private String chargerId;
	
	@Value("#{appConfig['reductionTsCarInfo.chargerNm']}")	
	private String chargerNm;
	
	@Value("#{appConfig['reductionTsCarInfo.chargerIpAdres']}")	
	private String chargerIpAdres;

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
			
			InetAddress local = InetAddress.getLocalHost();
			String ip = local.getHostAddress();
			
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
			LOG.info("["+carNo+"] ReductionTsCarInfo serviceName " + serviceName);
			LOG.info("["+carNo+"] ReductionTsCarInfo methodName " + methodName);
			LOG.info("["+carNo+"] ReductionTsCarInfo certServerId " + certServerId);
			LOG.info("["+carNo+"] ReductionTsCarInfo nameSpace " + nameSpace);
			LOG.info("["+carNo+"] ReductionTsCarInfo userDeptCode " + userDeptCode);
			LOG.info("["+carNo+"] ReductionTsCarInfo userName " + userName);
			LOG.info("["+carNo+"] ReductionTsCarInfo useSystemCode " + useSystemCode);
			LOG.info("===================================================================");
			
			commonHeader.setTransactionUniqueId(commonHeaderService.getTransactionUniqueIdFromTime());
			header = commonHeaderService.makeCommonHeaderToSOAPHeader(header,commonHeader);
			LOG.info("["+carNo+"] ReductionTsCarInfo TransactionUniqueId :" + commonHeader.getTransactionUniqueId());
		
			
			/*
			 * SOAP XML BODY 부분 작성
			 */
			LOG.info("["+carNo+"] ReductionTsCarInfo body make Start");
			ReductionTsCarInfoRequestBody body = new ReductionTsCarInfoRequestBody();
			body.setVhrNo(realTimeParkingCar.getCarNo());
			body.setChargerIpAdres(chargerIpAdres);
			body.setChargerNm(chargerNm);
			body.setChargerId(chargerId);
			body.setCntcInfoCode(cntcInfoCode);
			
			LOG.info("===================================================================");
			LOG.info("["+carNo+"] ReductionTsCarInfo chargerNm " + chargerNm);
			LOG.info("["+carNo+"] ReductionTsCarInfo chargerId " + chargerId);
			LOG.info("["+carNo+"] ReductionTsCarInfo chargerIpAdres " + chargerIpAdres);
			LOG.info("["+carNo+"] ReductionTsCarInfo methodName " + methodName);
			LOG.info("["+carNo+"] ReductionTsCarInfo nameSpace " + nameSpace);
			LOG.info("===================================================================");
			
			SOAPPart part = soapMsg.getSOAPPart();
			SOAPBodyElement soapBodyElement = soapService.makeBodyElement(part.getEnvelope(),methodName, nameSpace);
			soapBodyElement = makeReductionTsCarInfoBody(soapBodyElement,body);
			soapMsg.saveChanges();
			LOG.debug("["+carNo+"] ReductionTsCarInfo soapMsg [" + soapMsg + "]");
			
			/*
			 * SOAP XML Object -> String 반환
			 */
			requestXml = soapService.getXmlStringFromSoapMessageObject(soapMsg);
			requestXml = requestXml.replace("#userName#", userName );
			requestXml = requestXml.replace("#chargerNm#",body.getChargerNm() );
			requestXml = requestXml.replace("#vhrNo#",body.getVhrNo() );
//			LOG.debug("["+carNo+"] ReductionTsCarInfo requestXml [" + requestXml + "]");
			
			adminInfomationParkingCar.setReqSoapXml(requestXml);
			
			
			/*
			 * XML 메세지 Body 부분 암호화 처리
			 */
			if("Y".equals(useEncrypt)) {
				requestXml = soapService.requestMessageEncrypt(methodName, nameSpace, requestXml, targetServerId);
//				LOG.debug("["+carNo+"] ReductionTsCarInfo EnCrypt requestXml [" + requestXml + "]");
			}
			
			/*
			 * Http Client 통신으로 연계처리 후 응답 Xml 반환
			 */
			responseXml = communicationService.communicate(uri, requestXml, parkingManageNumber, serviceName, airportCode, carNo);
//			LOG.debug("["+carNo+"] ReductionTsCarInfo responseXml [" + responseXml + "]");
			
			if(null == responseXml) {
				beforeAdminInfomationParkingCar.setReqSoapXml(requestXml);
				
				beforeAdminInfomationParkingCar.setIfRsltYn("N");			
				beforeAdminInfomationParkingCar.setIfTime(adminInfomationParkingCar.getIfTime());
				beforeAdminInfomationParkingCar.setIfDt(adminInfomationParkingCar.getIfDt());
				beforeAdminInfomationParkingCar.setIfType("01");	
				beforeAdminInfomationParkingCar.setErrorChk(Boolean.TRUE);
				beforeAdminInfomationParkingCar.setCarNo(carNo);
				
				LOG.info("["+carNo+"] ReductionTsCarInfo responseXml null");
				return beforeAdminInfomationParkingCar;
			}
			
			/*
			 * XML 메세지 Body 부분 복호화 처리
			 */
			if("Y".equals(useEncrypt)) {
				responseXml = soapService.responseMessageDecrypt(methodName, nameSpace, responseXml);
//				LOG.debug("["+carNo+"] ReductionTsCarInfo responseXml Decrypt [" + responseXml + "]");
			}
			adminInfomationParkingCar.setResSoapXml(responseXml);
			
			/*
			 * XML 응답메세지 Body VO 매핑 처리
			 */
			ReductionTsCarInfoResponseBody reductionTsCarInfoResponseBody = parsingResultFromResponseXml(responseXml, airportCode, parkingManageNumber, carNo);
		
			//인터페이스 시간
			adminInfomationParkingCar.setIfTime(CommonUtil.getIfTime());
			adminInfomationParkingCar.setIfDt(CommonUtil.getIfDate());
	
			/*
			 * 응답 결과 
			 * cntcResultCode 				연계결과코드 		
			 * cntcResultDtls				연계결과상세
			 * vhctyAsortNm					차종종별명		승용,승합,화물,특수
			 * dsplvl						배기량
			 * processImprtyResnCode		처리불가사유코드	00 : 정상, 01 : 조회결과없음
			 * processImprtyResnDtls		처리불가사유명세	
			 */
			
			LOG.info(" ReductionTsCarInfo ResponseBody =======================================");
			LOG.info(" reductionTsCarInfoResponseBody  cntcResultCode : " + reductionTsCarInfoResponseBody.getCntcResultCode());
			LOG.info(" reductionTsCarInfoResponseBody  cntcResultDtls : " + reductionTsCarInfoResponseBody.getCntcResultDtls());
			LOG.info(" reductionTsCarInfoResponseBody  vhctyAsortNm : " + reductionTsCarInfoResponseBody.getVhctyAsortNm());
			LOG.info(" reductionTsCarInfoResponseBody  dsplvl : " + reductionTsCarInfoResponseBody.getDsplvl());
			LOG.info(" reductionTsCarInfoResponseBody  processImprtyResnCode : " + reductionTsCarInfoResponseBody.getProcessImprtyResnCode());
			LOG.info(" reductionTsCarInfoResponseBody  processImprtyResnDtls : " + reductionTsCarInfoResponseBody.getProcessImprtyResnDtls());
			LOG.info(" =================================================================");
			
			if("00".equals(reductionTsCarInfoResponseBody.getProcessImprtyResnCode())) {
				LOG.info(" reductionTsCarInfo resnCode is 00");
				if(1000 >= Integer.parseInt(reductionTsCarInfoResponseBody.getDsplvl()) && Integer.parseInt(reductionTsCarInfoResponseBody.getDsplvl()) >= 500) {
					LOG.info(" reductionTsCarInfo Dsplvl 1000 lower");
					adminInfomationParkingCar.setReductionYn("Y");
					adminInfomationParkingCar.setTsCarYn("Y");
				}
				else {
					adminInfomationParkingCar.setReductionYn("N");
					adminInfomationParkingCar.setTsCarYn("N");
				}
				
				adminInfomationParkingCar.setIfRsltYn("Y");
				adminInfomationParkingCar.setErrorChk(Boolean.FALSE);
			}
			else {
				LOG.info(" reductionTsCarInfo resnCode is not 00");
				if(null == reductionTsCarInfoResponseBody.getDsplvl()) {
					adminInfomationParkingCar.setErrorChk(Boolean.TRUE);
				}
				
				adminInfomationParkingCar.setReductionYn("N");
				adminInfomationParkingCar.setTsCarYn("N");
				adminInfomationParkingCar.setIfRsltYn("N");
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
	
	private SOAPBodyElement makeReductionTsCarInfoBody(SOAPBodyElement element, ReductionTsCarInfoRequestBody body) throws SOAPException,NullPointerException,Exception{
		element.addChildElement("cntcInfoCode").addTextNode(body.getCntcInfoCode());
		element.addChildElement("chargerId").addTextNode(body.getChargerId());
		element.addChildElement("chargerNm").addTextNode("#chargerNm#");
		element.addChildElement("chargerIpAdres").addTextNode(body.getChargerIpAdres()); 
		element.addChildElement("vhrNo").addTextNode("#vhrNo#");
		return element;
	}
	
	private ReductionTsCarInfoResponseBody parsingResultFromResponseXml(String responseXml, String airportCode, String parkingManageNumber, String carNo) throws Exception{
		
		ReductionTsCarInfoResponseBody reductionTsCarInfoResponseBody = new ReductionTsCarInfoResponseBody();
		try {
			reductionTsCarInfoResponseBody.setProcessImprtyResnCode(responseXml.split("<processImprtyResnCode>")[1].split("</processImprtyResnCode>")[0]);
			if (responseXml.contains("<dsplvl/>")) {
				reductionTsCarInfoResponseBody.setDsplvl("0");
			} else {
				reductionTsCarInfoResponseBody.setDsplvl(responseXml.split("<dsplvl>")[1].split("</dsplvl>")[0]);
			}
			if (responseXml.contains("<vhctyAsortNm/>")) {
				reductionTsCarInfoResponseBody.setVhctyAsortNm("미등록차량");
			} else {
				reductionTsCarInfoResponseBody.setVhctyAsortNm(responseXml.split("<vhctyAsortNm>")[1].split("</vhctyAsortNm>")[0]);
			}
			if (responseXml.contains("<processImprtyResnDtls/>")) {
				reductionTsCarInfoResponseBody.setProcessImprtyResnDtls(null);
			} else {
				reductionTsCarInfoResponseBody.setProcessImprtyResnDtls(responseXml.split("<processImprtyResnDtls>")[1].split("</processImprtyResnDtls>")[0]);
			}
			if (responseXml.contains("<cntcResultCode/>")) {
				reductionTsCarInfoResponseBody.setCntcResultCode("01");
			} else {
				reductionTsCarInfoResponseBody.setCntcResultCode(responseXml.split("<cntcResultCode>")[1].split("</cntcResultCode>")[0]);
			}
			
			if (responseXml.contains("<cntcResultDtls/>")) {
				reductionTsCarInfoResponseBody.setCntcResultDtls(null);
			} else {
				reductionTsCarInfoResponseBody.setCntcResultDtls(responseXml.split("<cntcResultDtls>")[1].split("</cntcResultDtls>")[0]);
			}
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {			
			reductionTsCarInfoResponseBody.setCntcResultCode("01");
			reductionTsCarInfoResponseBody.setVhctyAsortNm("미등록차량");
			reductionTsCarInfoResponseBody.setDsplvl(null);
			LOG.error("["+airportCode+"]["+parkingManageNumber+"]["+carNo+"] " + e.getMessage());
			
		}
	
		
		return reductionTsCarInfoResponseBody;		
	}
	
	@Async
	@Override
	public Future<AdminInfomationParkingCar> procAsync(RealTimeParkingCar realTimeParkingCar,Boolean dbProc,AdminInfomationParkingCar beforeAdminInfomationParkingCar) {
		
		
//		String parkingManageNumber = realTimeParkingCar.getParkingManageNumber();
//		String requestXml = null;
//		String responseXml = null;
//		String carNo = realTimeParkingCar.getCarNumber();
//		String siteId = realTimeParkingCar.getSiteId();
		
		
		AdminInfomationParkingCar adminInfomationParkingCar = new AdminInfomationParkingCar();
//		adminInfomationParkingCar.setPakMgmtNo(parkingManageNumber);
//		adminInfomationParkingCar.setPlotId(realTimeParkingCar.getPlotId());
//		adminInfomationParkingCar.setSiteId(realTimeParkingCar.getSiteId());
//		adminInfomationParkingCar.setIfRsltYn("N");
//			
//		adminInfomationParkingCar.setPlotCompositionId(realTimeParkingCar.getPlotCompositionId());
//		adminInfomationParkingCar.setCarKind(realTimeParkingCar.getCarkind());
//		adminInfomationParkingCar.setRegDate(realTimeParkingCar.getRegDate());
//		adminInfomationParkingCar.setCarNo(carNo);
//		
//		
//		try {
//			parkingManageNumber = realTimeParkingCar.getParkingManageNumber();
//			InetAddress local = InetAddress.getLocalHost();
//			String ip = local.getHostAddress();
//			SOAPMessage soapMsg = soapService.makeSoapMassage();
//			
//			SOAPHeader header = soapMsg.getSOAPHeader();
//			
//			CommonHeader commonHeader = new CommonHeader();
//			
//			commonHeader.setServiceName(serviceName);
//			commonHeader.setMethodName(methodName);
//			commonHeader.setServerId(certServerId);
//			commonHeader.setNameSpace(nameSpace);
//			commonHeader.setUserDeptCode(userDeptCode);
//			commonHeader.setUserName(userName);
//			commonHeader.setUseSystemCode(useSystemCode);
//			commonHeader.setTransactionUniqueId(commonHeaderService.getTransactionUniqueIdFromTime());
//			header = commonHeaderService.makeCommonHeaderToSOAPHeader(header,commonHeader);
//
//			ReductionTsCarInfoRequestBody body = new ReductionTsCarInfoRequestBody();
//			body.setVhrNo(realTimeParkingCar.getCarNumber());
//			body.setChargerIpAdres(ip);
//			body.setChargerNm(chargerNm);
//			body.setChargerId(chargerId);
//			body.setCntcInfoCode(cntcInfoCode);
//			SOAPPart part = soapMsg.getSOAPPart();
//			SOAPBodyElement soapBodyElement = soapService.makeBodyElement(part.getEnvelope(),methodName,nameSpace);
//			soapBodyElement = makeReductionTsCarInfoBody(soapBodyElement,body);
//			
//			
//			soapMsg.saveChanges();
//			requestXml = soapService.getXmlStringFromSoapMessageObject(soapMsg);
//			requestXml = requestXml.replace("#chargerNm#",body.getChargerNm() );
//			requestXml = requestXml.replace("#vhrNo#",body.getVhrNo() );
//			
//			if("Y".equals(useEncrypt)) {
//				requestXml = soapService.requestMessageEncrypt(methodName, nameSpace, requestXml, targetServerId);
//				
//			}
//			int tryCount = 1;
//			while (tryCount<4) {
//				responseXml = communicationService.communicate(uri, requestXml,parkingManageNumber,serviceName,siteId,carNo);				
//				if(null!=responseXml) {
//					break;
//				} 
//				tryCount++;
//			}
//			if (tryCount > 1 ) {
//				LOG.info("ReductionCar Info retry communication count : " + tryCount);
//			}
//			if(null == responseXml) {
//				if(null == beforeAdminInfomationParkingCar) {
//					beforeAdminInfomationParkingCar  = adminInfomationParkingCar;			
//				} else {
//					beforeAdminInfomationParkingCar.setIfRsltYn("N");			
//					beforeAdminInfomationParkingCar.setIfTime(adminInfomationParkingCar.getIfTime());
//					beforeAdminInfomationParkingCar.setIfDt(adminInfomationParkingCar.getIfDt());
//					beforeAdminInfomationParkingCar.setIfType("01");
//					beforeAdminInfomationParkingCar.setErrorChk(Boolean.TRUE);
//					beforeAdminInfomationParkingCar.setCarNo(carNo);
//				}		
//				
//				return new AsyncResult<AdminInfomationParkingCar>(beforeAdminInfomationParkingCar);
//			}
//			if("Y".equals(useEncrypt)) {
//				responseXml = soapService.responseMessageDecrypt(methodName, nameSpace,responseXml);
//				
//			}
//		
//			ReductionTsCarInfoResponseBody reductionTsCarInfoResponseBody = parsingResultFromResponseXml(responseXml,parkingManageNumber,siteId,carNo);
//			
//	
//			Integer carDspmnt = 0;
//			if("00".equals(reductionTsCarInfoResponseBody.getProcessImprtyResnCode())) {
//				adminInfomationParkingCar.setIfRsltYn("Y");				
//				adminInfomationParkingCar.setIfTime(CommonUtil.getIfTime());
//				adminInfomationParkingCar.setIfDt(CommonUtil.getIfDate());
//				adminInfomationParkingCar.setIfType("01");
//				adminInfomationParkingCar.setCarKindName(reductionTsCarInfoResponseBody.getVhctyAsortNm());
//				if(null != reductionTsCarInfoResponseBody.getDsplvl() && !"".equals(reductionTsCarInfoResponseBody.getDsplvl())) {
//					carDspmnt = CommonUtil.castIntegerDefalutZero(reductionTsCarInfoResponseBody.getDsplvl());
//					adminInfomationParkingCar.setCarDspmnt(carDspmnt);
//				}
//				adminInfomationParkingCar.setErrorChk(Boolean.FALSE);
//				adminInfomationParkingCar.setCarNo(carNo);
//			}
//			else {
//				adminInfomationParkingCar.setErrorChk(Boolean.FALSE);
//				adminInfomationParkingCar.setIfRsltYn("N");
//				adminInfomationParkingCar.setIfType("01");
//				adminInfomationParkingCar.setIfTime(CommonUtil.getIfTime());
//				adminInfomationParkingCar.setIfDt(CommonUtil.getIfDate());
//				adminInfomationParkingCar.setCarNo(carNo);
//				adminInfomationParkingCar.setCarKindName(reductionTsCarInfoResponseBody.getVhctyAsortNm());
//				if(null != reductionTsCarInfoResponseBody.getDsplvl() && !"".equals(reductionTsCarInfoResponseBody.getDsplvl())) {
//					carDspmnt = CommonUtil.castIntegerDefalutZero(reductionTsCarInfoResponseBody.getDsplvl());
//					adminInfomationParkingCar.setCarDspmnt(carDspmnt);
//				}
//				
//			}
//			if(Boolean.TRUE.equals(dbProc)) {
//				mapper.ZP_PI_ADMIN_INFO_PAK_CAR_INS(adminInfomationParkingCar);
//			} else {
//				return new AsyncResult<AdminInfomationParkingCar>(adminInfomationParkingCar); 
//			}
//			
//		} catch (SOAPException soapException){
//			LOG.error("["+adminInfomationParkingCar.getPakMgmtNo()+"] " + soapException.getMessage());
//			adminInfomationParkingCar.setIfRsltYn("N");
//			adminInfomationParkingCar.setIfType("01");
//			adminInfomationParkingCar.setCarNo(carNo);
//		} catch (NullPointerException nullPointerException){
//			LOG.error("["+adminInfomationParkingCar.getPakMgmtNo()+"] " + nullPointerException.getMessage());
//			adminInfomationParkingCar.setIfRsltYn("N");	
//			adminInfomationParkingCar.setIfType("01");
//			adminInfomationParkingCar.setCarNo(carNo);
//		} catch (ArrayIndexOutOfBoundsException e) {
//			LOG.error("["+adminInfomationParkingCar.getPakMgmtNo()+"] " + e.getMessage());
//			adminInfomationParkingCar.setIfRsltYn("N");
//			adminInfomationParkingCar.setIfType("01");
//			adminInfomationParkingCar.setCarNo(carNo);
//		} catch (Exception e){	
//			LOG.error("["+adminInfomationParkingCar.getPakMgmtNo()+"] " + e.getMessage());
//			adminInfomationParkingCar.setIfRsltYn("N");
//			adminInfomationParkingCar.setIfType("01");
//			adminInfomationParkingCar.setCarNo(carNo);
//		
//		} 
		return new AsyncResult<AdminInfomationParkingCar>(adminInfomationParkingCar);
		
	}
	
	

}
