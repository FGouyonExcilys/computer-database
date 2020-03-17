<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>  

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<spring:url value="resources/css/bootstrap.min.css" var="bootstrapCSS" />
<spring:url value="resources/css/font-awesome.css" var="fontAwesomeCSS" />
<spring:url value="resources/css/main.css" var="mainCSS" />
<link href="${bootstrapCSS}" rel="stylesheet" media="screen">
<link href="${fontAwesomeCSS}" rel="stylesheet" media="screen">
<link href="${mainCSS}" rel="stylesheet" media="screen">
</head>
<body>
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
					<div class="label label-default pull-right">id: ${ id }</div>
					<h1>Edit Computer</h1>

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

					<form action="editComputer" method="POST">
						<input type="hidden" value="0" id="id" />
						<!-- TODO: Change this value with the computer id -->
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									name="computerName" value="${ name }" required>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" id="introduced"
									name="introduced" value="${ introduced }">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued"
									name="discontinued" value="${ discontinued }">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<option value="0" selected>-- Remove Company assignment --</option>
									<c:forEach items="${ listeCompany }" var="company">
										<c:choose>
											<c:when test="${ currentCompany == company.getId() }">
												<option value="${ company.getId() }" selected><c:out
														value="${ company.getName() }" /></option>
											</c:when>
											<c:otherwise>
												<option value="${ company.getId() }"><c:out
														value="${ company.getName() }" /></option>
											</c:otherwise>
										</c:choose>

									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>