function validateVacation(form) {
    if (form["start"].value <= form["end"].value) {
        form["end"].setCustomValidity('');
        form["end"].reportValidity();
        return true;
    } else {
        form["end"].setCustomValidity("Date is earlier than starting date");
        form["end"].reportValidity();
        return false;
    }
}