<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>View Complaints</title>
    <base href="http://localhost:8080/xworkzProject/">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>


    .card {
                       max-width: 400px;
                       word-wrap: break-word;
                   }
    </style>
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
                                <a class="nav-link active text-light fs-5 " aria-current="page" href="admin/adminLogout">Logout</a>
                             </li>
                           <li class="nav-item">
                           <a class="nav-link active text-light fs-5 " aria-current="page" href="Admin.jsp">Home</a>
           </li>
   <li class="nav-item">
                           <a class="nav-link active text-light fs-5 " aria-current="page" href="#"> ${sessionScope.admin.name}</a>
           </li>
                       </ul>

        </div>
    </div>
</nav>
<div class="container mt-5">
    <h1 class="text-dark fw-bold mb-3">Users List</h1>
  <div class="table-responsive shadow-lg p-3 mb-5 bg-white rounded">
     <table class="table table-light table-striped  ">
        <thead class="text-light fw-bold table-dark">
        <tr>

            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Created By</th>
            <th>Created At</th>
            <th>Modified By</th>
            <th>Modified At</th>
        </tr>
        </thead>
        <tbody class="text-dark fw-bold">

        <c:forEach var="user" items="${users}">
            <tr>

                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>${user.createdBy}</td>
                <td>${user.createdAt}</td>
                <td>${user.modifiedBy}</td>
                <td>${user.modifiedAt}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
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
