<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: akiseleva
  Date: 13.04.2015
  Time: 6:42
  To change this template use File | Settings | File Templates.
--%>
<div class="row">
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
<div class="span12"> <h2>Approve Course</h2>    </div>

    <div class="span12">
        <h4>
            <a href="<c:url value="/informationBoard" context="/project"/>"> Seminars Information Board </a>
        </h4>
    </div>
    <c:set  var="course" value="${courseList}"/>
<div class="span12">
    <div class="span3"><strong> Course Category</strong></div> <div class="span6"><c:out value="${course.category}" escapeXml="true"/></div>
    <div class="span3"><strong> Course Name</strong></div> <div class="span6"> <c:out  value="${course.nameCourse}" escapeXml="true"/></div>

    <div class="span3"><strong> Course Description</strong></div><div class="span6"> <c:out value="${course.description}" escapeXml="true"/></div>
    <div class="span3"><strong> Course Links</strong></div><div class="span6"> <c:out value="${course.links}" escapeXml="true"/></div>
    <div class="span3"><strong> Course Duration</strong></div><div class="span6"> <c:out value="${course.duration}" escapeXml="true"/></div>
</div>

    <div class="span12"><br/></div>

    <div class="span12">
    <div class="span2"><strong>Manager Name</strong></div>  <div class="span2"><strong>Manager Role</strong></div>
    <div class="span4"><strong>Decision</strong></div>  <div class="span2"><strong>Reason</strong></div>
        </div>
    <div class="span12">
    <div class="span2">Manager name</div>   <div class="span2"> Departament manager</div>
    <div class="span4"><select name="approve">
        <option>approve</option>
        <option> not approve</option>
    </select></div>
    <div class="span2"><textarea></textarea></div>
    </div>
<div class="span12"> <br/></div>
    <div class="span12">
        <div class="span2">Manager name</div>   <div class="span2"> Knowledge manager</div>
        <div class="span4"><select name="approve">
            <option>approve</option>
            <option> not approve</option>
        </select></div>
        <div class="span2"><textarea></textarea></div>
    </div>

</div>
    <div class="control-group">
        <input type="submit" value="Save" name="save" class="btn-primary">
<a href="<c:url value="/informationBoard" context="/project"/>" class="btn">Cancel</a>
    </div>
</div>


</div>