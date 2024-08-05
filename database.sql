CREATE DATABASE webMyPham

use webMyPham

CREATE Table NguoiDung (
    MaNguoiDung Nvarchar(100) PRIMARY KEY,
    MatKhau VARCHAR(50) not NULL,
    TenNguoiDung NvarChar(100),
    VaiTro BIT not null,
    Email Nvarchar(100),
    SoDienThoai VARCHAR(12),
    DiaChi Nvarchar(200),
    TrangThai bit,
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
    SanPham Nvarchar(10),
    SoLuong int
)

CREATE Table SanPham (
    MaSanPham Nvarchar(10) PRIMARY KEY,
    TenSanPham Nvarchar(max),
    SoLuong INT,
    HinhAnh Nvarchar(max),
    LoaiSanPham Nvarchar(10),
    MauSac Nvarchar(max),
    MoTa Nvarchar(max),
    DonGia Float,
    TrangThai bit
)

CREATE TABLE LoaiSanPham (
    MaLoaiSanPham Nvarchar(10) PRIMARY KEY,
    TenLoaiSanPham Nvarchar(300)
)

CREATE Table DonHang (
    MaDonHang Nvarchar(10) PRIMARY KEY,
    NguoiDung Nvarchar(100),
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
('ND000', '1111111', N'Máy tính 000', 1, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND001', '1111111', N'Nguyễn Quốc Khánh', 1, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND002', '1111111', N'Ngô Trung Tín', 0, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND003', '1111111', N'Nguyễn An Bình', 0, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND004', '1111111', N'Trần Thị Hồng', 0, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND005', '1111111', N'Nguyễn Vũ Minh Nhân', 0, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND006', '1111111', N'Lê Văn Chiến', 0, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND007', '1111111', N'Lâm Hoài Thương', 0, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND008', '1111111', N'Nguyễn Thương', 0, 'abc@gmail.com', '0987654321', N'Quận12'),
('ND009', '1111111', N'Nguyễn Văn Anh Việt', 0, 'abc@gmail.com', '0987654321', N'Quận12')

SELECT * FROM "NguoiDung"

INSERT INTO LoaiSanPham VALUES
('LSP001', N'Nhập từ Mỹ Tho'),
('LSP002', N'Nhập từ Mỹ Đình'),
('LSP003', N'Nhập từ Trung Quốc'),
('LSP004', N'Nhập từ Mẽo'),
('LSP005', N'Hàng nhập nội'),
('LSP006', N'Hàng nhập sĩ'),
('LSP007', N'Hàng tồn kho')

INSERT INTO SanPham VALUES
('SP001',N'Son Môi',100,'hinhanh.jpg','LSP001',N'Đỏ gạch',N'Sản phẩm xịn',25000000),
('SP002',N'Son Môi',100,'hinhanh.jpg','LSP001',N'Đỏ gạch',N'Sản phẩm xịn',25000000),
('SP003',N'Son Môi',100,'hinhanh.jpg','LSP001',N'Đỏ gạch',N'Sản phẩm xịn',25000000),
('SP004',N'Son Môi',100,'hinhanh.jpg','LSP001',N'Đỏ gạch',N'Sản phẩm xịn',25000000),
('SP005',N'Son Môi',100,'hinhanh.jpg','LSP001',N'Đỏ gạch',N'Sản phẩm xịn',25000000),
('SP006',N'Son Môi',100,'hinhanh.jpg','LSP001',N'Đỏ gạch',N'Sản phẩm xịn',25000000)

INSERT INTO DonHang VALUES
('DH001', 'ND001', 1000000, N'Quận 12', '2024-12-12'),
('DH002', 'ND001', 1284000, N'Quận 12', '2024-11-04'),
('DH003', 'ND002', 300000, N'Quận 12', '2024-10-06'),
('DH004', 'ND004', 500000, N'Quận 12', '2024-09-06'),
('DH005', 'ND002', 340000, N'Quận 12', '2024-08-02'),
('DH006', 'ND005', 100900, N'Quận 12', '2024-08-12'),
('DH007', 'ND002', 400000, N'Quận 12', '2024-07-12'),
('DH008', 'ND003', 400000, N'Quận 12', '2024-01-09'),
('DH009', 'ND003', 2004000, N'Quận 12', '2024-02-22'),
('DH010', 'ND004', 90000, N'Quận 12', '2024-05-30'),
('DH011', 'ND005', 100000, N'Quận 12', '2024-03-07'),
('DH012', 'ND006', 500000, N'Quận 12', '2024-04-21'),
('DH013', 'ND006', 100009000, N'Quận 12', '2024-01-10')

INSERT INTO DonHangChiTiet VALUES
('DHCT001', 'DH001', 'SP003', 10, 10000000),
('DHCT002', 'DH001', 'SP002', 10, 10000000),
('DHCT003', 'DH001', 'SP006', 10, 10000000),
('DHCT004', 'DH002', 'SP003', 10, 10000000),
('DHCT005', 'DH002', 'SP007', 10, 10000000),
('DHCT006', 'DH002', 'SP002', 10, 10000000),
('DHCT007', 'DH004', 'SP007', 10, 10000000),
('DHCT008', 'DH004', 'SP003', 10, 10000000),
('DHCT009', 'DH004', 'SP010', 10, 10000000),
('DHCT010', 'DH003', 'SP030', 10, 10000000),
('DHCT011', 'DH003', 'SP020', 10, 10000000),
('DHCT012', 'DH005', 'SP023', 10, 10000000),
('DHCT013', 'DH007', 'SP027', 10, 10000000),
('DHCT014', 'DH008', 'SP013', 10, 10000000),
('DHCT015', 'DH008', 'SP003', 10, 10000000),
('DHCT016', 'DH006', 'SP013', 10, 10000000),
('DHCT017', 'DH006', 'SP023', 10, 10000000),
('DHCT018', 'DH009', 'SP022', 10, 10000000),
('DHCT019', 'DH010', 'SP020', 10, 10000000),
('DHCT020', 'DH010', 'SP015', 10, 10000000),
('DHCT021', 'DH011', 'SP001', 10, 10000000),
('DHCT022', 'DH011', 'SP004', 10, 10000000),
('DHCT023', 'DH012', 'SP008', 10, 10000000),
('DHCT024', 'DH008', 'SP003', 10, 10000000),
('DHCT025', 'DH012', 'SP005', 10, 10000000),
('DHCT026', 'DH008', 'SP009', 10, 10000000),
('DHCT027', 'DH012', 'SP011', 10, 10000000),
('DHCT028', 'DH010', 'SP016', 10, 10000000),
('DHCT029', 'DH005', 'SP018', 10, 10000000),
('DHCT030', 'DH006', 'SP019', 10, 10000000)

SELECT dhct.SanPham
FROM DonHangChiTiet dhct 
GROUP BY dhct.SanPham
ORDER BY COUNT(dhct.SanPham) desc
