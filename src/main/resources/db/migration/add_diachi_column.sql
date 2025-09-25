-- Thêm cột dia_chi_giao_hang vào bảng don_hang
ALTER TABLE don_hang 
ADD COLUMN IF NOT EXISTS dia_chi_giao_hang VARCHAR(500);

-- Cập nhật giá trị mặc định cho các bản ghi cũ
UPDATE don_hang 
SET dia_chi_giao_hang = 'Chưa có địa chỉ giao hàng' 
WHERE dia_chi_giao_hang IS NULL;
