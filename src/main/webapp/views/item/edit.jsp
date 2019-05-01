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

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${actionURL}" modelAttribute="item">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="item.name" path="name" placeholder="Lorem Ipsum"/>
	<br />

	<acme:textarea code="item.description" path="description" placeholder="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean at auctor massa" />
	<br />
	
	<acme:textbox code="item.link" path="link" placeholder="http://LoremIpsum.com" type="url" />
	<br />
	
	<acme:textbox code="item.picture" path="picture" placeholder="http://LoremIpsum.com" type="url" />
	<br />
	<jstl:if test="${not empty picture}">
		<img src="<jstl:out value='${picture}' />" />
		<br /><br />
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${item.id == 0}">
			<acme:submit name="save" code="button.register" />
		</jstl:when>
		<jstl:otherwise>
			<acme:submit name="save" code="button.save" />
		</jstl:otherwise>
	</jstl:choose>
	
	<acme:cancel url="item/provider/list.do" code="button.cancel" />
</form:form>