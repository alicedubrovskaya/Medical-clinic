var startMessage = null;
var dropClicked = false;
var currentMenu = null;

window.onload = function () {
    if (startMessage !== null) {
        show(startMessage);
    }
    var menus = document.getElementById("header").getElementsByClassName("drop");
    for (var index = 0, size = menus.length; index < size; index++) {
        menus[index].style.display = "none";
        menus[index].parentNode.getElementsByTagName("A")[0].onclick = dropMenu;
    }
}

window.onclick = function () {
    if (currentMenu != null) {
        if (!dropClicked) {
            currentMenu.style.display = "none";
            currentMenu = null;
        } else {
            dropClicked = false;
        }
    }
}

function show(message, action) {
    showMessage(message, [{
        caption: "Close",
        handler: (action !== undefined ? action : function () {
        })
    }]);
}

function confirmation(form, message) {
    showMessage(message, [{
        caption: "Удалить",
        handler: function () {
            form.submit();
        }
    }, {
        caption: "Отменить",
        handler: function () {
        }
    }]);
    return false;
}

function submitFormById(id) {
    var form = document.getElementById(id);
    var isSubmit = true;
    if (form.onsubmit != null) {
        isSubmit = form.onsubmit();
    }
    if (isSubmit) {
        form.submit();
    }
    return false;
}

function showMessage(message, buttons) {
    var body = document.getElementsByTagName("body")[0];
    var messageElement = document.createElement("div");
    messageElement.id = "confirm-message";
    var messageContent = document.createElement("div");
    var messageText = document.createElement("p");
    messageText.innerHTML = message;
    messageContent.appendChild(messageText);
    var buttonsElement = document.createElement("form");
    for (var index = 0, size = buttons.length; index < size; index++) {
        var button = document.createElement("button");
        button.type = "button";
        button.handler = buttons[index];
        button.onclick = function () {
            body.removeChild(messageElement);
            this.handler.handler();
        }
        button.appendChild(document.createTextNode(buttons[index].caption));
        buttonsElement.appendChild(button);
    }
    messageContent.appendChild(buttonsElement);
    messageElement.appendChild(messageContent);
    body.insertBefore(messageElement, body.firstChild);
}

function dropMenu(e) {
    var menu = e.currentTarget.parentNode.getElementsByClassName("drop")[0];
    if (menu.style.display === "none") {
        if (currentMenu != null && menu != currentMenu) {
            currentMenu.style.display = "none";
        }
        menu.style.display = "block";
        currentMenu = menu;
        dropClicked = true;
    }
    return false;
}

function getCurrentDate() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();

    if (dd < 10) {
        dd = '0' + dd
    }

    if (mm < 10) {
        mm = '0' + mm
    }
    today = yyyy + '-' + mm + '-' + dd;
    return today;
}

function setCurrentDate() {
    var today = getCurrentDate();
    document.getElementById('date').value = today;
}

function setStartDate() {
    var today = getCurrentDate();
    document.getElementById('start').value = today;
}

