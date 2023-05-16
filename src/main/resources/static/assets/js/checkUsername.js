$(document).ready(function() {
    $('#userName').on('input', function() {
        var username = $(this).val();


        $.ajax({
            url: 'api/v1/registration/checkUsername',
            type: 'POST',
            data: { username: username },
            success: function(response) {
                if (response === false) {
                    $('#userName').css('background-color', 'rgba(255, 0, 0, 0.1)');
                    $('#registerButton').prop('disabled', true);
                } else {
                    $('#userName').css('background-color', 'rgba(0, 255, 0, 0.1)');
                    $('#registerButton').prop('disabled', false);
                }
            }
        });
    });
});