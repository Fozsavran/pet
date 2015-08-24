<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<title>Pets</title>
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/screen.css" />" type="text/css" media="screen, projection">
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/print.css" />" type="text/css" media="print">
		<!--[if lt IE 8]>
			<link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection">
		<![endif]-->
		<script type="text/javascript" src="<c:url value="/resources/jquery-1.4.min.js" /> "></script>
		<script type="text/javascript" src="<c:url value="/resources/json.min.js" /> "></script>
	</head>
	<body>
		<div class="container">
			<h2>
				Create Pet
			</h2>
			<div class="span-12 last">
				<p>
					Enter a 15 or 18 character Person ID to create an pet.
				</p>
				<form:form modelAttribute="pet" action="pet" method="post">
				  	<fieldset>		
						<legend>Pet Fields</legend>
						<p>
							<form:label	id="idLabel" for="id" path="id" cssErrorClass="error">Id</form:label><br/>
							<form:input path="id" /><form:errors path="id" />
						</p>
						<p>	
							<input id="create" type="submit" value="Create" />
						</p>
					</fieldset>
				</form:form>
			</div>
			<hr>
			<h2>
				Existing Pets
			</h2>
			<table>
				<tr><th>Person ID</th><th>Pet Number</th></tr>
			    <c:forEach items="${pets}" var="pet">
			    	<tr><td><a href="petui/${pet.petId}"><c:out value="${pet.id}"/></a></td><td><a href="petui/${pet.petId}"><c:out value="${pet.petId}"/></a></td></tr>
			    </c:forEach>			
			</table>
		</div>
	</body>

	<script type="text/javascript">	
		$(document).ready(function() {
			$("#pet").submit(function() {
				var pet = $(this).serializeObject();
				$.postJSON("pet", [pet], function(data) {
					$.getJSON("pet/" + data[0].pet_number, function(pet) {
						alert("Created pet "+data[0].pet_number+
								"\nID = "+pet.id);
						window.location.reload(true);
					});			
				}, function(data) {
					var response = JSON.parse(data.response);
					alert("Error: "+response[0].id);
				});
				return false;				
			});
		});
	</script>
	
</html>
