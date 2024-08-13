<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>View Complaints</title>
    <base href="http://localhost:8080/xworkzProject/">
    <script src="/xworkzProject/js/userOtp.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .navbar-nav .nav-link:hover {
            color: #fff !important;
            background-color: #0d6efd;
        }
        .card {
            max-width: 400px;
            word-wrap: break-word;
        }
        .modal-dialog {
            max-width: 600px;
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
                <a class="nav-link active text-light fs-5 " aria-current="page" href="employeeLogout">Logout</a>
      </li>
        <li class="nav-item">
                <a class="nav-link active text-light fs-5 " aria-current="page" href="DepartmentAdmin.jsp">Home</a>
               </li>
       <li class="nav-item">
                               <a class="nav-link active text-light fs-5 " aria-current="page" href="#"> ${sessionScope.employee.name}</a>
               </li>
            </ul>
        </div>
    </div>
</nav>

<div class="shadow-lg p-1 m-5 mb-5 bg-muted rounded">
    <table class="table table-striped table-light">
        <thead class="text-light fw-bold table-dark">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Type</th>
                <th scope="col">Area</th>
                <th scope="col">Country</th>
                <th scope="col">State</th>
                <th scope="col">City</th>
                <th scope="col">Description</th>
                <th scope="col">Status</th>
                <th scope="col">Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${complaints}" var="complaint">
                <tr class="text-dark">
                    <th scope="row">${complaint.id}</th>
                    <td>${complaint.type}</td>
                    <td>${complaint.area}</td>
                    <td>${complaint.country}</td>
                    <td>${complaint.state}</td>
                    <td>${complaint.city}</td>
                    <td>${complaint.description}</td>
                    <td>${complaint.status}</td>
                    <td class="d-none d-md-table-cell">
                        <form action="#" method="post" class="status-form">
                            <c:if test="${complaint.status != 'resolved'}">
                                <input type="hidden" name="id" value="${complaint.id}">
                                <div class="input-group">
                                    <select class="form-select status-select" name="status" data-complaint-id="${complaint.id}">
                                        <option value="0">Choose...</option>
                                        <option value="inProgress">In Progress</option>
                                        <option value="resolved">Resolved</option>
                                        <option value="notResolved">Not Resolved</option>
                                    </select>
                                    <button type="button" class="btn btn-primary ms-2 update-btn" data-complaint-id="${complaint.id}">Update</button>
                                </div>
                            </c:if>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<!-- Modal for updating status -->
<div class="modal fade" id="statusModal" tabindex="-1" aria-labelledby="statusModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="statusModalLabel">Update Status</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="statusForm" action="updateStatus" method="post">
                    <input type="hidden" name="id" id="complaintId">
                    <input type="hidden" name="status" id="complaintStatus">
                    <div class="mb-3">
                        <label for="comments" class="form-label">Comment</label>
                        <textarea class="form-control" id="comments" name="comments" rows="3"></textarea>
                    </div>
                    <div class="mb-3" id="otpSendSection" style="display: none;">
                        <button type="button" class="btn btn-secondary mt-2" name="sendOtpButton" id="sendOtpButton">Send OTP</button>
                    </div>
                    <div class="mb-3" id="otpEnterSection" style="display: none;">
                        <label for="otp" class="form-label">Enter OTP</label>
                        <input type="text" class="form-control" id="otp" name="otp" onblur="validateOTP()">
                        <div id="otpError" class="text-danger mt-2"></div>
                    </div>
                    <button type="submit" name="btn" id="btn" class="btn btn-primary">Submit</button>
                       <c:if test="${not empty otpError}">
                            <div class="alert alert-danger mt-3">${otpError}</div>
                        </c:if>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="fixed-bottom">
    <footer class="d-flex flex-wrap justify-content-center align-items-center mb-0 py-3 my-4 border-top bg-dark">
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
<script>
   document.querySelectorAll('.update-btn').forEach(function(button) {
       button.addEventListener('click', function() {
           const complaintId = this.dataset.complaintId;
           const form = this.closest('form');
           const status = form.querySelector('.status-select').value;
           const modal = new bootstrap.Modal(document.getElementById('statusModal'), {
               backdrop: 'static',
               keyboard: false
           });

           document.getElementById('complaintId').value = complaintId;
           document.getElementById('complaintStatus').value = status;

           if (status === 'resolved') {
               document.getElementById('otpSendSection').style.display = 'block';
           } else {
               document.getElementById('otpSendSection').style.display = 'none';
               document.getElementById('otpEnterSection').style.display = 'none';
           }

           modal.show();
       });
   });

   document.getElementById('sendOtpButton').addEventListener('click', function() {
         const complaintId = document.getElementById('complaintId').value;

         const xhr = new XMLHttpRequest();
         xhr.open("POST", "sendOtp", true);
         xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
         xhr.onreadystatechange = function () {
             if (xhr.readyState === 4 && xhr.status === 200) {
                 document.getElementById('otpSendSection').style.display = 'none';
                 document.getElementById('otpEnterSection').style.display = 'block';
             } else if (xhr.readyState === 4) {
                 alert("Failed to send OTP.");
             }
         };
         xhr.send("id=" + complaintId);
     });


</script>
</body>
</html>
