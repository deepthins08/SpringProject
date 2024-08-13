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
                     max-width: 1500px;
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

            </ul>
        </div>
    </div>
</nav>

 <div class="col-md-6">
             <div class="d-flex justify-content-center mb-4 mt-3">
                    <div class="card px-1 py-1 bg-light shadow-lg p-3 mb-5 bg-white rounded">
                        <div class="card-body">

                    <form action="admin/adminViewComplaints" method="get">

                        <div class="row g-3 ">
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
                             <div class="col-auto align-self-end">
                            <input class="btn btn-primary" type="submit" value="Clear" name="clear" id="clear">
                              </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="shadow-lg p-3 mb-5 bg-white rounded">
        <table class="table table-striped table-light ">
            <thead class="text-light fw-bold table-dark">
                <tr>

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

                        <td>${complaint.type}</td>
                        <td>${complaint.area}</td>
                        <td>${complaint.country}</td>
                        <td>${complaint.state}</td>
                        <td>${complaint.city}</td>
                        <td>${complaint.description}</td>
                        <td>${complaint.status}</td>
                         <td class="d-none d-md-table-cell">

                        <c:if test="${complaint.status != 'resolved'}">
                        <form action="admin/updateComplaintAssignAndStatus" method="post"> <!-- Action form -->

                           <input type="hidden" name="id" value="${complaint.id}">
                 <div class="input-group">
                         <select class="form-select" name="departmentName">
                          <option value="">Choose...</option>
                          <option value="Drainage Department">Drainage Department</option>
                          <option value="Electric Department">Electric Department</option>
                          <option value="Plumber Department">Plumber Department</option>
                          option value="Wastage Department">Wastage Department</option>
                          <option value="Water Department">Water Department</option>
                           </select>


</c:if>
              <td class="d-none d-md-table-cell">
                        <c:if test="${complaint.status != 'resolved'}">


                        <input type="hidden" name="id" value="${complaint.id}">
                          <div class="input-group">
                        <select class="form-select" id="status" name="status">
                           <option value="0" ${selectedType == null ? 'selected' : ''}>Choose...</option>
                           <option value="active" ${selectedType == 'active' ? 'selected' : ''}>Active</option>
                           <option value="assigned" ${selectedType == 'assigned' ? 'selected' : ''}>Assigned</option>

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
