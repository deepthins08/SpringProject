<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Xworkz</title>
    <style>
    img{
              width: 60px;
              height: 60px;
              object-fit:fill;
              object-position: center
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
            <ul class="navbar-nav ms-auto">
                <li class="nav-item me-3 mt-2">
                    <a class="nav-link active text-dark fs-5 fw-bold" aria-current="page" href="Home.jsp">Home</a>
                </li>

                <li class="nav-item">
                <img src="${pageContext.request.contextPath}${sessionScope.profileImage}"  class="rounded-circle profile-image" alt="" id="profileImage" onerror="this.onerror=null;this.src='https://static.vecteezy.com/system/resources/previews/036/280/650/original/default-avatar-profile-icon-social-media-user-image-gray-avatar-icon-blank-profile-silhouette-illustration-vector.jpg';">
                </li>
            </ul>
        </div>
    </div>
</nav>

<form action="editProfile" method="post" enctype="multipart/form-data">
    <div class="container mt-5 mb-5 d-flex justify-content-center">
        <div class="card px-5 py-4 bg-light">
            <div class="card-body">
                <span class="text-success">${message}</span>
                <h6 class="card-title mb-3 text-dark fs-1">Edit Profile</h6>
                <div class="mb-3">
                    <label for="firstName" class="form-label text-dark">First Name</label>
                    <input type="text" class="form-control" name="firstName" id="firstName" value="${sessionScope.firstName}" aria-describedby="nameHelp">
                </div>
                <div class="mb-3">
                    <label for="lastName" class="form-label text-dark">Last Name</label>
                    <input type="text" class="form-control" name="lastName" id="lastName" value="${sessionScope.lastName}" aria-describedby="nameHelp">
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label text-dark">Email</label>
                    <input type="email" class="form-control" name="email" id="email" value="${sessionScope.email}" ${action=='edit'?'readonly':''}  aria-describedby="emailHelp">
                </div>
                <div class="mb-3">
                    <label for="phone" class="form-label text-dark">Phone</label>
                    <input type="text" class="form-control" name="phone" id="phone" value="${sessionScope.phone}" aria-describedby="phoneHelp">
                </div>
                <div class="mb-3">
                    <label for="file" class="form-label text-dark">Choose File</label>
                    <input type="file" class="form-control" name="file" id="file">
                </div>
                <div class="mb-3">
                    <input type="submit" class="form-control btn btn-primary" value="Upload" name="upload" id="upload">
                </div>
            </div>
        </div>
    </div>
</form>


<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
</body>
</html>
