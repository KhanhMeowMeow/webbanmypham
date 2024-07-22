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
    <nav style="background-color: white ; height: 70px; display: flex;">
        <div style="width: 1200px; margin: auto;" class="navMenu">
            <a href="/index"><img src="/image/Logo/logoXINHColor.png" alt="Logo" width="117"></a>

            <ul class="ulMenu" style="width: 220px;">
                <li><a href="/quanLySanPham">Quản lý</a></li>
                <li><a href="/login">Tài khoản</a></li>
                <li><a href="/gioHang">Giỏ hàng</a></li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <div class="formLogin">
            <div class="divSignUp">
                <div class="divCenter">
                    <div class="moduleLogin" style="align-items: center;">
                        <p class="p_label" style="margin: 0;">Đăng kí</p>
                    </div>
                    <div>
                        <p class="textBassic" style="font-size: 20px; color: var(--color_main); margin: 40px 0 40px 0;">Đăng kí để nhận ưu đãi hấp dẫn</p>
                        <div class="moduleLogin" style="align-items: center;">
                            <button class="btnStyle2" style="height: 50px; width: 130px;">Đăng kí</button>
                        </div>
                    </div>
                    <div style="display: none;">
                        <form action="" class="form">
                            <div class="moduleLogin">
                                <label for="TenDangNhap">Tên đăng nhập</label>
                                <input type="text" name="TenDangNhap">
                            </div>
                            <div class="moduleLogin">
                                <label for="MatKhau">Mật khẩu</label>
                                <input type="password" name="MatKhau">
                            </div>
                            <div class="moduleLogin">
                                <label style="color: red;">Sai mật khẩu</label>
                            </div>
                            <div class="moduleLogin" style="align-items: center; margin-top: 30px;">
                                <button class="btnStyle1" style="width: 130px; height: 50px;">Đăng
                                    nhập</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="divLogin">
                <div class="divCenter">
                    <div class="moduleLogin" style="align-items: center;">
                        <p class="p_label">Đăng nhập</p>
                    </div>
                    <div>

                    </div>
                    <div>
                        <form action="/submitLogin" class="form">
                            <div class="moduleLogin">
                                <label for="TenDangNhap">Tên đăng nhập</label>
                                <input type="text" name="TenDangNhap">
                            </div>
                            <div class="moduleLogin">
                                <label for="MatKhau">Mật khẩu</label>
                                <input type="password" name="MatKhau" style="margin-bottom: 10px;">
                            </div>
                            <div class="moduleLogin" style="height: 20px;">
                                <p class="error">${messLogin}</p>
                            </div>
                            <div class="moduleLogin" style="align-items: center; margin-top: 30px;">
                                <button class="btnStyle1" style="width: 130px; height: 50px;">Đăng nhập</button>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <footer>
        <div style="display: flex; justify-content: center;">
        </div>
    </footer>
</body>
<script>
   
</script>

</html>