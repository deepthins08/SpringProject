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
                      max-width: 400px;
                      word-wrap: break-word;
                  }
    </style>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body class="bg-muted">
<nav class="navbar navbar-expand-lg navbar-light bg-dark py-0 sticky-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <img src="https://www.x-workz.in/Logo.png" width="70" height="50" alt="Xworkz Logo">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link active text-light fs-5 " aria-current="page" href="index.jsp">Home</a>
                </li>

            </ul>
        </div>
    </div>
</nav>

<form action="resetPassword" method="post">
     <div class="container mt-5 mb-5 d-flex justify-content-center">
           <div class="card px-5 py-4 bg-light shadow-lg p-3 mb-5 bg-white rounded">
               <div class="card-body">
<span class="text-danger">
<c:forEach items="${errors}" var="objectError">
${objectError.defaultMessage}<br>
</c:forEach>


</span>


   </span>
                   <span class="text-success fs-4 fw-bold">${msg}</span>
                            <span class="text-success fs-4 fw-bold">${message}</span>

                   <h6 class="card-title mb-3 text-dark fs-1">Reset Password</h6>


                    <div class="mb-3">
                                       <label for="email" class="form-label text-dark">Email</label>
                                       <input type="email" value="${dto.email}" class="form-control" ${action='edit'?'readonly':''} name="email" id="email" aria-describedby="emailHelp" >
                                       <span id="emailError" class="text-danger"></span>
                                   </div>

                   <div class="mb-3">
                       <label for="password" class="form-label text-dark">Old Password</label>
                       <input type="password" class="form-control"  name="password" id="password" aria-describedby="phoneHelp" >
                       <span id="passwordError" class="text-danger"></span>

                   </div>

 <div class="mb-3">
                       <label for="newPassword" class="form-label text-dark">New Password</label>
                       <input type="password" class="form-control"  name="newPassword" id="newPassword" aria-describedby="phoneHelp" >
                       <span id="passwordError" class="text-danger"></span>

                   </div>

   <div class="mb-3">
                         <label for="confirmPassword" class="form-label text-dark">Confirm New Password</label>
                         <input type="password" class="form-control"  name="confirmPassword" id="confirmPassword" aria-describedby="phoneHelp" >
                         <span id="passwordError" class="text-danger"></span>

                     </div>

          <div class="mb-1 mt-3">
                <div class="container">
                    <div class="row">
                      <div class="col">
                    <input type="submit" value="Reset" name="reset" id="btn" class="btn btn-primary me-5" >
</div>

           </div>
    </div>

</form>
<div class="fixed-bottom">
<footer class="d-flex flex-wrap justify-content-center align-items-center mb-0 py-3 my-4 border-top bg-dark ">
    <div class="d-flex align-items-center">
        <a href="/" class="mb-3 me-2 mb-md-0 text-muted text-decoration-none lh-1">
            <svg class="bi" width="30" height="24">
                <use xlink:href="#bootstrap"></use>
            </svg>
        </a>
        <span class="text-light">Copyright &#169; 2024, All Right Reserved</span>
    </div>
</footer>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
</body>
</html>
