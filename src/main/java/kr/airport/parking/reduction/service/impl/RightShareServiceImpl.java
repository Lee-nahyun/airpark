package kr.airport.parking.reduction.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.airport.parking.reduction.component.RightShareManager;
import kr.airport.parking.reduction.service.ReductionTsCarInfoService;
import kr.airport.parking.reduction.service.RightShareService;
import kr.airport.parking.reduction.vo.table.AdminInfomationParkingCar;
import kr.airport.parking.reduction.vo.table.RealTimeParkingCar;

@Service
public class RightShareServiceImpl implements RightShareService {

	private static final Logger LOG = LoggerFactory.getLogger(RightShareService.class);
		
//	@Resource
//	private ParkingManageMapper parkingManageMapper;	
	
	@Resource
	private ReductionTsCarInfoService reductionTsCarInfoService;	
	
	
	
	@Resource
	private RightShareManager rightShareManager;

	@Value("#{appConfig['passPhrase']}")	
	private String passPhrase;
	
	@Value("#{appConfig['chldrnAllowCnt']}")	
	private String chldrnAllowCnt;
	
	@Value("#{appConfig['dbFlag']}")	
	private String dbFlag; //SITE_ID
	
	@Value("#{appConfig['reductionTsCarInfo.T1.procYn']}")	
	private String reductionTsCarInfoT1ProcYn;
	
	@Value("#{appConfig['reductionTsCarInfo.T2.procYn']}")	
	private String reductionTsCarInfoT2ProcYn;
	
	
	@Override
	public AdminInfomationParkingCar getReductionTsCarInfo(RealTimeParkingCar realTimeParkingCar) {
		
		AdminInfomationParkingCar adminInfomationParkingCar = reductionTsCarInfoService.proc(realTimeParkingCar,Boolean.FALSE,null);	
		return adminInfomationParkingCar;
	}
	
	@Override
	public void procReductCarInfo(String siteId) {	
//		String ifType = "01";
//		RealTimeParkingCar realTimeParkingCar = parkingManageMapper.getPiRtimPakCarBySiteIdAndIfType(siteId,ifType);
//		if(null == realTimeParkingCar) {
//			return;
//		}
//		
//		Boolean dbProc =  Boolean.FALSE;
//		if("T1".equals(siteId)) {
//			if("N".equals(reductionTsCarInfoT1ProcYn)) {
//				return;
//			}
//		} else if("T2".equals(siteId)) {
//			if("N".equals(reductionTsCarInfoT2ProcYn)) {
//				return;
//			}
//		} else {
//			LOG.info("["+siteId+"]["+realTimeParkingCar.getAirportCode()+"_"+realTimeParkingCar.getiId()+"]["+realTimeParkingCar.getCarNo()+"] Time :" + CommonUtil.getIfDate() +" " +CommonUtil.getIfTime()+ " ReductCarInfo Proc siteId ERROR " ); 
//			return;
//		}
//		 
//		if ("Y".equals(dbFlag)) {
//			 dbProc = Boolean.TRUE;
//		} else {
//			 dbProc = Boolean.FALSE;
//		}
//	
//		AdminInfomationParkingCar adminInfomationParkingCar = reductionTsCarInfoService.proc(realTimeParkingCar,dbProc,null);
//			
//		if(Boolean.FALSE.equals(adminInfomationParkingCar.getErrorChk())) {
//			if(Boolean.FALSE.equals(dbProc)) {				
//				parkingManageMapper.ZP_PI_ADMIN_INFO_PAK_CAR_INS(adminInfomationParkingCar);
//			}
////			LOG.info("["+siteId+"]["+realTimeParkingCar.getAirportCode()+"_"+realTimeParkingCar.getiId()+"]["+realTimeParkingCar.getCarNo()+"] Time :" + CommonUtil.getIfDate() +" " +CommonUtil.getIfTime()+ " | "+ adminInfomationParkingCar.getIfType() + ":" + adminInfomationParkingCar.getCarDspmnt()  ); 
//
//		} else {
//				LOG.info("["+siteId+"]["+realTimeParkingCar.getAirportCode()+"_"+realTimeParkingCar.getiId()+"]["+realTimeParkingCar.getCarNo()+"] Time :" + CommonUtil.getIfDate() +" " +CommonUtil.getIfTime()+ " ReductCarInfo Proc Error " ); 
//		}
	}

}
