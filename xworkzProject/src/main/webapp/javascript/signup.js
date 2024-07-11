let getFields=  {
"firstName":false,
"lastName":false,
"phone":false,
"email":false,
"confirm":false,

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

function setFirstName() {
    let name = document.getElementById("firstName");
    let error = document.getElementById("fnameError");
    // Regular expression to match only alphabetic characters and spaces
    let regex = /^[A-Za-z\s]+$/;

    if (name.value.trim() === "" || name.value.length < 3 || name.value.length > 30 || !regex.test(name.value)) {
        error.innerHTML = "Please enter a valid First Name (3-30 characters, only alphabets and spaces)";
        error.style.color = 'red';
        getFields["firstName"] = false;
    } else {
        getFields["firstName"] = true;
        error.innerHTML = "";
    }
    validate();
}


function setLastName() {
    let name = document.getElementById("lastName");
    let error = document.getElementById("lnameError");
    // Regular expression to match only alphabetic characters and spaces
    let regex = /^[A-Za-z\s]+$/;

    if (name.value.trim() === "" || name.value.length < 3 || name.value.length > 30 || !regex.test(name.value)) {
        error.innerHTML = "Please enter a valid Last name (3-30 characters, only alphabets and spaces)";
        error.style.color = 'red';
        getFields["lastName"] = false;
    } else {
        getFields["lastName"] = true;
        error.innerHTML = "";
    }
    validate();
}

function validateEmail() {
    let email = document.getElementById("email").value.trim();
    let error = document.getElementById("emailError");

    let xhr = new XMLHttpRequest();
    xhr.open("GET", `http://localhost:8080/xworkzProject/validateEmail?email=${encodeURIComponent(email)}`, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                let responseText = xhr.responseText.trim();
                if (responseText !== "") {
                    error.innerHTML = responseText;
                    error.style.color = 'red';
                    getFields["email"] = false;
                } else {
                    error.innerHTML = "";
                    getFields["email"] = true;
                }
                validate();
            } else {
                console.error('Error validating email:', xhr.statusText);
            }
        }
    };
    xhr.send();
}


function validatePhone() {
    let phone = document.getElementById("phone").value.trim();
    let error = document.getElementById("phoneError");

    let xhr = new XMLHttpRequest();
    xhr.open("GET", `http://localhost:8080/xworkzProject/validatePhone?phone=${encodeURIComponent(phone)}`, true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                let phoneExists = xhr.responseText.trim();
                if (phoneExists) {
                    error.innerHTML = "Phone number is already registered. Please use a different phone number.";
                    error.style.color = 'red';
                    getFields["phone"] = false;
                } else {
                    error.innerHTML = "";
                    getFields["phone"] = true;
                }
                validate();
            } else {
                console.error('Error validating phone number:', xhr.statusText);
            }
        }
    };

    xhr.send();
}



function setConfirm() {
    let check1 = document.getElementById("confirm");
    let error = document.getElementById("confirmError");

    if (!check1.checked ) {
        error.innerHTML = "Please Accept the Agreement.";
        error.style.color = 'red';
        getFields["confirm"] = false;
    } else {
        error.innerHTML = "";
        getFields["confirm"] = true;
    }
    validate();
}


