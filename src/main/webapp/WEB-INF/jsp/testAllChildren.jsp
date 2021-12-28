<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<head> 
<title>행안부 행정망 수동 조회페이지</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="/resources/js/jquery-1.11.3.min.js"></script>

<script>
$(function() {
	
	$("#btn").click(function(){
		
		var airportCode = $('#airportCode').val();
		var name = $('#name').val();
		var id = $('#id').val();
		
		$.ajax({
// 			url: "/getAllChldrnCoBirthInfo?airportCode="+ airportCode + "&name=" + encodeURIComponent(name) + "&id=" + id,
			url: "/getAllChldrnCoBirthInfo?airportCode="+ airportCode + "&name=" + encodeURI(encodeURIComponent(name)) + "&id=" + id,
// 			url: "/getAllChldrnCoBirthInfo",
			method:"POST",
			dataType:"text",
// 			data:{airportCode : airportCode, name: name, id:id},
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
  <h2>다자녀 차량 조회</h2>
  	공항코드 : <input type="text" id="airportCode" value="1">
 	이름 : <input type="text" id="name" value="소건">
 	주민번호 : <input type="text" id="id" value="8201221914410">
 
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



