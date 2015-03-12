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

<form action="editCourse" method="post" name="editForm">
<table align="center">
    <a href="<c:url value="/informationBoard"/>"> Seminar Information Board</a>
    <tr><td><strong>Course Category</strong></td>
        <td><input type="text" name="updCategory"/>
            <span style="color:red" id="errupdCategory"></span></td>
        </td>
    </tr>
    <tr><td><strong>Course Name</strong></td>
        <td>
            <input type="text" name="updCourseName"/>
            <span style="color:red" id="errName"></span></td>
        </td>
    </tr>
    <tr><td><strong>Course Description</strong></td>
        <td><input type="text" name="updCourseDescription"/></td>
    </tr>
    <tr><td><strong>Course Links</strong></td>
        <td><textarea name="updCourseLinks"> </textarea></td>
    </tr>
    <tr><td><strong>Course Duration</strong></td>
        <td><input type="text" name="updCourseDuration">
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
        <td><input type="checkbox" name="deleteCourse"> </td>
    </tr>
    <tr>
        <td><input type="reset" value="Cancel"/> </td>
        <td><input type="submit" value="Save" onclick="return validate();"/></td>
    </tr>
</table>
</form>
</div>

<script type="text/javascript">

    function validate() {
        var nameCourse = document.forms["regForm"]["newCourseName"].value;
        var durCourse = document.forms["regForm"]["newCourseDuration"].value;
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
