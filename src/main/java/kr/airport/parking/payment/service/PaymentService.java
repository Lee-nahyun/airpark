package kr.airport.parking.payment.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface PaymentService {
	public int payResult(HttpServletRequest request, HttpServletResponse response, HttpSession session, String userId);
}
