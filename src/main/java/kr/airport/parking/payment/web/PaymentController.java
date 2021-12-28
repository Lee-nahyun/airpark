package kr.airport.parking.payment.web;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.airport.parking.payment.service.PaymentService;

@Controller
public class PaymentController {
	
	@Resource
	private PaymentService paymentService;
	private static Logger logger = Logger.getLogger(PaymentController.class);
	
	@RequestMapping(value={"/payment/payResult.do"})
	public String payResult(HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes) {
		//	String serverUrl = "https://211.35.58.112"; // 운영
		String serverUrl = "https://parking.airport.co.kr"; // 운영
		//String serverUrl = "http://192.168.0.173:8080"; // 로컬
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");
		String airportCode = request.getParameter("airportCode"); // TODO : 공항코드 받는지 확인
		String userId = request.getParameter("userId"); // TODO : 공항코드 받는지 확인
		logger.info("========================================userId : " + userId);
		logger.info("========================================PaymentController start");
		int returnVal = paymentService.payResult(request, response, session, userId);
		logger.info("========================================PaymentController return : " + returnVal);
		
		redirectAttributes.addAttribute("airportCode", airportCode);
		redirectAttributes.addAttribute("userId", userId);
		
		if( returnVal == 1 ) {
			return "redirect:"+serverUrl+"/commuter/cmmtktHistory/main.do";
		}else {
			return "redirect:"+serverUrl+"/commuter/cmmtktExtension/main.do";
		}
	}
	
}
