package kr.airport.parking.reduction.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.airport.parking.reduction.component.RightShareManager;
import kr.airport.parking.reduction.service.AllChldrnCoBirthInfoService;
import kr.airport.parking.reduction.service.LowPollutionCarYnService;
import kr.airport.parking.reduction.service.MeritCarYnService;
import kr.airport.parking.reduction.service.NewLowPollutionCarYnService;
import kr.airport.parking.reduction.service.ReductionDisabledCarYnService;
import kr.airport.parking.reduction.service.ReductionService;
import kr.airport.parking.reduction.service.ReductionTsCarInfoNewService;
import kr.airport.parking.reduction.service.ReductionTsCarInfoService;
import kr.airport.parking.reduction.service.RightShareService;
import kr.airport.parking.reduction.vo.table.AdminInfomationParkingCar;
import kr.airport.parking.reduction.vo.table.AllChldrnCoBirthInfo;
import kr.airport.parking.reduction.vo.table.RealTimeParkingCar;

@Service
public class ReductionServiceImpl implements ReductionService {

	private static final Logger LOG = LoggerFactory.getLogger(RightShareService.class);
		
//	@Resource
//	private ParkingManageMapper parkingManageMapper;	
	
	@Resource
	private ReductionTsCarInfoService reductionTsCarInfoService;
	
	@Resource
	private ReductionTsCarInfoNewService reductionTsCarInfoNewService;	
	
	@Resource
	private LowPollutionCarYnService lowPollutionCarYnService;
	
	@Resource
	private NewLowPollutionCarYnService newLowPollutionCarYnService;	
	
	@Resource
	private MeritCarYnService meritCarYnService;
	
	@Resource
	private ReductionDisabledCarYnService reductionDisabledCarYnService;
	
	@Resource
	private AllChldrnCoBirthInfoService allChldrnCoBirthInfoService;
	
	
	
	
	@Resource
	private RightShareManager rightShareManager;
	
//	@Resource
//	private TckttrnsMapper tckttrnsMapper;
	
	
	/**
	 * @Comment("즉시감면 대상 자격여부 처리 웹호출-경차여부")
	 * 
	 */
	@Override
	public AdminInfomationParkingCar getReductionTsCarInfo(RealTimeParkingCar realTimeParkingCar) {
		LOG.info("getReductionTsCarInfo Service Call");
		AdminInfomationParkingCar adminInfomationParkingCar = reductionTsCarInfoService.proc(realTimeParkingCar,Boolean.FALSE,null);
		LOG.info("getReductionTsCarInfo Service End");
		return adminInfomationParkingCar;
	}
	
	/**
	 * @Comment("즉시감면 대상 자격여부 처리 웹호출-경차여부-신규")
	 * 
	 */
	@Override
	public AdminInfomationParkingCar getReductionTsCarNewInfo(RealTimeParkingCar realTimeParkingCar) {
		LOG.info("getReductionTsCarInfo Service Call");
		AdminInfomationParkingCar adminInfomationParkingCar = reductionTsCarInfoNewService.proc(realTimeParkingCar,Boolean.FALSE,null);
		LOG.info("getReductionTsCarInfo Service End");
		return adminInfomationParkingCar;
	}	
	
	
	/**
	 * @Comment("즉시감면 대상 자격여부 처리 웹호출-저공해차량여부")
	 * 
	 */
	@Override
	public AdminInfomationParkingCar getLowPollutionCarYn(RealTimeParkingCar realTimeParkingCar) {
		LOG.info("getLowPollutionCarYn Service Call");
		AdminInfomationParkingCar adminInfomationParkingCar = lowPollutionCarYnService.proc(realTimeParkingCar,Boolean.FALSE,null);
		LOG.info("getLowPollutionCarYn Service End");
		return adminInfomationParkingCar;
	}
	
	/**
	 * @Comment("즉시감면 대상 자격여부 처리 웹호출-(신규)저공해차량여부")
	 * 
	 */
	@Override
	public AdminInfomationParkingCar getNewLowPollutionCarYn(RealTimeParkingCar realTimeParkingCar) {
		LOG.info("getNewLowPollutionCarYn Service Call");
		AdminInfomationParkingCar adminInfomationParkingCar = newLowPollutionCarYnService.proc(realTimeParkingCar,Boolean.FALSE,null);
		LOG.info("getNewLowPollutionCarYn Service End");
		return adminInfomationParkingCar;
	}	
	
	/**
	 * @Comment("즉시감면 대상 자격여부 처리 웹호출-국가유공자차량여부")
	 * 
	 */
	@Override
	public AdminInfomationParkingCar getMeritCarYn(RealTimeParkingCar realTimeParkingCar) {
		LOG.info("getMeritCarYn Service Call");
		AdminInfomationParkingCar adminInfomationParkingCar = meritCarYnService.proc(realTimeParkingCar,Boolean.FALSE,null);
		LOG.info("getMeritCarYn Service End");
		return adminInfomationParkingCar;
	}
	
	/**
	 * @Comment("즉시감면 대상 자격여부 처리 웹호출-장애인차량여부")
	 * 
	 */
	@Override
	public AdminInfomationParkingCar getReductionDisabledCarYn(RealTimeParkingCar realTimeParkingCar) {
		LOG.info("getReductionDisabledCarYn Service Call");
		AdminInfomationParkingCar adminInfomationParkingCar = reductionDisabledCarYnService.proc(realTimeParkingCar,Boolean.FALSE,null);
		LOG.info("getReductionDisabledCarYn Service End");
		return adminInfomationParkingCar;
	}
	
	/**
	 * @Comment("즉시감면 대상 자격여부 처리 웹호출-장애인차량여부")
	 * 
	 */
	@Override
	public AllChldrnCoBirthInfo getAllChldrnCoBirthInfo(AllChldrnCoBirthInfo allChldrnCoBirthInfo) {
		LOG.info("getAllChldrnCoBirthInfo Service Call");
		AllChldrnCoBirthInfo resAllChldrnCoBirthInfo = allChldrnCoBirthInfoService.proc(allChldrnCoBirthInfo,Boolean.FALSE,null);
		LOG.info("getAllChldrnCoBirthInfo Service End");
		return resAllChldrnCoBirthInfo;
	}
	
	@Override
	public void callReductionProc(String airportCode, String iId, String carNo){
		
	}
}
