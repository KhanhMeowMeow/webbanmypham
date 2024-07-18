CREATE DATABASE webMyPham

use webMyPham

CREATE Table nguoiDung (
    idNguoiDung Nvarchar(50) PRIMARY KEY,
    matKhau VARCHAR(50) not NULL,
    tenNguoiDung NvarChar(100),
    vaiTro BIT not null
)

INSERT INTO nguoidung VALUES
('quockhanh', '123456', N'Nguyễn Quốc Khánh', 1),
('trungtin', '123456', N'Ngô Trung Tín', 0),
('anhviet', '123456', N'Nguyễn Văn Anh Việt', 0)

SELECT * FROM "nguoiDung"
