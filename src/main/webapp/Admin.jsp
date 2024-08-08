<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Page</title>
    <base href="http://localhost:8080/xworkzProject/">
    <style>
        .card {
            max-width: 400px;
            word-wrap: break-word;
        }
        .nav-link .badge {
            position: absolute;
            top: 0;
            right: 0;
            transform: translate(5%, 5%);
            background-color: red;
            color: white;
            border-radius: 50%;
            padding: 2px 5px;
        }
        .nav-item {
            position: relative;
        }
        .notification-sidebar {
            height: 100%;
            width: 0;
            position: fixed;
            top: 0;
            right: 0;
            background-color: #f8f9fa;
            overflow-x: hidden;
            transition: 0.5s;
            padding-top: 60px;
            box-shadow: -2px 0 5px rgba(0,0,0,0.3);
        }
        .notification-sidebar .notification-header {
            display: flex;
            justify-content: space-between;
            padding: 10px;
            background-color: #343a40;
            color: #fff;
        }
        .notification-sidebar .notification-list {
            padding: 10px;
        }
        .notification-item {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            cursor: pointer;
        }
        .notification-item.read {
            background-color: #e9ecef;
        }
        .notification-sidebar .btn-close {
            background: none;
            border: none;
            font-size: 1.5rem;
            color: #fff;
            cursor: pointer;
        }
        .notification-sidebar.open {
            width: 300px;
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" crossorigin="anonymous" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
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
                    <a class="nav-link active text-light fs-5" href="javascript:void(0);" onclick="toggleSidebar()">
                        <i class="fas fa-bell"></i>
                        <span class="badge bg-danger" id="notificationCount">${sessionScope.notificationCount}</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active text-light fs-5" aria-current="page" href="admin/adminLogout">Logout</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active text-light fs-5" aria-current="page" href="#">${admin.name}</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Notification Sidebar -->
<div id="notificationSidebar" class="notification-sidebar">
    <div class="notification-header">
        <h4>Notifications</h4>
        <button class="btn-close" onclick="toggleSidebar()">&times;</button>
    </div>
    <div class="notification-list">
        <c:forEach var="notification" items="${sessionScope.notifications}">
            <div class="notification-item ${notification.read ? 'read' : ''}" onclick="openComplaint(${notification.id})">
                <strong>${notification.type}</strong>
                <p><i>${notification.area}, ${notification.address}</i></p>
                <div class="notification-date-time m-0">
                    <p>${sessionScope.formattedDates[notification.id]}</p>
                    <p>${sessionScope.formattedTimes[notification.id]}</p>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Page Content -->
<div class="container mt-5 mb-5 d-flex justify-content-center">
    <div class="card px-5 py-4 bg-light w-100 shadow-lg p-3 mb-1 bg-white rounded" style="max-width: 600px;">
        <div class="mb-1 mt-3">
            <a class="dropdown-item fs-2" href="admin/viewUsers">View Users</a>
        </div>
    </div>
</div>
<div class="container mt-5 mb-5 d-flex justify-content-center">
    <div class="card px-5 py-4 bg-light w-100 shadow-lg p-3 mb-1 bg-white rounded" style="max-width: 600px;">
        <div class="mb-1 mt-3">
            <a class="dropdown-item fs-2" href="admin/adminViewComplaints">View Complaints</a>
        </div>
    </div>
</div>
<div class="container mt-5 mb-5 d-flex justify-content-center">
    <div class="card px-5 py-4 bg-light w-100 shadow-lg p-3 mb-1 bg-white rounded" style="max-width: 600px;">
        <div class="mb-1 mt-3">
            <a class="dropdown-item fs-2" href="admin/addDepartmentAdmin">Add Department Admin</a>
        </div>
    </div>
</div>
<div class="container mb-5  d-flex justify-content-center">
        <div class="card px-5 py-4 bg-light w-100 shadow-lg p-3 mb-5 bg-white rounded" style="max-width: 600px;">
            <div class="mb-1 mt-3">
    <a class="dropdown-item fs-2" href="/xworkzProject/admin/downloadComplaints">Download Complaints</a>
            </div>
        </div>
    </div>
<!-- Footer -->
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

<script>
    function toggleSidebar() {
        var sidebar = document.getElementById('notificationSidebar');
        sidebar.classList.toggle('open');
    }

    function openComplaint(complaintId) {
        // Mark the notification as read
        fetch('admin/markNotificationAsRead?notificationId=' + complaintId)
            .then(response => response.text())
            .then(result => {
                if (result === 'success') {
                    // Decrease the notification count by 1
                    var notificationCountElem = document.getElementById('notificationCount');
                    var count = parseInt(notificationCountElem.innerText);
                    if (count > 0) {
                        notificationCountElem.innerText = count - 1;
                    }

                    // Redirect to the complaint details page
                    window.location.href = 'admin/viewComplaints?id=' + complaintId;
                } else {
                    console.error('Failed to mark notification as read');
                }
            })
            .catch(error => console.error('Error:', error));
    }


    function fetchNotifications() {
            fetch('admin/getNotifications')
                .then(response => response.json())
                .then(data => {
                    // Update notification count
                    var notificationCountElem = document.getElementById('notificationCount');
                    notificationCountElem.innerText = data.notificationCount;

                    // Update notification list
                    var notificationListElem = document.getElementById('notificationList');
                    notificationListElem.innerHTML = '';
                    data.notifications.forEach(notification => {
                        var notificationItem = document.createElement('div');
                        notificationItem.className = 'notification-item ' + (notification.read ? 'read' : '');
                        notificationItem.onclick = function() {
                            openComplaint(notification.id);
                        };

                        var strongElem = document.createElement('strong');
                        strongElem.innerText = notification.type;

                        var pElem = document.createElement('p');
                        pElem.innerHTML = '<i>' + notification.area + ', ' + notification.address + '</i>';

                        var dateElem = document.createElement('p');
                        dateElem.innerText = notification.formattedDate;

                        var timeElem = document.createElement('p');
                        timeElem.innerText = notification.formattedTime;

                        var notificationDateTimeElem = document.createElement('div');
                        notificationDateTimeElem.className = 'notification-date-time m-0';
                        notificationDateTimeElem.appendChild(dateElem);
                        notificationDateTimeElem.appendChild(timeElem);

                        notificationItem.appendChild(strongElem);
                        notificationItem.appendChild(pElem);
                        notificationItem.appendChild(notificationDateTimeElem);

                        notificationListElem.appendChild(notificationItem);
                    });
                })
                .catch(error => console.error('Error fetching notifications:', error));
        }

        // Periodically fetch notifications every 30 seconds
        setInterval(fetchNotifications, 500);
</script>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
</body>
</html>
