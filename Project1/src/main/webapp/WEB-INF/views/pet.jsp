<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<title>Pet</title>
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/screen.css" />" type="text/css" media="screen, projection">
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/print.css" />" type="text/css" media="print">
		<!--[if lt IE 8]>
			<link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection">
		<![endif]-->
		<link rel="stylesheet" href="<c:url value="/resources/popup.css" />" type="text/css" media="screen, projection">
		<script type="text/javascript" src="<c:url value="/resources/jquery-1.4.min.js" /> "></script>
		<script type="text/javascript" src="<c:url value="/resources/json.min.js" /> "></script>
	</head>
	<body>
		<div class="container">
			<h2>
				Pet <c:out value="${pet.petId}"/>
			</h2>
			<div class="span-12 last">
			    <a href="javascript:history.back()">Back</a> <input id="delete" type="submit" value="Delete" />
			</div>
		</div>	
	</body>
	<script>
	$("#delete").click(function() {
		$.deleteJSON("/pet/${pet.petId}", function(data) {
			alert("Deleted pet ${pet.petId}");
			location.href = "/petui";
		}, function(data) {
			alert("Error deleting pet ${pet.petId}");
		});
		return false;				
	});
	</script>
</html>