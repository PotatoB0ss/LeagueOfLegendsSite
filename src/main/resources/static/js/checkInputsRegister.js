var registerButton = document.getElementById("registerButton");

registerButton.addEventListener("click", function(event) {
    event.preventDefault();

    var userLogin = document.getElementById("userName");
    var userEmail = document.getElementById("InputEmail");
    var userPassword = document.getElementById("passwordInput");
    var userPasswordConfirm = document.getElementById("confirmPasswordInput")

    var login = userLogin.value;
    var email = userEmail.value;
    var password = userPassword.value;
    var passwordConfirm = userPasswordConfirm.value;
    var recaptchaResponse = grecaptcha.getResponse();

    if (login.trim() === "" || password.trim() === "" || email.trim() === "" || passwordConfirm.trim() === "") {
        alert("Enter all inputs");
    } else if(recaptchaResponse === ""){
        alert('Please complete the captcha');
    }
    else {
        $('#recaptchaResponse').val(recaptchaResponse);
        userLogin.form.submit();
    }
});