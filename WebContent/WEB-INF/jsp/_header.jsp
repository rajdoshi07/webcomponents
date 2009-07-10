<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root version="2.0"
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:security="http://www.springframework.org/security/tags">
	<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" />
	<div id="super-header">
	<security:authorize ifAllGranted="IS_AUTHENTICATED_REMEMBERED">
		Benvenuto <security:authentication property="principal.username"/> |
		<c:url var="logout" value=''/>
		<a href="${logout}">Logout</a>
	</security:authorize>
	</div>
	<div id="header">
	</div>
</jsp:root>