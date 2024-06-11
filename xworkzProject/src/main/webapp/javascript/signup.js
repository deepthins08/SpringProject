let getFields=  {
"firstName":false,
"lastName":false,
"phone":false,
"email":false,
"confirm":false
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


function setPhone(){
let cid=document.getElementById("phone");
let error=document.getElementById("phoneError");


if(cid.value.trim().length==10 && /^\d+$/.test(cid.value.trim())){
getFields["phone"]=true;
error.innerHTML=""
}
else{
error.innerHTML="Please enter a valid Mobile Number.";
error.style.color='red';
getFields["phone"]=false;
}
validate();
}
function setMail() {
      let email = document.getElementById("email").value.trim();
      let error = document.getElementById("emailError");
      let emailRegex = /^[a-zA-Z]+[a-zA-Z0-9]*@gmail\.com$/;

      if (!emailRegex.test(email)) {
          error.innerHTML = "Please enter a valid email address.";
          error.style.color = 'red';
          getFields["email"] = false;
      } else {
          error.innerHTML = ""; // Clear the error message
          getFields["email"] = true;
      }

      validate(); // Call the validate function for overall form validation
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