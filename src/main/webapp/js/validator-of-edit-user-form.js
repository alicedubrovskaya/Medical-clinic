function validatePassword(form) {
    if (form["password"].value == form["password_confirm"].value) {
        form["password_confirm"].setCustomValidity('');
        form["password_confirm"].reportValidity();
        return true;
    } else {
        form["password_confirm"].setCustomValidity("Passwords don't match");
        form["password_confirm"].reportValidity();
        return false;
    }
}