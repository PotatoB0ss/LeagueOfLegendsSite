var buttons = document.querySelectorAll('button[type="button"]');
for (var i = 0; i < buttons.length; i++) {
    var button = buttons[i];

    // Проверка, что кнопка не имеет идентификатора "searchPlayerButton"
    if (button.id !== "searchPlayerButton") {
        button.addEventListener('click', function() {
            highlightButton(this);
        });
    }
}

function highlightButton(button) {
    // Удаление класса button-highlight у всех кнопок
    var buttons = document.querySelectorAll('button[type="button"]');
    for (var i = 0; i < buttons.length; i++) {
        buttons[i].classList.remove('button-highlight');
    }

    // Добавление класса button-highlight для выбранной кнопки
    button.classList.add('button-highlight');
}