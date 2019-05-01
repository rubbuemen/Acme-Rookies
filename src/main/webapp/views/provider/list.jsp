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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table pagesize="5" class="displaytag" name="providers"	requestURI="${requestURI}" id="row">

	<spring:message code="actor.name" var="name" />
	<display:column property="name" title="${name}" />

	<spring:message code="actor.surnames" var="surnames" />
	<display:column property="surnames" title="${surnames}" />

	<spring:message code="actor.VATNumber" var="VATNumber" />
	<display:column property="VATNumber" title="${VATNumber}" />

	<spring:message code="actor.photo" var="photo" />
	<display:column title="${photo}" >
		<img src="<jstl:out value="${row.photo}"/>" width="200px" height="200px" />
	</display:column>

	<spring:message code="actor.email" var="email" />
	<display:column property="email" title="${email}" />

	<spring:message code="actor.phoneNumber" var="phoneNumber" />
	<display:column property="phoneNumber" title="${phoneNumber}" />

	<spring:message code="actor.address" var="address" />
	<display:column property="address" title="${address}" />
	
	<spring:message code="provider.make" var="make" />
	<display:column property="make" title="${make}" />
	
	<spring:message code="provider.items" var="items" />
	<display:column title="${items}">
		<acme:button url="item/listGeneric.do?providerId=${row.id}" code="button.show" />
	</display:column>

</display:table>
