<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Xworkz</title>
        <base href="http://localhost:8080/xworkzProject/">
<style>

        .navbar-nav .nav-link:hover {
            color: #fff !important;
            background-color: #0d6efd;
        }
  .card {
                     max-width: 450px;
                     word-wrap: break-word;
                 }
    </style>
 <script src="/xworkzProject/js/signup.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body class="bg-primary">
<nav class="navbar navbar-expand-lg navbar-light bg-light py-0">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <img src="https://www.x-workz.in/Logo.png" width="70" height="50" alt="Xworkz Logo">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">

  <ul class="navbar-nav ms-auto ">
             <li class="nav-item dropdown ">
                                       <a class="nav-link dropdown-toggle text-dark fs-6 fw-bold" href="#" id="movieTicketBookingDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                          Menu
                                       </a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="menuDropdown">
                                        <li>  <a class="dropdown-item" href="index.jsp">Home</a></li>
                                       <li>  <a class="dropdown-item" href="SignIn">SignIn</a></li>

                                       </ul>
                                   </li>
            </ul>
            </ul>
        </div>
    </div>
</nav>

<form action="signup" method="post">
     <div class="container mt-5 mb-5 d-flex justify-content-center">
           <div class="card px-5 py-4 bg-light">
               <div class="card-body">
<span class="text-danger">
<c:forEach items="${errors}" var="objectError">
${objectError.defaultMessage}<br>
</c:forEach>


</span>


   </span>
                   <span class="text-success fs-4 fw-bold">${signupMsg}</span>

                   <h6 class="card-title mb-3 text-dark fs-1">Sign Up</h6>

                 <div class="mb-3">
                     <label for="firstName" class="form-label text-dark">First Name</label>
                     <input type="text" class="form-control" value="${dto.firstName}" name="firstName" id="firstName" aria-describedby="nameHelp" onblur="setFirstName()">
                     <span id="fnameError" class="text-danger"></span>
                 </div>

    <div class="mb-3">
                           <label for="lastName" class="form-label text-dark">Last Name</label>
                           <input type="text" class="form-control" value="${dto.lastName}" name="lastName" id="lastName" aria-describedby="nameHelp" onblur="setLastName()">
                           <span id="lnameError" class="text-danger"></span>
                       </div>
                    <div class="mb-3">
                                       <label for="email" class="form-label text-dark">Email</label>
                                       <input type="email" value="${dto.email}" class="form-control" name="email" id="email" aria-describedby="emailHelp" onblur="validateEmail()">
                                       <span id="emailError" class="text-danger"></span>
                                   </div>

                   <div class="mb-3">
                       <label for="phone" class="form-label text-dark">phone</label>
                       <input type="text" class="form-control" value="${dto.phone}" name="phone" id="phone" aria-describedby="phoneHelp" onblur="validatePhone()">
                       <span id="phoneError" class="text-danger"></span>

                   </div>
 <div class="mb-3">
  <label for="qualification" class="form-label text-dark fw-bold">Agreement</label>
  <p>Im Declaring, all the above details are true</p>.
 <div class="form-check form-check-inline">
   <input class="form-check-input" type="checkbox" name="confirm" id="confirm" value="Agreed" ${dto.confirm eq 'Agreed' ? 'checked' :''} onblur="setConfirm()">
   <label class="form-check-label" for="confirm">Agree</label>
   <span id="confirmError" class="text-danger"></span>
 </div>
          <div class=" mt-3">
                <div class="container">
             <div class="d-flex justify-content-between">
                                <input type="submit" value="Register" name="btn" id="btn" class="btn btn-primary" disabled>
                                <input class="btn btn-primary" type="reset" value="Reset">
                            </div>
    </div>

</form>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
</body>
</html>
