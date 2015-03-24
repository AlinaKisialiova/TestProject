<%--
  Created by IntelliJ IDEA.
  User: akiseleva
  Date: 06.03.2015
  Time: 8:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="true"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page import="by.mogilev.controller.InformationBoardController" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title><tiles:insertAttribute name="title" ignore="true"/></title>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.js"></script>

</head>
<body>

<div class="container">

    <div class="row">
<div class="span9 offset5">
    <tiles:insertAttribute name="header" />
</div>

<div class="content">
  <tiles:insertAttribute name="content" />

</div>

<div class="span9 offset5">
    <tiles:insertAttribute name="footer" />

</div>

</div>

</div>
</body>
</html>
