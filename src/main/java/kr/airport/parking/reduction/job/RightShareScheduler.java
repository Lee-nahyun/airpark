package kr.airport.parking.reduction.job;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import kr.airport.parking.reduction.service.RightShareService;


@Service
public class RightShareScheduler {
	
	@Resource
	private RightShareService service;
	
	/**
	 * <pre>
	 *	실시간 즉각감면 서비스 조회 
	 * 	전송주기 : 1초 딜레이 실행
	 * </pre>
	 * 
	 * @since
	 * @수정자
	 * @수정일
	 * @수정내용
	 *
	 */

//	@Scheduled(fixedDelay=1000)
	public void procReductCarInfoChk_T2(){		
		//service.procReductCarInfo("T2");
	}
	
}
