<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Администратор
  Date: 29.03.2015
  Time: 18:32
  To change this template use File | Settings | File Templates.
--%>
<div class="row">
<div class="col-md-8 col-md-offset-4">
<div class="control-group">
    <div class="control-label"> <b>Lector Name: </b>
    ${checkCourse.lector.name}</div>
</div>
    </div>

<div class="col-md-8 col-md-offset-4">
    <div class="control-group">
        <div class="control-label" ><b>Course Name: </b>
       ${checkCourse.nameCourse}</div>
    </div>
</div>

<div class="col-md-8 col-md-offset-4">
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
    <div class="col-md-8 col-md-offset-4">
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


</div>
</div>
