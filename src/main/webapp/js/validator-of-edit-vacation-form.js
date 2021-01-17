function validateEditVacation(form) {
	return validateForm(form, [{
		id: "surname",
		message: "Поле «Фамилия» не заполнено",
		checker: checkNotEmpty
	}, {
		id: "name",
		message: "Поле «Имя» не заполнено",
		checker: checkNotEmpty
	},{
		id:"vacation-start",
		message: "Поле «Дата начала» должно быть задано в формате dd.mm.yyyy",
		checker:checkDate
	},{
		id:"vacation-end",
		message: "Поле «Дата окончания» должно быть задано в формате dd.mm.yyyy",
		checker: checkDate
	}]);
}
