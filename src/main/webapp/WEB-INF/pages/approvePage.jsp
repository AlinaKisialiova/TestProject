<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
    <div class="col-md-8 col-md-offset-4">
        <div id='approve' class="table">
            <form action="approvePage" method="post">
                <input type="hidden" class="idC" name="id"/>
                <table id="approveTable">
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
                            <select multiple="multiple" size="10" name="selectCourse" id="selectCourse" onchange="setId(this)">
                                <c:forEach var="course" items="${coursesForApprove}">
                                    <option value="${course.id}"> <c:out value="${course.nameCourse}" escapeXml="true"/> </option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr><td> <br> </td></tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" class="btn btn-primary btn-lg btn-block" onclick="setAction('APPROVE')" value="Approve"/>
                            <input type="hidden" name="id_c" class="id_c"/>
                            <input type="hidden" name="fieldForSubmit" class="fieldForSubmit"/>
                        </td>

                    </tr>

                </table>
            </form>
        </div>
    </div>
</div>


