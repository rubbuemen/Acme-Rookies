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

<display:table pagesize="5" class="displaytag" name="sponsorships" requestURI="${requestURI}" id="row">

	<spring:message code="sponsorship.creditCard" var="creditCard" />
	<display:column title="${creditCard}">
			<spring:message code="creditCard.holder" />: ${row.creditCard.holder}<br />
			<spring:message code="creditCard.makeCreditCard" />: ${row.creditCard.makeCreditCard}<br />
			<spring:message code="creditCard.number" />: ${row.creditCard.number}<br />
			<spring:message code="creditCard.expirationMonth" />: ${row.creditCard.expirationMonth}<br />
			<spring:message code="creditCard.expirationYear" />: ${row.creditCard.expirationYear}<br />
			<spring:message code="creditCard.cvv" />: ${row.creditCard.cvv}
	</display:column>
	
	<spring:message code="sponsorship.banner" var="banner" />
	<display:column title="${banner}" >
		<img src="<jstl:out value="${row.banner}"/>" width="300px" height="100px" />
	</display:column>
	
	<spring:message code="sponsorship.targetPage" var="targetPage" />
	<display:column title="${targetPage}" >
		<a href="<jstl:out value="${row.targetPage}"/>">${row.targetPage}</a>
	</display:column>
	
	<spring:message code="sponsorship.position" var="position" />
		<display:column title="${position}">
			<acme:button url="position/provider/show.do?positionId=${row.position.id}" code="button.show" />
		</display:column>
	
	<spring:message code="sponsorship.edit" var="editH" />
	<display:column title="${editH}" >
		<acme:button url="sponsorship/provider/edit.do?sponsorshipId=${row.id}" code="button.edit" />
	</display:column>
	
	<spring:message code="sponsorship.delete" var="deleteH" />
	<display:column title="${deleteH}" >
		<acme:button url="sponsorship/provider/delete.do?sponsorshipId=${row.id}" code="button.delete" />
	</display:column>
			
</display:table>

<acme:button url="sponsorship/provider/create.do" code="button.create" />
