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
        .navbar-nav .nav-link:hover {
            color: #fff !important;
            background-color: #0d6efd;
        }
    </style>
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
                    <a class="nav-link text-dark fs-5 fw-bold" href="Admin.jsp">Home</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
 <div class="col-md-6">
             <div class="d-flex justify-content-center mb-4 mt-3">
                    <div class="card px-1 py-1 bg-light">
                        <div class="card-body">

                    <form action="admin/adminViewComplaints" method="get">

                        <div class="row g-3">
                            <div class="col-auto">
                                <label for="type" class="form-label text-dark">Type</label>
                                <select class="form-select" id="type" name="type">
                                    <option value="0" ${selectedType == null ? 'selected' : ''}>Choose...</option>
                                    <option value="Drainage Problem" ${selectedType == 'Drainage Problem' ? 'selected' : ''}>Drainage Problem</option>
                                    <option value="Electric Problem" ${selectedType == 'Electric Problem' ? 'selected' : ''}>Electric Problem</option>
                                    <option value="Plumber Problem" ${selectedType == 'Plumber Problem' ? 'selected' : ''}>Plumber Problem</option>
                                    <option value="Wastage Problem" ${selectedType == 'Wastage Problem' ? 'selected' : ''}>Wastage Problem</option>
                                    <option value="Water Problem" ${selectedType == 'Water Problem' ? 'selected' : ''}>Water Problem</option>
                                </select>
                            </div>
                            <div class="col-auto">
                                <label for="area" class="form-label text-dark">Area</label>
                                <input type="text" class="form-control" id="area" name="area" value="${selectedArea}">
                            </div>
                            <div class="col-auto align-self-end">
                                <input type="submit" value="Search" name="search" id="search" class="btn btn-primary">
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
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

                                    <th scope="col">Assign</th>
                                    <th scope="col">Action</th>

                </tr>
            </thead>
            <tbody>
                <c:forEach items="${complaint}" var="complaint">
                    <tr class="text-dark fw-bold">
                        <th scope="row">${complaint.id}</th>
                        <td>${complaint.type}</td>
                        <td>${complaint.area}</td>
                        <td>${complaint.country}</td>
                        <td>${complaint.state}</td>
                        <td>${complaint.city}</td>
                        <td>${complaint.description}</td>
                        <td>${complaint.status}</td>
                         <td class="d-none d-md-table-cell">

                        <c:if test="${complaint.status != 'resolved'}">
                        <form action="admin/updateComplaintAssign" method="post"> <!-- Action form -->
                         <input type="hidden" name="id" value="${complaint.id}">
                          <div class="btn-group">
                         <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                           Assign
                          </button>
                          <ul class="dropdown-menu">
                          <li><button class="dropdown-item" type="submit" name="assign" value="resolved">Drainage Department</button></li>
                           <li><button class="dropdown-item" type="submit" name="assign" value="pending">Electric Department</button></li>
                           <li><button class="dropdown-item" type="submit" name="assign" value="pending">Plumber Department </button></li>
                           <li><button class="dropdown-item" type="submit" name="assign" value="pending">Wastage Department</button></li>
                           <li><button class="dropdown-item" type="submit" name="assign" value="pending">Water Department</button></li>

                           </ul>
                         </div>
                         </form></td>
</c:if>
 <td class="d-none d-md-table-cell">
                        <c:if test="${complaint.status != 'resolved'}">

                            <form action="admin/updateComplaintStatus" method="post"> <!-- Status form -->
                                <input type="hidden" name="id" value="${complaint.id}">
                                  <div class="input-group">
                                <select class="form-select" id="status" name="status">
                                    <option value="0" ${selectedType == null ? 'selected' : ''}>Choose...</option>
                                    <option value="active" ${selectedType == 'active' ? 'selected' : ''}>Active</option>
                                    <option value="resolved" ${selectedType == 'resolved' ? 'selected' : ''}>Resolved</option>
                                    <option value="pending" ${selectedType == 'pending' ? 'selected' : ''}>Pending</option>

                                </select>
                                   <button type="submit" class="btn btn-primary">Update</button>
                                 </div>
                               </form>
                        </td>
                         </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
</body>
</html>
