<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/css/cssindex.css">
<title>Trang chủ</title>
</head>

<body>
<nav style="background-color: white ; height: 70px; display: flex;">
    <div style="width: 1200px; margin: auto;" class="navMenu">
        <a href="/index"><img src="/image/Logo/logoXINHColor.png" alt="Logo" width="117"></a>
        <form action="#" class="formFind">
            <input type="text" placeholder="Tìm kiếm...">
            <button type="submit">Tìm kiếm</button>
        </form>
        <ul class="ulMenu" style="width: 220px;">
            <li><a href="/index">Trang chủ</a></li>
            <li><a href="/login">Tài khoản</a></li>
            <li><a href="#">Giỏ hàng</a></li>
        </ul>
    </div>
</nav>
<div class="container">
    <div
        style="background-color: white; height: 330px; display: flex; justify-content: center; align-items: center; margin-top: 10px;">
        <h1 style="font-family: Asap Condensed;">Banner</h1>
    </div>
    <nav style="background-color: var(--color_main2); margin-top: 10px; height: 55px; display: flex;">
        <div class="container" style="margin: auto;">
            <ul class="ulMenu ulMenuTextColor" style="justify-content: space-evenly;">
                <li><a href="#">Cá nhân</a></li>
                <li><a href="#">Cơ thể</a></li>
                <li><a href="#">Da mặt</a></li>
                <li><a href="#">Làm đẹp</a></li>
                <li><a href="#">Tóc</a></li>
                <li><a href="#">Nước hoa</a></li>
            </ul>
        </div>
    </nav>
    <div style="margin-top: 20px;" class="container">
        <div class="listProducts">
            <p >Sản phẩm mới</p>
            <div class="layOutProduct">
                <a href="#" style="text-decoration: none; color: black;">
                    <div class="product">
                        <img src="/image/sanpham/sonKemMerzyAcademiaMellowTintAM1.jpeg" alt="">
                        <div class="inforProduct">
                            <p style="font-size: 15px;">Son kem Merzy Academia Mellow Tint AM1</p>
                            <p style="color: var(--color_main); font-weight: bold; font-size: 20px; margin-top: 5px;">
                                1000000<span>đ</span></p>
                            <p style="color: gray; font-size: 15px;"><del>2000000<span>đ</span></del></p>
                            
                        </div>
                    </div>
                </a>
            </div>
        </div>
    </div>
</div>
<footer>
    <div>
        
    </div>
</footer>
</body>
<script src="/js/jsView.js"></script>

</html>