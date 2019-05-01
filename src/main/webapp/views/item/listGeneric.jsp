<%--
 * index.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table pagesize="5" class="displaytag" name="items" requestURI="${requestURI}" id="row">

	<spring:message code="item.name" var="name" />
	<display:column property="name" title="${name}" />
	
	<spring:message code="item.description" var="description" />
	<display:column property="description" title="${description}" />
	
	<spring:message code="item.link" var="link" />
	<display:column title="${link}">
		<a href="<jstl:out value='${row.link}' />"><jstl:out value='${row.link}' /></a>
	</display:column>	
	
	<spring:message code="item.picture" var="picture" />
	<display:column title="${picture}">
	<jstl:if test="${not empty row.picture}">
		<img src="<jstl:out value='${row.picture}' />"  width="200px" height="200px" />
	</jstl:if>	
	</display:column>	

	<jstl:if test="${mapItemProvider != null}">
		<spring:message code="item.provider" var="provider" />
		<display:column title="${provider}">
			<acme:button url="provider/show.do?providerId=${mapItemProvider.get(row).id}" code="button.show" />
		</display:column>
	</jstl:if>	
			
</display:table>

