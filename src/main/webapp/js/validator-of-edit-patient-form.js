function validateEditPatient(form) {
    return validateForm(form, [{
        id: "surname",
        message: "Поле «Фамилия» не заполнено",
        checker: checkNotEmpty
    }, {
        id: "name",
        message: "Поле «Имя» не заполнено",
        checker: checkNotEmpty
    }, {
        id: "email",
        message: "Поле «Почта» некорректно",
        checker: checkEmail
    }, {
        id: "phoneNumber",
        message: "Поле «Номер телефона» некорректно",
        checker: checkWorkingShift
    }]);
}

// TODO
function checkEmail(value){
    return checkRegexp(value, "^[A-Z0-9._%+-]+@[A-Z0-9-]+.+.[A-Z]{2,4}$/i");
}
// TODO
function checkPhoneNumber(value){
    return checkRegexp(value, "");
}