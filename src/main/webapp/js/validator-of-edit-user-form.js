function validateEditUser(form) {
	return validateForm(form, [{
		id: "login",
		message: "Логин должен содержать от 3 до 16 символов, можно использовать дефис, нижнее подчеркивание, маленькие " +
			"латинские буквы, цифры",
		checker: checkLogin
	},{
		id: "password",
		message: "Пароль должен содержать от 6 до 16 символов",
		checker: checkPassword
	}]);
}

function checkLogin(value) {
	return checkRegexp(value, "^[a-z0-9_-]{3,16}$");
}

function checkPassword(value) {
	return checkRegexp(value, "^.{6,16}$");
}