<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<head> 
<title>행안부 행정망 수동 조회페이지</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="http://localhost:9081/js/lib/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="http://localhost:9081/js/cryptoa.js"></script>
<script>
$(function() {
	
	$("#btn").click(function(){
		
		if($("#selUrl option:selected").val()=="getChldrnYn") 
		{
			$.ajax({
				url: "/rightShare/" + $("#selUrl option:selected").val(),
				method:"POST",
				dataType:"text",
				data:{userName:$("#name").val(),registrationNumber:CryptoJS.AES.encrypt($("#registrationNumber").val(),"f442381a0a9b3e9d01ed937421a43de2")},
				success: function(data) {
					$("#resultArea").text(data);
				}
			});
		} else {
			$.ajax({
				url: "/rightShare/" + $("#selUrl option:selected").val(),
				method:"POST",
				dataType:"text",
				data:{carNo:$("#carNo").val()},
				success: function(data) {
					$("#resultArea").text(data);
				}
			});
		}
		
	    
	})
});

</script>
</head> 
 <body> 
 
<div name="demo">
  <h2>조회</h2>
  	조회할 서비스 : <select id="selUrl">
  	<option value="getRightShareYn">수동데이터</option>
  				<option value="getReductionTsCarInfo">경차</option>
  				</select>
  	차량번호 : <input type="text" id="carNo" value=""> 
	<!--이름 :    <input type="text" id="name" value=""> 
	주민번호 : <input type="text" id="registrationNumber" value=""> -->
 
  <input type="button" id="btn" value="조회">
</div>
<div>
<textarea id="resultArea" rows="30" cols="70"></textarea>
</div>
</body>



