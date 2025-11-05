-- Migration script: Thêm cột file_path vào bảng favourite và my_play_list
-- Chạy script này để lưu file_path trực tiếp vào bảng favourite và my_play_list

USE music_app;

-- Thêm cột file_path vào bảng favourite (chỉ thêm nếu chưa có)
SET @dbname = DATABASE();
SET @tablename = 'favourite';
SET @columnname = 'file_path';
SET @preparedStatement = (SELECT IF(
  (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
    WHERE
      (TABLE_SCHEMA = @dbname)
      AND (TABLE_NAME = @tablename)
      AND (COLUMN_NAME = @columnname)
  ) > 0,
  "SELECT 'Column already exists.'",
  CONCAT("ALTER TABLE ", @tablename, " ADD COLUMN ", @columnname, " VARCHAR(500) AFTER song_id")
));
PREPARE alterIfNotExists FROM @preparedStatement;
EXECUTE alterIfNotExists;
DEALLOCATE PREPARE alterIfNotExists;

-- Thêm cột file_path vào bảng my_play_list (chỉ thêm nếu chưa có)
SET @tablename = 'my_play_list';
SET @preparedStatement = (SELECT IF(
  (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
    WHERE
      (TABLE_SCHEMA = @dbname)
      AND (TABLE_NAME = @tablename)
      AND (COLUMN_NAME = @columnname)
  ) > 0,
  "SELECT 'Column already exists.'",
  CONCAT("ALTER TABLE ", @tablename, " ADD COLUMN ", @columnname, " VARCHAR(500) AFTER song_id")
));
PREPARE alterIfNotExists FROM @preparedStatement;
EXECUTE alterIfNotExists;
DEALLOCATE PREPARE alterIfNotExists;

-- Cập nhật dữ liệu hiện có: lấy file_path từ bảng songs
UPDATE favourite f
INNER JOIN songs s ON f.song_id = s.id
SET f.file_path = s.file_path
WHERE f.file_path IS NULL AND s.file_path IS NOT NULL;

UPDATE my_play_list p
INNER JOIN songs s ON p.song_id = s.id
SET p.file_path = s.file_path
WHERE p.file_path IS NULL AND s.file_path IS NOT NULL;

-- Kiểm tra kết quả
SELECT 'favourite' as table_name, COUNT(*) as total_rows, 
       COUNT(file_path) as rows_with_file_path
FROM favourite
UNION ALL
SELECT 'my_play_list' as table_name, COUNT(*) as total_rows,
       COUNT(file_path) as rows_with_file_path
FROM my_play_list;

