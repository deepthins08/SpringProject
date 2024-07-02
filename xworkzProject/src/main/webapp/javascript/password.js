let getFields = {
    "password": false,
    "newPassword": false,
    "confirmPassword": false
};

function validate() {
    let flag = false;

    for(let[key, value] of Object.entries(getFields)){
        if(value === false){
            flag = true;
            break;
        }
    }

    if(!flag){
        document.getElementById("reset").removeAttribute("disabled");
    } else {
        document.getElementById("reset").setAttribute("disabled","");
    }
}

function validatePassword() {
    let password = document.getElementById("password").value;
    let error = document.getElementById("passwordError");
    let regex = /^(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-])(?=.*\d)[A-Za-z\d!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]{16,}$/;

    if (!regex.test(password)) {
        error.innerHTML = "Password must be at least 16 characters long and contain at least one capital letter, one special character, and one number.";
        getFields["password"] = false;
    } else {
        error.innerHTML = "";
        getFields["password"] = true;
    }
    validate();
}

function validateNewPassword() {
    let newPassword = document.getElementById("newPassword").value;
    let error = document.getElementById("newPasswordError");
    let regex = /^(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-])(?=.*\d)[A-Za-z\d!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]{16,}$/;

    if (!regex.test(newPassword)) {
        error.innerHTML = "Password must be at least 16 characters long and contain at least one capital letter, one special character, and one number.";
        getFields["newPassword"] = false;
    } else {
        error.innerHTML = "";
        getFields["newPassword"] = true;
    }
    validate();
}

function validateConfirmPassword() {
    let newPassword = document.getElementById("newPassword").value;
    let confirmPassword = document.getElementById("confirmPassword").value;
    let error = document.getElementById("confirmPasswordError");

    if (confirmPassword !== newPassword) {
        error.innerHTML = "Passwords do not match.";
        getFields["confirmPassword"] = false;
    } else {
        error.innerHTML = "";
        getFields["confirmPassword"] = true;
    }
    validate();
}

document.getElementById("password").addEventListener
