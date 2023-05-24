document.getElementById("logoutButton").addEventListener("click", function() {
    var confirmation = confirm("Вы точно хотите выйти из аккаунта?");
    if (confirmation) {
        window.location.href = "/logout";

    } else {
        // Действия при отмене выхода
    }
});