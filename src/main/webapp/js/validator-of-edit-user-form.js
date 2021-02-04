function validatePassword() {
    let password = document.getElementById("password");
    let password_confirm = document.getElementById("password_confirm");

    if (password.value === password_confirm.value) {
        password_confirm.setCustomValidity('');
        password_confirm.reportValidity();
        return true;
    } else {
        password_confirm.setCustomValidity("Passwords don't match");
        password_confirm.reportValidity();
        return false;
    }
}