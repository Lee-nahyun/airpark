package kr.airport.parking.reduction.component;

import org.springframework.stereotype.Component;

@Component
public class RightShareManager {
	

	
	private Long checkingTime = 0L;

	public Long getCheckingTime() {
		return checkingTime;
	}

	public void setCheckingTime(Long checkingTime) {
		this.checkingTime = checkingTime;
	}
}
