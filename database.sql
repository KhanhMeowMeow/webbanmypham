CREATE DATABASE webMyPham

use webMyPham

CREATE Table NguoiDung (
    MaNguoiDung Nvarchar(50) PRIMARY KEY,
    MatKhau VARCHAR(50) not NULL,
    TenNguoiDung NvarChar(100),
    VaiTro BIT not null,
    Email Nvarchar(100),
    SoDienThoai VARCHAR(12),
    DiaChi Nvarchar(100)
)



CREATE Table SanPham (
    MaSanPham Nvarchar(10) PRIMARY KEY,
    TenSanPham Nvarchar(max),
    SoLuong INT,
    HinhAnh Nvarchar(max),
    LoaiSanPham Nvarchar(10),
    MauSac Nvarchar(max),
    MoTa Nvarchar(max)
)

CREATE TABLE LoaiSanPham (
    MaLoaiSanPham Nvarchar(10) PRIMARY KEY,
    TenLoaiSanPham Nvarchar(200)
)


INSERT INTO MguoiDung VALUES
('quockhanh', '123456', N'Nguyễn Quốc Khánh', 1),
('trungtin', '123456', N'Ngô Trung Tín', 0),
('anhviet', '123456', N'Nguyễn Văn Anh Việt', 0)

SELECT * FROM "NguoiDung"
