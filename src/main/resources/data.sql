-- =====================
-- USERS
-- =====================
INSERT INTO banks (bank_name, bin_code)
VALUES
    ('Vietcombank', '970436'),
    ('VietinBank', '970415'),
    ('Techcombank', '970407'),
    ('BIDV', '970418'),
    ('MBBank', '970422'),
    ('VPBank', '970432'),
    ('SHB', '970443');

INSERT INTO users (user_id, username, password_hash, full_name, avatar_url, bio, role, date_of_birth, status, bank_number, bank_id)
VALUES
    -- ADMIN accounts
    ('133e4567-e89b-12d3-a456-426614174000', 'admin1@gmail.com', 'admin1', 'Admin One', NULL, 'System administrator', 'ADMIN', '1990-01-01', 'ACTIVE', '0011223344',
        (SELECT bank_id FROM banks WHERE bank_name = 'Vietcombank')),
    ('233e4567-e89b-12d3-a456-426614174001', 'admin2@gmail.com', 'admin2', 'Admin Two', NULL, 'System administrator', 'ADMIN', '1990-01-01', 'ACTIVE', '0055667788',
        (SELECT bank_id FROM banks WHERE bank_name = 'Techcombank')),

    -- USER accounts
    ('633d1f00-4675-4337-b2cb-70deed2f3d13', 'vothanhlong235@gmail.com', '123123', 'Vo Thanh Long 235', NULL, NULL, 'USER', '1989-12-31', 'ACTIVE', '970415001',
        (SELECT bank_id FROM banks WHERE bank_name = 'VietinBank')),
    ('633d1f00-4675-4337-b2cb-70deed2f3d14', 'vothanhlong231@gmail.com', '123123', 'Vo Thanh Long 231', NULL, NULL, 'USER', '1989-12-31', 'ACTIVE', '970436002',
        (SELECT bank_id FROM banks WHERE bank_name = 'Vietcombank')),
    ('633d1f00-4675-4337-b2cb-70deed2f3d15', 'vothanhlong0987@gmail.com', '123123', 'Vo Thanh Long 0987', NULL, NULL, 'MEMBER', '1989-12-31', 'ACTIVE', '970418003',
        (SELECT bank_id FROM banks WHERE bank_name = 'BIDV')),
    ('633d1f00-4675-4337-b2cb-70deed2f3d16', 'vothanhlong233@gmail.com', '123123', 'Vo Thanh Long 233', NULL, NULL, 'USER', '1989-12-31', 'ACTIVE', '107873306270',
        (SELECT bank_id FROM banks WHERE bank_name = 'VietinBank')),
    ('633d1f00-4675-4337-b2cb-70deed2f3d17', 'nguyenhoangan060703@gmail.com', '123123', 'Nguyen Hoang An', NULL, NULL, 'USER', '1989-12-31', 'ACTIVE', '0000148289372',
        (SELECT bank_id FROM banks WHERE bank_name = 'MBBank'));

-- =====================
-- GROUPS
-- =====================
INSERT INTO groups (group_id, name, description, avatar_url, created_by) VALUES
    ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Tech Enthusiasts', 'Group for sharing tech news and projects', NULL, '633d1f00-4675-4337-b2cb-70deed2f3d14'),
    ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'Book Lovers', 'Discuss and share your favorite books', NULL, '633d1f00-4675-4337-b2cb-70deed2f3d14');

---- =====================
---- GROUP MEMBERS
---- =====================
--
---- Tech Enthusiasts group
--INSERT INTO group_members (group_id, user_id, role) VALUES
--    ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '11111111-1111-1111-1111-111111111111', 'ADMIN'),
--    ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '44444444-4444-4444-4444-444444444444', 'MEMBER');
--
---- Book Lovers group
--INSERT INTO group_members (group_id, user_id, role) VALUES
--    ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '44444444-4444-4444-4444-444444444444', 'ADMIN'),
--    ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '55555555-5555-5555-5555-555555555555', 'MEMBER');
--
---- =====================
---- GROUP CHATS
---- =====================
---- Tech Enthusiasts chat
--INSERT INTO group_chats (chat_id, group_id, sender_id, message, type, image_url) VALUES
--    ('ccccccc1-cccc-cccc-cccc-ccccccccccc1', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '11111111-1111-1111-1111-111111111111', 'Welcome to the Tech Enthusiasts group!', 'TEXT', NULL),
--    ('ccccccc2-cccc-cccc-cccc-ccccccccccc2', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '11111111-1111-1111-1111-111111111111', 'Excited to be here 🚀', 'TEXT', NULL),
--    ('ccccccc3-cccc-cccc-cccc-ccccccccccc3', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', '44444444-4444-4444-4444-444444444444', 'Check out this cool diagram', 'IMAGE', 'https://example.com/images/diagram.png');
--
---- Book Lovers chat
--INSERT INTO group_chats (chat_id, group_id, sender_id, message, type, image_url) VALUES
--    ('ccccccc4-cccc-cccc-cccc-ccccccccccc4', 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '44444444-4444-4444-4444-444444444444', 'Welcome to Book Lovers 📚', 'TEXT', NULL),
--    ('ccccccc5-cccc-cccc-cccc-ccccccccccc5', 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '44444444-4444-4444-4444-444444444444', 'Currently reading "Atomic Habits". Anyone else?', 'TEXT', NULL),
--    ('ccccccc6-cccc-cccc-cccc-ccccccccccc6', 'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2', '55555555-5555-5555-5555-555555555555', 'Found a great book cover design!', 'IMAGE', NULL);
--
---- =====================
---- GROUP EVENTS
---- =====================
--
--INSERT INTO group_events (
--    event_id, group_id, creator_id, title,
--    event_date, event_time, created_at, status, repeat_type
--) VALUES
--    ('eeeeeee1-eeee-eeee-eeee-eeeeeeeeeee1',
--     'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1',
--     '11111111-1111-1111-1111-111111111111',
--     'Weekly Tech Talk',
--     '2025-10-05', '19:00', CURRENT_TIMESTAMP,
--     'PENDING', 'WEEKLY'),
--
--    ('eeeeeee2-eeee-eeee-eeee-eeeeeeeeeee2',
--     'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2',
--     '44444444-4444-4444-4444-444444444444',
--     'Project Demo Night',
--     '2025-10-12', '20:00', CURRENT_TIMESTAMP,
--     'DELETED', 'NONE');
--
---- =====================
---- EXPENSE PARTICIPANTS
---- =====================
--
---- Tham gia sự kiện
--INSERT INTO event_participants (event_id, user_id, status, responded_at) VALUES
--    -- Weekly Tech Talk
--    ('eeeeeee1-eeee-eeee-eeee-eeeeeeeeeee1', '11111111-1111-1111-1111-111111111111', 'ACCEPTED', '2025-09-27 10:00:00'),
--    ('eeeeeee1-eeee-eeee-eeee-eeeeeeeeeee1', '44444444-4444-4444-4444-444444444444', 'DECLINED', '2025-09-28 09:30:00'),
--
--    -- Project Demo Night
--    ('eeeeeee2-eeee-eeee-eeee-eeeeeeeeeee2', '44444444-4444-4444-4444-444444444444', 'ACCEPTED', '2025-09-28 14:00:00'),
--    ('eeeeeee2-eeee-eeee-eeee-eeeeeeeeeee2', '55555555-5555-5555-5555-555555555555', 'DECLINED', '2025-09-29 16:20:00');
--
---- =====================
---- Polls
---- =====================
--
--INSERT INTO polls (poll_id, group_id, created_by, poll_name, created_at)
--VALUES
--    ('11111111-1111-1111-1111-111111111111',
--     'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1',
--     '22222222-2222-2222-2222-222222222222',
--     'Best Programming Language 2025',
--     CURRENT_TIMESTAMP);
--
---- =====================
---- Poll Options
---- =====================
--
--INSERT INTO poll_options (option_id, poll_id, option_text)
--VALUES
--    ('33333333-3333-3333-3333-333333333331', '11111111-1111-1111-1111-111111111111', 'AI will replace many jobs'),
--    ('33333333-3333-3333-3333-333333333332', '11111111-1111-1111-1111-111111111111', 'AI will create more jobs'),
--    ('33333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111', 'AI impact will be balanced');
--
---- =====================
---- Poll Votes
---- =====================
--
--INSERT INTO poll_votes (vote_id, poll_id, option_id, user_id, voted_at)
--VALUES
--    ('44444444-4444-4444-4444-444444444441', '11111111-1111-1111-1111-111111111111', '33333333-3333-3333-3333-333333333332', '11111111-1111-1111-1111-111111111111', CURRENT_TIMESTAMP),
--    ('44444444-4444-4444-4444-444444444442', '11111111-1111-1111-1111-111111111111', '33333333-3333-3333-3333-333333333331', '44444444-4444-4444-4444-444444444444', CURRENT_TIMESTAMP);
--
---- =====================
---- EXPENSES
---- =====================
--
--INSERT INTO expenses (expense_id, group_id, description, amount, paid_by)
--VALUES
--  ('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Dinner at BBQ', 600000.00, '11111111-1111-1111-1111-111111111111'), -- Alice trả
--  ('22222222-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Movie tickets', 300000.00, '11111111-1111-1111-1111-111111111111'), -- David trả
--  ('33333333-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Grab ride', 120000.00, '55555555-5555-5555-5555-555555555555'), -- Emily trả
--  ('44444444-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Dinner at LIQI', 600000.00, '11111111-1111-1111-1111-111111111111'); -- Alice trả
--
---- =====================
---- EXPENSE PARTICIPANTS
---- =====================
--
---- BBQ Dinner 600k chia 3 người
--INSERT INTO expense_participants (expense_id, user_id, share_amount, is_paid)
--VALUES
--  ('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 200000.00, TRUE),
--  ('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '44444444-4444-4444-4444-444444444444', 200000.00, FALSE),
--  ('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '55555555-5555-5555-5555-555555555555', 200000.00, FALSE);
--
--INSERT INTO expense_participants (expense_id, user_id, share_amount, is_paid)
--VALUES
--  ('44444444-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 200000.00, TRUE),
--  ('44444444-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '44444444-4444-4444-4444-444444444444', 200000.00, FALSE),
--  ('44444444-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '55555555-5555-5555-5555-555555555555', 200000.00, FALSE);
--
---- Movie tickets 300k chia 2 người
--INSERT INTO expense_participants (expense_id, user_id, share_amount, is_paid)
--VALUES
--  ('22222222-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 150000.00, FALSE),
--  ('22222222-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '44444444-4444-4444-4444-444444444444', 150000.00, TRUE);
--
---- Grab ride 120k chia 3 người
--INSERT INTO expense_participants (expense_id, user_id, share_amount, is_paid)
--VALUES
--  ('33333333-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', 40000.00, FALSE),
--  ('33333333-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '44444444-4444-4444-4444-444444444444', 40000.00, FALSE),
--  ('33333333-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '55555555-5555-5555-5555-555555555555', 40000.00, TRUE);

-- =====================
-- SETTINGS
-- =====================

--INSERT INTO settings (user_id, type, enabled) VALUES
---- Cài đặt chung
--('11111111-1111-1111-1111-111111111111', 'SOUND', TRUE),
--('11111111-1111-1111-1111-111111111111', 'DEVICE_NOTIFICATION', TRUE),
--
---- Thông báo quan trọng
--('11111111-1111-1111-1111-111111111111', 'GROUP_TRANSACTION', TRUE),
--('11111111-1111-1111-1111-111111111111', 'REMINDER', TRUE),
--('11111111-1111-1111-1111-111111111111', 'SECURITY_ALERT', TRUE),
--
---- Thông báo ưu đãi
--('11111111-1111-1111-1111-111111111111', 'SERVICE_PROMO', TRUE),
--('11111111-1111-1111-1111-111111111111', 'VOUCHER', TRUE),
--('11111111-1111-1111-1111-111111111111', 'ADVERTISING', TRUE),
--
---- Thông báo tương tác
--('11111111-1111-1111-1111-111111111111', 'FRIENDS_AND_GROUPS', TRUE),
--('11111111-1111-1111-1111-111111111111', 'GROUP_CHANGE', TRUE),
--('11111111-1111-1111-1111-111111111111', 'SURVEY_FEEDBACK', TRUE);

-- =====================
-- FEEDBACKS
-- =====================

--INSERT INTO feedbacks (user_id, type, content, image_url)
--VALUES
---- 1. Báo lỗi khi chuyển tiền
--('11111111-1111-1111-1111-111111111111', 'BUG',
-- 'I tried to transfer money but the transaction failed without any error message.',
-- NULL),
--
---- 2. Góp ý giao diện
--('22222222-2222-2222-2222-222222222222', 'SUGGESTION',
-- 'It would be nice to have a dark mode for the app.',
-- NULL),
--
---- 3. Báo lỗi hiển thị thông báo
--('33333333-3333-3333-3333-333333333333', 'BUG',
-- 'The notification page doesn’t load sometimes.',
-- 'https://example.com/images/notification_error.jpg'),
--
---- 4. Góp ý về UX khi bình luận
--('44444444-4444-4444-4444-444444444444', 'SUGGESTION',
-- 'Can you make the comment box larger on mobile?',
-- NULL),
--
---- 5. Phản hồi khác
--('55555555-5555-5555-5555-555555555555', 'OTHER',
-- 'Overall I really like the app. Keep up the great work!',
-- NULL);
--
---- =====================
---- NOTIFICATIONS
---- =====================
--INSERT INTO notifications (
--    notification_id, user_id, sender_id, group_id, type, title, message, amount, is_read, created_at, updated_at
--) VALUES
--    -- 1. PAYMENT_RECEIVED: Alice nhận được tiền từ Bob
--    ('aaaa1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
--     '11111111-1111-1111-1111-111111111111',   -- Alice nhận
--     '22222222-2222-2222-2222-222222222222',   -- Bob gửi
--     'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1',   -- Nhóm Tech Enthusiasts
--     'PAYMENT_RECEIVED',
--     'Bob Smith has sent you 100,000₫',
--     'You have received a payment of 100,000₫ from Bob Smith in the group Tech Enthusiasts.',
--     100000.00,
--     FALSE,
--     CURRENT_TIMESTAMP,
--     CURRENT_TIMESTAMP),
--
--    -- 2. PAYMENT_REMINDER: Charlie nhắc David thanh toán
--    ('aaaa2222-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
--     '44444444-4444-4444-4444-444444444444',   -- David nhận nhắc nhở
--     '33333333-3333-3333-3333-333333333333',   -- Charlie (Admin)
--     'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1',
--     'PAYMENT_REMINDER',
--     'Payment reminder for group expenses',
--     'Please complete your pending payment of 150,000₫ in Tech Enthusiasts group.',
--     150000.00,
--     FALSE,
--     CURRENT_TIMESTAMP,
--     CURRENT_TIMESTAMP),
--
--    -- 3. COMMENT: Emily bình luận vào bài viết của Alice
--    ('aaaa3333-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
--     '11111111-1111-1111-1111-111111111111',   -- Alice nhận thông báo
--     '55555555-5555-5555-5555-555555555555',   -- Emily bình luận
--     NULL,
--     'COMMENT',
--     'Emily White commented on your post',
--     'Emily White: "That’s a great project idea, Alice!"',
--     NULL,
--     TRUE,  -- đã đọc rồi
--     CURRENT_TIMESTAMP - INTERVAL '2 days',
--     CURRENT_TIMESTAMP - INTERVAL '2 days'),
--
--    -- 4. INVITE: Bob mời Emily vào nhóm Book Lovers
--    ('aaaa4444-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
--     '55555555-5555-5555-5555-555555555555',   -- Emily nhận
--     '22222222-2222-2222-2222-222222222222',   -- Bob mời
--     'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa2',
--     'INVITE',
--     'Invitation to join Book Lovers',
--     'Bob Smith has invited you to join the group "Book Lovers".',
--     NULL,
--     FALSE,
--     CURRENT_TIMESTAMP,
--     CURRENT_TIMESTAMP),
--
--    -- 5. PAYMENT_RECEIVED: Long (user_id cuối) nhận tiền từ Alice
--    ('aaaa5555-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
--     '633d1f00-4675-4337-b2cb-70deed2f3d13',
--     '11111111-1111-1111-1111-111111111111',
--     NULL,
--     'PAYMENT_RECEIVED',
--     'Alice Johnson sent you 50,000₫',
--     'You have received 50,000₫ from Alice Johnson.',
--     50000.00,
--     FALSE,
--     CURRENT_TIMESTAMP,
--     CURRENT_TIMESTAMP);
--
---- =====================
---- WALLET
---- =====================
INSERT INTO wallets (wallet_id, user_id, balance)
VALUES
('11111111-1111-1111-1111-111111111111', '633d1f00-4675-4337-b2cb-70deed2f3d13', 0),
('22222222-2222-2222-2222-222222222222', '633d1f00-4675-4337-b2cb-70deed2f3d14', 0),
('33333333-3333-3333-3333-333333333333', '633d1f00-4675-4337-b2cb-70deed2f3d15', 0),
('44444444-4444-4444-4444-444444444444', '633d1f00-4675-4337-b2cb-70deed2f3d16', 0),
('66666666-6666-6666-6666-666666666666', '633d1f00-4675-4337-b2cb-70deed2f3d17', 0);

-- =====================
-- WALLET TRANSACTIONS
-- =====================

--INSERT INTO wallet_transactions (wallet_id, amount, type, related_user_id, description)
--VALUES
---- Nạp tiền vào ví (DEPOSIT)
--('11111111-1111-1111-1111-111111111111', 500000.00, 'DEPOSIT', NULL, 'Deposit from bank'),
--
---- Nhận tiền từ người khác (TRANSFER_IN)
--('11111111-1111-1111-1111-111111111111', 150000.00, 'TRANSFER_IN', '22222222-2222-2222-2222-222222222222', 'Received from user B'),
--
---- Chuyển tiền cho người khác (TRANSFER_OUT)
--('11111111-1111-1111-1111-111111111111', 200000.00, 'TRANSFER_OUT', '33333333-3333-3333-3333-333333333333', 'Sent to user C');
--
---- =====================
---- GROUP ALBUMS
---- =====================
-- Mỗi album thuộc về một group cụ thể và do 1 user tạo ra.
INSERT INTO group_albums (album_id, group_id, name, created_by)
VALUES
  ('11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'AI Projects Showcase', '633d1f00-4675-4337-b2cb-70deed2f3d14'),
  ('22222222-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'Tech Memes Collection', '633d1f00-4675-4337-b2cb-70deed2f3d14');

--
---- =====================
---- STORIES
---- =====================
---- Story là ảnh hoặc bài đăng ngắn, có thể thuộc album group hoặc chỉ hiển thị cho followers.
--INSERT INTO stories (story_id, user_id, image_url, caption, visibility_type, album_id)
--VALUES
--  ('aaaa1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111',
--   'https://cdn.fu-exchange.com/stories/ai1.jpg', 'Our first AI prototype demo 🤖',
--   'GROUP', '11111111-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
--
--  ('bbbb2222-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '33333333-3333-3333-3333-333333333333',
--   'https://cdn.fu-exchange.com/stories/meme1.jpg', 'When your code runs on first try 😂',
--   'GROUP', '22222222-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
--
--  ('cccc3333-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '22222222-2222-2222-2222-222222222222',
--   'https://cdn.fu-exchange.com/stories/book1.jpg', 'Just finished “Clean Code” 📘',
--   'GROUP', '33333333-aaaa-aaaa-aaaa-aaaaaaaaaaaa');
--
--
---- =====================
---- STORY COMMENTS
---- =====================
---- Bình luận của người dùng trên từng story.
--INSERT INTO story_comments (comment_id, story_id, user_id, content)
--VALUES
--  ('dddd1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'aaaa1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
--   '22222222-2222-2222-2222-222222222222', 'Amazing project Alice! 🔥'),
--
--  ('dddd2222-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'bbbb2222-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
--   '11111111-1111-1111-1111-111111111111', 'Haha relatable 😅'),
--
--  ('dddd3333-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'cccc3333-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
--   '33333333-3333-3333-3333-333333333333', 'I love that book too 📖');
--
---- =====================
---- FOLLOWS
---- =====================
--INSERT INTO follows (follower_id, following_id)
--VALUES
--('11111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222'),
--('11111111-1111-1111-1111-111111111111', '33333333-3333-3333-3333-333333333333'),
--('22222222-2222-2222-2222-222222222222', '11111111-1111-1111-1111-111111111111'),
--('22222222-2222-2222-2222-222222222222', '44444444-4444-4444-4444-444444444444'),
--('33333333-3333-3333-3333-333333333333', '22222222-2222-2222-2222-222222222222');
--
---- =====================
---- STORY REACTIONS
---- =====================
---- Reaction thể hiện cảm xúc của user trên story, mỗi user chỉ được 1 reaction/story.
--INSERT INTO story_reactions (reaction_id, story_id, user_id, emoji)
--VALUES
--  ('eeee1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'aaaa1111-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
--   '33333333-3333-3333-3333-333333333333', '❤️'),
--
--  ('eeee2222-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'bbbb2222-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
--   '11111111-1111-1111-1111-111111111111', '😂'),
--
--  ('eeee3333-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'cccc3333-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
--   '22222222-2222-2222-2222-222222222222', '👍');
--
---- =====================
---- TRANSACTIONS SAMPLE DATA: DEPOSIT & MEMBERSHIP_PAYMENT
---- =====================
--
--INSERT INTO transactions (order_code, user_id, amount, transaction_type, status, description)
--VALUES
---- Alice nạp tiền vào ví
--(20001, '11111111-1111-1111-1111-111111111111', 500000.00, 'DEPOSIT', 'PAID', 'Deposit to wallet from bank'),
--
---- David nạp tiền vào ví
--(20002, '44444444-4444-4444-4444-444444444444', 300000.00, 'DEPOSIT', 'CANCELED', 'Deposit to wallet from bank'),
--
---- Emily nạp tiền vào ví
--(20003, '55555555-5555-5555-5555-555555555555', 200000.00, 'DEPOSIT', 'PAID', 'Deposit to wallet from bank'),
--
---- Alice mua membership tháng
--(20004, '11111111-1111-1111-1111-111111111111', 150000.00, 'MEMBERSHIP', 'PAID', 'Monthly membership purchase'),
--
---- David mua membership tháng
--(20005, '44444444-4444-4444-4444-444444444444', 150000.00, 'MEMBERSHIP', 'PAID', 'Monthly membership purchase'),
--
---- Long nạp tiền vào ví
--(20006, '633d1f00-4675-4337-b2cb-70deed2f3d13', 500000.00, 'DEPOSIT', 'PAID', 'Deposit to wallet from bank'),
--
---- Vo Thanh Long mua membership tháng
--(20007, '633d1f00-4675-4337-b2cb-70deed2f3d15', 150000.00, 'MEMBERSHIP', 'PAID', 'Monthly membership purchase');

INSERT INTO support_chats (chat_id, user_id, admin_id, status)
VALUES
    -- User vothanhlong235 hỏi admin1
    ('10000000-0000-0000-0000-000000000001', '633d1f00-4675-4337-b2cb-70deed2f3d13', '133e4567-e89b-12d3-a456-426614174000', 'RECEPTION'),

    -- User vothanhlong231 hỏi admin2
    ('10000000-0000-0000-0000-000000000002', '633d1f00-4675-4337-b2cb-70deed2f3d14', '233e4567-e89b-12d3-a456-426614174001', 'RECEPTION'),

    -- User vothanhlong0987 mới mở chat, chưa có admin tiếp nhận
    ('10000000-0000-0000-0000-000000000003', '633d1f00-4675-4337-b2cb-70deed2f3d15', NULL, 'NOT_YET');

INSERT INTO support_messages (chat_id, sender_id, message, created_at)
VALUES
    -- Cuộc chat 1: user vothanhlong235 nói chuyện với admin1
    ('10000000-0000-0000-0000-000000000001', '633d1f00-4675-4337-b2cb-70deed2f3d13', 'Hi admin, I need help with my order.', NOW() - INTERVAL '10 minutes'),
    ('10000000-0000-0000-0000-000000000001', '133e4567-e89b-12d3-a456-426614174000', 'Sure! What seems to be the issue?', NOW() - INTERVAL '9 minutes'),
    ('10000000-0000-0000-0000-000000000001', '633d1f00-4675-4337-b2cb-70deed2f3d13', 'I haven’t received my confirmation email.', NOW() - INTERVAL '8 minutes'),
    ('10000000-0000-0000-0000-000000000001', '133e4567-e89b-12d3-a456-426614174000', 'Got it. I will check your transaction and get back shortly.', NOW() - INTERVAL '7 minutes'),

    -- Cuộc chat 2: user vothanhlong231 nói chuyện với admin2
    ('10000000-0000-0000-0000-000000000002', '633d1f00-4675-4337-b2cb-70deed2f3d14', 'Hello, I’m facing a payment issue.', NOW() - INTERVAL '6 minutes'),
    ('10000000-0000-0000-0000-000000000002', '233e4567-e89b-12d3-a456-426614174001', 'Hi! Can you tell me what payment method you used?', NOW() - INTERVAL '5 minutes'),
    ('10000000-0000-0000-0000-000000000002', '633d1f00-4675-4337-b2cb-70deed2f3d14', 'I used VNPay but it failed after redirect.', NOW() - INTERVAL '4 minutes'),
    ('10000000-0000-0000-0000-000000000002', '233e4567-e89b-12d3-a456-426614174001', 'Thanks. We’ll investigate and refund if necessary.', NOW() - INTERVAL '3 minutes'),

    -- Cuộc chat 3: user vothanhlong0987 mới mở chat, chưa có admin tiếp nhận
    ('10000000-0000-0000-0000-000000000003', '633d1f00-4675-4337-b2cb-70deed2f3d15', 'Hello, is anyone available?', NOW() - INTERVAL '2 minutes');