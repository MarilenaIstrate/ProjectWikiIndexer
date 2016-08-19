/**
 * Created by mistrate on 8/18/2016.
 */
function validateForm() {
    if (document.mainForm.articleName.value == "" && document.mainForm.fileName.value == "") {
        document.getElementById('errors').style.color = '#F0FFFF';
        document.getElementById('errors').style.fontSize = 'large';
        document.getElementById('errors').innerHTML="*Please enter a title or upload a file";
        return false
        }
}
