<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Xworkz</title>
<style>

        .navbar-nav .nav-link:hover {
            color: #fff !important;
            background-color: #0d6efd;
        }
    </style>
    <base href="http://localhost:8080/xworkzProject/">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body class="bg-primary">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
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
                    <a class="nav-link active text-dark fs-5 fw-bold" aria-current="page" href="Signup.jsp">Sign Up</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-dark fs-5 fw-bold" href="SignIn.jsp">Sign In</a>
                </li>


                <li class="nav-item">
                    <a class="nav-link text-dark fs-5 fw-bold" href="admin/signin">Admin </a>

                </li>


            </ul>
        </div>
    </div>
</nav>


    <div class="container mt-5 mb-5 d-flex justify-content-center">
        <div class="card px-5 py-4 bg-light w-100" style="max-width: 600px;">

            <div class="mb-1 mt-3">
            <p class="fw-bold"><h4> Tech Stack: </h4><span class="text-">JSP,Bootstrap CSS,JavaScript,Java,Spring,Spring JPA/Hibernate.</span>
            </div>
            <div class="mb-1 mt-3">
             <p class="fw-bold"> <h4>Start Date:</h4> <span>11-06-2024</span>
                        </div>
            <div class="mb-1 mt-3">
             <p class="fw-bold"> <h4>VCS:</h4><a href="https://github.com/deepthins08/SpringProject" target="_blank">GitHub</a></span>
              </div>
            <div class="mb-1 mt-3">
              <p class="fw-bold"> <h4>Description:</h4> <span>Currently working on designing Landing Page and Sign Up page.
              Saving data to the data base.</span>
              </div>
        </div>
    </div>



<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
</body>
</html>
