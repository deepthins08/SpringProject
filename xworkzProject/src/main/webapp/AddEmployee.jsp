<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Xworkz</title>
    <base href="http://localhost:8080/xworkzProject/">
    <script src="/xworkzProject/js/addEmployee.js"></script>
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
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-dark fs-6 fw-bold" href="#" id="movieTicketBookingDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Add Employee
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="menuDropdown">
                        <li><a class="dropdown-item" href="DepartmentAdmin.jsp">Home</a></li>
                        <li><a class="dropdown-item" href="departmentAdmin/departmentAdminViewComplaints">View Complaints</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<form action="departmentAdmin/addEmployee" method="post">
    <div class="container mt-5 mb-5 d-flex justify-content-center">
        <div class="card px-5 py-4 bg-light">
            <div class="card-body">
                <span class="text-danger">
                    <c:forEach items="${errors}" var="objectError">
                        ${objectError.defaultMessage}<br>
                    </c:forEach>
                </span>
                <span class="text-success fs-4 fw-bold">${addMsg}</span>
                <span class="text-success fs-4 fw-bold">${departmentMsg
                }</span>
                <h6 class="card-title mb-3 text-dark fs-1">Add Employee</h6>
                <div class="mb-4">
                    <label for="departmentId" class="form-label text-dark">Department</label>
                    <select class="form-select" id="departmentId" name="departmentId" onblur="setDepartmentId()">
                        <option value="0">Choose...</option>
                        <c:forEach items="${departments}" var="department">
                            <option value="${department.departmentId}">${department.departmentName}</option>
                        </c:forEach>
                    </select>
                    <span id="departmentIdError" class="text-danger"></span>
                </div>
                <div class="mb-3">
                    <label for="name" class="form-label text-dark">Name</label>
                    <input type="text" class="form-control" value="${dto.name}" name="name" id="name" aria-describedby="nameHelp" onblur="setName()">
                    <span id="nameError" class="text-danger"></span>
                </div>
                <div class="mb-3">
                    <label for="designation" class="form-label text-dark">Designation</label>
                    <input type="text" class="form-control" value="${dto.designation}" name="designation" id="designation" aria-describedby="nameHelp" onblur="setDesignation()">
                    <span id="designationError" class="text-danger"></span>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label text-dark">Email</label>
                    <input type="email" value="${dto.email}" class="form-control" name="email" id="email" aria-describedby="emailHelp" onblur="emailValidation()">
                    <span id="emailError" class="text-danger"></span>
                </div>
                <div class="mb-3">
                    <label for="phone" class="form-label text-dark">Phone</label>
                    <input type="text" class="form-control" value="${dto.phone}" name="phone" id="phone" aria-describedby="phoneHelp" onblur="phoneValidation()">
                    <span id="phoneError" class="text-danger"></span>
                </div>
                <div class="mb-1 mt-3">
                    <div class="container">
                        <div class="row">
                            <div class="col">
                                <input type="submit" value="Register" name="btn" id="btn" class="btn btn-primary me-5" disabled>
                            </div>
                            <div class="col">
                                <input class="btn btn-primary ms-5" type="reset" value="Reset">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
</body>
</html>
