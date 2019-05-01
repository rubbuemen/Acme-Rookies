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


<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryC1"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryC1 == \"null\" ? 0 : avgQueryC1}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryC1 == \"null\" ? 0 : minQueryC1}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryC1 == \"null\" ? 0 : maxQueryC1}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryC1 == \"null\" ? 0 : stddevQueryC1}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryC2"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryC2 == \"null\" ? 0 : avgQueryC2}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryC2 == \"null\" ? 0 : minQueryC2}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryC2 == \"null\" ? 0 : maxQueryC2}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryC2 == \"null\" ? 0 : stddevQueryC2}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryC3"/></summary>

<display:table class="displaytag" name="queryC3" id="row1">
	<spring:message code="actor.name" var="name" />
	<display:column property="name" title="${name}" />

	<spring:message code="actor.surnames" var="surnames" />
	<display:column property="surnames" title="${surnames}" />

	<spring:message code="actor.VATNumber" var="VATNumber" />
	<display:column property="VATNumber" title="${VATNumber}" />

	<spring:message code="actor.photo" var="photo" />
	<display:column title="${photo}" >
		<img src="<jstl:out value="${row1.photo}"/>" width="200px" height="200px" />
	</display:column>

	<spring:message code="actor.email" var="email" />
	<display:column property="email" title="${email}" />

	<spring:message code="actor.phoneNumber" var="phoneNumber" />
	<display:column property="phoneNumber" title="${phoneNumber}" />

	<spring:message code="actor.address" var="address" />
	<display:column property="address" title="${address}" />
	
	<spring:message code="company.commercialName" var="commercialName" />
	<display:column property="commercialName" title="${commercialName}" />
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryC4"/></summary>

<display:table class="displaytag" name="queryC4" id="row2">
	<spring:message code="actor.name" var="name" />
	<display:column property="name" title="${name}" />

	<spring:message code="actor.surnames" var="surnames" />
	<display:column property="surnames" title="${surnames}" />

	<spring:message code="actor.VATNumber" var="VATNumber" />
	<display:column property="VATNumber" title="${VATNumber}" />

	<spring:message code="actor.photo" var="photo" />
	<display:column title="${photo}" >
		<img src="<jstl:out value="${row2.photo}"/>" width="200px" height="200px" />
	</display:column>

	<spring:message code="actor.email" var="email" />
	<display:column property="email" title="${email}" />

	<spring:message code="actor.phoneNumber" var="phoneNumber" />
	<display:column property="phoneNumber" title="${phoneNumber}" />

	<spring:message code="actor.address" var="address" />
	<display:column property="address" title="${address}" />
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryC5"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryC5 == \"null\" ? 0 : avgQueryC5}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryC5 == \"null\" ? 0 : minQueryC5}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryC5 == \"null\" ? 0 : maxQueryC5}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryC5 == \"null\" ? 0 : stddevQueryC5}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryC6"/></summary>

<h3><spring:message code="dashboard.queryC6.bestPosition"/></h3>
<display:table class="displaytag" name="queryC6_1" id="row3">
	<spring:message code="position.ticker" var="ticker" />
	<display:column property="ticker" title="${ticker}" />
	
	<spring:message code="position.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="position.description" var="description" />
	<display:column property="description" title="${description}" />
	
	<spring:message code="position.deadline" var="deadline" />
	<display:column title="${deadline}">
			<fmt:formatDate var="format" value="${row.deadline}" pattern="dd/MM/YYYY" />
			<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="position.skills" var="skills" />
	<display:column title="${skills}" >
	<ul>
	<jstl:forEach items="${row.skills}" var="skill">
		<li><jstl:out value="${skill}"/></li>
	</jstl:forEach>
	</ul>
	</display:column>
	
	<spring:message code="position.technologies" var="technologies" />
	<display:column title="${technologies}" >
	<ul>
	<jstl:forEach items="${row.technologies}" var="technology">
		<li><jstl:out value="${technology}"/></li>
	</jstl:forEach>
	</ul>
	</display:column>
	
	<spring:message code="position.salary" var="salary" />
	<display:column property="salary" title="${salary}" />
	
	<spring:message code="position.profile" var="profile" />
	<display:column property="profile" title="${profile}" />
</display:table>

<h3><spring:message code="dashboard.queryC6.worstPosition"/></h3>
<display:table class="displaytag" name="queryC6_2" id="row4">
	<spring:message code="position.ticker" var="ticker" />
	<display:column property="ticker" title="${ticker}" />
	
	<spring:message code="position.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="position.description" var="description" />
	<display:column property="description" title="${description}" />
	
	<spring:message code="position.deadline" var="deadline" />
	<display:column title="${deadline}">
			<fmt:formatDate var="format" value="${row.deadline}" pattern="dd/MM/YYYY" />
			<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="position.skills" var="skills" />
	<display:column title="${skills}" >
	<ul>
	<jstl:forEach items="${row.skills}" var="skill">
		<li><jstl:out value="${skill}"/></li>
	</jstl:forEach>
	</ul>
	</display:column>
	
	<spring:message code="position.technologies" var="technologies" />
	<display:column title="${technologies}" >
	<ul>
	<jstl:forEach items="${row.technologies}" var="technology">
		<li><jstl:out value="${technology}"/></li>
	</jstl:forEach>
	</ul>
	</display:column>
	
	<spring:message code="position.salary" var="salary" />
	<display:column property="salary" title="${salary}" />
	
	<spring:message code="position.profile" var="profile" />
	<display:column property="profile" title="${profile}" />
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryB1"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryB1 == \"null\" ? 0 : avgQueryB1}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryB1 == \"null\" ? 0 : minQueryB1}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryB1 == \"null\" ? 0 : maxQueryB1}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryB1 == \"null\" ? 0 : stddevQueryB1}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryB2"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryB2 == \"null\" ? 0 : avgQueryB2}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryB2 == \"null\" ? 0 : minQueryB2}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryB2 == \"null\" ? 0 : maxQueryB2}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryB2 == \"null\" ? 0 : stddevQueryB2}"></jstl:out></li>
</ul>

</details><br/>


<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryB3"/></summary>

<ul>
<li><b><spring:message code="dashboard.ratio"/>:</b> <jstl:out value="${ratioQueryB3 == \"null\" ? 0 : ratioQueryB3}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeRookiesC1"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryAcmeRookiesC1 == \"null\" ? 0 : avgQueryAcmeRookiesC1}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryAcmeRookiesC1 == \"null\" ? 0 : minQueryAcmeRookiesC1}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryAcmeRookiesC1 == \"null\" ? 0 : maxQueryAcmeRookiesC1}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryAcmeRookiesC1 == \"null\" ? 0 : stddevQueryAcmeRookiesC1}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeRookiesC2"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryAcmeRookiesC2 == \"null\" ? 0 : avgQueryAcmeRookiesC2}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryAcmeRookiesC2 == \"null\" ? 0 : minQueryAcmeRookiesC2}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryAcmeRookiesC2 == \"null\" ? 0 : maxQueryAcmeRookiesC2}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryAcmeRookiesC2 == \"null\" ? 0 : stddevQueryAcmeRookiesC2}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeRookiesC3"/></summary>

<display:table class="displaytag" name="queryAcmeRookiesC3" id="row5">
	<spring:message code="actor.name" var="name" />
	<display:column property="name" title="${name}" />

	<spring:message code="actor.surnames" var="surnames" />
	<display:column property="surnames" title="${surnames}" />

	<spring:message code="actor.VATNumber" var="VATNumber" />
	<display:column property="VATNumber" title="${VATNumber}" />

	<spring:message code="actor.photo" var="photo" />
	<display:column title="${photo}" >
		<img src="<jstl:out value="${row5.photo}"/>" width="200px" height="200px" />
	</display:column>

	<spring:message code="actor.email" var="email" />
	<display:column property="email" title="${email}" />

	<spring:message code="actor.phoneNumber" var="phoneNumber" />
	<display:column property="phoneNumber" title="${phoneNumber}" />

	<spring:message code="actor.address" var="address" />
	<display:column property="address" title="${address}" />
	
	<spring:message code="company.commercialName" var="commercialName" />
	<display:column property="commercialName" title="${commercialName}" />
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeRookiesC4"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryAcmeRookiesC4 == \"null\" ? 0 : avgQueryAcmeRookiesC4}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeRookiesB1"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryAcmeRookiesB1 == \"null\" ? 0 : avgQueryAcmeRookiesB1}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryAcmeRookiesB1 == \"null\" ? 0 : minQueryAcmeRookiesB1}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryAcmeRookiesB1 == \"null\" ? 0 : maxQueryAcmeRookiesB1}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryAcmeRookiesB1 == \"null\" ? 0 : stddevQueryAcmeRookiesB1}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeRookiesB2"/></summary>

<display:table class="displaytag" name="queryAcmeRookiesB2" id="row6">
	<spring:message code="actor.name" var="name" />
	<display:column property="name" title="${name}" />

	<spring:message code="actor.surnames" var="surnames" />
	<display:column property="surnames" title="${surnames}" />

	<spring:message code="actor.VATNumber" var="VATNumber" />
	<display:column property="VATNumber" title="${VATNumber}" />

	<spring:message code="actor.photo" var="photo" />
	<display:column title="${photo}" >
		<img src="<jstl:out value="${row6.photo}"/>" width="200px" height="200px" />
	</display:column>

	<spring:message code="actor.email" var="email" />
	<display:column property="email" title="${email}" />

	<spring:message code="actor.phoneNumber" var="phoneNumber" />
	<display:column property="phoneNumber" title="${phoneNumber}" />

	<spring:message code="actor.address" var="address" />
	<display:column property="address" title="${address}" />
	
	<spring:message code="provider.make" var="make" />
	<display:column property="make" title="${make}" />
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeRookiesA1"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryAcmeRookiesA1 == \"null\" ? 0 : avgQueryAcmeRookiesA1}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryAcmeRookiesA1 == \"null\" ? 0 : minQueryAcmeRookiesA1}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryAcmeRookiesA1 == \"null\" ? 0 : maxQueryAcmeRookiesA1}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryAcmeRookiesA1 == \"null\" ? 0 : stddevQueryAcmeRookiesA1}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeRookiesA2"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryAcmeRookiesA2 == \"null\" ? 0 : avgQueryAcmeRookiesA2}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryAcmeRookiesA2 == \"null\" ? 0 : minQueryAcmeRookiesA2}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryAcmeRookiesA2 == \"null\" ? 0 : maxQueryAcmeRookiesA2}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryAcmeRookiesA2 == \"null\" ? 0 : stddevQueryAcmeRookiesA2}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeRookiesA3"/></summary>

<display:table class="displaytag" name="queryAcmeRookiesA3" id="row7">
	<spring:message code="actor.name" var="name" />
	<display:column property="name" title="${name}" />

	<spring:message code="actor.surnames" var="surnames" />
	<display:column property="surnames" title="${surnames}" />

	<spring:message code="actor.VATNumber" var="VATNumber" />
	<display:column property="VATNumber" title="${VATNumber}" />

	<spring:message code="actor.photo" var="photo" />
	<display:column title="${photo}" >
		<img src="<jstl:out value="${row7.photo}"/>" width="200px" height="200px" />
	</display:column>

	<spring:message code="actor.email" var="email" />
	<display:column property="email" title="${email}" />

	<spring:message code="actor.phoneNumber" var="phoneNumber" />
	<display:column property="phoneNumber" title="${phoneNumber}" />

	<spring:message code="actor.address" var="address" />
	<display:column property="address" title="${address}" />
	
	<spring:message code="provider.make" var="make" />
	<display:column property="make" title="${make}" />
</display:table>

</details><br/>