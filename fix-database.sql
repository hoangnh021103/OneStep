-- Fix database schema for ma_don column
-- Run this script in your MySQL database

USE sanday;

-- Update the ma_don column to allow 20 characters instead of 10
ALTER TABLE don_hang MODIFY COLUMN ma_don VARCHAR(20) NOT NULL;

-- Verify the change
DESCRIBE don_hang;

-- Show current orders to verify everything is working
SELECT id, ma_don, ho_ten, loai_don, trang_thai, ngay_cap_nhat 
FROM don_hang 
WHERE da_xoa = 0 
ORDER BY id DESC 
LIMIT 10;
