<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

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
<h1>Seminars  Information Board</h1>
Hello, <security:authentication property="principal.username" var="user" /> ${user}! <a href="<c:url value="j_spring_security_logout"/>"> Logout</a>
</br>

<sec:authorize access="hasRole('ROLE_LECTOR')">
I am know that you are lector!
</sec:authorize>

<sec:authorize access="hasRole('ROLE_PARTICIPANT')">
    I am know that you are participant!
</sec:authorize>

<sec:authorize access="hasRole('ROLE_MANAGER')">
    I am know that you are manager!
</sec:authorize>
</br>
</br>


<table align="justify">
    <tr> <td><a href="<c:url value=""/>">My seminars</a></td>
   <td><a href="<c:url value=""/>">Register Course</a></td>
    <td>
        <form> <td>Fiter</td>
           <td> <select>
                <option selected value="All Course">All Course</option>
                <option value="Given Courses">Given Courses </option>
                <option value="Popular Courses "> Popular Courses</option>
                <option value="Evaluation"> Evaluation </option>
            </select> </td>  </form>

        <td>Course Category</td>
<td>
        <select>
            <option selected value="Project Management">Project Management</option>
            <option value="Development">Development </option>

        </select> </td></tr>

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
</table>
</body>
</html>
