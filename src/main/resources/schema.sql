DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS group_members CASCADE;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS group_chats CASCADE;
DROP TABLE IF EXISTS group_events CASCADE;
DROP TABLE IF EXISTS event_participants CASCADE;
DROP TABLE IF EXISTS reminders CASCADE;
DROP TABLE IF EXISTS polls CASCADE;
DROP TABLE IF EXISTS poll_options CASCADE;
DROP TABLE IF EXISTS poll_votes CASCADE;
DROP TABLE IF EXISTS expenses CASCADE;
DROP TABLE IF EXISTS expense_participants CASCADE;
DROP TABLE IF EXISTS debts CASCADE;
DROP TABLE IF EXISTS settings CASCADE;
DROP TABLE IF EXISTS faqs CASCADE;
DROP TABLE IF EXISTS support_chats CASCADE;
DROP TABLE IF EXISTS support_messages CASCADE;
DROP TABLE IF EXISTS feedbacks CASCADE;
DROP TABLE IF EXISTS follows CASCADE;
DROP TABLE IF EXISTS stories CASCADE;
DROP TABLE IF EXISTS notifications CASCADE;
DROP TABLE IF EXISTS wallets CASCADE;
DROP TABLE IF EXISTS wallet_transactions CASCADE;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

create table users (
    user_id uuid primary key default gen_random_uuid(),
    username varchar(50) unique not null,
    password_hash text not null,
    full_name varchar(100),
    avatar_url text,
    bio text,
    date_of_birth date,
    role varchar(20) check (role in ('USER','STAFF','ADMIN')) default 'USER',
    status varchar(20) check (status in ('ACTIVE','INACTIVE','BANNED','PENDING')) default 'PENDING',
    created_at timestamp default now()
);
-- =====================
-- Groups Table
-- =====================
CREATE TABLE groups (
    group_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name         VARCHAR(255) NOT NULL,
    description  TEXT,
    avatar_url   TEXT,
--    qrcode_url   TEXT,
    created_by   UUID NOT NULL,
    created_at   TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- =====================
-- GroupMembers Table
-- =====================
CREATE TABLE group_members (
    group_id   UUID NOT NULL,
    user_id    UUID NOT NULL,
    role       VARCHAR(50) CHECK (role IN ('ADMIN', 'MEMBER')) DEFAULT 'MEMBER',
    joined_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (group_id, user_id),
    FOREIGN KEY (group_id) REFERENCES groups(group_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) -- nếu có bảng users
);

-- =====================
-- GroupChats Table
-- =====================
CREATE TABLE group_chats (
    chat_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    group_id    UUID NOT NULL,
    sender_id   UUID NOT NULL,
    message     TEXT NOT NULL,
    type        VARCHAR(50) CHECK (type IN ('TEXT', 'IMAGE', 'FILE')) DEFAULT 'TEXT',
    image_url   TEXT, -- 👈 thêm cột này để lưu link ảnh
    created_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (group_id) REFERENCES groups(group_id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES users(user_id)
);

-- =====================
-- Event Table
-- =====================
-- Bảng sự kiện nhóm
CREATE TABLE group_events (
    event_id    UUID PRIMARY KEY,
    creator_id  UUID NOT NULL REFERENCES users(user_id),
    group_id    UUID NOT NULL REFERENCES groups(group_id) ON DELETE CASCADE,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    event_date  DATE NOT NULL,
    event_time  TIME NOT NULL,
    status      VARCHAR(20) DEFAULT 'PENDING'
                  CHECK (status IN ('PENDING', 'DELETED')),
    repeat_type VARCHAR(20) DEFAULT 'NONE'
                  CHECK (repeat_type IN ('NONE', 'DAILY', 'WEEKLY', 'MONTHLY')),
    created_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Bảng thành viên tham gia sự kiện
CREATE TABLE event_participants (
    event_id UUID REFERENCES group_events(event_id) ON DELETE CASCADE,
    user_id UUID REFERENCES users(user_id) ON DELETE CASCADE,
    status VARCHAR(20)
             CHECK (status IN ('ACCEPTED', 'DECLINED')),
    responded_at TIMESTAMP,
    PRIMARY KEY (event_id, user_id)
);
-- =====================
-- Polls Table
-- =====================
CREATE TABLE polls (
    poll_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    group_id    UUID NOT NULL,
    created_by  UUID NOT NULL,
    poll_name   VARCHAR(255) NOT NULL,
    created_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (group_id) REFERENCES groups(group_id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

-- =====================
-- PollOptions Table
-- =====================
CREATE TABLE poll_options (
    option_id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    poll_id      UUID NOT NULL,
    option_text  TEXT NOT NULL,
    FOREIGN KEY (poll_id) REFERENCES polls(poll_id) ON DELETE CASCADE
);

-- =====================
-- PollVotes Table
-- =====================
CREATE TABLE poll_votes (
    vote_id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    poll_id    UUID NOT NULL,
    option_id  UUID NOT NULL,
    user_id    UUID NOT NULL,
    voted_at   TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (poll_id) REFERENCES polls(poll_id) ON DELETE CASCADE,
    FOREIGN KEY (option_id) REFERENCES poll_options(option_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- =====================
-- Expenses Table
-- =====================
CREATE TABLE expenses (
    expense_id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    group_id     UUID NOT NULL,
    description  TEXT NOT NULL,
    amount       DECIMAL(12,2) NOT NULL CHECK (amount >= 0),
    paid_by      UUID NOT NULL,
    created_at   TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (group_id) REFERENCES groups(group_id) ON DELETE CASCADE,
    FOREIGN KEY (paid_by) REFERENCES users(user_id)
);

-- =====================
-- ExpenseParticipants Table
-- =====================
CREATE TABLE expense_participants (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    expense_id    UUID NOT NULL,
    user_id       UUID NOT NULL,
    share_amount  DECIMAL(12,2) NOT NULL CHECK (share_amount >= 0),
    is_paid       BOOLEAN DEFAULT FALSE,
    paid_at       TIMESTAMPTZ,
    FOREIGN KEY (expense_id) REFERENCES expenses(expense_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- =====================
-- Settings Table
-- =====================
CREATE TABLE settings (
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id  UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    type     VARCHAR(50) NOT NULL CHECK (
        type IN (
            'SOUND',
            'DEVICE_NOTIFICATION',
            'GROUP_TRANSACTION',
            'REMINDER',
            'SECURITY_ALERT',
            'SERVICE_PROMO',
            'VOUCHER',
            'ADVERTISING',
            'FRIENDS_AND_GROUPS',
            'GROUP_CHANGE',
            'SURVEY_FEEDBACK'
        )
    ),
    enabled BOOLEAN DEFAULT TRUE
);

-- =====================
-- Support Chats Table
-- =====================
CREATE TABLE support_chats (
    chat_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id     UUID NOT NULL,
    admin_id    UUID,
    created_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    status      VARCHAR(20) CHECK (status IN ('OPEN', 'CLOSED')) DEFAULT 'OPEN',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (admin_id) REFERENCES users(user_id)
);

-- =====================
-- Support Messages Table
-- =====================
CREATE TABLE support_messages (
    message_id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    chat_id     UUID NOT NULL,
    sender_id   UUID NOT NULL,
    message     TEXT NOT NULL,
    created_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (chat_id) REFERENCES support_chats(chat_id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES users(user_id)
);

-- =====================
-- Feedbacks Table
-- =====================
CREATE TABLE feedbacks (
    feedback_id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id      UUID NOT NULL,
    type         VARCHAR(20) NOT NULL CHECK (type IN ('BUG', 'SUGGESTION', 'OTHER')),
    content      TEXT NOT NULL,
    image_url    TEXT,
    created_at   TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- =====================
-- Follows Table
-- =====================
CREATE TABLE follows (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    follower_id   UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    following_id  UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    created_at    TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (follower_id, following_id)
);

-- =====================
-- Stories Table
-- =====================
CREATE TABLE stories (
    story_id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id     UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    image_url   TEXT NOT NULL,
    caption     TEXT,
    created_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    expires_at  TIMESTAMPTZ NOT NULL  -- ví dụ: 24h sau khi tạo
);

-- =====================
-- Notifications Table
-- =====================
CREATE TABLE notifications (
    notification_id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id           UUID NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,  -- người nhận thông báo
    sender_id         UUID REFERENCES users(user_id) ON DELETE SET NULL,          -- người gửi / người liên quan
    group_id          UUID REFERENCES groups(group_id) ON DELETE SET NULL,        -- nhóm liên quan (nếu có)
    type              VARCHAR(50) NOT NULL CHECK (
                         type IN ('PAYMENT_RECEIVED', 'PAYMENT_REMINDER', 'COMMENT', 'INVITE', 'TRANSFER')
                      ),
    title             VARCHAR(255),        -- tiêu đề ngắn (VD: "Lê Anh đã trả bạn 100.000đ")
    message           TEXT NOT NULL,       -- nội dung chi tiết
    amount            NUMERIC(15,2),       -- số tiền liên quan (nếu có)
    is_read           BOOLEAN DEFAULT FALSE,
    created_at        TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- =====================
-- Wallets Table
-- =====================
CREATE TABLE wallets (
    wallet_id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id     UUID UNIQUE NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    balance     NUMERIC(12,2) DEFAULT 0 CHECK (balance >= 0),
    updated_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- =====================
-- Wallet Transactions Table
-- =====================
CREATE TABLE wallet_transactions (
    transaction_id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    wallet_id       UUID NOT NULL REFERENCES wallets(wallet_id) ON DELETE CASCADE,
    amount          NUMERIC(12,2) NOT NULL CHECK (amount > 0),
    type            VARCHAR(30) NOT NULL CHECK (type IN ('DEPOSIT', 'TRANSFER_IN', 'TRANSFER_OUT')),
    related_user_id UUID REFERENCES users(user_id) ON DELETE SET NULL,  -- người liên quan (người nhận hoặc người gửi)
    description     TEXT,
    created_at      TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);