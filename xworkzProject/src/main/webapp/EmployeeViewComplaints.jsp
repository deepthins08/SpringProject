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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
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
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-light fs-6 " href="#" id="complaintsDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        View Complaints
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="complaintsDropdown">
                        <li><a class="dropdown-item" href="DepartmentAdmin.jsp">Home</a></li>
                        <li><a class="dropdown-item" href="departmentAdmin/addEmployee">Add Employees</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="shadow-lg p-3 mb-5 bg-white rounded">
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
                <tr class="text-dark fw-bold">
                    <th scope="row">${id.index + 1}</th>
                    <td>${complaint.type}</td>
                    <td>${complaint.area}</td>
                    <td>${complaint.country}</td>
                    <td>${complaint.state}</td>
                    <td>${complaint.city}</td>
                    <td>${complaint.description}</td>
                    <td>${complaint.status}</td>
                    <td class="d-none d-md-table-cell">
                        <form action="updateStatus" method="post">
                            <c:if test="${complaint.status != 'resolved'}">
                                <input type="hidden" name="id" value="${complaint.id}">
                                <div class="input-group">
                                    <select class="form-select status-select" name="status" data-complaint-id="${complaint.id}">
                                        <option value="0" ${selectedType == null ? 'selected' : ''}>Choose...</option>
                                        <option value="inProgress" ${selectedType == 'inProgress' ? 'selected' : ''}>In Progress</option>
                                        <option value="resolved" ${selectedType == 'resolved' ? 'selected' : ''}>Resolved</option>
                                        <option value="notResolved" ${selectedType == 'notResolved' ? 'selected' : ''}>Not Resolved</option>
                                    </select>
                                </div>
                            </c:if>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<!-- Add a new modal for OTP handling -->
<div class="modal fade" id="statusModal" tabindex="-1" aria-labelledby="statusModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="statusModalLabel">Update Status</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="statusForm" action="updateStatus" method="post">
                    <input type="hidden" name="id" id="complaintId" value="">
                    <input type="hidden" name="status" id="complaintStatus" value="">

                    <!-- Comment Section -->
                    <div class="mb-3">
                        <label for="comments" class="form-label">Comment</label>
                        <textarea class="form-control" id="comments" name="comments" rows="3"></textarea>
                    </div>

                    <!-- Send OTP Section -->
                                     <div class="mb-3" id="otpSendSection" style="display: none;">
                                         <form id="sendOtpForm" action="sendOtp" method="post">
                                             <input type="hidden" name="id" id="otpComplaintId">
                                             <button type="button" class="btn btn-secondary mt-2" id="sendOtpButton">Send OTP</button>
                                         </form>
                                     </div>

                                     <!-- Enter OTP Section (Initially Hidden) -->
                                     <div class="mb-3" id="otpEnterSection" style="display: none;">
                                         <label for="otp" class="form-label">Enter OTP</label>
                                         <input type="text" class="form-control" id="otp" name="otp">
                                     </div>

                    <button type="submit" class="btn btn-primary">Submit</button>
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

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
<script>
   document.querySelectorAll('.status-select').forEach(function(selectElement) {
          selectElement.addEventListener('change', function() {
              const complaintId = this.dataset.complaintId;
              const status = this.value;
              const modal = new bootstrap.Modal(document.getElementById('statusModal'), {
                  backdrop: 'static',
                  keyboard: false
              });
              document.getElementById('complaintId').value = complaintId;
              document.getElementById('complaintStatus').value = status;

              if (status === 'resolved') {
                  document.getElementById('otpSendSection').style.display = 'block'; // Show Send OTP button
                  document.getElementById('otpEnterSection').style.display = 'block'; // Show Enter OTP field
              } else {
                  document.getElementById('otpSendSection').style.display = 'none'; // Hide Send OTP button
                  document.getElementById('otpEnterSection').style.display = 'none'; // Hide Enter OTP field
              }
              modal.show();
          });
      });

      document.getElementById('sendOtpButton').addEventListener('click', function(event) {
          event.preventDefault(); // Prevent form submission
          const form = document.getElementById('statusForm');
          const formData = new FormData(form);

          fetch(form.action, {
              method: 'POST',
              body: formData
          }).then(response => {
              if (response.ok) {
                  return response.json();
              } else {
                  throw new Error('Failed to send OTP');
              }
          }).then(data => {
              alert('OTP sent successfully!');
              // Enable OTP input field and update button
              document.getElementById('otpEnterSection').style.display = 'block';
              document.getElementById('sendOtpButton').disabled = true;
          }).catch(error => {
              console.error('Error:', error);
              alert('Failed to send OTP. Please try again.');
          });
      });
</script>
</body>
</html>
