const urlParams = new URLSearchParams(window.location.search);
const errorParam = urlParams.get('error');

document.getElementById("loginError").innerText = errorParam;
