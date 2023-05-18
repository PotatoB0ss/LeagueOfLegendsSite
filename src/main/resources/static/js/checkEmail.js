$(document).ready(function() {
    $('#InputEmail').on('input', function() {
        var email = $(this).val();


        $.ajax({
            url: 'api/v1/registration/checkEmail',
            type: 'POST',
            data: { email: email },
            success: function(response) {
                if (response === false) {
                    $('#InputEmail').css('background-color', 'rgba(255, 0, 0, 0.1)');
                    $('#registerButton').prop('disabled', true);
                } else {
                    $('#InputEmail').css('background-color', 'rgba(0, 255, 0, 0.1)');
                    $('#registerButton').prop('disabled', false);
                }
            }
        });
    });
});