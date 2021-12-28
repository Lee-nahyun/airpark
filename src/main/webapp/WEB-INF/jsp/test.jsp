<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<head> 
<title>행안부 행정망 수동 조회페이지</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>

<script>
$(function() {
	
	$("#btn").click(function(){
		
		$.ajax({
			url: "/" + $("#selUrl option:selected").val(),
			method:"POST",
			dataType:"text",
			data:{airportCode : '1', iId: '11604696', carNo:$("#carNo").val()},
			success: function(responseData) {
				var data = JSON.parse(responseData);
				console.log(data);
				$("#requestXmlArea").text(data.reqSoapXml);
				$("#responseXmlArea").text(data.resSoapXml);
			}
		});
		
	})
	
});

</script>
</head> 
 <body> 
 
<div name="demo">
  <h2>조회</h2>
  	조회할 서비스 : 
  		<select id="selUrl">
  			<option value="getReductionTsCarInfo">경차 감면 조회</option>
  			<option value="getLowPollutionCarYn">저공해차량 감면 조회</option>
  			<option value="getMeritCarYn">국가유공자 감면 조회</option>
  			<option value="getReductionDisabledCarYn">장애인 감면 조회</option>
  			<option value="getNewLowPollutionCarYn">(신규)저공해차량 감면 조회</option>
  			<option value="getReductionTsCarInfoNew">(신규)경차 감면 조회</option>  			
  			<option value="getReductionCar">전체조회</option>
  		</select>
  	차량번호 : <input type="text" id="carNo" value=""> 
 
  <input type="button" id="btn" value="조회">
</div>


<table>
	<tr>
		<td>
			<div>
				<h2>요청XML</h2>
				<textarea id="requestXmlArea" rows="30" cols="70"></textarea>
			</div>
		</td>
		<td>
			<div>
				<h2>응답XML</h2>
				<textarea id="responseXmlArea" rows="30" cols="70"></textarea>
			</div>	
		</td>
	</tr>
</table>



</body>



