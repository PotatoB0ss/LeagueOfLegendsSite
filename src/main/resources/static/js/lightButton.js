var buttons = document.querySelectorAll('button[type="button"]');
for (var i = 0; i < buttons.length; i++) {
    var button = buttons[i];

    if (button.id !== "searchPlayerButton") {
        button.addEventListener('click', function() {
            highlightButton(this);
        });
    }
}

function highlightButton(button) {
    var buttons = document.querySelectorAll('button[type="button"]');
    for (var i = 0; i < buttons.length; i++) {
        buttons[i].classList.remove('button-highlight');
    }

    button.classList.add('button-highlight');
}