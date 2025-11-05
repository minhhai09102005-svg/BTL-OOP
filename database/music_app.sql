-- Schema cho music_app theo baocaooop.pdf
CREATE DATABASE IF NOT EXISTS music_app CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE music_app;

-- -----------------------------
-- BẢNG user (Tạo trước tiên)
-- -----------------------------
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('user','artist') NOT NULL DEFAULT 'user'
);

-- -----------------------------
-- BẢNG genres (Tạo trước tiên)
-- -----------------------------
CREATE TABLE IF NOT EXISTS genres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- -----------------------------
-- BẢNG album (Phụ thuộc vào 'user')
-- -----------------------------
CREATE TABLE IF NOT EXISTS album (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    release_date DATE,
    artist_id INT,
    FOREIGN KEY (artist_id) REFERENCES user(id) ON DELETE SET NULL
);

-- -----------------------------
-- BẢNG songs (Phụ thuộc vào 'album')
-- -----------------------------
CREATE TABLE IF NOT EXISTS songs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    song_title VARCHAR(255) NOT NULL,
    artists VARCHAR(255),
    album_id INT,
    duration INT,
    lyric TEXT,
    release_date DATE,
    file_path VARCHAR(500),  -- ⭐ Đường dẫn file MP3 trên filesystem
    FOREIGN KEY (album_id) REFERENCES album(id) ON DELETE SET NULL
);

-- -----------------------------
-- BẢNG favourite (Phụ thuộc vào 'user' và 'songs')
-- -----------------------------
CREATE TABLE IF NOT EXISTS favourite (
    user_id INT NOT NULL,
    song_id INT NOT NULL,
    file_path VARCHAR(500),  -- ⭐ Đường dẫn file MP3 (để truy vấn nhanh hơn)
    PRIMARY KEY (user_id, song_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE CASCADE
);

-- -----------------------------
-- BẢNG my_play_list (Phụ thuộc vào 'user' và 'songs')
-- -----------------------------
CREATE TABLE IF NOT EXISTS my_play_list (
    user_id INT NOT NULL,
    song_id INT NOT NULL,
    file_path VARCHAR(500),  -- ⭐ Đường dẫn file MP3 (để truy vấn nhanh hơn)
    PRIMARY KEY (user_id, song_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE CASCADE
);

-- -----------------------------
-- BẢNG song_genres (Phụ thuộc vào 'songs' và 'genres')
-- -----------------------------
CREATE TABLE IF NOT EXISTS song_genres (
    song_id INT NOT NULL,
    genre_id INT NOT NULL,
    PRIMARY KEY (song_id, genre_id),
    FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

