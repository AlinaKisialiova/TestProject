<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: akiseleva
  Date: 07.04.2015
  Time: 7:52
  To change this template use File | Settings | File Templates.
--%>

<div class="row">

    <div class="col-md-8 col-md-offset-4">
        <table>
            <tbody>
            <tr>
                <td>
                    <b>Course Category: </b></td>
                <td> ${checkCourse.category}</td>
            </tr>
            <tr>
                <td>
                    <b>Course Name: </b></td>
                <td><a href="<c:url value="/courseDetails/${checkCourse.id}" context="/project/"/>">
                    ${checkCourse.nameCourse}
                </a>
                </td>
            </tr>
            <tr>
                <td><br></td>
            </tr>
            <tr>
                <td colspan="2">
                    <b>Participants:</b>
                </td>
            </tr>

            <c:forEach items="${checkCourse.attenders}" var="attend">
                <tr>
                    <td><c:out value="${attend.name}" escapeXml="true"/></td>
                    <td><c:out value="${attend.email}" escapeXml="true"/></td>
                </tr>
            </c:forEach>

            <tr>

                <form action="<c:url value="/evaluationReminder/${checkCourse.id}" context="/project/"/>" method="post">
                    <td>
                        <input type="submit" value="Send notification" class="btn btn-primary"/>
                    </td>
                    <td>
                        <a href="<c:url value="/informationBoard" context="/project"/>">
                           <input type="button" value="Cancel"
                                   class="btn"></a>
                    </td>
                </form>
            </tr>
            </tbody>

        </table>
    </div>
</div>
