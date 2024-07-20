CREATE DATABASE webMyPham

use webMyPham

CREATE Table NguoiDung (
    MaNguoiDung Nvarchar(100) PRIMARY KEY,
    MatKhau VARCHAR(50) not NULL,
    TenNguoiDung NvarChar(100),
    VaiTro BIT not null,
    Email Nvarchar(100),
    SoDienThoai VARCHAR(12),
    DiaChi Nvarchar(200)
)

CREATE TABLE GioHang (
    MaGioHang Nvarchar(10) PRIMARY KEY,
    NguoiDung Nvarchar(100),
    SoLuongSanPham INT,
    TongTien FLOAT
)

CREATE TABLE GioHangChiTiet (
    MaGioHangChiTiet Nvarchar(10) PRIMARY KEY,
    GioHang Nvarchar(10),
    SanPham Nvarchar(10)
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
    TenLoaiSanPham Nvarchar(300)
)

CREATE Table DonHang (
    MaDonHang Nvarchar(10) PRIMARY KEY,
    NguoiDung Nvarchar(100),
    ThoiGian TIME,
    NgayThang DATE,
    TongTien FLOAT,
    DiaChiGia Nvarchar(200)
)

CREATE TABLE DonHangChiTiet (
    MaDonHangChiTiet Nvarchar(10) PRIMARY KEY,
    DonHang Nvarchar(10),
    SanPham Nvarchar(10),
    SoLuong INT,
    TongGia FLOAT
)

ALTER Table SanPham Add ConsTraint FK_SanPham_LoaiSanPham
Foreign Key (LoaiSanPham) REFERENCES LoaiSanPham(MaLoaiSanPham)

ALTER Table DonHangChiTiet Add ConsTraint FK_DonHangChiTiet_SanPham
Foreign Key (SanPham) REFERENCES SanPham(MaSanPham)

ALTER Table DonHangChiTiet Add ConsTraint FK_DonHangChiTiet_DonHang
Foreign Key (DonHang) REFERENCES DonHang(MaDonHang)

ALTER Table DonHang Add ConsTraint FK_DonHang_NguoiDung
Foreign Key (NguoiDung) REFERENCES NguoiDung(MaNguoiDung)

ALTER Table GioHang Add ConsTraint FK_GioHang_NguoiDung
Foreign Key (NguoiDung) REFERENCES NguoiDung(MaNguoiDung)

ALTER Table GioHangChiTiet Add ConsTraint FK_GioHangChiTiet_GioHang
Foreign Key (GioHang) REFERENCES GioHang(MaGioHang)

ALTER Table GioHangChiTiet Add ConsTraint FK_GioHangChiTiet_SanPham
Foreign Key (SanPham) REFERENCES SanPham(MaSanPham)

SELECT * FROM "NguoiDung"

INSERT INTO NguoiDung VALUES
-- ('ND000', '1111111', N'Máy tính 000', 0, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND001', '1111111', N'Nguyễn Quốc Khánh', 1, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND002', '1111111', N'Ngô Trung Tín', 0, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND003', '1111111', N'Nguyễn An Bình', 0, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND004', '1111111', N'Trần Thị Hồng', 0, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND005', '1111111', N'Nguyễn Vũ Minh Nhân', 0, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND006', '1111111', N'Lê Văn Chiến', 0, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND007', '1111111', N'Lâm Hoài Thương', 0, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND008', '1111111', N'Nguyễn Thương', 0, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND009', '1111111', N'Nguyễn Văn Anh Việt', 0, 'abc@gmail.com', '0987654321', N'Quận12')

