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
        <td><c:out value="${course.subscribers.size()}"/></td>
    </tr>
    <tr><td><strong>Numbers of Attende</strong></td>
        <td><c:out value="${course.attenders.size()}"/></td>
    </tr>
    <tr><td><strong>Devilered</strong></td>
        <td><c:out value="${course.delivered}"/></td>
    </tr>
    <tr><td><strong>Course Evaluation</strong></td>
        <td><c:out value="${course.evaluation}"/></td>
    </tr>

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
</table>
</div>