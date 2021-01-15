const workingShift = ['Первая', 'Вторая'];

function checkEmptyOrPositiveInteger(value) {
	return !checkNotEmpty(value) || checkPositiveInteger(value);
}

function checkPositiveInteger(value) {
	return checkInteger(value) && value > 0;
}

function checkInteger(value) {
	return checkNumber(value) && value == Math.round(value);
}

function checkNumber(value) {
	return !isNaN(parseFloat(value)) && isFinite(value);
}

function checkRegexp(value, regexp) {
	return new RegExp(regexp).test(value);
}

function checkNotEmpty(value) {
	return value.length != 0;
}

function validateForm(form, data) {
	for(i in data) {
		if(!data[i].checker(form[data[i].id].value)) {
			errorMessage(form[data[i].id], data[i].message);
			return false;
		}
	}
	return true;
}

function checkWorkingShift(value){
	return workingShift.includes(value)
}

function errorMessage(element, message) {
	show(message, function() {element.focus()});
}
