package kr.airport.parking.reduction.web;

import java.net.URLDecoder;
import java.nio.charset.Charset;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.airport.parking.reduction.service.AllChldrnCoBirthInfoService;
import kr.airport.parking.reduction.service.ReductionService;
import kr.airport.parking.reduction.vo.table.AdminInfomationParkingCar;
import kr.airport.parking.reduction.vo.table.AllChldrnCoBirthInfo;
import kr.airport.parking.reduction.vo.table.RealTimeParkingCar;


@Controller
public class ReductionController {
	
	private final Logger LOG = LoggerFactory.getLogger(AllChldrnCoBirthInfoService.class);
	
	@Resource 
	ReductionService reductionService;
	
	
	/*
	 * 감면 대상 정보 전체 조회
	 *  SELECT I_RATE, AC_RATE_KEY_NAME, D_MISC1, AC_PLATE1 FROM TCKTTRNS
 	 *   1. I_RATE 필드에 맞는 코드값 , AC_RATE_KEY_NAME 할인명 업데이트
 	 *   2. 저공해 차량의 경우 D_MISC1 필드에 1종 -> 1, 2종 -> 2, 3종 -> 3 업데이트  
	 */
	@RequestMapping(value={"/getReductionCar"})
	public @ResponseBody AdminInfomationParkingCar getReductionCar(
			@RequestParam(value="carNo", required=false) String carNo) {
		
		try {
			carNo = URLDecoder.decode(carNo,"utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		RealTimeParkingCar realTimeParkingCar = new RealTimeParkingCar();
		realTimeParkingCar.setCarNo(carNo);
		
		//감면 대상 차량일 경우 디비 정보 업데이트 여부
		realTimeParkingCar.setRightShareYn("Y");
		
		AdminInfomationParkingCar adminInfomationParkingCar = new AdminInfomationParkingCar(); 
		
		//네 개중 하나라도 감면 대상 여부가  Y이면 정보 업데이트 및 MQ서버 전송 등록
		adminInfomationParkingCar = reductionService.getReductionTsCarInfo(realTimeParkingCar);
		if("Y".equals(adminInfomationParkingCar.getReductionYn())){
			return adminInfomationParkingCar;
		}
		else{
			adminInfomationParkingCar = reductionService.getMeritCarYn(realTimeParkingCar);
			if("Y".equals(adminInfomationParkingCar.getReductionYn())){
				return adminInfomationParkingCar;
			}
			else{
				adminInfomationParkingCar = reductionService.getReductionDisabledCarYn(realTimeParkingCar);
				if("Y".equals(adminInfomationParkingCar.getReductionYn())){
					return adminInfomationParkingCar;
				}
				else{
					// adminInfomationParkingCar = reductionService.getLowPollutionCarYn(realTimeParkingCar); // 기존 환경관리공단 연동 방식
					adminInfomationParkingCar = reductionService.getNewLowPollutionCarYn(realTimeParkingCar); // 신규 행안부 연동방식
					if("Y".equals(adminInfomationParkingCar.getReductionYn())){
						return adminInfomationParkingCar;
					}
				}
			}
		}
		
		return adminInfomationParkingCar;
	}
	
	/*
	 * 대형차 할증 웹 조회(신규) 							
	 */
	@RequestMapping(value={"/getReductionBigCar"},method=RequestMethod.POST)
	public @ResponseBody AdminInfomationParkingCar getReductionBigCar(
			@RequestParam(value="carNo", required=false) String carNo) {
		
		try {
			carNo = URLDecoder.decode(carNo,"utf-8");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		RealTimeParkingCar realTimeParkingCar = new RealTimeParkingCar();
		realTimeParkingCar.setCarNo(carNo);
		
		//감면 대상 차량일 경우 디비 정보 업데이트 여부
		realTimeParkingCar.setRightShareYn("Y");
		
		AdminInfomationParkingCar adminInfomationParkingCar = new AdminInfomationParkingCar(); 
		
		// 대형차 할증 조회
		adminInfomationParkingCar = reductionService.getReductionTsCarNewInfo(realTimeParkingCar);
		if("Y".equals(adminInfomationParkingCar.getReductionYn())){
			return adminInfomationParkingCar;
		}
		
		return adminInfomationParkingCar;
	}
	
	/*
	 * 경차 감면 웹 조회 							
	 */
	@RequestMapping(value={"/getReductionTsCarInfo"},method=RequestMethod.POST)
	public @ResponseBody AdminInfomationParkingCar getReductionTsCarInfo(
			@RequestParam(value="carNo", required=true) String carNo,
			@RequestParam(value="pakNumber", required=false) String pakNumber) {
		
		RealTimeParkingCar realTimeParkingCar = new RealTimeParkingCar();
		realTimeParkingCar.setCarNo(carNo);
		
		//감면 대상 차량일 경우 디비 정보 업데이트 여부
		realTimeParkingCar.setRightShareYn("N");
		return reductionService.getReductionTsCarInfo(realTimeParkingCar);
	}
	
	/*
	 * 경차 감면 웹 조회(신규) 							
	 */
	@RequestMapping(value={"/getReductionTsCarInfoNew"},method=RequestMethod.POST)
	public @ResponseBody AdminInfomationParkingCar getReductionTsCarInfoNew(
			@RequestParam(value="carNo", required=true) String carNo,
			@RequestParam(value="pakNumber", required=false) String pakNumber) {
		
		RealTimeParkingCar realTimeParkingCar = new RealTimeParkingCar();
		realTimeParkingCar.setCarNo(carNo);
		
		//감면 대상 차량일 경우 디비 정보 업데이트 여부
		realTimeParkingCar.setRightShareYn("N");
		return reductionService.getReductionTsCarNewInfo(realTimeParkingCar);
	}
	
	/*
	 * 저공해 차량 감면 웹 조회 
	 */
	@RequestMapping(value={"/getLowPollutionCarYn"},method=RequestMethod.POST)
	public @ResponseBody AdminInfomationParkingCar getLowPollutionCarYn(
			@RequestParam(value="carNo", required=true) String carNo,
			@RequestParam(value="pakNumber", required=false) String pakNumber) {
		RealTimeParkingCar realTimeParkingCar = new RealTimeParkingCar();
		realTimeParkingCar.setCarNo(carNo);
		
		//감면 대상 차량일 경우 디비 정보 업데이트 여부
		realTimeParkingCar.setRightShareYn("N");
		return reductionService.getLowPollutionCarYn(realTimeParkingCar);
	}
	
	/*
	 * 저공해 차량 감면 웹 조회(신규)
	 */
	@RequestMapping(value={"/getNewLowPollutionCarYn"},method=RequestMethod.POST)
	public @ResponseBody AdminInfomationParkingCar getNewLowPollutionCarYn(
			@RequestParam(value="carNo", required=true) String carNo,
			@RequestParam(value="pakNumber", required=false) String pakNumber) {
		RealTimeParkingCar realTimeParkingCar = new RealTimeParkingCar();
		realTimeParkingCar.setCarNo(carNo);
		
		//감면 대상 차량일 경우 디비 정보 업데이트 여부
		realTimeParkingCar.setRightShareYn("N");
		return reductionService.getNewLowPollutionCarYn(realTimeParkingCar);
	}	
	
	/*
	 * 국가유공자 차량 감면 웹 조회
	 */
	@RequestMapping(value={"/getMeritCarYn"},method=RequestMethod.POST)
	public @ResponseBody AdminInfomationParkingCar getMeritCarYn(
			@RequestParam(value="carNo", required=true) String carNo,
			@RequestParam(value="pakNumber", required=false) String pakNumber) {
		RealTimeParkingCar realTimeParkingCar = new RealTimeParkingCar();
		realTimeParkingCar.setCarNo(carNo);
		
		//감면 대상 차량일 경우 디비 정보 업데이트 여부
		realTimeParkingCar.setRightShareYn("N");
		return reductionService.getMeritCarYn(realTimeParkingCar);
	}
	
	/*
	 * 장애인 차량 감면 웹 조회
	 */
	@RequestMapping(value={"/getReductionDisabledCarYn"},method=RequestMethod.POST)
	public @ResponseBody AdminInfomationParkingCar getReductionDisabledCarYn(
			@RequestParam(value="carNo", required=true) String carNo,
			@RequestParam(value="pakNumber", required=false) String pakNumber) {
		RealTimeParkingCar realTimeParkingCar = new RealTimeParkingCar();
		realTimeParkingCar.setCarNo(carNo);
		
		//감면 대상 차량일 경우 디비 정보 업데이트 여부
		realTimeParkingCar.setRightShareYn("N");
		return reductionService.getReductionDisabledCarYn(realTimeParkingCar);
	}
	
	/*
	 * 다자녀 차량 감면 웹 조회
	 */
	@RequestMapping(value={"/getAllChldrnCoBirthInfo"})
	public @ResponseBody AllChldrnCoBirthInfo getAllChldrnCoBirthInfo(
			@RequestParam(value="airportCode", required=true) String airportCode,
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="id", required=false) String id) {
		
		try {
			/* 운영시 */
			name = URLDecoder.decode(name,"utf-8");
			LOG.info("getAllChldrnCoBirthInfo name : " + name);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		AllChldrnCoBirthInfo allChldrnCoBirthInfo = new AllChldrnCoBirthInfo();
		allChldrnCoBirthInfo.setAirportCode(airportCode);
		allChldrnCoBirthInfo.setId(id);
		allChldrnCoBirthInfo.setName(name);
		
		return reductionService.getAllChldrnCoBirthInfo(allChldrnCoBirthInfo);
	}
	
}
