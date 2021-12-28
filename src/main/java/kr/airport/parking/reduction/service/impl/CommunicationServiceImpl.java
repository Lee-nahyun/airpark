package kr.airport.parking.reduction.service.impl;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.airport.parking.reduction.service.CommunicationService;
@Service
public class CommunicationServiceImpl implements CommunicationService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommunicationService.class);
	
	@Override
	public String communicate(String serviceUrl, String requestXml, String keyString, String serviceName) throws Exception{	
		return this.communicate(serviceUrl,requestXml,keyString,serviceName, "", "");	
	}

	@Override
	public String communicate(String serviceUrl, String requestXml, String keyString, String serviceName, String airportCode, String carNo) throws Exception {
		String contentType = "text/xml; charset=utf-8"; 
		String encoding =  "UTF-8";
		
		Integer connectTimeout = null;
		Integer soTimeout = null;
		PostMethod method = null; 
		
		if(null == connectTimeout ) {
			connectTimeout =  5 * 1000; 			 
		} 
		if(null == soTimeout ) {
			soTimeout = 5 * 1000;
		}
	
		String responseXml = null; 
		try {
		
		
		HttpClient client; 
			
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();

		params.setConnectionTimeout(connectTimeout);
		params.setSoTimeout(soTimeout);
		
		params.setTcpNoDelay(true);
			
		HttpConnectionManager conn = new SimpleHttpConnectionManager();
				
		conn.setParams(params);
		client = new HttpClient(conn);
				
		method = new PostMethod(serviceUrl);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
		method.getParams().setContentCharset(encoding);
		method.setRequestHeader("Content-Type", contentType);
		method.setRequestHeader("Connection", "close");
		
		RequestEntity requestEntity = new StringRequestEntity(requestXml, contentType, encoding);
		method.setRequestEntity(requestEntity);
			

		int responseCode = client.executeMethod(method); 
			
			LOG.info("HTTP Connettion serviceUrl '" + serviceUrl + "', reponseCode [" + responseCode + "]");
		
			if (responseCode == HttpStatus.SC_OK) {
					InputStream is = method.getResponseBodyAsStream();
					try {
						int readLen;
						byte[] buffer = new byte[1024];
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						while ((readLen = is.read(buffer)) >= 0) {
							baos.write(buffer, 0, readLen);
						}
						byte[] data = baos.toByteArray();
						responseXml = new String(data, encoding);
					} finally {
						is.close();
					}
			}
			else if(responseCode == HttpStatus.SC_BAD_GATEWAY) {
				responseXml = null;				
			}
			else if(responseCode == HttpStatus.SC_FORBIDDEN) {
				responseXml = null;
			}
		
		} catch (Throwable e) {
			
			LOG.error("["+carNo+"] " + serviceName +" " + e.getMessage());			
			responseXml = null;
			
			
		} finally {
			if (method != null) {
				method.releaseConnection(); 
			}
		}

		return responseXml;
		
	}
	
	
	

}
