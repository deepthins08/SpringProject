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

    </style>
 <script src="/xworkzProject/js/signin.js"></script>
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

<c:choose>
    <c:when test="${!admin && !departmentAdmin}">
        <form action="signIn" method="post">
    </c:when>

    <c:when test="${admin}">
        <form action="admin/signin" method="post">
    </c:when>
    <c:when test="${departmentAdmin}">
        <form action="departmentAdmin/signin" method="post">
    </c:when>
</c:choose>

     <div class="container mt-5 mb-5 d-flex justify-content-center">
           <div class="card px-5 py-4 bg-light shadow p-3 mb-5 bg-white rounded">
               <div class="card-body">
<span class="text-danger">
<c:forEach items="${errors}" var="objectError">
${objectError.defaultMessage}<br>
</c:forEach>


</span>


   </span>
                   <span class="text-danger fs-6 fw-bold">${mailMsg}</span>
                <c:if test="${not empty errorsMsg}">
                             <div class="alert alert-danger" role="alert">${errorsMsg}</div>
                         </c:if>


                   <h6 class="card-title mb-3 text-dark fs-2">
                 <c:choose>
                                    <c:when test="${admin}">
                                        Admin
                                    </c:when>
                                    <c:when test="${departmentAdmin}">
                                        Department Admin
                                    </c:when>

                                </c:choose>

                   Sign In</h6>


                    <div class="mb-3">
                                       <label for="email" class="form-label text-dark">Email</label>
                                       <input type="email" class="form-control" name="email" value="${dto.email}"  id="email" aria-describedby="emailHelp">
                                       <span id="emailError" class="text-danger"></span>
                                   </div>

                   <div class="mb-3">
                       <label for="password" class="form-label text-dark">Password</label>
                       <input type="password" class="form-control" name="password" id="password" aria-describedby="phoneHelp" >
                       <span id="passwordError" class="text-danger"></span>

                   </div>


                 <div class="mb-3">
                                    <div class="captcha">
                                        <label for="captcha-input">Enter Captcha</label>
                                        <div id="captchaPreview" class="mb-2 bg-white p-2 text-center border"></div>
                                        <div class="captcha d-flex align-items-center">
                                            <input type="text" name="captcha" id="captcha" placeholder="Enter captcha text" class="form-control me-2">
                                            <button type="button" class="btn btn-secondary" onclick="generateCaptcha()">
                                                <i class="fas fa-sync-alt"></i>
                                                 &#8635;
                                            </button>
                                        </div>
                                        <span id="captchaError" class="text-danger"></span>
                                    </div>
                                </div>

          <div class="mb-1 mt-3">
                <div class="container">
                    <div class="row">
                      <div class="col">
                    <input type="submit" value="Sign In" name="btn" id="btn" class="btn btn-primary me-5" >
          </div>
           <div class="col">

          <a class="fw-bold" style="text-decoration:none;" href="ForgotPassword.jsp">Forgot Password ?</a>

           </div>
         </div>
         <c:if test="${!admin && !departmentAdmin}">
         <div class="mb-1 mt-3">
         <p>Don&#39t You have Registered Yet,
         <a class="fw-bold" style="text-decoration:none;" href="Signup.jsp">SignUp</a> here</p>
    </div>
</c:if>
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
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
</body>
</html>
