var modal = document.getElementById("myModal");
var modalContent = document.querySelector(".modal-content");
var openModalButtons = document.querySelectorAll(".btnBuyProduct");


// Открытие модального окна при клике на кнопку с классом btnBuyProduct
openModalButtons.forEach(function(button) {
    button.addEventListener("click", function () {
        var buttonId = button.getAttribute('data-button-id'); // Получение значения скрытого параметра
        loadModalContent(buttonId);
    });
});

// Закрытие модального окна при клике вне его области
window.addEventListener("click", function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
});

// Функция для загрузки содержимого модального окна
function loadModalContent(buttonId) {
    var url = "/mb?productNumber=" + buttonId; // Замените на путь к файлу с содержимым модального окна

    $.ajax({
        url: url,
        method: "GET",
        success: function (data) {
            modalContent.innerHTML = data; // Загруженный контент помещается внутрь модального окна
            modal.style.display = "block"; // Открытие модального окна
        },
        error: function (error) {
            console.log("Ошибка загрузки контента модального окна:", error);
        }
    });
}
