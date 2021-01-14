function confirmation(form, message) {
    showMessage(message, [{
        caption: "Удалить",
        handler: function() {
            form.submit();
        }
    }, {
        caption: "Отменить",
        handler: function() {}
    }]);
    return false;
}

function submitFormById(id) {
    var form = document.getElementById(id);
    var isSubmit = true;
    if(form.onsubmit != null) {
        isSubmit = form.onsubmit();
    }
    if(isSubmit) {
        form.submit();
    }
    return false;
}

function showMessage(message, buttons) {
    var body = document.getElementsByTagName("BODY")[0];
    var messageElement = document.createElement("DIV");
    messageElement.id = "confirm-message";
    var messageContent = document.createElement("DIV");
    var messageText = document.createElement("P");
    messageText.innerHTML = message;
    messageContent.appendChild(messageText);
    var buttonsElement = document.createElement("FORM");
    for(var index = 0, size = buttons.length; index < size; index++) {
        var button = document.createElement("BUTTON");
        button.type = "BUTTON";
        button.handler = buttons[index];
        button.onclick = function() {
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
