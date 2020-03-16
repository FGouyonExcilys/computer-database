<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body onload="initForm()">
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
				
					<h1>Add Computer</h1>
					
					<c:choose>
						<c:when test="${ error == 'date' }">
							<div class="alert alert-warning" role="alert">
								<b><i class="fa fa-exclamation-triangle fa-lg"></i>&nbsp;&nbsp;Please
									make sure that the introduced date is before the discontinued
									date</b>
							</div>
						</c:when>
						<c:when test="${ cheatingError == 'cheat' }">
							<div class="alert alert-warning" role="alert">
								<div style="font-size: 30px; display: inline">&#x1F620;</div>
								<b>&nbsp;&nbsp;You've tried cheating on the database, don't you? Too bad! Try again.</b>
							</div>
						</c:when>
					</c:choose>
					
					<form action="addComputer" method="POST" id="addComputerForm" onload="">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									name="computerName" placeholder="Computer name"
									pattern=".{2,255}" required>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" id="introduced"
									name="introduced" placeholder="Introduced date">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued"
									name="discontinued" placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach items="${ listeCompany }" var="company">
										<option value="${ company.getId() }"><c:out
												value="${ company.getName() }" /></option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
					<script>$("#addComputerForm").validate();</script>
				</div>
			</div>
		</div>
		
	</section>
</body>
<script>
function initForm(){
	document.getElementById("addComputerForm").reset();
}</script>
</html>