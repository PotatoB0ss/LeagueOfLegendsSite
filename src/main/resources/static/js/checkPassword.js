$(document).ready(function() {
    $('#passwordInput, #confirmPasswordInput').on('input', function() {
        var password = $('#passwordInput').val();
        var passwordConfirm = $('#confirmPasswordInput').val();

        $.ajax({
            url: 'api/v1/registration/checkPassword',
            type: 'POST',
            data: { password: password },
            success: function(response) {
                if (response === false) {
                    $('#passwordInput').css('background-color', 'rgba(255, 0, 0, 0.1)');
                    $('#registerButton').prop('disabled', true);
                } else {
                    $('#passwordInput').css('background-color', 'rgba(0, 255, 0, 0.1)');
                    $('#registerButton').prop('disabled', false);
                }

                if (password !== passwordConfirm) {
                    $('#confirmPasswordInput').css('background-color', 'rgba(255, 0, 0, 0.1)');
                    $('#registerButton').prop('disabled', true);
                } else {
                    $('#confirmPasswordInput').css('background-color', 'rgba(0, 255, 0, 0.1)');
                    $('#registerButton').prop('disabled', false);
                }
            }
        });
    });
});