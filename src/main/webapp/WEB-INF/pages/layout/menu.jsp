<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: akiseleva
  Date: 06.05.2015
  Time: 5:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container">
<ul class="nav nav-tabs nav-justified">
    <li><a href="<c:url value="/informationBoard" context="/project"/>">Information Board</a></li>
    <li><a href="<c:url value="/mySeminars" context="/project"/>">My Seminars</a></li>
    <li><a href="<c:url value="/subscribePage" context="/project"/>">Subscribe </a></li>
    <li><a href="<c:url value="/attendeePage" context="/project"/>">Participate</a></li>
    <sec:authorize access="hasRole('ROLE_LECTOR')">
        <li><a href="<c:url value="/registrationCourse" context="/project"/>">Register course</a></li>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('KNOWLEDGE_MANAGER', 'DEPARTMENT_MANAGER')">
        <li><a href="<c:url value="/approvePage" context="/project"/>">Approve Course</a></li>
    </sec:authorize>
</ul>
</div>