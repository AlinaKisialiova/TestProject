<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 29.03.2015
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<div class="row">
    <div class="span12">
        Hello, <security:authentication property="principal.username" var="user"/> ${user}!
        <a href=j_spring_security_logout> Logout</a>
    </div>
    <div class="span12">
        <sec:authorize access="hasRole('ROLE_LECTOR')">
            I am know that you are a lector!
        </sec:authorize>

        <sec:authorize access="hasRole('ROLE_USER')">
            I am know that you are a user!
        </sec:authorize>
    </div>
    <div class="span12 header">
        <h1>Participants List</h1></div>

<div class="span12">
<div class="control-group">
    <div class="control-label"> <b>Lector Name: </b>
    ${checkCourse.lector.name}</div>
</div>
    </div>

<div class="span12">
    <div class="control-group">
        <div class="control-label" ><b>Course Name: </b>
       ${checkCourse.nameCourse}</div>
    </div>
</div>

<div class="span8">
<div class="control-group">
    <form action="<c:url value="/participantsList/${checkCourse.id}"/>" method="post">
        <select name="selectParticipants">
            <option>All Participants</option>
            <option>Current Attendees</option>
        </select>
        <input type="submit" value="Ok" class="btn">
    </form>
</div>
</div>
    <div class="span8">
    <div class="control-group">
        <div class="control-label html-editor-bold"><b>Participants</b></div>
    <c:forEach items="${participants}" var="partic">
    <div class="controls text">
        <c:out value="${partic.name}" escapeXml="true"/>
        <span class="controls text"><c:out value="${partic.email}" escapeXml="true"/></span>
    </div>
    </c:forEach>
    </div>
    </div>

    <div class="span12">
    <a href="<c:url value="/informationBoard" context="/project"/>">Back</a>
    </div>

</div>
</div>
