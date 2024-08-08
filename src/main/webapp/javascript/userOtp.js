let getFields = {
    "otp": false
}

function validate() {
    let flag = false;
    for (let [key, value] of Object.entries(getFields)) {
        if (value === false) {
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

function validateOTP() {
    let otp = document.getElementById("otp").value.trim();
    let error = document.getElementById("otpError");
    let complaintId = document.getElementById("complaintId").value; // Get the complaint ID from the hidden input

    let xhr = new XMLHttpRequest();
    xhr.open("GET", `http://localhost:8080/xworkzProject/validateOtp?otp=${encodeURIComponent(otp)}&complaintId=${encodeURIComponent(complaintId)}`, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                let responseText = xhr.responseText.trim();
                if (responseText !== "") {
                    error.innerHTML = responseText;
                    error.style.color = 'red';
                    getFields["otp"] = false;
                } else {
                    error.innerHTML = "";
                    getFields["otp"] = true;
                }
                validate();
            } else {
                console.error('Error validating OTP:', xhr.statusText);
            }
        }
    };
    xhr.send();
}


