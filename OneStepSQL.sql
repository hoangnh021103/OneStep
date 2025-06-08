
create database BanGiay
use BanGiay

CREATE DATABASE BanGiay;
USE BanGiay;

CREATE TABLE SanPham (
    ma_san_pham INT IDENTITY(1,1) PRIMARY KEY,
    ten_san_pham NVARCHAR(255) NOT NULL,
    ma_code NVARCHAR(20) NOT NULL ,
    mo_ta NVARCHAR(1000),
    duong_dan_anh NVARCHAR(200),
    trang_thai INT,
    ngay_cap_nhat DATE, -- ❗️ Đã đổi lại từ BIGINT thành DATE
    nguoi_tao NVARCHAR(200),
    nguoi_cap_nhat NVARCHAR(200),
    da_xoa TINYINT
);


CREATE TABLE ChiTietSanPham (
    ma_chi_tiet INT IDENTITY(1,1) PRIMARY KEY,
    thuong_hieu_id INT,
    kieu_dang_id INT,
    kich_co_id INT,
    san_pham_id INT FOREIGN KEY REFERENCES SanPham(ma_san_pham),
    chat_lieu_id INT,
    mau_sac_id INT,
    hang_san_xuat_id INT,
    de_giay_id INT,
    duong_dan_anh NVARCHAR(200),
    gia_tien FLOAT,
    so_luong_ton INT,
    trang_thai INT,
    tien_giam_gia FLOAT,
    da_xoa TINYINT,
    ngay_cap_nhat DATE,  
    nguoi_tao NVARCHAR(200),
    nguoi_cap_nhat NVARCHAR(200)
);

INSERT INTO SanPham (ten_san_pham, ma_code, mo_ta, duong_dan_anh, trang_thai, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa)
VALUES
(N'Giày Thể Thao AirMax', N'AM001', N'Giày thể thao nam, thoáng khí, đế êm', N'/images/airmax.jpg', 1, '2025-06-05', N'admin', N'admin', 0),
(N'Giày Chạy Bộ ZoomX', N'ZX002', N'Giày chạy bộ nhẹ, hỗ trợ tốt khi chạy đường dài', N'/images/zoomx.jpg', 1, '2025-06-05', N'admin', N'admin', 0),
(N'Giày Sneaker Classic', N'SC003', N'Giày sneaker phong cách cổ điển, thời trang', N'/images/classic.jpg', 1, '2025-06-05', N'admin', N'admin', 0),
(N'Giày Trail Running Terra', N'TRT004', N'Giày chạy địa hình, bền bỉ, chống trượt', N'/images/terra.jpg', 1, '2025-06-05', N'admin', N'admin', 0),
(N'Giày Lười Casual Easy', N'CE005', N'Giày lười nam, tiện lợi, phù hợp đi chơi và làm việc', N'/images/casual.jpg', 1, '2025-06-05', N'admin', N'admin', 0);

INSERT INTO ChiTietSanPham (thuong_hieu_id, kieu_dang_id, kich_co_id, san_pham_id, chat_lieu_id, mau_sac_id, hang_san_xuat_id, de_giay_id, duong_dan_anh, gia_tien, so_luong_ton, trang_thai, tien_giam_gia, da_xoa, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat)
VALUES
(1, 1, 42, 1, 3, 1, 2, 1, N'/images/details/airmax-1.jpg', 2500000, 100, 1, 200000, 0, '2025-06-05', N'admin', N'admin'),
(2, 2, 40, 2, 2, 2, 1, 2, N'/images/details/zoomx-1.jpg', 2800000, 50, 1, 300000, 0, '2025-06-05', N'admin', N'admin'),
(3, 3, 41, 3, 1, 3, 3, 3, N'/images/details/classic-1.jpg', 1800000, 150, 1, 100000, 0, '2025-06-05', N'admin', N'admin'),
(4, 4, 39, 4, 4, 4, 4, 4, N'/images/details/terra-1.jpg', 3200000, 30, 1, 400000, 0, '2025-06-05', N'admin', N'admin'),
(5, 5, 43, 5, 5, 5, 5, 5, N'/images/details/casual-1.jpg', 1500000, 80, 1, 0, 0, '2025-06-05', N'admin', N'admin');

SELECT * FROM dbo.SanPham;


select *from ChiTietSanPham
SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'san_pham';
