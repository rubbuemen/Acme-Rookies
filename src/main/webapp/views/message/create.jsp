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

<form:form modelAttribute="messageEntity">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<jstl:if test="${broadcast != true}">
		<acme:select items="${recipients}" multiple="true" itemLabel="userAccount.username" code="message.recipients" path="recipients"/>
		<br />
	</jstl:if>
	
	<acme:textbox code="message.subject" path="subject" placeholder="Lorem Ipsum"/>
	<br />
	
	<acme:textarea code="message.body" path="body" placeholder="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean at auctor massa" />
	<br />
	
	<acme:textarea code="message.tags" path="tags" placeholder="Lorem ipsum, Lorem ipsum, Lorem ipsum, Lorem ipsum" />
	<br />

	<acme:submit name="save" code="button.send" />
	<acme:cancel url="message/list.do" code="button.cancel" />

</form:form>