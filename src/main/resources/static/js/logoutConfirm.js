document.getElementById("logoutButton").addEventListener("click", function() {
    var confirmation = confirm("Do you really want to logout");
    if (confirmation) {
        window.location.href = "/logout";

    } else {

    }
});