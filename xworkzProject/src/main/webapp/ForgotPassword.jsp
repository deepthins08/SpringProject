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
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" integrity="sha384-KyZXEAg3QhqLMpG8r+Knujsl5/ieIJ6gIFhvjaCln2gq5oWeFf3vSUcIB+q5VR7j" crossorigin="anonymous">
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
                    <a class="nav-link active text-light fs-5" aria-current="page" href="index.jsp">Home</a>


            </ul>
        </div>
    </div>
</nav>



<form action="emailPassword" method="get">


     <div class="container mt-5 mb-5 d-flex justify-content-center">
           <div class="card px-5 py-4 bg-light shadow-lg p-3 mb-5 bg-white rounded">
               <div class="card-body">
<span class="text-danger">
<c:forEach items="${errors}" var="objectError">
${objectError.defaultMessage}<br>
</c:forEach>


</span>

                   <span class="text-danger fs-6 fw-bold">${mailMsg}</span>

                   <h3 class="card-title mb-3 text-dark fs-2">

                   Please Enter Your Registered Email</h3>

                    <div class="mb-3">
                                       <label for="email" class="form-label text-dark">Email</label>
                                       <input type="email" class="form-control" name="email" value="${dto.email}"  id="email" aria-describedby="emailHelp">
                                       <span id="emailError" class="text-danger"></span>
                                   </div>

          <div class="mb-1 mt-3">
                <div class="container">
                    <div class="row">
                      <div class="col">
                    <input type="submit" value="Sign In" name="btn" id="btn" class="btn btn-primary me-5" >
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
