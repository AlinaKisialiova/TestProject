<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: akiseleva
  Date: 11.03.2015
  Time: 9:36
  To change this template use File | Settings | File Templates.
--%>
<h1>Course Details Update</h1>

<c:set var="course" value="${checkCourse}"/>

<form method="post" name="editForm">
<table align="center">
    <a href="<c:url value="/informationBoard"/>"> Seminar Information Board</a>
    <tr><td><strong>Course Category</strong></td>
        <td>
            <select name="newCourseCategory">
                <option selected value="Project Management">Project Management </option>
                <option value="Development"> Development</option>
                </select>
        </td>
    </tr>
    <tr><td><strong>Course Name</strong></td>
        <td>
            <input type="text" name="updCourseName" value="<c:out value="${course.nameCourse}"/>"/>
            <span style="color:red" id="errName"></span></td>
        </td>
    </tr>
    <tr><td><strong>Course Description</strong></td>
        <td><input type="text" name="updCourseDescription" value="<c:out value="${course.description}"/>"/></td>
    </tr>
    <tr><td><strong>Course Links</strong></td>
        <td><textarea name="updCourseLinks"> <c:out value="${course.links}"/>
        </textarea></td>
    </tr>
    <tr><td><strong>Course Duration</strong></td>
        <td><input type="text" name="updCourseDuration" value="<c:out value="${course.duration}"/>"/>
            <span style="color:red" id="errDuration"/>
        </td>
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

    <tr><td><strong>Delete Course</strong></td>
        <td>
            <input type="checkbox" name="deleteCourse" />
        </td>

    </tr>
    <tr>
        <td><input type="reset" value="Cancel"/> </td>
        <td><input type="submit" value="Save" onclick="return validate();"/></td>
    </tr>
</table>
</form>
<font face="Arial" size=5 color="blue" >  ${message}</font>
</div>

<script type="text/javascript">

    function validate() {
        var nameCourse = document.forms["editForm"]["updCourseName"].value;
        var durCourse = document.forms["editForm"]["updCourseDuration"].value;
        if (nameCourse.length==0 ){
            document.getElementById("errName").innerHTML="*required field";
            return false;
        }

        if (durCourse.length==0 || parseInt(durCourse) < 1 ){
            document.getElementById("errDuration").innerHTML="*incorrectly field";
            return false;
        }

    }
</script>
