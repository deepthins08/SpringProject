<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>View Complaints</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .hidden {
            display: none;
        }
        img {
            width: 60px;
            height: 60px;
            object-fit: fill;
            object-position: center;
        }
        .navbar-nav .nav-link:hover {
            color: #fff !important;
            background-color: #0d6efd;
        }
  .card {
                     max-width: 400px;
                     word-wrap: break-word;
                 }
    </style>
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
             <ul class="navbar-nav ms-auto me-3">

            <li class="nav-item dropdown me-4 mt-3">
                   <a class="nav-link dropdown-toggle text-dark fs-6 fw-bold" href="#" id="bloodDonationDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                     View Complaints
                       </a>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="menuDropdown">
                       <li><a class="dropdown-item" href="Home.jsp">Home</a></li>
                        <li><a class="dropdown-item" href="raiseComplaintPage">Raise Complaints</a></li>
                        <li><a class="dropdown-item" href="ProfileEdit.jsp">Edit Profile</a></li>
                        </ul>
                         </li>
         <li class="nav-item">
                           <img src="${pageContext.request.contextPath}${sessionScope.profileImage}" class="rounded-circle profile-image" alt="" id="profileImage" onerror="this.onerror=null;this.src='https://static.vecteezy.com/system/resources/previews/036/280/650/original/default-avatar-profile-icon-social-media-user-image-gray-avatar-icon-blank-profile-silhouette-illustration-vector.jpg';">
                       </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-5 ${not empty complaints ? 'hidden' : ''}">
    <div class="d-flex justify-content-center mb-4">
        <div class="card px-5 py-4 bg-light">
            <div class="card-body">
                <span class="text-success fs-4 fw-bold">${complaintMsg}</span>
                <h6 class="card-title mb-3 text-dark fs-1">View Complaints</h6>

                <form action="viewComplaints" method="get">
                    <div class="mb-4">
                        <label for="status" class="form-label text-dark">Complaints Status</label>
                        <select class="form-select" id="status" name="status">
                            <option value="0" ${selectedStatus == null ? 'selected' : ''}>Choose...</option>
                            <option value="active" ${selectedStatus == 'active' ? 'selected' : ''}>Active</option>
                            <option value="resolved" ${selectedStatus == 'resolved' ? 'selected' : ''}>Resolved</option>
                        </select>
                        <span id="groupError" class="text-danger"></span>
                    </div>


                    <div class="mb-1 mt-3">
                        <input type="submit" value="Search" name="submit" id="submit" class="btn btn-primary me-5">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<c:if test="${not empty complaints}">
    <div class="container mt-5">
        <div class="table-responsive">
            <table class="table table-striped table-light">
                <thead class="text-dark fw-bold">
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Type</th>
                        <th scope="col">Area</th>
                        <th scope="col">Country</th>
                        <th scope="col">State</th>
                        <th scope="col">City</th>
                        <th scope="col">Description</th>
                        <th scope="col">Status</th>
                         <c:if test="${complaint.status != 'resolved'}">
                        <th scope="col">Edit</th>
                        </c:if>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${complaints}" var="complaint">
                        <tr class="text-dark fw-bold">
                            <th scope="row">${complaint.id}</th>
                            <td>${complaint.type}</td>
                            <td>${complaint.area}</td>
                            <td>${complaint.country}</td>
                            <td>${complaint.state}</td>
                            <td>${complaint.city}</td>
                            <td>${complaint.description}</td>
                            <td>${complaint.status}</td>
                             <c:if test="${complaint.status != 'resolved'}">
                            <td><a href="editComplaint?id=${complaint.id}&edit=edit">Edit</a></td>
                       </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
</body>
</html>
