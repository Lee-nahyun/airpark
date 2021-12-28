package kr.airport.parking.reduction.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kr.airport.parking.util.gpki.NewGpkiUtil;


@Component
public class GpkiFactory {
	private static final Logger LOG = LoggerFactory.getLogger(GpkiFactory.class);
		
	private NewGpkiUtil newGpkiUtil;
	
	@Autowired
	public GpkiFactory(
			@Value("#{appConfig['GPKI.myServerId']}") String myServerId,
			@Value("#{appConfig['GPKI.certFilePath']}") String certFilePath,
			@Value("#{appConfig['GPKI.envCertFilePathName']}")	String envCertFilePathName,
			@Value("#{appConfig['GPKI.envPrivateKeyFilePathName']}")  String envPrivateKeyFilePathName,
			@Value("#{appConfig['GPKI.envPrivateKeyPasswd']}")	 String envPrivateKeyPasswd,
			@Value("#{appConfig['GPKI.sigCertFilePathName']}")	String sigCertFilePathName,
			@Value("#{appConfig['GPKI.sigPrivateKeyFilePathName']}") String sigPrivateKeyFilePathName,
			@Value("#{appConfig['GPKI.sigPrivateKeyPasswd']}")	String sigPrivateKeyPasswd,
			@Value("#{appConfig['GPKI.gpkiLicPath']}")	String gpkiLicPath,
			@Value("#{appConfig['GPKI.targetServerIdList']}") String targetServerIdList) {	
		try {
			
			
			newGpkiUtil = new NewGpkiUtil();
			// 이용기관 서버CN
		
			// 이용기관 서버인증서 경로
			newGpkiUtil.setCertFilePath(certFilePath);
			
			// 이용기관 GPKI API 라이선스파일 경로
			newGpkiUtil.setGpkiLicPath(gpkiLicPath);
			newGpkiUtil.setEnvCertFilePathName(envCertFilePathName);		
			newGpkiUtil.setEnvPrivateKeyFilePathName(envPrivateKeyFilePathName);
			newGpkiUtil.setEnvPrivateKeyPasswd(envPrivateKeyPasswd);
			// LDAP 의 사용유무
			// 미사용일 경우 암호화할 타겟의 인증서를 파일로 저장해놓고 사용하여야함.
			newGpkiUtil.setIsLDAP(true);
			newGpkiUtil.setMyServerId(myServerId);
			newGpkiUtil.setSigCertFilePathName(sigCertFilePathName);
			newGpkiUtil.setSigPrivateKeyFilePathName(sigPrivateKeyFilePathName);
			newGpkiUtil.setSigPrivateKeyPasswd(sigPrivateKeyPasswd);
			newGpkiUtil.setTargetServerIdList(targetServerIdList);
			
			LOG.info("======GpkiFactory Settting=================================");
			LOG.info("GpkiFactory certFilePath " + certFilePath);
			LOG.info("GpkiFactory gpkiLicPath " + gpkiLicPath);
			LOG.info("GpkiFactory envCertFilePathName " + envCertFilePathName);
			LOG.info("GpkiFactory envPrivateKeyFilePathName " + envPrivateKeyFilePathName);
			LOG.info("GpkiFactory envPrivateKeyPasswd " + envPrivateKeyPasswd);
			LOG.info("GpkiFactory myServerId " + myServerId);
			LOG.info("GpkiFactory sigCertFilePathName " + sigCertFilePathName);
			LOG.info("GpkiFactory sigPrivateKeyFilePathName " + sigPrivateKeyFilePathName);
			LOG.info("GpkiFactory sigPrivateKeyPasswd " + sigPrivateKeyPasswd);
			LOG.info("GpkiFactory targetServerIdList " + targetServerIdList);
			LOG.info("======================================================");
			
//			newGpkiUtil.init();
			
			LOG.debug("GPKI Module initialize End");
		} catch (NoClassDefFoundError noClassEx) {
			LOG.error("GPKI API Loding Error");
		} catch (Exception e) {
			LOG.error("GPKI Module init Error");
			LOG.error(e.getMessage());
		}
		
		
	}

	
	public NewGpkiUtil getNewGpkiUtil(){
		
		return newGpkiUtil;
		
	}
	
	//수신데이터 GPKI  복호화 
		/*
		 * 
		 */
	public String decrypt(String receiveString) {
		String decrypted = null;
		byte[] decoded;
		try {
			decoded = newGpkiUtil.decode(receiveString);
			byte[] validated = newGpkiUtil.validate(decoded);
			decrypted = new String(newGpkiUtil.decrypt(validated), "UTF-8");
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
	public String encrypt(String sendString,String targetServerId)
	{
		byte[] encrypted = null;
		String encoded = null;
		try {
		
			encrypted = newGpkiUtil.encrypt(sendString.getBytes("UTF-8"), targetServerId);
			byte[] signed = newGpkiUtil.sign(encrypted);
			encoded = newGpkiUtil.encode(signed);
			return encoded;
		}
		catch (Exception e) {
			e.printStackTrace();
				
		}
		return encoded;
	}



	
}
