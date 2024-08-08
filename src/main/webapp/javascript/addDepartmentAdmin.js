let getFields = {
    "departmentId": false,
    "name": false,
    "email": false,
    "phone": false
};

function validate() {
    let flag = false;
    for (let value of Object.values(getFields)) {
        if (!value) {
            flag = true;
            break;
        }
    }
    if (!flag) {
        document.getElementById("btn").removeAttribute("disabled");
    } else {
        document.getElementById("btn").setAttribute("disabled", "");
    }
}

function setDepartmentId() {
    let departmentId = document.getElementById("departmentId");
    let error = document.getElementById("departmentIdError");
    if (departmentId.value != "0") {
        error.innerHTML = "";
        getFields["departmentId"] = true;
    } else {
        error.innerHTML = "Please select a Department";
        error.style.color = 'red';
        getFields["departmentId"] = false;
    }
    validate();
}

function setName() {
    let name = document.getElementById("name");
    let error = document.getElementById("nameError");
    let regex = /^[A-Za-z\s]+$/;
    if (name.value.trim() === "" || name.value.length < 3 || name.value.length > 30 || !regex.test(name.value)) {
        error.innerHTML = "Please enter a valid name (3-30 characters, only alphabets and spaces)";
        error.style.color = 'red';
        getFields["name"] = false;
    } else {
        getFields["name"] = true;
        error.innerHTML = "";
    }
    validate();
}



function emailValidation() {
    let email = document.getElementById("email").value.trim();
    let error = document.getElementById("emailError");

    if (email === "") {
        error.innerHTML = "Please enter an email.";
        error.style.color = 'red';
        getFields["email"] = false;
        validate();
        return;
    }

    let xhr = new XMLHttpRequest();
    xhr.open("GET", `http://localhost:8080/xworkzProject/validateAdminEmail?email=${encodeURIComponent(email)}`, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                let responseText = xhr.responseText.trim();
                if (responseText !== "") {
                    error.innerHTML = responseText; // Display error message in emailError span
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

function phoneValidation() {
    let phone = document.getElementById("phone").value.trim();
    let error = document.getElementById("phoneError");

    // Validate phone number format (e.g., 10 digits for simplicity)
    let phoneRegex = /^[0-9]{10}$/;
    if (!phoneRegex.test(phone)) {
        error.innerHTML = "Please enter a valid 10-digit phone number.";
        error.style.color = 'red';
        getFields["phone"] = false;
        validate();
        return;
    }

    let xhr = new XMLHttpRequest();
    xhr.open("GET", `http://localhost:8080/xworkzProject/validateAdminPhone?phone=${encodeURIComponent(phone)}`, true);

    xhr.onreadystatechange = function() {
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
