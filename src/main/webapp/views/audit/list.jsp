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

<display:table pagesize="5" class="displaytag" name="audits" requestURI="${requestURI}" id="row">

	<spring:message code="audit.moment" var="moment" />
	<display:column title="${moment}">
			<fmt:formatDate var="format" value="${row.moment}" pattern="dd/MM/YYYY HH:mm" />
			<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="audit.text" var="text" />
	<display:column property="text" title="${text}" />
	
	<spring:message code="audit.score" var="score" />
	<display:column property="score" title="${score}" />
	
	<spring:message code="audit.position" var="position" />
	<display:column title="${position}">
		<acme:button url="position/auditor/show.do?positionId=${row.position.id}" code="button.show" />
	</display:column>
		
	<spring:message code="audit.edit" var="editH" />
	<display:column title="${editH}" >
		<jstl:if test="${!row.isFinalMode}">
			<acme:button url="audit/auditor/edit.do?auditId=${row.id}" code="button.edit" />
		</jstl:if>	
	</display:column>
	
	<spring:message code="audit.delete" var="deleteH" />
	<display:column title="${deleteH}" >
		<jstl:if test="${!row.isFinalMode}">
			<acme:button url="audit/auditor/delete.do?auditId=${row.id}" code="button.delete" />
		</jstl:if>	
	</display:column>
	
	<spring:message code="audit.changeFinalMode" var="changeFinalModeH" />
	<display:column title="${changeFinalModeH}" >
		<jstl:choose>
			<jstl:when test="${!row.isFinalMode}">
				<acme:button url="audit/auditor/change.do?auditId=${row.id}" code="button.change" />
			</jstl:when>
			<jstl:when test="${row.isFinalMode}">
				<spring:message code="audit.isFinalMode" />
			</jstl:when>
		</jstl:choose>
	</display:column>
			
</display:table>

<acme:button url="audit/auditor/create.do" code="button.create" />
