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
                <td><input type="submit" value="Ok"/></td>
            </tr>
            <tr><td><br></td></tr>
         <tr>  <td><b>Select Course </b></td></tr>
            <tr>
<td> </td>
                <td>
                    <select multiple="multiple" size="10" name="selectCourse" id="selectCourse">
                        <c:forEach var="courses" items="${nameCourses}">
                            <option value="${courses.id}"> ${courses.nameCourse}</option>
                        </c:forEach>
                    </select>
</td>
            </tr>
            <tr><td> <br> </td></tr>
            <tr>
                <td colspan="2">
                    <input type="submit" onclick="setAction('SUBSCRIBE')" value="Subscribe/Delete" class="btn btn-primary btn-lg btn-block" />
                    <input type="hidden" name="fieldForSubmit" class="fieldForSubmit"/>
                </td>

            </tr>

        </table>
    </form>
</div>
        </div>
    </div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min3.0.2.js"/>"></script>
<script>

    function setAction(action) {
        $(".fieldForSubmit").val(action);
        $(".idC").val(id);

    }
</script>