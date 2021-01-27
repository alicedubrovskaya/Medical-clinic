function validateEditUser(form) {
    var isValid = validateForm(form, [{
        id: "login",
        message: "Логин должен содержать от 3 до 16 символов, можно использовать дефис, нижнее подчеркивание, маленькие " +
            "латинские буквы, цифры",
        checker: checkLogin
    }, {
        id: "password",
        message: "Пароль должен содержать от 6 до 16 символов",
        checker: checkPassword
    }]);
    if (!isValid)
        return false;
    else {
        var passwordMatch = checkPasswords(form["password"].value, form["password_confirm"].value);
        if (!passwordMatch) {
            errorMessage("password", "Пароли не совпадают");
            return false;
        }
    }
    return true;
}

function checkLogin(value) {
    return checkRegexp(value, "^[a-z0-9_-]{3,16}$");
}

function checkPassword(value) {
    return checkRegexp(value, "^.{6,16}$");
}

function checkPasswords(value1, value2) {
    return value1 === value2;
}