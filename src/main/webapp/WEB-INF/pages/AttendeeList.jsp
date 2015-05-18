<%@ page import="by.mogilev.model.Course" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
        <tr><td><strong>Devilered</strong></td>
            <td><c:out value="${course.courseStatus}"/></td>
        </tr>
        <tr><td><strong>Course Evaluation</strong></td>
            <td><c:out value="${course.evaluation}"/></td>
        </tr>
        <tr><td><strong>Attendee List: </strong></td>
            <td>
                <c:forEach items="${attendee}" var="att">
                    <c:out value="${att.name}"/> ,
                </c:forEach>
            </td>
        </tr>
        <tr>
            <form action="<c:url value="/attendeeList/${course.id}" context="/project"/>" method="post">
                <td colspan="2">
                    <c:choose>
                        <c:when test="${(attCourseOfUser.contains(course))}">
                            <input type="submit" class="btn btn-danger btn-lg btn-block"name="REMOTE_FROM_ATT" value="Exclude me from Exclude List" onclick="setAction('REMOTE_FROM_ATT') ">
                        </c:when>
                        <c:otherwise>
                            <input type="submit" class="btn btn-primary btn-lg btn-block" name="ADD_IN_ATT" value="Include me into Attendee List" onclick="setAction('ADD_IN_ATT')"
                        </c:otherwise>
                    </c:choose>

                </td>

                <input type="hidden" name="fieldForSubmit" class="fieldForSubmit"/>
            </form>
        </tr>
    </table>


</div>


