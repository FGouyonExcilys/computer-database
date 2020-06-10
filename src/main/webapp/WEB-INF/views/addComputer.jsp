<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>  

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="label.title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<spring:url value="resources/css/bootstrap.min.css" var="bootstrapCSS" />
<spring:url value="resources/css/font-awesome.css" var="fontAwesomeCSS" />
<spring:url value="resources/css/main.css" var="mainCSS" />
<spring:url value="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.3.0/css/flag-icon.min.css" var="flagCSS" />
<link href="${bootstrapCSS}" rel="stylesheet" media="screen">
<link href="${fontAwesomeCSS}" rel="stylesheet" media="screen">
<link href="${mainCSS}" rel="stylesheet" media="screen">
<link href="${flagCSS}" rel="stylesheet" media="screen">
</head>
<body onload="initForm()">
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container" style="white-space:nowrap">
			<a class="dropdown-item" href="?lang=<spring:message code="label.urlswitch" />">
			<span class="flag-icon flag-icon-<spring:message code="label.flag" /> navbar-brand" style="width:1%; "></span></a>
			<a class="navbar-brand" href="dashboard">&nbsp;&nbsp;&nbsp;Application - 
				<spring:message code="label.title"/> </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
				
					<h1><spring:message code="label.addComputer"/></h1>
					
					<c:choose>
						<c:when test="${ error == 'incorrectDate' }">
							<div class="alert alert-warning" role="alert">
								<b><i class="fa fa-exclamation-triangle fa-lg"></i>&nbsp;&nbsp;
								<spring:message code="label.incorrectDate"/></b>
							</div>
						</c:when>
						<c:when test="${ error == 'incorrectName' }">
							<div class="alert alert-warning" role="alert">
								<b><i class="fa fa-exclamation-triangle fa-lg"></i>&nbsp;&nbsp;
								<spring:message code="label.incorrectName"/></b>
							</div>
						</c:when>
						<c:when test="${ error == 'cheat' }">
							<div class="alert alert-warning" role="alert">
								<div style="font-size: 30px; display: inline">&#x1F620;</div>
								<b>&nbsp;&nbsp;<spring:message code="label.cheat"/></b>
							</div>
						</c:when>
					</c:choose>
					
					<form action="addComputer" method="POST" id="addComputerForm" onload="">
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message code="label.computerName"/></label>
								<input type="text" class="form-control" id="computerName" name="computerName" 
									   placeholder="<spring:message code="label.computerName"/>"
									   pattern=".{2,255}" required>
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message code="label.dateIntro"/></label> <input
									type="date" class="form-control" id="introduced"
									name="introduced" 
									placeholder="<spring:message code="label.dateIntro"/>">
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message code="label.dateDisc"/></label> <input
									type="date" class="form-control" id="discontinued" name="discontinued" 
									placeholder="<spring:message code="label.dateDisc"/>">
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message code="label.company"/></label> <select
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
							<input type="submit" value="<spring:message code="label.add"/>" class="btn btn-primary"> 
							<spring:message code="label.or"/> <a href="dashboard" class="btn btn-default">
							<spring:message code="label.cancel"/></a>
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
