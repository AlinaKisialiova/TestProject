<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: akiseleva
  Date: 07.04.2015
  Time: 7:52
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
    <div class="span12">  <h1>Evaluation Reminder</h1></div>

    <div class="span12">
        <h3>
            <a href="<c:url value="/informationBoard" context="/project"/>"> Seminars Information Board </a>
        </h3>
        </div>
    <div class="span12"><br/></div>
    <div class="span12">
        <div class="control-group">
            <div class="control-label"> <b>Course Category: </b>
                ${checkCourse.category}</div>
        </div>
    </div>

    <div class="span12">
        <div class="control-group">
            <div class="control-label" ><b>Course Name: </b>
                <a href="<c:url value="/courseDetails/${checkCourse.id}" context="/project/"/>" >
                ${checkCourse.nameCourse}
                </a>
            </div>

        </div>
    </div>


    <div class="span8">
        <div class="control-group">
            <div class="control-label html-editor-bold"><b>Participants</b></div>
            <c:forEach items="${checkCourse.attenders}" var="attend">
                <div class="controls text">
                           <c:out value="${attend.name}" escapeXml="true"/>
                    <span class="controls text"><c:out value="${attend.email}" escapeXml="true"/></span>

                </div>
            </c:forEach>
        </div>
    </div>

<div class="span12">
    <div class="btn-group">
        <form action="<c:url value="/evaluationReminder/${checkCourse.id}" context="/project/"/>" method="post">
        <input type="submit" value="Start" class="btn-primary"/>
        <input type="submit" value="Reset" class="btn-info">
        </form>
        </div>
</div>
<div class="span12">
   <h2> ${startMessage}</h2>
    </div>
    <div class="span12">
        <a href="<c:url value="/informationBoard" context="/project"/>">Back</a>
    </div>

</div>
</div>
