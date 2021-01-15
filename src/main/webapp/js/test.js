function validateTest() {
    var x = document.forms["myForm"]["fname"].value;
    if (x == "") {
        alert("Необходимо ввести имя");
        return false;
    }
}

function deleteConfirmation(f) {
    if (confirm("Вы уверены, что хотите удалить?"))
        f.submit();
}