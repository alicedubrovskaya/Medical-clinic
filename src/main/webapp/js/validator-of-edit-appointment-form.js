function validateDateAndDays() {
    let days = document.getElementById("days");
    let date = document.getElementById("date");

    if (date.value <= getCurrentDatePlusDays(days.value) && (date.value >= getCurrentDate())) {
        date.setCustomValidity('');
        date.reportValidity();
        return true;
    } else {
        date.setCustomValidity("Date cannot be earlier than current and later than 7 days after current");
        date.reportValidity();
        return false;
    }
}

function validateDays() {
    let days = document.getElementById("days");

    if (days.value < 8) {
        days.setCustomValidity('');
        days.reportValidity();
        return true;
    } else {
        days.setCustomValidity("Count of days should be less than 8");
        days.reportValidity();
        return false;
    }
}