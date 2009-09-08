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
<style type="text/css">
#subscription {
	width: 360px;
}
#subscription textarea {
	width: 100%;
}
</style>
</head>
<body>
<div id="container">
	<c:import url="../_header.jsp" />
	<div id="content">
		<h1><spring:message code="label.subscription"/></h1>
		<form:form commandName="subscription">
			<div class="textField horz">
				<form:label path="screenName"><spring:message code="label.screenName"/></form:label>
				<form:input path="screenName" maxlength="15"/>
				<form:errors path="screenName" cssClass="error"/>
			</div>
			<div class="textField horz">
				<form:label path="password"><spring:message code="label.password"/></form:label>
				<form:password path="password" maxlength="25"/>
				<form:errors path="password" cssClass="error"/>
			</div>
			<div class="textField horz">
				<form:label path="passwordConfirm"><spring:message code="label.passwordConfirm"/></form:label>
				<form:password path="passwordConfirm" maxlength="25"/>
				<form:errors path="passwordConfirm" cssClass="error"/>
			</div>
			<div class="textField horz">
				<form:label path="email"><spring:message code="label.email"/></form:label>
				<form:input path="email" maxlength="60"/>
				<form:errors path="email" cssClass="error"/>
			</div>
<jsp:scriptlet>
java.util.UUID uuid = java.util.UUID.randomUUID();
String contextPath = application.getContextPath();
if(contextPath == null || "/".equals(contextPath)) {
	contextPath = "";
}
pageContext.setAttribute("captchaSrc", contextPath + "/captcha/" + uuid.toString() + ".jpg");
</jsp:scriptlet>
			<div id="humanTestField" class="humanTest">		
				<form:label path="humanTest"><spring:message code="label.captcha"/></form:label>
				<div><img src="${captchaSrc}" width="182" height="100" alt="captcha"/></div>
				<input id="humanTest" name="humanTest" maxlength="64" />
				<form:errors path="humanTest" cssClass="error"/>
			</div>
			<fieldset id="privacyPolicyAcceptedField">
				<legend><spring:message code="label.privacy"/></legend> 
				<textarea id="privacy" readonly="readonly" rows="5" cols="49"><spring:message code="text.privacy"/></textarea>
				<form:label path="privacyPolicyAccepted"><form:radiobutton path="privacyPolicyAccepted" value="true"/> <spring:message code="label.agree"/></form:label>
				<form:label path="privacyPolicyAccepted"><form:radiobutton path="privacyPolicyAccepted" value="false"/> <spring:message code="label.disagree"/></form:label>
				<form:errors path="privacyPolicyAccepted" cssClass="error"/>
			</fieldset>
			<fieldset id="termsOfUseAcceptedField">
				<legend><spring:message code="label.use"/></legend>
				<textarea id="use" readonly="readonly" rows="5" cols="49"><spring:message code="text.use"/></textarea>
				<form:label path="termsOfUseAccepted"><form:radiobutton path="termsOfUseAccepted" value="true"/> <spring:message code="label.agree"/></form:label>
				<form:label path="termsOfUseAccepted"><form:radiobutton path="termsOfUseAccepted" value="false"/> <spring:message code="label.disagree"/></form:label>
				<form:errors path="termsOfUseAccepted" cssClass="error"/>
			</fieldset>
			<ul class="collection buttons">
				<li><button type="submit"><spring:message code="label.submit"/></button></li>
			</ul>
			<span class="important">* <spring:message code="label.required"/></span>
		</form:form>
	</div>
	<c:import url="../_footer.jsp" />
</div>
</body>
</html>
</jsp:root>