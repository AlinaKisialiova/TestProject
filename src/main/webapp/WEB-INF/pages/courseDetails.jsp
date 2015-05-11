<%@ page import="by.mogilev.model.CourseStatus" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div style="text-align: center;">
<c:set var="course" value="${checkCourse}"/>
<table align="center">
    <tr><td><strong>Course Category</strong></td>
    <td><c:out value="${course.category}" escapeXml="true"/> </td>
    </tr>
    <tr><td><strong>Course Name</strong></td>
        <td><c:out value="${course.nameCourse}"/></td>
    </tr>
    <tr><td><strong>Course Description</strong></td>
        <td><c:out value="${course.description}"/></td>
    </tr>
    <tr><td><strong>Course Links</strong></td>
        <td><c:out value="${course.links}"/></td>
    </tr>
    <tr><td><strong>Course Duration</strong></td>
        <td><c:out value="${course.duration}"/></td>
    </tr>
    <tr><td><strong>Numbers of Subscribers</strong></td>
        <td><c:out value="${course.subscribers.size()}"/></td>
    </tr>
    <tr><td><strong>Numbers of Attende</strong></td>
        <td><c:out value="${course.attenders.size()}"/></td>
    </tr>
    <tr><td><strong>Course Status</strong></td>
        <td>
        <c:set var="NOT_APPROVE" value="<%=CourseStatus.NOT_APPROVE%>"/>
        <c:set var="APPROVE_DEPARTMENT_MANAGER" value="<%=CourseStatus.APPROVE_DEPARTMENT_MANAGER%>"/>
        <c:set var="APPROVE_DEPARTMENT_MANAGER" value="<%=CourseStatus.APPROVE_DEPARTMENT_MANAGER%>"/>
        <c:set var="APPROVE_KNOWLEDGE_MANAGER" value="<%=CourseStatus.APPROVE_KNOWLEDGE_MANAGER%>"/>
        <c:set var="DELIVERED" value="<%=CourseStatus.DELIVERED%>"/>
        <c:choose>
            <c:when test="${course.courseStatus eq NOT_APPROVE}">
                <c:out value="Not Approve" escapeXml="true"/>
            </c:when>

            <c:when test="${course.courseStatus == APPROVE_DEPARTMENT_MANAGER}">
                <c:out
                        value="Approve department manager" escapeXml="true"/>
            </c:when>
            <c:when test="${course.courseStatus == APPROVE_KNOWLEDGE_MANAGER}">
                <c:out
                        value="Approve knowledge manager" escapeXml="true"/></c:when>
            <c:when test="${course.courseStatus == DELIVERED}">
                <c:out value="Delivered"   escapeXml="true"/>
            </c:when>
        </c:choose>
        </td>
    </tr>
    <tr><td><strong>Course Evaluation</strong></td>
        <td><c:out value="${course.evaluation}"/></td>
    </tr>
    <security:authentication property="principal.username" var="user"/>
    <c:if test="${user eq course.lector.username}">
    <form action="<c:url value="/courseDetails/${course.id}"/>" method="post" >
    <tr>
        <td>
        <a href="<c:url value="/editCourse/${course.id}"/>" class="btn-info"> <b>Edit Course </b></a>
    </td>


            <td>
                <input type="submit" name="deleteCourse" value="Delete" class="btn-danger"/>
            </td>

        </tr>
    </form>
    </c:if>
</table>
</div>