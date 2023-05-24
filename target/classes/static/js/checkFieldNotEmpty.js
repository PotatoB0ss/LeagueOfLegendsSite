document.getElementById('continueButton').addEventListener('click', function(event) {
    event.preventDefault(); // Предотвращаем отправку формы

    var email = document.getElementById('email').value;
    var forText = document.getElementById('textik');
    if (email.trim() === '') {
        alert('Email field cannot be empty');
        return;
    }

    var url = 'api/v1/recovery/reset?email=' + encodeURIComponent(email);
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            forText.textContent = xhr.responseText;
            if(xhr.responseText === "A link to change your password has been sent to your email!"){
                $('#textik').css('background-color', 'rgba(0, 255, 0, 0.1)');
            } else {
                $('#textik').css('background-color', 'rgba(255, 0, 0, 0.1)');
            }

        }
    };

    xhr.send();
});