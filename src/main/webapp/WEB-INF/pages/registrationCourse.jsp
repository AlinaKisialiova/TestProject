<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<br/>
<h1>Register Course</h1>
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
<a href="informationBoard"> Seminar Information Board</a>
<br/><br/><br/>

<form:form action="registrationCourse" method="post" name="regForm" class="form-horizontal" modelAttribute="Course">    <fieldset>

    <div class="control-group">
    <label class="control-label">  Course Category</label>
        <div class="controls">
    <form:select path="category" name="category"  class="span5">
    <form:option value="Project Management" label="Project Management" />
    <form:option value="Development" label="Development" />
            </form:select>
        </div>
</div>
        <div class="control-group">
    <label class="control-label">Course Name </label>
        <div class="controls">
<form:input path="nameCourse" class="span5" id="newCourseName"/>
                <span style="color:red" id="errName"></span>
        </div>
        </div>

        <div class="control-group">
            <label class="control-label">Course Description</label>
        <div class="controls">
            <form:input path="description" name="newCourseDescription" cssClass="span5"/>
            <span style="color:red" id="errDescription"></span>
            </div>
            </div>

        <div class="control-group">
            <label class="control-label">Course Links</label>
            <div class="controls">

           <form:textarea name="newCourseLinks" path="links"/>
             <img src="resources/reg.jpg"/>


       </div>
            </div>

        <div class="control-group">
            <label class="control-label">Course Duration</label>
            <div class="controls">
            <form:input type="text" id="newCourseDuration" path="duration"/>
                <span style="color:red" id="errDuration"/>
                </div>
            </div>

        <security:authentication property="principal.username" var="user"/>
        <div class="form-actions">

            <input type="submit" value="Save" onclick="return validate();" class="btn btn-primary"/>

                <a href="<c:url value="/informationBoard" context="/project"/>">Back</a>


            </div>


    </fieldset>
</form:form>

<script type="text/javascript">

    function validate() {
        var nameCourse = document.forms["regForm"]["newCourseName"].value;
        var durCourse = document.forms["regForm"]["newCourseDuration"].value;
        if (nameCourse.length==0 ){
            document.getElementById("errName").innerHTML="*required field";
            return false;
        }
        var par_pattern=/^\d+$/;
        if (durCourse.length==0 || parseInt(durCourse) < 1  ||  !(par_pattern.test(durCourse))){

            document.getElementById("errDuration").innerHTML="*incorrectly field";
            return false;
        }

    }
    </script>
