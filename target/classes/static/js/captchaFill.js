$(document).ready(function() {
    $('form').submit(function(event) {
        var recaptchaResponse = grecaptcha.getResponse();
        $('#recaptchaResponse').val(recaptchaResponse);
    });
});