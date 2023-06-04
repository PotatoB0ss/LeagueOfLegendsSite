var modal = document.getElementById("myModal");
var modalContent = document.querySelector(".modal-content");
var openModalButtons = document.querySelectorAll(".btnBuyProduct");





openModalButtons.forEach(function(button) {
    button.addEventListener("click", function () {
        var buttonId = button.getAttribute('data-button-id');
        loadModalContent(buttonId);
    });
});


window.addEventListener("click", function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
});


function loadModalContent(buttonId) {
    var url = "/mb?productNumber=" + buttonId;

    $.ajax({
        url: url,
        method: "GET",
        success: function (data) {
            modalContent.innerHTML = data;
            modal.style.display = "block";
            loadScript("/js/checkPrice.js");
        },
        error: function (error) {
            console.log("Ошибка загрузки контента модального окна:", error);
        }
    });
}

function loadScript(url) {
    var script = document.createElement("script");
    script.src = url;
    document.body.appendChild(script);
}