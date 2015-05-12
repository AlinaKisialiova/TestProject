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
                                    <option value="${course.id}"> ${course.nameCourse}</option>
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


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min3.0.2.js"/>"></script>
<script>

    function setId(id) {
        $(".id_c").val(id);
        return id;
    }
    function setAction(action) {
        $(".fieldForSubmit").val(action);

    }
</script>