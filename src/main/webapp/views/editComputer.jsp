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
					</c:choose>

					<form action="editComputer" method="POST">
						<input type="hidden" value="0" id="id" />
						<!-- TODO: Change this value with the computer id -->
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									name="computerName" value="${ name }">
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
									<option value="none" selected>-- Remove Company assignment --</option>
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