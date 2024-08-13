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

        img {
            width: 60px;
            height: 60px;
            object-fit: fill;
            object-position: center;
        }
        .card {
            max-width: 450px;
            word-wrap: break-word;
        }
    </style>
    <script src="/xworkzProject/js/complaint.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
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
                    <a class="nav-link active text-light fs-5 " aria-current="page" href="Home.jsp">Home</a>
                </li>
                <li class="nav-item">
                                <a class="nav-link active text-light fs-5 " aria-current="page" href="logout">Logout</a>
                </li>
                <li class="nav-item">
                <a class="nav-link active text-light fs-5 " aria-current="page" href="#">${sessionScope.firstName} ${sessionScope.lastName}</a>
</li>
            </ul>

                <li class="nav-item">
                    <img src="${pageContext.request.contextPath}${sessionScope.profileImage}" class="rounded-circle profile-image" alt="" id="profileImage" onerror="this.onerror=null;this.src='https://static.vecteezy.com/system/resources/previews/036/280/650/original/default-avatar-profile-icon-social-media-user-image-gray-avatar-icon-blank-profile-silhouette-illustration-vector.jpg';">
                </li>
            </ul>
        </div>
    </div>
</nav>

<c:choose>
    <c:when test="${action == 'edit'}">
        <form action="raiseComplaint" method="post">
    </c:when>
    <c:otherwise>
        <form action="raiseComplaint" method="post">
    </c:otherwise>
</c:choose>

<div class="container mt-5 mb-5 d-flex justify-content-center">
    <div class="card px-5 py-4 bg-light shadow-lg p-3 mb-5 bg-white rounded">
        <div class="card-body">
            <span class="text-danger">
                <c:forEach items="${errors}" var="objectError">
                    ${objectError.defaultMessage}<br>
                </c:forEach>
            </span>
            <span class="text-success fs-4 fw-bold">${complaintMsg}</span>

            <h6 class="card-title mb-3 text-dark fs-1">${action.equalsIgnoreCase('edit') ? 'Edit' : 'Raise'} Complaint</h6>

            <c:if test="${action == 'edit'}">
                <input type="hidden" name="id" value="${complaint.id}">
                <input type="hidden" name="btn" value="Edit">
            </c:if>

            <div class="mb-4">
                <label for="type" class="form-label text-dark">Types</label>
                <select class="form-select" id="type" name="type" ${action == 'edit' ? 'disabled' : ''} onblur="setGroup()">
                    <option value="0" ${complaint.type == null ? 'selected' : ''}>Choose...</option>
                    <option value="Drainage Problem" ${complaint.type eq 'Drainage Problem' ? 'selected' : ''}>Drainage Problem</option>
                    <option value="Electric Problem" ${complaint.type eq 'Electric Problem' ? 'selected' : ''}>Electric Problem</option>
                    <option value="Plumber Problem" ${complaint.type eq 'Plumber Problem' ? 'selected' : ''}>Plumber Problem</option>
                    <option value="Wastage Problem" ${complaint.type eq 'Wastage Problem' ? 'selected' : ''}>Wastage Problem</option>
                    <option value="Water Problem" ${complaint.type eq 'Water Problem' ? 'selected' : ''}>Water Problem</option>
                </select>
                <span id="groupError" class="text-danger"></span>
            </div>

            <div class="mb-3">
                <label for="area" class="form-label text-dark">Area</label>
                <input type="text" class="form-control" value="${complaint.area}" ${action == 'edit' ? 'readonly' : ''} name="area" id="area" aria-describedby="nameHelp" onblur="setArea()">
                <span id="areaError" class="text-danger"></span>
            </div>

            <div class="mb-3">
                <div class="form-floating">
                    <textarea class="form-control" placeholder="Leave a comment here" ${action == 'edit' ? 'readonly' : ''} name="address" id="address" onblur="setAddress()">${complaint.address}</textarea>
                    <label for="address">Address</label>
                </div>
                <span id="addressError" class="text-danger"></span>
            </div>

            <!-- Country Dropdown -->
            <div class="mb-3">
                <label for="country" class="form-label text-dark">Country</label>
                <select class="form-select" id="country" name="country" ${action == 'edit' ? 'disabled' : ''} onblur="fetchStates(); validateCountry();">
                    <c:choose>
                        <c:when test="${action != 'edit'}">
                            <option value="0">Choose...</option>
                        </c:when>
                        <c:otherwise>
                            <option value="0">${complaint.country}</option>
                        </c:otherwise>
                    </c:choose>
                </select>
                <span id="countryError" class="text-danger"></span>
            </div>

            <!-- State Dropdown -->
            <div class="mb-3">
                <label for="state" class="form-label text-dark">State</label>
                <select class="form-select" id="state" name="state" ${action == 'edit' ? 'disabled' : ''} onblur="fetchCities(); validateState();">
                    <c:choose>
                        <c:when test="${action != 'edit'}">
                            <option value="0">Choose...</option>
                        </c:when>
                        <c:otherwise>
                            <option value="0">${complaint.state}</option>
                        </c:otherwise>
                    </c:choose>
                </select>
                <span id="stateError" class="text-danger"></span>
            </div>

            <!-- City Dropdown -->
            <div class="mb-3">
                <label for="city" class="form-label text-dark">City</label>
                <select class="form-select" id="city" name="city" ${action == 'edit' ? 'disabled' : ''} onblur="validateCity()">
                    <c:choose>
                        <c:when test="${action != 'edit'}">
                            <option value="0">Choose...</option>
                        </c:when>
                        <c:otherwise>
                            <option value="0">${complaint.city}</option>
                        </c:otherwise>
                    </c:choose>
                </select>
                <span id="cityError" class="text-danger"></span>
            </div>

            <div class="mb-3">
                <div class="form-floating">
                    <textarea class="form-control" placeholder="Leave a comment here" name="description" id="description" oninput="updateDescriptionCount()" maxlength="300" onblur="validateDescription()">${complaint.description}</textarea>
                    <label for="description">Description</label>
                </div>
                <span id="descriptionError" class="text-danger"></span>
                <small id="descriptionCount" class="form-text text-muted">300 characters remaining</small>
            </div>
<div class="d-flex justify-content-between">
            <c:choose>
                <c:when test="${action == 'edit'}">
                    <input type="submit" value="Edit" name="submit" id="submit" class="btn btn-primary me-5">
                </c:when>
                <c:otherwise>
                    <input type="submit" value="Register" name="btn" id="btn" class="btn btn-primary me-5" disabled>
                </c:otherwise>
            </c:choose>
               <input class="btn btn-primary ms-5 " type="reset" value="Reset">

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
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script>
</body>
</html>
