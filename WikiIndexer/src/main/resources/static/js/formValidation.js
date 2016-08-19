/**
 * Created by mistrate on 8/18/2016.
 */
function validateForm() {
    if (document.mainForm.articleName.value == "")
        if (document.mainForm.fileName.value == "") {
            return false;
        }
}
