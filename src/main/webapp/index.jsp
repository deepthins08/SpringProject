<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Xworkz</title>
    <style>
        .card {
            max-width: 400px;
            word-wrap: break-word;
        }
    </style>
    <base href="http://localhost:8080/xworkzProject/">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
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
                <!-- Removed dropdown icon and added button for offcanvas -->
                <li class="nav-item">
                    <button class="btn btn-light" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight" aria-controls="offcanvasRight">
                        <i class="fas fa-bars"></i>
                    </button>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
    <div class="offcanvas-header bg-dark">
        <h5 id="offcanvasRightLabel" class="text-light fs-4 fw-bold">Menu</h5>
        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>
    <div class="offcanvas-body bg-muted">
        <ul class="list-group">
            <li class="list-group-item bg-dark mb-3">
                <button class="btn btn-dark text-light fs-3 w-100" type="button" data-bs-toggle="collapse" data-bs-target="#userMenu" aria-expanded="false">
                    User
                </button>
                <div class="collapse" id="userMenu">
                    <ul class="list-group">
                        <li class="mt-3 rounded-1 text-center"><a class="list-group-item fs-5 " href="Signup.jsp">Sign Up</a></li>
                        <li class="mt-2 rounded-1 text-center"><a class="list-group-item fs-5" href="SignIn.jsp">Sign In</a></li>
                    </ul>
                </div>
            </li>
            <li class="list-group-item bg-dark mb-3">
                <button class="btn btn-dark text-light fs-3 w-100" type="button" data-bs-toggle="collapse" data-bs-target="#adminMenu" aria-expanded="false">
                    Admin
                </button>
                <div class="collapse" id="adminMenu">
                    <ul class="list-group">
                        <li class=" mt-3 rounded-1 text-center"><a class="list-group-item fs-5" href="admin/signin">Admin Sign In</a></li>
                        <li class="mt-2 rounded-1 text-center"><a class="list-group-item fs-5" href="departmentAdminSignIn">Department Admin Sign In</a></li>
                    </ul>
                </div>
            </li>
            <li class="list-group-item bg-dark mb-3">
                <button class="btn btn-dark text-light fs-3 w-100" type="button" data-bs-toggle="collapse" data-bs-target="#employeeMenu" aria-expanded="false">
                    Employee
                </button>
                <div class="collapse" id="employeeMenu">
                    <ul class="list-group  ">
                        <li class="mt-3 rounded-1 text-center"><a class="list-group-item fs-5" href="EmployeeSignIn.jsp">Employee Sign In</a></li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>
</div>

<div class="container mt-5 mb-5 d-flex justify-content-center">
    <div class="card px-5 py-4 bg-light w-100 shadow-lg p-3 mb-5 bg-white rounded" style="max-width: 600px;">
        <div class="mb-1 mt-3">
            <p class="fw-bold"><h4>Tech Stack:</h4><span class="text-">JSP, Bootstrap CSS, JavaScript, Java, Spring, Spring JPA/Hibernate.</span></p>
        </div>
        <div class="mb-1 mt-3">
            <p class="fw-bold"><h4>Start Date:</h4> <span>11-06-2024</span></p>
        </div>
        <div class="mb-1 mt-3">
            <p class="fw-bold"><h4>VCS:</h4><a href="https://github.com/deepthins08/SpringProject" target="_blank">GitHub</a></p>
        </div>
        <div class="mb-1 mt-3">
            <p class="fw-bold"><h4>Description:</h4> <span>Currently working on designing Landing Page and Sign Up page. Saving data to the database. Created Sign Up page and generated password to email for login and for first-time login will be redirected to reset password to reset the password. Captcha is added to Sign In. Account Lock is Created.</span></p>
        </div>
    </div>
</div>

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
