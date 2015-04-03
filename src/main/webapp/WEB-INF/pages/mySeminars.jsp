<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: akiseleva
  Date: 02.04.2015
  Time: 9:54
  To change this template use File | Settings | File Templates.
--%>

<div class="row">
    <div class="span12"> <h1>My Seminars</h1></div>

    <table align="justify" >

        <tr>
            <td colspan="3"><a href="informationBoard"> Seminars Information Board </a></td>
            <td colspan="3"><a href="" onclick="subscCours()">Subscribe for the Course</a></td>

        </tr>


        <form action="mySeminars" method="post">
            <tr>
                <td><br></td>
                <td><br></td>
                <td><br></td>
                <td><br></td>
                <td><br>
                <td>
                <td>Course Category</td>
                <td>
                    <select name="selectCategory" class="btn dropdown-toggle">
                        <option value="All">All</option>
                        <option  value="Project Management">Project Management</option>
                        <option value="Development">Development</option>
                    </select>
                    <input type="submit" value="ok" class="btn"/>

                </td>
            </tr>


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

                    <td class="lector_${course.id}"><c:out value="${course.lector.name}" escapeXml="true"/></td>
                    <td >
                        <a href="<c:out value="courseDetails/${course.id}" escapeXml="true"/>">
           <span class="course_${course.id}">
               <c:out  value="${course.nameCourse}" escapeXml="true"/></span>
                        </a>
                    </td>
                    <td><c:out value="${course.category}" escapeXml="true"/></td>
                    <td><c:out value="${course.subscribers.size()}" escapeXml="true"/></td>
                    <td>
                        <a href="<c:out value="participantsList/${course.id}" escapeXml="true"/>">
                        <c:out value="${course.attenders.size()}" escapeXml="true"/>
                        </a>
                    </td>
                    <td><c:out value="${course.delivered}" escapeXml="true"/></td>
                    <td class="grade_${course.id}"><c:out value="${course.evaluation}" escapeXml="true"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${attCourseOfUser.contains(course)}">
                                <a href=""> + </a>
                            </c:when>
                            <c:otherwise>
                                <a href=""> - </a>
                            </c:otherwise>
                        </c:choose>



                        </td>
                            <input type="hidden" name="fieldForSubmit" class="fieldForSubmit"/>
                            <input type="hidden" class="idC" name="id"/>



</tr>


</c:forEach>
</table>

</div>