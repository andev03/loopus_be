INSERT INTO users (username, password_hash, full_name, avatar_url, bio, role)
VALUES
    ('alice123', 'hashed_password_1', 'Alice Johnson', 'https://example.com/avatars/alice.jpg', 'Loves reading and coding.', 'USER'),
    ('bob_the_staff', 'hashed_password_2', 'Bob Smith', 'https://example.com/avatars/bob.jpg', 'Moderator of tech topics.', 'STAFF'),
    ('charlie_admin', 'hashed_password_3', 'Charlie Nguyen', 'https://example.com/avatars/charlie.jpg', 'Site administrator and backend dev.', 'ADMIN'),
    ('david99', 'hashed_password_4', 'David Lee', NULL, 'Casual user who enjoys music.', 'USER'),
    ('emily_w', 'hashed_password_5', 'Emily White', 'https://example.com/avatars/emily.jpg', NULL, 'USER');
