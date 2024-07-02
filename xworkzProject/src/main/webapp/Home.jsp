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

        img{
          width: 60px;
          height: 60px;
          object-fit:fill;
          object-position: center;

        }
    </style>
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
            <ul class="navbar-nav ms-auto me-3">

<li class="nav-item dropdown me-4 mt-3">
                           <a class="nav-link dropdown-toggle text-dark fs-6 fw-bold" href="#" id="bloodDonationDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                               Hi, ${sessionScope.firstName} ${sessionScope.lastName}
                           </a>
                           <ul class="dropdown-menu" aria-labelledby="bloodDonationDropdown">
                               <li><a class="dropdown-item" href="raiseComplaintPage">Raise Complaint</a></li>
                               <li><a class="dropdown-item" href="ViewComplaint.jsp">View Complaints</a></li>
                               <li><a class="dropdown-item" href="ProfileEdit.jsp">Edit Profile</a></li>
                           </ul>
                       </li>

<li class="nav-item">
                <img src="${pageContext.request.contextPath}${sessionScope.profileImage}"  class="rounded-circle profile-image" alt="" id="profileImage" onerror="this.onerror=null;this.src='https://static.vecteezy.com/system/resources/previews/036/280/650/original/default-avatar-profile-icon-social-media-user-image-gray-avatar-icon-blank-profile-silhouette-illustration-vector.jpg';">
                </li>


            </ul>
        </div>
    </div>
</nav>

   <form action="home" method="get">
    <div class="container mt-5 mb-5 d-flex justify-content-center">
        <div class="card px-5 py-4 bg-light w-100" style="max-width: 600px;">

            <div class="mb-1 mt-3">
            <p class="fw-bold"><h4> Welcome <span>${sessionScope.firstName} ${sessionScope.lastName}</span>
            </div>
            <div class="mb-1 mt-3">

        </div>
    </div>
</form>


<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
</body>
</html>
