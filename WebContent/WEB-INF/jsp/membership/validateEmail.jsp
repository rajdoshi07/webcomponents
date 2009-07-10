<?xml version="1.0" encoding="ISO-8859-1" ?>
<jsp:root version="2.0"
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core">
<jsp:directive.page errorPage="true"/>
<c:choose>
	<c:when test="${empty exception}">
		<c:redirect url="/message.html">
			<c:param name="msg" value="activation.success"/>
		</c:redirect>
	</c:when>
	<c:otherwise>
		<c:redirect url="/message.html">
			<c:param name="msg" value="${exception.class.simpleName}"/>
		</c:redirect>
	</c:otherwise>
</c:choose>
</jsp:root>