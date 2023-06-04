var purchaseButton = document.querySelector("[data-submit-button]");

purchaseButton.addEventListener("click", function(event) {
    event.preventDefault();

    var userNameInput = document.getElementById("userName");
    var userEmailInput = document.getElementById("userEmail");

    var userName = userNameInput.value;
    var userEmail = userEmailInput.value;

    if (userName.trim() === "" || userEmail.trim() === "") {
        alert("Enter name and email");
        return;
    }

    var emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
    if (!emailRegex.test(userEmail)) {
        alert("Email address is incorrect");
        return;
    }

    var productPriceElement = document.getElementById("productPrice");

    var productPrice = parseFloat(productPriceElement.innerText.replace("$", ""));

    if (productPrice > 1.40) {
        document.getElementById("purchaseForm").submit();
    } else {
        alert("Minimum purchase amount is 1.40$");
    }
});
