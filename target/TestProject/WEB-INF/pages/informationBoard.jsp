<%@ page import="by.mogilev.controller.InformationBoardController" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<%--
  Created by IntelliJ IDEA.
  User: akiseleva
  Date: 02.03.2015
  Time: 5:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Seminars Information Board</title>

</head>
<body>


<h1>Seminars Information Board</h1>
Hello, <security:authentication property="principal.username" var="user"/> ${user}! <a
        href="<c:url value="j_spring_security_logout"/>"> Logout</a>
</br>

<sec:authorize access="hasRole('ROLE_LECTOR')">
    I am know that you are a lector!
</sec:authorize>

<sec:authorize access="hasRole('ROLE_PARTICIPANT')">
    I am know that you are a participant!
</sec:authorize>

<sec:authorize access="hasRole('ROLE_MANAGER')">
    I am know that you are a manager!
</sec:authorize>
</br>
</br>

<div id='EvalRemindBlock' style="display: none;">
    <form method="post" action="informationBoard">
        <input type="hidden" class="idC" name="id" />
        <table id="evalRemindTable">
            <tr>
                <td>Course Lector</td>
                <td class="lect"> </p>
                </td>
            </tr>
            <tr>
                <td>Course name</td>
                <td class="cours">

                    </td>
            </tr>
            <tr>
                <td>Course Grade</td>
                <td><input type="text" name="grade"/></td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Save"/>
                </td>
                <td>
                    <input type="button" value="Cancel"/>
                </td>
            </tr>

        </table>
    </form>
</div>


<table align="justify">
    <tr>
        <td><a href="<c:url value=""/>">My seminars</a></td>
        <td><a href="<c:url value="registrationCourse.jsp"/>">Register Course</a></td>

        <form>
            <td><br></td>
            <br>
            <td><br></td>
            <br>
            <td><br>
            <td><br></td>
            <td>Filter</td>
            <td><select>
                <option selected value="All Course">All Course</option>
                <option value="Given Courses">Given Courses</option>
                <option value="Popular Courses "> Popular Courses</option>
                <option value="Evaluation"> Evaluation</option>
            </select></td>
        </form>
    </tr>
    <form>
        <tr>
            <td><br></td>
            <td><br></td>
            <td><br></td>
            <td><br></td>
            <td><br>
            <td>
            <td>Course Category</td>
            <td>
                <select>
                    <option selected value="Project Management">Project Management</option>
                    <option value="Development">Development</option>

                </select></td>
        </tr>

    </form>


    <tr bgcolor="#b0c4de">
        <th>Lector Name</th>
        <th>Course Name</th>
        <th>Course Category</th>
        <th>Subscribed</th>
        <th>Participated</th>
        <th>Delivered Course</th>
        <th>Evaluation</th>
        <th>Actions</th>
    </tr>

    <c:forEach items="${courseList}" var="course">
        <tr>
            <td class="lector_${course.id}" > <c:out value="${course.nameLector}" escapeXml="true" /> </td>
            <td class="course_${course.id}"><c:out value="${course.nameCourse}" escapeXml="true"/></td>
            <td><c:out value="${course.category}" escapeXml="true"/></td>
            <td><c:out value="${course.numbOfSubscribers}" escapeXml="true"/></td>
            <td><c:out value="${course.numbOfAttendee}" escapeXml="true"/></td>
            <td><c:out value="${course.delivered}" escapeXml="true"/></td>
            <td><c:out value="${course.evaluation}" escapeXml="true"/></td>
            <td>

                <c:if test="${ user eq course.nameLector}">
                <form:form action="informationBoard" method="post" commandName="course">
                <c:if test="${course.delivered}">
                    <c:set var="visibleDelete" value="disabled='disabled'"/>
                </c:if>
                <input type="submit" value="Delete" <c:out value="${visibleDelete}" escapeXml="true"/>
                    <input type="hidden" type="text" name="<%=InformationBoardController.EDIT_ID%>" value="${course.id}"/>

                <input type="button" value="Evaluation Reminder" onclick="show(${course.id})"/>


                </form:form>
                </c:if>
        </tr>
    </c:forEach>
</table>

<script language="JavaScript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.0/jquery.min.js"></script>
<script>
    function show(id){
        $("#EvalRemindBlock").show();

        var nCourse=$(".lector_"+id).html();
        var lCourse=$(".course_"+id).html();
        $(".lect").text(nCourse);
        $(".cours").text(lCourse);
        $(".idC").val(id);

    }

</script>


</body>
</html>