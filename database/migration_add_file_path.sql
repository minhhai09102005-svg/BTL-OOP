-- Migration script: Thêm cột file_path vào bảng songs
-- Chạy script này nếu database đã tồn tại và chưa có cột file_path

USE music_app;

-- Kiểm tra và thêm cột file_path nếu chưa có
ALTER TABLE songs 
ADD COLUMN IF NOT EXISTS file_path VARCHAR(500) AFTER release_date;

-- Kiểm tra kết quả
SELECT COLUMN_NAME, DATA_TYPE, CHARACTER_MAXIMUM_LENGTH 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_SCHEMA = 'music_app' 
  AND TABLE_NAME = 'songs' 
  AND COLUMN_NAME = 'file_path';

