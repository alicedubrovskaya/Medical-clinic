function validateVacation() {
    let start = document.getElementById("start");
    let end = document.getElementById("end");

    if (start.value <= end.value) {
        end.setCustomValidity('');
        end.reportValidity();
        return true;
    } else {
        end.setCustomValidity("Date is earlier than starting date");
        end.reportValidity();
        return false;
    }
}