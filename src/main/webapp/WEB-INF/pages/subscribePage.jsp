<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: akiseleva
  Date: 06.05.2015
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-8 col-md-offset-4">
<div id='SubscOnCourse' class="table">
    <form action="subscribePage" method="post">
        <input type="hidden" class="idC" name="id"/>
        <table id="subscCourse">
            <tr>
                <td><b> Select Category </b></td>
                <td>
                    <select name="selectCategory">
                        <option value="All">All</option>
                        <option value="Project Management">Project Management</option>
                        <option value="Development">Development</option>
                    </select>
                </td>
                <td><input type="submit" value="Ok" onclick="window.location.href=window.location.href"/></td>
            </tr>
            <tr><td><br></td></tr>
         <tr>  <td><b>Select Course </b></td></tr>
            <tr>
<td> </td>
                <td>
                    <select multiple="multiple" size="10" name="selectCourse" id="selectCourse" onchange="setNameButton(this)">
                        <c:forEach var="courses" items="${nameCourses}">
                      <option value="${courses.id}"
                              <c:choose>
                             <c:when test="${coursesForUser.contains(courses)}">
                                 style="color:#ee5f5b"
                                    </c:when>
                            <c:otherwise>
                                style="color: #0e90d2"
                            </c:otherwise>
                              </c:choose>
                             >

                            <c:out value="${courses.nameCourse}" escapeXml="true"/> </option>
                        </c:forEach>
                    </select>
</td>
            </tr>
            <tr><td> <br> </td></tr>
            <tr>
                <td colspan="2">
                    <input type="submit" onclick="setAction('ADD_IN_SUBSCR') " value="Subscribe" class="btn btn-primary btn-lg btn-block" />
                    <input type="submit" onclick="setAction('REMOVE_FROM_SUBSCR')" value="Delete" class="btn btn-danger btn-lg btn-block" />
                    <input type="hidden" name="fieldForSubmit" class="fieldForSubmit"/>
                </td>

            </tr>

        </table>
    </form>
</div>
        </div>
    </div>


