package kr.airport.parking.reduction.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.airport.parking.reduction.service.ReductionService;
import kr.airport.parking.reduction.vo.table.AllChldrnCoBirthInfo;

@Controller
public class TestController {
	
	@Resource 
	ReductionService reductionService;
	
	
	@RequestMapping(value={"/test.do"})
	public String test() {
		return "test";
	}
	
	@RequestMapping(value={"/testAllChildren.do"})
	public String testAllChildren() {
		return "testAllChildren";
	}
	
	
	/*
	 * 다자녀 차량 감면 웹 조회
	 */
	@RequestMapping(value={"/getTestAllChldrnCoBirthInfo"})
	public @ResponseBody AllChldrnCoBirthInfo getAllChldrnCoBirthInfo(
			@RequestParam(value="airportCode", required=true) String airportCode,
			@RequestParam(value="name", required=true) String name,
			@RequestParam(value="id", required=false) String id) {
		
		AllChldrnCoBirthInfo allChldrnCoBirthInfo = new AllChldrnCoBirthInfo();
		allChldrnCoBirthInfo.setAirportCode(airportCode);
		allChldrnCoBirthInfo.setId(id);
		allChldrnCoBirthInfo.setName(name);
		
		return reductionService.getAllChldrnCoBirthInfo(allChldrnCoBirthInfo);
	}
	
}
