let getFields=  {
"captcha":false
}

function validate(){

let flag=false;

for(let[key,value] of Object.entries(getFields)){
if(value===false){

flag=true;
break;
}
}
if(!flag){
document.getElementById("btn").removeAttribute("disabled");
}else{
document.getElementById("btn").setAttribute("disabled","");
}
}




function generateCaptcha() {
    let characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    captchaCode = '';
    for (let i = 0; i < 6; i++) {
        captchaCode += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    document.getElementById("captchaPreview").innerText = captchaCode;
    getFields["captcha"] = false; // Reset captcha validation
    document.getElementById("captchaError").innerText = ""; // Clear any previous error message
    validate();
}

function setCaptcha() {
    let captchaInput = document.getElementById("captcha").value.trim();
    let error = document.getElementById("captchaError");

    if (captchaInput === captchaCode) {
        getFields["captcha"] = true;
        error.innerHTML = "";
    } else {
        error.innerHTML = "Please enter the correct captcha.";
        error.style.color = 'red';
        getFields["captcha"] = false;
    }
    validate();
}

document.addEventListener("DOMContentLoaded", function() {
    generateCaptcha();

    document.getElementById("captcha").addEventListener("input", setCaptcha);

    document.getElementById("signInForm").addEventListener("submit", function(event) {
        setCaptcha(); // Validate captcha on form submit

        if (!getFields["captcha"]) {
            event.preventDefault(); // Prevent form submission if captcha is incorrect
        }
    });
});