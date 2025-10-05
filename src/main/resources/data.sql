-- =====================
-- USERS
-- =====================

INSERT INTO users (user_id, username, password_hash, full_name, avatar_url, bio, role, date_of_birth, status)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'alice123', 'hashed_password_1', 'Alice Johnson', NULL, 'Loves reading and coding.', 'USER', '1995-03-12', 'ACTIVE'),
    ('44444444-4444-4444-4444-444444444444', 'david99', 'hashed_password_4', 'David Lee', NULL, 'Casual user who enjoys music.', 'USER', '2000-01-30', 'PENDING'),
    ('55555555-5555-5555-5555-555555555555', 'emily_w', 'hashed_password_5', 'Emily White', NULL, NULL, 'USER', '1998-09-14', 'INACTIVE'),
    ('22222222-2222-2222-2222-222222222222', 'bob_the_staff', 'hashed_password_2', 'Bob Smith', NULL, 'Moderator of tech topics.', 'STAFF', '1990-07-25', 'ACTIVE'),
    ('33333333-3333-3333-3333-333333333333', 'charlie_admin', 'hashed_password_3', 'Charlie Nguyen', NULL, 'Site administrator and backend dev.', 'ADMIN', '1988-11-05', 'ACTIVE'),
    ('633d1f00-4675-4337-b2cb-70deed2f3d13', 'vothanhlong235@gmail.com', '123123', 'adsdsa dsadas', NULL, NULL, 'USER', '1989-12-31', 'ACTIVE'),
    ('633d1f00-4675-4337-b2cb-70deed2f3d14', 'vothanhlong231@gmail.com', '123123', 'adsdsa dsadas', NULL, NULL, 'USER', '1989-12-31', 'ACTIVE'),
    ('633d1f00-4675-4337-b2cb-70deed2f3d15', 'vothanhlong233@gmail.com', '123123', 'adsdsa dsadas', NULL, NULL, 'USER', '1989-12-31', 'ACTIVE');

-- =====================
-- GROUPS
-- =====================
INSERT INTO groups (group_id, name, description, avatar_url, created_by) VALUES
    ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Tech Enthusiasts', 'Group for sharing tech news and projects', NULL, '11111111-1111-1111-1111-111111111111'),
    ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'Book Lovers', 'Discuss and share your favorite books', NULL, '22222222-2222-2222-2222-222222222222');

-- =====================
-- GROUP MEMBERS
-- =====================

-- Tech Enthusiasts group
INSERT INTO group_members (group_id, user_id, role) VALUES
    ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '11111111-1111-1111-1111-111111111111', 'ADMIN'),
    ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '44444444-4444-4444-4444-444444444444', 'MEMBER');

-- Book Lovers group
INSERT INTO group_members (group_id, user_id, role) VALUES
    ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '44444444-4444-4444-4444-444444444444', 'ADMIN'),
    ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '55555555-5555-5555-5555-555555555555', 'MEMBER');

-- =====================
-- GROUP CHATS
-- =====================
-- Tech Enthusiasts chat
INSERT INTO group_chats (chat_id, group_id, sender_id, message, type, image_url) VALUES
    ('ccccccc1-cccc-cccc-cccc-ccccccccccc1', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '11111111-1111-1111-1111-111111111111', 'Welcome to the Tech Enthusiasts group!', 'TEXT', NULL),
    ('ccccccc2-cccc-cccc-cccc-ccccccccccc2', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '11111111-1111-1111-1111-111111111111', 'Excited to be here 🚀', 'TEXT', NULL),
    ('ccccccc3-cccc-cccc-cccc-ccccccccccc3', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '44444444-4444-4444-4444-444444444444', 'Check out this cool diagram', 'IMAGE', 'https://example.com/images/diagram.png');

-- Book Lovers chat
INSERT INTO group_chats (chat_id, group_id, sender_id, message, type, image_url) VALUES
    ('ccccccc4-cccc-cccc-cccc-ccccccccccc4', 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '44444444-4444-4444-4444-444444444444', 'Welcome to Book Lovers 📚', 'TEXT', NULL),
    ('ccccccc5-cccc-cccc-cccc-ccccccccccc5', 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '44444444-4444-4444-4444-444444444444', 'Currently reading "Atomic Habits". Anyone else?', 'TEXT', NULL),
    ('ccccccc6-cccc-cccc-cccc-ccccccccccc6', 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '55555555-5555-5555-5555-555555555555', 'Found a great book cover design!', 'IMAGE', NULL);

-- =====================
-- GROUP EVENTS
-- =====================

INSERT INTO group_events (
    event_id, group_id, creator_id, title, description,
    event_date, event_time, created_at, status, repeat_type
) VALUES
    ('eeeeeee1-eeee-eeee-eeee-eeeeeeeeeee1',
     'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1',
     '11111111-1111-1111-1111-111111111111',
     'Weekly Tech Talk',
     'Discussion about the latest in AI and software development.',
     '2025-10-05', '19:00', CURRENT_TIMESTAMP,
     'PENDING', 'WEEKLY'),

    ('eeeeeee2-eeee-eeee-eeee-eeeeeeeeeee2',
     'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2',
     '44444444-4444-4444-4444-444444444444',
     'Project Demo Night',
     'Members showcase their personal or group projects.',
     '2025-10-12', '20:00', CURRENT_TIMESTAMP,
     'DELETED', 'NONE');

-- =====================
-- EXPENSE PARTICIPANTS
-- =====================

-- Tham gia sự kiện
INSERT INTO event_participants (event_id, user_id, status, responded_at) VALUES
    -- Weekly Tech Talk
    ('eeeeeee1-eeee-eeee-eeee-eeeeeeeeeee1', '11111111-1111-1111-1111-111111111111', 'ACCEPTED', '2025-09-27 10:00:00'),
    ('eeeeeee1-eeee-eeee-eeee-eeeeeeeeeee1', '44444444-4444-4444-4444-444444444444', 'DECLINED', '2025-09-28 09:30:00'),

    -- Project Demo Night
    ('eeeeeee2-eeee-eeee-eeee-eeeeeeeeeee2', '44444444-4444-4444-4444-444444444444', 'ACCEPTED', '2025-09-28 14:00:00'),
    ('eeeeeee2-eeee-eeee-eeee-eeeeeeeeeee2', '55555555-5555-5555-5555-555555555555', 'DECLINED', '2025-09-29 16:20:00');

-- =====================
-- Polls
-- =====================

INSERT INTO polls (poll_id, group_id, created_by, poll_name, created_at)
VALUES
    ('11111111-1111-1111-1111-111111111111',
     'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1',
     '22222222-2222-2222-2222-222222222222',
     'Best Programming Language 2025',
     CURRENT_TIMESTAMP);

-- =====================
-- Poll Options
-- =====================

INSERT INTO poll_options (option_id, poll_id, option_text)
VALUES
    ('33333333-3333-3333-3333-333333333331', '11111111-1111-1111-1111-111111111111', 'AI will replace many jobs'),
    ('33333333-3333-3333-3333-333333333332', '11111111-1111-1111-1111-111111111111', 'AI will create more jobs'),
    ('33333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111', 'AI impact will be balanced');

-- =====================
-- Poll Votes
-- =====================

INSERT INTO poll_votes (vote_id, poll_id, option_id, user_id, voted_at)
VALUES
    ('44444444-4444-4444-4444-444444444441', '11111111-1111-1111-1111-111111111111', '33333333-3333-3333-3333-333333333332', '11111111-1111-1111-1111-111111111111', CURRENT_TIMESTAMP),
    ('44444444-4444-4444-4444-444444444442', '11111111-1111-1111-1111-111111111111', '33333333-3333-3333-3333-333333333331', '44444444-4444-4444-4444-444444444444', CURRENT_TIMESTAMP);

-- =====================
-- EXPENSES
-- =====================

INSERT INTO expenses (expense_id, group_id, description, amount, paid_by)
VALUES
  ('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Dinner at BBQ', 600.00, '11111111-1111-1111-1111-111111111111'), -- Alice trả
  ('22222222-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Movie tickets', 300.00, '11111111-1111-1111-1111-111111111111'), -- David trả
  ('33333333-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Grab ride', 120.00, '55555555-5555-5555-5555-555555555555'); -- Emily trả

-- =====================
-- EXPENSE PARTICIPANTS
-- =====================

-- BBQ Dinner 600k chia 3 người
INSERT INTO expense_participants (expense_id, user_id, share_amount, is_paid)
VALUES
  ('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 200.00, TRUE),
  ('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '44444444-4444-4444-4444-444444444444', 200.00, FALSE),
  ('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '55555555-5555-5555-5555-555555555555', 200.00, FALSE);

-- Movie tickets 300k chia 2 người
INSERT INTO expense_participants (expense_id, user_id, share_amount, is_paid)
VALUES
  ('22222222-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 150.00, FALSE),
  ('22222222-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '44444444-4444-4444-4444-444444444444', 150.00, TRUE);

-- Grab ride 120k chia 3 người
INSERT INTO expense_participants (expense_id, user_id, share_amount, is_paid)
VALUES
  ('33333333-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 40.00, FALSE),
  ('33333333-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '44444444-4444-4444-4444-444444444444', 40.00, FALSE),
  ('33333333-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '55555555-5555-5555-5555-555555555555', 40.00, TRUE);

-- =====================
-- SETTINGS
-- =====================

INSERT INTO settings (user_id, type, enabled) VALUES
-- Cài đặt chung
('11111111-1111-1111-1111-111111111111', 'SOUND', TRUE),
('11111111-1111-1111-1111-111111111111', 'DEVICE_NOTIFICATION', TRUE),

-- Thông báo quan trọng
('11111111-1111-1111-1111-111111111111', 'GROUP_TRANSACTION', TRUE),
('11111111-1111-1111-1111-111111111111', 'REMINDER', TRUE),
('11111111-1111-1111-1111-111111111111', 'SECURITY_ALERT', TRUE),

-- Thông báo ưu đãi
('11111111-1111-1111-1111-111111111111', 'SERVICE_PROMO', TRUE),
('11111111-1111-1111-1111-111111111111', 'VOUCHER', TRUE),
('11111111-1111-1111-1111-111111111111', 'ADVERTISING', TRUE),

-- Thông báo tương tác
('11111111-1111-1111-1111-111111111111', 'FRIENDS_AND_GROUPS', TRUE),
('11111111-1111-1111-1111-111111111111', 'GROUP_CHANGE', TRUE),
('11111111-1111-1111-1111-111111111111', 'SURVEY_FEEDBACK', TRUE);

-- =====================
-- FEEDBACKS
-- =====================

INSERT INTO feedbacks (user_id, type, content, image_url)
VALUES
-- 1. Báo lỗi khi chuyển tiền
('11111111-1111-1111-1111-111111111111', 'BUG',
 'I tried to transfer money but the transaction failed without any error message.',
 NULL),

-- 2. Góp ý giao diện
('22222222-2222-2222-2222-222222222222', 'SUGGESTION',
 'It would be nice to have a dark mode for the app.',
 NULL),

-- 3. Báo lỗi hiển thị thông báo
('33333333-3333-3333-3333-333333333333', 'BUG',
 'The notification page doesn’t load sometimes.',
 'https://example.com/images/notification_error.jpg'),

-- 4. Góp ý về UX khi bình luận
('44444444-4444-4444-4444-444444444444', 'SUGGESTION',
 'Can you make the comment box larger on mobile?',
 NULL),

-- 5. Phản hồi khác
('55555555-5555-5555-5555-555555555555', 'OTHER',
 'Overall I really like the app. Keep up the great work!',
 NULL);