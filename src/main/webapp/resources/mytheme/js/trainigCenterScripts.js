/**
 * Created by akiseleva on 30.04.2015.
 */

function filter(phrase) {
    alert(phrase);
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