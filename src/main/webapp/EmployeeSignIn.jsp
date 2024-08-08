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
        .card {
            max-width: 400px;
            word-wrap: break-word;
        }
        .countdown {
            font-size: 1.1rem;
            color: red;
        }
    </style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" integrity="sha384-KyZXEAg3QhqLMpG8r+Knujsl5/ieIJ6gIFhvjaCln2gq5oWeFf3vSUcIB+q5VR7j" crossorigin="anonymous">
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
                   <a class="nav-link active text-light fs-5 " aria-current="page" href="index.jsp">Home</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-5 mb-5 d-flex justify-content-center">
    <div class="card px-5 py-4 bg-light shadow-lg p-3 mb-5 bg-white rounded">
        <div class="card-body">
            <span class="text-danger">
                <c:forEach items="${errors}" var="objectError">
                    ${objectError.defaultMessage}<br>
                </c:forEach>
            </span>
            <span class="text-success fs-6 fw-bold">${message}</span>
            <c:if test="${not empty errorsMsg}">
                <div class="alert alert-danger" role="alert">${errorsMsg}</div>
            </c:if>

            <h6 class="card-title mb-3 text-dark fs-2">Employee Sign In</h6>

            <form action="getOtp" method="post">
                <div class="form-group mb-3">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Enter your email Id" required value="${param.email}">
                    <span class="text-danger">${emailError}</span>
                </div>
                <div class="form-group mb-3 text-center">
                    <button class="btn btn-primary" id="getOtpBtn" type="submit">Get OTP</button>
                </div>
            </form>

            <c:if test="${param.email != null}">
                <form action="loginWithOtp" method="post">
                    <input type="hidden" name="email" value="${param.email}">
                    <div class="form-group mb-3">
                        <label for="otp">OTP</label>
                        <input type="text" class="form-control" id="otp" name="otp" placeholder="Enter your OTP" required>
                        <span class="text-danger">${otpError}</span>
                    </div>

                    <div class="form-group mb-3 text-center">
                        <button class="btn btn-primary" id="loginBtn" type="submit">Login</button>
                    </div>
                </form>

                <c:if test="${not empty otpGenerationTime}">
                    <div class="countdown" id="countdown"></div>
                    <script>
                        // Calculate the expiration time
                        var expirationTime = new Date('${otpGenerationTime}').getTime() + 60000; // 1 minute from OTP generation

                        function updateCountdown() {
                            var now = new Date().getTime();
                            var distance = expirationTime - now;

                            if (distance < 0) {
                                document.getElementById("countdown").innerHTML = "OTP has expired";
                                document.getElementById("loginBtn").disabled = true;
                                return;
                            }

                            var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                            var seconds = Math.floor((distance % (1000 * 60)) / 1000);

                            document.getElementById("countdown").innerHTML ="OTP Expires in :"+ minutes + "m " + seconds + "s ";

                            setTimeout(updateCountdown, 1000);
                        }

                        updateCountdown();
                    </script>
                </c:if>
            </c:if>
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
