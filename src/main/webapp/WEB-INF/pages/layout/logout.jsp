<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: akiseleva
  Date: 06.05.2015
  Time: 7:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

Hello, <security:authentication property="principal.username" var="user"/> ${user}!
<a href="<c:url value="/j_spring_security_logout" context="/project"/>"> Logout</a>
<br>

<sec:authorize access="hasRole('ROLE_LECTOR')">
    I am know that you are a lector!
</sec:authorize>

<sec:authorize access="hasRole('ROLE_USER')">
    I am know that you are a user!
</sec:authorize>

<sec:authorize access="hasRole('KNOWLEDGE_MANAGER')">
    I am know that you are a Knowledge Manager!
    <input type="hidden" id="manager" name="manager" value="KNOWLEDGE_MANAGER"/>
</sec:authorize>

<sec:authorize access="hasRole('DEPARTMENT_MANAGER')">
    I am know that you are a Department Manager!
    <input type="hidden" name="manager" id="manager" value="DEPARTMENT_MANAGER"/>
</sec:authorize>



