<html><body>

<%@ include file="./WEB-INF/jsp/header.jsp"%>
<center>
<form method="POST"

	action="ManageMyPicks.do">


<!--<input type="text" name="uid" size="25" value="<%= (request.getUserPrincipal().getName()) %>">-->
<input name="uid" type=hidden value="<%= (request.getUserPrincipal().getName()) %>">
<br><br>



<input type="SUBMIT" value="Get my picks">



</center>

</form>
</body>
</html>
