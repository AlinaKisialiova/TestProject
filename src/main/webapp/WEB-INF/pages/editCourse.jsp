<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: akiseleva
  Date: 11.03.2015
  Time: 9:36
  To change this template use File | Settings | File Templates.
--%>
<h1>Course Details Update</h1>
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
<h2> ${message}</h2>

<form:form method="post" name="editForm"  modelAttribute="Course">
<table align="center">
    <a href="<c:url value="/informationBoard"/>"> Seminar Information Board</a>
    <tr><td><strong>Course Category</strong></td>
        <td>
            <form:select path="category" items="${categoryMap}"/>
        </td>
    </tr>
    <tr><th>Course Name</th>
        <td>
            <form:input path="nameCourse" id="updCourseName" value="${course.nameCourse}"/>
            <span style="color:red" id="errName"></span></td>
        </td>
    </tr>
    <tr><th>Course Description</th>
        <td><form:input path="description" id="updCourseDescription" value="${course.description}"/>
        </td>
    </tr>
    <tr><th>Course Links</th>
        <td><form:textarea path="links" name="updCourseLinks" value="${course.links}"/>
        </td>
    </tr>
    <tr><th>Course Duration</th>
        <td><form:input path="duration" id="updCourseDuration" value="${course.duration}" />
            <span style="color:red" id="errDuration"/>
        </td>
    </tr>
    <tr><th>Numbers of Subscribers</th>
        <td><c:out value="${course.subscribers.size()}"/></td>
    </tr>
    <tr><td><strong>Numbers of Attende</strong></td>
        <td><c:out value="${course.attenders.size()}"/></td>
    </tr>
    <tr><td><strong>Devilered</strong></td>
        <td><c:out value="${course.courseStatus}"/></td>
    </tr>
    <tr><td><strong>Course Evaluation</strong></td>
        <td>
            <c:out value="${course.evaluation}"/>
        </td>
    </tr>

    <tr>
        <td><a href="informationBoard"> Cancel</a> </td>
        <td><input type="submit" value="OK" onclick="return validate();"/></td>
    </tr>
</table>
</form:form>

</div>

<script type="text/javascript">

    function validate() {
        var nameCourse = document.forms["editForm"]["updCourseName"].value;
        var durCourse = document.forms["editForm"]["updCourseDuration"].value;
        if (nameCourse.length==0 ){
            document.getElementById("errName").innerHTML="*required field";
            return false;
        }
        var par_pattern=/^\d+$/;
        if (durCourse.length==0 || parseInt(durCourse) < 1 || !(par_pattern.test(durCourse)) ){
            document.getElementById("errDuration").innerHTML="*incorrectly field";
            return false;
        }

    }
</script>
