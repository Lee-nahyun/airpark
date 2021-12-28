package kr.airport.parking.reduction.vo.table;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("TckttrnsVO")
public class TckttrnsVO {
	
	private String rnum;
	
	private String airportCode;
	private String airportName;
	private Integer iId;
	private Integer iLotArea;
	private String acLotAreaName1;
	private Integer iInClient;
	private Integer iInEqpm;
	private String acEqpmName;
	private String iPaymentType;
	private Integer iTicket;
	private Date dtInDate;
	private String dtInDate1;
	private String dtInDate2;
	private Date dtOutDate;
	private String dtOutDate1;
	private String dtOutDate2;
	private Date dtPayDate;
	private String dtPayDate1;
	private String dtPayDate2;
	private Integer dfee;
	private Integer dPaid;
	private Integer dChange;
	private Integer dInCome;
	private Integer iServiceA;
	private Integer dServiceA;
	private Integer iServiceB;
	private Integer dServiceB;
	private Integer iServiceC;
	private Integer dServiceC;
	private String iAccountFlag;
	private String iAccountFlagName;
	private String iVoidUseFlag;
	private Integer iOutClient;
	private Integer iOutEqpm;
	private String acOutEqpmName;
	private Integer iPayClient;
	private Integer iPayEqpm;
	private String acPayEqpmName;
	private String iCardType;
	private String iCardTypeNot;
	private String iInOutStatus;
	private Integer iRate;
	private Integer dShortAmount;
	private Integer iOtpUsedAmount;
	private Integer iCardRate;
	private Integer dTransNo;
	private Integer dDebitAmount;
	private Integer iShopNo1;
	private Integer dShopAmount1;
	private Integer iShopNo2;
	private Integer dShopAmount2;
	private Integer iShopNo3;
	private Integer dShopAmount3;
	private Integer sTax;
	private Integer dMisc1;
	private Integer dMisc2;
	private Integer dMisc3;
	private String iCredit;
	private String acCarStayHours;
	private String iOperator;
	private String dInsffcntPayOut;
	private String iPaymentMode;
	private Integer dPasscardNo;
	private Integer dDebitCardNo;
	private Integer dEventCardNo;
	private Integer dOptCardNo;
	private String dtPaymentDate;
	private String iInEqpmType;
	private String iOutEqpmType;
	private String acInTime;
	private String acOutTime;
	private String acPayTime;
	private String acUserName;
	private String acPlate1;
	private String acPlate2;
	private String acTelNo;
	private Integer iGroup;
	private Date dtMgmntDate;
	private Integer iExtendLotArea;
	private Integer dParkingAmount;
	private String acMemo;
	private String acRateKeyName;
	private String acEntrancePicName;
	private String acGoOutPicName;
	private String iSrvrUpdtFlag;
	private Integer idckind;
	private String delYn;
	private Integer dSupplyFee;
	private Integer dVat;
	private Date createTm;
	private String createId;
	private Date updateTm;
	private String updateId;
	private String acOperatorName;
	private String searchType;
	private String searchVal;
	private Integer inCarCnt;
	private Integer outCarCnt;
	
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public Integer getiId() {
		return iId;
	}
	public void setiId(Integer iId) {
		this.iId = iId;
	}
	public Integer getiLotArea() {
		return iLotArea;
	}
	public void setiLotArea(Integer iLotArea) {
		this.iLotArea = iLotArea;
	}
	public Integer getiInClient() {
		return iInClient;
	}
	public void setiInClient(Integer iInClient) {
		this.iInClient = iInClient;
	}
	public Integer getiInEqpm() {
		return iInEqpm;
	}
	public void setiInEqpm(Integer iInEqpm) {
		this.iInEqpm = iInEqpm;
	}
	public String getiPaymentType() {
		return iPaymentType;
	}
	public void setiPaymentType(String iPaymentType) {
		this.iPaymentType = iPaymentType;
	}
	public Integer getiTicket() {
		return iTicket;
	}
	public void setiTicket(Integer iTicket) {
		this.iTicket = iTicket;
	}
	public Date getDtInDate() {
		return dtInDate;
	}
	public void setDtInDate(Date dtInDate) {
		this.dtInDate = dtInDate;
	}
	public Date getDtOutDate() {
		return dtOutDate;
	}
	public void setDtOutDate(Date dtOutDate) {
		this.dtOutDate = dtOutDate;
	}
	public Date getDtPayDate() {
		return dtPayDate;
	}
	public void setDtPayDate(Date dtPayDate) {
		this.dtPayDate = dtPayDate;
	}
	public Integer getDfee() {
		return dfee;
	}
	public String getDtPayDate1() {
		return dtPayDate1;
	}
	public void setDtPayDate1(String dtPayDate1) {
		this.dtPayDate1 = dtPayDate1;
	}
	public String getDtPayDate2() {
		return dtPayDate2;
	}
	public void setDtPayDate2(String dtPayDate2) {
		this.dtPayDate2 = dtPayDate2;
	}
	public void setDfee(Integer dfee) {
		this.dfee = dfee;
	}
	public Integer getdPaid() {
		return dPaid;
	}
	public void setdPaid(Integer dPaid) {
		this.dPaid = dPaid;
	}
	public Integer getdChange() {
		return dChange;
	}
	public void setdChange(Integer dChange) {
		this.dChange = dChange;
	}
	public Integer getdInCome() {
		return dInCome;
	}
	public void setdInCome(Integer dInCome) {
		this.dInCome = dInCome;
	}
	public Integer getiServiceA() {
		return iServiceA;
	}
	public void setiServiceA(Integer iServiceA) {
		this.iServiceA = iServiceA;
	}
	public Integer getdServiceA() {
		return dServiceA;
	}
	public void setdServiceA(Integer dServiceA) {
		this.dServiceA = dServiceA;
	}
	public Integer getiServiceB() {
		return iServiceB;
	}
	public void setiServiceB(Integer iServiceB) {
		this.iServiceB = iServiceB;
	}
	public Integer getdServiceB() {
		return dServiceB;
	}
	public void setdServiceB(Integer dServiceB) {
		this.dServiceB = dServiceB;
	}
	public Integer getiServiceC() {
		return iServiceC;
	}
	public void setiServiceC(Integer iServiceC) {
		this.iServiceC = iServiceC;
	}
	public Integer getdServiceC() {
		return dServiceC;
	}
	public void setdServiceC(Integer dServiceC) {
		this.dServiceC = dServiceC;
	}
	public String getiAccountFlag() {
		return iAccountFlag;
	}
	public void setiAccountFlag(String iAccountFlag) {
		this.iAccountFlag = iAccountFlag;
	}
	public String getiVoidUseFlag() {
		return iVoidUseFlag;
	}
	public String getiAccountFlagName() {
		return iAccountFlagName;
	}
	public void setiAccountFlagName(String iAccountFlagName) {
		this.iAccountFlagName = iAccountFlagName;
	}
	public void setiVoidUseFlag(String iVoidUseFlag) {
		this.iVoidUseFlag = iVoidUseFlag;
	}
	public Integer getiOutClient() {
		return iOutClient;
	}
	public void setiOutClient(Integer iOutClient) {
		this.iOutClient = iOutClient;
	}
	public Integer getiOutEqpm() {
		return iOutEqpm;
	}
	public void setiOutEqpm(Integer iOutEqpm) {
		this.iOutEqpm = iOutEqpm;
	}
	public Integer getiPayClient() {
		return iPayClient;
	}
	public void setiPayClient(Integer iPayClient) {
		this.iPayClient = iPayClient;
	}
	public Integer getiPayEqpm() {
		return iPayEqpm;
	}
	public void setiPayEqpm(Integer iPayEqpm) {
		this.iPayEqpm = iPayEqpm;
	}
	public String getiCardType() {
		return iCardType;
	}
	public void setiCardType(String iCardType) {
		this.iCardType = iCardType;
	}
	public String getiCardTypeNot() {
		return iCardTypeNot;
	}
	public void setiCardTypeNot(String iCardTypeNot) {
		this.iCardTypeNot = iCardTypeNot;
	}
	public String getiInOutStatus() {
		return iInOutStatus;
	}
	public void setiInOutStatus(String iInOutStatus) {
		this.iInOutStatus = iInOutStatus;
	}
	public Integer getiRate() {
		return iRate;
	}
	public void setiRate(Integer iRate) {
		this.iRate = iRate;
	}
	public Integer getdShortAmount() {
		return dShortAmount;
	}
	public void setdShortAmount(Integer dShortAmount) {
		this.dShortAmount = dShortAmount;
	}
	public Integer getiOtpUsedAmount() {
		return iOtpUsedAmount;
	}
	public void setiOtpUsedAmount(Integer iOtpUsedAmount) {
		this.iOtpUsedAmount = iOtpUsedAmount;
	}
	public Integer getiCardRate() {
		return iCardRate;
	}
	public void setiCardRate(Integer iCardRate) {
		this.iCardRate = iCardRate;
	}
	public Integer getdTransNo() {
		return dTransNo;
	}
	public void setdTransNo(Integer dTransNo) {
		this.dTransNo = dTransNo;
	}
	public Integer getdDebitAmount() {
		return dDebitAmount;
	}
	public void setdDebitAmount(Integer dDebitAmount) {
		this.dDebitAmount = dDebitAmount;
	}
	public Integer getiShopNo1() {
		return iShopNo1;
	}
	public void setiShopNo1(Integer iShopNo1) {
		this.iShopNo1 = iShopNo1;
	}
	public Integer getdShopAmount1() {
		return dShopAmount1;
	}
	public void setdShopAmount1(Integer dShopAmount1) {
		this.dShopAmount1 = dShopAmount1;
	}
	public Integer getiShopNo2() {
		return iShopNo2;
	}
	public void setiShopNo2(Integer iShopNo2) {
		this.iShopNo2 = iShopNo2;
	}
	public Integer getdShopAmount2() {
		return dShopAmount2;
	}
	public void setdShopAmount2(Integer dShopAmount2) {
		this.dShopAmount2 = dShopAmount2;
	}
	public Integer getiShopNo3() {
		return iShopNo3;
	}
	public void setiShopNo3(Integer iShopNo3) {
		this.iShopNo3 = iShopNo3;
	}
	public Integer getdShopAmount3() {
		return dShopAmount3;
	}
	public void setdShopAmount3(Integer dShopAmount3) {
		this.dShopAmount3 = dShopAmount3;
	}
	public Integer getsTax() {
		return sTax;
	}
	public void setsTax(Integer sTax) {
		this.sTax = sTax;
	}
	public Integer getdMisc1() {
		return dMisc1;
	}
	public void setdMisc1(Integer dMisc1) {
		this.dMisc1 = dMisc1;
	}
	public Integer getdMisc2() {
		return dMisc2;
	}
	public void setdMisc2(Integer dMisc2) {
		this.dMisc2 = dMisc2;
	}
	public Integer getdMisc3() {
		return dMisc3;
	}
	public void setdMisc3(Integer dMisc3) {
		this.dMisc3 = dMisc3;
	}
	public String getiCredit() {
		return iCredit;
	}
	public void setiCredit(String iCredit) {
		this.iCredit = iCredit;
	}
	public String getAcCarStayHours() {
		return acCarStayHours;
	}
	public void setAcCarStayHours(String acCarStayHours) {
		this.acCarStayHours = acCarStayHours;
	}
	public String getiOperator() {
		return iOperator;
	}
	public void setiOperator(String iOperator) {
		this.iOperator = iOperator;
	}
	public String getdInsffcntPayOut() {
		return dInsffcntPayOut;
	}
	public void setdInsffcntPayOut(String dInsffcntPayOut) {
		this.dInsffcntPayOut = dInsffcntPayOut;
	}
	public String getiPaymentMode() {
		return iPaymentMode;
	}
	public void setiPaymentMode(String iPaymentMode) {
		this.iPaymentMode = iPaymentMode;
	}
	public Integer getdPasscardNo() {
		return dPasscardNo;
	}
	public void setdPasscardNo(Integer dPasscardNo) {
		this.dPasscardNo = dPasscardNo;
	}
	public Integer getdDebitCardNo() {
		return dDebitCardNo;
	}
	public void setdDebitCardNo(Integer dDebitCardNo) {
		this.dDebitCardNo = dDebitCardNo;
	}
	public Integer getdEventCardNo() {
		return dEventCardNo;
	}
	public void setdEventCardNo(Integer dEventCardNo) {
		this.dEventCardNo = dEventCardNo;
	}
	public Integer getdOptCardNo() {
		return dOptCardNo;
	}
	public void setdOptCardNo(Integer dOptCardNo) {
		this.dOptCardNo = dOptCardNo;
	}
	public String getDtPaymentDate() {
		return dtPaymentDate;
	}
	public void setDtPaymentDate(String dtPaymentDate) {
		this.dtPaymentDate = dtPaymentDate;
	}
	public String getiInEqpmType() {
		return iInEqpmType;
	}
	public void setiInEqpmType(String iInEqpmType) {
		this.iInEqpmType = iInEqpmType;
	}
	public String getiOutEqpmType() {
		return iOutEqpmType;
	}
	public void setiOutEqpmType(String iOutEqpmType) {
		this.iOutEqpmType = iOutEqpmType;
	}
	public String getAcInTime() {
		return acInTime;
	}
	public void setAcInTime(String acInTime) {
		this.acInTime = acInTime;
	}
	public String getAcOutTime() {
		return acOutTime;
	}
	public void setAcOutTime(String acOutTime) {
		this.acOutTime = acOutTime;
	}
	public String getAcPayTime() {
		return acPayTime;
	}
	public void setAcPayTime(String acPayTime) {
		this.acPayTime = acPayTime;
	}
	public String getAcUserName() {
		return acUserName;
	}
	public void setAcUserName(String acUserName) {
		this.acUserName = acUserName;
	}
	public String getAcPlate1() {
		return acPlate1;
	}
	public void setAcPlate1(String acPlate1) {
		this.acPlate1 = acPlate1;
	}
	public String getAcPlate2() {
		return acPlate2;
	}
	public void setAcPlate2(String acPlate2) {
		this.acPlate2 = acPlate2;
	}
	public String getAcTelNo() {
		return acTelNo;
	}
	public void setAcTelNo(String acTelNo) {
		this.acTelNo = acTelNo;
	}
	public Integer getiGroup() {
		return iGroup;
	}
	public void setiGroup(Integer iGroup) {
		this.iGroup = iGroup;
	}
	public Date getDtMgmntDate() {
		return dtMgmntDate;
	}
	public void setDtMgmntDate(Date dtMgmntDate) {
		this.dtMgmntDate = dtMgmntDate;
	}
	public Integer getiExtendLotArea() {
		return iExtendLotArea;
	}
	public void setiExtendLotArea(Integer iExtendLotArea) {
		this.iExtendLotArea = iExtendLotArea;
	}
	public Integer getdParkingAmount() {
		return dParkingAmount;
	}
	public void setdParkingAmount(Integer dParkingAmount) {
		this.dParkingAmount = dParkingAmount;
	}
	public String getAcMemo() {
		return acMemo;
	}
	public void setAcMemo(String acMemo) {
		this.acMemo = acMemo;
	}
	public String getAcRateKeyName() {
		return acRateKeyName;
	}
	public void setAcRateKeyName(String acRateKeyName) {
		this.acRateKeyName = acRateKeyName;
	}
	public String getAcEntrancePicName() {
		return acEntrancePicName;
	}
	public void setAcEntrancePicName(String acEntrancePicName) {
		this.acEntrancePicName = acEntrancePicName;
	}
	public String getAcGoOutPicName() {
		return acGoOutPicName;
	}
	public void setAcGoOutPicName(String acGoOutPicName) {
		this.acGoOutPicName = acGoOutPicName;
	}
	public String getiSrvrUpdtFlag() {
		return iSrvrUpdtFlag;
	}
	public void setiSrvrUpdtFlag(String iSrvrUpdtFlag) {
		this.iSrvrUpdtFlag = iSrvrUpdtFlag;
	}
	public Integer getIdckind() {
		return idckind;
	}
	public void setIdckind(Integer idckind) {
		this.idckind = idckind;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public Integer getdSupplyFee() {
		return dSupplyFee;
	}
	public void setdSupplyFee(Integer dSupplyFee) {
		this.dSupplyFee = dSupplyFee;
	}
	public Integer getdVat() {
		return dVat;
	}
	public void setdVat(Integer dVat) {
		this.dVat = dVat;
	}
	public Date getCreateTm() {
		return createTm;
	}
	public void setCreateTm(Date createTm) {
		this.createTm = createTm;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public Date getUpdateTm() {
		return updateTm;
	}
	public void setUpdateTm(Date updateTm) {
		this.updateTm = updateTm;
	}
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	public String getAirportName() {
		return airportName;
	}
	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}
	public String getAcEqpmName() {
		return acEqpmName;
	}
	public void setAcEqpmName(String acEqpmName) {
		this.acEqpmName = acEqpmName;
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getAcLotAreaName1() {
		return acLotAreaName1;
	}
	public void setAcLotAreaName1(String acLotAreaName1) {
		this.acLotAreaName1 = acLotAreaName1;
	}
	public String getDtOutDate1() {
		return dtOutDate1;
	}
	public void setDtOutDate1(String dtOutDate1) {
		this.dtOutDate1 = dtOutDate1;
	}
	public String getDtOutDate2() {
		return dtOutDate2;
	}
	public void setDtOutDate2(String dtOutDate2) {
		this.dtOutDate2 = dtOutDate2;
	}
	public Integer getInCarCnt() {
		return inCarCnt;
	}
	public void setInCarCnt(Integer inCarCnt) {
		this.inCarCnt = inCarCnt;
	}
	public Integer getOutCarCnt() {
		return outCarCnt;
	}
	public void setOutCarCnt(Integer outCarCnt) {
		this.outCarCnt = outCarCnt;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getAcOperatorName() {
		return acOperatorName;
	}
	public void setAcOperatorName(String acOperatorName) {
		this.acOperatorName = acOperatorName;
	}
	public String getDtInDate1() {
		return dtInDate1;
	}
	public void setDtInDate1(String dtInDate1) {
		this.dtInDate1 = dtInDate1;
	}
	public String getDtInDate2() {
		return dtInDate2;
	}
	public void setDtInDate2(String dtInDate2) {
		this.dtInDate2 = dtInDate2;
	}
	public String getAcOutEqpmName() {
		return acOutEqpmName;
	}
	public void setAcOutEqpmName(String acOutEqpmName) {
		this.acOutEqpmName = acOutEqpmName;
	}
	public String getAcPayEqpmName() {
		return acPayEqpmName;
	}
	public void setAcPayEqpmName(String acPayEqpmName) {
		this.acPayEqpmName = acPayEqpmName;
	}
	
}
