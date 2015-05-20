<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<security:authentication property="principal.username" var="user"/>
<form:form action="registrationCourse" method="post" name="regForm" modelAttribute="Course">
<div class="row">
    <div class="col-md-8 col-md-offset-4">
    <div class="control-group">
    <label class="control-label"> Course Category</label>
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
                <span class="errorText" id="errName"></span>
        </div>
        </div>

        <div class="control-group">
            <label class="control-label">Course Description</label>
        <div class="controls">
            <form:input path="description" name="newCourseDescription" cssClass="span5"/>
            <span id="errDescription" class="errorText"></span>
            </div>
            </div>

        <div class="control-group">
            <label class="control-label">Course Links</label>
            <div class="controls">

           <form:textarea name="newCourseLinks" path="links"/>
             <img src="resources/img/reg.jpg"/>
       </div>
            </div>

        <div class="control-group">
            <label class="control-label">Course Duration</label>
            <div class="controls">
            <form:input type="text" id="newCourseDuration" path="duration"/>
                <span class="errorText" id="errDuration"></span>
                </div>
            </div>
    <br>
    <div class="col-md-6 col-md-offset-1">

            <input type="submit" value="Save" onclick="return validate();" class="btn btn-primary"/>
    <a href="<c:url value="/informationBoard" context="/project"/>" > Cancel</a>
    </div>

</form:form>

