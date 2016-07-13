<%--
 * layout.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="shortcut icon" href="favicon.ico"/> 

<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript" src="scripts/jmenu.js"></script>

<!-- Modal Bootstrap -->
<script type="text/javascript" src="scripts/bootstrap-modal.js"></script>
<script type="text/javascript" src="scripts/bootstrap-modalmanager.js"></script>
<!-- Collapse and transitions -->
<script type="text/javascript" src="scripts/bootstrap-collapse.js"></script>
<script type="text/javascript" src="scripts/bootstrap-transition.js"></script>
<!-- Google Maps -->
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script type="text/javascript" src="scripts/google-maps.js"></script>
<!-- Carousel -->
<script type="text/javascript" src="scripts/bootstrap-carousel.js"></script>

<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/jmenu.css" media="screen" type="text/css" />
<link rel="stylesheet" href="styles/displaytag.css" type="text/css">
<link rel="stylesheet" href="styles/ownStyle.css" type="text/css">
<!-- Cookie bar -->
<link rel="stylesheet" href="styles/jquery.cookiebar.css" type="text/css">
<!-- Menu bar -->
<link rel="stylesheet" href="styles/menu.css" type="text/css">
<!-- Modal Bootstrap -->
<link rel="stylesheet" href="styles/bootstrap-modal.css" type="text/css">
<link rel="stylesheet" href="styles/bootstrap-modal-bs3patch.css" type="text/css">
<!-- Star Rating -->
<link rel="stylesheet" href="styles/star-rating.css" type="text/css" />
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<!-- Bootstrap -->
<link rel="stylesheet" href="styles/bootstrap.css">

<title><tiles:insertAttribute name="title" ignore="true" /></title>

<script type="text/javascript">
	$(function() {
	  if ($.browser.msie && $.browser.version.substr(0,1)<7)
	  {
	    $('li').has('ul').mouseover(function(){
	        $(this).children('ul').css('visibility','visible');
	        }).mouseout(function(){
	        $(this).children('ul').css('visibility','hidden');
	    	})
	  }
	});
</script>

</head>

<body>

	<div>
		<tiles:insertAttribute name="header" />
	</div>
	<div>
		<h1>
			<tiles:insertAttribute name="title" />
		</h1>
		<tiles:insertAttribute name="body" />	
		<jstl:if test="${message != null}">
			<br />
			<span class="message"><spring:message code="${message}" /></span>
		</jstl:if>	
	</div>
	<div>
		<tiles:insertAttribute name="footer" />
	</div>

</body>
</html>