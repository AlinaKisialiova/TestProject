/**
 * Created by akiseleva on 30.04.2015.
 */

function validate() {
    var nameCourse = document.forms["regForm"]["newCourseName"].value;
    var durCourse = document.forms["regForm"]["newCourseDuration"].value;
    if (nameCourse.length==0 ){
        document.getElementById("errName").innerHTML="*required field";
        return false;
    }
    var par_pattern=/^\d+$/;
    if (durCourse.length==0 || parseInt(durCourse) < 1  ||  !(par_pattern.test(durCourse))){

        document.getElementById("errDuration").innerHTML="*incorrectly field";
        return false;
    }

}

function val_ev() {
    var grade = $(".grade").val();
    var par_pattern = /^\d+$/;
    if (grade.length == 0 || parseInt(grade) < 1 || parseInt(grade) > 10 || !(par_pattern.test(grade))) {
        $(".errGrade").html("*incorrectly field");
        return false;
    }
}

function filter(phrase) {

    var words = phrase.value.toLowerCase().split(" ");
    var table = document.getElementById('tableCourse');
    if (words.indexOf("all") < 0) {

        for (var r = 2; r < table.rows.length; r++) {
                     var cellsV = table.rows[r].cells[2].innerHTML.replace(/<[^>]+>/g, "");

            var displayStyle = 'none';
            for (var i = 0; i < words.length; i++) {
                if (cellsV.toLowerCase().indexOf(words[i]) >= 0)
                    displayStyle = '';
                else {
                    displayStyle = 'none';
                    break;
                }
            }
            table.rows[r].style.display = displayStyle;
        }
    }
    else {
        for (var r = 2; r < table.rows.length; r++)
            table.rows[r].style.display = '';

    }

}


function show(id, element) {
    if (element == "#EvalRemindBlock") {
        $(element).show();
        var lCourse = $(".lector_" + id).html();
        var nCourse = $(".course_" + id).html();
        var gCourse = $(".hiddenEval_" + id).val();

        $(".lect").text(lCourse);
        $(".cours").text(nCourse);
        $(".grade").val(gCourse);
        $(".idC").val(id);
        $(".fieldForSubmit").val("EVAL_REM");
    }
    if (element == "#SubscOnCourse")
        $(element).show();
}

function hide(element) {
    if (element == "#EvalRemindBlock")
        $(element).hide();
}

function setAction(action) {
    $(".fieldForSubmit").val(action);
    $(".idC").val(id);
}

function setId(id) {
    $(".id_c").val(id);
    return id;
}


