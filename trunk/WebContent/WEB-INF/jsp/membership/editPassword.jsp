<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root version="2.0"
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:spring="http://www.springframework.org/tags">
    <jsp:directive.page language="java"
        contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" />
    <jsp:text><![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">]]></jsp:text>
<html xmlns="http://www.w3.org/1999/xhtml" lang="${requestContext.locale.language}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Content-Language" content="${requestContext.locale.language}"/>
<title>Title</title>
<link rel="stylesheet" type="text/css" href="/css/style.css"/>
</head>
<body>
<div id="container">
	<c:import url="../_header.jsp" />
	<div id="content">
		<h1><spring:message code="editPassword.label"/></h1>
		<form:form id="editPassword">
			<div class="textField">
				<form:label path="password"><spring:message code="label.passwordCheck"/></form:label>
				<form:password path="passwordCheck" maxlength="25"/>
				<form:errors path="passwordCheck" cssClass="error"/>
			</div>
			<div class="textField">
				<form:label path="password"><spring:message code="label.passwordNew"/></form:label>
				<form:password path="password" maxlength="25" />
				<form:errors path="password" cssClass="error"/>
			</div>
			<div class="textField">
				<form:label path="passwordConfirm"><spring:message code="label.passwordConfirm"/></form:label>
				<form:password path="passwordConfirm" maxlength="25" />
				<form:errors path="passwordConfirm" cssClass="error"/>
			</div>
			<ul class="collection buttons">
				<li><button type="submit"><spring:message code="label.submit"/></button></li>
			</ul>
		</form:form>
	</div>
	<c:import url="../_footer.jsp" />
</div>
</body>
</html>
</jsp:root>