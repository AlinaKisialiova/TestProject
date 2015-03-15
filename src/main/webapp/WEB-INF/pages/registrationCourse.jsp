<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<h1>Register Course</h1>
<a href="informationBoard"> Seminar Information Board</a>
<br/>
<form action="registrationCourse" method="post" name="regForm">
    <table>
        <tr>
            <td>Course Category</td>
            <td> <select name="newCourseCategory">
                <option selected value="Project Management">Project Management </option>
                <option value="Development"> Development</option>
            </select></td>

        </tr>
        <tr>
            <td>Course Name</td>
            <td><input type="text" name="newCourseName"/>
                <span style="color:red" id="errName"></span></td>

        </tr>
        <tr>
            <td>Course Description</td>
            <td><input type="text" name="newCourseDescription"/>
                <span style="color:red" id="errDescription"></span></td>

        </tr>
        <tr>
            <td>Course Links</td>
            <td><textarea name="newCourseLinks"></textarea> </td>
            <td>   <img src="resources/reg.jpg"/></td>
        </tr>
        <tr>
            <td>Course Duration</td>
            <td><input type="text" name="newCourseDuration">
                <span style="color:red" id="errDuration"></span></td>
        </tr>
        <security:authentication property="principal.username" var="user"/>
<input type="hidden" name="lectorName" value="${user}"/>
        <tr>
            <td><input type="reset" value="Cancel"/> </td>
            <td><input type="submit" value="Save" onclick="return validate();"/></td>
        </tr>

    </table>
 <font face="Arial" size=5 color="blue" >  ${message}</font>
</form>

<script type="text/javascript">

    function validate() {
        var nameCourse = document.forms["regForm"]["newCourseName"].value;
        var durCourse = document.forms["regForm"]["newCourseDuration"].value;
        if (nameCourse.length==0 ){
            document.getElementById("errName").innerHTML="*required field";
            return false;
        }
        var par_pattern=/^[0-9]$/;
        if (durCourse.length==0 || parseInt(durCourse) < 1  ||  !(par_pattern.test(durCourse))){

            document.getElementById("errDuration").innerHTML="*incorrectly field";
            return false;
        }

    }
    </script>