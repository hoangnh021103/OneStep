-- Update ma_don column length from 10 to 20 characters
-- This fixes the data truncation error when creating orders

ALTER TABLE don_hang MODIFY COLUMN ma_don VARCHAR(20) NOT NULL;

-- Add comment to document the change
ALTER TABLE don_hang MODIFY COLUMN ma_don VARCHAR(20) NOT NULL COMMENT 'Order code, max 20 characters';
