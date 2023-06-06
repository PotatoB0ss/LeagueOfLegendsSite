var loginButton = document.getElementById("loginButton");

loginButton.addEventListener("click", function(event) {
    event.preventDefault();

    var userLogin = document.getElementById("exampleInputEmail1");
    var userPassword = document.getElementById("exampleInputPassword1");

    var login = userLogin.value;
    var password = userPassword.value;
    var recaptchaResponse = grecaptcha.getResponse();

    if (login.trim() === "" || password.trim() === "") {
        alert("Enter login and password");
    } else if(recaptchaResponse === ""){
        alert('Please complete the captcha');
    }
    else {

        $('#recaptchaResponse').val(recaptchaResponse);
        userLogin.form.submit();
    }
});
