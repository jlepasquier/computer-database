<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.12/css/all.css">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
		
			<div class="navbar-header">
				<a class="navbar-brand" href="dashboard"> Application -	Computer Database</a>
			</div>
			
			<div class="navbar-header pull-right text-center">
				<p class="navbar-brand my-0" style="margin-bottom:0;">Logged in as : <b><c:out value="${pageContext.request.remoteUser}"/></b></p>
				    <form action="logout" method="post">
				      <input type="submit" value="Logout" />
				      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				    </form>
			</div>
		</div>
	</nav>