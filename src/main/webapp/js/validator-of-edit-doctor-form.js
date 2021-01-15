function validateEditDoctor(form) {
    return validateForm(form, [{
        id: "surname",
        message: "Поле «Фамилия» не заполнено",
        checker: checkNotEmpty
    }, {
        id: "name",
        message: "Поле «Имя» не заполнено",
        checker: checkNotEmpty
    }, {
        id: "specialization",
        message: "Поле «Специализация» не заполнено",
        checker: checkNotEmpty
    }, {
        id: "workingShift",
        message: "Поле «Рабочая смена» некорректно",
        checker: checkWorkingShift
    }]);
}

