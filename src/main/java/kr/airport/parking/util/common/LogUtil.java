package kr.airport.parking.util.common;

import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class LogUtil {
	static public void startLog(Logger logger, String procedureName, HashMap<String, Object> param){
		logger.info(getCallerName() + "---------- [" + procedureName + "] 실행 시작 ----------");
		
		if  ( param != null ) {
			for(Entry<String, Object> entry : param.entrySet()) {
			    logger.info(getCallerName() + "<parameter> :: " + entry.getKey() + " = " + entry.getValue());
			}
		}
	}
	 
	static public void resultLog(Logger logger, String procedureName, String message){
		if  ( message.equals("OK") ) {
			logger.info(getCallerName() + procedureName + " 처리 성공!");
		} else {
			logger.warn(getCallerName() + "MESSAGE : " + message);
		}
	}
	
	static public void endLog(Logger logger, String procedureName){
		logger.info(getCallerName() + "---------- [" + procedureName + "] 실행 완료 ----------");
	}
	
	static private String getCallerName(){
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		StackTraceElement e = stackTraceElements[3];
		
		return "[" + e.getMethodName() + "] ";
	}
}
