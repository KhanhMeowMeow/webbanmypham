<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/csslogin.css">
    <title>Đăng nhập</title>
</head>

<body>
    <nav style="background-color:rgb(159, 37, 72) ; height: 70px; display: flex;">
        <div style="width: 80%; margin: auto;">
            <div class="navMenu">
                <div style="width: 250px;">
                    <a href="/index"><img src="/image/Logo/logoXINH.png" alt="Logo" width="120px"></a>
                </div>
                <ul class="ulMenu" style="width: 250px;">
                    <li><a href="/index">Trang chủ</a></li>
                    <li><a href="/login">Tài khoản</a></li>
                    <li><a href="#">Giỏ hàng</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="formLogin">
            <div class="divSignUp">
                <p class="p_Label">ĐĂNG KÍ</p>
                <form action="" class="form">
                    <div>
                        <label for="usernameSigup">Tên đăng nhập</label>
                        <input type="text">
                    </div>
                    <div>
                        <label for="passwordSignup">Mật khẩu</label>
                        <input type="password">
                    </div>
                    <div style="align-items: center;">
                        <button class="submitBtn">Đăng kí</button>
                    </div>
                </form>
                <div style="display: flex; flex-direction: column; align-items: center;">
                    <p style="font-size: 20px; font-weight: 500; height: 20px; color: var(--color_Backgroud); margin-top: 38px; margin-bottom: 40px;">Đăng kí để nhận nhiều ưu đãi hấp dẫn</p>
                    <button class="submitBtn" style="border: 1px solid; border-color: var(--color_Backgroud); font-size: var(--font-size_Normal); color: var(--color_Backgroud); background-color: white;">Đăng kí</button>
                </div>
            </div>
            <div class="divLogin">
                <p class="p_Label">ĐĂNG NHẬP</p>
                <form action="/submitLogin" class="form" method="post">
                    <div>
                        <label for="usernameLogin">Tên đăng nhập</label>
                        <input type="text" name="idNguoiDung"/>
                    </div>
                    <div style="margin-bottom: 30px;">
                        <label for="passwordLogin">Mật khẩu</label>
                        <input type="password" name="matKhau" />
                    </div>
                    <div style="align-items: center;">
                        <button type="submit" class="submitBtn">Đăng nhập</button>
                    </div>
                <form>
            </div>
        </div>
    </div>

    <footer>
        <div style="display: flex; justify-content: center;">
            <h1 style="color: white;">${messCC}</h1>
        </div>
    </footer>
</body>
<script>
    var messLogin = '${messLogin}';
    if(messLogin == 'successLogin'){
        alert('Đăng nhập thành công !')
    }
    if(messLogin == 'failureLogin'){
        alert('Đăng nhập thất bại !')
    }

    if(messLogin == 'userFale'){
        alert('Không thấy người dùng !')
    }
</script>
</html>