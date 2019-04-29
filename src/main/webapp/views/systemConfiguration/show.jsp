<%--
 * index.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<ul style="list-style-type: disc;">
	<spring:message code="systemConfiguration.nameSystem" var="nameSystem" />
	<li><b>${nameSystem}:</b> <jstl:out value="${systemConfiguration.nameSystem}" /></li>
	
	<jstl:if test="${not empty systemConfiguration.bannerUrl}">
		<spring:message code="systemConfiguration.bannerUrl" var="bannerUrl" />
		<li><b>${bannerUrl}:</b> <jstl:out value="${systemConfiguration.bannerUrl}" /></li>
		<img src="<jstl:out value='${systemConfiguration.bannerUrl}' />" /><br /><br />
	</jstl:if>
	
	<spring:message code="systemConfiguration.welcomeMessageEnglish" var="welcomeMessageEnglish" />
	<li><b>${welcomeMessageEnglish}:</b> <jstl:out value="${systemConfiguration.welcomeMessageEnglish}" /></li>
	
	<spring:message code="systemConfiguration.welcomeMessageSpanish" var="welcomeMessageSpanish" />
	<li><b>${welcomeMessageSpanish}:</b> <jstl:out value="${systemConfiguration.welcomeMessageSpanish}" /></li>
	
	<spring:message code="systemConfiguration.phoneCountryCode" var="phoneCountryCode" />
	<li><b>${phoneCountryCode}:</b> <jstl:out value="${systemConfiguration.phoneCountryCode}" /></li>
	
	<spring:message code="systemConfiguration.periodFinder" var="periodFinder" />
	<li><b>${periodFinder}:</b> <jstl:out value="${systemConfiguration.periodFinder}" /></li>
	
	<spring:message code="systemConfiguration.maxResultsFinder" var="maxResultsFinder" />
	<li><b>${maxResultsFinder}:</b> <jstl:out value="${systemConfiguration.maxResultsFinder}" /></li>
	
	<spring:message code="systemConfiguration.spamWords" var="spamWords" />
	<li><b>${spamWords}:</b> <jstl:out value="${systemConfiguration.spamWords}" /></li>
	
</ul>

<acme:button url="systemConfiguration/administrator/edit.do" code="button.edit" /><br /><br />
<acme:button url="systemConfiguration/administrator/computeSpammer.do" code="button.computeSpammer" />
