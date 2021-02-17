function validateDays() {
    let days = document.getElementById("days");
    let date = document.getElementById("date");

    if (days.value < 8 & date.value < getCurrentDatePlusDays(days.value) && (date.value >= getCurrentDate())) {
        days.setCustomValidity('');
        days.reportValidity();
        return true;
    } else {
        days.setCustomValidity("Count of days should be less than 8 and date cannot be earlier than current");
        days.reportValidity();
        return false;
    }
}