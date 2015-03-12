<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="text-align: center;">
<h1>Course Details</h1>
    <a href="<c:url value="/informationBoard"/>"> Seminar Information Board</a>
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
        <td><c:out value="${course.numbOfSubscribers}"/></td>
    </tr>
    <tr><td><strong>Numbers of Attende</strong></td>
        <td><c:out value="${course.numbOfAttendee}"/></td>
    </tr>
    <tr><td><strong>Devilered</strong></td>
        <td><c:out value="${course.delivered}"/></td>
    </tr>
    <tr><td><strong>Course Evaluation</strong></td>
        <td><c:out value="${course.evaluation}"/></td>
    </tr>
    <th><a href="<c:url value="/editCourse"/>"> Edit</a></th>

</table>
</div>