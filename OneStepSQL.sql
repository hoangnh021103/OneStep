
use BanGiay

--------------------------------------------------------------------------------
-- 1. BẢNG LOOKUP / DANH MỤC
--------------------------------------------------------------------------------
-- Bảng ThuongHieu
CREATE TABLE ThuongHieu (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten NVARCHAR(255) NOT NULL,
    trang_thai INT,
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);

-- Bảng KieuDang
CREATE TABLE KieuDang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten NVARCHAR(255) NOT NULL,
    trang_thai INT,
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);

-- Bảng KichCo
CREATE TABLE KichCo (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten NVARCHAR(255) NOT NULL,
    trang_thai INT,
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);

-- Bảng MauSac
CREATE TABLE MauSac (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten NVARCHAR(255) NOT NULL,
    ma NVARCHAR(10) NOT NULL,
    trang_thai INT,
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);

-- Bảng PhuongThucThanhToan
CREATE TABLE PhuongThucThanhToan (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten NVARCHAR(255) NOT NULL,
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);

-- Bảng Voucher
CREATE TABLE Voucher (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ma NVARCHAR(10) NOT NULL,
    ten NVARCHAR(255),
    loai INT,
    gia_tri FLOAT,
    dieu_kien FLOAT,
    so_luong INT,
    ngay_bat_dau DATE,
    ngay_ket_thuc DATE,
    duong_dan_anh NVARCHAR(255),
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);

-- Bảng DeGiay
CREATE TABLE DeGiay (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten NVARCHAR(255) NOT NULL,
    trang_thai INT,
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);

-- Bảng PhuongThucVanChuyen
CREATE TABLE PhuongThucVanChuyen (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten NVARCHAR(255) NOT NULL,
    mo_ta NVARCHAR(1000),
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);

-- Bảng ChatLieu
CREATE TABLE ChatLieu (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten NVARCHAR(255) NOT NULL,
    trang_thai INT,
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);
GO

--------------------------------------------------------------------------------
-- 2. BẢNG SẢN PHẨM & CHI TIẾT SẢN PHẨM
--------------------------------------------------------------------------------
-- Bảng SanPham
CREATE TABLE SanPham (
    ma_san_pham INT IDENTITY(1,1) PRIMARY KEY,
    ten_san_pham NVARCHAR(255) NOT NULL,
    ma_code NVARCHAR(20) NOT NULL,
    mo_ta NVARCHAR(1000),
    thuong_hieu_id INT NOT NULL REFERENCES ThuongHieu(id),
    chat_lieu_id INT NOT NULL REFERENCES ChatLieu(id),
    de_giay_id INT NOT NULL REFERENCES DeGiay(id),
    kieu_dang_id INT NOT NULL REFERENCES KieuDang(id),
    duong_dan_anh NVARCHAR(255),
    trang_thai INT,
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(200),
    nguoi_cap_nhat NVARCHAR(200),
    da_xoa TINYINT
);

-- Bảng ChiTietSanPham
CREATE TABLE ChiTietSanPham (
    ma_chi_tiet INT IDENTITY(1,1) PRIMARY KEY,
    kich_co_id INT NOT NULL REFERENCES KichCo(id),
    san_pham_id INT NOT NULL REFERENCES SanPham(ma_san_pham),
    mau_sac_id INT NOT NULL REFERENCES MauSac(id),
    duong_dan_anh NVARCHAR(255) NULL,
    gia_tien FLOAT NOT NULL,
    so_luong_ton INT NOT NULL,
    trang_thai INT NOT NULL,
    tien_giam_gia FLOAT NOT NULL DEFAULT 0,
    da_xoa TINYINT NOT NULL DEFAULT 0,
    ngay_cap_nhat DATE NOT NULL DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(200) NOT NULL,
    nguoi_cap_nhat NVARCHAR(200) NOT NULL
);
GO

--------------------------------------------------------------------------------
-- 3. BẢNG VAI TRÒ & NHÂN VIÊN
--------------------------------------------------------------------------------
CREATE TABLE VaiTro (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ten_vai_tro NVARCHAR(255) NOT NULL,
    ngay_tao DATE,
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);

CREATE TABLE NhanVien (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ho_ten NVARCHAR(255),
    ngay_sinh DATE,
    gioi_tinh NVARCHAR(50),
    email NVARCHAR(255),
    mat_khau NVARCHAR(255) NOT NULL,
    so_dien_thoai NVARCHAR(20),
    dia_chi NVARCHAR(500),
    url_anh NVARCHAR(255),
    vai_tro_id INT REFERENCES VaiTro(id),
    ngay_tao DATE,
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);
GO

--------------------------------------------------------------------------------
-- 4. BẢNG KHÁCH HÀNG & ĐỊA CHỈ
--------------------------------------------------------------------------------
CREATE TABLE KhachHang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ho_ten NVARCHAR(255),
    ngay_sinh DATE,
    gioi_tinh NVARCHAR(50),
    email NVARCHAR(255),
    mat_khau NVARCHAR(255) NOT NULL,
    so_dien_thoai NVARCHAR(20),
    url_anh NVARCHAR(255),
    ngay_tao DATE,
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);

CREATE TABLE DiaChi (
    id INT IDENTITY(1,1) PRIMARY KEY,
    khach_hang_id INT REFERENCES KhachHang(id),
    ma_quan NVARCHAR(50),
    ten_quan NVARCHAR(50),
    ma_tinh NVARCHAR(50),
    ten_tinh NVARCHAR(50),
    ma_phuong NVARCHAR(50),
    ten_phuong NVARCHAR(50),
    la_mac_dinh BIT,
    thong_tin_them NVARCHAR(255),
    so_dien_thoai NVARCHAR(20),
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);
GO

--------------------------------------------------------------------------------
-- 5. BẢNG ĐƠN HÀNG & LỊCH SỬ
--------------------------------------------------------------------------------
CREATE TABLE DonHang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    khach_hang_id INT REFERENCES KhachHang(id),
    nhan_vien_id INT REFERENCES NhanVien(id),
    voucher_id INT REFERENCES Voucher(id),
    dia_chi_id INT REFERENCES DiaChi(id),
    phuong_thuc_van_chuyen_id INT REFERENCES PhuongThucVanChuyen(id),
    so_dien_thoai NVARCHAR(20),
    ho_ten NVARCHAR(255),
    email NVARCHAR(255),
    tong_tien_goc FLOAT,
    tien_giam FLOAT,
    tong_tien FLOAT,
    tien_ship FLOAT,
    ngay_xac_nhan DATE,
    ngay_du_kien DATE,
    ngay_nhan DATE,
    loai_don INT,
    ghi_chu NVARCHAR(1000),
    ma_don NVARCHAR(10) NOT NULL,
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);

CREATE TABLE LichSuDonHang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    don_hang_id INT UNIQUE REFERENCES DonHang(id),
    trang_thai INT,
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);
GO

--------------------------------------------------------------------------------
-- 6. BẢNG GIỎ HÀNG & CHI TIẾT
--------------------------------------------------------------------------------
CREATE TABLE GioHang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    khach_hang_id INT REFERENCES KhachHang(id),
    ngay_tao DATE,
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);

CREATE TABLE ChiTietGioHang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    gio_hang_id INT NOT NULL REFERENCES GioHang(id) ON DELETE CASCADE,
    chi_tiet_san_pham_id INT NOT NULL REFERENCES ChiTietSanPham(ma_chi_tiet),
    so_luong INT NOT NULL,
    ngay_cap_nhat DATE NOT NULL DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255) NOT NULL,
    nguoi_cap_nhat NVARCHAR(255) NOT NULL,
    da_xoa TINYINT NOT NULL DEFAULT 0
);

CREATE TABLE ChiTietDonHang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    don_hang_id INT NOT NULL REFERENCES DonHang(id) ON DELETE CASCADE,
    chi_tiet_san_pham_id INT NOT NULL REFERENCES ChiTietSanPham(ma_chi_tiet),
    so_luong INT NOT NULL,
    don_gia FLOAT NOT NULL,
    tong_tien FLOAT NOT NULL,
    trang_thai INT NOT NULL,
    ngay_cap_nhat DATE NOT NULL DEFAULT GETDATE(),
    nguoi_tao NVARCHAR(255) NOT NULL,
    nguoi_cap_nhat NVARCHAR(255) NOT NULL,
    da_xoa TINYINT NOT NULL DEFAULT 0
);
GO

--------------------------------------------------------------------------------
-- 7. BẢNG THANH TOÁN
--------------------------------------------------------------------------------
CREATE TABLE ThanhToan (
    id INT IDENTITY(1,1) PRIMARY KEY,
    don_hang_id INT UNIQUE REFERENCES DonHang(id),
    phuong_thuc_id INT REFERENCES PhuongThucThanhToan(id),
    ma_giao_dich NVARCHAR(50),
    tong_tien FLOAT,
    mo_ta NVARCHAR(1000),
    trang_thai INT,
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);
GO

--------------------------------------------------------------------------------
-- 8. BẢNG TRẢ HÀNG & LỊCH SỬ
--------------------------------------------------------------------------------
CREATE TABLE PhieuTraHang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ma NVARCHAR(10) NOT NULL,
    gia_tri FLOAT,
    so_tien_phai_tra REAL,
    thong_tin_thanh_toan NVARCHAR(255),
    hinh_thuc_thanh_toan NVARCHAR(255),
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);

CREATE TABLE LichSuPhieuTraHang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    phieu_tra_hang_id INT REFERENCES PhieuTraHang(id),
    trang_thai_hanh_dong SMALLINT,
    ghi_chu NVARCHAR(1000),
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);
GO

--------------------------------------------------------------------------------
-- 9. BẢNG SẢN PHẨM KHUYẾN MÃI
--------------------------------------------------------------------------------
CREATE TABLE SanPhamKhuyenMai (
    id INT IDENTITY(1,1) PRIMARY KEY,
    san_pham_id INT REFERENCES SanPham(ma_san_pham),
    khuyen_mai_id INT REFERENCES Voucher(id),
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);
GO

--------------------------------------------------------------------------------
-- 10. BẢNG CHI TIẾT PHIẾU TRẢ HÀNG
--------------------------------------------------------------------------------
CREATE TABLE ChiTietPhieuTraHang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    phieu_tra_hang_id INT REFERENCES PhieuTraHang(id),
    chi_tiet_san_pham_id INT REFERENCES ChiTietSanPham(ma_chi_tiet),
    so_luong INT,
    gia_tri FLOAT,
    ghi_chu NVARCHAR(1000),
    ngay_cap_nhat DATE,
    nguoi_tao NVARCHAR(255),
    nguoi_cap_nhat NVARCHAR(255),
    da_xoa TINYINT
);
GO

--------------------------------------------------------------------------------
-- 11. DỮ LIỆU MẪU CHO CÁC BẢNG LOOKUP / DANH MỤC
--------------------------------------------------------------------------------
INSERT INTO ThuongHieu (ten, trang_thai, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(N'Nike', 1, '2025-06-01', N'admin', N'admin', 0),
(N'Adidas', 1, '2025-06-01', N'admin', N'admin', 0),
(N'Puma', 1, '2025-06-01', N'admin', N'admin', 0),
(N'Reebok', 1, '2025-06-01', N'admin', N'admin', 0),
(N'New Balance', 1, '2025-06-01', N'admin', N'admin', 0);

INSERT INTO KieuDang (ten, trang_thai, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(N'Sneaker', 1, '2025-06-02', N'admin', N'admin', 0),
(N'Thể Thao', 1, '2025-06-02', N'admin', N'admin', 0),
(N'Casual', 1, '2025-06-02', N'admin', N'admin', 0),
(N'Running', 1, '2025-06-02', N'admin', N'admin', 0),
(N'Basketball', 1, '2025-06-02', N'admin', N'admin', 0);

INSERT INTO KichCo (ten, trang_thai, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(N'38', 1, '2025-06-03', N'admin', N'admin', 0),
(N'39', 1, '2025-06-03', N'admin', N'admin', 0),
(N'40', 1, '2025-06-03', N'admin', N'admin', 0),
(N'41', 1, '2025-06-03', N'admin', N'admin', 0),
(N'42', 1, '2025-06-03', N'admin', N'admin', 0);

INSERT INTO ChatLieu (ten, trang_thai, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(N'Da Thật', 1, '2025-06-04', N'admin', N'admin', 0),
(N'Vải Mesh', 1, '2025-06-04', N'admin', N'admin', 0),
(N'Synthetic', 1, '2025-06-04', N'admin', N'admin', 0),
(N'Da PU', 1, '2025-06-04', N'admin', N'admin', 0),
(N'Canvas', 1, '2025-06-04', N'admin', N'admin', 0);

INSERT INTO MauSac (ten, ma, trang_thai, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(N'Trắng', N'WHITE', 1, '2025-06-05', N'admin', N'admin', 0),
(N'Đen', N'BLACK', 1, '2025-06-05', N'admin', N'admin', 0),
(N'Đỏ', N'RED', 1, '2025-06-05', N'admin', N'admin', 0),
(N'Xanh', N'BLUE', 1, '2025-06-05', N'admin', N'admin', 0),
(N'Xám', N'GRAY', 1, '2025-06-05', N'admin', N'admin', 0);

INSERT INTO PhuongThucThanhToan (ten, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(N'COD', '2025-06-06', N'admin', N'admin', 0),
(N'VNPAY', '2025-06-06', N'admin', N'admin', 0),
(N'MoMo', '2025-06-06', N'admin', N'admin', 0),
(N'Credit Card', '2025-06-06', N'admin', N'admin', 0),
(N'Internet Banking', '2025-06-06', N'admin', N'admin', 0);

INSERT INTO Voucher (ma, ten, loai, gia_tri, dieu_kien, so_luong, ngay_bat_dau, ngay_ket_thuc, duong_dan_anh, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(N'GIAM10', N'Giảm 10%', 0, 10, 1000000, 50, '2025-06-01', '2025-06-30', N'/imgs/v1.jpg', '2025-06-01', N'admin', N'admin', 0),
(N'GIAM50K', N'Giảm 50k', 1, 50000, 200000, 100, '2025-06-01', '2025-07-01', N'/imgs/v2.jpg', '2025-06-01', N'admin', N'admin', 0),
(N'FREESHIP', N'Free Ship', 1, 0, 0, 999, '2025-06-01', '2025-12-31', N'/imgs/v3.jpg', '2025-06-01', N'admin', N'admin', 0),
(N'LUCKY7', N'Giảm 7%', 0, 7, 500000, 30, '2025-06-05', '2025-06-25', N'/imgs/v4.jpg', '2025-06-05', N'admin', N'admin', 0),
(N'SUMMER', N'Khuyến mãi hè', 1, 100000, 500000, 20, '2025-06-10', '2025-07-10', N'/imgs/v5.jpg', '2025-06-10', N'admin', N'admin', 0);

INSERT INTO DeGiay (ten, trang_thai, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(N'Đế EVA', 1, '2025-06-10', N'admin', N'admin', 0),
(N'Đế Cao Su', 1, '2025-06-10', N'admin', N'admin', 0),
(N'Đế PU', 1, '2025-06-10', N'admin', N'admin', 0),
(N'Đế TPR', 1, '2025-06-10', N'admin', N'admin', 0),
(N'Đế Phylon', 1, '2025-06-10', N'admin', N'admin', 0);

INSERT INTO PhuongThucVanChuyen (ten, mo_ta, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(N'Giao hàng nhanh', N'Giao trong 24h', '2025-06-10', N'admin', N'admin', 0),
(N'Giao hàng tiết kiệm', N'Giao trong 3-5 ngày', '2025-06-10', N'admin', N'admin', 0),
(N'Giao hàng tiêu chuẩn', N'Giao trong 2-4 ngày', '2025-06-10', N'admin', N'admin', 0),
(N'Nhận tại cửa hàng', N'Khách tự đến nhận', '2025-06-10', N'admin', N'admin', 0),
(N'Giao hàng quốc tế', N'Giao ngoài lãnh thổ Việt Nam', '2025-06-10', N'admin', N'admin', 0);
GO

--------------------------------------------------------------------------------
-- 12. DỮ LIỆU MẪU CHO SẢN PHẨM & CHI TIẾT SẢN PHẨM
--------------------------------------------------------------------------------
INSERT INTO SanPham (ten_san_pham, ma_code, mo_ta, thuong_hieu_id, chat_lieu_id, de_giay_id, kieu_dang_id, duong_dan_anh, trang_thai, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(N'Giày Converse Sneaker', N'CONV-01', N'Giày sneaker Converse phong cách cổ điển', 4, 5, 1, 1,N'https://drake.vn/image/catalog/H%C3%ACnh%20content/gi%C3%A0y-sneaker-converse/giay-sneaker-converse-09.jpg', 1, '2025-06-08', N'admin', N'admin', 0),
(N'Giày Adidas Ultraboost', N'AD-UB-20', N'Giày chạy bộ êm ái', 2, 2, 2, 4,N'https://bizweb.dktcdn.net/100/413/756/products/image-1702894353567.png?v=1730995459190', 1, '2025-06-08', N'admin', N'admin', 0),
(N'Giày Nike Court Vision Mid', N'NIKE-CV-MID', N'Giày Nike phối màu Smoke Grey', 1, 3, 3, 3,  N'https://trungsneaker.com/wp-content/uploads/2022/12/giay-nike-court-vision-mid-smoke-grey-dn3577-002-44-1020x680.jpg', 1, '2025-06-08', N'admin', N'admin', 0),
(N'Giày Vans Old Skool', N'VANS-OS-01', N'Giày trượt ván cổ điển đen trắng', 5, 5, 4, 1,  N'https://bizweb.dktcdn.net/100/140/774/products/vans-old-skool-black-white-vn000d3hy28-3.jpg?v=1625905150880', 1, '2025-06-08', N'admin', N'admin', 0),
(N'Giày Puma Speedcat OG', N'PUMA-SC-01', N'Giày Puma Speedcat màu xanh light blue', 3, 2, 5, 1, N'https://sneakerdaily.vn/wp-content/uploads/2024/10/Giay-PUMA-Speedcat-OG-Team-Light-Blue-398847-01.jpg', 1, '2025-06-08', N'admin', N'admin', 0);

INSERT INTO ChiTietSanPham (kich_co_id, san_pham_id, mau_sac_id, duong_dan_anh, gia_tien, so_luong_ton, trang_thai, tien_giam_gia, da_xoa, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat) VALUES
(1, 1, 1, N'https://drake.vn/image/catalog/H%C3%ACnh%20content/gi%C3%A0y-sneaker-converse/giay-sneaker-converse-09.jpg', 2500000, 50, 1, 100000, 0, '2025-06-08', N'admin', N'admin'),
(2, 2, 2, N'https://bizweb.dktcdn.net/100/413/756/products/image-1702894353567.png?v=1730995459190', 2700000, 60, 1, 150000, 0, '2025-06-08', N'admin', N'admin'),
(3, 3, 3, N'https://trungsneaker.com/wp-content/uploads/2022/12/giay-nike-court-vision-mid-smoke-grey-dn3577-002-44-1020x680.jpg', 1800000, 80, 1, 0, 0, '2025-06-08', N'admin', N'admin'),
(4, 4, 4, N'https://bizweb.dktcdn.net/100/140/774/products/vans-old-skool-black-white-vn000d3hy28-3.jpg?v=1625905150880', 3200000, 30, 1, 200000, 0, '2025-06-08', N'admin', N'admin'),
(5, 5, 5, N'https://sneakerdaily.vn/wp-content/uploads/2024/10/Giay-PUMA-Speedcat-OG-Team-Light-Blue-398847-01.jpg', 1500000, 100, 1, 50000, 0, '2025-06-08', N'admin', N'admin');
GO

--------------------------------------------------------------------------------
-- 13. DỮ LIỆU MẪU CHO VAI TRÒ & NHÂN VIÊN, KHÁCH HÀNG, ĐỊA CHỈ
--------------------------------------------------------------------------------
INSERT INTO VaiTro (ten_vai_tro, ngay_tao, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(N'Admin', '2025-06-01', '2025-06-08', N'admin', N'admin', 0),
(N'Nhân viên bán hàng', '2025-06-01', '2025-06-08', N'admin', N'admin', 0),
(N'Quản lý kho', '2025-06-01', '2025-06-08', N'admin', N'admin', 0);

INSERT INTO NhanVien (ho_ten, ngay_sinh, gioi_tinh, email, mat_khau, so_dien_thoai, dia_chi, url_anh, vai_tro_id, ngay_tao, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(N'Nguyễn Văn A', '1990-01-01', N'Nam', N'a@shop.com', N'123456', N'0123456789', N'123 Lý Thường Kiệt', N'https://randomuser.me/api/portraits/men/10.jpg', 1, '2025-06-08', '2025-06-08', N'admin', N'admin', 0),
(N'Trần Thị B', '1992-02-02', N'Nữ', N'b@shop.com', N'abcdef', N'0987654321', N'456 Trần Hưng Đạo', N'https://randomuser.me/api/portraits/women/11.jpg', 2, '2025-06-08', '2025-06-08', N'admin', N'admin', 0);

INSERT INTO KhachHang (ho_ten, ngay_sinh, gioi_tinh, email, mat_khau, so_dien_thoai, url_anh, ngay_tao, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(N'Lê Văn C', '1995-03-03', N'Nam', N'c@gmail.com', N'12345678', N'0901234567', N'https://randomuser.me/api/portraits/men/12.jpg', '2025-06-08', '2025-06-08', N'admin', N'admin', 0),
(N'Phạm Thị D', '1993-04-04', N'Nữ', N'd@gmail.com', N'qwerty', N'0912345678', N'https://randomuser.me/api/portraits/women/13.jpg', '2025-06-08', '2025-06-08', N'admin', N'admin', 0);

INSERT INTO DiaChi (khach_hang_id, ma_quan, ten_quan, ma_tinh, ten_tinh, ma_phuong, ten_phuong, la_mac_dinh, thong_tin_them, so_dien_thoai, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(1, N'Q1', N'Quận 1', N'HCM', N'Hồ Chí Minh', N'P1', N'Phường 1', 1, N'Nhà riêng', N'0901234567', '2025-06-08', N'admin', N'admin', 0),
(2, N'Q3', N'Quận 3', N'HCM', N'Hồ Chí Minh', N'P2', N'Phường 2', 0, N'Văn phòng', N'0912345678', '2025-06-08', N'admin', N'admin', 0);
GO

--------------------------------------------------------------------------------
-- 14. DỮ LIỆU MẪU CHO ĐƠN HÀNG, GIỎ HÀNG, THANH TOÁN, TRẢ HÀNG
--------------------------------------------------------------------------------
INSERT INTO DonHang (khach_hang_id, nhan_vien_id, voucher_id, dia_chi_id, phuong_thuc_van_chuyen_id, so_dien_thoai, ho_ten, email, tong_tien_goc, tien_giam, tong_tien, tien_ship, ngay_xac_nhan, ngay_du_kien, ngay_nhan, loai_don, ghi_chu, ma_don, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(1, 1, 1, 1, 1, N'0901234567', N'Lê Văn C', N'c@gmail.com', 2500000, 100000, 2400000, 30000, '2025-06-09', '2025-06-11', '2025-06-11', 0, N'Giao nhanh', N'DH001', '2025-06-09', N'admin', N'admin', 0),
(2, 2, 2, 2, 3, N'0912345678', N'Phạm Thị D', N'd@gmail.com', 2700000, 50000, 2650000, 30000, '2025-06-09', '2025-06-12', '2025-06-12', 1, N'Đơn ship', N'DH002', '2025-06-09', N'admin', N'admin', 0);

INSERT INTO LichSuDonHang (don_hang_id, trang_thai, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(1, 1, '2025-06-09', N'admin', N'admin', 0),
(2, 1, '2025-06-09', N'admin', N'admin', 0);

INSERT INTO GioHang (khach_hang_id, ngay_tao, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(1, '2025-06-08', '2025-06-08', N'admin', N'admin', 0),
(2, '2025-06-08', '2025-06-08', N'admin', N'admin', 0);

INSERT INTO ChiTietGioHang (gio_hang_id, chi_tiet_san_pham_id, so_luong, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(1, 1, 2, '2025-06-08', N'admin', N'admin', 0),
(1, 2, 1, '2025-06-08', N'admin', N'admin', 0),
(2, 3, 1, '2025-06-08', N'admin', N'admin', 0);

INSERT INTO ChiTietDonHang (don_hang_id, chi_tiet_san_pham_id, so_luong, don_gia, tong_tien, trang_thai, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(1, 1, 2, 1250000, 2500000, 1, '2025-06-09', N'admin', N'admin', 0),
(2, 2, 1, 2700000, 2700000, 1, '2025-06-09', N'admin', N'admin', 0);

INSERT INTO ThanhToan (don_hang_id, phuong_thuc_id, ma_giao_dich, tong_tien, mo_ta, trang_thai, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(1, 1, N'TT001', 2400000, N'Thanh toán khi nhận hàng', 1, '2025-06-09', N'admin', N'admin', 0),
(2, 2, N'TT002', 2650000, N'Thanh toán qua VNPAY', 1, '2025-06-09', N'admin', N'admin', 0);

INSERT INTO PhieuTraHang (ma, gia_tri, so_tien_phai_tra, thong_tin_thanh_toan, hinh_thuc_thanh_toan, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(N'TH001', 1250000, 1250000, N'Ngân hàng A', N'VNPAY', '2025-06-10', N'admin', N'admin', 0),
(N'TH002', 2700000, 2700000, N'Thanh toán COD', N'COD', '2025-06-10', N'admin', N'admin', 0);

INSERT INTO LichSuPhieuTraHang (phieu_tra_hang_id, trang_thai_hanh_dong, ghi_chu, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(1, 1, N'Khách trả do lỗi sản phẩm', '2025-06-10', N'admin', N'admin', 0),
(2, 1, N'Khách không nhận hàng', '2025-06-10', N'admin', N'admin', 0);

INSERT INTO SanPhamKhuyenMai (san_pham_id, khuyen_mai_id, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(1, 1, '2025-06-10', N'admin', N'admin', 0),
(2, 2, '2025-06-10', N'admin', N'admin', 0),
(3, 3, '2025-06-10', N'admin', N'admin', 0),
(4, 4, '2025-06-10', N'admin', N'admin', 0),
(5, 5, '2025-06-10', N'admin', N'admin', 0);

INSERT INTO ChiTietPhieuTraHang (phieu_tra_hang_id, chi_tiet_san_pham_id, so_luong, gia_tri, ghi_chu, ngay_cap_nhat, nguoi_tao, nguoi_cap_nhat, da_xoa) VALUES
(1, 1, 1, 1250000, N'Lỗi sản xuất', '2025-06-10', N'admin', N'admin', 0),
(1, 2, 2, 2500000, N'Không vừa size', '2025-06-10', N'admin', N'admin', 0),
(2, 3, 1, 2700000, N'Khách đổi ý', '2025-06-10', N'admin', N'admin', 0),
(2, 4, 1, 3200000, N'Giao nhầm mẫu', '2025-06-10', N'admin', N'admin', 0),
(2, 5, 1, 1500000, N'Khách không nhận', '2025-06-10', N'admin', N'admin', 0);
GO

--------------------------------------------------------------------------------
-- 15. CÁC SELECT KIỂM TRA
--------------------------------------------------------------------------------
SELECT * FROM ThuongHieu;
SELECT * FROM KieuDang;
SELECT * FROM KichCo;
SELECT * FROM ChatLieu;
SELECT * FROM MauSac;
SELECT * FROM PhuongThucThanhToan;
SELECT * FROM Voucher;
SELECT * FROM DeGiay;
SELECT * FROM PhuongThucVanChuyen;
SELECT * FROM SanPham;
SELECT * FROM ChiTietSanPham;
SELECT * FROM VaiTro;
SELECT * FROM NhanVien;
SELECT * FROM KhachHang;
SELECT * FROM DiaChi;
SELECT * FROM DonHang;
SELECT * FROM LichSuDonHang;
SELECT * FROM GioHang;
SELECT * FROM ChiTietGioHang;
SELECT * FROM ChiTietDonHang;
SELECT * FROM ThanhToan;
SELECT * FROM PhieuTraHang;
SELECT * FROM LichSuPhieuTraHang;
SELECT * FROM SanPhamKhuyenMai;
SELECT * FROM ChiTietPhieuTraHang;
GO