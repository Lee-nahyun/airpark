package kr.airport.parking.util.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.airport.parking.util.gpki.NewGpkiUtil;;

public class CommonUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommonUtil.class);
	private static final String charset = "UTF-8";
	
	//수신데이터 GPKI  복호화 
	/*
	 *  
	 */
	public static String decrypt(NewGpkiUtil g,String receiveString)
	{
		String decrypted = null;
		byte[] decoded;
		try {
			decoded = g.decode(receiveString);
			byte[] validated = g.validate(decoded);
			decrypted = new String(g.decrypt(validated), "UTF-8");
			decrypted = decrypted.replace("><", ">\n<");
		} catch (Exception e) {
			e.printStackTrace();
			decrypted = null;
		}
		
		return decrypted;
	}
	
	//수신데이터 GPKI 암호화 
	/*
	 * 
	 */
	public static String encrypt(NewGpkiUtil g,String sendString,String targetServerId)
	{
		byte[] encrypted = null;
		String encoded = null;
		try {
		
			encrypted = g.encrypt(sendString.getBytes(charset), targetServerId);
			byte[] signed = g.sign(encrypted);
			encoded = g.encode(signed);
			return encoded;
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		return encoded;
	}
	/*
	 * 웹에서 오는 id decrypt 
	 */
	public static String webDecrypt(String id){
		String decrypt = null;
		return decrypt;
		
	}
	
	
	/*
	 * 수신용 TransactionUniqueKey 생성 
	 */	
	public static String getTransactionUniqueId()
	{
		
		String rnd1 = Double.toString(java.lang.Math.random()).substring(2, 6);
		String rnd2 = Double.toString(java.lang.Math.random()).substring(2, 6);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.KOREA);
		String cur = sdf.format(new Date());
		return cur + rnd1 + rnd2;
	}
	
	/*
	 * 현 날짜 반환 DATE mask yyyy-MM-dd
	 */	
	public static String getIfDate()
	{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		String cur = sdf.format(new Date());
		return cur;
	}
	
	/*
	 * 현 시간 반환 DATE mask HH:mm:ss
	 */	
	public static String getIfTime()
	{
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
		String cur = sdf.format(new Date());
		return cur;
	}
	
	/*
	 * 현 시간 반환 DATE mask HH:mm:ss
	 */	
	public static Integer castIntegerDefalutZero(String s)
	{
		Integer i = 0;
		try {
			i = Integer.valueOf(s);
			
		} catch (Exception e) {
			LOG.debug(e.getMessage());
			i = 0;
			// TODO: handle exception
		}
		return i;
	}
	/*
	 * 차량번호 체크
	 */
	public static boolean isValidCarNumber(String carNum){
		if(null == carNum) {
			return false;
		}
		carNum = carNum.replaceAll(" ", "");
		boolean returnValue = false;
		try {
			String regex = "^\\d{2}[가|나|다|라|마|거|너|더|러|머|버|서|어|저|고|노|도|로|모|보|소|오|조|구|누|두|루|무|부|수|우|주|바|사|아|자|허|배|호|하\\x20]\\d{4}/*$";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(carNum);
			if (m.matches()) {
				returnValue = true;
			} else {
				regex = "^[서울|부산|대구|인천|대전|광주|울산|제주|경기|강원|충남|전남|전북|경남|경북|세종]{2}\\d{2}[가|나|다|라|마|거|너|더|러|머|버|서|어|저|고|노|도|로|모|보|소|오|조|구|누|두|루|무|부|수|우|주|바|사|아|자|허|배|호|하\\x20]\\d{4}$";
				p = Pattern.compile(regex);
				m = p.matcher(carNum);
				if (m.matches()) {
					returnValue = true;
				}
			}
			return returnValue;
		} catch(Exception e) {
			return false;
		}
	} 
	
	/**
	 * null 처리
	 * @param obj
	 * @param defaultVal
	 * @return
	 */
	public static String nvl(Object obj, String defaultVal) {
		return (obj == null || "".equals(obj)) ? defaultVal : obj.toString();
	}

}
