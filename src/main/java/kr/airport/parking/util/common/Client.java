package kr.airport.parking.util.common;

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

/**
 * API 송수신 모듈 
 *
 */
public abstract class Client {
   
	private static final Logger LOG = LoggerFactory.getLogger(Client.class);

	
	public static String doService(String serviceUrl,String requestXml)   {
		
		String	contentType = null;
		String	encoding = null;
		Integer connectTimeout = null;
		Integer soTimeout = null;
		
		PostMethod method = null; 
		if(null == contentType ) {
			contentType  = "text/xml";
		} 
		if(null == encoding ) {
			encoding = "UTF-8";
			
		} 
		if(null == connectTimeout ) {
			connectTimeout =  5 * 60 * 1000; 
			 
		} 
		if(null == soTimeout ) {
			soTimeout = 900 * 1000;
		}
	
 
		String responseXml = null; 
		try {

			HttpClient client; 
			{
				HttpConnectionManagerParams params = new HttpConnectionManagerParams();

				params.setConnectionTimeout(connectTimeout);
				params.setSoTimeout(soTimeout);
				params.setTcpNoDelay(true);
			
				HttpConnectionManager conn = new SimpleHttpConnectionManager();
				
				conn.setParams(params);
				client = new HttpClient(conn);
				
				method = new PostMethod(serviceUrl);
				method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
						new DefaultHttpMethodRetryHandler(0, false));

				method.setRequestHeader("Content-Type", contentType);
				method.setRequestHeader("Connection", "close");

				RequestEntity requestEntity = new StringRequestEntity(
						requestXml, contentType, encoding);
				method.setRequestEntity(requestEntity);
			}

			int responseCode = client.executeMethod(method); 
			

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
			
				
				if (responseXml.indexOf("Fault>") > 0) {
					LOG.error("Soap호출 오류발생 : 잘못된 SOAP 양식");
				}
			}
			else if(responseCode == HttpStatus.SC_BAD_GATEWAY) {
			
				LOG.error("Soap URL 호출 오류발생 SC_BAD_GATEWAY ");
				responseXml = null;				
				
			}
			else if(responseCode == HttpStatus.SC_FORBIDDEN) {
				LOG.error("Soap URL 호출 오류발생 SC_FORBIDDEN ");
				responseXml = null;
			}
		
		} catch (Throwable e) {
			e.printStackTrace();
			LOG.error("통신에러 : " +  e.getMessage());
			
		} finally {
			if (method != null) {
				method.releaseConnection(); 
			}
		}

		return responseXml;
	}
}
 