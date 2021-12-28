package kr.airport.parking.payment.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.airport.parking.payment.service.PaymentService;
import kr.airport.parking.util.common.CommonUtil;
import kr.co.nicevan.nicepay.adapter.web.NicePayHttpServletRequestWrapper;
import kr.co.nicevan.nicepay.adapter.web.NicePayWEB;
import kr.co.nicevan.nicepay.adapter.web.dto.WebMessageDTO;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {
	private static Logger log = Logger.getLogger(PaymentServiceImpl.class);

	@Transactional
	public int payResult(HttpServletRequest request, HttpServletResponse response, HttpSession session, String userId) {
		String serverUrl = "https://parking.airport.co.kr"; // 운영
//		String serverUrl = "http://192.168.0.173:8080"; // 로컬
		
		String payMethod = request.getParameter("PayMethod");
		String startDateStr = request.getParameter("dtStartDate");
		String logPath = "/TMAX/airpark/web/log";

		String airportCode = request.getParameter("airportCode"); // TODO : 공항코드 받는지 확인
//      String userId = request.getParameter("userId"); // TODO : 공항코드 받는지 확인
		String merchantKey = request.getParameter("merchantKey"); // TODO : 공항코드 받는지 확인
//		String merchantID = request.getParameter("merchantID"); // TODO : 공항코드 받는지 확인
		String merchantPassword = request.getParameter("merchantPassword"); // TODO : 공항코드 받는지 확인
//		String logPath = request.getParameter("logPath"); // TODO : 공항코드 받는지 확인

		NicePayHttpServletRequestWrapper httpRequestWrapper = new NicePayHttpServletRequestWrapper(request);
		NicePayWEB nicepayWEB = new NicePayWEB();
		nicepayWEB.setParam("NICEPAY_LOG_HOME", logPath);
		nicepayWEB.setParam("APP_LOG", "1");
		nicepayWEB.setParam("EVENT_LOG", "1");
		nicepayWEB.setParam("EncFlag", "S");
		nicepayWEB.setParam("SERVICE_MODE", "PY0");
		nicepayWEB.setParam("Currency", "KRW");
		nicepayWEB.setParam("PayMethod", payMethod);

		// TODO 세션정보에 담긴 airportCode 별 pg.merchantKey, pg.merchantID,
		// pg.merchantPassword 각각 다르게 설정
		nicepayWEB.setParam("EncodeKey", merchantKey);
		WebMessageDTO responseDTO = nicepayWEB.doService(httpRequestWrapper, response);
		String resultCode = responseDTO.getParameter("ResultCode");
		String resultMsg = responseDTO.getParameter("ResultMsg");
		String authDate = responseDTO.getParameter("AuthDate");
		String authCode = responseDTO.getParameter("AuthCode");
		String buyerName = responseDTO.getParameter("BuyerName");
		String mallUserID = responseDTO.getParameter("MallUserID");
		String goodsName = responseDTO.getParameter("GoodsName");
		String mid = responseDTO.getParameter("MID");
		String tid = responseDTO.getParameter("TID");
		String moid = responseDTO.getParameter("Moid");
		String amt = responseDTO.getParameter("Amt");
		String cardCode = responseDTO.getParameter("CardCode");
		String cardName = responseDTO.getParameter("CardName");
		String cardQuota = responseDTO.getParameter("CardQuota");
		String dstAddr = responseDTO.getParameter("DstAddr");
		String bankCode = responseDTO.getParameter("BankCode");
		String bankName = responseDTO.getParameter("BankName");
		String rcptType = responseDTO.getParameter("RcptType");
		String rcptAuthCode = responseDTO.getParameter("RcptAuthCode");
		String rcptTID = responseDTO.getParameter("RcptTID");

		log.info("========================================message1=======================================");
		log.debug("responseDTO:airportCode["+airportCode+"],userId[" + userId + "],merchantKey[" + merchantKey + "]\nresultCode[" + resultCode + "]\nresultMsg[" + resultMsg + "]\n" + "authDate[" + authDate
				+ "]\nauthCode[" + authCode + "]\n" + "buyerName[" + buyerName + "]\nmallUserID[" + mallUserID + "]\n"
				+ "goodsName[" + goodsName + "]\nmid[" + mid + "]\ntid[" + tid + "]\n" + "moid[" + moid + "]\namt["
				+ amt + "]\ncardCode[" + cardCode + "]\n" + "cardName[" + cardName + "]\ncardQuota[" + cardQuota + "]\n"
				+ "dstAddr[" + dstAddr + "]\nbankCode[" + bankCode + "]\n" + "bankName[" + bankName + "]\nrcptType["
				+ rcptType + "]\n" + "rcptAuthCode[" + rcptAuthCode + "]\nrcptTID[" + rcptTID + "]\n" + "startDateStr["
				+ startDateStr + "]");

		String paySuccess = "false";
		if (resultCode.equals("3001")) {
			paySuccess = "true";
		} else if (resultCode.equals("4000")) {
			paySuccess = "true";
		}
		log.info("========================================message2=======================================");
		
		log.info("Nice Pay Status paySuccess: " + paySuccess);

		String inputLine;
		StringBuffer responseStringBuffer = new StringBuffer();
		
		if("true".equals(paySuccess)) {
			try {
				log.info("========================================message2=======================================");
				String urlParameters = "paySuccess=" + URLEncoder.encode(CommonUtil.nvl(paySuccess,""), "utf-8");
				urlParameters = urlParameters + "&airportCode=" + URLEncoder.encode(CommonUtil.nvl(airportCode,""), "utf-8");
				urlParameters = urlParameters + "&userId=" + URLEncoder.encode(CommonUtil.nvl(userId,""), "utf-8");
				urlParameters = urlParameters + "&moid=" + URLEncoder.encode(CommonUtil.nvl(moid,""), "utf-8");
				urlParameters = urlParameters + "&mid=" + URLEncoder.encode(CommonUtil.nvl(mid,""), "utf-8");
				urlParameters = urlParameters + "&startDateStr=" + URLEncoder.encode(CommonUtil.nvl(startDateStr,""), "utf-8");
				urlParameters = urlParameters + "&amt=" + URLEncoder.encode(CommonUtil.nvl(amt,""), "utf-8");
				urlParameters = urlParameters + "&cardCode=" + URLEncoder.encode(CommonUtil.nvl(cardCode,""), "utf-8");
				urlParameters = urlParameters + "&bankCode=" + URLEncoder.encode(CommonUtil.nvl(bankCode,""), "utf-8");
				urlParameters = urlParameters + "&authCode=" + URLEncoder.encode(CommonUtil.nvl(authCode,""), "utf-8");
				urlParameters = urlParameters + "&authDate=" + URLEncoder.encode(CommonUtil.nvl(authDate,""), "utf-8");
				urlParameters = urlParameters + "&cardName=" + URLEncoder.encode(CommonUtil.nvl(cardName,""), "utf-8");
				
				// DB 처리 호출
				String url = serverUrl + "/commuter/cmmtktExtension/payResultExec.do"; // 통합 WEB으로 DB 처리 호출
				log.info("http Connection url : " + url);
				URL obj = new URL(url);
				
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				con.setRequestMethod("POST");
	
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();
	
				int responseCode = con.getResponseCode();
				log.info("http Connection responseCode : " + responseCode);
	
				if (responseCode == 200) {
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	
					while ((inputLine = in.readLine()) != null) {
						responseStringBuffer.append(inputLine);
					}
					in.close();
				} else {
					// 결제 취소 처리를 해야 함.
					cancleExce(response, httpRequestWrapper, nicepayWEB, amt, mid, tid, logPath, merchantPassword, moid, airportCode, userId);
				}
				con.disconnect();
	
				String resString = responseStringBuffer.toString();
				
				if ("\"0\"".equals(resString)) { // DB 처리에 오류가 있으면 결제 취소 처리를 해야 함.
					cancleExce(response, httpRequestWrapper, nicepayWEB, amt, mid, tid, logPath, merchantPassword, moid, airportCode, userId);
				}
				return 1; // 정상
			} catch (Exception e) {
				log.error("============================================================");
				log.error(e.getMessage());
				log.error("============================================================");
				cancleExce(response, httpRequestWrapper, nicepayWEB, amt, mid, tid, logPath, merchantPassword, moid, airportCode, userId);
				return 0; // 오류
			}
		}else {
			return 0; // 오류
		}
	}

	public boolean cancleExce(HttpServletResponse response, NicePayHttpServletRequestWrapper httpRequestWrapper,
			NicePayWEB nicepayWEB, String amt, String mid, String tid, String logPath, String merchantPassword,
			String moid, String airportCode, String userId) {
		
		String serverUrl = "https://parking.airport.co.kr"; // 운영
//		String serverUrl = "http://192.168.0.173:8080"; // 로컬
		try {
			httpRequestWrapper.addParameter("CancelAmt", amt);
			httpRequestWrapper.addParameter("Mid", mid);
			httpRequestWrapper.addParameter("Tid", tid);
			// TODO 세션정보에 담긴 airportCode 별 pg.merchantKey, pg.merchantID,
			// pg.merchantPassword 각각 다르게 설정
			httpRequestWrapper.addParameter("CancelPwd", merchantPassword);
			httpRequestWrapper.addParameter("CancelMsg", "결제 디비처리 오류");
			httpRequestWrapper.addParameter("CancelIP", "127.0.0.1");
			httpRequestWrapper.addParameter("ParialCancelCode", "0");
			nicepayWEB.setParam("NICEPAY_LOG_HOME", logPath);
			nicepayWEB.setParam("APP_LOG", "1");
			nicepayWEB.setParam("EVENT_LOG", "1");
			nicepayWEB.setParam("EncFlag", "S");
			nicepayWEB.setParam("SERVICE_MODE", "CL0");

			WebMessageDTO cancelResponseDTO = nicepayWEB.doService(httpRequestWrapper, response);
			String cancelResultCode = cancelResponseDTO.getParameter("ResultCode");
			String cancelResultMsg = cancelResponseDTO.getParameter("ResultMsg");
			String cancelAmt = cancelResponseDTO.getParameter("CancelAmt");
			String cancelDate = cancelResponseDTO.getParameter("CancelDate");
			String cancelTime = cancelResponseDTO.getParameter("CancelTime");
			String cancelNum = cancelResponseDTO.getParameter("CancelNum");
			String cancelPayMethod = cancelResponseDTO.getParameter("PayMethod");
			String cancelMid = cancelResponseDTO.getParameter("MID");
			String cancelTid = cancelResponseDTO.getParameter("TID");

			// 결제 취소 완료 되면
			if (cancelResultCode.equals("2001")) {
				// 통합 WEB쪽 호출하기
				String inputLine;
				StringBuffer responseStringBuffer = new StringBuffer();

				try {

					String urlParameters = "moid=" + URLEncoder.encode(CommonUtil.nvl(moid,""), "utf-8");
					urlParameters = urlParameters + "&airportCode=" + URLEncoder.encode(CommonUtil.nvl(airportCode,""), "utf-8");
					urlParameters = urlParameters + "&userId=" + URLEncoder.encode(CommonUtil.nvl(userId,""), "utf-8");					

					// DB 처리 호출
					String url = serverUrl + "/commuter/cmmtktExtension/payResultCancel.do"; // 통합 WEB으로 DB 처리 호출
					log.info("http Connection url : " + url);
					URL obj = new URL(url);

					HttpURLConnection con = (HttpURLConnection) obj.openConnection();
					con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
					con.setRequestMethod("POST");

					con.setDoOutput(true);
					DataOutputStream wr = new DataOutputStream(con.getOutputStream());
					wr.writeBytes(urlParameters);
					wr.flush();
					wr.close();

					int responseCode = con.getResponseCode();
					log.info("http Connection responseCode : " + responseCode);

					if (responseCode == 200) {
						BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

						while ((inputLine = in.readLine()) != null) {
							responseStringBuffer.append(inputLine);
						}
						in.close();
					} else {
						return false; // 예외처리
					}
					con.disconnect();

					if ("false".equals(response.toString())) {
						return false; // 예외처리
					}
				} catch (Exception e) {
					log.error(e.getMessage());
					return false; // 예외처리
				}
				return true;
			} else {
				log.error("결제 취소 처리시 오류 발생 : [" + cancelResultCode + "]");
				return false; // 예외처리
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return false; // 예외처리
		}
	}
}
